package com.pixelpear.perfulandia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixelpear.perfulandia.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findAll();
}
