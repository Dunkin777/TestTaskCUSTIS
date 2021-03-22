import java.io.IOException;
import java.util.Random;

public class MainClass {
    public static void main(String[] args) throws IOException {
        FileGenerator.generateFile(15, 1000);
        FileSorter.sort("filename.txt");
    }
}
