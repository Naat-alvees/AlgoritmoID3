package ID3;

import java.util.ArrayList;

public class No
{
    private int id;
    private No pai;
    private ArrayList<No> filhos;
    private boolean atributoUsados[]; 
    private double corte;
    private ArrayList<Integer> posicoesClasses;
    
    
    public No(int qntAtributos)
    {
        this.posicoesClasses = new ArrayList<>();
        this.atributoUsados = new boolean[qntAtributos];
        this.pai = null;
        this.filhos = new ArrayList<>();
        this.corte = 0.0;
        
    }

    @Override
    public String toString() {
        return "No{" + "id=" + id + ", pai=" + pai + ", atributoUsados=" + atributoUsados[id-1] + ", corte=" + corte + '}';
    }

    public ArrayList<Integer> getPosicoesClasses() {
        return posicoesClasses;
    }

    public void setPosicoesClasses(ArrayList<Integer> posicoesClasses) {
        this.posicoesClasses = posicoesClasses;
    }

    
    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public boolean[] getAtributoUsados() {
        return atributoUsados;
    }

    public void setAtributoUsados(int posicao) {
        this.atributoUsados[posicao] = true;
    }

    public double getCorte()
    {
        return corte;
    }

    public void setCorte(double corte)
    {
        this.corte = corte;
    }
    
    public void setPrimeiroVetorPosicoes(int numeroObs){
        for (int i = 0; i < numeroObs; i++) {
            this.posicoesClasses.add(i);
        }
    }
    
}
