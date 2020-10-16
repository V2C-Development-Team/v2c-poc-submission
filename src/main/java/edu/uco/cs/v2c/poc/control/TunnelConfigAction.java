package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;
import java.io.File;

import edu.uco.cs.v2c.poc.net.Tunnel;
import edu.uco.cs.v2c.poc.ui.TunnelConfigFrame;

public class TunnelConfigAction extends ModuleAction {
  
  private Tunnel tunnel = null;
  private TunnelConfigFrame tunnelConfigFrame = null;
  
  public TunnelConfigAction(ModuleHandler handler, Tunnel tunnel) {
    super(handler);
    this.tunnel = tunnel;
  }
  
  @Override public void actionPerformed(ActionEvent e) {
    if(tunnelConfigFrame == null)
      tunnelConfigFrame = new TunnelConfigFrame(this);
    tunnelConfigFrame.setVisible(true);
  }
  
  public boolean onConfigUpdate(String localPort, String remoteHost,
      String internalHost, String internalPort, String username,
      String keyfilePath, String keyfilePassword) {
    
    try {
      int lPort = Integer.parseInt(localPort);
      int iPort = Integer.parseInt(internalPort);
      File kFile = new File(keyfilePath);
      
      if(!kFile.isFile() || !kFile.canRead())
        return false;
      
      tunnel.setLocalPort(lPort)
          .setRemoteHost(remoteHost)
          .setInternalHost(internalHost)
          .setInternalPort(iPort)
          .setKeyfile(kFile, keyfilePassword.isBlank() ? null : keyfilePassword);
      
      return true;
      
    } catch(Exception e) { }
    
    return false;
  }
  
  public void setEnabled(boolean enabled) {
    tunnel.setEnabled(enabled);
  }
  
}
