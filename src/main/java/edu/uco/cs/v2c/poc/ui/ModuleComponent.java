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
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ModuleComponent extends JPanel {
  
  private static final String TITLE_TEMPLATE = "[[ %1$s - %2$s ]]";

  private JLabel titleLabel = new JLabel();
  private JPanel titlePanel = new JPanel();
  private JPanel buttonPanel = new JPanel();
  private JTextArea textArea = new JTextArea(200, 100);
  private JScrollPane scrollPane = new JScrollPane(textArea);
  private String label = null;
  
  public ModuleComponent(String label) {
    this.label = label;
    setLayout(new BorderLayout());
    titleLabel.setText("[[ " + label + " ]]");
    titlePanel.add(titleLabel);
    add(titlePanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);
    add(scrollPane, BorderLayout.CENTER);
    scrollPane.setPreferredSize(new Dimension(750, 400));
    textArea.setEditable(false);
  }
  
  public String getLabel() {
    return label;
  }
  
  public void putLine(String line) {
    textArea.insert(line + '\n', 0);
  }
  
  public JButton addButton(String label, ActionListener listener) {
    JButton button = new JButton(label);
    button.addActionListener(listener);
    buttonPanel.add(button);
    return button;
  }
  
  public void setActive(boolean active) {
    titleLabel.setText(String.format(TITLE_TEMPLATE, label, active ? "Active" : "Inactive"));
  }
  
}
