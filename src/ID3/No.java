package ID3;

import java.util.ArrayList;

public class No
{
    private No pai;
    private ArrayList<No> filhos;
    private double corte, entropia, ganho;
    
    
    public No()
    {
        this.pai = null;
        this.filhos = new ArrayList<>();
        this.corte = 0.0;
        this.entropia = 0.0;
        this.ganho = 0.0;
    }

    public double getCorte()
    {
        return corte;
    }

    public void setCorte(double corte)
    {
        this.corte = corte;
    }

    public double getEntropia()
    {
        return entropia;
    }

    public void setEntropia(double entropia)
    {
        this.entropia = entropia;
    }

    public double getGanho()
    {
        return ganho;
    }

    public void setGanho(double ganho)
    {
        this.ganho = ganho;
    }    
    
}
