package gebal.ver3;

import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
    	Rate.createRateDirectory();
    	Rate.createRateFile();
    	Scanner scanner = new Scanner(System.in);
    	while (true) {
    		 System.out.print("\n");
    	        System.out.println("가위바위보 게임에 오신걸 환영합니다!");
    	        System.out.println("1. 회원가입");
    	        System.out.println("2. 로그인");
    	        System.out.println("3. 승률 랭킹보기");
    	        System.out.println("4. 승리 제일 많이한 랭킹 보기");
    	        System.out.println("5. 게임 종료");
    	        System.out.print("메뉴를 선택하세요: ");
    	        int choice = scanner.nextInt();

    	        if(choice == 1) {
    	           Registration.registerUser();//사용자가 1을 입력했을 때 Registreation Class에 registerUser()메서드 호출해서 사용자 등록 진행
    	        }else if(choice == 2) {
    	           Registration.loginUser();//사용자가 2를 입력했을 때 Registreation Class에 loginUser()메서드 호출해 로그인 진행
    	        }else if(choice == 3) {
    	           Rate.createRateDirectory();//사용자가 3을 입력했을 때 Rate Class에 createRateDirectory()메서드 호출
    	           Rate.createRateFile();//위와 같음
    	           
    	           
    	           List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");//Rate Class의 readRankingFile메서드를 호출 해 Player랭킹 데이터파일을 읽어와
    	           //player 객체들의 list인 players 생성
    	           if (players.isEmpty()) {//만약 불러들인 데이터파일에 기록이 없는경우 아래 메시지 출력
    	            System.out.println("아직 기록된 정보가 없습니다");
    	            continue;
    	         }else {
    	             Rate.printTop10Ranking(players);//기록이 있다면 탑10랭킹을 출력함.
    	             continue;
    	         }
    	          
    	        }else if(choice ==4) {
    	           List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();//사용자가 4를 입력했을 때 RankingSystem Class에 getPlayerDataList()메서드 호출하여
    	           RankingSystem.sortPlayersByScoreDescending(playerDataList);//playerDataList객체들의 리스트들을 불러와 sortPlayerByScoreDescending메서드를 호출해
    	            RankingSystem.displayRanking(playerDataList);//점수에 따라 내림차순으로 정렬해 출력함.
    	            continue;
    	        }else if(choice ==5) {
    	           System.out.println("게임을 종료합니다.");
    	           System.exit(0);//사용자가 5를 입력했을 때 위 메세지를 출력 후 종료함.
    	        }else {//사용자가 1 ~ 5의 숫자가 아닌 다른 문자열을 입력했을 때 아래 메세지를 출력 후 다음 반복으로 넘어감
    	           System.out.println("잘못 된 입력 입니다.");
    	           continue;
    	        }
    	        scanner.close();
		}
       
       
    }

   public static void PlayGame(String a) {
      List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();//RankingSystem Class에 
      //getPlayerDataList 메서드를 호출하여 playerDataList를 가져옴.
      String email = a;
        int atIndex = email.indexOf("@");//"@"의 인덱스를 찾아 해당 
        String id = email.substring(0, atIndex);//"@"의 인덱스까지의 문자열을 아이디로 추출
        System.out.println("추출된 아이디: " + id);//id에 위에 문자열을 아이디로 추출한 값을 대입
      System.out.println("안녕하세요"+id+"님");
      //추가
      Scanner scanner = new Scanner(System.in);
      
      while (true) {
    	  System.out.println("1. 게임 시작");
          System.out.println("2. 승률 랭킹보기");
          System.out.println("3. 승리 제일 많이한 랭킹 보기");
          System.out.println("4. 회원탈퇴");
          System.out.println("5. 게임 종료");
          int choice = scanner.nextInt();
          
          if(choice == 1) {
             Gamed.startGame(id);//Gamed클래스의 startGame()메서드 호출
          
          }else if(choice == 2){
             List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
             Rate.createRateDirectory();//위메인에서의 초이스 2번 코드와 같은 맥락
             Rate.createRateFile();
             
            
          if (players.isEmpty()) {
                System.out.println("아직 기록된 정보가 없습니다");
                continue;
             }else {
                 Rate.printTop10Ranking(players);
                 continue;
             }
             
               
          }else if(choice==3) {//이하동문
             RankingSystem.sortPlayersByScoreDescending(playerDataList);
             RankingSystem.displayRanking(playerDataList);
             continue;
             
          }else if(choice == 4){//Regisration Class의 withdrawUser()메서드를 호출해 회원 탈퇴를 진행
        	  Registration.withdrawUser();
        	  continue;
             
          }else if (choice==5) {//사용자가 5를 입력했을 경우 종료.
    		System.exit(0);
    	}else {
    		System.out.println("잘못된 입력 값입니다");//사용자가 1 ~ 5의 값이 아닌 문자열을 입력했을 때
    		continue;//위 메세지를 출력하고 반복문을 다시 실행.
    	}
    		
    	
          scanner.close();
       }
	}
      
      
      

}

       
    