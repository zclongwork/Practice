import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        try {
            // 读取 a.txt 和 b.txt 文件
            List<String> aLines = readLines("/Users/zcl/Downloads/test/a.txt");
            List<String> bLines = readLines("/Users/zcl/Downloads/test/b.txt");

            // 创建输出文件 c.txt
            File cFile = new File("/Users/zcl/Downloads/test/c.txt");
            FileWriter cWriter = new FileWriter(cFile);

            boolean aInb = false;
            // 遍历 b.txt 的每一行,检查是否在 a.txt 中存在
            for (String bLine : bLines) {


                for(String aLine : aLines) {
                    if (bLine.trim().contains(aLine)) {
                        aInb = true;
                    }
                }


                if (aInb) {
//                    cWriter.write(bLine + "尼日\n");
                    cWriter.write("尼日\n");
                } else {
                    cWriter.write("\n");
                }
                aInb = false;
            }

            cWriter.flush();
            cWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
