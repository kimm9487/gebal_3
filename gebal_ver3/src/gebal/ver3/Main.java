package gebal.ver3;

import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 회원탈퇴");
        System.out.print("메뉴를 선택하세요: ");
        int choice = scanner.nextInt();

        if(choice == 1) {
           Registration.registerUser();
        }else if(choice == 2) {
           Registration.loginUser();
        }else if(choice == 3) {
           Registration.withdrawUser();
        }else {
           System.out.println("잘못 된 입력 입니다.");
        }
        scanner.close();
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
      
      System.out.println("1. 게임 시작");
      System.out.println("2. 승률 랭킹보기!");
      System.out.println("3. 게임 승 多 TOP10");
      System.out.println("4. 게임 종료");
      int choice = scanner.nextInt();
      
      if(choice == 1) {
         Gamed.startGame(id);
      }else if(choice == 4) {
         System.exit(0);
      }else if(choice==2){
    	  List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
    	    Rate.printTop10Ranking(players);
      }else if(choice==3) {
         RankingSystem.sortPlayersByScoreDescending(playerDataList);
         RankingSystem.displayRanking(playerDataList);
         
      }else{
         System.out.println("잘못 된 입력 입니다.");
         
      }
      scanner.close();
   }

}

       
    

