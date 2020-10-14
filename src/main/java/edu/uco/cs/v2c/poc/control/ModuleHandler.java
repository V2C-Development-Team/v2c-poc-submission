package edu.uco.cs.v2c.poc.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JButton;

import org.apache.commons.lang3.SystemUtils;

import edu.uco.cs.v2c.poc.ModuleID;
import edu.uco.cs.v2c.poc.ui.ModuleComponent;

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
    }
    
    public String getBin() {
      return bin;
    }
  }
  
  private AtomicBoolean go = new AtomicBoolean();
  private AtomicReference<Process> currentProcess = new AtomicReference<>();
  private ModuleComponent moduleComponent = null;
  private ModuleID moduleID = null;
  private Set<JButton> enabledButtonsOnLivingProcess = new HashSet<>();
  private Set<JButton> enabledButtonsOnDyingProcess = new HashSet<>();
  private String runtimeBin = null;
  private String moduleBin = null;
  private Thread thread = null;
  
  private ModuleHandler(ModuleComponent moduleComponent, ModuleID moduleID) {
    this.moduleID = moduleID;
    this.moduleComponent = moduleComponent;
    if(moduleID != null) {
      moduleComponent.setActive(false);
      runtimeBin = moduleID.getProcessType().getBin();
      moduleBin = moduleID.getDefaultModulePath();
    }
  }
  
  public static ModuleHandler build(ModuleComponent moduleComponent, ModuleID moduleID) {
    ModuleHandler handler = new ModuleHandler(moduleComponent, moduleID);
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
        String workDir = null;
        
        switch(moduleID.getProcessType()) {
        case JAVA_PROCESS:
          commands.add(runtimeBin);
          commands.add("-jar");
          commands.add(moduleBin);
          break;
        case NPM_PROCESS:
          commands.add(runtimeBin);
          commands.add("start");
          workDir = moduleBin;
          break;
        case PYTHON_PROCESS:
          commands.add(runtimeBin);
          commands.add("-u"); 
          String[] moduleTokens = moduleBin.replace("\\\\", "\\").replace("\\", "/").split("/");
          commands.add(moduleTokens[moduleTokens.length - 1]);
          if(moduleTokens.length > 1)
            workDir = moduleBin.substring(0, moduleBin.length() - moduleTokens[moduleTokens.length - 1].length());
          break;
        default:
          break;
        }
        
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        if(workDir != null)
          processBuilder.directory(new File(workDir));
        processBuilder.redirectErrorStream(true);
        
        try {
          Process process = processBuilder.start();
          currentProcess.set(process);
          
          moduleComponent.setActive(true);
          for(JButton button : enabledButtonsOnLivingProcess)
            button.setEnabled(true);
          for(JButton button : enabledButtonsOnDyingProcess)
            button.setEnabled(false);
          
          try(BufferedReader streamReader = new BufferedReader(
              new InputStreamReader(process.getInputStream()))) {
            String line;
            while((line = streamReader.readLine()) != null)
              moduleComponent.putLine(line);
          }
          
        } catch(IOException e) {
          e.printStackTrace();
          currentProcess.set(null);
        } finally {
          go.set(false);
          moduleComponent.setActive(false);
          for(JButton button : enabledButtonsOnDyingProcess)
            button.setEnabled(true);
          for(JButton button : enabledButtonsOnLivingProcess)
            button.setEnabled(false);
        }
      }
    } catch(InterruptedException e) { }
  }
  
  public void go() {
    for(JButton button : enabledButtonsOnDyingProcess)
      button.setEnabled(false);
    go.set(true);
  }
  
  public void terminate() {
    for(JButton button : enabledButtonsOnLivingProcess)
      button.setEnabled(false);
    Process process = currentProcess.get();
    if(process != null) {
      process.descendants().forEach(p -> {
        p.destroy();
      });
      process.destroy();
    }
  }
  
  public boolean isRunning() {
    return go.get();
  }
  
  public ModuleID getModuleID() {
    return moduleID;
  }
  
  public void addButtonToEnableOnLivingProcess(JButton button) {
    enabledButtonsOnLivingProcess.add(button);
  }
  
  public void addButtonToEnableOnDyingProcess(JButton button) {
    enabledButtonsOnDyingProcess.add(button);
  }
}
