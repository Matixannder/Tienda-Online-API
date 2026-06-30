package com.tiendaonline.repository;

import java.util.Optional;

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
    // El parser de los nombres de los metodos creados en la interface dentro de
    // aquellas que extienden JpaRepository es bastante util, ya que incluso
    // puede parsear palabras claves como "And" y "Or" para poder mapear el
    // metodo a una query que use "WHERE"
    //
    // Esta seria igual a "SELECT 1 FROM clientes WHERE correo = <correo> OR dirreccion = <direccion> OR telefono = <telefono>"
    boolean existsByCorreoOrDireccionOrTelefono(String correo, String direccion, String telefono);
    Optional<Cliente> findByCorreo(String correo);
    Optional<Cliente> findByTelefono(String telefono);
}
