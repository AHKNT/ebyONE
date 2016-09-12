import java.util.ArrayList;

public class Game {

	/**
	 * roomIDとuserIDでがんばって取得してください
	 */
	private int groupID;

	/**
	 * 回答登録時に使用
	 */
	private int questionID;

	private String question;

	/**
	 * ArrayList<String>
	 * 回答のリスト
	 */
	private ArrayList answer;

	private int mKeyAns;

	/**
	 * 考察時間（初期にホストが入力する時間）
	 */
	private Time eTime;

	/**
	 * 固定時間
	 * 
	 */
	private Time fTime;

	private RndCamera rndCamera;

	private Time cTime;

	private Game(int groupID) {

	}

	/**
	 * ユーザーから質問が送られてきて、
	 * その質問をDBに登録する。
	 * 登録された際のquestionIDを取得し、
	 * 同グループ内のユーザーに送信する。
	 */
	public void question(String question) {

	}

	/**
	 * eTimeとfTimeを取得
	 * 
	 * Game()内で実行
	 */
	public void getTime() {

	}

	/**
	 * 実行時に、受信待ち
	 * そして、回答数とグループの人数が一致したときに
	 * 全員に送信される
	 */
	public void answer() {

	}

	public void majority(int groupID, int keyID) {

	}

	/**
	 * 多数決で出された1つのキーが正解しているかジャッジ
	 * 結果をユーザに送信
	 * 正解したらDBのグループテーブルの終了フラグを書き換えてる
	 */
	public void keyJudge(int key) {

	}

	public void questionerSelect() {

	}

}
