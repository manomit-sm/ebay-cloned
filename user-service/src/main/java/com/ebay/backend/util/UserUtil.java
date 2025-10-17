package com.ebay.backend.util;

import java.security.SecureRandom;

public class UserUtil {
    public static int getEmailVerificationCode() {
        SecureRandom secureRandom = new SecureRandom();
        return 100000 + secureRandom.nextInt(900000);
    }
}
