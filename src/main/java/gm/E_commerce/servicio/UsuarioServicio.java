package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioServicio implements IUsuarioServicio{

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




}
