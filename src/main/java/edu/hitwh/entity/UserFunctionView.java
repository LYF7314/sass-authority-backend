package edu.hitwh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFunctionView {
    Integer userId;
    Integer tenantFunctionId;
}
