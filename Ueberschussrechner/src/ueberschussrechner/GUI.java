package ueberschussrechner;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class GUI extends javax.swing.JFrame {

    private Tabelle tabelle;

    public GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JLabelUeberschrift = new javax.swing.JLabel();
        jScrollPaneTabelle = new javax.swing.JScrollPane();
        jTableTabelle = new javax.swing.JTable();
        jButtonDrucken = new javax.swing.JButton();
        jButtonLaden = new javax.swing.JButton();
        jButtonSpeichern = new javax.swing.JButton();
        jButtonHilfe = new javax.swing.JButton();
        jButtonSortieren = new javax.swing.JButton();
        textFieldUeberschuss = new java.awt.TextField();
        jLabelUeberschuss = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JLabelUeberschrift.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        JLabelUeberschrift.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelUeberschrift.setText("Überschussrechner");

        jTableTabelle.setAutoCreateRowSorter(true);
        jTableTabelle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, java.awt.Color.black));
        jTableTabelle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Buchungsnummer", "Buchungsdatum", "Bemerkung", "Einnahmen", "Ausgaben"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTabelle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableTabelleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableTabelleKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTableTabelleKeyTyped(evt);
            }
        });
        jScrollPaneTabelle.setViewportView(jTableTabelle);

        jButtonDrucken.setText("Drucken");
        jButtonDrucken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDruckenActionPerformed(evt);
            }
        });

        jButtonLaden.setText("Datei laden");
        jButtonLaden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLadenActionPerformed(evt);
            }
        });

        jButtonSpeichern.setText("Datei speichern");
        jButtonSpeichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSpeichernActionPerformed(evt);
            }
        });

        jButtonHilfe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonHilfe.setText("?");
        jButtonHilfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHilfeActionPerformed(evt);
            }
        });

        jButtonSortieren.setText("Sortieren");
        jButtonSortieren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSortierenActionPerformed(evt);
            }
        });

        textFieldUeberschuss.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabelUeberschuss.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelUeberschuss.setText("Überschuss");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JLabelUeberschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneTabelle, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonHilfe, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(563, 563, 563)
                                        .addComponent(jLabelUeberschuss)
                                        .addGap(40, 40, 40)
                                        .addComponent(textFieldUeberschuss, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(343, 343, 343)
                                        .addComponent(jButtonSortieren, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jButtonDrucken, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(13, 13, 13)
                                        .addComponent(jButtonSpeichern)
                                        .addGap(11, 11, 11)
                                        .addComponent(jButtonLaden, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(JLabelUeberschrift)
                .addGap(21, 21, 21)
                .addComponent(jScrollPaneTabelle, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelUeberschuss, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldUeberschuss, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonHilfe)
                    .addComponent(jButtonSortieren, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDrucken, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSpeichern, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLaden, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDruckenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDruckenActionPerformed
        tabelle.drucken();
    }//GEN-LAST:event_jButtonDruckenActionPerformed

    private void jButtonLadenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLadenActionPerformed
        tabelle.csvEinlesen(jTableTabelle);
        setTabelle(tabelle);
        tabelle.addBuchungslisteToJTable(jTableTabelle);
        tabelle.setTabelleGefuellt(true);
        textFieldUeberschuss.setText(Double.toString(tabelle.getUeberschuss()));
    }//GEN-LAST:event_jButtonLadenActionPerformed

    private void jButtonSpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSpeichernActionPerformed
        tabelle.csvSpeichern(jTableTabelle);
    }//GEN-LAST:event_jButtonSpeichernActionPerformed

    private void jButtonHilfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHilfeActionPerformed
        GUIHilfe guiHilfe = new GUIHilfe();
        guiHilfe.setVisible(true);
    }//GEN-LAST:event_jButtonHilfeActionPerformed

    private void jButtonSortierenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSortierenActionPerformed
        tabelle.sortieren();
        tabelle.addBuchungslisteToJTable(jTableTabelle);
    }//GEN-LAST:event_jButtonSortierenActionPerformed

    private void jTableTabelleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableTabelleKeyTyped
        tabelle.addJTableToBuchungsliste(jTableTabelle);
        tabelle.ueberschussBerechnen();
        textFieldUeberschuss.setText(Double.toString(tabelle.getUeberschuss()));
    }//GEN-LAST:event_jTableTabelleKeyTyped

    private void jTableTabelleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableTabelleKeyPressed
        tabelle.addJTableToBuchungsliste(jTableTabelle);
        tabelle.ueberschussBerechnen();
        textFieldUeberschuss.setText(Double.toString(tabelle.getUeberschuss()));
    }//GEN-LAST:event_jTableTabelleKeyPressed

    private void jTableTabelleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableTabelleKeyReleased
        tabelle.addJTableToBuchungsliste(jTableTabelle);
        tabelle.ueberschussBerechnen();
        textFieldUeberschuss.setText(Double.toString(tabelle.getUeberschuss()));
    }//GEN-LAST:event_jTableTabelleKeyReleased

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    public void setTabelle(Tabelle tabelle) {
        this.tabelle = tabelle;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelUeberschrift;
    private javax.swing.JButton jButtonDrucken;
    private javax.swing.JButton jButtonHilfe;
    private javax.swing.JButton jButtonLaden;
    private javax.swing.JButton jButtonSortieren;
    private javax.swing.JButton jButtonSpeichern;
    private javax.swing.JLabel jLabelUeberschuss;
    private javax.swing.JScrollPane jScrollPaneTabelle;
    private javax.swing.JTable jTableTabelle;
    private java.awt.TextField textFieldUeberschuss;
    // End of variables declaration//GEN-END:variables
}
