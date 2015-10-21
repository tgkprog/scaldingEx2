package academic;

import java.io.File;
import java.io.IOException;

/**Debug helper*/
public class M {
	
	public M(){		
		printEnvInfo();
	}
	
	public static void main(String[] args) {
		new M();
	}
	
	
	public static void printEnvInfo() {
		System.out.println("java home :" + System.getenv("JAVA_HOME"));
		try {
			File f = new File("./");
			System.out.println("Current dir :" + f.getCanonicalPath());
		} catch (IOException e) {
			System.out.println("Current dir : err " + e);
			e.printStackTrace();
		}
		
	}

}
