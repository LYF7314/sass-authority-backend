package edu.hitwh.dto;

import edu.hitwh.entity.Tenant;
import lombok.Data;

@Data
public class TenantDTO {
    private Long id;
    private String name;

    public TenantDTO(Tenant tenant) {
        this.id = tenant.getId();
        this.name = tenant.getName();
    }
}
