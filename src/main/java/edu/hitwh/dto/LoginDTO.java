package edu.hitwh.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDTO {
    private Long tenantId;
    private String userName;
    private String password;
}
