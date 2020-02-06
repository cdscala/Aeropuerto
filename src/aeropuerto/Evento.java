package aeropuerto;

public abstract class Evento {
	private int tipo;
	private float tiempo;
	private Item item;
	private Transito transito;
        
	public Transito getTransito(){
            return transito;
        }
        
        public void setTransito(Transito transito){
            this.transito = transito;
        }
        
        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
	
	
	
	public float getTiempo() {
            return tiempo;
	}
	

	public void setTiempo(float tiempo) {
            this.tiempo = tiempo;
	}
	
	public int getTipo() {
            return tipo;
	}
	
	public void setTipo(int tipo) {
            this.tipo = tipo;
	}
        
	public abstract void planificarEvento();
}
