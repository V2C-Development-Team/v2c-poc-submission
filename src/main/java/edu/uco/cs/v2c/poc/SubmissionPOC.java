package edu.uco.cs.v2c.poc;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import edu.uco.cs.v2c.poc.control.ModuleConfigAction;
import edu.uco.cs.v2c.poc.control.ModuleHandler;
import edu.uco.cs.v2c.poc.control.StartAction;
import edu.uco.cs.v2c.poc.control.StopAction;
import edu.uco.cs.v2c.poc.control.TunnelConfigAction;
import edu.uco.cs.v2c.poc.net.Tunnel;
import edu.uco.cs.v2c.poc.ui.LandingFrame;
import edu.uco.cs.v2c.poc.ui.ModuleComponent;

public class SubmissionPOC {
  
  private static Set<ModuleHandler> handlers = new HashSet<>();
  
  public static void main(String[] args) {
    LandingFrame landingFrame = new LandingFrame();
    
    for(ModuleID moduleID : ModuleID.values()) {
      if(moduleID.getProcessType() == null) continue;
      ModuleComponent moduleComponent = landingFrame.getModule(moduleID);
      ModuleHandler moduleHandler = ModuleHandler.build(landingFrame.getHomeComponent(), moduleComponent, moduleID);
      handlers.add(moduleHandler);
      
      JButton startButton = moduleComponent.addButton("START MODULE", new StartAction(moduleHandler));
      moduleHandler.addButtonToEnableOnDyingProcess(startButton);
      JButton stopButton = moduleComponent.addButton("STOP MODULE", new StopAction(moduleHandler));
      stopButton.setEnabled(false);
      moduleHandler.addButtonToEnableOnLivingProcess(stopButton);
      moduleComponent.addButton("CONFIGURE", new ModuleConfigAction(moduleHandler));
      
      if(moduleID.hasTunnel()) {
        Tunnel tunnel = new Tunnel();
        moduleComponent.addButton("TUNNEL", new TunnelConfigAction(moduleHandler, tunnel));
        moduleHandler.setTunnel(tunnel);
      }
      
      landingFrame.getHomeComponent().addModuleHandler(moduleHandler);
    }
    
    landingFrame.addWindowListener(new WindowListener() {

      @Override public void windowOpened(WindowEvent e) { }

      @Override public void windowClosing(WindowEvent e) {
        for(ModuleHandler handler : handlers)
          handler.terminate();
      }

      @Override public void windowClosed(WindowEvent e) { }

      @Override public void windowIconified(WindowEvent e) { }

      @Override public void windowDeiconified(WindowEvent e) { }

      @Override public void windowActivated(WindowEvent e) { }

      @Override public void windowDeactivated(WindowEvent e) { }
      
    });
    
  }
  
}
