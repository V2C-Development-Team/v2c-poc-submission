package edu.uco.cs.v2c.poc.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.SystemUtils;

import edu.uco.cs.v2c.poc.ModuleID;

public class ModuleHandler implements Runnable {
  
  public static enum ProcessType {
    JAVA_PROCESS("java"),
    NPM_PROCESS("npm"),
    PYTHON_PROCESS("python");
    
    private String bin = null;
    
    private ProcessType(String bin) {
      
      String[] pathDirs = System.getenv("PATH").split(";");
      if(SystemUtils.IS_OS_WINDOWS) {
        String[] pathExts = System.getenv("PATHEXT").split(";");
        seek:for(String pathDir : pathDirs) {
          for(String pathExt : pathExts) {
            File binary = new File(pathDir, bin + pathExt);
            if(binary.exists() && binary.canExecute()) {
              this.bin = pathDir + (pathDir.charAt(pathDir.length() - 1) == '\\' ? "" : "\\") + bin + pathExt.toLowerCase();
              break seek;
            }
          }
        }
      } else {
        for(String pathDir : pathDirs) {
          File binary = new File(pathDir, bin);
          if(binary.exists() && binary.canExecute()) {
            this.bin = pathDir + "/" + bin;
            break;
          }
        }
      }
      
      if(this.bin == null) this.bin = bin;
      System.out.println("Registered at: " + this.bin);
    }
    
    public String getBin() {
      return bin;
    }
  }
  
  private AtomicBoolean go = new AtomicBoolean();
  private AtomicReference<Process> currentProcess = new AtomicReference<>();
  private ModuleID moduleID = null;
  private String runtimeBin = null;
  private String moduleBin = null;
  private Thread thread = null;
  
  private ModuleHandler(ModuleID moduleID) {
    this.moduleID = moduleID;
    if(moduleID != null) {
      runtimeBin = moduleID.getProcessType().getBin();
      moduleBin = moduleID.getDefaultModulePath();
    }
  }
  
  public static ModuleHandler build(ModuleID moduleID) {
    ModuleHandler handler = new ModuleHandler(moduleID);
    handler.thread = new Thread(handler);
    handler.thread.setDaemon(true);
    handler.thread.start();
    return handler;
  }
  
  public String getRuntimeBin() {
    return runtimeBin;
  }
  
  public void setRuntimeBin(String runtimeBin) {
    this.runtimeBin = runtimeBin;
  }
  
  public String getModuleBin() {
    return moduleBin;
  }
  
  public void setModuleBin(String moduleBin) {
    this.moduleBin = moduleBin;
  }
  
  @Override public void run() {
    try {
      while(!thread.isInterrupted()) {
        while(!go.get()) Thread.sleep(500L);
        currentProcess.set(null);
        
        List<String> commands = new ArrayList<>();
        
        switch(moduleID.getProcessType()) {
        case JAVA_PROCESS:
          commands.add(runtimeBin);
          commands.add("-jar");
          commands.add(moduleBin);
          break;
        case NPM_PROCESS:
          commands.add(runtimeBin);
          commands.add("start");
          break;
        case PYTHON_PROCESS:
          commands.add(runtimeBin);
          commands.add(moduleBin);
          break;
        default:
          break;
        }
        
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        if(moduleID.getProcessType() == ProcessType.PYTHON_PROCESS)
          processBuilder.directory(new File(moduleBin));
        processBuilder.redirectErrorStream(true);
        
        try {
          currentProcess.set(processBuilder.start());
        } catch(IOException e) {
          e.printStackTrace();
          currentProcess.set(null);
        } finally {
          go.set(false);
        }
      }
    } catch(InterruptedException e) { }
  }
  
  public void go() {
    go.set(true);
  }
  
  public void terminate() {
    Process process = currentProcess.get();
    if(process != null) process.destroy();
  }
  
  public boolean isRunning() {
    return go.get();
  }
  
  public ModuleID getModuleID() {
    return moduleID;
  }
}
