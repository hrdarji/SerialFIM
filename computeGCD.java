import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;



public class computeGCD {

	

	
	static BigDecimal gcd(BigDecimal a,BigDecimal b)
	{
	//	BigDecimal a = new BigDecimal(1);
		BigDecimal temp = new BigDecimal(0);
		
		while(!b.equals(new BigDecimal(0)))
		{
			temp=b;
			b=a.remainder(b);
			//b=a % b;
			a=temp;
			System.out.printf("in gcd function while loop: %2.0e \n",b);
		}
		return a;
	}
	
	static int factorize(long a)
	{
//		
//		long prime[]={2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131};
		int i=0,count=0;
//		while(a!=1)
//		{
//			double y= computeGCD.gcd(a, prime[i]);
//			if(y==prime[i])
//			{
//				count++;
//				a=a/prime[i];
//				i++;
//			}
//			else
//				i++;
//		}
		
		return count;
	}
	
	static int factorizenew(BigDecimal a)
	{
	
		FileReader fingetprime=null;
		BufferedReader bufgetprime=null;
		int count=0;
		
        System.out.print("measuring support in factorizenew");
		
		 String primefileURL="src"+File.separator+"primes-to-100k.txt";
		 try {
			fingetprime = new FileReader(new File(primefileURL));
			bufgetprime = new BufferedReader(fingetprime);
			BigDecimal i;
			while(true)
			{
				
				i = new BigDecimal(Double.parseDouble(bufgetprime.readLine()));
		
				BigDecimal b = a.remainder(i);
			//	System.out.println(a+"-"+i+"-"+b);
				
				if(b.equals(new BigDecimal(0)))
				{
			//		System.out.println("prime satisfied: "+i);	
					a = a.divide(i);
					count++;
					if(a.equals(new BigDecimal(1)))
						break;
		
				}
			}
			
			
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 catch(IOException e)
		 {
			 e.printStackTrace();
		 }
		
		
		return count;
    
	}
	
	public static void main(String[] args) 
	{
		//System.out.println(computeGCD.gcd(154, 11));
		long starttime = System.currentTimeMillis();
		
	//	long n= Long.parseLong(args[0]);
	//	long starttime = System.nanoTime();
	//	System.out.println(factorize(n));
		long endtime = System.currentTimeMillis();
		long difference = endtime-starttime;
		System.out.println(""+endtime+"--"+starttime+"="+difference/(1000000*60));
		
		starttime = System.nanoTime();
	//	System.out.println(factorizenew(n));
		System.out.println(""+endtime+"--"+starttime+"="+difference/(1000000*60));
		
		BigDecimal bd = new BigDecimal(5d);
		double i=1;
	//	while(true)
	//	{
	//		bd = bd.multiply(bd);
	//		System.out.println(bd.scaleByPowerOfTen(10));
	//	}
		
		System.out.println("gcd "+gcd(new BigDecimal(1001), new BigDecimal(210)));
		
		
		System.out.println("factorizenew: "+factorizenew(new BigDecimal(1001)));
	}

}
