package ufabcmanager;

import jade.core.Agent;
import java.util.HashMap;
import java.util.Map;

class HorarioSala{
    String Docente;
    String Turma;
    HorarioSala(String Docente, String Turma){
        this.Docente = Docente;
        this.Turma = Turma;
    }
    HorarioSala(){ }
}


public class Sala extends Agent {
    volatile String Mensagem;
    volatile String Agente = "Sala";
    volatile String Type="Receber turmas";
    volatile Map<String, HorarioSala> mapaNomes = new HashMap<String, HorarioSala>(); 
    
    @Override
    public void setup() 
    {
        System.out.println("Novo agente Sala inicializado.");
        this.setMensagem("Sala");
        InicializaMap();
        addBehaviour(new RegistrarServico(this));
        addBehaviour(new ReceberProposta(this));
    }
    
    
     protected void InicializaMap(){
        HorarioSala hora= new HorarioSala("", "");
        mapaNomes.put("S1", hora);
        mapaNomes.put("S2", hora);
        mapaNomes.put("T1", hora);
        mapaNomes.put("T2", hora);
        mapaNomes.put("Q1", hora);
        mapaNomes.put("Q2", hora);
        mapaNomes.put("QUI1", hora);
        mapaNomes.put("QUI2", hora);
        mapaNomes.put("SX1", hora);
        mapaNomes.put("SX2", hora);
    }
    @Override
    protected void takeDown()
    {
        
    }

    public HorarioSala getDay(String Dia){       
        return mapaNomes.get(Dia);
    }
    
    public void setDay(String Dia, String Turma, String Docente){
        HorarioSala hora= new HorarioSala(Docente, Turma);
        mapaNomes.put(Dia, hora);
    }
    

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String Mensagem) {
        this.Mensagem = Mensagem;
    }

    public String getType() {
        return Type;
    }

    public String getAgente() {
        return Agente;
    }

   
    
}
