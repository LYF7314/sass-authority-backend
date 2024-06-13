package edu.hitwh.dto;

import edu.hitwh.entity.Function;
import edu.hitwh.entity.UserFunctionView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunctionNode {

    private Long id;

    private String name;

    private Long parentId;

    private Boolean isLeaf;

    private Integer order;

    private String url;

    private Long serviceId;

    private List<FunctionNode> children;

    public FunctionNode(Function function) {
        this.id = function.getId();
        this.name = function.getName();
        this.parentId = function.getParentId();
        this.isLeaf = function.getIsLeaf();
        this.order = function.getOrder();
        this.url = function.getUrl();
        this.serviceId = function.getServiceId();
        this.children = new ArrayList<FunctionNode>();
    }

    public FunctionNode(UserFunctionView userFunctionView) {
        this.id = userFunctionView.getFunctionId();
        this.name = userFunctionView.getName();
        this.parentId = userFunctionView.getParentId();
        this.isLeaf = userFunctionView.getIsLeaf();
        this.order = userFunctionView.getOrder();
        this.url = userFunctionView.getUrl();
        this.serviceId = userFunctionView.getServiceId();
        this.children = new ArrayList<FunctionNode>();
    }
}
