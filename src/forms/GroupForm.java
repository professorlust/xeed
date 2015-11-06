/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRelations.java
 *
 * Created on 2011-feb-10, 19:03:07
 */
package forms;

import templates.RelationsPanel;
import xeed.*;
import xeed.Character;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Erik
 */
public class GroupForm extends javax.swing.JFrame implements TableModelListener {

   private Vector jTableModel = new Vector(0, 2); // Model för tabellen
   private Vector jTableHeader = new Vector(0);
   private boolean boolReloading = false; //Förhindrara uppdate loopar
   public RelationsPanel relationsHandle = null;

   /**
    * Creates new form frmRelations
    */
   public GroupForm() {
      initComponents();

      try {
         ArrayList<Image> images = new ArrayList(0);
         images.add(ImageIO.read(this.getClass().getResource("/icon.png")));
         images.add(ImageIO.read(this.getClass().getResource("/group.png")));
         this.setIconImages(images);
      } catch (IOException e) {
      }

      tblMembers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      tblMembers.getColumnModel().getColumn(0).setResizable(false);
      tblMembers.getColumnModel().getColumn(0).setPreferredWidth(20);
      tblMembers.getColumnModel().getColumn(1).setPreferredWidth(tblMembers.getWidth() - 20);

      tblMembers.getModel().addTableModelListener(this);
      this.setVisible(true);
      RefreshData();

   }

   public void LoadRelationship() {
      jTabbedPane1.remove(relationsHandle);

      Group g = (Group) comboGroup.getSelectedItem();
      if (g == null) {
         return;
      }

      relationsHandle = new RelationsPanel(g);
      jTabbedPane1.addTab("Relations", relationsHandle);
      relationsHandle.setPreferredSize(new Dimension(jTabbedPane1.getWidth(), jTabbedPane1.getHeight()));
   }

   public final void RefreshData() {

      Object prev_selection = comboGroup.getSelectedItem();

      comboGroup.removeAllItems();
      for (int x = 0; x < XEED.groupDB.size(); x++) {
         comboGroup.addItem(XEED.groupDB.get(x));
      }

      comboGroup.setSelectedItem(prev_selection);

      //     LoadMembers();, called later in (combogroup action)
      if (comboGroup.getSelectedItem() == null) {
         txtDescription.setEnabled(false);
      } else {
         txtDescription.setEnabled(true);
      }

   }

   private void LoadMembers() {

      boolReloading = true;
      jTableModel.clear();
      if (comboGroup.getSelectedItem() != null) {
         for (int x = 0; x < XEED.charDB.size(); x++) {
            Vector o = new Vector(0);
            Group g = (Group) comboGroup.getSelectedItem();
            if (g.IsMember(XEED.charDB.get(x).characterID)) {
               o.add(true);
            } else {
               o.add(false);
            }
            o.add(XEED.charDB.get(x));
            jTableModel.add(o);
         }
      }

      DefaultTableModel df = (DefaultTableModel) tblMembers.getModel();
      df.fireTableDataChanged();
      boolReloading = false;
   }

   public void tableChanged(TableModelEvent e) {
      if (e.getType() != TableModelEvent.UPDATE) {
         return;
      }

      if (comboGroup.getSelectedItem() == null) {
         return;
      }

      if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
         return;
      }

      if (jTableModel.isEmpty()) {
         return;
      }

      if (boolReloading) {
         return;
      }

      Group g = (Group) comboGroup.getSelectedItem();
      for (int x = 0; x < jTableModel.size(); x++) {
         Vector o = (Vector) jTableModel.get(x);
         xeed.Character c = (Character) o.get(1);

         boolean b = (Boolean) o.get(0);
         if (b) {
            g.AddMember(c.characterID);
         } else {
            g.DeleteMember(c.characterID);
         }
      }

      Character[] affectedcharacters = new Character[XEED.charDB.size()];
      XEED.charDB.toArray(affectedcharacters);
      XEED.hwndNotifier.FireUpdate(affectedcharacters, true, false, false, false, false, true, false, false, false);
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jScrollPane1 = new javax.swing.JScrollPane();
      tblMembers = new javax.swing.JTable();
      comboGroup = new javax.swing.JComboBox();
      btnDelete = new javax.swing.JButton();
      btnRename = new javax.swing.JButton();
      btnAdd = new javax.swing.JButton();
      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      jTabbedPane1 = new javax.swing.JTabbedPane();
      jPanel1 = new javax.swing.JPanel();
      jScrollPane2 = new javax.swing.JScrollPane();
      txtDescription = new javax.swing.JTextArea();

      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      setTitle("Groups");
      setLocationByPlatform(true);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
         }
      });

      jScrollPane1.setName("jScrollPane1"); // NOI18N

      jTableHeader.add("");
      jTableHeader.add("Name");
      tblMembers.setAutoCreateRowSorter(true);
      tblMembers.setModel(new javax.swing.table.DefaultTableModel(jTableModel, jTableHeader) {

         Class[] types = new Class[] { java.lang.Boolean.class, Object.class };

         public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
         }

         public boolean isCellEditable(int rowIndex, int mColIndex) {
            if (mColIndex != 0) {
               return false;
            } else {
               return true;
            }
         }
      });
      tblMembers.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
      tblMembers.setName("tblMembers"); // NOI18N
      tblMembers.setShowHorizontalLines(false);
      tblMembers.setShowVerticalLines(false);
      tblMembers.getTableHeader().setReorderingAllowed(false);
      jScrollPane1.setViewportView(tblMembers);

      comboGroup
            .setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      comboGroup.setName("comboGroup"); // NOI18N
      comboGroup.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            comboGroupActionPerformed(evt);
         }
      });

      btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
      btnDelete.setToolTipText("Remove");
      btnDelete.setName("btnDelete"); // NOI18N
      btnDelete.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnDeleteActionPerformed(evt);
         }
      });

      btnRename.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wrench.png"))); // NOI18N
      btnRename.setToolTipText("Rename");
      btnRename.setName("btnRename"); // NOI18N
      btnRename.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnRenameActionPerformed(evt);
         }
      });

      btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
      btnAdd.setToolTipText("Add");
      btnAdd.setName("btnAdd"); // NOI18N
      btnAdd.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnAddActionPerformed(evt);
         }
      });

      jLabel1.setText("Group");
      jLabel1.setName("jLabel1"); // NOI18N

      jLabel2.setText("Members");
      jLabel2.setName("jLabel2"); // NOI18N

      jTabbedPane1.setName("jTabbedPane1"); // NOI18N

      jPanel1.setName("jPanel1"); // NOI18N

      jScrollPane2.setName("jScrollPane2"); // NOI18N

      txtDescription.setColumns(20);
      txtDescription.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
      txtDescription.setLineWrap(true);
      txtDescription.setRows(5);
      txtDescription.setWrapStyleWord(true);
      txtDescription.setEnabled(false);
      txtDescription.setName("txtDescription"); // NOI18N
      txtDescription.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyReleased(java.awt.event.KeyEvent evt) {
            txtDescriptionKeyReleased(evt);
         }
      });
      jScrollPane2.setViewportView(txtDescription);

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                  jPanel1Layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .addContainerGap()));
      jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                  jPanel1Layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                        .addContainerGap()));

      jTabbedPane1.addTab("Description", jPanel1);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jLabel1)
                              .addGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                          .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0,
                                                Short.MAX_VALUE)
                                          .addComponent(comboGroup, javax.swing.GroupLayout.Alignment.LEADING, 0,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                          .addGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                layout.createSequentialGroup().addComponent(btnAdd).addGap(18, 18, 18)
                                                      .addComponent(btnRename).addGap(18, 18, 18)
                                                      .addComponent(btnDelete))).addComponent(jLabel2))
                  .addGap(18, 18, 18)
                  .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                  .addContainerGap()));
      layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING,
                                    javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                              .addGroup(
                                    layout.createSequentialGroup()
                                          .addComponent(jLabel1)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addComponent(comboGroup, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addGroup(
                                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(btnAdd).addComponent(btnRename)
                                                      .addComponent(btnDelete))
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                          .addComponent(jLabel2)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412,
                                                Short.MAX_VALUE))).addContainerGap()));

      pack();
   } // </editor-fold>//GEN-END:initComponents

   private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
      //GEN-FIRST:event_btnAddActionPerformed
      String szName = JOptionPane
            .showInputDialog(null, "Enter group name:", "Add new group", JOptionPane.PLAIN_MESSAGE);
      if (szName == null) {
         return;
      }

      Group g = new Group();
      g.szName = szName;
      g.lngID = XEED.CreateUniqueGroupID();
      XEED.groupDB.add(g);
      RefreshData();
      comboGroup.setSelectedItem(g);

      Character[] affectedcharacters = new Character[XEED.charDB.size()];
      XEED.charDB.toArray(affectedcharacters);
      XEED.hwndNotifier.FireUpdate(affectedcharacters, true, false, true, false, false, false, true, false, false);

   } //GEN-LAST:event_btnAddActionPerformed

   private void btnRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenameActionPerformed
      if (comboGroup.getSelectedItem() == null) {
         return;
      }

      Group g = (Group) comboGroup.getSelectedItem();

      String szName = JOptionPane.showInputDialog(null, "Enter new group name:", "Rename " + g.szName,
            JOptionPane.PLAIN_MESSAGE);
      if (szName == null) {
         return;
      }

      g.szName = szName;
      comboGroup.repaint();

      Character[] affectedcharacters = new Character[XEED.charDB.size()];
      XEED.charDB.toArray(affectedcharacters);
      XEED.hwndNotifier.FireUpdate(affectedcharacters, true, false, true, false, false, true, true, false, false);

   } //GEN-LAST:event_btnRenameActionPerformed

   private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
      if (comboGroup.getSelectedItem() == null) {
         return;
      }

      Group g = (Group) comboGroup.getSelectedItem();

      for (int x = 0; x < XEED.charDB.size(); x++) {
         XEED.charDB.get(x).DeleteRelation(g.lngID, 1);
      }

      XEED.groupDB.remove(g);
      RefreshData();
      LoadRelationship();

      Character[] affectedcharacters = new Character[XEED.charDB.size()];
      XEED.charDB.toArray(affectedcharacters);
      XEED.hwndNotifier.FireUpdate(affectedcharacters, true, false, true, false, false, true, true, false, false);
   } //GEN-LAST:event_btnDeleteActionPerformed

   private void comboGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboGroupActionPerformed
      txtDescription.setText("");
      if (comboGroup.getSelectedItem() == null) {
         return;
      }
      Group g = (Group) comboGroup.getSelectedItem();
      txtDescription.setText(g.szDescription);
      txtDescription.setCaretPosition(0);

      LoadMembers();
      LoadRelationship();
   } //GEN-LAST:event_comboGroupActionPerformed

   private void txtDescriptionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescriptionKeyReleased
      if (comboGroup.getSelectedItem() == null) {
         return;
      }
      Group g = (Group) comboGroup.getSelectedItem();
      g.szDescription = txtDescription.getText();

      Character[] affectedcharacters = new Character[XEED.charDB.size()];
      XEED.charDB.toArray(affectedcharacters);
      XEED.hwndNotifier.FireUpdate(affectedcharacters, true, false, false, false, false, false, false, false, false);

   } //GEN-LAST:event_txtDescriptionKeyReleased

   private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      XEED.hwndGroup = null;
      super.dispose();
   } //GEN-LAST:event_formWindowClosing
     // Variables declaration - do not modify//GEN-BEGIN:variables

   private javax.swing.JButton btnAdd;
   private javax.swing.JButton btnDelete;
   private javax.swing.JButton btnRename;
   private javax.swing.JComboBox comboGroup;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JTabbedPane jTabbedPane1;
   private javax.swing.JTable tblMembers;
   private javax.swing.JTextArea txtDescription;
   // End of variables declaration//GEN-END:variables
}
