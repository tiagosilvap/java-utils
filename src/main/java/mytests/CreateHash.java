package mytests;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateHash {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Long subscriptionId = 3L;
        String fakeHash = "fakeHash" + subscriptionId.toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(fakeHash.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedhash);
    }
}
