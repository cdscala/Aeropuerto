package aeropuerto;
import java.util.LinkedList;


public class Queue {
	private int cantidadItems;
	private LinkedList cola;
	private float tiempoEsperaCola = 0;
		
	public Queue(){
            cola = new LinkedList();
            cantidadItems = 0;
	}
	
	public float getTiempoEsperaCola() {
            return tiempoEsperaCola;
	}
        
        public void setTiempoEsperaCola(float tiempoActual, float tiempoDuracionServicio, float tiempoArribo) {
            tiempoEsperaCola += tiempoActual - (tiempoDuracionServicio + tiempoArribo);
	}
        
	public void insertarCola(Item item,int tipo){
            switch(tipo){
                case 0:
                    System.out.println("Item: "+item.getNumero()+" a cola de pista transito liviano");
                    break;
                case 1:
                    System.out.println("Item: "+item.getNumero()+" a cola de pista transito mediano");
                    break;
                case 2:
                    System.out.println("Item: "+item.getNumero()+" a cola de pista transito pesado");
                    break;
            }
            cantidadItems ++;
            cola.add(item);
	}
	
	public Item suprimirCola(){
           return (Item) cola.remove();
	}
	
	public boolean HayCola(){
            return !cola.isEmpty();
	}
        
        public int getCantidadItems(){
            return cantidadItems;
        }
        
        public LinkedList getLista(){
            return cola;
        }
}