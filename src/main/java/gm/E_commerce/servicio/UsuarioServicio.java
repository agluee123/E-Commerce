package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.repositorio.UsuarioRepositorio;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioServicio implements IUsuarioServicio{

    @Inject
    private EntityManager em;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuarios> Listar() {
        return this.usuarioRepositorio.findAll();
    }

    @Override
    public void guardar(Usuarios usuario) {
        this.usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarPorId(int idUsuario) {
        this.usuarioRepositorio.deleteById(idUsuario);
    }

    @Override
    public Usuarios obtenerUsuarioPorId(int usuarioId) {
        return this.usuarioRepositorio.findById(usuarioId).orElse(null);
    }

    public Usuarios buscarPorEmailYClave(String email, String clave) {
        try {
            return em.createQuery(
                            "SELECT u FROM Usuarios u WHERE u.email = :email AND u.clave = :clave", Usuarios.class)
                    .setParameter("email", email)
                    .setParameter("clave", clave)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Si no hay coincidencia
        }
    }





}
