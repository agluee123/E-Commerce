package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Carrito;
import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.servicio.CarritoServicio;
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