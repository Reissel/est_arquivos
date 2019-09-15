package home.atividade;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExternalMerge  implements Comparator<Endereco> {

	public int compare(Endereco e1, Endereco e2) {
		return e1.getCep().compareTo(e2.getCep());
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		RandomAccessFile f = new RandomAccessFile("cep.dat", "r");
		int divisoes = 8;
		Long tamanho = 300L;
		Long qtdRegistros = f.length() / tamanho;
		int resto = (int) (qtdRegistros%divisoes);
		
		long lStartTime = System.nanoTime();
		
		for(int i = 0; i < divisoes; i++) {
			
			if(i == divisoes-1) {
				for(int j = 0; j < (qtdRegistros/divisoes)+resto; j++ ) {
					Endereco e = new Endereco();
					e.leEndereco(f);
					enderecos.add(e);
				}
			}else {
				for(int j = 0; j < (qtdRegistros/divisoes); j++ ) {
					Endereco e = new Endereco();
					e.leEndereco(f);
					enderecos.add(e);
				}
			}
			
			Collections.sort(enderecos,new ExternalMerge());
			
			RandomAccessFile f2 = new RandomAccessFile("cep_ordenado_"+i+".dat", "rw");
	        for(Endereco e: enderecos)
	        {
	            e.escreveEndereco(f2);
	        }
	        f2.close();
	        enderecos.clear();
		}

		f.close();
		
		IntercalaMerge intercala = new IntercalaMerge();
		intercala.intercala(8,"cep_ordenado_", 0);
				
		long lEndTime = System.nanoTime();

		long output = lEndTime - lStartTime;

		System.out.println("Tempo decorrido em segundos: " + output / 1000000000);
	}

}
