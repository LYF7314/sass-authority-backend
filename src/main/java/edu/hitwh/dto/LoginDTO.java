package edu.hitwh.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer tenantId;
    private String username;
    private String password;
}