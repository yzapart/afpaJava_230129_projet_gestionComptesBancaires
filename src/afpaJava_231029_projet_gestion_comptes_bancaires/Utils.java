package afpaJava_231029_projet_gestion_comptes_bancaires;
import java.lang.Math;
import java.util.regex.*;

public class Utils {

	public static int nAlea(int min, int max) {
		return min + (int) Math.floor(Math.random() * (max - min));
	}

	public static double round2dec(double n) {
		double temp = (double) (Math.rint(n * 100) / 100);
		return temp;
	}
	
	public static boolean regexOk (String exp, String pattern) {
      Pattern p = Pattern.compile(pattern);
      Matcher m = p.matcher(exp);
      return m.find();
	}
}
