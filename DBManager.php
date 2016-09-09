<?php
    class DBManager{
        private $user = "player";
        private $password = "password";
        private $dbhost = "localhost";
        private $dbname = "ebyONE";
        private $myPdo;

        //データーベース接続
        public function dbConnect(){
            try{
            $this->myPdo = new PDO('mysql:host=' . $this->dbhost . ';dbname=' . $this->dbname  . ';charset=utf8', $this->user, $this->password, array(PDO::ATTR_EMULATE_PREPARES => false));

            }catch(PDOException $e) {
            die('データベース接続失敗。'.$e->getMessage());
            }
        }

        //データベース切断
        public function dbDisconnect(){
            unset($myPdo);
        }


        /*
            以下Game関連
         */

        //質問登録
		public function insertQuedtion($groupID,$qContent){
            try{
                $this->dbConnect();
                $stmt = $this->myPdo->prepare("INSERT INTO Question (QContent, GroupID) 
                                                    VALUES (:qCon, :gID)");

                $stmt->bindParam(':qCon', $qContent, PDO::PARAM_STR);
                $stmt->bindParam(':gID', $groupID, PDO::PARAM_STR);

                $stmt->execute();

                $this->dbDisconnect();

            }catch(PDOException $e){
                die("登録失敗".$e->getMessage());
            }
		}
        //質問の回答登録
		public function insertAnswer($groupID,$aContent,$userID){
            try{
                $this->dbConnect();

                $questionID = $this->getQuestionID($groupID);

                var_dump($questionID);

                $stmt = $this->myPdo->prepare("INSERT INTO Answer (AContent, QuestionID, UserID) 
                                                    VALUES (:aCon, :qID, :userID)");

                $stmt->bindParam(':aCon', $aContent, PDO::PARAM_STR);
                $stmt->bindParam(':qID', $questionID, PDO::PARAM_STR);
                $stmt->bindParam(':userID', $userID, PDO::PARAM_STR);

                $stmt->execute();

                $this->dbDisconnect();
                
            }catch(PDOException $e){
                die("挿入失敗".$e->getMessage());
            }
		}
        //選択キー登録
        public function insertChosen($groupID,$keyID){
            try{
                $this->dbConnect();
                $stmt = $this->myPdo->prepare("INSERT INTO Chosen (GroupID, KeyID) 
                                                    VALUES (:gID, :keyID)");
                $stmt->bindParam(':gID', $groupID, PDO::PARAM_STR);
                $stmt->bindParam(':keyID', $keyID, PDO::PARAM_STR);

                $stmt->execute();

                $this->dbDisconnect();
            }catch(PDOException $e){
                die("挿入失敗".$e->getMessage());
            }
        }
        //グループのユーザ取得
        public function getUser($groupID){
            try{
                $this->dbConnect();
                $stmt = $this->myPdo->prepare("SELECT UserID
                                                 FROM GroupMember
                                                 WHERE GroupID = :gID");
                $stmt->bindParam(':gID', $groupID, PDO::PARAM_STR);

                $stmt->execute();

                $userList = array();
                while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
                    $userList[] = $row['UserID'];                  
                }
                $this->dbDisconnect();
                
                return $userList;
            }catch(PDOException $e){
                die("取得失敗".$e->getMessage());
            }
        }
        //グループのユーザー数取得
        public function getUserCnt($groupID){
            try{
                $this->dbConnect();
                $stmt = $this->myPdo->prepare("SELECT count(UserID)
                                                 FROM GroupMember
                                                 WHERE GroupID = :gID");
                $stmt->bindParam(':gID', $groupID, PDO::PARAM_STR);

                $stmt->execute();
                while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
                    $userCnt = $row['count(UserID)'];
                }
                $this->dbDisconnect();

                return $userCnt;
            }catch(PDOException $e){
                die("取得失敗".$e->getMessage());
            }
        }
        //回答済みユーザー取得
        public function getAnsweredUser($groupID){
            try{
                $this->dbConnect();

                $questionID = $this->getQuestionID($groupID);

                $stmt = $this->myPdo->prepare("SELECT UserID
                                                 FROM Answer
                                                 WHERE QuestionID = :qID");
                $stmt->bindParam(':qID', $questionID, PDO::PARAM_STR);

                $stmt->execute();

                $userList = array();
                while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
                    $userList[] = $row['UserID'];                  
                }

                return $userList;

            }catch(PDOException $e){
                die("取得失敗".$e->getMessage());
            }
        }
        //グループのメンバーの質問の回答取得
        public function getAnswer($groupID){
            try{
                $this->dbConnect();

                $questionID = $this->getQuestionID($groupID);

                $stmt = $this->myPdo->prepare("SELECT AContent
                                                 FROM Answer
                                                 WHERE QuestionID = :qID");
                
                $stmt->bindParam(':qID', $questionID, PDO::PARAM_STR);

                $stmt->execute();

                $AContentList = array();
                while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
                    $AContentList[] = $row['AContent'];                  
                }
                
                return $AContentList;

            }catch(PDOException $e){
                die("取得失敗".$e->getMessage());
            }
        }
        //グループの最新の質問ID取得
        //insertAnswer,getAnsweredUser,getAnswer内で使用
        public function getQuestionID($groupID){
            try{
                $this->dbConnect();
                $stmt = $this->myPdo->prepare("SELECT MAX(QuestionID)
                                                 FROM Question
                                                WHERE GroupID = :gID");
                $stmt->bindParam(':gID', $groupID, PDO::PARAM_STR);

                $stmt->execute();

                while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
                    $questionID = $row['MAX(QuestionID)'];                  
                }

                $this->dbDisconnect();

                return $questionID;

            }catch(PDOException $e){
                die("取得失敗".$e->getMessage());
            }
        }

        /*
            以下result関連
         */

        //ゲームのログを取得
        public function getGameLog(){
            
        }

    }
?>