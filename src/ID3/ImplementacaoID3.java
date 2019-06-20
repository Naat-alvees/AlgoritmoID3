/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ID3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author nathy
 */
public class ImplementacaoID3 {
    double atributosTreinamento[][]; 
    double atributosTeste[][];
    double classe[];
    
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
        this.entropiaTotal(classe, 3);
    }
    
    public void divideDadosContinuos(double[][] atributos){
        int numAtributos = atributos[0].length;
        int idAtributo = 0;
        for (int i = 0; i < numAtributos; i++) {
            double atributosI[] = new double[atributos.length];
            for (int j = 0; j < atributosI.length; j++) {
                atributosI[j] = atributos[j][idAtributo];
            }
            Arrays.sort(atributosI);
            
            // Fazer cortes e calcular a entropia e o ganho
        }
    }
    
    
    public void entropiaTotal(double[] classe,int qntClasse){
        int[] contaClasse = new int[qntClasse+1];
        double entropiaTotal = 0, pi = 0;
        for (int i = 1; i <= qntClasse; i++) {
            for (int j = 0; j < classe.length; j++) {
                if(classe[j] == i)
                {
                    contaClasse[i]++;
                } 
            }
            System.out.println("Cont["+i+"] = "+contaClasse[i]);
        }
        
        for (int i = 1; i < contaClasse.length; i++) {
            pi = (double) contaClasse[i]/(double) classe.length;
//            System.out.println("Cont["+i+"] = "+contaClasse[i]);
//            System.out.println(classe.length);
            System.out.println(pi);
            entropiaTotal += pi * ((-pi) * (Math.log(pi)/ Math.log(2)));
        }
        System.out.println("Entropia: "+entropiaTotal);
        
    }
}
