package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mag. Stefan Hagmann
 */
public class MACFinder {

  private Pattern pattern;
  private Matcher matcher;

  private static final String MAC_PATTERN
          = "([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})";

  public MACFinder() {
    pattern = Pattern.compile(MAC_PATTERN);
  }

  /**
   * Validate MAC address with regular expression
   *
   * @param str MAC address for validation
   * @return true valid ip address, false invalid ip address
   */
  public boolean validate(final String str) {
    matcher = pattern.matcher(str);
    return matcher.matches();
  }
  
  public String find(final String str) {
    matcher = pattern.matcher(str);
    if (matcher.find()){
      return matcher.group();
    }else{
      return null;
    }
  }

}
