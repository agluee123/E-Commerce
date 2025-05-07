package gm.E_commerce.controlador;

import gm.E_commerce.modelo.Articulos;
import gm.E_commerce.modelo.Carrito;
import gm.E_commerce.modelo.Usuarios;
import gm.E_commerce.servicio.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoControlador {

    @Autowired
    private CarritoServicio carritoServicio;

    // Ver el carrito del usuario
    @GetMapping
    public String verCarrito(Model model) {
        Usuarios usuario = new Usuarios();  // Aquí deberías obtener al usuario actual (por ejemplo, desde la sesión)
        Carrito carrito = carritoServicio.obtenerCarritoActivo(usuario);
        model.addAttribute("carrito", carrito);
        return "carrito";  // La vista JSF que mostrará el carrito
    }

    // Agregar artículo al carrito
    @PostMapping("/agregar")
    public String agregarArticulo(@RequestParam("articuloId") int articuloId, @RequestParam("cantidad") int cantidad) {
        // Aquí obtienes el artículo, en este caso lo estoy creando con el ID
        Articulos articulo = new Articulos();
        articulo.setId(articuloId);

        // Llamamos al servicio para agregar el artículo
        Usuarios usuario = new Usuarios();  // Aquí deberías obtener al usuario actual
        carritoServicio.agregarArticulo(usuario, articulo, cantidad);

        return "redirect:/carrito";  // Redirigimos para ver el carrito actualizado
    }

    // Eliminar un artículo del carrito
    @PostMapping("/eliminar")
    public String eliminarItem(@RequestParam("itemId") int itemId) {
        // Llamamos al servicio para eliminar el artículo del carrito
        Usuarios usuario = new Usuarios();  // Aquí deberías obtener al usuario actual
        carritoServicio.eliminarItem(usuario, itemId);

        return "redirect:/carrito";  // Redirigimos para ver el carrito actualizado
    }

    // Vaciar el carrito
    @PostMapping("/vaciar")
    public String vaciarCarrito() {
        Usuarios usuario = new Usuarios();  // Aquí deberías obtener al usuario actual
        Carrito carrito = carritoServicio.obtenerCarritoActivo(usuario);
        carritoServicio.vaciarCarrito(carrito);

        return "redirect:/carrito";  // Redirigimos para ver el carrito vacío
    }
}
