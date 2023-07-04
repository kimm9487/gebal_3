package gebal.ver3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;





public class Registration {
    private static String emailPrefix;
    private static String dataDir = "D:/thePlayer/";

private static String email;
    
    static private String path;

   public static String getPath() {
      return path;
   }

   public static void setPath(String path) {
      Registration.path = path;
   }

    
    public static String getEmail() {
      return email;
   }

   public static void setEmail(String email) {
      Registration.email = email;
   }

    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("이메일을 입력하세요: ");
        String email = scanner.nextLine();

        // 동일한 이메일이 있는 경우 또는 올바른 이메일 형식이 아닌 경우 재입력 요청
        while (isExistingEmail(email) || !isValidEmail(email) || !isValidEmailCheck(email)) {
            if (isExistingEmail(email)) {
                System.out.println("이미 존재하는 이메일입니다. 다른 이메일을 입력해주세요.");
            } else {
                System.out.println("올바른 이메일 형식이 아닙니다. 대문자, 숫자를 포함하고 8~12자 이내로 입력해주세요.");
            }
            System.out.print("이메일을 입력하세요: ");
            email = scanner.nextLine();
        }

        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        // 비밀번호 유효성 검사 및 회원가입 처리
        while (!isValidPassword(password)) {
            System.out.println("비밀번호는 8~12자 이내로 입력해주세요.");
            System.out.print("비밀번호를 입력하세요: ");
            password = scanner.nextLine();
        }

        createUserDirectory(email);
        saveUserData(email, password);
        System.out.println("축 회원가입!");
        
        Main.main(new String[0]);
        scanner.close();
        
    }

    // 동일한 이메일이 이미 존재하는지 확인하는 메서드
    private static boolean isExistingEmail(String email) {
        String userFolder = dataDir + email.split("@")[0] + "/";
        File directory = new File(userFolder);
        return directory.exists();
    }

    // 이메일 유효성 검사 메서드
    private static boolean isValidEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false; // '@' 기호가 없으면 유효하지 않은 이메일
        }

        String username = parts[0];
        // 이메일 아이디는 대문자와 숫자로만 구성되고 8~12자 이내여야 함
        boolean hasUppercase = false;
        boolean hasNumber = false;

        // 이메일 아이디는 대문자와 숫자로만 구성되어야 함
        for (char ch : username.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }
        }

        // 대문자와 숫자가 모두 포함되고 8~12자 이내이어야 함
        return hasUppercase && hasNumber && username.length() >= 8 && username.length() <= 12;
    }

    // 이메일 요구사항 검사 메서드
    private static boolean isValidEmailCheck(String email) {
        // 이메일에 대문자, 숫자 포함 여부 및 길이 조건을 검사.
        boolean hasUppercase = false;
        boolean hasNumber = false;

        for (char ch : email.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (hasUppercase && hasNumber) {
                return true;
            }
        }

        return false;
    }

    // 비밀번호 길이 검사 메서드
    private static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 12;
    }

    // 사용자 폴더 생성 메서드
    private static void createUserDirectory(String email) {
        String userFolder = dataDir + email.split("@")[0] + "/";
        File directory = new File(userFolder);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("사용자 폴더가 생성되었습니다.");
            } else {
                System.out.println("사용자 폴더 생성에 실패하였습니다.");
            }
        }
    }

    // 사용자 데이터 저장 메서드
    private static void saveUserData(String email, String password) {
        String userFolder = dataDir + email.split("@")[0] + "/";
        String userDataFile = userFolder + email.split("@")[0] + ".txt";

        try (FileWriter writer = new FileWriter(userDataFile)) {
            writer.write("이메일: " + email + "\n");
            writer.write("비밀번호: " + password + "\n");
            writer.write("마지막 로그인 시간: " + getCurrentTime() + "\n"); // 현재 시간 정보 추가
            System.out.println("사용자 데이터 저장완료.");
        } catch (IOException e) {
            System.out.println("사용자 데이터 저장실패.");
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date currentTime = new Date();
      
      return dateFormat.format(currentTime);
   }

   // 사용자 정보 로드 메서드
    private static String loadUserData(String email) {
        String userFolder = dataDir + email.split("@")[0] + "/";
        String userDataFile = userFolder + email.split("@")[0] + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(userDataFile))) {
            StringBuilder userData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                userData.append(line).append("\n");
            }
            return userData.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //마지막시간 검증 메서드
    public static String getLastLoginTime(String email) {
        String userData = loadUserData(email);
        if (userData != null) {
            int startIndex = userData.indexOf("마지막 로그인 시간: ");
            if (startIndex != -1) {
                int endIndex = userData.indexOf("\n", startIndex);
                if (endIndex != -1) {
                    return userData.substring(startIndex + "마지막 로그인 시간: ".length(), endIndex);
                }
            }
        }
        return null;
    }

    // 로그인 검증 메서드
    public static boolean validateLogin(String email, String password) {
        String userData = loadUserData(email);//loadUserData메서드를 사용해 email에 해당하는 사용자 데이터를 가져옴
        if (userData != null) {//사용자 데이터가 null이 아닌경우에만 내부의 코드 실행
            if(userData.contains("이메일: " + email) && userData.contains("비밀번호: " + password)){//데이터에 이메일과 비밀번호가 모두 포함된건지 확인 데이터에
            	//이메일과 비밀번호가 저장되어있어야 유효함
               String lastLoginTime = getLastLoginTime(email);//이전에 마지막으로 로그인 했을 경우에 저장된 시간을 가져옴
               if(lastLoginTime != null) {//마지막 로그인 시간이 null아닐때만 코드실행
                  System.out.println("마지막 로그인 시간: " + lastLoginTime);//마지막 로그인 시간 출력
               }
                //입력한 이메일과 비밀번호가 데이터와 일치하면 마지막 로그인 시간을 출력함.
                updateLastLoginTime(email);
               return true;
            }
            
        }
        return false;
    }
    
 // 마지막 로그인 시간 갱신 메서드
    private static void updateLastLoginTime(String email) {//사용자의 마지막 로그인 시간을 업데이트 하는 역할의 메서드
        String userFolder = dataDir + email.split("@")[0] + "/";//사용자 경로를 생성하고 dataDir은 데이터 디렉토리의 경로
        //email.split("@")[0]은 @이전의 부분을 추출해서 사용자 폴더 경로를 생성함
        String userDataFile = userFolder + email.split("@")[0] + ".txt";

        try {
            Path filePath = Paths.get(userDataFile);//데이터 파일을 Paths.get 메서드를 사용해 문자열을 Path객체로 변환
            List<String> lines = Files.readAllLines(filePath);
            //파일에서 모든 라인을 List형태로 저장해서 readAllLines 메서드를 사용해 List에 저장함.
            
            // 마지막 로그인 시간 라인 찾기
            for (int i = 0; i < lines.size(); i++) {
            	//각 라인에 대해 반복문 실행
            	
                if (lines.get(i).startsWith("마지막 로그인 시간:")) {//각 라인을 반복문으로 실행하던 중 "마지막 로그인 시간:으로 시작하는 라인을 확인함.
                	//이 조건에 맞는경우 라인을 찾은것.
                    lines.set(i, "마지막 로그인 시간: " + getCurrentTime());//getCurrentTime 메서드를 호출해서 해당 라인을 현재 시간으로 갱신함.
                    break;
                }
            }

            Files.write(filePath, lines);//Files.write 메서드를 이용해 갱신한 라인으로 파일을 다시 씀
            System.out.println("마지막 로그인 시간이 갱신되었습니다.");//모든 작업 예외없이 수행 시 출력
        } catch (IOException e) {
            System.out.println("마지막 로그인 시간 갱신에 실패하였습니다.");//예외 발생 했을 시 출력
            e.printStackTrace();
        }
    }
 // 로그인 메서드
    public static void loginUser() {
        Scanner scanner = new Scanner(System.in);//사용자 입력을 받기 위해 Scanner 객체 생성

        String email;
        String password;

        while (true) {//루프 시작 로그인을 성공하거나 취소 할 때 까지 루프 반복
            System.out.print("이메일을 입력하세요: ");//이메일 입력메세지 출력
            email = scanner.nextLine();//입력받은 문자열을 email 변수에 저장하고 scanner.nextLine()으로 사용자의 입력을 받음.

            if (!isValidEmail(email)) {//이메일이 유효하지 않을때
                System.out.println("잘못된 이메일입니다.");
                System.out.print("다시 시도하시겠습니까? (Y/N): ");
                String retry = scanner.nextLine();//사용자로부터 다시 시도 여부를 입력 받음.

                if (!retry.equalsIgnoreCase("Y")) {
                	break;
                	//잘못된 이메일이 입력되었을 때 사용자가 Y를 입력하지 않으면 루프를 탈출하고 종료.
                }

                continue;
            }

            System.out.print("비밀번호를 입력하세요: ");//유효한 이메일일 경우 비밀번호 입력을 받음.
            password = scanner.nextLine();
            System.out.print("\n");
            
            if (!isValidPassword(password)) {//위와 같은 원리의 로직.
                System.out.println("잘못된 비밀번호입니다.");
                System.out.print("다시 시도하시겠습니까? (Y/N): ");
                String retry = scanner.nextLine();

                if (!retry.equalsIgnoreCase("Y")) {
                    break;
                }

                continue;
            }

            if (validateLogin(email, password)) {//위에서 옳은 이메일과 비밀번호를 입력했을 경우 
            	//validateLogin 함수를 호출해서 로그인을 검증하고 예외가 없다면 
                System.out.println("로그인 성공!");//해당 메시지 출력
                
                // 로그인 성공 후 처리할 로직
                String userFolder = dataDir + email.split("@")[0] + "/";
                setPath(userFolder + email.split("@")[0] + ".txt");  // 경로 저장
                UserData.setId(email.split("@")[0]);
                Main.PlayGame(email);//로그인에 성공 했을 때 메인클래스의 PlayGame메서드를 호출해 게임을 실행
//              여기 email 부분 코드 추가함
                break;
            } else {//아이디와 비밀번호가 예외 없이 옳은 방식이나 회원가입한 데이터가 없는 아이디일 경우 로그인에 실패하고
            	//다시 시도 할 코드 위의 아이디,비밀번호 검증 코드와 같음.
                System.out.println("잘못된 이메일 또는 비밀번호입니다.");
                System.out.print("다시 시도하시겠습니까? (Y/N): ");
                String retry = scanner.nextLine();

                if (!retry.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        }

        scanner.close();
    }


    public static void withdrawUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("이메일을 입력하세요: ");
        String email = scanner.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        if (validateLogin(email, password)) {
            System.out.print("정말로 회원탈퇴 하시겠습니까? (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                String userFolder = dataDir + email.split("@")[0] + "/";
                File directory = new File(userFolder);

                deleteDirectory(directory);
                System.out.println("회원탈퇴가 완료되었습니다.");
                System.exit(0); // 게임 종료
            } else {
                System.out.println("회원탈퇴를 취소하셨습니다.");
            }
        } else {
            System.out.println("잘못된 이메일 또는 비밀번호입니다.");
        }

        scanner.close();
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}