package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Carrito;
import gm.E_commerce.modelo.ItemCarrito;
import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.servicio.CarritoServicio;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CarritoControlador {

    @Autowired
    private CarritoServicio carritoServicio;

    @Autowired
    private LoginControlador loginControlador;

    private Carrito carrito;
    private int cantidadItems;
    private String mensaje;
    private boolean mostrarMensaje;

    public void cargarCarrito() {
        Usuarios usuario = loginControlador.getUsuarioAutenticado();
        if (usuario != null) {
            this.carrito = carritoServicio.obtenerCarritoActivo(usuario);
            this.cantidadItems = carrito != null ? carrito.getItems().size() : 0;
        }
    }


    public void incrementarCantidad(ItemCarrito item) {

        if (item.getCantidad() < item.getArticulo().getStock()) {
            item.setCantidad(item.getCantidad() + 1);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Límite de stock alcanzado",
                    "No hay suficiente stock disponible");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }



    public void decrementarCantidad(ItemCarrito item) {
        if (item.getCantidad() > 1) {
            item.setCantidad(item.getCantidad() - 1);
            // Aquí puedes agregar lógica adicional como actualizar el stock, etc.
        }
    }

    public double calcularTotalCarrito() {
        double total = 0.0;
        if (carrito != null && carrito.getItems() != null) {
            for (ItemCarrito item : carrito.getItems()) {
                total += item.getCantidad() * item.getPrecioUnitario();
            }
        }
        return total;
    }

    public void agregarAlCarrito(int articuloId) {
        try {
            Usuarios usuario = loginControlador.getUsuarioAutenticado();
            if (usuario == null) {
                this.mensaje = "Debes iniciar sesión para agregar al carrito";
                this.mostrarMensaje = true;
                return;
            }

            Articulos articulo = new Articulos();
            articulo.setId(articuloId);

            carritoServicio.agregarArticulo(usuario, articulo, 1);
            cargarCarrito();

            this.mensaje = "Artículo agregado al carrito";
            this.mostrarMensaje = true;
        } catch (Exception e) {
            this.mensaje = "Error: " + e.getMessage();
            this.mostrarMensaje = true;
        }
    }

    public void eliminarItem(int itemId) {
        try {
            Usuarios usuario = loginControlador.getUsuarioAutenticado();
            carritoServicio.eliminarItem(usuario, itemId);
            cargarCarrito();

            this.mensaje = "Artículo eliminado del carrito";
            this.mostrarMensaje = true;
        } catch (Exception e) {
            this.mensaje = "Error: " + e.getMessage();
            this.mostrarMensaje = true;
        }
    }

    public void vaciarCarrito() {
        try {
            Usuarios usuario = loginControlador.getUsuarioAutenticado();
            Carrito carrito = carritoServicio.obtenerCarritoActivo(usuario);
            carritoServicio.vaciarCarrito(carrito);
            cargarCarrito();

            this.mensaje = "Carrito vaciado";
            this.mostrarMensaje = true;
        } catch (Exception e) {
            this.mensaje = "Error: " + e.getMessage();
            this.mostrarMensaje = true;
        }
    }




    // Getters y Setters
    public Carrito getCarrito() {
        return carrito;
    }

    public int getCantidadItems() {
        return cantidadItems;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isMostrarMensaje() {
        return mostrarMensaje;
    }

    public void setMostrarMensaje(boolean mostrarMensaje) {
        this.mostrarMensaje = mostrarMensaje;
    }
}