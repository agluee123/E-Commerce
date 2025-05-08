package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.repositorio.ArticulosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloServicio implements IArticuloServicio {

    @Autowired
    private ArticulosRepositorio articuloRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Articulos> listarArticulos() {
        List<Articulos> articulos = articuloRepositorio.findAll();
        return articulos;
    }

    @Override
    @Transactional(readOnly = true)
    public Articulos buscarArticuloPorId(Integer idArticulo) {
        Optional<Articulos> optionalArticulo = articuloRepositorio.findById(idArticulo);
        return optionalArticulo.orElseThrow(() ->
                new RuntimeException("Artículo no encontrado con ID: " + idArticulo));
    }

    @Override
    @Transactional
    public void guardarArticulo(Articulos articulo) {
        articuloRepositorio.save(articulo);
    }

    @Override
    @Transactional
    public void eliminarArticuloPorId(int idArticulo) {
        articuloRepositorio.deleteById(idArticulo);

    }

    @Override
    @Transactional
    public void actualizarArticulo(Articulos articuloNuevo) {
        articuloRepositorio.save(articuloNuevo);
    }

    @Override
    public Articulos obtenerArticuloPorId(int id) {
        return articuloRepositorio.findById(id).orElse(null);
    }

}