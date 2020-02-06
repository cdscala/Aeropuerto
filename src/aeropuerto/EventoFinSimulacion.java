package aeropuerto;

public class EventoFinSimulacion extends Evento {
	
	public EventoFinSimulacion(float tiempo){
            this.setTipo(2);
            this.setTiempo(tiempo);
	}
	
        @Override
        public void planificarEvento(){
            //no hace nada
        }
}