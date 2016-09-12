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
	//部屋立て 
	public int sendRoomInfo(String roomName, String password, String hostName, int num, double latitude, double longitude, int time) {
		DBManager dbm = new DBManager();
		int this.roomID = dbm.makeRoom(roomName,password,hostName,num,latitude,longitude,time);
		return this.roomID;
	}
	
	//アプリ側にRoomIDを送信
	public void sendBackRoomID(int roomID) {

	}

}
