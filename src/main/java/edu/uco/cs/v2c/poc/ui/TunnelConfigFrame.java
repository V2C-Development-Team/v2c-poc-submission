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

import edu.uco.cs.v2c.poc.control.TunnelConfigAction;

public class TunnelConfigFrame extends JFrame {
  
  private enum TunnelConfigOption {
    LOCAL_PORT,
    REMOTE_HOST,
    REMOTE_PORT,
    INTERNAL_HOST,
    INTERNAL_PORT,
    USERNAME,
    KEYFILE_PATH,
    KEYFILE_PASSWORD;
    
    private JLabel label = null;
    private JTextField textField = new JTextField(10);
    
    private TunnelConfigOption() {
      String[] tokens = name().split("_");
      StringBuilder sb = new StringBuilder();
      for(String token : tokens) {
        if(token.length() > 0) sb.append(Character.toUpperCase(token.charAt(0)));
        if(token.length() > 1) sb.append(token.substring(1, token.length()).toLowerCase());
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
  
  private TunnelConfigAction tunnelConfigAction = null;
  private JPanel buttonPanel = new JPanel();
  private JPanel configPanel = new JPanel();
  
  public TunnelConfigFrame(TunnelConfigAction tunnelConfigAction) {
    super("Tunnel Config");
    this.tunnelConfigAction = tunnelConfigAction;
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    
    JButton saveButton = new JButton("SAVE");
    saveButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        setVisible(false);
        tunnelConfigAction.onConfigUpdate(
            TunnelConfigOption.LOCAL_PORT.getTextField().getText(),
            TunnelConfigOption.REMOTE_HOST.getTextField().getText(),
            TunnelConfigOption.REMOTE_PORT.getTextField().getText(),
            TunnelConfigOption.INTERNAL_HOST.getTextField().getText(),
            TunnelConfigOption.INTERNAL_PORT.getTextField().getText(),
            TunnelConfigOption.USERNAME.getTextField().getText(),
            TunnelConfigOption.KEYFILE_PATH.getTextField().getText(),
            TunnelConfigOption.KEYFILE_PASSWORD.getTextField().getText());
      }
    });
    
    JButton enableButton = new JButton("ENABLE");
    enableButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        boolean doEnable = enableButton.getText().equals("ENABLE");
        enableButton.setText(doEnable ? "DISABLE" : "ENABLE"); // toggle
        tunnelConfigAction.setEnabled(doEnable);
      }
    });
    
    buttonPanel.add(enableButton);
    buttonPanel.add(saveButton);
    
    SpringLayout layout = new SpringLayout();
    configPanel.setLayout(layout);
    
    int rowCount = TunnelConfigOption.values().length;
    
    for(TunnelConfigOption option : TunnelConfigOption.values()) {
      configPanel.add(option.getLabel());
      configPanel.add(option.getTextField());
    }
    
    SpringUtilities.makeCompactGrid(configPanel, rowCount, 2, 6, 6, 6, 6);
    
    getContentPane().add(configPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    setSize(400, 350);
    setLocationRelativeTo(null);
  }
  
  @Override public void setVisible(boolean visible) {
    if(visible) {
      
    }
    super.setVisible(visible);
  }
  
}
























