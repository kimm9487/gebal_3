package gebal.ver3;

import java.util.List;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        Rate.createRateDirectory();
        Rate.createRateFile();
        
        while (true) {
            JOptionPane.showMessageDialog(null, "가위바위보 게임에 오신걸 환영합니다!");
            
            String choice = JOptionPane.showInputDialog("1. 회원가입\n2. 로그인\n3. 승률 랭킹보기\n4. 승리 제일 많이한 랭킹 보기\n5. 게임 종료\n메뉴를 선택하세요:");
            
            switch (choice) {
                case "1":
                    Registration.registerUser();
                    break;
                case "2":
                    Registration.loginUser();
                    break;
                case "3":
                    Rate.createRateDirectory();
                    Rate.createRateFile();

                    List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
                    if (players.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "아직 기록된 정보가 없습니다");
                    } else {
                        Rate.printTop10Ranking(players);
                    }
                    break;
                case "4":
                    List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();
                    RankingSystem.sortPlayersByScoreDescending(playerDataList);
                    RankingSystem.displayRanking(playerDataList);
                    break;
                case "5":
                    JOptionPane.showMessageDialog(null, "게임을 종료합니다.");
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "잘못된 입력입니다.");
                    break;
            }
        }
    }

    public static void PlayGame(String a) {
        List<PlayerData> playerDataList = RankingSystem.getPlayerDataList();
        String email = a;
        int atIndex = email.indexOf("@");
        String id = email.substring(0, atIndex);
        //JOptionPane.showMessageDialog(null, "추출된 아이디: " + id);
        JOptionPane.showMessageDialog(null, "안녕하세요 " + id + "님");

        while (true) {
            String choice = JOptionPane.showInputDialog("1. 게임 시작\n2. 승률 랭킹보기\n3. 승리 제일 많이한 랭킹 보기\n4. 회원탈퇴\n5. 게임 종료");

            switch (choice) {
                case "1":
                    Gamed.startGame(id);
                    break;
                case "2":
                    List<Player> players = Rate.readRankingFile("D:\\ranking\\ranking.txt");
                    Rate.createRateDirectory();
                    Rate.createRateFile();

                    if (players.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "아직 기록된 정보가 없습니다");
                    } else {
                        Rate.printTop10Ranking(players);
                    }
                    break;
                case "3":
                    RankingSystem.sortPlayersByScoreDescending(playerDataList);
                    RankingSystem.displayRanking(playerDataList);
                    break;
                case "4":
                    Registration.withdrawUser();
                    break;
                case "5":
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "잘못된 입력 값입니다");
                    break;
            }
        }
    }
}
