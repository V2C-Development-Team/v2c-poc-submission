package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;

import edu.uco.cs.v2c.poc.ui.ConfigurationFrame;

public class ConfigureAction extends ModuleAction {
  
  public ConfigureAction(ModuleHandler handler) {
    super(handler);
  }

  @Override public void actionPerformed(ActionEvent e) {
    System.out.printf("Hit configuration button for %1$s.\n", super.moduleHandler.getModuleID().toString());
    ConfigurationFrame configurationFrame = new ConfigurationFrame(this);
    configurationFrame.setVisible(true);
  }
  
  public void onConfigUpdate(String runtimeBin, String moduleBin) {
    System.out.printf("Got new configs for %1$s: runtime=%2$s, module=%3$s.\n",
        super.moduleHandler.getModuleID().toString(), runtimeBin, moduleBin);
  }
  
}
