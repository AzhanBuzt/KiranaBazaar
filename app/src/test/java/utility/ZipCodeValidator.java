package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipCodeValidator {


    //note in this expression below, each back slash is an escape character, so
    //the regular expression should be "\b[0-9]{5}(?:-[0-9]{4})?\b"
    final static String zipcodePattern="\\b[0-9]{5}(?:-[0-9]{4})?\\b";
    private static Pattern pattern;
    private static Matcher matcher;
    public ZipCodeValidator()
    {
        pattern=Pattern.compile(zipcodePattern);
    }

    public boolean validate (String ZipCode)
    {
        matcher=pattern.matcher(ZipCode);
        return matcher.matches();
    }

}
