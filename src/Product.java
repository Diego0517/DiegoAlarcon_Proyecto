import java.time.LocalDateTime;
import java.util.List;

class Product {
    int id;
    String title;
    double price;
    String description;
    List<String> tags; // O cualquier otra propiedad que tengas en tu modelo
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Category category;

    // Constructor con los parámetros necesarios
    public Product(int id, String title, double price, String description, List<String> tags,
            LocalDateTime createdAt, LocalDateTime updatedAt, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }

    // Método toString para mostrar la información del producto
    @Override
    public String toString() {
        return "Producto [ID: " + id + ", Nombre: " + title + ", Precio: " + price +
                ", Descripción: " + description + ", Categoría: " + category.name + "]";
    }
}
