package edu.uco.cs.v2c.poc;

import edu.uco.cs.v2c.poc.control.ModuleHandler.ProcessType;

public enum ModuleID {
  HOME,
  DISPATCHER(ProcessType.JAVA_PROCESS),
  RECOGNIZER(ProcessType.PYTHON_PROCESS),
  DESKTOP_CONTROLLER(ProcessType.JAVA_PROCESS),
  DASHBOARD_BACKEND(ProcessType.JAVA_PROCESS),
  DASHBOARD_FRONTEND(ProcessType.NPM_PROCESS);
  
  private String name = null;
  private ProcessType processType = null;
  
  private ModuleID() {
    this(null);
  }
  
  private ModuleID(ProcessType processType) {
    this.processType = processType;
    String[] tokens = name().split("_");
    StringBuilder sb = new StringBuilder();
    for(String token : tokens) {
      if(token.length() > 0) sb.append(Character.toUpperCase(token.charAt(0)));
      if(token.length() > 1) sb.append(token.substring(1, token.length()).toLowerCase());
      sb.append(' ');
    }
    name = sb.toString().strip();
  }
  
  @Override public String toString() {
    return name;
  }
  
  public ProcessType getProcessType() {
    return processType;
  }
}