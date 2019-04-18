import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
	public static void main(String args[]) throws UnknownHostException, IOException {
		
		Socket sc=new Socket("127.0.0.1", 8088);
		Scanner s=new Scanner(System.in);
		DataInputStream dis=new DataInputStream(sc.getInputStream());
		DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
		
		
		System.out.print("\n enter p: ");
		int p=s.nextInt();

		
		System.out.print("\n enter q: ");
		int q=s.nextInt();
		
		int x=(int)Math.random()+1;
		BigInteger r1=((BigInteger.valueOf(q).pow(x)).mod(BigInteger.valueOf(p)));
		
		dos.writeInt(p);
		dos.writeInt(q);
		dos.writeInt(r1.intValue());
		
		
		int r2=dis.readInt();
		BigInteger k=((BigInteger.valueOf(r2).pow(x)).mod(BigInteger.valueOf(p)));
		System.out.print("\n decrypted key: "+k.intValue());
		sc.close();
		
	}
}
