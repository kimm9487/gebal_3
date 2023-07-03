package gebal.ver3;

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

	public static void PlayGame() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("1. 게임 시작");
		System.out.println("2. 승률 랭킹보기!");
		System.out.println("3. 게임 판수 TOP10");
		System.out.println("4. 게임 종료");
		int choice = scanner.nextInt();
		
		if(choice == 1) {
			Gamed.startGame();
		}else if(choice == 4) {
			System.exit(0);
		}else {
			System.out.println("잘못 된 입력 입니다.");
		}
		scanner.close();
	}

}


