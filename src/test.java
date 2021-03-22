import java.io.*;

public class test {
    public static void main(String[] args) throws IOException {

        File file = new File("temp1.txt");
        File file2 = new File("temp2.txt");
        if (file2.exists())
            throw new java.io.IOException("file exists");
        boolean success = file.renameTo(file2);
    }
}
