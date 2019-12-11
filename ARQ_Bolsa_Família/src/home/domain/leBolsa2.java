package home.domain;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

 
public class leBolsa2
{
    public static void main(String args[]) throws Exception
    {
    	long hashsize = 13905349;
        String linha;
        String colunas[];
        RandomAccessFile f = new RandomAccessFile("files/201712_BolsaFamilia_Pagamentos.csv", "r");
        
//        RandomAccessFile f2 = new RandomAccessFile("files/bolsa.ind", "rw");
//        
//        for(int i = 0; i < hashsize; i++) {
//        	ElementoIndice vazio = new ElementoIndice();
//        	vazio.nis = "00000000000";
//        	vazio.ponteiro = 0L;
//        	vazio.escreve(f2);
//        	
//        	if(i % 100000 == 0) {
//        		System.out.println("#");
//        	}
//        	
//        	if(i % 1000000 == 0) {
//        		System.out.println("$");
//        	}
//        	
//        }
//        f2.close();
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        RandomAccessFile fIndice = new RandomAccessFile("files/bolsa.ind", "rw");
        long tamanhoIndice = fIndice.length();
        //Ler cabecalho
        f.readLine();
        while ((linha = f.readLine())!= null) {
            colunas = linha.split("\\;");
            ElementoIndice e = new ElementoIndice();
          	e.nis = colunas[5].substring(1,12);
          	e.ponteiro = 0L;
          	
          	//Faz o hash do nis
        	md.update(e.nis.getBytes());
        	//Transforma num array de bytes
        	byte[] hashMd5 = md.digest();
        	//Passa o array para uma String
            String hash = stringHexa(hashMd5);
            //define a posicao no arquivo
            int p = (int) ((hash.hashCode() &0xfffffff)%hashsize);

            ElementoIndice registro = new ElementoIndice();
            //Lê registro
            fIndice.seek(p*19L);
            //Passa para um registro
            registro.leElementoIndice(fIndice);
            //Volta para a posicao anterior a leitura do registro
            fIndice.seek(p*19L);
            //Verifica se o registro esta vazio
            if(registro.nis.equals("00000000000") || registro.nis.equals(e.nis)) {
            	e.escreve(fIndice);
            } else {
            	//Salva o ponteiro do registro
            	long proxPointer = registro.ponteiro;
            	//Atualiza o ponteiro para o fim do arquivo
            	registro.ponteiro = tamanhoIndice;
            	registro.escreve(fIndice);
            	
            	fIndice.seek(tamanhoIndice);
            	e.ponteiro = proxPointer;
            	e.escreve(fIndice);
            	tamanhoIndice = fIndice.length();
            }
        }
        f.close();
        fIndice.close();
    }
    
    public static String stringHexa(byte[] bytes) {
    	   StringBuilder s = new StringBuilder();
    	   for (int i = 0; i < bytes.length; i++) {
    	       int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
    	       int parteBaixa = bytes[i] & 0xf;
    	       if (parteAlta == 0) s.append('0');
    	       s.append(Integer.toHexString(parteAlta | parteBaixa));
    	   }
    	   return s.toString();
    }
 
}
