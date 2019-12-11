package home.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ElementoIndice {
	    String nis;
	    long ponteiro;
	     
	    void escreve(DataOutput out) throws IOException {
	    	Charset enc = Charset.forName("ISO-8859-1");
	        out.write(this.nis.getBytes(enc));
	        out.writeLong(this.ponteiro);
	    }
	    
	    public void leElementoIndice(DataInput din) throws IOException
	    {
	        byte nis[] = new byte[11];
	        byte ponteiro[] = new byte[8];

	        din.readFully(nis);
	        din.readFully(ponteiro);
	         
	        // Define a forma como caracteres especiais estão codificados.
	        Charset enc = Charset.forName("ISO-8859-1");
	         
	        this.nis = new String(nis,enc);
	        
	        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	        buffer.put(ponteiro);
	        buffer.flip();
	        this.ponteiro = buffer.getLong();
	    }
	    
	    public int compare(ElementoIndice e1, ElementoIndice e2) {
			return e1.nis.compareTo(e2.nis);
		}
}
