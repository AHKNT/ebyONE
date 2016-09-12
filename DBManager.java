package jp.ac.asojuku.st;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;

public class DBManager{
	
	private final String URL = "jdbc:mysql://localhost/ebyONE?useUnicode=true&characterEncoding=utf8";
	private final String USERNAME = "player";
	private final String PASSWORD = "password";
	private Connection connection;
	
	//データーベース接続
	public void dbConnect(){
		try{
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("接続失敗");
		}
	}
	//データベース切断
	public void dbDisconnect(){
		try{
			if (connection != null){
				connection.close();
			}
		}catch (SQLException e){
			System.out.println("切断失敗");
		}
	}
	/*
	以下Game関連
	 */

	//質問登録
	public void insertQuestion(int groupID,String qContent){
		try{
			dbConnect();
			Statement stmt = connection.createStatement();
			String sql ="INSERT INTO Question (QContent, GroupID) VALUES ('" + qContent + "', '" + Integer.toString(groupID) + "')";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			System.out.println("質問の登録失敗");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
	}
	
	//質問の回答登録
	public void insertAnswer(int groupID,String aContent,int userID){
		try{
			dbConnect();
			
			int questionID = getQuestionID(groupID);
			
			Statement stmt = connection.createStatement();
			String sql ="INSERT INTO Answer (AContent, QuestionID, UserID) VALUES ('" + aContent + "','" + Integer.toString(questionID) + "', '" + Integer.toString(userID) + "')";
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			System.out.println("質問の回答登録失敗");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
	}
	
	//選択キー登録
	public void insertChosen(int groupID,int keyID){
		try{
			dbConnect();
			Statement stmt = connection.createStatement();
			String sql ="INSERT INTO Chosen (GroupID, KeyID) VALUES ('" + Integer.toString(groupID) + "','" + Integer.toString(keyID) + "')";
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			System.out.println("選択キー登録失敗");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
	}
	
	//グループのユーザ取得	
	public ArrayList<Integer> getUser(int groupID){
		ArrayList <Integer> userList = new ArrayList<Integer>();
		try{
			dbConnect();
			Statement stmt = connection.createStatement();
			String sql ="SELECT UserID FROM GroupMember WHERE GroupID =" + Integer.toString(groupID);
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				userList.add(result.getInt("UserID"));
			}
		}catch(SQLException e){
			System.out.println("取得失敗");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
		return userList;
	}
	
	//グループのユーザー数取得
	public int getUserCnt(int groupID){
		int userCnt = 0;
		try{
			dbConnect();
			Statement stmt = connection.createStatement();
			String sql ="SELECT count(UserID) FROM GroupMember WHERE GroupID =" + groupID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				userCnt = result.getInt("count(UserID)");
			}
		}catch(SQLException e){
			System.out.println("ユーザ数取得失敗");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
		return userCnt;
	}
	
	//回答済みユーザー取得
	public ArrayList<Integer> getAnsweredUser(int groupID){
		ArrayList<Integer> userList = new ArrayList<Integer>();
		try{
			dbConnect();
			
			int questionID = getQuestionID(groupID);
			
			Statement stmt = connection.createStatement();
			String sql ="SELECT UserID FROM Answer WHERE QuestionID =" + questionID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				userList.add(result.getInt("UserID"));
			}
		}catch(SQLException e){
			System.out.println("回答済みユーザ取得失敗");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
		return userList;
	}
	
	//グループのメンバーの質問の回答取得
	public ArrayList<String> getAnswer(int groupID){
		ArrayList<String> AContentList = new ArrayList<String>();
		try{
			dbConnect();
			
			int questionID = getQuestionID(groupID);
			
			Statement stmt = connection.createStatement();
			String sql ="SELECT AContent FROM Answer WHERE QuestionID =" + questionID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				AContentList.add(result.getString("AContent"));
			}
		}catch(SQLException e){
			System.out.println("グループのメンバーの質問の回答取得");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
		return AContentList;
	}
	
	//グループの最新の質問ID取得
	//insertAnswer,getAnsweredUser,getAnswer内で使用
	public int getQuestionID(int groupID){
		int questionID = 0;
		try{
			Statement stmt = connection.createStatement();
			String sql ="SELECT MAX(QuestionID) FROM Question WHERE GroupID =" + groupID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				questionID = result.getInt("MAX(QuestionID)");
			}
		}catch(SQLException e){
			System.out.println("グループの最新の質問ID取得失敗");
			e.printStackTrace();
		}
		return questionID;
	}
	
	/*
	以下result関連
	 */

	//ゲームのログを取得
	public GameLog getGameLog(int groupID){
		//DB接続
		dbConnect();
		
		GameLog log = new GameLog();
		
		//グループにいるユーザのリスト
		log.userList = new ArrayList<Integer>(getUserR(groupID));
		//質問IDと質問内容のリスト
		ArrayList<Question> questionList = new ArrayList<Question>(getQuestionR(groupID));
		log.questionList = new ArrayList<Integer>();
		log.questionContentList = new ArrayList<String>();
		for(Question question: questionList){
			log.questionList.add(question.questionID);
			log.questionContentList.add(question.qContent);
		}
		//質問IDに対するグループのユーザの回答のHashMap
		log.answerMap = new HashMap<Integer,ArrayList<String>>();
		for(Question question: questionList){
			log.answerMap.put(question.questionID, getAnswerR(question.questionID));
		}
		//ゲーム内で選択したキーのリスト
		log.chosenKeyList = new ArrayList<Integer>(getChosenKeyR(groupID));
		
		//ルームID
		log.roomID = getRoomIDR(groupID);
		//DB切断
		dbDisconnect();
		
		return log;
	}
	
	//グループメンバー取得 result専用
	public ArrayList<Integer> getUserR(int groupID){
		ArrayList <Integer> userList = new ArrayList<Integer>();
		try{
			Statement stmt = connection.createStatement();
			String sql ="SELECT UserID FROM GroupMember WHERE GroupID =" + Integer.toString(groupID);
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				userList.add(result.getInt("UserID"));
			}
		}catch(SQLException e){
			System.out.println("グループメンバー取得失敗");
			e.printStackTrace();
		}
		return userList;
	}
	
	//グループの質問ID全て取得 result専用
	public ArrayList <Question> getQuestionR(int groupID){
		ArrayList <Question> questionIDList = new ArrayList<Question>();
		try{
			Statement stmt = connection.createStatement();
			String sql ="SELECT QuestionID,QContent FROM Question WHERE GroupID =" + Integer.toString(groupID);
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				Question question = new Question();
				question.questionID = result.getInt("QuestionID");
				question.qContent = result.getString("QContent");
				questionIDList.add(question);
			}
		}catch(SQLException e){
			System.out.println("グループの質問IDの全て取得取得失敗");
			e.printStackTrace();
		}
		return questionIDList;
	}
	//Questionを渡す時に怠いからクラスにした
	public class Question{
		int questionID;
		String qContent;
	}
	
	//questionIDに対するユーザの回答取得
	public ArrayList<String> getAnswerR(int questionID){
		ArrayList<String> aContentList = new ArrayList<String>();
		try{
			Statement stmt = connection.createStatement();
			String sql ="SELECT AContent FROM Answer WHERE QuestionID =" + questionID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				aContentList.add(result.getString("AContent"));
			}
		}catch(SQLException e){
			System.out.println("questionIDに対するユーザの回答取得失敗");
			e.printStackTrace();
		}
		return aContentList;
	}
	
	//グループのゲーム終了までの選択キー取得
	public ArrayList<Integer> getChosenKeyR(int groupID){
		ArrayList<Integer> chosenKey = new ArrayList<Integer>();
		try{
			Statement stmt = connection.createStatement();
			String sql ="SELECT KeyID FROM Chosen WHERE GroupID ="+ groupID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				chosenKey.add(result.getInt("KeyID"));
			}
		}catch(SQLException e){
			System.out.println("グループのゲーム終了までの選択キー取得失敗");
			e.printStackTrace();
		}
		return chosenKey;
	}
	
	//ルームID取得
	public int getRoomIDR(int groupID){
		int roomID = 0;
		try{
			Statement stmt = connection.createStatement();
			String sql ="SELECT RoomID FROM Groups WHERE GroupID =" + groupID;
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				roomID = result.getInt("RoomID");
			}
		}catch(SQLException e){
			System.out.println("");
			e.printStackTrace();
		}	
		return roomID;
	}

}



/*テンプレ
		try{
			dbConnect();
			Statement stmt = connection.createStatement();
			String sql ="";
			ResultSet result = stmt.executeQuery(sql);
			ResultSet result = stmt.executeUpdate(sql);
			while(result.next()){
				
			}
		}catch(SQLException e){
			System.out.println("");
			e.printStackTrace();
		}finally{
			dbDisconnect();
		}
*/

