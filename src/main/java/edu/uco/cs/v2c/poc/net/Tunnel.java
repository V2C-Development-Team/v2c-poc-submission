/*
 * Copyright (c) 2020 Caleb L. Power, Everistus Akpabio, Rashed Alrashed,
 * Nicholas Clemmons, Jonathan Craig, James Cole Riggall, and Glen Mathew.
 * All rights reserved. Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.uco.cs.v2c.poc.net;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Tunnel {

  private boolean enabled = false;
  private int localPort = -1;
  private int internalPort = -1;
  private int remotePort = -1;
  private JSch jsch = null;
  private Session session = null;
  private String keyfile = null;
  private String internalHost = null;
  private String remoteHost = null;
  private String username = null;
  private String keyfilePassword = null;
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public Tunnel setEnabled(boolean enabled) {
    this.enabled = enabled;
    return this;
  }
  
  public Tunnel setRemoteHost(String remoteHost) {
    this.remoteHost = remoteHost;
    return this;
  }
  
  public Tunnel setRemotePort(int remotePort) {
    this.remotePort = remotePort;
    return this;
  }
  
  public Tunnel setUsername(String username) {
    this.username = username;
    return this;
  }
  
  public Tunnel setKeyfile(String keyfile, String keyfilePassword) {
    this.keyfile = keyfile;
    this.keyfilePassword = keyfilePassword;
    return this;
  }
  
  public Tunnel setLocalPort(int localPort) {
    this.localPort = localPort;
    return this;
  }
  
  public Tunnel setInternalHost(String internalHost) {
    this.internalHost = internalHost;
    return this;
  }
  
  public Tunnel setInternalPort(int internalPort) {
    this.internalPort = internalPort;
    return this;
  }
  
  public Tunnel spinUp() {
    if(jsch != null) return this;
    
    try {
      jsch = new JSch();
      
      if(keyfilePassword == null) jsch.addIdentity(keyfile);
      else jsch.addIdentity(keyfile, keyfilePassword);
      
      session = jsch.getSession(username, remoteHost, remotePort);
      
      Properties properties = new Properties();
      properties.put("StrictHostKeyChecking", "no");
      session.setConfig(properties);
      
      session.connect();
      session.setPortForwardingL(localPort, internalHost, internalPort);
      
      System.out.println("Tunnel successfully established.");
      
    } catch(Exception e) {
      System.err.printf("Some exception was thrown when spinning up a tunnel to %1$s: %2$s\n", remoteHost, e.getMessage());
      e.printStackTrace();
    }
    
    return null;
  }
  
  public Tunnel spinDown() {
    if(session != null) session.disconnect();
    session = null;
    jsch = null;
    
    return this;
  }
  
}
