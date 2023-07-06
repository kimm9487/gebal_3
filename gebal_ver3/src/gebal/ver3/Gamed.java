package gebal.ver3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Gamed {
    static int cumwin = 0; // 컴퓨터 전적
    static int userwin = 0; // 유저 전적
    static int draw = 0; // 무승부 횟수
    static Scanner sc = new Scanner(System.in);
    static String id;
    static String filePath = ""; // 파일 경로를 전역 변수로 선언

    public static void main(String[] args) {
        System.out.println("게임을 시작합니다.");
        id = sc.nextLine();

        filePath = "D:/thePlayer/" + id + ".txt"; // ID와 결과값으로 파일 경로 생성

        int[] values = readValuesFromFile(filePath); // 파일에서 전적 값 읽어오기
        if (values != null) {
            System.out.println("전적: 컴퓨터 전적 " + values[0] + "승 / 유저 전적 " + values[1] + "승 / 무승부 횟수 " + values[2]);
            cumwin = values[0]; // 컴퓨터 전적 값 설정
            userwin = values[1]; // 유저 전적 값 설정
            draw = values[2]; // 무승부 전적 값 설정
        } else {
            int[] defaultValues = {0, 0, 0}; // 기본 전적 값
            createFileWithDefaultValues(filePath, defaultValues);
        }

        gameIng();
    }

    private static int[] readValuesFromFile(String filePath) { // 파일에서 전적 값 읽어들이는 메서드
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int[] values = new int[3];

            for (int i = 0; i < 3; i++) {
                String line = bufferedReader.readLine();
                values[i] = Integer.parseInt(line);
            }

            bufferedReader.close();

            return values;
        } catch (IOException | NumberFormatException e) {
            System.out.println("사용자 파일이 존재하지 않거나 손상되었습니다.");
            return null;
        }
    }

    // 파일 생성하고 기본 전적값을 저장하는 메서드
    private static void createFileWithDefaultValues(String filePath, int[] defaultValues) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);

            for (int i = 0; i < 3; i++) {
                fileWriter.write(defaultValues[i] + "\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 게임 시작
    public static void startGame(String a) {
        id = a;
       // JOptionPane.showInputDialog(null,"가위(1) / 바위(2) / 보(3) 중 하나를 입력하세요: ");
        String[] input = {"가위", "바위", "보"};
        String str = JOptionPane.showInputDialog(null,"가위(1) / 바위(2) / 보(3) 중 하나를 입력하세요: "); // 선택
        int userNum = 0;
        if (str.equals("가위") || str.equals("1")) {
            userNum = 1;
        } else if (str.equals("바위") || str.equals("2")) {
            userNum = 2;
        } else if (str.equals("보") || str.equals("3")) {
            userNum = 3;
        }

        Random random = new Random();
        int cumNum = random.nextInt(3) + 1;

        if (cumNum > userNum) {
            if (cumNum == 3 && userNum == 1) {
                userwin++;
                JOptionPane.showMessageDialog(null, "Computer: " + input[cumNum - 1] + "\nUser: " + input[userNum - 1] + "\n결과: User 승");
            } else {
                cumwin++;
                JOptionPane.showMessageDialog(null, "Computer: " + input[cumNum - 1] + "\nUser: " + input[userNum - 1] + "\n결과: Computer 승");
            }
        } else if (cumNum < userNum) {
            if (cumNum == 1 && userNum == 3) {
                cumwin++;
                JOptionPane.showMessageDialog(null, "Computer: " + input[cumNum - 1] + "\nUser: " + input[userNum - 1] + "\n결과: Computer 승");
            } else {
                userwin++;
                JOptionPane.showMessageDialog(null, "Computer: " + input[cumNum - 1] + "\nUser: " + input[userNum - 1] + "\n결과: User 승");
            }
        } else {
            draw++;
            JOptionPane.showMessageDialog(null, "Computer: " + input[cumNum - 1] + "\nUser: " + input[userNum - 1] + "\n결과: 무승부");
        }

        int[] values = {cumwin, userwin, draw};
        String resultFilePath = "D:/thePlayer/" + id + "_result.txt";
        writeValuesToFile(resultFilePath, values);
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간 얻기.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"); // 원하는 방식으로 초기화하기.
        String formattedDateTime = now.format(formatter);
          UserData.setRate((double) (Gamed.userwin) / (double) (Gamed.cumwin + Gamed.userwin + Gamed.draw) * 100);
          UserData.setUserRate(formattedDateTime+" [전적로그]\n" 
              + "승 - " +  Gamed.userwin 
              + "\n패 - "+ Gamed.cumwin 
              + "\n무승부 - " + Gamed.draw
              + "\n누적 승률 - " + (int)UserData.getRate() +"%");
          
        
        gameIng();
    }

    // 전적값을 파일에 쓰기.
    private static void writeValuesToFile(String filePath, int[] values) {
        try {
            Path directoryPath = Paths.get("D:/thePlayer");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            FileWriter fileWriter = new FileWriter(filePath);

            for (int i = 0; i < 3; i++) {
                fileWriter.write(values[i] + "\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 게임 끝나고, 이후
    static void gameIng() {
        while (true) {
            String input = JOptionPane.showInputDialog("1. 게임 계속하기\n2. 전적 보기\n3. 승률별 랭킹보기\n4. 승리 제일 많이한 랭킹 보기\n5. 회원탈퇴하기\n6. 저장 및 종료");
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 1:
                    startGame(id); // 게임 계속하기 하면, startGame()메서드 호출
                    break;
                case 2:
                    Rate.rate(); // 전적 보기면, gameRate() 메서드 호출
                    break;
                case 3:
                    List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
                    Rate.printTop10Ranking(players);
                    break;
                case 4:
                    List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();
                    RankingSystem.sortPlayersByScoreDescending(playerDataList);
                    RankingSystem.displayRanking(playerDataList);
                    break;
                case 5:
                    Registration.withdrawUser();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "게임을 종료합니다.");
                    Rate.saveUserRate();
                    Rate.rankSave();
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "잘못된 입력 값입니다.");
                    break;
            }
        }
    }


    
}