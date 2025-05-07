package gm.E_commerce.servicio;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Carrito;
import gm.E_commerce.modelo.ItemCarrito;
import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.repositorio.ArticulosRepositorio;
import gm.E_commerce.repositorio.CarritoRepositorio;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Optional;

@ApplicationScope
@Transactional
@Service
public class CarritoServicio implements ICarritoServicio{

    @Inject
    private CarritoRepositorio carritoRepo;


    @Inject
    private ArticulosRepositorio articuloRepo;

    @Override
    public Carrito obtenerCarritoActivo(Usuarios usuario) {
        return carritoRepo.findByUsuarioAndEstado(usuario, "ACTIVO")
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setUsuario(usuario);
                    nuevo.setEstado("ACTIVO");
                    return carritoRepo.save(nuevo);
                });
    }

    @Override
    public void agregarArticulo(Usuarios usuario, Articulos articulo, int cantidad) {
        Carrito carrito = obtenerCarritoActivo(usuario);

        Optional<ItemCarrito> existente = carrito.getItems().stream()
                .filter(i -> i.getArticulo().getId() == articulo.getId())
                .findFirst();

        if (existente.isPresent()) {
            ItemCarrito item = existente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setArticulo(articulo);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setPrecioUnitario(articulo.getPrecio());
            nuevoItem.setCarrito(carrito);
            carrito.getItems().add(nuevoItem);
        }

        carritoRepo.save(carrito);
    }

    @Override
    public void eliminarItem(Usuarios usuario, int itemId) {
        Carrito carrito = obtenerCarritoActivo(usuario);

        // Eliminar el ítem con el ID proporcionado
        carrito.getItems().removeIf(item -> item.getId() == itemId);

        // Guardar el carrito después de eliminar el ítem
        carritoRepo.save(carrito);
    }

    @Override
    public void vaciarCarrito(Carrito carrito) {
        carrito.getItems().clear();
        carritoRepo.save(carrito);
    }


}
