package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.servicio.UsuarioServicio;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginControlador implements Serializable {

    private String email;
    private String clave;
    private Usuarios usuarioAutenticado;

    @Inject
    private UsuarioServicio usuarioServicio;

    public String login() {
        Usuarios u = usuarioServicio.buscarPorEmailYClave(email, clave);
        if (u != null) {
            usuarioAutenticado = u;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", u);

            // Redirige por rol
            if ("ADMIN".equals(u.getRol())) {
                return "adminArticulos?faces-redirect=true";
            } else {
                return "index?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales incorrectas"));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public void verificarAdmin() throws IOException {
        if (usuarioAutenticado == null || !"ADMIN".equals(usuarioAutenticado.getRol())) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
        }
    }

    public void verificarUser() throws IOException {
        if (usuarioAutenticado == null || !"USER".equals(usuarioAutenticado.getRol())) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }

    public boolean esAdmin() {
        return usuarioAutenticado != null && "ADMIN".equals(usuarioAutenticado.getRol());
    }

    public boolean esUser() {
        return usuarioAutenticado != null && "USER".equals(usuarioAutenticado.getRol());
    }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Usuarios getUsuarioAutenticado() { return usuarioAutenticado; }
}

