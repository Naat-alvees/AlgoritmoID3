package ID3;

import java.util.ArrayList;
import java.util.Arrays;

public class No
{
    private int id;
    private No pai;
    private ArrayList<No> filhos;
    private boolean atributoUsados[]; 
    private double corte;
    private ArrayList<Integer> posicoesClasses;
    private double entropia;
    private double classeNo;
    
    
    public No(int qntAtributos)
    {
        this.posicoesClasses = new ArrayList<>();
        this.atributoUsados = new boolean[qntAtributos];
        this.pai = null;
        this.filhos = new ArrayList<>();
        this.corte = 0.0;
        this.entropia = 0.0;
        this.classeNo = 0;
    }

    public No()
    {
        this.posicoesClasses = new ArrayList<>();
        this.pai = null;
        this.filhos = new ArrayList<>();
        this.corte = 0.0;
        this.entropia = 0.0;
        this.classeNo = 0;
    }
    
    @Override
    public String toString() {
        return "No{" + "id=" + id + ", pai=" + pai + ", atributoUsados=" + atributoUsados[id-1] + ", corte=" + corte + '}';
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
    
    

//    public void setPosicoesClasses(ArrayList<Integer> posicoesClasses) {
//        this.posicoesClasses = posicoesClasses;
//    }
    
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

    
    public boolean[] getAtributoUsados() {
        return atributoUsados;
    }

    public void addAtributoUsados(int posicao) {
        this.atributoUsados[posicao] = true;
    }

    public void setAtributoUsados(boolean[] atributoUsados) {
        this.atributoUsados = atributoUsados;
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
    
    public void setTodosAtributosUsadosFalse(){
        for(int i=0; i<atributoUsados.length; i++){
            atributoUsados[i] = false;
        }
    }
    
    // Retorna true caso todos atributos tenham sido usados
    public boolean verificaAtributosUsados(){
        for (int i = 0; i < atributoUsados.length; i++) {
            if(!atributoUsados[i]){
                return false;
            }
        }
        return true;
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
        
        
        filho1.setAtributoUsados(Arrays.copyOf(this.atributoUsados, atributoUsados.length));
        filho1.addAtributoUsados(this.id);
        filho2.setAtributoUsados(Arrays.copyOf(this.atributoUsados, atributoUsados.length));
        filho2.addAtributoUsados(this.id);

        if(filho1.verificaAtributosUsados()){
            filho1.setTodosAtributosUsadosFalse();
            filho1.addAtributoUsados(this.id);
        }
        
        if(filho2.verificaAtributosUsados()){
            filho2.setTodosAtributosUsadosFalse();
            filho2.addAtributoUsados(this.id);
        }
        
        this.filhos.add(filho1);
        this.filhos.add(filho2);

    }
    
}
