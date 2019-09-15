package home.atividade;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class IntercalaMerge {
	
	public int compare(Endereco e1, Endereco e2) {
		return e1.getCep().compareTo(e2.getCep());
	}
	
	public void intercala(int qtd, String arquivo, int first_index) {
		if(qtd%2 != 0) {
			System.out.println("Numero nao e divisivel por 2");
		} else {
			try {
				
				ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
				int j = 0;
				for(int i = 0; i < qtd/2; i++) {
				
					RandomAccessFile entrada1 = new RandomAccessFile(arquivo+(first_index+i+j)+".dat", "r");
					RandomAccessFile entrada2 = new RandomAccessFile(arquivo+(first_index+i+j+1)+".dat", "r");
					
					Endereco e1 = new Endereco();
					e1.leEndereco(entrada1);
					Endereco e2 = new Endereco();
					e2.leEndereco(entrada2);
					
					
					boolean fimarquivo1=false, fimarquivo2=false;
					
					while(!fimarquivo1 && !fimarquivo2) {
	
						if(compare(e1,e2)<0)
				        {
							try {
							enderecos.add(e1);
							e1 = new Endereco();
							e1.leEndereco(entrada1);
							
							} catch (EOFException e) {
								fimarquivo1 = true;
							}
				        }
				        else {
				        	try {
				        	enderecos.add(e2);
				        	e2 = new Endereco();
				        	e2.leEndereco(entrada2);
				        	} catch (EOFException e) {
				        		fimarquivo2 = true;
							}
				        }
					}
					
				    while(!fimarquivo1)
				    {
				    	try {
				    		enderecos.add(e1);
							e1 = new Endereco();
							e1.leEndereco(entrada1);
							
				    	} catch(EOFException e) {
				    		fimarquivo1 = true;
				    	}
				    }
				    
				    while(!fimarquivo2)
				    {	
				    	try {
				    		enderecos.add(e2);
							e2 = new Endereco();
							e2.leEndereco(entrada2);
							
				    	} catch(EOFException e){
				    		fimarquivo2 = true;
						 }
				    }
				    
				    
				    entrada1.close();
				    File file1 = new File(arquivo+(first_index+i+j)+".dat");
				    file1.delete();
				    entrada2.close();
				    File file2 = new File(arquivo+(first_index+i+j+1)+".dat");
				    file2.delete();
				    
				    RandomAccessFile saida = new RandomAccessFile(arquivo+(first_index+qtd+j)+".dat", "rw");
		
			        for(Endereco e: enderecos)
			        {
			            e.escreveEndereco(saida);
			        }
			        enderecos.clear();
			        saida.close();
			        j++;
			        
				}
				
		        if(qtd/2 == 1)
		        	System.out.println("Acabou!");
		        else
		        	intercala(qtd/2,arquivo,qtd+first_index);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
