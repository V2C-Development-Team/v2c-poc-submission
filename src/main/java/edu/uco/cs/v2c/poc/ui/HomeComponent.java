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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.uco.cs.v2c.poc.ModuleID;
import edu.uco.cs.v2c.poc.control.ModuleHandler;

public class HomeComponent extends JPanel {

  private JButton startAllButton = new JButton("LAUNCH ALL");
  private JButton stopAllButton = new JButton("KILL ALL");
  private Map<ModuleID, ModuleHandler> handlers = new ConcurrentHashMap<>();
  private Map<ModuleID, ModuleStatusCard> statusCards = new ConcurrentHashMap<>();
  private Set<ModuleID> activeModules = new CopyOnWriteArraySet<>();
  
  public HomeComponent() {
    setLayout(new BorderLayout(15, 15));
    
    JLabel titleLabel = new JLabel("V2C Submission POC");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    
    JPanel titlePanel = new JPanel();
    titlePanel.add(titleLabel);
    
    JPanel buttonPanel = new JPanel();
    
    startAllButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        for(ModuleHandler handler : handlers.values())
          if(!handler.isRunning()) handler.go();
      }
    });
    
    stopAllButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        for(ModuleHandler handler : handlers.values())
          if(handler.isRunning()) handler.terminate();
      }
    });
    stopAllButton.setEnabled(false);
    
    buttonPanel.add(startAllButton);
    buttonPanel.add(stopAllButton);
    
    JPanel statusPanel = new JPanel();
    statusPanel.setLayout(new GridLayout((ModuleID.values().length + 2) / 3, 3));
    for(ModuleID moduleID : ModuleID.values()) {
      ModuleStatusCard statusCard = new ModuleStatusCard(moduleID);
      statusCards.put(moduleID, statusCard);
      statusPanel.add(statusCard);
    }
    
    add(titlePanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);
    add(statusPanel, BorderLayout.CENTER);
  }
  
  public void addModuleHandler(ModuleHandler handler) {
    this.handlers.put(handler.getModuleID(), handler);
  }
  
  public void notifyModuleStateChange(ModuleID moduleID, boolean active) {
    ModuleStatusCard statusCard = statusCards.get(moduleID);
    
    if(active) {
      statusCard.setActive(true);
      activeModules.add(moduleID);
    } else {
      statusCard.setActive(false);
      activeModules.remove(moduleID);
    }
    
    startAllButton.setEnabled(activeModules.size() != ModuleID.values().length);
    stopAllButton.setEnabled(!activeModules.isEmpty());
  }
  
}
