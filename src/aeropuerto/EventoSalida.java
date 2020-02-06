package aeropuerto;

public class EventoSalida extends Evento {
    
    public EventoSalida(float tiempo,Item item, Transito transito){
       this.setTipo(1);
       this.setTiempo(tiempo);
       this.setItem(item);
       this.setTransito(transito);
    }
   
    @Override
    public void planificarEvento(){
        //Comprueba cola
        if(this.getTransito().getQueue().HayCola()){    //Hay cola
            Item itemCola = this.getTransito().getQueue().suprimirCola();
            Evento salida = new EventoSalida(Principal.tiempoSimulacion+itemCola.getTiempoDuracionServicio(),itemCola, this.getTransito()); //Salida
            Fel.getFel().insertarFel(salida); //Salida a fel
            salida.planificarEvento();
        }
        else                                            //No hay cola 
            this.getTransito().getServidor().setEstado(false); //Servidor pasa a desocupado
    }
}