package zestaw1;

public class Zad1 {

	public static void main(String[] args){
		Zad1 im = new Zad1();
		int start = 0;
		int end = 5;
		int[] precisions = {1, 5, 10, 100, 1000, 10000, 100000, 1000000};
		
		for(int n : precisions){
			double result1 = im.countRectangle(start, end, n);
			double result2 = im.countTrapeze(start, end, n);
			double result3 = im.countSimpson(start, end, n);
			System.out.println("----- dla n = " +n + "------");
			System.out.println("Wynik prostok¹tów to: " + result1);
			System.out.println("Wynik trapezow to: " + result2);
			System.out.println("Wynik Simpsona to: " + result3);
			System.out.println();
		}
	}
	
	private double countRectangle(int start, int end, int precision){
		int n = precision;
		if (n == 0){
			try {
				throw new Exception("precision could not be 0, nothing");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double h =(double) (end - start) / n;
		double sum = 0;
		double xi = start;
		for (int i = 0; i < n; i++ ){
			xi = start + i * h;
			sum += getFunctionValue(xi);
		}
		return sum * h;
	}
	private double getFunctionValue(double fnArg){
		return 3 * Math.pow(fnArg, 3) - 2;
	}
	
	private double countTrapeze(int start, int end, int precision){
		int n = precision%2 == 0 ? precision : precision +1;
		if (n ==0 ){
			try {
				throw new Exception("precision could not be 0 or 1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		if (n ==0){
			try {
				throw new Exception("precision could not be 0 or 1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
}

