package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.servicio.IUsuarioServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named // Anotación clave para que JSF lo reconozca
@ViewScoped
public class UsuarioControlador implements Serializable {

    @Inject
    private IUsuarioServicio usuarioServicio;

    private List<Usuarios> listaUsuarios;
    private Usuarios usuarioNuevo;

    @PostConstruct
    public void init() {
        try {
            cargarUsuarios();
            prepararNuevoUsuario();
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar datos iniciales");
            e.printStackTrace();
        }
    }

    private void cargarUsuarios() {
        listaUsuarios = usuarioServicio.Listar();
        // Debug: Verifica cuántos usuarios se cargaron
        System.out.println("Usuarios cargados: " + (listaUsuarios != null ? listaUsuarios.size() : "null"));
    }

    private void prepararNuevoUsuario() {
        usuarioNuevo = new Usuarios();
    }

    public void guardarUsuario() {
        try {
            usuarioServicio.guardar(usuarioNuevo);
            prepararNuevoUsuario();
            cargarUsuarios();
            mostrarMensajeExito("Usuario guardado exitosamente");
        } catch (Exception e) {
            mostrarMensajeError("Error al guardar el usuario");
            e.printStackTrace();
        }
    }

    // Métodos para mostrar mensajes
    private void mostrarMensajeExito(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje));
    }

    private void mostrarMensajeError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje));
    }

    // Getters y Setters
    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public Usuarios getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(Usuarios usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }
}

