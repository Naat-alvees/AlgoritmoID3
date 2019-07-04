package ID3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ImplementacaoID3 {
    double atributosTreinamento[][]; 
    double atributosTeste[][];
    double classe[];
    int quantidadeClasses;
    ArrayList<double[]> vetorAtributos;
    double classificacao = -1;
    
    
    public ImplementacaoID3(String caminhoArquivoTreinamento, String caminhoArquivoTeste) throws IOException{
        vetorAtributos = new ArrayList<>();
        tratarArquivo(caminhoArquivoTreinamento, caminhoArquivoTeste);
        quantidadeClasses = contaClasses();
    }
    
    public String principal()
    {
        String resultado = "";
        No raiz = new No();
        
        raiz.setPai(null);
        raiz.setPrimeiroVetorPosicoes(vetorAtributos.get(0).length);
        raiz.setEntropia(calcularEntropiaTotal(raiz.getPosicoesClasses()));
        
        gerarArvore(raiz);
        
        double[] obs = new double[atributosTeste[0].length];
        for (int i = 0; i < atributosTeste.length; i++) {
            for (int j = 0; j < obs.length; j++) {
                obs[j] = atributosTeste[i][j];
            }
            classificacao = -1;
            
            resultado += Double.toString(classificarObservacoes(obs,raiz)) + "\n";
            
            
        }   
        return resultado;
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
    
    public int contaClasses(){
        ArrayList<Double> todasClasses = new ArrayList<>();
        for (int i = 0; i < classe.length; i++) {
            if(!todasClasses.contains(classe[i])){
                todasClasses.add(classe[i]);
            }
        }
        return todasClasses.size();
    }
    
    public void gerarArvore(No noAtual){
        if(noAtual.getEntropia() == 0){
            noAtual.setClasseNo(classe[noAtual.getPosicoesClasses().get(noAtual.getPosicoesClasses().size()-1)]);
            //System.out.println("Classe:"+noAtual.getClasseNo());
            return;
        }
                
        int melhorAtributo = escolherMelhorAtributo(noAtual.getEntropia(), noAtual.getPosicoesClasses());
        noAtual.setId(melhorAtributo);
        noAtual.setCorte(calcularCorte(noAtual.getPosicoesClasses(), noAtual.getId()));  
        
        noAtual.dividirFilhos(vetorAtributos);
        ArrayList<No> filhos = noAtual.getFilhos();
        
        for (int i = 0; i < filhos.size(); i++) {
            filhos.get(i).setEntropia(calcularEntropiaTotal(filhos.get(i).getPosicoesClasses()));
 
            gerarArvore(filhos.get(i));
            
        }
    }
    
    public double classificarObservacoes(double[] obs, No noAtual){
        
        if(noAtual.getClasseNo() != -1){
            classificacao = noAtual.getClasseNo();
            return noAtual.getClasseNo();
        }
        
        if(obs[noAtual.getId()] <= noAtual.getCorte()){
            classificarObservacoes(obs, noAtual.getFilhos().get(0));
        } else {
            classificarObservacoes(obs, noAtual.getFilhos().get(1));
        }
      
        return classificacao;
    }
    
    // Metodo Funcionando
    public double calcularEntropiaAtributos(ArrayList<Integer> posicoes, int idAtributo)
    { 
        double corte = calcularCorte(posicoes, idAtributo);
        double[] numClassesCorte1 = new double[quantidadeClasses+1];
        double[] numClassesCorte2 = new double[quantidadeClasses+1];
             
        if(posicoes.size() == 2){
            numClassesCorte1[0] = 1;
            numClassesCorte1[(int)classe[posicoes.get(0)]] = 1;
            numClassesCorte2[0] = 1;
            numClassesCorte2[(int)classe[posicoes.get(1)]] = 1;
        } else {
            for (int j = 0; j < posicoes.size(); j++)
            {
                if(vetorAtributos.get(idAtributo)[posicoes.get(j)] <= corte)
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
        }
        
        
        
        double probabilidade = 0.0, entropia1 =0.0, entropia2 = 0.0;
        
        
        for (int i = 0; i < numClassesCorte1.length-1; i++)
        {
            probabilidade = numClassesCorte1[i+1]/numClassesCorte1[0];
            if(probabilidade != 0)
            {
              entropia1 += probabilidade * (-probabilidade * (Math.log(probabilidade)/Math.log(quantidadeClasses)));  
            }
            
            probabilidade = numClassesCorte2[i+1]/numClassesCorte2[0];
            if(probabilidade != 0)
            {
               entropia2 += probabilidade * (-probabilidade * (Math.log(probabilidade)/Math.log(quantidadeClasses)));  
            }
           
        }
        
        return (entropia1 + entropia2);
        
    }
    
    //Metodo Funcionando
    public double calcularCorte(ArrayList<Integer> posicoes, int idAtributo)
    {
        double corte = 0.0;
        double[] atributosI = new double[posicoes.size()];
        for (int i = 0; i < atributosI.length; i++)
        {
            if(posicoes.get(i) == -1){
                System.out.println("Chegouaqui");
            }
            if(idAtributo == -1){
                System.out.println("Veioaqui");
            }
            if(i == -1){
                System.out.println("Veioaqui2");
            }
            atributosI[i] = vetorAtributos.get(idAtributo)[posicoes.get(i)];
        }
        Arrays.sort(atributosI);
        
        if(atributosI.length<=2){
            corte = atributosI[0];
        } else {
            corte = atributosI[atributosI.length/2];
        }
        
        
        return corte;
    }
    
    //Metod Funcionando
    public double calcularEntropiaTotal(ArrayList<Integer> posicoes)
    {
        int[] contaClasse = new int[quantidadeClasses];
        double entropiaTotal = 0, pi = 0;
        for (int i = 0; i < quantidadeClasses; i++) {
            for (int j = 0; j < posicoes.size(); j++)
            {
                if(classe[posicoes.get(j)] == i)
                {
                    contaClasse[i]++;
                } 
            }
        }
        
        for (int i = 0; i < quantidadeClasses; i++)
        {
            pi = (double)contaClasse[i]/(double)posicoes.size();
            if(pi !=0){
                entropiaTotal += (-pi * (Math.log(pi)/Math.log(quantidadeClasses)));
            }
        }
        //System.out.println("EntropiaTotal: "+entropiaTotal);
        return entropiaTotal;
    }
    
    //Metodo Funcionando
    public double calcularGanho(double entropiaTotal, double entropiaAtributo)
    {
        double ganho;
        ganho = entropiaTotal - entropiaAtributo;
        
        return ganho;
    }
    
    //Metodo Funcionando    
    public int escolherMelhorAtributo(double entropiaNo,ArrayList<Integer> posicoes)
    {
        int idMelhorAtributo = -1;
        double ganho = Double.MIN_VALUE, entropiaParcial = 0.0, ganhoParcial = 0.0;
        
        for (int i = 0; i < vetorAtributos.size(); i++)
        {
            entropiaParcial = calcularEntropiaAtributos(posicoes,i); 
            ganhoParcial = calcularGanho(entropiaNo, entropiaParcial);
            if(ganhoParcial < 0){
                ganhoParcial = ganhoParcial * (-1);
                System.out.println("Entrou AQUI");
            }
            //System.out.println("Ganho Parcial: " + ganhoParcial);
            if(ganhoParcial>ganho){
                ganho = ganhoParcial;
                idMelhorAtributo = i;
            }
                
            
        }
        
        
        return idMelhorAtributo;
    }
    
}
