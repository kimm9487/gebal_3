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

import javax.swing.JOptionPane;

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
       JOptionPane.showMessageDialog(null, " [전적]\n승 : "+ Gamed.userwin + "\n패 : "  + Gamed.cumwin + "\n무승부: " + Gamed.draw + "\n승률 :"
            + (int) (UserData.getRate()) + "%");

      System.out.println(formattedDateTime + UserData.getUserRate());
      createRateDirectory();
      createRateFile();
      saveUserRate();
      
      rankSave();

   }
   
   public static void printTop10Ranking(List<Player> userDatas) { //최종 전체 랭킹 10위까지 출력
      //printTop10Ranking 메서드는 players 리스트를 승률을 기준으로 내림차순으로 정렬한 후, 상위 10개의 플레이어의 이름과 승률을 출력합니다.
      
      
      //printTop10Ranking(List<Player> userDatas) 파라미터에 
      //List<Player> players =Rate.readRankingFile("D:\\ranking\\ranking.txt")을 넣는 이유
      //readRankingFile파일에 리턴 값이 "D:\\ranking\\ranking.txt"를 하나 씩 자른 후 
      //배열로 담은 각각 인덱스를 담은 리스트를 반환하기 때문에 plyers라는 리스트에 assign 이 가능
      
       /*
        - Collections.sort(userDatas,Comparator.comparingDouble(Player::getWinRate).reversed());
          이 부분은 Collections 클래스의 sort 메소드를 사용하여 userDatas 리스트를 정렬하는 부분. 
          sort 메소드는 첫 번째 매개변수(userDatas)로 정렬할 리스트를 받고,
           두 번째 매개변수로는 정렬을 위한 Comparator 객체(Comparator.comparingDouble(Player::getWinRate).reversed())를 받음.

      - Comparator.comparingDouble(Player::getWinRate) 
         이 부분은 Comparator 객체를 생성하는데, Player 클래스의 getWinRate 메소드를 호출하여 winRate 값을 비교. 
         comparingDouble 메소드는 기준으로 사용할 속성(타입)을 지정할 수 있도록 해주는 메소드.

        - .reversed()
         이 메소드는 이전에 생성한 Comparator 객체를 역순으로 변경.
          따라서 winRate 값을 기준으로 내림차순 정렬이 이루어짐.

       결과적으로, 위의 코드는 userDatas 리스트의 요소들을 winRate 속성을 기준으로 내림차순으로 정렬하는 작업을 수행. 
       이를 통해 Player 객체의 winRate 속성이 가장 높은 값부터 가장 낮은 값까지 정렬된 리스트를 얻을 수 있다.
       */
       
       
       
        int count = Math.min(userDatas.size(), 10);// 해당 코드는 userDatas 리스트의 크기와 10 중에서 작은 값을 선택하여 변수 `카운트`에 저장하는 역할을 한다. 
        
        for (int i = 0; i < count; i++) {
            Player player= userDatas.get(i);
            JOptionPane.showMessageDialog(null,"["+ (i + 1)  + "등] 이름: " + player.getId() + "\n승률: " + player.getWinRate());
        }
    }
   public static void deleteTop10Ranking(List<Player> userDatas) { 
         
        
         Collections.sort(userDatas,Comparator.comparingDouble(Player::getWinRate).reversed());

          int count = Math.min(userDatas.size(), 10);
          for (int i = 0; i < count; i++) {
              
              
              if (userDatas.get(i).getId().equals(UserData.getId())) {
                 userDatas.remove(i);
                 
                 // 내림차순 정렬 후 현재 userDatas 리스트를 순회하여 UserData에 getId ( 회원 탈퇴하는 id) 와 같은 값이 담긴 인덱스를 삭제한다.
                 }
              
          }
          saveNewRankingFile(userDatas,"D:\\ranking\\ranking.txt");
          // 순회하여 삭제 후 해당 인덱스가 삭제된 리스트를 다시 ranking 파일에 덮어쓴다.
      }
   
   public static void saveNewRankingFile(List<Player> players, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Player player : players)  {
               //파람으로 받은 Player 클래스에 players 리스트를 순회하여 ":"구분자로 id와 승률을 다시 덮어쓴다.
                bw.write(player.getId() + ":" + player.getWinRate());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
      
       // D:\ranking\ranking.txt 경로에 .write 메소드를 이용해 UserData.getId() + ":" + UserData.getRate()를 추가한다
      try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\ranking\\ranking.txt",true))) {
         
         bw.write(UserData.getId() + ":" + UserData.getRate());
         bw.newLine();
         bw.flush();
         

         
      } catch (IOException e) {
         System.out.println("사용자 데이터 저장실패.");
         e.printStackTrace();
      }
   }
   public static void createRateDirectory() {
        // D:\ranking 폴더 생성
        File directory = new File("D:\\ranking");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                
            } else {
                System.out.println("사용자 폴더 생성에 실패하였습니다.");
            }
        }
   }
   
   
   public static void createRateFile() {
      // D:\ranking 파일에 \ranking.txt 파일 생성
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
            String line=null;
            while ((line = br.readLine()) != null) { 
               // line 변수에 readLine 변수를 이용해 한 줄씩 자르며 그 줄에 텍스트가 없을 때 까지 읽는다.
                String[] data = line.split(":"); // split 메소드를 이용하여 한 줄마다 ":" 기준으로 자른 텍스트를 data 배열에 담는다
                String id = data[0]; 
                double winRate = Double.parseDouble(data[1]);
                //데이터 배열 인덱스 값을 각각 변수에 대입하여 players 리스트에 생성자를 이용하여 파라미터값으로 추가한다.
                players.add(new Player(id, winRate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players; // return 값으로 players를 주어서 값이 대입된 리스트를 리턴시켜 값을 저장시킨다.
    }
    
}
 