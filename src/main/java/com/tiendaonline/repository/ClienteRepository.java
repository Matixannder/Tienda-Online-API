package com.tiendaonline.repository;

import com.tiendaonline.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ¿Qué ventaja tiene extender una interfaz de Spring en lugar de escribir SQL?
// Una de las ventajas que se me ocurre es que puedo tener acceso a
// interacciones basicas con la base de datos, junto con un formato accesible de
// la misma información que estas interacciones devuelve, sin necesidad de
// escribir el SQL por mi cuenta o tener que configurar las queries dentro del
// codigo manualmente. En resumen, ofrece un repertorio de operaciones amplio
// accesible desde la aplicación inmediatamente

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existeEmail(String email);
    boolean existeNumero(String numero);
    boolean existeDireccion(String direccion);
}
