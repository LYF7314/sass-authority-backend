package edu.hitwh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TenantFunctionDTO {
    // 租户 ID
    Long tenantId;
    // 租户名称
    List<Long> functionIds;
}
