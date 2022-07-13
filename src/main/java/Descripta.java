import utils.TextChunk;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Descripta {

    String encryptedData;
    List<BigInteger> encryptedList = new ArrayList<>();
    BigInteger modulo;
    BigInteger privateKey;
    BigInteger publicKey;
    String oneLine = "";

    public Descripta(String publicKeyFile, String encryptedFileName, String destFileName)  {
        readFile(encryptedFileName);
        readKeys();

        for (BigInteger line : encryptedList) {
            BigInteger originalChunk = line.modPow(getPrivateKey(), getModulo());
            TextChunk tx = new TextChunk(originalChunk);
            oneLine += tx;
        }
        System.out.println(oneLine);
        byte[] decodedBytes = Base64.getDecoder().decode(oneLine);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
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

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }


    private void readFile(String sourceFileName) {
        StringBuilder extractedData = new StringBuilder();

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

        setEncryptedData(extractedData.toString());
    }

    private void readKeys() {
        BigInteger modulo = new BigInteger("2314320674865135737");
        BigInteger privateKey = new BigInteger("1420672095574243181");
        BigInteger publicKey = new BigInteger("101");
        setModulo(modulo);
        setPrivateKey(privateKey);
        setPublicKey(publicKey);
    }
}
