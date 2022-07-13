import utils.TextChunk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Descripta {

    List<BigInteger> encryptedList = new ArrayList<>();
    BigInteger modulo;
    BigInteger privateKey;
    String decryptedData = "";

    public Descripta(String keysFileName, String encryptedFileName, String destFileName)  {
        readFile(encryptedFileName);
        readPrivateKey(keysFileName);

        for (BigInteger line : encryptedList) {
            BigInteger originalChunk = line.modPow(getPrivateKey(), getModulo());
            TextChunk tx = new TextChunk(originalChunk);
            decryptedData += tx;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(decryptedData);
        String decodedString = new String(decodedBytes);

        try {
            File encryptedFile = new File("src/main/resources/" + destFileName);
            FileWriter encryptedFileWriter = new FileWriter(encryptedFile);
            boolean isFileCreated = encryptedFile.createNewFile();

            if (isFileCreated) {
                System.out.println("Arquivo criado com sucesso!");
            }

            encryptedFileWriter.write(decodedString);
            encryptedFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BigInteger getModulo() {
        return modulo;
    }

    public void setModulo(BigInteger modulo) {
        this.modulo = modulo;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    private void readFile(String sourceFileName) {
        try {
            File source = new File("src/main/resources/" + sourceFileName);
            Scanner sc = new Scanner(source);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                encryptedList.add(new BigInteger(line));
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readPrivateKey(String keysFileName) {
        try {
            File privateKeyFile = new File("src/main/resources/" + keysFileName);
            Scanner fileReader = new Scanner(privateKeyFile);
            List<String> fileLines = new ArrayList<>();

            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                fileLines.add(data);
            }
            fileReader.close();

            setModulo(new BigInteger(fileLines.get(0)));
            setPrivateKey(new BigInteger(fileLines.get(1)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
