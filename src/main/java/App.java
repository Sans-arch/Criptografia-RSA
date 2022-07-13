public class App {
    public static void main(String[] args) {
        Encripta encripta = new Encripta("publicKey.txt", "source.txt", "encryptedFile.txt");
        Descripta descripta = new Descripta("privateKey.txt", "encryptedFile.txt",
                "converted.txt");
    }
}
