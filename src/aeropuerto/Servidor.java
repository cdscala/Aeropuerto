package aeropuerto;

public class Servidor {
	private Item item;
	private boolean estado;
	private float tiempoOcioso;
	private float tiempoInicioOcio;
	
	
	public Servidor(){
            item = null;
            estado = false;
            tiempoOcioso = 0;
            tiempoInicioOcio = 0;
	}
	
	public Item getItem() {
		return item;
	}

        public void setItem(Item item) {
		this.item = item;
	}

        public boolean isEstado() {
            return estado;
	}
	
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public float getTiempoOcioso() {
		return tiempoOcioso;
	}
	
	public void setTiempoOcioso(float tiempoOcioso){
            this.tiempoOcioso += tiempoOcioso - tiempoInicioOcio;
	}

	public float getTiempoInicioOcio() {
		return tiempoInicioOcio;
	}

	public void setTiempoInicioOcio(float tiempoInicioOcio) {
		this.tiempoInicioOcio = tiempoInicioOcio;
	}
}
