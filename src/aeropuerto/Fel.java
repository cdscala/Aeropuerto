package aeropuerto;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Comparator;


public class Fel {
	private static Fel fel;
	private final LinkedList lista;
        
	private Fel(){
            lista = new LinkedList();	
	}
	
	public static Fel getFel(){
            if(fel==null) fel = new Fel();
            return(fel);
	}
	
	public void insertarFel(Evento e){
            lista.add(e);
            Collections.sort(lista, new Comparator<Evento>(){
                @Override
                public int compare(Evento t, Evento t1) {
                    if(t.getTiempo() < t1.getTiempo()) {return -1;}
                    else if(t.getTiempo() == t1.getTiempo()) {return 0;} 
                         else return 1;
                }
            });
	}

	public Evento suprimirFel(){
            Evento e = (Evento) lista.removeFirst();
            Principal.tiempoSimulacion = e.getTiempo();
            String tipoTransito ="";
            switch(e.getTipo()){
                case 0: 
                    EventoArribo arribo = (EventoArribo) e;
                    if(arribo.getTransito().getTipo() == 0) tipoTransito = "Liviano";
                    else if(arribo.getTransito().getTipo() == 1) tipoTransito = "Mediano";
                    else if(arribo.getTransito().getTipo() == 2) tipoTransito = "Pesado ";
                    System.out.println("Arribo | Transito: "+tipoTransito+"  Numero de item en pista: "+arribo.getItem().getNumero()+" Tiemmpo de arribo: "+arribo.getItem().getTiempoArribo()+"  Reloj: "+Principal.tiempoSimulacion);
                    break;
                case 1:
                    EventoSalida salida = (EventoSalida) e;
                    if(salida.getTransito().getTipo() == 0) tipoTransito = "Liviano";
                    else if(salida.getTransito().getTipo() == 1) tipoTransito = "Mediano";
                    else if(salida.getTransito().getTipo() == 2) tipoTransito = "Pesado ";
                    System.out.println("Salida | Transito: "+tipoTransito+"  Numero de item en pista: "+salida.getItem().getNumero()+" Duracion de Servicio: "+salida.getItem().getTiempoDuracionServicio()+"  Reloj: "+Principal.tiempoSimulacion);
                    break;
                case 2:
                    System.out.println("----------------------------FIN DE SIMULACION--------------------------------");
                    break;
            }
            return e;
	}
	
	public LinkedList getLista() {
		return lista;
	}
	
}