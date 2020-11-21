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
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.uco.cs.v2c.poc.ModuleID;

public class ModuleStatusCard extends JPanel {
  
  private JLabel statusLabel = null;
  private JLabel moduleLabel = null;
  
  public ModuleStatusCard(ModuleID moduleID) {
    this.moduleLabel = new JLabel(moduleID.toString());
    this.statusLabel = new JLabel("Loading...");
    this.statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
    setActive(false);
    
    setLayout(new BorderLayout());
    
    JPanel modulePanel = new JPanel();
    modulePanel.add(moduleLabel);
    add(modulePanel, BorderLayout.NORTH);
    
    JPanel statusPanel = new JPanel();
    statusPanel.add(statusLabel);
    add(statusPanel, BorderLayout.CENTER);
  }
  
  public void setActive(boolean active) {
    if(active) {
      statusLabel.setText("ACTIVE");
      statusLabel.setForeground(Color.GREEN);
    } else {
      statusLabel.setText("INACTIVE");
      statusLabel.setForeground(Color.RED);
    }
  }
  
}
