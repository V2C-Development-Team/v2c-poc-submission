package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartAction extends ModuleAction {
  
  public StartAction(ModuleHandler handler) {
    super(handler);
  }
  
  @Override public void actionPerformed(ActionEvent e) {
    moduleHandler.go();
  }

}
