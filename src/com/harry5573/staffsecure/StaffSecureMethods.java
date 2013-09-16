/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Harry5573
 */
public class StaffSecureMethods {

    public static String getMD5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes(), 0, value.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            //Dont Need ATM - Harry5573
        }
        return value;
    }
}
