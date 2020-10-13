package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;

public class ConfigureAction extends ModuleAction {
  
  public ConfigureAction(ModuleHandler handler) {
    super(handler);
  }

  @Override public void actionPerformed(ActionEvent e) {
    System.out.printf("Hit configuration button for %1$s.\n", super.moduleHandler.getModuleID().toString());
  }
  
}
