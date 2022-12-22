package crc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
 
public class crc { static int datasize=200;static int patternsize,size,frame[],pat[],temp[],FCS[],temmp1[],c=0;
 static int FCSsize;

	public static void main(String[] args) throws IOException {
		BufferedReader reader;
		String senderFileName="test1.txt";
		String testFileName="test.txt";

		int i = 0, j, k, l;
		Scanner s = new Scanner(System.in);
		System.out.println("\n Enter Pattern size at least 2 bit: ");
		patternsize = s.nextInt();

		size = datasize + patternsize;
		frame = new int[size];
		pat = new int[size];

		temp = new int[size];
		FCS = new int[size];
		temmp1 = new int[size];
		c = 0;
		// read main frame
		Scanner data;
		data = new Scanner(new File(senderFileName));

		double sum = 0;
		for (i = 0; i < datasize; i++) {

			frame[i] = data.nextInt();
		}
		// read pattern
		while (patternsize < 2) {
			System.err.println("\n The size of Pattern should be at least 2: ");
			patternsize = s.nextInt();
		}
		
		while(pat[0]==0||pat[patternsize-1]==0) {

		System.out.println("\n Enter Pattern as binary number (the first and last bit is 1) :");
		for (i = 0; i < patternsize; i++) {
			pat[i] = s.nextInt();
		}
		if (pat[0] == 0 || pat[patternsize - 1] == 0) {
			System.err.println("\n The Pattern is not allowed please enter it again: ");
			for (i = 0; i < patternsize; i++) {
				pat[i] = s.nextInt();
			}
		}
		}
		// Append 0's
		FCSsize = patternsize - 1;

		System.out.println("\n Number of 0's to be appended:" + FCSsize);
		for (i = datasize; i < size; i++) {
			frame[i] = 0;
		}

		for (i = 0; i < size; i++) {
			temp[i] = frame[i];// data word after adding CRC (putting in temp)
		}

		System.out.println("\n number of bits in frame that sends:" + size);

		// Division
		for (i = 0; i < datasize; i++) {
			j = 0;
			k = i;
			// check whether it is divisible or not (1-1)(1-0)(0-0)
			if (temp[k] >= pat[j]) {
				for (j = 0, k = i; j < patternsize; j++, k++) {
					if ((temp[k] == 1 && pat[j] == 1) || (temp[k] == 0 && pat[j] == 0)) {
						temp[k] = 0;// the same
					} else {
						temp[k] = 1;// not same
					} // 0+0=0 1+1=0 1+0=1 0+1=1 updating the value in temp
				} 
			}
		}

		// CRC
		for (i = 0, j = datasize; i < FCSsize; i++, j++) {
			FCS[i] = temp[j];
		}

		System.out.print("CRC bits : ");
		for (i = 0; i < FCSsize; i++) {
			System.out.print(FCS[i]);
		}

		for (i = datasize, j = 0; i < size; i++, j++) {
			temmp1[i] = FCS[j];
		}

		data.close();
// *****************************************************************************************88888

		reader = new BufferedReader(new FileReader(testFileName));
		String string_size = reader.readLine();

		int size = Integer.parseInt(string_size);
		for (int a = 0; a < size; a++) {
			String line = reader.readLine();
			data = new Scanner((new File(line)));
			System.out.print("\n\n " + a + ") receve file name :" + line);
			sum += calc(data);
			data.close();
			// System.out.println("sum =="+sum);

		}
		reader.close();

		System.out.print("\n\n percentge of error discover is " + sum / size * 100 + '%');
		// System.out.println("size =="+sum);

	}

        public static int calc (Scanner data ) 
        { int rec[] = new int [size];
		for (int i = 0; i <  datasize; i++)
		{ 
			rec[i] = data.nextInt();
		}
		
		for (int i = datasize,j=0; i <  size; i++,j++)
		{ 
			rec[i] = FCS[j];
		}
		
		//Division
		for (int i = 0; i < size; i++)
		{
			temp[i]=rec[i];
		}
		int j,k;
		for (int i = 0; i < datasize; i++)
		{
			j = 0;
			k = i;
			if (temp[k] >= pat[j])
			{
				for (j = 0, k = i; j < patternsize ; j++, k++)
				{
					if ((temp[k] == 1 && pat[j] == 1) || (temp[k] == 0 && pat[j] == 0))
					{
						temp[k] = 0;
					}
					else
					{
						temp[k] = 1;
					}
				}
			}
			
		}
		int rrem[] = new int [size];
		for (int i = datasize, a = 0; i < size; i++, a++)
		{
				rrem[a] = temp[i];
		}
	
		System.out.print(" \n Reaminder: ");
		for (int i = 0; i < FCSsize; i++)
		{
			System.out.print( rrem[i]);
		}
		

		int flag = 0;
		for (int i = 0; i < FCSsize; i++)
		{
			if (rrem[i] != 0)
			{
				flag = 1;
			}
		}

		if (flag == 0)
		{
			System.out.println( "\n \n *** The recived Data is a Correct Data ***" );return 0;
		}
		else
		{
			System.err.println( "\n \n  **** There error in recived Data **** " );
                        return 1;
		}
		
		//to calculate the percentage of error
		
		
        }
        
        
        
}


   