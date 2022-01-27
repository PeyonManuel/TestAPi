package com.example.encuentro2.controller;

import com.example.encuentro2.handle.MessageErrorHandle;
import com.example.encuentro2.handle.FirstApplicationException;
import com.example.encuentro2.model.Producto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(path = "/api")
public class productoController {

    Logger logger = LogManager.getLogger(MessageErrorHandle.class);

    private Producto productos[] = new Producto[0];

    @PostConstruct
    private void addProducts(){
        growSize();
        Producto newProduct = new Producto((long) productos.length - 1, "Silla", (float) 200,  "");
        productos[this.productos.length - 1] = newProduct;
        growSize();
        Producto newProduct2 = new Producto((long) productos.length - 1, "Mesa", (float) 500,  "");
        productos[this.productos.length - 1] = newProduct2;
    }

    private void growSize(){
        Producto[] temp = new Producto[this.productos.length + 1];
        for(var i = 0; i < this.productos.length; i++){
            temp[i] = this.productos[i];
        }
        this.productos = temp;
    }

    @GetMapping(path = "/productos")
    private ResponseEntity<Producto[]> getAll() throws FirstApplicationException {
        logger.info("Listando productos");
        return new ResponseEntity<>(this.productos, HttpStatus.OK);

    }
    @GetMapping(path = "/productos/{id}")
    private ResponseEntity<Producto> getById(@PathVariable Long id) throws FirstApplicationException {
            for(var i = 0; i < productos.length; i++){
                try{
                    if (id == productos[i].getId()) {
                        logger.info("Mostrando producto de id: " + i);
                        return new ResponseEntity<>(productos[i], HttpStatus.OK);
                    }
                }catch(NullPointerException e){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
        throw new FirstApplicationException("Producto no encontrado");
    }
    @PostMapping(path = "/productos")
    private ResponseEntity<Producto> create(@RequestBody @Validated Producto nuevoProducto){
        growSize();
        nuevoProducto.setId((long) this.productos.length - 1);
        productos[this.productos.length - 1] = nuevoProducto;
        logger.info("Se creo el producto exitosamente");
        return new ResponseEntity<Producto>(nuevoProducto, HttpStatus.OK);
    }

}
