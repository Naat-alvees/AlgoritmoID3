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
        vetorAtributos = new ArrayList<>();
        tratarArquivo(caminhoArquivoTreinamento, caminhoArquivoTeste);
        quantidadeClasses = qntdClasse;
    }
    
    public void principal()
    {
        
        No raiz = new No(vetorAtributos.size());
        
        raiz.setPai(null);
        raiz.setId(escolherMelhorAtributo(calcularEntropiaTotal(),raiz.getAtributoUsados()));
        raiz.setCorte(calcularCorte(vetorAtributos.get(raiz.getId())));
        raiz.setPrimeiroVetorPosicoes(vetorAtributos.get(0).length);
        
        //gerarArvore(raiz);
        
        
  
        
        //escolherMelhorAtributo(calcularEntropiaTotal(),atributosUsados);
        //this.costruirArvore(raiz,1);
        
    }
    
    public void tratarArquivo(String caminhoArquivoTreinamento, String caminhoArquivoTeste) throws IOException
    {
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
        costruirArrayAtributos();
        
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
            }
            vetorAtributos.add(i,atributosI);
            idAtributo++;
        }
       
    }
    
    public No gerarArvore(No noAtual){
        if(verificarEstadoFinal(noAtual.getPosicoesClasses())) {
            return noAtual;
        }
        
        // Dividir filhos
                
        
        return null;
    }
    
    public boolean verificarEstadoFinal(ArrayList<Integer> posicoes){
        //Essa função pode não estar funcionando
        for (int i = 1; i < posicoes.size(); i++) {
            if(classe[posicoes.get(i-1)] != classe[posicoes.get(i)]){
                return false;
            }
        }
        return true;
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
    
    public double calcularCorte(double[] dadosAtributos)
    {
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
            entropiaTotal += (-pi * (Math.log(pi)/Math.log(quantidadeClasses)));
        }
        System.out.println("Entropia total:"+entropiaTotal);
        return entropiaTotal;
    }
    
    public double calcularGanho(double entropiaTotal, double entropiaAtributo)
    {
        double ganho;
        ganho = entropiaTotal - entropiaAtributo;
        
        return ganho;
    }
    
    public int escolherMelhorAtributo(double entropiaTotal, boolean[] atributosUsados)
    {
        int idMelhorAtributo = Integer.MAX_VALUE;
        double ganho = Double.MIN_VALUE, entropiaParcial = 0.0, ganhoParcial = 0.0;
        
        for (int i = 0; i < atributosUsados.length; i++)
        {
            if(!atributosUsados[i]){
                entropiaParcial = calcularEntropiaAtributos(vetorAtributos.get(i)); 
                ganhoParcial = calcularGanho(entropiaTotal, entropiaParcial);
                //System.out.println("Ganho Parcial: " + ganhoParcial);
                if(ganhoParcial>ganho){
                    //System.out.println("entreiii");
                    ganho = ganhoParcial;
                    idMelhorAtributo = i;
                }
                
            }
        }
        return idMelhorAtributo;
    }
    
}
