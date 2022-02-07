package com.util.crypto;


public interface PasswordEncoder {

    String encode(CharSequence password);

    boolean matches(CharSequence password, String encodedPassword);

}