package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionListener;

public abstract class ModuleAction implements ActionListener {
  
  protected ModuleHandler moduleHandler = null;
  
  protected ModuleAction(ModuleHandler handler) {
    this.moduleHandler = handler;
  }
  
  public ModuleHandler getModuleHandler() {
    return moduleHandler;
  }
  
}
