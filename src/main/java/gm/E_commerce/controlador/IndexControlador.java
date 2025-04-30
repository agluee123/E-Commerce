package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Categorias;
import gm.E_commerce.servicio.IArticuloServicio;
import gm.E_commerce.servicio.ICategoriasServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ViewScoped
public class IndexControlador {

    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Inject
    private IArticuloServicio articuloServicio;

    @Inject
    private ICategoriasServicio categoriaServicio;

    private List<Articulos> articulos;
    private Articulos articuloNuevo;
    private List<Categorias> categorias;
    private int categoriaSeleccionadaId;  // Nuevo campo para manejar el ID seleccionado

    @PostConstruct
    public void init() {
        try {
            cargarDatosIniciales();
            prepararNuevoArticulo();
        } catch (Exception e) {
            logger.error("Error al inicializar controlador", e);
            mostrarMensajeError("Error al cargar datos iniciales");
        }
    }

    private void cargarDatosIniciales() {
        this.articulos = articuloServicio.listarArticulos();
        this.categorias = categoriaServicio.listarTodas();
    }

    public void prepararNuevoArticulo() {
        this.articuloNuevo = new Articulos();
        if(!this.categorias.isEmpty()) {
            this.categoriaSeleccionadaId = this.categorias.get(0).getId();
        }
    }


    public void guardarArticulo() {
        try {
            // Validación de nombre primero (más común)
            if(articuloNuevo.getNombre() == null || articuloNuevo.getNombre().trim().isEmpty()) {
                mostrarMensajeError("El nombre es obligatorio");
                return;
            }

            // Validación de categoría seleccionada (0 es valor inválido)
            if(categoriaSeleccionadaId == 0) {  // Cambiado de null a 0
                mostrarMensajeError("Seleccione una categoría");
                return;
            }

            // Obtener la categoría completa desde el ID
            Categorias categoria = categoriaServicio.obtenerCategoriaPorId(categoriaSeleccionadaId);
            if(categoria == null) {
                mostrarMensajeError("Categoría no válida");
                return;
            }

            // Asignar la categoría al artículo
            articuloNuevo.setCategoria(categoria);

            if (articuloNuevo.getId() != 0) {
                // Si el ID no es 0, significa que estamos modificando un artículo existente
                articuloServicio.actualizarArticulo(articuloNuevo);  // Método para actualizar
                mostrarMensajeExito("Artículo actualizado correctamente");
            } else {
                // Si el ID es 0, significa que estamos guardando un nuevo artículo
                articuloServicio.guardarArticulo(articuloNuevo);
                mostrarMensajeExito("Artículo guardado correctamente");
            }

            // Actualizar la lista de artículos
            this.articulos = articuloServicio.listarArticulos();
            prepararNuevoArticulo();  // Prepara un nuevo artículo para añadir

        } catch (Exception e) {
            logger.error("Error al guardar artículo", e);
            mostrarMensajeError("Error al guardar el artículo");
        }
    }


    public void eliminarArticulo(int idArticulo) {
        try {
            if (idArticulo <= 0) {
                mostrarMensajeError("ID de artículo inválido");
                return;
            }

            articuloServicio.eliminarArticuloPorId(idArticulo);
            this.articulos = articuloServicio.listarArticulos(); // Actualizar lista

            mostrarMensajeExito("Artículo eliminado correctamente");

        } catch (Exception e) {
            logger.error("Error al eliminar artículo ID: " + idArticulo, e);
            mostrarMensajeError("No se pudo eliminar el artículo");
        }
    }

    // Métodos para mensajes
    private void mostrarMensajeError(String mensaje) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje));
    }

    private void mostrarMensajeExito(String mensaje) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje));
    }

    public void cargarArticuloParaEditar(int articuloId) {
        try {
            // Buscar el artículo por ID
            Articulos articulo = articuloServicio.obtenerArticuloPorId(articuloId);

            if (articulo != null) {
                // Asignar el artículo a la variable para edición
                this.articuloNuevo = articulo;
                // Asignar la categoría seleccionada
                this.categoriaSeleccionadaId = articulo.getCategoria().getId();
            } else {
                mostrarMensajeError("El artículo no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al cargar el artículo para editar", e);
            mostrarMensajeError("Error al cargar el artículo para editar.");
        }
    }

    // Getters y Setters adicionales
    public int getCategoriaSeleccionadaId() {
        return categoriaSeleccionadaId;
    }

    public void setCategoriaSeleccionadaId(int categoriaSeleccionadaId) {
        this.categoriaSeleccionadaId = categoriaSeleccionadaId;
    }

    // Resto de getters y setters existentes
    public IArticuloServicio getArticuloServicio() {
        return articuloServicio;
    }

    public void setArticuloServicio(IArticuloServicio articuloServicio) {
        this.articuloServicio = articuloServicio;
    }

    public ICategoriasServicio getCategoriaServicio() {
        return categoriaServicio;
    }

    public void setCategoriaServicio(ICategoriasServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    public List<Articulos> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulos> articulos) {
        this.articulos = articulos;
    }

    public Articulos getArticuloNuevo() {
        return articuloNuevo;
    }

    public void setArticuloNuevo(Articulos articuloNuevo) {
        this.articuloNuevo = articuloNuevo;
    }

    public List<Categorias> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categorias> categorias) {
        this.categorias = categorias;
    }
}
