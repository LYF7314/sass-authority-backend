package edu.hitwh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantFunctionDTO {
    Long tenantId;
    FunctionNode[] functions;

}
