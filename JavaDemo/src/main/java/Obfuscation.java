import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obfuscation {
//    private static final String[] NEED_GENERAL_LANGUAGE = {"i", "I", "1", "l"};
//    private static final String[] NEED_GENERAL_LANGUAGE = {"0", "O", "o"};
//    private static final String[] NEED_GENERAL_LANGUAGE = {"B", "8", "6"};
    private static final String[] NEED_GENERAL_LANGUAGE = {"m", "M", "3", "8"};



    private static final int LENGTH = 500;
    private static final String RES_PATH = "/Users/zcl/Downloads/output_dict.txt";

    public static void main(String[] args) {
        int count = 0;
        int repeatCount = 0;
        List<String> list = new ArrayList<>();
        Random random = new Random();

        while (count < LENGTH) {
//            int width = random.nextInt(7) + 4;
            int width = random.nextInt(2) + 5;
            int i = 0;
            count++;

            StringBuilder temp = new StringBuilder();
            while (i < width) {
                i++;
                temp.append(NEED_GENERAL_LANGUAGE[random.nextInt(NEED_GENERAL_LANGUAGE.length)]);
            }

            System.out.println("当前字母: " + temp.toString());

            if (list.contains(temp.toString())) {
                repeatCount++;
                System.out.println("重复字母: " + temp.toString());
                if (repeatCount == 100) {
                    System.out.println("连续重复次数超过100次 已达到最大行数 无法继续生成");
                    break;
                }
            } else {
                list.add(temp.toString());
                repeatCount = 0;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RES_PATH))) {
            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
