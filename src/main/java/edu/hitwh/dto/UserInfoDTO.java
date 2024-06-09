package edu.hitwh.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoDTO {

    private String userName;

    private String tenantName;

    List<String> roleNames;

}
