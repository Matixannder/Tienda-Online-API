package com.tiendaonline.repository;

import com.tiendaonline.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Necesario que el nombre tenga
    // palabras claves como "exists", "find", entre otras. Ya que JPA usa el
    // nombre para ver el tipo de operación que tendra que aplicar sobre la base
    // de datos y que valor debe retornar
    boolean existsByNombre(String nombre);
}
