package com.todo.todo.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

  public static boolean isValid(String email) {
    if (email == null) {
      return false;
    }

    Pattern pattern = java.util.regex.Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

}
