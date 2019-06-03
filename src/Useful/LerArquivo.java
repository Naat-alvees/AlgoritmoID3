/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Useful;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nathy
 */
public class LerArquivo {
    public static ArrayList<String> leitorString (String path) throws IOException { 
        BufferedReader buffRead = new BufferedReader(new FileReader(path)); 
        ArrayList<String> texto = new ArrayList<>();
        String linha = buffRead.readLine();
        while (linha != null) { 
            texto.add(linha);
            linha = buffRead.readLine(); 
        } 
        buffRead.close(); 
        return texto;
    }     
}
