package edu.uco.cs.v2c.poc.net;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.LocalPortForwarder;

public class Tunnel {

  private boolean enabled = false;
  private int localPort = -1;
  private int internalPort = -1;
  private Connection connection = null;
  private File keyfile = null;
  private LocalPortForwarder forwarder = null;
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
  
  public Tunnel setUsername(String username) {
    this.username = username;
    return this;
  }
  
  public Tunnel setKeyfile(File keyfile, String keyfilePassword) {
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
    try {
      connection = new Connection(remoteHost);
      connection.connect();
      if(!connection.authenticateWithPublicKey(username, keyfile, keyfilePassword))
        throw new IOException("SSH authentication failed for " + remoteHost);
      
      forwarder = connection.createLocalPortForwarder(localPort, internalHost, internalPort); 
    } catch(IOException e) {
      System.err.printf("Some exception was thrown when spinning up a tunnel to %1$s: %3$s\n", remoteHost, e.getMessage());
    }
    
    return null;
  }
  
  public Tunnel spinDown() {
    try {
      if(forwarder != null) forwarder.close();
      if(connection != null) connection.close();
    } catch(IOException e) { }
    
    forwarder = null;
    connection = null;
    
    return this;
  }
  
}
