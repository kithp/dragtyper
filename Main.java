import java.util.*;

public class Main{

	public static void main(String[] args){
		String x = "test";
		System.out.println(":" + x.substring(1) + ":");
		x = "t";
		System.out.println(":" + x.substring(1) + ":");


		
		DragTyper dt = new DragTyper();
		//dt.loadDictionary("british/brit-a-z.txt");

		//dt.test1("sdftrewasert");
	
		dt.test1("yhjkoiu");
		dt.test1("asdre");
		dt.test1("wwwertyuioiuytrfghjkijnbg");

		//dt.test2("staarrt", "sdfttrewaaseert");

		//dt.test2("start", "sdftrewasert");
		//dt.test2("start", "sdfrewasert");

	}
}
