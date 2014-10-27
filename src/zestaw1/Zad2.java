package zestaw1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Zad2 {

	// expected half for 30 degrees for sin function
//	private final static double resultExpected = 0.5;
	
	public static void main(String[] args) {
		Zad2 z = new Zad2();
		double[] precisions = {0.0001, 0.001, 0.01, 0.1, 1};
		double[] angels = {0, 30, 45, 60, 90, 114, 140, 150, 169, 180};
		File file =  new File(".", "result1-2.csv");
		
		try(BufferedWriter writer = Files.newBufferedWriter(
				file.toPath(), Charset.defaultCharset())){

			for(int i = 0; i < precisions.length; i++){
				double mae1 = 0.;
				double mae2 = 0.;
				double mae3 = 0.;
				for(int j = 0; j < angels.length; j++){
				
					
					double arg = angels[j] * Math.PI / 180;
					double expectedValue =  resultExpected(arg);
					double result1 = z.derivative2Points(arg, precisions[i]);
					double delay1 = Math.abs(result1 - expectedValue);
					double result2 = z.derivative3Points(arg, precisions[i]);
					double delay2 = Math.abs(result2 -expectedValue);
					double result3 = z.derivative5Points(arg, precisions[i]);
					double delay3 = Math.abs(result3 - expectedValue);
					System.out.println("----- dla n = " + precisions[i] + "------");
					System.out.println("Wynik pochodnej 2-points to: " + result1 + " b³ad: " + delay1);
					System.out.println("Wynik pochodnej 3-points to: " + result2 + " b³ad: " + delay2);
					System.out.println("Wynik pochodnej 5-points to: " + result3 + " b³ad: " + delay3);
					System.out.println();
					
					 writer.append(angels[j] + ";" + precisions[i] + ";" + result1+ ";" + delay1 + ";" + result2 + ";" + delay2 + ";" + result3 + ";" + delay3);
					 writer.newLine();
					 
					 mae1 += Math.abs(delay1);
					 mae2 += Math.abs(delay2);
					 mae3 += Math.abs(delay3);
				}
				mae1 /= (double) angels.length;
				mae2 /= (double) angels.length;
				mae3 /= (double) angels.length;
				
				writer.append("precision" + ";" + precisions[i] +";" +"MAE-2-points" + ";" + mae1 + ";" + "MAE-3-points" + ";" + mae2 + ";" + "MAE-5-points" + ";" + mae3);
				writer.newLine();
				writer.newLine();
			}


		  writer.flush();
		}catch(IOException exception){
		  System.out.println("Error writing to fileS");
		}
	}

	private double derivative2Points(double x, double dx){
		return (Math.sin( x + dx) - Math.sin(x)) / dx ;
	}
	
	private double derivative3Points(double x, double dx){
		return (Math.sin( x + dx) - Math.sin(x - dx)) / (2*dx) ;
	}
	
	private double derivative5Points(double x, double dx){
		return (Math.sin( x - 2*dx) - 8*Math.sin(x - dx) + 8*Math.sin(x +dx) - Math.sin(x + 2*dx)) / (12*dx) ;
	}
	
	private static double resultExpected(double arg){
		return Math.cos(arg);
	}
}
