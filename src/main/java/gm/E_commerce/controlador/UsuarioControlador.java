package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.servicio.IUsuarioServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;

@Named // Anotación clave para que JSF lo reconozca
@ViewScoped
public class UsuarioControlador implements Serializable {

    @Inject
    private IUsuarioServicio usuarioServicio;

    private List<Usuarios> listaUsuarios;
    private Usuarios usuarioNuevo;
    private Usuarios usuarioSeleccionado; // Para edición

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

    public void eliminarUsuario(Usuarios usuario) {
        try {
            usuarioServicio.eliminarPorId(usuario.getId()); // Usando el método que implementaste
            listaUsuarios = usuarioServicio.Listar(); // Actualizar la lista

            // Mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito",
                    "Usuario eliminado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            // Mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo eliminar el usuario");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
        }
    }

    public void cargarUsuarioParaEditar(int usuarioId) {
        try {
            // Buscar el usuario por ID
            Usuarios usuario = usuarioServicio.obtenerUsuarioPorId(usuarioId);

            if (usuario != null) {
                // Asignar el usuario a la variable para edición
                this.usuarioNuevo = usuario;
                // Si necesitas cargar algún atributo específico como rol, sería aquí
                // Ejemplo: this.rolSeleccionado = usuario.getRol();
            } else {
                mostrarMensajeError("El usuario no existe.");
            }
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar el usuario para editar.");
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

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
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

