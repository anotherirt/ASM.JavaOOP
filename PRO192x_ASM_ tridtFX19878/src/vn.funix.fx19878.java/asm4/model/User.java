package vn.funix.fx19878.java.asm4.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class User implements Serializable {
    private String name;
    private String customerId;

    public User() {
    }

    public User(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
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

    /* HÀM KIỂM TRA SỐ CCCD NHẬP VÀO CÓ HỢP LỆ */
    public static boolean validateCustomerId(String idNumbers) {
        Pattern pattern = Pattern.compile("^\\d{12}$");
        Map<String, String> provinces = new HashMap<String, String>() {
            {
                put("001", "Hà N?i");
                put("002", "Hà Giang");
                put("004", "Cao B?ng");
                put("006", "B?c K?n");
                put("008", "Tuyên Quang");
                put("010", "Lào Cai");
                put("011", "?i?n Biên");
                put("012", "Lai Châu");
                put("014", "S?n La");
                put("015", "Yên Bái");
                put("017", "Hòa Bình");
                put("019", "Thái Nguyên");
                put("020", "L?ng S?n");
                put("022", "Qu?ng Ninh");
                put("024", "B?c Giang");
                put("025", "Phú Th?");
                put("026", "V?nh Phúc");
                put("027", "B?c Ninh");
                put("030", "H?i D??ng");
                put("031", "H?i Phòng");
                put("033", "H?ng Yên");
                put("034", "Thái Bình");
                put("035", "Hà Nam");
                put("036", "Nam ??nh");
                put("037", "Ninh Bình");
                put("038", "Thanh Hóa");
                put("040", "Ngh? An");
                put("042", "Hà T?nh");
                put("044", "Qu?ng Bình");
                put("045", "Qu?ng Tr?");
                put("046", "Th?a Thiên Hu?");
                put("048", "?à N?ng");
                put("049", "Qu?ng Nam");
                put("051", "Qu?ng Ngãi");
                put("052", "Bình ??nh");
                put("054", "Phú Yên");
                put("056", "Khánh Hòa");
                put("058", "Ninh Thu?n");
                put("060", "Bình Thu?n");
                put("062", "Kon Tum");
                put("064", "Gia Lai");
                put("066", "??k L?k");
                put("067", "??k Nông");
                put("068", "Lâm ??ng");
                put("070", "Bình Ph??c");
                put("072", "Tây Ninh");
                put("074", "Bình D??ng");
                put("075", "??ng Nai");
                put("077", "V?ng Tàu");
                put("079", "H? Chí Minh");
                put("080", "Long An");
                put("082", "Ti?n Giang");
                put("083", "B?n Tre");
                put("084", "Trà Vinh");
                put("086", "V?nh Long");
                put("087", "??ng Tháp");
                put("089", "An Giang");
                put("091", "Kiên Giang");
                put("092", "C?n Th?");
                put("093", "H?u Giang");
                put("094", "Sóc Tr?ng");
                put("095", "B?c Liêu");
                put("096", "Cà Mau");
            }
        };

        String provinceId = idNumbers.substring(0, 3);
        if (provinces.get(provinceId) != null) {
            return pattern.matcher(idNumbers).find();
        }
        throw new CustomerIdNotValidException("ID khong hop le");
    }

    public void setCustomerId(String customerId) {
        if (validateCustomerId(customerId)) {
            this.customerId = customerId;
        }
    }
}
