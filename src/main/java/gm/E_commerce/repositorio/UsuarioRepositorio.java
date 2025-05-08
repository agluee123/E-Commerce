package gm.E_commerce.repositorio;

import gm.E_commerce.modelo.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Integer> {
}
