package com.example.encuentro2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Producto {
    private Long id;
    private String title;
    private Float precio;
    private String thumbnail;


    public Producto() {
    }

    public Producto(Long id, String title, Float precio, String thumbnail) {
        this.id = id;
        this.title = title;
        this.precio = precio;
        this.thumbnail = thumbnail;
    }
}
