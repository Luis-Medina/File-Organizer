
import java.awt.Cursor;
import java.io.File;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 *
 * Created on Nov 5, 2009, 8:00:43 PM
 */
/**
 *
 * @author Luiso
 */
public class Main extends javax.swing.JFrame {

    private String[] audio = {"mp3", "flac", "m4a", "aiff", "wav", "wma"
        + "m4b", "m4p", "3gp", "aac", "ra", "ram"
        + "ogg", "mid", "ac3", "midi", "mpa"
    };
    private String[] video = {"avi", "mpeg", "mpg", "wmv", "mov", "qt"
        + "divx", "dvr-ms", "flv", "m4v", "mkv", "mp4", "rmvb", "asf",};
    private String[] picture = {"bmp", "jpeg", "jpg", "img", "tiff", "gif", "png", "tif"};
    private JFileChooser chooser;
    private File destination;
    private MyTableModel model;

    /**
     * Creates new form Main
     */
    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        setLocationRelativeTo(null);
        model = new MyTableModel();
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jTable1.setModel(model);
    }

    class MoveTask extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            boolean organize = jRadioButton1.isSelected();
            String destFolder;
            for (File root : model.getFiles()) {
                if (organize) {
                    destFolder = root.getAbsolutePath();
                } else {
                    destFolder = destination.getAbsolutePath();
                }
                Collection<File> files = FileUtils.listFiles(root, FileFileFilter.FILE, TrueFileFilter.INSTANCE);
                for (File file : files) {
                    if (isVideo(file)) {
                        File newFile = new File(destFolder + "\\Video" + "\\" + file.getName());
                        moveFile(file, newFile);
                    } else if (isAudio(file)) {
                        File newFile = new File(destFolder + "\\Audio" + "\\" + file.getName());
                        moveFile(file, newFile);
                    } else if (isPicture(file)) {
                        File newFile = new File(destFolder + "\\Image" + "\\" + file.getName());
                        moveFile(file, newFile);
                    }
                }
            }
            if (jCheckBox1.isSelected()) {
                for (File folder : model.getFiles()) {
                    deleteEmptyFolder(folder);
                }
            }
            return null;
        }

        @Override
        public void done() {
            jButton2.setEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        private void moveFile(File file, File newFile) {
            try {
                FileUtils.moveFile(file, newFile);
            } catch (Exception e) {
                if (e.getMessage().contains("already exists")) {
                    short count = 1;
                    while (count < Short.MAX_VALUE) {
                        try {
                            FileUtils.moveFile(file, nextFileInSequence(newFile, count));
                            break;
                        } catch (Exception e1) {
                            if (!e1.getMessage().contains("already exists")) {
                                break;
                            }
                        }
                    }
                } else {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }

        private File nextFileInSequence(File file, short i) {
            int start = file.getAbsolutePath().lastIndexOf(".");
            if (start > 0) {
                File f = new File(file.getAbsolutePath().substring(0, start) + "(" + i + ")" + file.getAbsolutePath().substring(start, file.getAbsolutePath().length()));
                return f;
            } else {
                return null;
            }
        }

        private void deleteEmptyFolder(File root) {
            File[] children = root.listFiles();
            if (children.length > 0) {
                for (File f : children) {
                    if (f.isDirectory()) {
                        if (f.listFiles().length == 0) {
                            f.delete();
                        } else {
                            deleteEmptyFolder(f);
                        }
                    }
                }
            } else {
                root.delete();
            }
            if (root.listFiles().length == 0) {
                root.delete();
            }
        }

        private boolean isVideo(File f) {
            return accept(f, video);
        }

        private boolean isAudio(File f) {
            return accept(f, audio);
        }

        private boolean isPicture(File f) {
            return accept(f, picture);
        }

        public boolean accept(File arg0, String[] suffixes) {
            IOCase caseSensitivity = IOCase.INSENSITIVE;
            if (arg0.isFile()) {
                String name = arg0.getName();
                for (int i = 0; i < suffixes.length; i++) {
                    if (caseSensitivity.checkEndsWith(name, suffixes[i])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("File Organizer");

        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Go");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Delete Remaining Empty Folders");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folder"
            }
        ));
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Destination:");

        jTextField1.setEnabled(false);

        jButton4.setText("Browse");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Directory organize");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Move to");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 431, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(jCheckBox1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        chooser.setMultiSelectionEnabled(true);
        int choice = chooser.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File[] folders = chooser.getSelectedFiles();
            model.addFiles(folders);
            updateUIForRun();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (model.getFiles() != null) {
            jButton2.setEnabled(false);
            SwingWorker moveTask = new MoveTask();
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            moveTask.execute();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        model.clear();
        updateUIForRun();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        chooser.setMultiSelectionEnabled(false);
        int choice = chooser.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            destination = chooser.getSelectedFile();
            jTextField1.setText(destination.getAbsolutePath());
            updateUIForRun();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        jButton4.setEnabled(true);
        jTextField1.setEnabled(true);
        updateUIForRun();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jButton4.setEnabled(false);
        jTextField1.setEnabled(false);
        updateUIForRun();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void updateUIForRun() {
        if (readyToRun()) {
            jButton2.setEnabled(true);
        } else {
            jButton2.setEnabled(false);
        }
    }

    private boolean readyToRun() {
        if (model.getFiles().size() > 0
                && (jRadioButton1.isSelected()
                || (jRadioButton2.isSelected() && !jTextField1.getText().isEmpty()))) {
            return true;
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
