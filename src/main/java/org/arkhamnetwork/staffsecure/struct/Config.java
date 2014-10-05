/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.struct;

import lombok.Getter;

/**
 *
 * @author devan_000
 */
public class Config {

      @Getter
      private final String messagePrefix;
      @Getter
      private final boolean motdEnabled;
      @Getter
      private final boolean forceLoginOnRelogIfIpChange;
      @Getter
      private final boolean forceLoginOnRelog;

      public Config(String messagePrefix, boolean motdEnabled, boolean forceLoginOnRelogIfIpChange, boolean forceLoginOnRelog) {
            this.messagePrefix = messagePrefix;
            this.motdEnabled = motdEnabled;
            this.forceLoginOnRelogIfIpChange = forceLoginOnRelogIfIpChange;
            this.forceLoginOnRelog = forceLoginOnRelog;
      }

}
