package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;

import edu.uco.cs.v2c.poc.ui.ModuleConfigFrame;

public class ModuleConfigAction extends ModuleAction {
  
  private ModuleConfigFrame moduleConfigFrame = null;
  
  public ModuleConfigAction(ModuleHandler handler) {
    super(handler);
  }

  @Override public void actionPerformed(ActionEvent e) {
    if(moduleConfigFrame == null)
      moduleConfigFrame = new ModuleConfigFrame(this);
    moduleConfigFrame.setVisible(true);
  }
  
  public void onConfigUpdate(String runtimeBin, String moduleBin) {
    // TODO maybe do some validation and verification here
    
    moduleHandler.setRuntimeBin(runtimeBin);
    moduleHandler.setModuleBin(moduleBin);
  }
  
}
