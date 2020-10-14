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

import edu.uco.cs.v2c.poc.control.ConfigureAction;
import edu.uco.cs.v2c.poc.control.ModuleHandler.ProcessType;

public class ConfigurationFrame extends JFrame {
  
  private enum ConfigOption {
    RUNTIME,
    MODULE;
    
    private JLabel label = null;
    private JTextField textField = new JTextField(10);
    
    private ConfigOption() {
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
  
  private ConfigureAction configureAction = null;
  private JPanel buttonPanel = new JPanel();
  private JPanel configPanel = new JPanel();
  
  public ConfigurationFrame(ConfigureAction configureAction) {
    super(configureAction.getModuleHandler().getModuleID().toString() + " Config");
    this.configureAction = configureAction;
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    
    JButton saveButton = new JButton("SAVE");
    saveButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        setVisible(false);
        configureAction.onConfigUpdate(
            ConfigOption.RUNTIME.getTextField().getText(),
            ConfigOption.MODULE.getTextField().getText());
      } 
    });
    buttonPanel.add(saveButton);
    
    SpringLayout layout = new SpringLayout();
    configPanel.setLayout(layout);
    
    int rowCount = ConfigOption.values().length;
    
    for(ConfigOption option : ConfigOption.values()) {
      configPanel.add(option.getLabel());
      configPanel.add(option.getTextField());
    }
    
    SpringUtilities.makeCompactGrid(configPanel, rowCount, 2, 6, 6, 6, 6);
    
    getContentPane().add(configPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    setSize(400, 150);
    setLocationRelativeTo(null);
  }
  
}
