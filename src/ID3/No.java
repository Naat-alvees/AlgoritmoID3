package ID3;

import java.util.ArrayList;

public class No
{
    private int id;
    private No pai;
    private ArrayList<No> filhos;
    private double corte;
    private ArrayList<Integer> posicoesClasses;
    private double entropia;
    private double classeNo;
    
    
    public No()
    {
        this.posicoesClasses = new ArrayList<>();
        this.pai = null;
        this.filhos = new ArrayList<>();
        this.corte = 0.0;
        this.entropia = 0.0;
        this.classeNo = -1;
    }

    
    @Override
    public String toString() {
        return "No{" + "id=" + id + ", pai=" + pai + ", corte=" + corte + '}';
    }

    public double getEntropia() {
        return entropia;
    }

    public void setEntropia(double entropia) {
        this.entropia = entropia;
    }

    public double getClasseNo() {
        return classeNo;
    }

    public void setClasseNo(double classeNo) {
        this.classeNo = classeNo;
    }
    
    

    public ArrayList<Integer> getPosicoesClasses() {
        return posicoesClasses;
    }

    public ArrayList<No> getFilhos() {
        return filhos;
    }
    
    
    public void addPosicoesClasses(int posicao) {
        this.posicoesClasses.add(posicao);
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
    
    public void dividirFilhos(ArrayList<double[]> vetorAtributos){
        No filho1 = new No(), filho2 = new No();
        
        if(posicoesClasses.size() == 2){
            filho1.addPosicoesClasses(posicoesClasses.get(0));
            filho2.addPosicoesClasses(posicoesClasses.get(1));
        } else {
            for (int i = 0; i < posicoesClasses.size(); i++) {
                if(vetorAtributos.get(id)[posicoesClasses.get(i)]  <= corte){
                    filho1.addPosicoesClasses(posicoesClasses.get(i));
                } else {
                    filho2.addPosicoesClasses(posicoesClasses.get(i));
                }
            }
        }
        
        filho1.setPai(this);
        filho2.setPai(this);
        
        this.filhos.add(filho1);
        this.filhos.add(filho2);

    }
    
}
