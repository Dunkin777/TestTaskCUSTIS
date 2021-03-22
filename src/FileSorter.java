import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSorter {
    static void sort(String fileNameToSort) throws IOException {

        //если исходный файл больше файла памяти, это означает что мы не можем использовать промежуточные коллекции

        //создаёт временный файл
        if (!Files.exists(Path.of("temp1.txt"))) Files.createFile(Path.of("temp1.txt"));


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileNameToSort))) {
            String currentString = bufferedReader.readLine();

            //записали первую строчку
            FileWriter fileWriter = new FileWriter("temp1.txt", true);
            fileWriter.write(currentString);
            fileWriter.close();


            //проходим по исходному файлу для сортировки со 2 строки, для корректной работы
            while ((currentString = bufferedReader.readLine()) != null) {
                long indexToInsert = 1;

                BufferedReader reader = new BufferedReader(new FileReader("temp1.txt"));
                long lines = 0; //число строк временного файла
                while (reader.readLine() != null) lines++;
                reader.close();
                //получаем индекс, куда вставить текущую строку файла, каждый раз число линий временного файла увеличивается
                for (long i = 0; i <= lines - 1; i++) {
                    String lineI = Files.lines(Paths.get("temp1.txt")).skip(i).findFirst().get();
                    if (currentString.compareTo(lineI) <= 0) { //проверка, что текущая строка ещё не нашла своё место для вставки
                        break;
                    } else indexToInsert++;
                }

                //создаёт временный файл 2
                if (!Files.exists(Path.of("temp2.txt"))) Files.createFile(Path.of("temp2.txt"));

                //вставка строчки по индексу (в новый временный файл записываются данные из старого, когда линия дойдёт до индекса вставки, вставляется текущая строчка исходного файла)
                try (BufferedReader fileTemp1Reader = new BufferedReader(new FileReader("temp1.txt"));
                     FileWriter fileTemp2Writer = new FileWriter("temp2.txt")) {
                    String data;
                    long indexLine = 1;
                    while ((data = fileTemp1Reader.readLine()) != null || currentString != null) {
                        if (indexLine == indexToInsert) {
                            fileTemp2Writer.write(currentString + "\n");
                            currentString = null;
                        }
                        if (data != null) {
                            fileTemp2Writer.write(data + "\n");
                            indexLine++;
                        }
                    }
                }

                //удаляет первый временный файл
                if (Files.exists(Path.of("temp1.txt"))) Files.delete(Path.of("temp1.txt"));

                //переименовывает второй файл в первый
                File file = new File("temp1.txt");
                File file2 = new File("temp2.txt");
                if (file.exists())
                    throw new java.io.IOException("file exists");
                boolean success = file2.renameTo(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //удаляет исходный файл
        if (Files.exists(Path.of("filename.txt"))) Files.delete(Path.of("filename.txt"));
        //переименовывает временный файл в исходный
        File file = new File("temp1.txt");
        File file2 = new File("filename.txt");
        if (file2.exists())
            throw new java.io.IOException("file exists");
        boolean success = file.renameTo(file2);
    }
}
