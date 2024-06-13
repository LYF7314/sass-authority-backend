package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.FunctionNode;
import edu.hitwh.entity.*;
import edu.hitwh.mapper.FrameFunctionMapper;
import edu.hitwh.mapper.FrameTenantfunctionMapper;
import edu.hitwh.mapper.FrameUserFunctionViewMapper;
import edu.hitwh.mapper.FrameUserfunctionMapper;
import edu.hitwh.service.IFrameFunctionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@Service
public class FrameFunctionServiceImpl extends ServiceImpl<FrameFunctionMapper, Function> implements IFrameFunctionService {

    @Resource
    private FrameFunctionMapper frameFunctionMapper;

    @Resource
    private FrameUserfunctionMapper frameUserfunctionMapper;

    @Resource
    private FrameTenantfunctionMapper tenantFunctionMapper;

    @Resource
    private FrameUserFunctionViewMapper userFunctionViewMapper;

    @Override
    public Result createFunction(Function function) {
        function.setState(Function.STATE_AVAILABLE);
        frameFunctionMapper.insert(function);
        return Result.ok();
    }

    @Override
    public Result getAllFunctions() {
        List<FunctionNode> functionNodeTree = getFunctionTree(0L);
        if (functionNodeTree == null || functionNodeTree.size() == 0) {
            return Result.fail("Not any function nodes");
        }
        return Result.ok(functionNodeTree);
    }

    public List<FunctionNode> getFunctionTree(Long parentId) {
        List<Function> functions = frameFunctionMapper.selectByParentId(parentId);
        List<FunctionNode> functionNodes = functions.stream()
                .map(FunctionNode::new)
                .collect(Collectors.toList());
        functionNodes.forEach(node -> {
            if (node.getIsLeaf() == 0) {
                node.setChildren(getFunctionTree(node.getId()));
            }
        });
        return functionNodes;
    }

    @Override
    public Result updateFunction(Function function) {
        Function targetFunction = frameFunctionMapper.selectById(function.getId());
        targetFunction.setName(function.getName());
        targetFunction.setIsLeaf(function.getIsLeaf());
        targetFunction.setOrder(function.getOrder());
        targetFunction.setUrl(function.getUrl());
        targetFunction.setServiceId(function.getServiceId());
        int res = frameFunctionMapper.updateById(targetFunction);
        if (res == 0) {
            return Result.fail("No function was updated");
        }
        return Result.ok();
    }

    @Override
    public Result deleteFunction(Long id) {
        // 获取要删除的功能及其子功能的ID列表
        List<Long> functionIds = new ArrayList<>();
        collectFunctionIds(id, functionIds);

        // 执行删除操作
        int deletedCount = frameFunctionMapper.deleteBatchIds(functionIds);

        if (deletedCount == 0) {
            return Result.fail("No function was deleted");
        }

        return Result.ok();
    }

    // 递归收集功能及其子功能的ID
    private void collectFunctionIds(Long parentId, List<Long> functionIds) {
        // 获取当前父功能下的所有子功能
        List<Function> functions = frameFunctionMapper.selectByParentId(parentId);

        // 将当前父功能加入ID列表
        functionIds.add(parentId);

        // 递归处理子功能
        for (Function function : functions) {
            collectFunctionIds(function.getId(), functionIds);
        }
    }

    @Override
    public Result getNavigation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LOGIN_INFO_KEY);
        Long userId = user.getId();
        List<FunctionNode> functionNodeList = userFunctionViewMapper.selectList(new LambdaQueryWrapper<UserFunctionView>()
                        .eq(UserFunctionView::getUserId, userId))
                .stream()
                .map(FunctionNode::new)
                .toList();
        List<FunctionNode> userFunctionTree = getUserFunctionTree(functionNodeList);
        // 返回功能树
        if (userFunctionTree == null || userFunctionTree.isEmpty()) {
            return Result.ok("No Function Found");
        }
        return Result.ok("Success");
    }

    public List<FunctionNode> getUserFunctionTree(List<FunctionNode> functionNodeList) {
        // 创建一个Map以便快速查找父节点
        Map<Long, FunctionNode> nodeMap = functionNodeList.stream()
                .collect(Collectors.toMap(FunctionNode::getId, node -> node));
        // 根节点列表
        List<FunctionNode> rootNodes = new ArrayList<>();
        // 构建树结构
        for (FunctionNode node : functionNodeList) {
            if (node.getParentId() == 0 || !nodeMap.containsKey(node.getParentId())) {
                // 如果节点没有父节点，或者父节点不在nodeMap中，则认为是根节点
                rootNodes.add(node);
            } else {
                // 否则，将其添加到父节点的children列表中
                FunctionNode parentNode = nodeMap.get(node.getParentId());
                parentNode.getChildren().add(node);
            }
        }
        // 根据order字段对根节点及其子节点进行排序
        rootNodes.sort(Comparator.comparing(FunctionNode::getOrder));
        for (FunctionNode node : functionNodeList) {
            node.getChildren().sort(Comparator.comparing(FunctionNode::getOrder));
        }
        return rootNodes;
    }

}
