/*
 * Copyright (C) 2018 cottr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.embeddediq.searchmonkey.RegexWizard;

import java.awt.CardLayout;
import javax.swing.JTable;

/**
 *
 * @author cottr
 */
public class RegexCellComponent extends javax.swing.JPanel {

    /**
     * Creates new form RegexCellComponent
     */
    public RegexCellComponent() {
        initComponents();
    }

    RegexExpression regex;
    
    public void updateData(RegexExpression regex, boolean isSelected, JTable table) {
        this.regex = regex;
        
        // Update the panel
        jLabel1.setText("" + regex.getPosition() + " " + regex.getExpression() + " repeats " + regex.getRepetition() + "");
        jComboBox3.setSelectedIndex(regex.expression);
        jComboBox4.setSelectedIndex(regex.repetition);
        if ((regex.content != null) && (regex.content.getClass().isInstance(String.class))) {
            jTextField3.setText(regex.content.toString());   
        }

        if (isSelected) {
          setBackground(table.getSelectionBackground());
        }else{
          setBackground(table.getBackground());
        }
        
        // Switch to the appropriate card
        CardLayout cards = (CardLayout)this.getLayout();
        cards.show(this, isSelected ? "cardEdit" : "cardView");
    }
    
    public RegexExpression getData()
    {
        RegexExpression tmp = new RegexExpression(this.regex);
        tmp.expression = this.jComboBox3.getSelectedIndex();
        tmp.content = this.jTextField3.getText();
        tmp.repetition = this.jComboBox4.getSelectedIndex();
        return tmp;
    }
    
    /**
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Don't know", "This exact phrase", "One of these words", "A white space charactor", "One of these charactors", "Any numeric charactor", "Any text charactor", "Any charactor except", "Any charactor" }));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/embeddediq/searchmonkey/RegexWizard/Bundle"); // NOI18N
        jLabel2.setText(bundle.getString("RegexCellComponent.jLabel2.text")); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Only once", "Perhaps once", "Any number of times", "Any number of times (greedy)", "Once or more times", "Once or more times (greedy)" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        add(jPanel1, "cardEdit");

        jLabel1.setText(bundle.getString("RegexCellComponent.jLabel1.text")); // NOI18N
        add(jLabel1, "cardView");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
