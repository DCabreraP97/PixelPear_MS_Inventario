package com.pixelpear.perfulandia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> mostrarStock(){
        return productoRepository.findAll();
    }

    public Producto mostrarProductoPorId(Long idProducto){
        return productoRepository.findById(idProducto).orElse(null);
    }

    public Producto reponerStock(Long idProducto, int cantidadAReponer){
        return productoRepository.findById(idProducto)
            .map(producto ->{
            producto.setStock(producto.getStock() + cantidadAReponer);
            return productoRepository.save(producto);
        }).orElse(null);
    }

    public void agregarProducto(Producto producto){
        productoRepository.save(producto);
    }

    /*public Producto crearProducto(String nombre, double precio, Integer stock ){
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setStock(stock);
        return productoRepository.save(nuevoProducto);
    }*/

    public void eliminarProducto(Long idProducto){
        productoRepository.deleteById(idProducto);
    }
}

