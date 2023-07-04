package gebal.ver3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Player{
   private String id;
   private double winRate;
   public Player(String id, double winRate) {
      
      this.id = id;
      this.winRate = winRate;
   }
   public String getId() {
      return id;
   }
   
   public double getWinRate() {
      return winRate;
   }
}

public class Rate {
      
   public static void rate() {
   LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간 얻기.
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"); // 원하는 방식으로 초기화하기.
      String formattedDateTime = now.format(formatter);
      
      UserData.setRate((double) (Gamed.userwin) / (double) (Gamed.cumwin + Gamed.userwin + Gamed.draw) * 100);
      String rateString = (" [전적]\n승 : "+ Gamed.userwin + "\n패 : "  + Gamed.cumwin + "\n무승부: " + Gamed.draw + "\n승률 :"
            + (int) (UserData.getRate()) + "%");
      UserData.setUserRate(formattedDateTime+" [전적로그]\n" 
            + "승 - " +  Gamed.userwin 
            + "\n패 - "+ Gamed.cumwin 
            + "\n무승부 - " + Gamed.draw
            + "\n누적 승률 - " + (int)UserData.getRate() +"%");
      System.out.println(formattedDateTime + rateString);
      
   }
   
   public static void printTop10Ranking(List<Player> userDatas) { //최종 전체 랭킹 10위까지 출력
      
       // 메소드 쓰는 법 클래스명.printTop10Ranking
      // List<Player> players =Test.readRankingFile("D:\\ranking\\ranking.txt");
         //Test.printTop10Ranking(players);
      
       Collections.sort(userDatas,Comparator.comparingDouble(Player::getWinRate).reversed());

        int count = Math.min(userDatas.size(), 10);
        for (int i = 0; i < count; i++) {
            Player player= userDatas.get(i);
            System.out.println("승률별 랭킹");
           System.out.println("["+ (i + 1)  + "등] 이름: " + player.getId() + "\n승률: " + player.getWinRate());
        }
        System.out.print("\n");
        System.out.println("메뉴를 선택하세요");
        Gamed.gameIng();
        
    }
   
   // 유저 파일에 전적과 승률을 이어쓴다.
   public static void saveUserRate() {
      
   
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(Registration.getPath(),true))) {
         
         bw.write(UserData.getUserRate());
         bw.flush();
         bw.newLine();
         
         
      } catch (IOException e) {
         System.out.println("사용자 데이터 저장실패.");
         e.printStackTrace();
      }
   }
   
   
   
   
   public static void rankSave() {
      
       // D:\\ranking\\ranking.txt 경로를 생성하여 allRate 에 사용 할 파일 생성
      try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\ranking\\ranking.txt",true))) {
         
         bw.write(UserData.getId() + ":" + String.format("%.3f", UserData.getRate()));
         bw.newLine();
         bw.flush();
         

         
      } catch (IOException e) {
         System.out.println("사용자 데이터 저장실패.");
         e.printStackTrace();
      }
   }
   
    
   
   
   public static void createRateDirectory() {
        
        File directory = new File("D:\\ranking");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                
            } else {
                System.out.println("사용자 폴더 생성에 실패하였습니다.");
            }
        }
   }
   
   
   public static void createRateFile() {
       File file = new File("D:\\ranking\\ranking.txt");
        
         try {
             if (file.createNewFile()) {
                 
             } else {
                 
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
       
   public static List<Player> readRankingFile(String fileName) { 
        List<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                String id = data[0];              
                double winRate = Double.parseDouble(data[1]);
                players.add(new Player(id, winRate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

   
}
 