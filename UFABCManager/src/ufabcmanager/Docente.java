package ufabcmanager;

import jade.core.Agent;
import java.util.*;

public class Docente extends Agent {
    static String DISCIPLINA = "";
    private String S1="",S2="",T1="",T2="",Q1="",Q2="",QUI1="",QUI2="",SX1="",SX2="";
    
    @Override
    public void setup() 
    {
        System.out.println("Novo agente Docente inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
    }
    
    @Override
    protected void takeDown()
    {
        
    }

    public String getS1() {
        return S1;
    }

    public void setS1(String S1) {
        this.S1 = S1;
    }

    public String getS2() {
        return S2;
    }

    public void setS2(String S2) {
        this.S2 = S2;
    }

    public String getT1() {
        return T1;
    }

    public void setT1(String T1) {
        this.T1 = T1;
    }

    public String getT2() {
        return T2;
    }

    public void setT2(String T2) {
        this.T2 = T2;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String Q1) {
        this.Q1 = Q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String Q2) {
        this.Q2 = Q2;
    }

    public String getQUI1() {
        return QUI1;
    }

    public void setQUI1(String QUI1) {
        this.QUI1 = QUI1;
    }

    public String getQUI2() {
        return QUI2;
    }

    public void setQUI2(String QUI2) {
        this.QUI2 = QUI2;
    }

    public String getSX1() {
        return SX1;
    }

    public void setSX1(String SX1) {
        this.SX1 = SX1;
    }

    public String getSX2() {
        return SX2;
    }

    public void setSX2(String SX2) {
        this.SX2 = SX2;
    }

}
