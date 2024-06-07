package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.FunctionNode;
import edu.hitwh.entity.Function;
import edu.hitwh.mapper.FrameFunctionMapper;
import edu.hitwh.service.IFrameFunctionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        int res = frameFunctionMapper.deleteById(id);
        if (res == 0) {
            return Result.fail("No function was deleted");
        }
        return Result.ok();
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
}
