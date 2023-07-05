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
    	           Registration.registerUser();
    	        }else if(choice == 2) {
    	           Registration.loginUser();
    	        }else if(choice == 3) {
    	           Rate.createRateDirectory();
    	           Rate.createRateFile();
    	           
    	           
    	           List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
    	           if (players.isEmpty()) {
    	            System.out.println("아직 기록된 정보가 없습니다");
    	            continue;
    	         }else {
    	             Rate.printTop10Ranking(players);
    	             continue;
    	         }
    	          
    	        }else if(choice ==4) {
    	           List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();
    	           RankingSystem.sortPlayersByScoreDescending(playerDataList);
    	            RankingSystem.displayRanking(playerDataList);
    	            continue;
    	        }else if(choice ==5) {
    	           System.out.println("게임을 종료합니다.");
    	           System.exit(0);
    	        }else {
    	           System.out.println("잘못 된 입력 입니다.");
    	           continue;
    	        }
    	        scanner.close();
		}
       
       
    }

   public static void PlayGame(String a) {
      List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();
      String email = a;
        int atIndex = email.indexOf("@");
        String id = email.substring(0, atIndex);
        System.out.println("추출된 아이디: " + id);
      System.out.println("안녕하세요"+id+"님");
//      추가
      Scanner scanner = new Scanner(System.in);
      
      while (true) {
    	  System.out.println("1. 게임 시작");
          System.out.println("2. 승률 랭킹보기");
          System.out.println("3. 승리 제일 많이한 랭킹 보기");
          System.out.println("4. 회원탈퇴");
          System.out.println("5. 게임 종료");
          int choice = scanner.nextInt();
          
          if(choice == 1) {
             Gamed.startGame(id);
          
          }else if(choice == 2){
             List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
             Rate.createRateDirectory();
             Rate.createRateFile();
             
            
          if (players.isEmpty()) {
                System.out.println("아직 기록된 정보가 없습니다");
                continue;
             }else {
                 Rate.printTop10Ranking(players);
                 continue;
             }
             
               
          }else if(choice==3) {
             RankingSystem.sortPlayersByScoreDescending(playerDataList);
             RankingSystem.displayRanking(playerDataList);
             continue;
             
          }else if(choice == 4){
        	  Registration.withdrawUser();
        	  continue;
             
          }else if (choice==5) {
    		System.exit(0);
    	}else {
    		System.out.println("잘못된 입력 값입니다");
    		continue;
    	}
    		
    	
          scanner.close();
       }
	}
      
      
      

}

       
    