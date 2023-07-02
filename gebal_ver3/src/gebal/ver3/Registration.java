package gebal.ver3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Registration {
    private static String emailPrefix;
    private static String dataDir = "D:/thePlayer/";

    public static String getEmailPrefix() {
        return emailPrefix;
    }

    public static void setEmailPrefix(String emailPrefix) {
        Registration.emailPrefix = emailPrefix;
    }

    public static String getDataDir() {
        return dataDir;
    }

    public static void setDataDir(String dataDir) {
        Registration.dataDir = dataDir;
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
            System.out.println("사용자 데이터 저장완료.");
        } catch (IOException e) {
            System.out.println("사용자 데이터 저장실패.");
            e.printStackTrace();
        }
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

    // 로그인 검증 메서드
    public static boolean validateLogin(String email, String password) {
        String userData = loadUserData(email);
        if (userData != null) {
            return userData.contains("이메일: " + email) && userData.contains("비밀번호: " + password);
        }
        return false;
    }

 // 로그인 메서드
    public static void loginUser() {
        Scanner scanner = new Scanner(System.in);

        String email;
        String password;

        while (true) {
            System.out.print("이메일을 입력하세요: ");
            email = scanner.nextLine();

            if (!isValidEmail(email)) {
                System.out.println("잘못된 이메일입니다.");
                System.out.print("다시 시도하시겠습니까? (Y/N): ");
                String retry = scanner.nextLine();

                if (!retry.equalsIgnoreCase("Y")) {
                    break;
                }

                continue;
            }

            System.out.print("비밀번호를 입력하세요: ");
            password = scanner.nextLine();

            if (!isValidPassword(password)) {
                System.out.println("잘못된 비밀번호입니다.");
                System.out.print("다시 시도하시겠습니까? (Y/N): ");
                String retry = scanner.nextLine();

                if (!retry.equalsIgnoreCase("Y")) {
                    break;
                }

                continue;
            }

            if (validateLogin(email, password)) {
                System.out.println("로그인 성공!");
                // 로그인 성공 후 처리할 로직을 작성하세요.
                break;
            } else {
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