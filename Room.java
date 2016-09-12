public class Room {

	private String roomName;

	private String hostName;

	private String password;

	private double latitude;

	private double longitude;

	private int num;

	private int eTime;

	private int roomID;

	/**
	 * DB上のroomIDは、AutoIncrementで勝手に割り振られている。
	 * それを、返り値で受け取る。
	 * 
	 * 
	 * 返り値は, roomID : int
	 * 
	 * 
	 */
	public int sendRoomInfo(String roomName, String password, String hostName, int num, double latitude, double longitude, int time) {
		return 0;
	}

	public void sendBackRoomID(int roomID) {

	}

}
