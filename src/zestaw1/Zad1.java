package zestaw1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.omg.CORBA.Environment;

public class Zad1 {

	private final static double resultExpected = 115.;
	
	public static void main(String[] args){
		Zad1 im = new Zad1();
		int start = 0;
		int end = 5;
		int[] precisions = {1, 5, 10, 100, 1000, 10000, 100000, 1000000, 10000000};
		
		File file =  new File(".", "result1-1.csv");
		
		try(BufferedWriter writer = Files.newBufferedWriter(
				file.toPath(), Charset.defaultCharset())){
			for(int n : precisions){
				double result1 = im.countRectangle(start, end, n);
				double delay1 = Math.abs(result1 - resultExpected);
				double result2 = im.countTrapeze(start, end, n);
				double delay2 = Math.abs(result2 - resultExpected);
				double result3 = im.countSimpson(start, end, n);
				double delay3 = Math.abs(result3 - resultExpected);
				System.out.println("----- dla n = " +n + "------");
				System.out.println("Wynik prostok¹tów to: " + result1 + " b³ad: " + delay1);
				System.out.println("Wynik trapezow to: " + result2 + " b³ad: " + delay2);
				System.out.println("Wynik Simpsona to: " + result3 + " b³ad: " + delay3);
				System.out.println();
				
				 writer.append(n + ";" + result1+ ";" + delay1 + ";" + result2 + ";" + delay2 + ";" + result3 + ";" + delay3);
				 writer.newLine();
			}

		  writer.flush();
		}catch(IOException exception){
		  System.out.println("Error writing to file");
		}
		
	}
	
	private double countRectangle(int start, int end, int precision){
		int n = precision;
		double h =(end - start) /(double) n;
		double sum = 0;
		double xi = start;
		for (int i = 0; i < n; i++ ){
			xi = start + i * h;
			sum += getFunctionValue(xi);
		}
		return sum * h;
	}
	
	private double countTrapeze(int start, int end, int precision){
		int n = precision%2 == 0 ? precision : precision +1;
		double h =(double) (end - start) / n;
		double sum = 0;
		double xi = start;
		for (int i = 1; i < n; i++ ){
			xi = start + i * h;
			sum += getFunctionValue(xi);
		}
		double fn0 = getFunctionValue(start);
		double fnN = getFunctionValue(end);
		return (fn0/2 + sum + fnN/2 )* h;
	}
	
	private double countSimpson(int start, int end, int precision){
		int n = precision%2 == 0 ? precision : precision +1;
		double h =(double) (end - start) / n;
		double sumEven = 0.;
		double sumOdd = 0.;
		double xi = start;
		for (int i = 1; i < n; i++ ){
			xi = start + i * h;
			if ( i%2 == 0){
				sumEven += getFunctionValue(xi);
			} else {
				sumOdd += getFunctionValue(xi);
			}
		}
		double fn0 = getFunctionValue(start);
		double fnN = getFunctionValue(end);
		return (fn0 + 4 * sumOdd + 2 * sumEven + fnN )* (h/3);
	}
	
	private double getFunctionValue(double fnArg){
		return 3 * Math.pow(fnArg, 2) - 2;
	}
}

