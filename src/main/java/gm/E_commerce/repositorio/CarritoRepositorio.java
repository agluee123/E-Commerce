package gm.E_commerce.repositorio;

import gm.E_commerce.modelo.Carrito;
import gm.E_commerce.modelo.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepositorio extends JpaRepository<Carrito,Integer> {
    Optional<Carrito> findByUsuarioAndEstado(Usuarios usuario, String estado);
}
