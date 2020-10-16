package edu.uco.cs.v2c.poc.ui;

import java.awt.BorderLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import edu.uco.cs.v2c.poc.ModuleID;

public class LandingFrame extends JFrame {

  private HomeComponent homeComponent = new HomeComponent();
  private JTabbedPane tabbedPane = new JTabbedPane();
  private Map<ModuleID, ModuleComponent> tabPanels = new LinkedHashMap<>();
  
  public LandingFrame() {
    super("Submission POC");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    tabbedPane.addTab("Home", homeComponent);
    
    for(ModuleID id : ModuleID.values()) {
      ModuleComponent component = new ModuleComponent(id.toString());
      tabPanels.put(id, component);
      tabbedPane.addTab(component.getLabel(), component);
    }
    
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
    setSize(800, 600);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  public ModuleComponent getModule(ModuleID id) {
    if(id != null && tabPanels.containsKey(id))
      return tabPanels.get(id);
    return null;
  }
  
  public HomeComponent getHomeComponent() {
    return homeComponent;
  }
  
}
