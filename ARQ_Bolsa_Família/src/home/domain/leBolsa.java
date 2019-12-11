package home.domain;

import java.io.*;

public class leBolsa
{
    public static void main(String args[]) throws Exception
    {
//        String linha, nis;
//        String colunas[];
//        long posicao;
//        RandomAccessFile f = new RandomAccessFile("files/201712_BolsaFamilia_Pagamentos.csv", "r");
//        f.readLine();
//        while(f.getFilePointer() < f.length())
//        {
//            posicao = f.getFilePointer();
//            linha = f.readLine();
//            colunas = linha.split("\\;");
//            nis = colunas[5];
//            System.out.println("NIS => " + nis + " esta na posicao " + posicao);
//        }
//        f.close();
        
        
        RandomAccessFile fIndice = new RandomAccessFile("files/bolsa.ind", "r");
        long tamanhoIndice = fIndice.length();
        //Ler cabecalho
        while (true) {
        	 ElementoIndice registro = new ElementoIndice();
             //Passa para um registro
        	 
             registro.leElementoIndice(fIndice);
             //Volta para a posicao anterior a leitura do registro
        	System.out.println("Aguenta aew!");
        }
    }
 
}