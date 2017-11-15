package ufabcmanager;

import jade.core.Agent;

public class Turma extends Agent {
    private static String DISCIPLINA = "";
    private  String []Horarios = new String[10];

    
    @Override
    public void setup() 
    {
        Horarios [0]= "S1";
        Horarios [1]= "S2";
        Horarios [2]= "T1";
        Horarios [3]= "T2";
        Horarios [4]= "Q1";
        Horarios [5]= "Q2";
        Horarios [6]= "QUI1";
        Horarios [7]= "QUI2";
        Horarios [8]= "SX1";
        Horarios [9]= "SX2";
        
        System.out.println("Novo agente Turma inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
    }
    
    @Override
    protected void takeDown()
    {
        
    }
    
    
    public static String getDISCIPLINA() {
        return DISCIPLINA;
    }

    public String[] getHorarios() {
        return Horarios;
    }

    public void setHorarios(String[] Horarios) {
        this.Horarios = Horarios;
    }

    
}
