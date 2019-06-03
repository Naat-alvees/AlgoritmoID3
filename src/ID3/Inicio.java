/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ID3;

import Useful.LerArquivo;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nathy
 */
public class Inicio {
    public void tratarArquivo(String caminhoArquivo) throws IOException{
        ArrayList<String> arquivoInteiro = new ArrayList<> ();
        String linhaLida[];
        
        arquivoInteiro = LerArquivo.leitorString(caminhoArquivo);
        
//        for (int i = 0; i < arquivoInteiro.size(); i++) {
//            linhaLida = arquivoInteiro.get(i).split(",");
//            for (int j = 0; j < linhaLida.length ; j++) {
//                
//            }
//        };
    }
}
