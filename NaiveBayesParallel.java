import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class NaiveBayesParallel {

	private static ArrayList<ArrayList<String>> lista;
	private static int[] attributesBelow50;
	private static int[] attributesAbove50;
	private static int klasaAbove50;
	private static int klasaBelow50;
	private static int klasaTotal;
	private static String s1 = "Zema povekje od 50iljadi";
	private static String s2 = "Zema pomalku od 50iljadi";
	
	
	
	private static void readData(){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lazar\\Desktop\\PP-proekt\\set3.csv"));
			String line;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] instanca = line.split(",");
				for(int i=0;i<9;i++){
					ArrayList<String> pom = lista.get(i);
					pom.add(instanca[i]);
					lista.set(i, pom);
				}
			}
			br.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

		ArrayList<String> klasa = lista.get(8);
		for(int i=0;i<klasa.size();i++){
			if(klasa.get(i).equals("<=50K"))
				klasaBelow50++;
			else if(klasa.get(i).equals(">50K"))
				klasaAbove50++;
		}
		klasaTotal=klasaBelow50+klasaAbove50;
	}
	private static void bayesClassify(int i, String att1, String att2, String att3,
			String att4, String att5, String att6, String att7, String att8){
		String[] attributes = {att1, att2, att3, att4, att5, att6,
				att7, att8};
			ArrayList<String> attribut = lista.get(i);
			ArrayList<String> klasa = lista.get(8);
			for(int j=0;j<attribut.size();j++){
				if(attribut.get(j).equals(attributes[i])){
					if(klasa.get(j).equals("<=50K"))
						attributesBelow50[i]++;
					else if(klasa.get(j).equals(">50K"))
						attributesAbove50[i]++;
				}
			}
	}
	public static void main(String[] args){
		Thread threads[] = new Thread[8];
		long startTime = System.currentTimeMillis();
		initiateVariables();
		readData();
		for(int i=0;i<8;i++){
			final Integer innerI = new Integer(i);
			threads[i] = new Thread(new Runnable(){
				@Override
				public void run() {
					bayesClassify(innerI,"Local-gov", "Bachelors", "Divorced", "Tech-support",
				"Unmarried", "White", "Female", "England");
				}
				
			});
		
		}
		for(int i=0;i<8;i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		float probAbove50;
		float probBelow50;
		probAbove50 = (float)klasaAbove50/klasaTotal;
		probBelow50 = (float)klasaBelow50/klasaTotal;
		for(int i=0;i<8;i++){
			probAbove50*=(float)attributesAbove50[i]/klasaAbove50;
			probBelow50*=(float)attributesBelow50[i]/klasaBelow50;
		}
		System.out.println(probAbove50 > probBelow50 ? s1 : s2);
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	private static void initiateVariables(){
		klasaAbove50=0;
		klasaBelow50=0;
		klasaTotal=0;
		
		attributesBelow50 = new int[8];
		attributesAbove50 = new int[8];
		for(int i=0;i<8;i++){
			attributesBelow50[i]=0;
			attributesAbove50[i]=0;
		}
		
		lista = new ArrayList<ArrayList<String>>();
		ArrayList<String> n1 = new ArrayList<String>();
		ArrayList<String> n2 = new ArrayList<String>();
		ArrayList<String> n3 = new ArrayList<String>();
		ArrayList<String> n4 = new ArrayList<String>();
		ArrayList<String> n5 = new ArrayList<String>();
		ArrayList<String> n6 = new ArrayList<String>();
		ArrayList<String> n7 = new ArrayList<String>();
		ArrayList<String> n8 = new ArrayList<String>();
		ArrayList<String> n9 = new ArrayList<String>();
		n1.add("");
		n2.add("");
		n3.add("");
		n4.add("");
		n5.add("");
		n6.add("");
		n7.add("");
		n8.add("");
		n9.add("");
		lista.add(n1);
		lista.add(n2);
		lista.add(n3);
		lista.add(n4);
		lista.add(n5);
		lista.add(n6);
		lista.add(n7);
		lista.add(n8);
		lista.add(n9);
	}
}
