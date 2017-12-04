package ufabcmanager;

import java.util.HashMap;
import java.util.Map;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.*;

class Horario{
    String Sala;
    String Turma;
    Horario(String Sala, String Turma){
        this.Sala = Sala;
        this.Turma = Turma;
    }
    Horario(){ }
}


public class Docente extends Agent {


    
    Map<String, Horario> mapaNomes = new HashMap<String, Horario>(); 
    private String DISCIPLINA = "";
    private String Type = "Lecionar Disciplinas";
    private String Agente = "Docente";
    
    @Override
    public void setup() 
    {
        InicializaMap();

        System.out.println("Novo agente Docente inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
        InicializaMap();
        register();//addBehaviour(new RegistrarServico(this));
        addBehaviour(new ReceberProposta(this));
    }
    
    
    protected void InicializaMap(){
        Horario hora= new Horario("", "");
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

    public Horario getDay(String Dia){       
        return mapaNomes.get(Dia);
    }
    
    public void setDay(String Dia, String Turma, String Sala){
        Horario hora= new Horario(Sala, Turma);
        mapaNomes.put(Dia, hora);
    }
    
    public String getMensagem(){
        return this.DISCIPLINA;
    }

    public String getType() {
        return Type;
    }

    public String getAgente() {
        return Agente;
    }
    
    public void register() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
      
        ServiceDescription sd = new ServiceDescription();
        System.out.println("Registrando Docente: " + this.getType() + " " + this.getMensagem());
        sd.setType(this.getType());
        sd.setName(this.getMensagem());
     
        dfd.addServices(sd);
      
        try 
        {
            DFService.register(this, dfd);
        } 
        catch (FIPAException e) 
        {
            e.printStackTrace();
        }
    }
}
