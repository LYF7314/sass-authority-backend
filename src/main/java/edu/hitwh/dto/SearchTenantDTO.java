package edu.hitwh.dto;

public class SearchTenantDTO {
    private String name;
    private Integer state;

    public SearchTenantDTO(String name, Integer state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public Integer getState() {
        return state;
    }
}
