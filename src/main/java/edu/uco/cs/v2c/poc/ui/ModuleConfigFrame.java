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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.uco.cs.v2c.poc.control.ModuleConfigAction;
import edu.uco.cs.v2c.poc.control.ModuleHandler.ProcessType;

public class ModuleConfigFrame extends JFrame {
  
  private enum ModuleConfigOption {
    RUNTIME,
    MODULE;
    
    private JLabel label = null;
    private JTextField textField = new JTextField(10);
    
    private ModuleConfigOption() {
      String[] tokens = name().split("_");
      StringBuilder sb = new StringBuilder();
      for(String token : tokens) {
        if(token.length() > 0) sb.append(Character.toUpperCase(token.charAt(0)));
        if(token.length() > 1) sb.append(token.substring(1, token.length()).toLowerCase());
        sb.append(' ');
      }
      label = new JLabel(sb.toString().strip(), JLabel.TRAILING);
    }
    
    public JLabel getLabel() {
      return label;
    }
    
    public JTextField getTextField() {
      return textField;
    }
  }
  
  private ModuleConfigAction moduleConfigAction = null;
  private JPanel buttonPanel = new JPanel();
  private JPanel configPanel = new JPanel();
  
  public ModuleConfigFrame(ModuleConfigAction moduleConfigAction) {
    super(moduleConfigAction.getModuleHandler().getModuleID().toString() + " Config");
    this.moduleConfigAction = moduleConfigAction;
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    
    JButton saveButton = new JButton("SAVE");
    saveButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        setVisible(false);
        moduleConfigAction.onConfigUpdate(
            ModuleConfigOption.RUNTIME.getTextField().getText(),
            ModuleConfigOption.MODULE.getTextField().getText());
      } 
    });
    buttonPanel.add(saveButton);
    
    SpringLayout layout = new SpringLayout();
    configPanel.setLayout(layout);
    
    int rowCount = ModuleConfigOption.values().length;
    
    for(ModuleConfigOption option : ModuleConfigOption.values()) {
      configPanel.add(option.getLabel());
      configPanel.add(option.getTextField());
    }
    
    SpringUtilities.makeCompactGrid(configPanel, rowCount, 2, 6, 6, 6, 6);
    
    getContentPane().add(configPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    setSize(400, 150);
    setLocationRelativeTo(null);
  }
  
  @Override public void setVisible(boolean visible) {
    if(visible) {
      ModuleConfigOption.RUNTIME.getTextField().setText(
          moduleConfigAction.getModuleHandler().getRuntimeBin());
      ModuleConfigOption.MODULE.getTextField().setText(
          moduleConfigAction.getModuleHandler().getModuleBin());
    }
    super.setVisible(visible);
  }
  
}
