package jp.ac.asojuku.st;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLog {
	//ルームID
	int roomID;
	//グループにいるユーザのリスト
	ArrayList<Integer> userList;
	//質問IDと質問内容のリスト
	ArrayList<Integer> questionList;
	//質問内容のリスト
	ArrayList<String> questionContentList;
	//質問IDに対するグループのユーザの回答のHashMap
	HashMap<Integer,ArrayList<String>> answerMap;
	//ゲーム内で選択したキーのリスト
	ArrayList<Integer> chosenKeyList;
}
