import java.util.Date;

public class User {

	private String name;

	private String nickName;

	private byte gender;

	private Date birthday;

	private String message;

	private int userID;

	/**
	 * DB上のuserIDは、AutoIncrementで勝手に割り振られている。
	 * それを、返り値で受け取る。
	 * 
	 * 
	 * 返り値は, userID : int
	 * 
	 * 
	 * 
	 */
	public int sendUserInfo(String name, String nickName, byte gender, Date birthday, String message) {
		return 0;
	}

	public void sendBackRoomID(int userID) {

	}

}
