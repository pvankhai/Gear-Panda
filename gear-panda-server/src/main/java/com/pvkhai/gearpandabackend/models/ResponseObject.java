package com.pvkhai.gearpandabackend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObject {
    private String status;
    private String message;
    private Object data;

    public ResponseObject(String status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
}
