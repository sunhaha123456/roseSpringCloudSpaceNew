package com.rose.parent.data.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDto implements Serializable{
    protected String token;
    protected Long userId;

    public BaseDto() {
    }

    public BaseDto(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}