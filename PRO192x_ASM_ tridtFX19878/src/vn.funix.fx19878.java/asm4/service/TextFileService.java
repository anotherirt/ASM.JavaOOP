package vn.funix.fx19878.java.asm4.service;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.stream.Collectors;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";

    /* HÀM ĐỌC VÀ LẤY RA DỮ LIỆU TỪ FILE CUSTOMERS.TXT */
    public static List<List<String>> ReadFile(String filePath) {
        List<List<String>> listOfLists = new ArrayList<>();
        File file = new File(filePath);
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                List<String> values = List.of(line.split(COMMA_DELIMITER));
                listOfLists.add(values);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Tep khong ton tai"); // bắt exception khi nhập không đúng đường dẫn file (chỉ nhận "store/customers.txt")
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfLists;
    }
}
