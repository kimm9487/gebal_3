package gebal.ver3;



public class UserData /*implements Comparable<UserData>*/{
	 private static String id;
	
	 private static String userRate;
	
	 private static double rate;
	 
	 private static int rateInt;

	public UserData(String id, double rate) {

		UserData.id = id;
		UserData.rate = rate;
	}
	
 
	public static int getRateInt() {
		return rateInt;
	}


	public static void setRateInt(int rateInt) {
		UserData.rateInt = rateInt;
	}


	public static String getId() {
		return id;
	}

	public static void setId(String setId) {
		UserData.id = setId;
	}

	public static double getRate() {
		return rate;
	}

	public static void setRate(double setRate) {
		UserData.rate = setRate;
	}


	public static String getUserRate() {
		return userRate;
	}


	public static void setUserRate(String userRate) {
		UserData.userRate = userRate;
	}
	
	//@SuppressWarnings("static-access")
	//@Override
	/*public  int compareTo(UserData o) {
		// TODO Auto-generated method stub
		return (int)(o.getRate())- (int)(getRate());
	}*/
	
	
}


