package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Carrito;
import gm.E_commerce.modelo.Usuarios;

public interface ICarritoServicio {
    Carrito obtenerCarritoActivo(Usuarios usuario);
    void agregarArticulo(Usuarios usuario, Articulos articulo, int cantidad);
    void eliminarItem(Usuarios usuario, int itemId);
    void vaciarCarrito(Carrito carrito);

}
