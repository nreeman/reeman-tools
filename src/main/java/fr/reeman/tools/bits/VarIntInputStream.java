package fr.reeman.tools.bits;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VarIntInputStream  implements Closeable {

	private final ByteArrayInputStream byteArrayInputStream;
	
	public VarIntInputStream(byte[] bytes) {
		this(new ByteArrayInputStream(bytes));
	}
	
    public VarIntInputStream(byte buf[], int offset, int length) {
    	byteArrayInputStream = new ByteArrayInputStream(buf, offset, length);
    }
    
	public VarIntInputStream(ByteArrayInputStream byteArrayInputStream) {
		this.byteArrayInputStream = byteArrayInputStream;
	}
	
	public VarInt read() {
        int i = byteArrayInputStream.read();
        if (i == -1) {
        	return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while (i != -1) {
            byte b = (byte) (255 & i);
            output.write(b);
            if ((b & 0x80) == 0x00) {
            	break;
            }
            
            i = byteArrayInputStream.read();
        }
        
        byte[] out = output.toByteArray();
        return out.length == 0 ? null : new VarInt(out);
	}

    public VarInt[] readAll() {
        List<VarInt> varInts = new ArrayList<>();

        VarInt varInt = read();
        while (varInt != null) {
            varInts.add(varInt);
            varInt = read();
        }

        return varInts.toArray(VarInt[]::new);
    }

	@Override
	public void close() throws IOException {
		byteArrayInputStream.close();
	}
}
