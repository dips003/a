import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
	
	public static void main(String args[]) throws IOException {
		
		ServerSocket s=new ServerSocket(8088);
		System.out.print("\n waiting for connection: "+s.getLocalPort());
		Socket sc=s.accept();
		System.out.print("\n connection established: "+sc.getLocalSocketAddress());
		
		DataInputStream dis=new DataInputStream(sc.getInputStream());
		DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
		
		int p=dis.readInt();
		int q=dis.readInt();
		int r1=dis.readInt();
		
		int y=(int)Math.random()+1;
		BigInteger r2=((BigInteger.valueOf(q).pow(y)).mod(BigInteger.valueOf(p)));
		
		dos.writeInt(r2.intValue());
		
		BigInteger k=((BigInteger.valueOf(r1).pow(y)).mod(BigInteger.valueOf(p)));
		System.out.print("\n decrypted key: "+k.intValue());
		sc.close();
		
		
	}
}
