/*
 * Copyright (c) 2020 Caleb L. Power, Everistus Akpabio, Rashed Alrashed,
 * Nicholas Clemmons, Jonathan Craig, James Cole Riggall, and Glen Mathew.
 * All rights reserved. Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
