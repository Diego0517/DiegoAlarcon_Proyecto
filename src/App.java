import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final List<Product> products = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private static final String FILE_NAME = "productos.json"; // Aqui se guardan los productos, se agregan manualmente

    public static void main(String[] args) {
        // Aqui se cargan
        cargarProductosDesdeArchivo();

        App app = new App();
        int option;

        do {
            app.mostrarMenuPrincipal();
            option = app.leerOpcion();

            switch (option) {
                case 1:
                    app.mostrarMenuBusqueda();
                    break;
                case 2:
                    app.agregarProducto();
                    break;
                case 3:
                    app.mostrarMenuEliminar();
                    break;
                case 0:
                    // Los productos se guardan en el archivo antes de salir
                    app.guardarProductosEnArchivo();
                    System.out.println("Gracias por usar el catálogo. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (option != 0);
    }

    // Método para guardar los productos en un archivo JSON
    public void guardarProductosEnArchivo() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(products, writer); 
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar los productos en el archivo.");
        }
    }

    // Método para cargar los productos desde el archivo JSON
    public static void cargarProductosDesdeArchivo() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();
            List<Product> loadedProducts = gson.fromJson(reader, listType);
            if (loadedProducts != null) {
                products.addAll(loadedProducts); // Cargar los productos en la lista
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los productos desde el archivo.");
        }
    }

    public void mostrarMenuPrincipal() {
        System.out.println("*****************************************************");
        System.out.println("* Bienvenido al Catálogo de Productos.             *");
        System.out.println("* Selecciona una de las siguientes opciones:       *");
        System.out.println("* 1) Buscar Productos                              *");
        System.out.println("* 2) Agregar Producto                              *");
        System.out.println("* 3) Eliminar Producto                             *");
        System.out.println("* 0) Salir                                         *");
        System.out.println("*****************************************************");
    }

    public int leerOpcion() {
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    public void mostrarMenuBusqueda() {
        System.out.println("1) Buscar productos por:");
        System.out.println(" a) ID");
        System.out.println(" b) Categoría");
        System.out.println(" c) Nombre/Descripción");
        System.out.println(" d) Rango de precio");
        System.out.print("Seleccione un criterio: ");
        char criterio = scanner.next().toLowerCase().charAt(0);
        scanner.nextLine(); 

        switch (criterio) {
            case 'a':
                buscarProductoPorId();
                break;
            case 'b':
                buscarProductoPorCategoria();
                break;
            case 'c':
                buscarProductoPorNombreDescripcion();
                break;
            case 'd':
                buscarProductoPorRangoPrecio();
                break;
            default:
                System.out.println("Criterio no válido.");
        }
    }

    public void buscarProductoPorId() {
        System.out.print("Ingrese el ID del producto(1-12): ");
        int id = scanner.nextInt(); 
        scanner.nextLine();

        for (Product product : products) {
            if (product.id == id) { 
                mostrarInformacionProducto(product);
                return;
            }
        }
        System.out.println("Producto no encontrado...");
    }

    public void buscarProductoPorCategoria() {
        System.out.print("Ingrese la categoría: ");
        String category = scanner.nextLine();
        boolean found = false;
        for (Product product : products) {
            if (product.category != null && product.category.name != null
                    && product.category.name.equalsIgnoreCase(category)) {
                mostrarInformacionProducto(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No se encontraron productos para esta categoría.");
        }
    }

    public void buscarProductoPorNombreDescripcion() {
        System.out.print("Ingrese el nombre o descripción: ");
        String search = scanner.nextLine();
        for (Product product : products) {
            if (product.title.toLowerCase().contains(search.toLowerCase())
                    || product.description.toLowerCase().contains(search.toLowerCase())) {
                mostrarInformacionProducto(product);
            }
        }
    }

    public void buscarProductoPorRangoPrecio() {
        System.out.print("Ingrese el precio mínimo: ");
        double min = scanner.nextDouble();
        System.out.print("Ingrese el precio máximo: ");
        double max = scanner.nextDouble();
        for (Product product : products) {
            if (product.price >= min && product.price <= max) {
                mostrarInformacionProducto(product);
            }
        }
    }

    public void agregarProducto() {
        System.out.print("Ingrese el ID del producto(1-12): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el Nombre: ");

        String name = scanner.nextLine();
        System.out.print("Ingrese el Precio: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ingrese la Categoría: ");
        String categoryName = scanner.nextLine();
        System.out.print("Ingrese una Descripción: ");
        String description = scanner.nextLine();

        Category category = new Category(1, categoryName, "", LocalDateTime.now(), LocalDateTime.now());
        Product product = new Product(id, name, price, description, new ArrayList<>(), LocalDateTime.now(),
                LocalDateTime.now(), category);
        products.add(product);
        System.out.println("Producto agregado exitosamente.");
    }

    public void mostrarMenuEliminar() {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        Product toRemove = null;
        for (Product product : products) {
            if (product.id == id) {
                toRemove = product;
                break;
            }
        }
        if (toRemove != null) {
            products.remove(toRemove);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Producto no encontrado...");
        }
    }

    public void mostrarInformacionProducto(Product p) {
        System.out.println(p);
    }
}
