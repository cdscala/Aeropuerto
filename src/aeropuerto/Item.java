package aeropuerto;

public class Item  {
	private int numero;
	private float tiempoArribo;
	private float tiempoDuracionServicio;
	private static float tiempoTransito = 0;
	private static int cantidadItems = 0;
	
        public Item(int numero,float tiempoArribo){
            this.numero = numero;
            this.tiempoArribo = tiempoArribo;
            this.tiempoDuracionServicio = 0;
	}
        
	public static int getCantidadItems() {
            return cantidadItems;
	}
	
	public static void setCantidadItems(int cantidadItems) {
            Item.cantidadItems = cantidadItems;
	}
	
	public static float getTiempoTransito() {
            return tiempoTransito;
	}
	
	public static void setTiempoTransito(float tiempoActual, float tiempoArribo) {
            tiempoTransito += tiempoActual - tiempoArribo;
	}
	
	public int getNumero() {
            return numero;
	}

	public void setNumero(int numero) {
            this.numero = numero;
	}

	public float getTiempoArribo() {
            return tiempoArribo;
	}

	public void setTiempoArribo(float tiempoArribo) {
            this.tiempoArribo = tiempoArribo;
	}

	public float getTiempoDuracionServicio() {
            return tiempoDuracionServicio;
	}

	public void setTiempoDuracionServicio(float tiempoDuracionServicio) {
            this.tiempoDuracionServicio = tiempoDuracionServicio;
	}
}
