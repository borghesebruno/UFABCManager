package ufabcmanager;

import jade.core.Agent;

public class Turma extends Agent {
    static String DISCIPLINA = "";
    
    @Override
    public void setup() 
    {
        System.out.println("Novo agente Turma inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
    }
    
    @Override
    protected void takeDown()
    {
        
    }
}
