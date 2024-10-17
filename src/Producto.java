
public class Producto {
	String nombre;
	String categoria;
	int cantidad;
	
	public Producto(String nombre, String categoria, int cantidad) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
