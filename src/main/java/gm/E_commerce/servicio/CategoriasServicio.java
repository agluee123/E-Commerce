package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Categorias;
import gm.E_commerce.repositorio.CategoriasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoriasServicio implements ICategoriasServicio {

    @Autowired
    private CategoriasRepositorio categoriaRepositorio;

    @Override
    public List<Categorias> listarTodas() {
        return categoriaRepositorio.findAll();
    }

    @Override
    public Categorias obtenerCategoriaPorId(int id) {
        // Obtener todas las categorías
        List<Categorias> todasCategorias = this.listarTodas();

        // Buscar la categoría por ID
        return todasCategorias.stream()
                .filter(c -> c != null && id == c.getId()) // Comparación directa con ==
                .findFirst()
                .orElse(null);
    }
}
