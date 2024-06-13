package edu.hitwh.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Long tenantId;
    private String userName;
    private String password;
}
