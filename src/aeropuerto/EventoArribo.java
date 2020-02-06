package aeropuerto;

public class EventoArribo extends Evento{
    
    public EventoArribo(float tiempo, Transito transito){
        this.setTipo(0);
        this.setTiempo(tiempo);
        this.setTransito(transito);
        Item.setCantidadItems(Item.getCantidadItems()+1);//cantidad total en sistema
        switch(this.getTransito().getTipo()){
            case 0:
                TransitoLiviano.setCantidad(TransitoLiviano.getCantidad()+1);//cantidad en transito liviano
                this.setItem(new Item((TransitoLiviano.getCantidad()), this.getTiempo()));
                break;
            case 1:
                TransitoMediano.setCantidad(TransitoMediano.getCantidad()+1);//cantidad en transito mediano
                this.setItem(new Item((TransitoMediano.getCantidad()), this.getTiempo()));
                break;
            case 2:
                TransitoPesado.setCantidad(TransitoPesado.getCantidad()+1);//cantidad en transito pesado
                this.setItem(new Item((TransitoPesado.getCantidad()), this.getTiempo()));
                break;
        }
    } 
      
    @Override
    public void planificarEvento(){             
        //Estado del servidor
        if(this.getTransito().getServidor().isEstado()){//Ocupado
            this.getTransito().getQueue().insertarCola(this.getItem(),this.getTransito().getTipo()); //Item a cola
        }
        else{                                           //Desocupado:
            this.getTransito().getServidor().setEstado(true); //Servidor pasa a ocupado
            this.getTransito().setTarifaTotal(); //Costo por usar pista
            this.getItem().setTiempoDuracionServicio(GeneradorTiempos.getTiempoDuracionServicio(this.getTransito().getTipo())); 
            this.getTransito().getServidor().setItem(this.getItem()); //Servidor atiende a este avión
            Evento salida = new EventoSalida(this.getTiempo()/*Principal.tiempoSimulacion*/+this.getTransito().getServidor().getItem().getTiempoDuracionServicio(),this.getTransito().getServidor().getItem(), this.getTransito()); //Salida
            Fel.getFel().insertarFel(salida); //Salida a fel
            salida.planificarEvento();
        }
        
        float tiempoEntreArribo = GeneradorTiempos.getTiempoEntreArribos(this.getTransito().getTipo()); 
        Evento arribo = new EventoArribo(this.getTiempo()/*Principal.tiempoSimulacion*/+tiempoEntreArribo, this.getTransito()); //Arribo
        Fel.getFel().insertarFel(arribo); //Arribo a fel
        
        if(arribo.getTiempo()<(this.getItem().getTiempoDuracionServicio())+(this.getTiempo())){
            this.getTransito().getServidor().setEstado(true);
        }
        else{
            this.getTransito().getServidor().setEstado(false);
        }
   }
}