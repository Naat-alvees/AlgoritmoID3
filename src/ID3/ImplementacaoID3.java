package ID3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ImplementacaoID3 {
    double atributosTreinamento[][]; 
    double atributosTeste[][];
    double classe[];
    boolean atributosUsados[];
    int quantidadeClasses;
    ArrayList<double[]> vetorAtributos;
    
    
    public ImplementacaoID3(String caminhoArquivoTreinamento, String caminhoArquivoTeste, int qntdClasse) throws IOException{
        tratarArquivo(caminhoArquivoTreinamento, caminhoArquivoTeste);
        quantidadeClasses = qntdClasse;
        vetorAtributos = new ArrayList<>();
        atributosUsados = new boolean[atributosTreinamento[0].length];
        for (int i = 0; i < atributosUsados.length; i++){
            atributosUsados[i] = false;
        }
    }
    
    public void principal()
    {
        No raiz = new No();
        
        //this.costruirArvore(raiz,1);
        costruirArrayAtributos();
    }
    
    public void tratarArquivo(String caminhoArquivoTreinamento, String caminhoArquivoTeste) throws IOException{
        ArrayList<String> arquivoInteiroTreinamento = new ArrayList<>();
        ArrayList<String> arquivoInteiroTeste = new ArrayList<>();
        String linhaLida[];
        
        arquivoInteiroTreinamento = LerArquivo.leitorString(caminhoArquivoTreinamento);
        arquivoInteiroTeste = LerArquivo.leitorString(caminhoArquivoTeste);
        
        classe = new double[arquivoInteiroTreinamento.size()];
        atributosTreinamento = new double[arquivoInteiroTreinamento.size()][];
        atributosTeste = new double[arquivoInteiroTeste.size()][];
        
        for (int i = 0; i < arquivoInteiroTreinamento.size(); i++) {
            linhaLida = arquivoInteiroTreinamento.get(i).split(",");
            atributosTreinamento[i] = new double[linhaLida.length-1];
            for (int j = 0; j < linhaLida.length; j++) {
                if(j != (linhaLida.length-1)){
                    atributosTreinamento[i][j] = Double.parseDouble(linhaLida[j]);
                } else {
                    classe[i] = Double.parseDouble(linhaLida[j]);
                }
            }
        }
        
        for (int i = 0; i < arquivoInteiroTeste.size(); i++) {
            linhaLida = arquivoInteiroTeste.get(i).split(",");
            atributosTeste[i] = new double[linhaLida.length];
            for (int j = 0; j < linhaLida.length; j++) {
                atributosTeste[i][j] = Double.parseDouble(linhaLida[j]);
            }
        }
        
        //this.divideDadosContinuos(atributosTreinamento);7
//        this.entropiaTotal(classe, 3);
    }
    
    public void costruirArrayAtributos()
    {
        int idAtributo = 0;
        double atributosI[];
        for (int i = 0; i < atributosTreinamento[idAtributo].length; i++)
        {
            atributosI = new double[atributosTreinamento.length];
            for (int j = 0; j < atributosI.length; j++) {
                atributosI[j] = atributosTreinamento[j][idAtributo];
                System.out.println(atributosI[j]+ " ");
            }
            vetorAtributos.add(i,atributosI);

            idAtributo++;
            System.out.println("");
        }
        
        for (int i = 0; i < vetorAtributos.size(); i++)
        {
            for (int j = 0; j < vetorAtributos.get(i).length; j++)
            {
                System.out.print(vetorAtributos.get(i)[j] + " ");
            }
            System.out.println("");
        }
    }
    
    public void costruirArvore(No no,double entropiaTotal)
    {
        escolherMelhorAtributo(entropiaTotal);
    }
    
    public double calcularEntropiaAtributos(double[] dadosAtributos)
    { 
  
        double corte = calcularCorte(dadosAtributos);
        double[] numClassesCorte1 = new double[quantidadeClasses+1];
        double[] numClassesCorte2 = new double[quantidadeClasses+1];
             
        for (int j = 0; j < dadosAtributos.length; j++)
        {
            if(dadosAtributos[j] <= corte)
            {
                for (int i = 1; i <= quantidadeClasses; i++)
                { 
                    if(classe[j] == i)
                    {
                       numClassesCorte1[i]++;
                    }
                }
                numClassesCorte1[0]++;    
            }
            else
            {
                for (int i = 1; i <= quantidadeClasses; i++)
                { 
                    if(classe[j] == i)
                    {
                       numClassesCorte2[i]++;
                    }
                }
                numClassesCorte2[0]++;    
            }
        }
        
        double probabilidade = 0.0, entropia1 =0.0, entropia2 = 0.0;
        
        for (int i = 0; i < numClassesCorte1.length-1; i++)
        {
            probabilidade = numClassesCorte1[i+1]/numClassesCorte1[0];
            if(probabilidade != 0)
            {
              entropia1 += probabilidade * (-probabilidade * (Math.log(probabilidade)/Math.log(2)));  
            }
            
            probabilidade = numClassesCorte2[i+1]/numClassesCorte2[0];
            if(probabilidade != 0)
            {
               entropia2 += probabilidade * (-probabilidade * (Math.log(probabilidade)/Math.log(2)));  
            }
           
        }
        return (entropia1 + entropia2);
        
    }
    
    public double calcularCorte(double[] dadosAtributos){
        double corte = 0.0;
        double[] atributosI = new double[dadosAtributos.length];
        for (int i = 0; i < dadosAtributos.length; i++)
        {
            atributosI[i] = dadosAtributos[i];
        }
        Arrays.sort(atributosI);
        corte = atributosI[atributosI.length/2];

        return corte;
    }
    
    
    
    public double calcularEntropiaTotal()
    {
        int[] contaClasse = new int[quantidadeClasses+1];
        double entropiaTotal = 0, pi = 0;
        for (int i = 1; i <= quantidadeClasses; i++) {
            for (int j = 0; j < classe.length; j++)
            {
                if(classe[j] == i)
                {
                    contaClasse[i]++;
                } 
            }
        }
        
        for (int i = 1; i <= quantidadeClasses; i++)
        {
            pi = (double)contaClasse[i]/(double)classe.length;
            entropiaTotal += (pi * (-pi * (Math.log(pi)/Math.log(2))));
        }
        
        return entropiaTotal;
    }
    
    public double calcularGanho(double entropiaTotal, double entropiaAtributo)
    {
        double ganho;
        ganho = entropiaTotal - entropiaAtributo;
        
        return ganho;
    }
    
    public int escolherMelhorAtributo(double entropiaTotal)
    {
        int idMelhorAtributo = Integer.MAX_VALUE;
        double ganho = Double.MIN_VALUE, entropiaParcial = 0.0, ganhoParcial = 0.0;
        
        for (int i = 0; i < atributosUsados.length; i++)
        {
            if(!atributosUsados[i]){
                //entropiaParcial = calcularEntropiaAtributos(selecionarColunaAtributo(i)); 
                ganhoParcial = calcularGanho(entropiaTotal, entropiaParcial);
                System.out.println("Ganho Parcial: " + ganhoParcial);
                if(ganhoParcial>ganho){
                    System.out.println("entreiii");
                    ganho = ganhoParcial;
                    idMelhorAtributo = i;
                }
                
            }
        }
        atributosUsados[idMelhorAtributo] = true;
        return idMelhorAtributo;
    }
    
}
