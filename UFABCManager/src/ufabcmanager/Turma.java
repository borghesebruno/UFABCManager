package ufabcmanager;

import jade.core.Agent;

public class Turma extends Agent {
    private static String DISCIPLINA = "";
    private static String HORARIOS = "";

    
    @Override
    public void setup() 
    {
        System.out.println("Novo agente Turma inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
        HORARIOS = (String) args[1];
    }
    
    @Override
    protected void takeDown()
    {
        
    }
    
    
    public static String getDISCIPLINA() {
        return DISCIPLINA;
    }

    public String getHorarios() {
        return HORARIOS;
    }

    public void setHorarios(String Horarios) {
        this.HORARIOS = Horarios;
    }

    
}
