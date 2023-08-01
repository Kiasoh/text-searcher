import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    public static String PhoneNumberValidation(String phoneNumber) throws Exception {
        Pattern pattern = Pattern.compile("^09[0-9]{9}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.find())
            throw new Exception("PhoneNumber ain't it brov!");
        return phoneNumber;
    }
    public static String passwordValidation(String pass) throws Exception {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,32}$");
        Matcher matcher = pattern.matcher(pass);
        if (!matcher.find())
            throw new Exception("password ain't it brov!");
        return pass;
    }

}
