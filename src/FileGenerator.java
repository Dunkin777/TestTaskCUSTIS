import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileGenerator {
    static void generateFile(int stringMaxLength, long maxFileLength) throws IOException {
        try {
            File myObj = new File("filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter("filename.txt")) {
            for (long i = 0; i < maxFileLength; i++) {
                int leftLimit = 48; // numeral '0'
                int rightLimit = 122; // letter 'z'
                Random random = new Random();

                String generatedString = random.ints(leftLimit, rightLimit + 1)
                        .filter(s -> (s <= 57 || s >= 97))
                        .limit(random.nextInt(stringMaxLength + 1) + 1)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

                fileWriter.write(generatedString + "\n");
            }
        }
    }
}
