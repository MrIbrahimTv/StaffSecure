/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author devan_000
 */
public class EncryptionUtils {

      public static String getMD5(String value) {
            try {
                  MessageDigest md = MessageDigest.getInstance("MD5");
                  md.update(value.getBytes(), 0, value.length());
                  return new BigInteger(1, md.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
            }
            return value;
      }

}
