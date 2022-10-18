package dongyang.spmis.config;

public class GetName {
	public static String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName().toString();
	}
}
