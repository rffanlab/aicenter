package io.afu.aicenterapi.pojo.business.dto;


import lombok.Data;

@Data
public class TaskResultDTO {


    private Task task;

    private Data data;

    @lombok.Data
    public static class Data {

        private String type;

        private String url;

        private String text;


    }


    @lombok.Data
    public static class Task {


        private String type;

        private Integer id;


    }











}
