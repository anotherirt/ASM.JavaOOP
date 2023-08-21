package vn.funix.fx19878.java.asm1;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Random;
public class ASM1 {
    public static final String AUTHOR = "FX19878";
    public static final String VERSION = "V1.0.0";

    public static void main(String[] args) {
        security();
        screenMenu();
    }
     /*
      *  CHỌN MỨC BẢO MẬT THẤP HOẶC CAO
      */
    private static void security() {
        Scanner sc = new Scanner(System.in);
        System.out.println("+----------+----------------------+--------+");
        System.out.println("| NGAN HANG SO | " + AUTHOR + "@" + VERSION + "            |");
        System.out.println("+----------+----------------------+--------+");
        System.out.println("| 1. Muc bao mat thuong (3 ky tu)          |");
        System.out.println("| 2. Muc bao mat cao (6 ky tu)             |");
        System.out.println("| 0. Thoat                                 |");
        System.out.println("+----------+----------------------+--------+");
        System.out.print("Chuc nang: ");
        // GỌI VỀ HÀM NHẬP BẢO MẬT THẤP HOẶC CAO SAU KHI NGƯỜI DÙNG CHỌN:
        // TRY - CATCH, BUỘC NGƯỜI DÙNG NHẬP ĐÚNG ĐỊNH DẠNG
        try {
            int n = sc.nextInt();
            while (true) {
                switch (n) {
                    case 1:
                        otp();
                    case 2:
                        otpAdvance();
                    case 0:
                        System.out.println("Cam on, hen gap lai!");
                        System.exit(1);
                    default:
                        // NGƯỜI DÙNG NHẬP KHÔNG ĐÚNG SỐ YÊU CẦU NHẬP LẠI, GỌI LẠI HÀM
                        System.out.println("----------------");
                        System.out.println("So ban nhap khong dung, vui long chon lai!");
                        System.out.print("Chuc nang: ");
                        n = sc.nextInt();
                        if (n == 1) {
                            otp();
                        } else {
                            otpAdvance();
                        }
                }
            }
        }
        catch (Exception InputMismatchException){
            System.out.println("----------------");
            System.out.println("Sai cu phap, vui long nhap lai!");
            security();
        }
    }

    /*
     * Menu chọn chức năng sau khi chọn mức bảo mật
     */
    private static void screenMenu() {
        System.out.println("+----------+----------------------+--------+");
        System.out.println("| NGAN HANG SO | " + AUTHOR + "@" + VERSION + "            |");
        System.out.println("+----------+----------------------+--------+");
        System.out.println("| 1. Nhap CCCD                             |");
        System.out.println("| 0. Thoat                                 |");
        System.out.println("+----------+----------------------+--------+");
        System.out.print("Chuc nang: ");
    }

    private static void otp() {
        screenMenu();
        Scanner sc = new Scanner(System.in);
        int randomNumber = (int) (Math.random() * 900) + 100;
        try {
            int option = sc.nextInt();
            while (true) {
                handleInputOfOTP(option, randomNumber);
            }
        }
        catch (Exception InputMismatchException){
            System.out.println("----------------");
            System.out.println("Sai cu phap, vui long nhap lai!");
            otp();
        }
    }

    private static void otpAdvance(){
        screenMenu();
        Scanner sc = new Scanner(System.in);
        String codeAdvance = generateRandomString(6);
        try {
            int option = sc.nextInt();
            while (true) {
                handleInputOfOTPAdvance(option, codeAdvance);
            }
        }
        catch (Exception InputMismatchException){
            System.out.println("----------------");
            System.out.println("Sai cu phap, vui long nhap lai!");
            otpAdvance();
        }
    }
    private static void handleInputOfOTP(int option, int randomNumber) {
        Scanner sc = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Ma xac thuc la: " + randomNumber);
                try {
                    int inputCode = sc.nextInt();
                    while (inputCode != randomNumber) {
                        System.out.println("----------------");
                        System.out.println("Ma xac thuc khong chinh xac, vui long thu lai");
                        System.out.println("Ma xac thuc la: " + randomNumber);
                        inputCode = sc.nextInt();
                    }
                } catch (Exception InputMismatchException) {
                    System.out.println("----------------");
                    System.out.println("Sai cu phap, vui long nhap lai!");
                    handleInputOfOTP(option, randomNumber);
                }
                System.out.println("Ma xac thuc chinh xac!");
                checkCCCD();
                break;

            case 0:
                System.out.println("Cam on, hen gap lai!");
                System.exit(1);
            default:
                System.out.println("----------------");
                System.out.println("So ban nhap khong dung, vui long chon lai!");
                otp();
                break;
        }
    }
    private static void handleInputOfOTPAdvance(int option, String codeAdvance) {
        Scanner sc = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Ma xac thuc la: " + codeAdvance);
                try {
                    String inputCodeAdvance = sc.nextLine();
                    while (!inputCodeAdvance.equals(codeAdvance)) {
                        System.out.println("----------------");
                        System.out.println("Ma xac thuc khong chinh xac, vui long thu lai");
                        System.out.println("Ma xac thuc la: " + codeAdvance);
                        inputCodeAdvance = sc.nextLine();
                    }
                } catch (Exception InputMismatchException) {
                    System.out.println("----------------");
                    System.out.println("Sai cu phap, vui long nhap lai!");
                    handleInputOfOTPAdvance(option, codeAdvance);
                }
                System.out.println("Ma xac thuc chinh xac!");
                checkCCCD();
                break;
            case 0:
                System.out.println("Cam on, hen gap lai!");
                System.exit(1);
            default:
                System.out.println("----------------");
                System.out.println("So ban nhap khong dung, vui long chon lai!");
                otpAdvance();
                break;
        }
    }
    // HÀM CHECK SỐ CCCD CÓ ĐÚNG ĐỊNH DẠNG
    private static void checkCCCD() {
        Scanner sc = new Scanner(System.in);
        Pattern pattern = Pattern.compile("^\\d{12}$"); //REGEX CHUỖI STRING LÀ 1,2,3,4,5,6,7,8,9.
        System.out.print("Vui long nhap so CCCD: ");
            String cccdNumbers = sc.nextLine();
            //VÒNG LẶP WHILE ĐỂ NGƯỜI DÙNG NHẬP VÀO CCCD, SAI THÌ YÊU CẦU NHẬP LẠI, NẾU NGƯỜI DÙNG NHẬP NO CHƯƠNG TRÌNH KẾT THÚC
            while (!(pattern.matcher(cccdNumbers).find())) {
                System.out.println("----------------");
                System.out.println("So CCCD khong hop le. \nVui long nhap lai hoac 'No' de thoat.");
                System.out.print("Vui long nhap so CCCD: ");
                cccdNumbers = sc.nextLine();
                if (cccdNumbers.equals("No") || cccdNumbers.equals("no")) {
                    System.exit(1);
                }
            }
            selectAnOption(cccdNumbers);
    }
    // BẢNG CHỨC NĂNG
    private static void optionOfCCCD() {
        System.out.println("| 1. Kiem tra noi sinh");
        System.out.println("| 2. Kiem tra tuoi, gioi tinh");
        System.out.println("| 3. Kiem tra so ngau nhien");
        System.out.println("| 0. Thoat");
        System.out.print("Chuc Nang: ");
    }
    // HÀM CHỌN CHỨC NĂNG
    private static void selectAnOption(String cccdNumbers) {
        Scanner sc = new Scanner(System.in);
        optionOfCCCD();
        try {
            int option = sc.nextInt();
            while (true) {
                handleFeature(option, cccdNumbers);
                selectAnOption(cccdNumbers);
            }
        }
        catch (Exception InputMismatchException){
            System.out.println("----------------");
            System.out.println("Sai cu phap, vui long nhap lai!");
            selectAnOption(cccdNumbers);
        }
    }

    private static void checkProvince(String cccdNumbers) {
        Map<String, String> provinces = new HashMap<String, String>() {
            {
                put("001", "Hà Nội");
                put("002", "Hà Giang");
                put("004", "Cao Bằng");
                put("006", "Bắc Kạn");
                put("008", "Tuyên Quang");
                put("010", "Lào Cai");
                put("011", "Điện Biên");
                put("012", "Lai Châu");
                put("014", "Sơn La");
                put("015", "Yên Bái");
                put("017", "Hòa Bình");
                put("019", "Thái Nguyên");
                put("020", "Lạng Sơn");
                put("022", "Quảng Ninh");
                put("024", "Bắc Giang");
                put("025", "Phú Thọ");
                put("026", "Vĩnh Phúc");
                put("027", "Bắc Ninh");
                put("030", "Hải Dương");
                put("031", "Hải Phòng");
                put("033", "Hưng Yên");
                put("034", "Thái Bình");
                put("035", "Hà Nam");
                put("036", "Nam Định");
                put("037", "Ninh Bình");
                put("038", "Thanh Hóa");
                put("040", "Nghệ An");
                put("042", "Hà Tĩnh");
                put("044", "Quảng Bình");
                put("045", "Quảng Trị");
                put("046", "Thừa Thiên Huế");
                put("048", "Đà Nẵng");
                put("049", "Quảng Nam");
                put("051", "Quảng Ngãi");
                put("052", "Bình Định");
                put("054", "Phú Yên");
                put("056", "Khánh Hòa");
                put("058", "Ninh Thuận");
                put("060", "Bình Thuận");
                put("062", "Kon Tum");
                put("064", "Gia Lai");
                put("066", "Đắk Lắk");
                put("067", "Đắk Nông");
                put("068", "Lâm Đồng");
                put("070", "Bình Phước");
                put("072", "Tây Ninh");
                put("074", "Bình Dương");
                put("075", "Đồng Nai");
                put("077", "Vũng Tàu");
                put("079", "Hồ Chí Minh");
                put("080", "Long An");
                put("082", "Tiền Giang");
                put("083", "Bến Tre");
                put("084", "Trà Vinh");
                put("086", "Vĩnh Long");
                put("087", "Đồng Tháp");
                put("089", "An Giang");
                put("091", "Kiên Giang");
                put("092", "Cần Thơ");
                put("093", "Hậu Giang");
                put("094", "Sóc Trăng");
                put("095", "Bạc Liêu");
                put("096", "Cà Mau");
            }
        };

        String provinceId = cccdNumbers.substring(0, 3); // LẤY 3 KÝ TỰ BẮT TỪ VỊ TRÍ 0 ĐẾN VỊ TRÍ 2

        String province = (String) provinces.get(provinceId);
        /*
         * GET VALUE THEO KEY TƯƠNG ỨNG TRONG HASH MAP:
         * LẤY 3 KÝ TỰ ĐẦU TRONG CHUỖI STRING CCCD NHẬP VÀO
         * VD: "096" => "Cà Mau"
         */
        System.out.println("Noi Sinh: " + province); // provine == value hashmap tương ứng key
    }

    private static void checkGendernBirthyears(String cccdNumbers) {
        System.out.print("Gioi tinh | Nam sinh: ");
        int genderId = Integer.parseInt(cccdNumbers.substring(3, 4)); // LẤY KÝ TỰ TRONG STRING Ở VỊ TRÍ THỨ 3

        String gender;

        if (genderId % 2 == 0) {
            gender = "Nam ";
        } else {
            gender = "Nu ";
        }
        System.out.print(gender);
        // HASHMAP VALUE THẾ KỶ, TƯƠNG ỨNG VỚI TỪNG KEY GIỚI TÍNH
        Map<Integer, Integer> birthYears = new HashMap<Integer, Integer>() {
            {
                put(0, 1900);
                put(1, 1900);
                put(2, 2000);
                put(3, 2000);
                put(4, 2100);
                put(5, 2100);
                put(6, 2200);
                put(7, 2200);
                put(8, 2300);
                put(9, 2300);
            }
        };
        int year = Integer.parseInt(cccdNumbers.substring(4, 6)); // LẤY 2 KÝ TỰ TRONG STRING TỪ VỊ TRÍ THỨ 4 VÀ 5
        int birthYear = birthYears.get(genderId); // LẤY 2 SỐ CUỐI NĂM SINH THEO HASHMAP
        // NĂM ĐẦU THẾ KỶ + 2 SỐ CUỐI NĂM SINH = NĂM SINH TƯƠNG ỨNG TRÊN CCCD
        System.out.print("| " + (birthYear + year) + "\n");

    }
    // LẤY 6 SỐ NGẪU NHIÊN TRONG CCCD
    private static void randomNumbersOfCCCD(String cccdNumbers) {
        String randomNumbers = cccdNumbers.substring(6);

        System.out.println("So ngau nhien: " + randomNumbers);
    }

    // HÀM CHỌN TÍNH NĂNG NGƯỜI DÙNG MUỐN KIỂM TRA TRONG CCCD
    private static void handleFeature(int option, String cccdNumbers) {

        switch (option) {
            case 1:
                checkProvince(cccdNumbers); // KT NƠI SINH
                break;
            case 2:
                checkGendernBirthyears(cccdNumbers); // KT GIỚI TÍNH - NĂM SINH
                break;
            case 3:
                randomNumbersOfCCCD(cccdNumbers);// KT 6 SỐ NGẪU NHIÊN TRONG CCCD
                break;
            case 0:
                System.out.println("Cam on, hen gap lai!");
                System.exit(1);
                break;
            default:
                System.out.println("----------------");
                System.out.println("So ban nhap khong dung, vui long nhap lai");
                selectAnOption(cccdNumbers);
        }
    }
    //

    /*
    *   Tạo random 6 chữ-số bảo mật, yêu cầu người dùng nhập để xác thực
    *
     */
    private static final Random random = new SecureRandom();
    private static final String ALPHABET = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";

    private static String generateRandomString(int length) {
        StringBuffer buffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            buffer.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(buffer);
    }


}
