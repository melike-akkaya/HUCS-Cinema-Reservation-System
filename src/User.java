import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class User {
    private String username;
    private String password, passwordAgain;
    private Boolean ifClubMember, ifAdmin;

    User(String[] commands) {
        this.username = commands[1];
        this.password = commands[2];
        this.ifClubMember = Boolean.valueOf(commands[3]);
        this.ifAdmin = Boolean.valueOf(commands[4]);
    }

    // constructor to create new user
    User() {
    }

    private static String hashPassword(String password) {
        byte [] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte [] md5Digest = new byte [0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = hashPassword(passwordAgain);
    }

    public void setIfClubMember(Boolean ifClubMember) {
        this.ifClubMember = ifClubMember;
    }

    public void setIfAdmin(Boolean ifAdmin) {
        this.ifAdmin = ifAdmin;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public Boolean isIfClubMember() {
        return ifClubMember;
    }

    public Boolean isIfAdmin() {
        return ifAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
