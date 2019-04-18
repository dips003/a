import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
		
		public static long decrypt(long phiofn ,long e) {
			double num=0.0;
			for(int i=1;i<100;i++){
				num=(double)(i*phiofn+1)/e;
				
				if(num%1==0)
					break;
			}
			return (long)num;
		}
		public static void main(String args[]) throws IOException {
			
			System.out.print("\n===============SERVER=================\n");
			/*ServerSocket s=new ServerSocket(8088);
			System.out.print("\n Waiting for connection: "+s.getLocalPort());
			Socket sc=s.accept();
			System.out.print("\n connection established: "+sc.getLocalSocketAddress());
			
			DataInputStream dis=new DataInputStream(sc.getInputStream());
			DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
			
			long phiofn=dis.readLong();
			long n=dis.readLong();
			int r1=dis.readInt();
			int e=dis.readInt();
			
			int d=(int) decrypt(phiofn, e);
			
			BigInteger r2=((BigInteger.valueOf(r1).pow(d))).mod(BigInteger.valueOf(n));
			
			System.out.print("\n decrypted message: "+r2.intValue());
			
			
			
			
			
			
			sc.close();		*/
			
			
			ServerSocket s=new ServerSocket(8088);
			System.out.print("\n Waiting for connection: "+s.getLocalPort());
			Socket sc=s.accept();
			System.out.print("\n connection established: "+sc.getLocalSocketAddress());
			
			DataInputStream dis=new DataInputStream(sc.getInputStream());
			DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
			
			
			
			long n=dis.readLong();
			long phiofn=dis.readLong();
			int e=dis.readInt();
			int ct=dis.readInt();
			
			int d=(int)decrypt(phiofn, e);
			
			BigInteger pt=((BigInteger.valueOf(ct)).pow(d)).mod(BigInteger.valueOf(n));
			
			System.out.print("\n decrypted msg: "+pt.intValue());
			sc.close();
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*ServerSocket s=new ServerSocket(8088);
			System.out.print("\n waiting for connecton: "+s.getLocalPort());
			Socket sc=s.accept();
			System.out.print("\n connection established with  "+sc.getLocalAddress());
			
			DataInputStream dis=new DataInputStream(sc.getInputStream());
			DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
			
			long phiofn=dis.readLong();
			long n=dis.readLong();
			int e=dis.readInt();
			int ct=dis.readInt();
			
			int d=(int)decrypt(phiofn,e);
			
			BigInteger pt=(BigInteger.valueOf(ct).pow(d)).mod(BigInteger.valueOf(n));
			
			System.out.print("\nplain text: "+pt.intValue());
			sc.close();*/
					
			
		}
}
