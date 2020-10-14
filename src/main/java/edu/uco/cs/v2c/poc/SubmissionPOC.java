package edu.uco.cs.v2c.poc;

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
      moduleComponent.addButton("START", new StartAction(moduleHandler));
      moduleComponent.addButton("STOP", new StopAction(moduleHandler));
      moduleComponent.addButton("CONFIGURE", new ConfigureAction(moduleHandler));
    }
  }
  
}
