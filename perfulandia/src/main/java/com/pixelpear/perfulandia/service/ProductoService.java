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

}
