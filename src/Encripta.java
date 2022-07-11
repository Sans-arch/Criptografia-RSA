import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Scanner;

import utils.TextChunk;

public class Encripta {
  // String publicKeyFile, String sourceFileName, String destFileName

  public Encripta(String publicKeyFile, String sourceFileName, String destFileName) {
    String sourceData = readFile(sourceFileName);
    String encodedData = encodeToBase64(sourceData);

    // Ler o arquivo com chave pública
    TextChunk textChunk = new TextChunk(encodedData);
    BigInteger bg = textChunk.bigIntValue();
  }

  public Encripta(String sourceFileName) {
    readFile(sourceFileName);
  }

  private String readFile(String sourceFileName) {
    String originalData = "";
    
    try {
      Scanner sc = new Scanner(new FileReader(sourceFileName));
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        originalData += line;
      }
      sc.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return originalData;
  }

  private String readKeys(String publicKeyFile) {
  }
  
  private String encodeToBase64(String data) {
    String encodedString = Base64.getEncoder().encodeToString(data.getBytes());
    return encodedString;
  }

}
