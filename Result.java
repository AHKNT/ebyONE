import java.util.List;

public class Result {

	/**
	 * Lisｔ<image> こんなん
	 */
	private List image;

	/**
	 * List<majority>
	 */
	private List majority;

	/**
	 * List<userID,image>の長さがグループの人数と一致したら、
	 * Listを送信
	 */
	public void sendImage(List image) {

	}

	/**
	 * List<majority>にユーザが投票した写真の票数を取得、加算
	 */
	public void imgMajority(List majority) {

	}

	/**
	 * List<majority>で最も票数の多い添字と一致する
	 * List<imge>内の写真をMVPとする。
	 * 
	 * その後、DB内の戦歴を取り出し、
	 * MVPの写真とともにユーザに送信する
	 */
	public void result() {

	}

}
