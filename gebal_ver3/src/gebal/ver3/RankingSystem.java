package gebal.ver3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

public class RankingSystem {
    private static final String DIRECTORY_PATH = "D:/thePlayer/"; // 파일이 저장된 디렉토리 경로

    public static void main(String[] args) {
        List<PlayerData> playerDataList = getPlayerDataList();
        sortPlayersByScoreDescending(playerDataList);
        displayRanking(playerDataList);
    }

    // 디렉토리에서 플레이어 데이터 목록을 가져오는 메서드
    static List<PlayerData> getPlayerDataList() {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();
        List<PlayerData> playerDataList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String[] parts = fileName.split("_");
                    String playerId = parts[0];
                    int score = getScore(file);
                    playerDataList.add(new PlayerData(playerId, score));
                }
            }
        }

        return playerDataList;
    }

    // 파일의 첫 줄의 숫자를 가져오는 메서드
    private static int getScore(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            return Integer.parseInt(firstLine);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 플레이어 데이터를 점수를 기준으로 내림차순으로 정렬하는 메서드
    static void sortPlayersByScoreDescending(List<PlayerData> playerDataList) {
        Collections.sort(playerDataList, new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData player1, PlayerData player2) {
                return Integer.compare(player2.getScore(), player1.getScore());
            }
        });
    }

    // 랭킹을 출력하는 메서드
    public static void displayRanking(List<PlayerData> playerDataList) {
    	
       JOptionPane.showMessageDialog(null, "<승리 제일 많이한 랭킹>");

        int rank = 1;
        for (PlayerData playerData : playerDataList) {
        	 JOptionPane.showMessageDialog(null,rank + ". 아이디: " + playerData.getPlayerId() + ", 점수: " + playerData.getScore());
            rank++;
            if (rank > 10) {
                break;
            }
        }
        
     
    }
}

class PlayerData {
    private String playerId;
    private int score;

    public PlayerData(String playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }
}