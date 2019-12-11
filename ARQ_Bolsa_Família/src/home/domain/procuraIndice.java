package home.domain;

import java.io.RandomAccessFile;
import java.security.MessageDigest;

public class procuraIndice {

	public static void main(String[] args) throws Exception {
        String colunas[];
        
    	long hashsize = 13905349;
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        
		RandomAccessFile f = new RandomAccessFile("files/201801_BolsaFamilia_Pagamentos.csv", "r");
		RandomAccessFile f2 = new RandomAccessFile("files/bolsa.ind", "r");
		RandomAccessFile f3 = new RandomAccessFile("files/bolsaIntersecao.ind", "rw");
		
		String linha = null;
		
		int countInter = 0;
		
		//Ler o cabecalho
		f.readLine();
		
		while((linha = f.readLine()) != null) {
			colunas = linha.split("\\;");
            ElementoIndice e = new ElementoIndice();
          	e.nis = colunas[5].substring(1,12);
          	e.ponteiro = 0L;
          	
          	//Faz o hash do nis
        	md.update(e.nis.getBytes());
        	//Transforma num array de bytes
        	byte[] hashMd5 = md.digest();
        	//Passa o array para uma String
            String hash = leBolsa2.stringHexa(hashMd5);
            //define a posicao no arquivo
            int p = (int) ((hash.hashCode() &0xfffffff)%hashsize);

            ElementoIndice registro = new ElementoIndice();
            //Lê registro
            f2.seek(p*19L);
            //Passa para um registro
            registro.leElementoIndice(f2);
            //Verifica se o registro existe no arquivo de indices
            if(registro.nis.equals(e.nis)) {
            	e.escreve(f3);
            	countInter++;
            } else if(registro.ponteiro != 0) {
		//Se nao existe mas o hash bate e o registro encontrado tem valor no ponteiro,
		//busca todos os registros tratados pela colisao externa
            	long aux = registro.ponteiro;
            	while(aux != 0) {
            		f2.seek(aux);
            		ElementoIndice colisao = new ElementoIndice();
            		colisao.leElementoIndice(f2);
            		if(colisao.nis.equals(e.nis)) {
            			e.escreve(f3);
            			countInter++;
            		}
            		aux = colisao.ponteiro;
            	}
            }
		}
		f.close();
		f2.close();
		f3.close();
		System.out.println("Total de pessoas nos dois meses: " + countInter);
	}
}
