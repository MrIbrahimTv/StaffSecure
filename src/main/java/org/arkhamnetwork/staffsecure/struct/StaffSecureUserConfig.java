/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.struct;

import java.io.File;
import java.io.IOException;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author devan_000
 */
public class StaffSecureUserConfig {

      private final FileConfiguration configFile;

      @Getter
      private final File playerConfigFile;
      @Getter
      private String playerIP;
      @Getter
      private String encryptedPassword;
      @Getter
      private boolean loggedIntoLastLoggedIp;

      public StaffSecureUserConfig(FileConfiguration config, File playerConfigFile) {
            this.configFile = config;
            this.playerIP = config.getString("lastloggedip");
            this.encryptedPassword = config.getString("password");
            this.loggedIntoLastLoggedIp = config.getBoolean("loggedintolastloggedip");
            this.playerConfigFile = playerConfigFile;
      }

      public void setIP(String ip) {
            this.playerIP = ip;
            configFile.set("lastloggedip", ip);
            try {
                  configFile.save(playerConfigFile);
            } catch (IOException ex) {
            }
      }

      public void setPassword(String encryptedPassword) {
            this.encryptedPassword = encryptedPassword;
            configFile.set("password", encryptedPassword);
            try {
                  configFile.save(playerConfigFile);
            } catch (IOException ex) {
            }
      }

      public void setLoggedInToLastIP(boolean value) {
            this.loggedIntoLastLoggedIp = value;
            configFile.set("loggedintolastloggedip", value);
            try {
                  configFile.save(playerConfigFile);
            } catch (IOException ex) {
            }
      }
}
