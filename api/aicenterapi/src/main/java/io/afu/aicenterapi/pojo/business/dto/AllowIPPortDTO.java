package io.afu.aicenterapi.pojo.business.dto;


import lombok.Data;

@Data
public class AllowIPPortDTO {

    private String allowIp;

    private String allowPort;

    private String allowDesc;

    private Integer serverId;




}
