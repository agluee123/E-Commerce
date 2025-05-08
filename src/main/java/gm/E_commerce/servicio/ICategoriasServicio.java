package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Categorias;

import java.util.List;

public interface ICategoriasServicio {
    public List<Categorias> listarTodas();

    Categorias obtenerCategoriaPorId(int id);
}
