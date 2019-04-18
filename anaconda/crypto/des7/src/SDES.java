import java.io.DataInputStream;
import java.io.IOException;


public class SDES {
	
	public int K1,K2;
	//Note: below matrics will be provided in lab(PICT)..
		//For performing P10 permutation
		public static final int P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6};  
	    public static final int P10max = 10;
	    //10 to 8 bit permutation
	    public static final int P8[] = { 6, 3, 7, 4, 8, 5, 10, 9};
	    public static final int P8max = 10;
	    public static final int P4[] = { 2, 4, 3, 1};
	    public static final int P4max = 4;
	    //Performing initial permutaion
	    public static final int IP[] = { 2, 6, 3, 1, 4, 8, 5, 7};
	    public static final int IPmax = 8;
	    //performing final permuttaion(Inverse of initial permutation)
	    public static final int IPI[] = { 4, 1, 3, 5, 7, 2, 8, 6};
	    public static final int IPImax = 8;
	    //expansion/permutation operation:Used in F1 Function
	    public static final int EP[] = { 4, 1, 2, 3, 2, 3, 4, 1};
	    public static final int EPmax = 4;
	    public static final int S0[][] = {{ 1, 0, 3, 2},{ 3, 2, 1, 0},{ 0, 2, 1,
	                                                          3},{ 3, 1, 3, 2}};
	    public static final int S1[][] = {{ 0, 1, 2, 3},{ 2, 0, 1, 3},{ 3, 0, 1,
	                                                          2},{ 2, 1, 0, 3}};
	    
	    
	    public  SDES(int k) {
			k=permute(k,P10,P10max);
			int t1=((k >> 5 ) & 0x1f);
			int t2=k & 0x1f;
			
			t1=((t1 & 0xf) << 1) | ((t1 & 0x10) >> 4);
			t2=((t2 & 0xF) << 1) | ((t2 & 0x10) >> 4);
			K1=permute((t1 << 5) | t2,P8,P8max);
			
			t1=((t1 & 0x7) << 2) | ((t1 & 0x18) >> 3);
			t2=((t1 & 0x7) << 2) | ((t1 & 0x18) >> 3);
			
			K2=permute((t1 << 5) | t2,P8,P8max);
			
		}
	    
	    
	    public int permute(int x,int p[],int pmax) {
			
	    	
	    	int y=0;
	    	
	    	for(int i=0;i<p.length;i++){
	    		
	    		y=y << 1;
	    		y=y| (x >> ( pmax-p[i]))&1;
	    	}
	    	return y;
	    	
		}
	    
	    public int F(int R,int K) {
			
	    	int t=permute(R,EP,EPmax)^K;
	    	int t0= (t >> 4) & 0xF;
	    	int t1= t & 0xF;
	    	
	    	t0=S0[((t0 & 0x8) >> 2) | (t0 & 1)][(t0 >> 1) & 0x3];
	    	t1=S1[((t1 & 0x8) >> 2) | (t1 & 1)][(t1 >> 1) & 0x3];
	    	
	    	t=permute((t0 << 2)|t1,P4,P4max);
	    	
	    	return t;
		}
	    
	    public int FK(int m,int k) {
			
	    	int L= (m >> 4) & 0xF;
	    	int R= m & 0xF;
	    	
	    	return ((L ^ F(R,k)) << 4)| R;
		}
	    
	    
	    public int  SW(int m) {
			
	    	return ((m & 0xF ) << 4)| ((m >> 4) & 0xF);
		}
	    
	    public static void printdata(int x,int n) {
			
	    	int mask= 1 << (n-1);
	    	
	    	while(mask > 0){
	    		System.out.print(((mask & x)==0) ? '0' : '1');
	    		mask >>=1;
	    	}
		}
	    
	    
	    
	    public byte encrypt(int m) {
			
	    	System.out.print("\n\n encrypting process: .....\n");
	    	printdata(m, 8);
	    	
	    	m=permute(m,IP,IPmax);
	    	System.out.print("\nInitial permutation: ");
	    	printdata(m, 8);
	    	
	    	m=FK(m,K1);
	    	System.out.print("\n before swapping: ");
	    	printdata(m, 8);
	    	m=SW(m);
	    	System.out.print("\n after swapping:...");
	    	printdata(m, 8);
	    	
	    	m=FK(m,K2);
	    	
	    	System.out.print("\n before inverse permutation: ");
	    	printdata(m, 8);
	    	
	    	m=permute(m, IPI, IPImax);
	    	
	    	return (byte)m;
	    	/*
	    	 * 	
	    	System.out.print("\n encryption started......");
	    	printdata(m, 8);
	    	
	    	m=permute(m,IP,IPmax);
	    	System.out.print("\n initial permutation: ");
	    	printdata(m, 8);
	    	
	    	
	    	m=FK(m,K1);
	    	System.out.print("\n before Swapping: ");
	    	printdata(m, 8);
	    	
	    	m=SW(m);
	    	System.out.print("\n after swapping: ");
	    	printdata(m, 8);
	    	
	    	m=FK(m,K2);
	    	
	    	System.out.print("\n before Inverse permuting : ");
	    	printdata(m, 8);
	    	m=permute(m, IPI, IPImax);
	    	
	    	return (byte)m;
		
	    	 */
	    	
		}
	    
	 public byte decrypt(int m) {
			
	    	System.out.print("\n\n decrypting process: .....\n");
	    	printdata(m, 8);
	    	
	    	m=permute(m,IP,IPmax);
	    	System.out.print("\nInitial permutation: ");
	    	printdata(m, 8);
	    	
	    	m=FK(m,K2);
	    	System.out.print("\n before swapping: ");
	    	printdata(m, 8);
	    	m=SW(m);
	    	System.out.print("\n after swapping:...");
	    	printdata(m, 8);
	    	
	    	m=FK(m,K1);
	    	
	    	System.out.print("\n before inverse permutation: ");
	    	printdata(m, 8);
	    	
	    	m=permute(m, IPI, IPImax);
	    	System.out.print("\n after inverse permute:: ");
			printdata(m, 8);
	    	return (byte)m;
	    	/*
	    	 * System.out.print("\n\n\n decryption process started.....");
			printdata(m, 8);
			
			m=permute(m, IP, IPmax);
			System.out.print("\n initial permutation: ");
			printdata(m, 8);
			
			m=FK(m,K2);
			System.out.print("\n before swapping: ");
			printdata(m, 8);
			
			m=SW(m);
			System.out.print("\n after swapping: ");
			printdata(m, 8);
			m=FK(m,K1);
	    	
	    	System.out.print("\n before Inverse permuting : ");
	    	printdata(m, 8);
	    	m=permute(m, IPI, IPImax);
			
	    	System.out.print("\n after inverse permute:: ");
			printdata(m, 8);
	    	
	    	return (byte)m;
		
	    	 */
	    	
		}

	public static void main(String args[]) throws NumberFormatException, IOException {
		
		DataInputStream dis=new DataInputStream(System.in);
		System.out.print("\n enter 10 bit key: ");
		int k=Integer.parseInt(dis.readLine(),2);
		
		
		System.out.print("\n enter 8 bit message: ");
		int m=Integer.parseInt(dis.readLine(),2);
		
		SDES A=new SDES(k);
		System.out.print("\n key1: ");
		SDES.printdata(A.K1, 8);
		
		System.out.print("\n key2: ");
		SDES.printdata(A.K2, 8);
		
		System.out.print("\n encrypting message\n\n\n");
		m=A.encrypt(m);
		System.out.print("\n ==========================\n\n\n");
		
		printdata(m, 8);
		
		System.out.print("\n decrypting message\n\n\n");
		m=A.decrypt(m);
		System.out.print("\n ==========================\n\n\n");
		
		printdata(m, 8);
		
		
		
	}

}
