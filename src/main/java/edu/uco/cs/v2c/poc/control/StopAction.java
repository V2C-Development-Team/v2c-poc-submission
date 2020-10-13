package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;

public class StopAction extends ModuleAction {
  
  public StopAction(ModuleHandler handler) {
    super(handler);
  }
  
  @Override public void actionPerformed(ActionEvent e) {
    moduleHandler.go();
  }
  
}
