package edu.uco.cs.v2c.poc.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ModuleComponent extends JPanel {
  
  private JPanel buttonPanel = new JPanel();
  private JTextArea textArea = new JTextArea(200, 100);
  private JScrollPane scrollPane = new JScrollPane(textArea);
  private String label = null;
  
  public ModuleComponent(String label) {
    this.label = label;
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
    scrollPane.setPreferredSize(new Dimension(750, 400));
    textArea.setEditable(false);
  }
  
  public String getLabel() {
    return label;
  }
  
  public void pushLine(String line) {
    textArea.insert(line + '\n', 0);
  }
  
  public void addButton(String label, ActionListener listener) {
    JButton button = new JButton(label);
    button.addActionListener(listener);
    buttonPanel.add(button);
  }
  
}
