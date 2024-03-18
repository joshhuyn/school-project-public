package com.school.utils;

import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginUtils
{
    public String hashPassword(String password, String salt)
    {
        String passHash = null;

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = java.util.Base64.getEncoder();

            passHash = enc.encodeToString(hash);
        }
        catch(InvalidKeySpecException | NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return passHash;
    }
}
