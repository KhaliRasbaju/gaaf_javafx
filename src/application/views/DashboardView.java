package application.views;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DashboardView {

	private boolean menuOpen = true;
	
	public Scene getScene() {
		VBox vbox = new VBox(15);
		vbox.setPadding(new Insets(20));
		vbox.setPrefWidth(200);
		vbox.setStyle("-fx-background-color: #2C3E50;");
		
		Text title = new Text("Dasboard");
		title.setFont(Font.font(20));
		title.setFill(Color.WHITE);
		
		
		Button btnProductos = new Button("Productos");
        Button btnProveedor = new Button("Proveedor");
        Button btnBodega = new Button("Bodega");
        Button btnInventario = new Button("Inventario");
        Button btnPedido = new Button("Pedido");
        Button btnReporteInventario = new Button("Reporte Inventario");
        Button btnReporteBodega = new Button("Reporte Bodega");
        
        vbox.getChildren().addAll(title, btnProductos, btnProveedor, btnBodega, btnInventario, btnPedido, btnReporteInventario, btnReporteBodega);
        
        
        StackPane content = new StackPane();
        content.setStyle("-fx-background-color: #ECF0F1;");
        content.getChildren().add(new Text("Selecciona una opcion del menu"));
        
        
        
        btnProductos.setOnAction(e -> {
            class Producto {
                private int id_producto;
                private String nombre;
                private String descripcion;
                public Producto(int id_producto, String nombre, String descripcion) {
                    this.id_producto = id_producto;
                    this.nombre = nombre;
                    this.descripcion = descripcion;
                }
                public int getId_producto() { return id_producto; }
                public String getNombre() { return nombre; }
                public String getDescripcion() { return descripcion; }
                public void setNombre(String nombre) { this.nombre = nombre; }
                public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
            }
            TableView<Producto> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<Producto, Integer> colId = new TableColumn<>("ID Producto");
            colId.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
            TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Producto, String> colDescripcion = new TableColumn<>("Descripcion");
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            table.getColumns().addAll(colId, colNombre, colDescripcion);
            ObservableList<Producto> data = FXCollections.observableArrayList(
                new Producto(1, "Producto A", "Descripcion del producto A"),
                new Producto(2, "Producto B", "Descripcion del producto B"),
                new Producto(3, "Producto C", "Descripcion del producto C")
            );
            table.setItems(data);

            Button btnAgregar = new Button("Agregar nueva entrada");
            Button btnEliminar = new Button("Eliminar entrada seleccionada");
            Button btnEditar = new Button("Editar entrada seleccionada");

            btnAgregar.setOnAction(ev -> {
                int nextId = data.size() + 1;
                data.add(new Producto(nextId, "Nuevo Producto", "Nueva descripcion"));
            });

            btnEliminar.setOnAction(ev -> {
                Producto selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    data.remove(selected);
                }
            });

            btnEditar.setOnAction(ev -> {
                Producto selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selected.setNombre(selected.getNombre() + " (Editado)");
                    selected.setDescripcion(selected.getDescripcion() + " (Editado)");
                    table.refresh();
                }
            });

            VBox productosBox = new VBox(10, table, btnAgregar, btnEliminar, btnEditar);
            content.getChildren().setAll(productosBox);
        });
        
        
        
        btnProveedor.setOnAction(e -> {
            class Proveedor {
                private String nit;
                private String nombre;
                private String direccion;
                private String telefono;
                private String correo;
                public Proveedor(String nit, String nombre, String direccion, String telefono, String correo) {
                    this.nit = nit;
                    this.nombre = nombre;
                    this.direccion = direccion;
                    this.telefono = telefono;
                    this.correo = correo;
                }
                public String getNit() { return nit; }
                public String getNombre() { return nombre; }
                public String getDireccion() { return direccion; }
                public String getTelefono() { return telefono; }
                public String getCorreo() { return correo; }
                public void setNombre(String nombre) { this.nombre = nombre; }
                public void setDireccion(String direccion) { this.direccion = direccion; }
                public void setTelefono(String telefono) { this.telefono = telefono; }
                public void setCorreo(String correo) { this.correo = correo; }
            }
            TableView<Proveedor> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<Proveedor, String> colNit = new TableColumn<>("NIT");
            colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
            TableColumn<Proveedor, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Proveedor, String> colDireccion = new TableColumn<>("Direccion");
            colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            TableColumn<Proveedor, String> colTelefono = new TableColumn<>("Telefono");
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            TableColumn<Proveedor, String> colCorreo = new TableColumn<>("Correo");
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
            table.getColumns().addAll(colNit, colNombre, colDireccion, colTelefono, colCorreo);
            ObservableList<Proveedor> data = FXCollections.observableArrayList(
                new Proveedor("80012345", "Proveedor A", "Calle 1", "3001234567", "proveedora@email.com"),
                new Proveedor("80067890", "Proveedor B", "Calle 2", "3007654321", "proveedorb@email.com")
            );
            table.setItems(data);

            Button btnAgregar = new Button("Agregar nueva entrada");
            Button btnEliminar = new Button("Eliminar entrada seleccionada");
            Button btnEditar = new Button("Editar entrada seleccionada");

            btnAgregar.setOnAction(ev -> {
                data.add(new Proveedor("Nuevo NIT", "Nuevo Proveedor", "Nueva direccion", "Nuevo telefono", "Nuevo correo"));
            });

            btnEliminar.setOnAction(ev -> {
                Proveedor selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    data.remove(selected);
                }
            });

            btnEditar.setOnAction(ev -> {
                Proveedor selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selected.setNombre(selected.getNombre() + " (Editado)");
                    selected.setDireccion(selected.getDireccion() + " (Editado)");
                    selected.setTelefono(selected.getTelefono() + " (Editado)");
                    selected.setCorreo(selected.getCorreo() + " (Editado)");
                    table.refresh();
                }
            });

            VBox proveedorBox = new VBox(10, table, btnAgregar, btnEliminar, btnEditar);
            content.getChildren().setAll(proveedorBox);
        });
        
        btnBodega.setOnAction(e -> {
            class Bodega {
                private int id_bodega;
                private String nombre;
                private String ubicacion;
                public Bodega(int id_bodega, String nombre, String ubicacion) {
                    this.id_bodega = id_bodega;
                    this.nombre = nombre;
                    this.ubicacion = ubicacion;
                }
                public int getId_bodega() { return id_bodega; }
                public String getNombre() { return nombre; }
                public String getUbicacion() { return ubicacion; }
                public void setNombre(String nombre) { this.nombre = nombre; }
                public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
            }
            TableView<Bodega> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<Bodega, Integer> colId = new TableColumn<>("ID Bodega");
            colId.setCellValueFactory(new PropertyValueFactory<>("id_bodega"));
            TableColumn<Bodega, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Bodega, String> colUbicacion = new TableColumn<>("Ubicacion");
            colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
            table.getColumns().addAll(colId, colNombre, colUbicacion);
            ObservableList<Bodega> data = FXCollections.observableArrayList(
                new Bodega(1, "Bodega Central", "Zona Industrial"),
                new Bodega(2, "Bodega Norte", "Zona Norte")
            );
            table.setItems(data);

            Button btnAgregar = new Button("Agregar nueva entrada");
            Button btnEliminar = new Button("Eliminar entrada seleccionada");
            Button btnEditar = new Button("Editar entrada seleccionada");

            btnAgregar.setOnAction(ev -> {
                int nextId = data.size() + 1;
                data.add(new Bodega(nextId, "Nueva Bodega", "Nueva ubicacion"));
            });

            btnEliminar.setOnAction(ev -> {
                Bodega selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    data.remove(selected);
                }
            });

            btnEditar.setOnAction(ev -> {
                Bodega selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selected.setNombre(selected.getNombre() + " (Editado)");
                    selected.setUbicacion(selected.getUbicacion() + " (Editado)");
                    table.refresh();
                }
            });

            VBox bodegaBox = new VBox(10, table, btnAgregar, btnEliminar, btnEditar);
            content.getChildren().setAll(bodegaBox);
        });
        
        btnInventario.setOnAction(e -> {
            class Inventario {
                private int id_inventario;
                private String fecha_actualizacion;
                private int cantidad_disponible;
                private int cantidad_reservada;
                private int id_producto;
                private int id_bodega;
                public Inventario(int id_inventario, String fecha_actualizacion, int cantidad_disponible, int cantidad_reservada, int id_producto, int id_bodega) {
                    this.id_inventario = id_inventario;
                    this.fecha_actualizacion = fecha_actualizacion;
                    this.cantidad_disponible = cantidad_disponible;
                    this.cantidad_reservada = cantidad_reservada;
                    this.id_producto = id_producto;
                    this.id_bodega = id_bodega;
                }
                public int getId_inventario() { return id_inventario; }
                public String getFecha_actualizacion() { return fecha_actualizacion; }
                public int getCantidad_disponible() { return cantidad_disponible; }
                public int getCantidad_reservada() { return cantidad_reservada; }
                public int getId_producto() { return id_producto; }
                public int getId_bodega() { return id_bodega; }
                public void setFecha_actualizacion(String fecha_actualizacion) { this.fecha_actualizacion = fecha_actualizacion; }
                public void setCantidad_disponible(int cantidad_disponible) { this.cantidad_disponible = cantidad_disponible; }
                public void setCantidad_reservada(int cantidad_reservada) { this.cantidad_reservada = cantidad_reservada; }
            }
            TableView<Inventario> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<Inventario, Integer> colId = new TableColumn<>("ID Inventario");
            colId.setCellValueFactory(new PropertyValueFactory<>("id_inventario"));
            TableColumn<Inventario, String> colFecha = new TableColumn<>("Fecha Actualizacion");
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_actualizacion"));
            TableColumn<Inventario, Integer> colDisponible = new TableColumn<>("Cantidad Disponible");
            colDisponible.setCellValueFactory(new PropertyValueFactory<>("cantidad_disponible"));
            TableColumn<Inventario, Integer> colReservada = new TableColumn<>("Cantidad Reservada");
            colReservada.setCellValueFactory(new PropertyValueFactory<>("cantidad_reservada"));
            TableColumn<Inventario, Integer> colProducto = new TableColumn<>("ID Producto");
            colProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
            TableColumn<Inventario, Integer> colBodega = new TableColumn<>("ID Bodega");
            colBodega.setCellValueFactory(new PropertyValueFactory<>("id_bodega"));
            table.getColumns().addAll(colId, colFecha, colDisponible, colReservada, colProducto, colBodega);
            ObservableList<Inventario> data = FXCollections.observableArrayList(
                new Inventario(1, "2025-09-15", 100, 20, 1, 1),
                new Inventario(2, "2025-09-14", 50, 5, 2, 2)
            );
            table.setItems(data);

            Button btnAgregar = new Button("Agregar nueva entrada");
            Button btnEliminar = new Button("Eliminar entrada seleccionada");
            Button btnEditar = new Button("Editar entrada seleccionada");

            btnAgregar.setOnAction(ev -> {
                int nextId = data.size() + 1;
                data.add(new Inventario(nextId, "Nueva fecha", 0, 0, 0, 0));
            });

            btnEliminar.setOnAction(ev -> {
                Inventario selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    data.remove(selected);
                }
            });

            btnEditar.setOnAction(ev -> {
                Inventario selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selected.setFecha_actualizacion(selected.getFecha_actualizacion() + " (Editado)");
                    selected.setCantidad_disponible(selected.getCantidad_disponible() + 1);
                    selected.setCantidad_reservada(selected.getCantidad_reservada() + 1);
                    table.refresh();
                }
            });

            VBox inventarioBox = new VBox(10, table, btnAgregar, btnEliminar, btnEditar);
            content.getChildren().setAll(inventarioBox);
        });
        
        btnPedido.setOnAction(e -> {
            class Pedido {
                private int id_pedido;
                private String fecha_pedido;
                private String fecha_entrega;
                private boolean recibido;
                private double valor;
                private String nit_proveedor;
                public Pedido(int id_pedido, String fecha_pedido, String fecha_entrega, boolean recibido, double valor, String nit_proveedor) {
                    this.id_pedido = id_pedido;
                    this.fecha_pedido = fecha_pedido;
                    this.fecha_entrega = fecha_entrega;
                    this.recibido = recibido;
                    this.valor = valor;
                    this.nit_proveedor = nit_proveedor;
                }
                public int getId_pedido() { return id_pedido; }
                public String getFecha_pedido() { return fecha_pedido; }
                public String getFecha_entrega() { return fecha_entrega; }
                public boolean isRecibido() { return recibido; }
                public double getValor() { return valor; }
                public String getNit_proveedor() { return nit_proveedor; }
                public void setFecha_pedido(String fecha_pedido) { this.fecha_pedido = fecha_pedido; }
                public void setFecha_entrega(String fecha_entrega) { this.fecha_entrega = fecha_entrega; }
                public void setRecibido(boolean recibido) { this.recibido = recibido; }
                public void setValor(double valor) { this.valor = valor; }
            }
            TableView<Pedido> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<Pedido, Integer> colId = new TableColumn<>("ID Pedido");
            colId.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));
            TableColumn<Pedido, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
            colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
            TableColumn<Pedido, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
            colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fecha_entrega"));
            TableColumn<Pedido, Boolean> colRecibido = new TableColumn<>("Recibido");
            colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
            TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor");
            colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
            TableColumn<Pedido, String> colNitProveedor = new TableColumn<>("NIT Proveedor");
            colNitProveedor.setCellValueFactory(new PropertyValueFactory<>("nit_proveedor"));
            table.getColumns().addAll(colId, colFechaPedido, colFechaEntrega, colRecibido, colValor, colNitProveedor);
            ObservableList<Pedido> data = FXCollections.observableArrayList(
                new Pedido(1, "2025-09-10", "2025-09-15", true, 150000.0, "80012345"),
                new Pedido(2, "2025-09-12", "2025-09-18", false, 200000.0, "80067890")
            );
            table.setItems(data);

            Button btnAgregar = new Button("Agregar nueva entrada");
            Button btnEliminar = new Button("Eliminar entrada seleccionada");
            Button btnEditar = new Button("Editar entrada seleccionada");

            btnAgregar.setOnAction(ev -> {
                int nextId = data.size() + 1;
                data.add(new Pedido(nextId, "Nueva fecha pedido", "Nueva fecha entrega", false, 0.0, "Nuevo NIT"));
            });

            btnEliminar.setOnAction(ev -> {
                Pedido selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    data.remove(selected);
                }
            });

            btnEditar.setOnAction(ev -> {
                Pedido selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selected.setFecha_pedido(selected.getFecha_pedido() + " (Editado)");
                    selected.setFecha_entrega(selected.getFecha_entrega() + " (Editado)");
                    selected.setRecibido(!selected.isRecibido());
                    selected.setValor(selected.getValor() + 1000.0);
                    table.refresh();
                }
            });

            VBox pedidoBox = new VBox(10, table, btnAgregar, btnEliminar, btnEditar);
            content.getChildren().setAll(pedidoBox);
        });
        
        btnReporteInventario.setOnAction(e -> {
            class ReporteInventario {
                private String fecha;
                private int cantidad_disponible;
                private int cantidad_reservada;
                private String producto;
                private String bodega;
                public ReporteInventario(String fecha, int cantidad_disponible, int cantidad_reservada, String producto, String bodega) {
                    this.fecha = fecha;
                    this.cantidad_disponible = cantidad_disponible;
                    this.cantidad_reservada = cantidad_reservada;
                    this.producto = producto;
                    this.bodega = bodega;
                }
                public String getFecha() { return fecha; }
                public int getCantidad_disponible() { return cantidad_disponible; }
                public int getCantidad_reservada() { return cantidad_reservada; }
                public String getProducto() { return producto; }
                public String getBodega() { return bodega; }
            }
            TableView<ReporteInventario> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<ReporteInventario, String> colFecha = new TableColumn<>("Fecha");
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            TableColumn<ReporteInventario, Integer> colDisponible = new TableColumn<>("Cantidad Disponible");
            colDisponible.setCellValueFactory(new PropertyValueFactory<>("cantidad_disponible"));
            TableColumn<ReporteInventario, Integer> colReservada = new TableColumn<>("Cantidad Reservada");
            colReservada.setCellValueFactory(new PropertyValueFactory<>("cantidad_reservada"));
            TableColumn<ReporteInventario, String> colProducto = new TableColumn<>("Producto");
            colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
            TableColumn<ReporteInventario, String> colBodega = new TableColumn<>("Bodega");
            colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));
            table.getColumns().addAll(colFecha, colDisponible, colReservada, colProducto, colBodega);
            ObservableList<ReporteInventario> data = FXCollections.observableArrayList(
                new ReporteInventario("2025-09-15", 100, 20, "Producto A", "Bodega Central - Zona Industrial"),
                new ReporteInventario("2025-09-14", 50, 5, "Producto B", "Bodega Norte - Zona Norte")
            );
            table.setItems(data);
            VBox reporteInventarioBox = new VBox(10, table);
            content.getChildren().setAll(reporteInventarioBox);
        });

        btnReporteBodega.setOnAction(e -> {
            class ReporteBodega {
                private String bodega;
                private int cantidad_disponible_total;
                private int cantidad_reservada_total;
                private int total;
                public ReporteBodega(String bodega, int cantidad_disponible_total, int cantidad_reservada_total, int total) {
                    this.bodega = bodega;
                    this.cantidad_disponible_total = cantidad_disponible_total;
                    this.cantidad_reservada_total = cantidad_reservada_total;
                    this.total = total;
                }
                public String getBodega() { return bodega; }
                public int getCantidad_disponible_total() { return cantidad_disponible_total; }
                public int getCantidad_reservada_total() { return cantidad_reservada_total; }
                public int getTotal() { return total; }
            }
            TableView<ReporteBodega> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            TableColumn<ReporteBodega, String> colBodega = new TableColumn<>("Bodega");
            colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));
            TableColumn<ReporteBodega, Integer> colDisponibleTotal = new TableColumn<>("Cantidad Disponible Total");
            colDisponibleTotal.setCellValueFactory(new PropertyValueFactory<>("cantidad_disponible_total"));
            TableColumn<ReporteBodega, Integer> colReservadaTotal = new TableColumn<>("Cantidad Reservada Total");
            colReservadaTotal.setCellValueFactory(new PropertyValueFactory<>("cantidad_reservada_total"));
            TableColumn<ReporteBodega, Integer> colTotal = new TableColumn<>("Total");
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            table.getColumns().addAll(colBodega, colDisponibleTotal, colReservadaTotal, colTotal);
            ObservableList<ReporteBodega> data = FXCollections.observableArrayList(
                new ReporteBodega("Bodega Central", 100, 20, 120),
                new ReporteBodega("Bodega Norte", 50, 5, 55)
            );
            table.setItems(data);
            VBox reporteBodegaBox = new VBox(10, table);
            content.getChildren().setAll(reporteBodegaBox);
        });
        
        Button toggleMenu = new Button("☰");
        
        toggleMenu.setOnAction(e -> {
        	TranslateTransition slide = new TranslateTransition(Duration.millis(300), vbox);
        	
        	if(menuOpen) {
        		slide.setToX(-200);
        		menuOpen = false;
        	}else {
        		slide.setToX(0);
        		menuOpen = true;
        	}
        	slide.play();
        	
        });
		
        
        BorderPane root = new BorderPane();
        root.setLeft(vbox);
        root.setTop(toggleMenu);
        root.setCenter(content);
        
        Scene scene = new Scene(root, 800, 600);
        return  scene;
        
	}
}