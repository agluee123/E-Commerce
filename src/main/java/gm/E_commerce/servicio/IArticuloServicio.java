package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Articulos;

import java.util.List;

public interface IArticuloServicio {
    public List<Articulos> listarArticulos();

    public Articulos buscarArticuloPorId(Integer idCliente);

    public void guardarArticulo(Articulos articulo);

    public void eliminarArticuloPorId(int idArticulo);


    public void actualizarArticulo(Articulos articuloNuevo);

    public Articulos obtenerArticuloPorId(int articuloId);
}
