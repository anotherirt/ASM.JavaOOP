package vn.funix.fx19878.java.asm2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class User {
    private String name;
    private String customerId;

    public User(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }
    // KIỂM TRA SỐ CCCD NHẬP VÀO CÓ ĐÚNG ĐỊNH DẠNG
    public static boolean validateCustomerId(String idNumbers){

        Pattern pattern = Pattern.compile("^\\d{12}$");

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

        String provinceId = idNumbers.substring(0, 3); // LẤY 3 KÝ TỰ BẮT TỪ VỊ TRÍ 0 ĐẾN VỊ TRÍ 2
        boolean checkProvince = true;
        if(provinces.get(provinceId) == null){
                return checkProvince = false;
        }

        return pattern.matcher(idNumbers).find();

    }
    public void setCustomerId(String customerId) {
        if(validateCustomerId(customerId)){
            this.customerId = customerId;
        }
    }

}
