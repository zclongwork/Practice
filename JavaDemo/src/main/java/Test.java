import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String inputFileName = "/Users/zcl/code/nga/appDiscount/src/main/java/com/duit/dompet/services/CollectService.java"; // 替换为您的输入文件名
        String outputFileName = "/Users/zcl/Downloads/output.txt "; // 替换为您的输出文件名

        List<String> inputDirectories = new ArrayList<>();

        inputDirectories.add("/Users/zcl/code/nga/appDiscount/src/main/java/com/duit/dompet/services/CollectService.java");
        inputDirectories.add("/Users/zcl/code/nga/basicLiveness/src/main/java/ai/advance/liveness/sdk/services/LivenessService.java");
        inputDirectories.add("/Users/zcl/code/nga/bizMine/src/main/java/com/duit/dompet/mine/services/DeleteAccountService.kt");
        inputDirectories.add("/Users/zcl/code/nga/bizMine/src/main/java/com/duit/dompet/mine/services/MineService.java");
        inputDirectories.add("/Users/zcl/code/nga/bizMine/src/main/java/com/duit/dompet/mine/services/RefundService.java");
        inputDirectories.add("/Users/zcl/code/nga/bizLoan/src/main/java/com/duit/dompet/loan/services/AdService.java");
        inputDirectories.add("/Users/zcl/code/nga/bizLoan/src/main/java/com/duit/dompet/loan/services/LoanService.java");
        inputDirectories.add("/Users/zcl/code/nga/bizLogin/src/main/java/com/duit/dompet/login/services/LoginService.java");
        inputDirectories.add("/Users/zcl/code/nga/commonLib/src/main/java/com/geecloud/vn/commonres/services/AppConfigService.java");
        inputDirectories.add("/Users/zcl/code/nga/commonLib/src/main/java/com/geecloud/vn/commonres/services/EquityService.java");
        inputDirectories.add("/Users/zcl/code/nga/commonLib/src/main/java/com/geecloud/vn/commonres/services/FileUploadUseCase.kt");
        inputDirectories.add("/Users/zcl/code/nga/commonLib/src/main/java/com/geecloud/vn/commonres/services/UploadService.java");
        inputDirectories.add("/Users/zcl/code/nga/commonLib/src/main/java/com/geecloud/vn/commonres/services/UserCouponsService.java");


        List<String> postContentList = new ArrayList<>();

        try {
            for (String inputDirectory : inputDirectories) {
                Path inputDir = Paths.get(inputDirectory);
                Files.walk(inputDir)
                        .filter(Files::isRegularFile)
                        .forEach(file -> readPostContentFromFile(file.toString(), postContentList));
            }

            writePostContentToFile(outputFileName, postContentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readPostContentFromFile(String fileName, List<String> postContentList) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
//                if (line.contains("@POST(")) {
//                    postContentList.add(line);
//                    // 您可以在这里对 @POST( 开头的内容进行其他处理
//                    processPostContent(line);
//                }

                if (line.contains("@POST(")) {
                    int startIndex = line.indexOf("\"") + 1;
                    int endIndex = line.lastIndexOf("\"");
                    if (startIndex != -1 && endIndex != -1) {
                        String postContent = line.substring(startIndex, endIndex);
                        postContentList.add(postContent);
                        // 您可以在这里对提取的内容进行其他处理
                        processPostContent(postContent);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writePostContentToFile(String outputFileName, List<String> postContentList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String postContent : postContentList) {
                bw.write(postContent);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processPostContent(String postContent) {
        // 在这里编写处理 @POST( 开头内容的逻辑
        System.out.println("Processing post content: " + postContent);
    }
}
