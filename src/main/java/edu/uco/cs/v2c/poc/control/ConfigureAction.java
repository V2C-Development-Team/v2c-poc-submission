package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;

import edu.uco.cs.v2c.poc.ui.ConfigurationFrame;

public class ConfigureAction extends ModuleAction {
  
  public ConfigureAction(ModuleHandler handler) {
    super(handler);
  }

  @Override public void actionPerformed(ActionEvent e) {
    ConfigurationFrame configurationFrame = new ConfigurationFrame(this);
    configurationFrame.setVisible(true);
  }
  
  public void onConfigUpdate(String runtimeBin, String moduleBin) {
    // TODO maybe do some validation and verification here
    
    moduleHandler.setRuntimeBin(runtimeBin);
    moduleHandler.setModuleBin(moduleBin);
  }
  
}
