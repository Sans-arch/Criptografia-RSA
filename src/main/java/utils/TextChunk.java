package utils;

import java.math.*;

public class TextChunk {
    String stringVal; // this TextChunk as a Java String

    public TextChunk(String s) {
        stringVal = s;
    }

    public TextChunk(BigInteger n) {
        BigInteger big256 = new BigInteger("256");
        BigInteger big0 = new BigInteger("0");

        if (n.compareTo(big0) == 0) {
            stringVal = "0";
        } else {
            stringVal = "";
            while (n.compareTo(big0) > 0) {
                BigInteger[] ans = n.divideAndRemainder(big256);
                int charNum = ans[1].intValue();
                stringVal = stringVal + (char) charNum;
                n = ans[0];
            }
        }
    }

    public BigInteger bigIntValue() {
        BigInteger big256 = new BigInteger("256");
        BigInteger result = new BigInteger("0");

        for (int i = stringVal.length() - 1; i >= 0; i--) {
            result = result.multiply(big256);
            result = result.add(BigInteger.valueOf((int) stringVal.charAt(i)));
        }
        return result;
    }

    public static int blockSize(BigInteger N) {
        // value computed is log_2(N-1)
        BigInteger big1 = new BigInteger("1");
        BigInteger big2 = new BigInteger("2");

        int blockSize = 0; // result
        BigInteger temp = N.subtract(big1);
        while (temp.compareTo(big1) > 0) {
            temp = temp.divide(big2);
            blockSize++;
        }
        return blockSize / 8;
    }

    public String toString() {
        return stringVal;
    }
}