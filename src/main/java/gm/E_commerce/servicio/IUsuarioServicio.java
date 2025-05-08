package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Usuarios;

import java.util.List;

public interface IUsuarioServicio {
    public List<Usuarios> Listar();

    public void guardar(Usuarios usuario);

    public void eliminarPorId(int idArticulo);

    public Usuarios obtenerUsuarioPorId(int usuarioId);

}
