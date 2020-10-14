package edu.uco.cs.v2c.poc.control;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class StartAction extends ModuleAction {
  
  public StartAction(ModuleHandler handler) {
    super(handler);
  }
  
  @Override public void actionPerformed(ActionEvent e) {
    moduleHandler.go();
  }

}
