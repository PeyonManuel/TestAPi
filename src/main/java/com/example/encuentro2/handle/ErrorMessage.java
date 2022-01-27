package com.example.encuentro2.handle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

}
