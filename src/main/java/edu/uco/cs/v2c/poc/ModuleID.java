package edu.uco.cs.v2c.poc;

import edu.uco.cs.v2c.poc.control.ModuleHandler.ProcessType;

public enum ModuleID {
  DISPATCHER(ProcessType.JAVA_PROCESS, "v2c-dispatcher/build/libs/v2c-dispatcher.jar", false),
  RECOGNIZER(ProcessType.PYTHON_PROCESS, "v2c-recognizer/Recognizer/speech.py", false),
  DESKTOP_CONTROLLER(ProcessType.JAVA_PROCESS, "v2c-desktop-controller-linux/build/libs/v2c-desktop-controller-linux.jar", false),
  DASHBOARD_BACKEND(ProcessType.JAVA_PROCESS, "v2c-dashboard-backend/build/libs/v2c-dashboard-backend.jar", true),
  DASHBOARD_FRONTEND(ProcessType.NPM_PROCESS, "v2c-dashboard", false);
  
  private boolean hasTunnel = false;
  private String name = null;
  private String defaultModulePath = null;
  private ProcessType processType = null;
  
  private ModuleID(ProcessType processType, String defaultModulePath, boolean hasTunnel) {
    this.hasTunnel = hasTunnel;
    this.processType = processType;
    this.defaultModulePath = defaultModulePath;
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
  
  public boolean hasTunnel() {
    return hasTunnel;
  }
  
  public ProcessType getProcessType() {
    return processType;
  }
  
  public String getDefaultModulePath() {
    return defaultModulePath;
  }
}