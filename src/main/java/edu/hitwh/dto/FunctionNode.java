package edu.hitwh.dto;

import edu.hitwh.entity.Function;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FunctionNode {

    private Long id;

    private String name;

    private Long parentId;

    private Integer isLeaf;

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
}
