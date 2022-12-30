package com.tratr.service.dto;

import java.io.Serializable;

public class HashDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String hashName;

    public HashDTO() {}

    public HashDTO(String hashName) {
        this.hashName = hashName;
    }

    public String getHashName() {
        return this.hashName;
    }

    @Override
    public String toString() {
        return "HashDTO {" + "hashName" + hashName + "}";
    }
}
