import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
	
	public static long gcd(long phiofn,long e) {
		long temp;
		while(true){
			temp=phiofn%e;
			if(temp==0){
				return e;
			}
			phiofn=e;
			e=temp;
		}
		
		
	}
	public static long encrypt(long phiofn) {
			long e=2;
			for(e=2;e<phiofn;e++){
				if(gcd(phiofn,e)==1)
					return e;
			}
			return e;
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		
		
		System.out.print("\n===============CLIENT=================\n");
		

System.out.print("\n===============CLIENT=================\n");

Socket sc=new Socket("127.0.0.1", 8088);

Scanner s=new Scanner(System.in);
DataInputStream dis=new DataInputStream(sc.getInputStream());
DataOutputStream dos=new DataOutputStream(sc.getOutputStream());

System.out.print("\n enetr p prime: ");
long p=s.nextLong();

System.out.print("\n enetr q prime: ");
long q=s.nextLong();

long phiofn=(p-1)*(q-1);
long n=p*q;

int e=(int)encrypt(phiofn);

System.out.print("\n enter plain text: ");
long pt=s.nextLong();

BigInteger ct=((BigInteger.valueOf(pt)).pow(e)).mod(BigInteger.valueOf(n));

dos.writeLong(n);
dos.writeLong(phiofn);
dos.writeInt(e);
dos.writeInt(ct.intValue());

		
sc.close();		
		
	}
}
