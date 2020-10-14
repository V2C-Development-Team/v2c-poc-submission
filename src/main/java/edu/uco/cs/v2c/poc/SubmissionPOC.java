package edu.uco.cs.v2c.poc;

import javax.swing.JButton;

import edu.uco.cs.v2c.poc.control.ConfigureAction;
import edu.uco.cs.v2c.poc.control.ModuleHandler;
import edu.uco.cs.v2c.poc.control.StartAction;
import edu.uco.cs.v2c.poc.control.StopAction;
import edu.uco.cs.v2c.poc.ui.LandingFrame;
import edu.uco.cs.v2c.poc.ui.ModuleComponent;

public class SubmissionPOC {
  
  public static void main(String[] args) {
    LandingFrame landingFrame = new LandingFrame();
    for(ModuleID moduleID : ModuleID.values()) {
      if(moduleID.getProcessType() == null) continue;
      ModuleComponent moduleComponent = landingFrame.getModule(moduleID);
      ModuleHandler moduleHandler = ModuleHandler.build(moduleComponent, moduleID);
      JButton startButton = moduleComponent.addButton("START", new StartAction(moduleHandler));
      moduleHandler.addButtonToEnableOnDyingProcess(startButton);
      JButton stopButton = moduleComponent.addButton("STOP", new StopAction(moduleHandler));
      stopButton.setEnabled(false);
      moduleHandler.addButtonToEnableOnLivingProcess(stopButton);
      moduleComponent.addButton("CONFIGURE", new ConfigureAction(moduleHandler));
    }
  }
  
}
