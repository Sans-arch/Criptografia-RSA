import utils.TextChunk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Encripta {

  String originalData;
  String encodedData;
  BigInteger modulo;
  BigInteger publicKey;

  public Encripta(String keysFileName, String sourceFileName, String destFileName)  {
    readFile(sourceFileName);
    encodeToBase64();

    readPublicKey(keysFileName);

    int chunkSize = TextChunk.blockSize(modulo);
    String[] arrayChunks = splitToNChar(getEncodedData(), chunkSize);
    List<BigInteger> encryptedChunks = new ArrayList<>();

    try {
      File encryptedFile = new File("src/main/resources/" + destFileName);
      FileWriter encryptedFileWriter = new FileWriter(encryptedFile);
      boolean isFileCreated = encryptedFile.createNewFile();

      if (isFileCreated) {
        System.out.println("Arquivo criado com sucesso!");
      }

      for (String chunk : arrayChunks) {
        TextChunk textChunk = new TextChunk(chunk);
        BigInteger binaryChunk = textChunk.bigIntValue();
        encryptedChunks.add(binaryChunk.modPow(getPublicKey(), getModulo()));
      }

      for (BigInteger encryptedChunk : encryptedChunks) {
        encryptedFileWriter.write(encryptedChunk + "\n");
      }

      encryptedFileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String[] splitToNChar(String text, int size) {
    List<String> parts = new ArrayList<>();

    int length = text.length();
    for (int i = 0; i < length; i += size) {
      parts.add(text.substring(i, Math.min(length, i + size)));
    }
    return parts.toArray(new String[0]);
  }

  public String getEncodedData() {
    return encodedData;
  }

  public void setEncodedData(String encodedData) {
    this.encodedData = encodedData;
  }

  public String getOriginalData() {
    return originalData;
  }

  public void setOriginalData(String originalData) {
    this.originalData = originalData;
  }

  public BigInteger getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(BigInteger publicKey) {
    this.publicKey = publicKey;
  }

  public BigInteger getModulo() {
    return modulo;
  }

  public void setModulo(BigInteger modulo) {
    this.modulo = modulo;
  }

  private void readPublicKey(String keysFileName) {
    try {
      File publicKeyFile = new File("src/main/resources/" + keysFileName);
      Scanner fileReader = new Scanner(publicKeyFile);
      List<String> fileLines = new ArrayList<>();

      while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        fileLines.add(data);
      }
      fileReader.close();

      setModulo(new BigInteger(fileLines.get(0)));
      setPublicKey(new BigInteger(fileLines.get(1)));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readFile(String sourceFileName) {
    StringBuilder extractedData = new StringBuilder();

    try {
      File source = new File("src/main/resources/" + sourceFileName);
      Scanner sc = new Scanner(source);

      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        extractedData.append(line).append('\n');
      }

      sc.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    setOriginalData(extractedData.toString());
  }

  private void encodeToBase64() {
    setEncodedData(Base64.getEncoder().encodeToString(getOriginalData().getBytes()));
  }
}
