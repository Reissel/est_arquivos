package home.atividade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class BuscaCEP implements Comparator<Endereco> {

	public int compare(Endereco e1, Endereco e2) {
		return e1.getCep().compareTo(e2.getCep());
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Endereco> a = new ArrayList<Endereco>();
		RandomAccessFile f = new RandomAccessFile("..//Atividade01//src//home//atividade//cep_ordenado.dat", "r");
		Long tamanho = 300L;
		Long qtdRegistros = f.length() / tamanho;
		Long inicio = 0L;
		Long meio = qtdRegistros / 2;
		Long fim = qtdRegistros;

		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o CEP a ser pesquisado: ");
		String cep = sc.nextLine();

		int i = 0;
		boolean found = false;
		long lStartTime = System.nanoTime();
        while(!found)
        {
        	i++;
        	f.seek(meio*300L);
          Endereco e = new Endereco();
          e.leEndereco(f); //lê o Endereco do meio
          if(Long.parseLong(e.getCep()) == Long.parseLong(cep)) {
          	System.out.println(e.toString());
          	found = true;
          } else if(Long.parseLong(e.getCep()) < Long.parseLong(cep)) {
            	inicio = meio + 1;
            	meio = (inicio + fim)/2;
          } else if (Long.parseLong(e.getCep()) > Long.parseLong(cep)) {
            	fim = meio - 1;
            	meio = (inicio + fim)/2;
          }
          if (fim.equals(meio)) {
            	System.out.println("CEP não encontrado!");
            	break;
          }
            
        }
//		while (!found) {
//			i++;
//			Endereco e = new Endereco();
//			e.leEndereco(f);
//			if (Long.parseLong(e.getCep()) == Long.parseLong(cep)) {
//				System.out.println(e.toString());
//				found = true;
//			}
//		}
		System.out.println("Número de comparações: " + i);
		f.close();

		long lEndTime = System.nanoTime();

		long output = lEndTime - lStartTime;

		System.out.println("Tempo decorrido em milisegundos: " + output / 1000000);
	}

}
