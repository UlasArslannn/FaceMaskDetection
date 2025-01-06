package org.example;

import ij.IJ;
import java.io.File;
import javax.swing.JLabel;
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 *
 * @author MONSTER
 */
public class NewJFrame3 extends javax.swing.JFrame {


    private String filePathLabel;
    private File selectedFile;
    private List<String> taggedNiftis = new ArrayList<>();
    private String nifti_txt= "tagged_niftis.txt";
    File nifti_file;
    int fileType;
    /**
     * Creates new form NewJFrame3
     */
    public NewJFrame3() {
        initComponents();




    }

    public void displayNiftiOnLabel(File niftiFile, JLabel label) {
        try {
            Opener opener = new Opener();
            ImagePlus imagePlus = opener.openImage(niftiFile.getAbsolutePath());

            System.out.println("Dosya yolu: " + niftiFile.getAbsolutePath());

            // Dosya tipini kontrol et
            fileType = opener.getFileType(niftiFile.getAbsolutePath());
            System.out.println("Dosya tipi: " + fileType);

            imagePlus = opener.openImage(niftiFile.getAbsolutePath());
            System.out.println("ImagePlus oluştu mu: " + (imagePlus != null));



            if (imagePlus != null) {
                // Get the first slice if it's a stack
                imagePlus.setSlice(1);
                ImageProcessor processor = imagePlus.getProcessor();

                if (processor != null) {
                    BufferedImage bufferedImage = processor.getBufferedImage();

                    // Scale image to fit the label
                    int labelWidth = label.getWidth();
                    int labelHeight = label.getHeight();

                    if (labelWidth > 0 && labelHeight > 0) {
                        bufferedImage = scaleImage(bufferedImage, labelWidth, labelHeight);
                    } else {
                        // If label size is not yet set, use reasonable defaults
                        bufferedImage = scaleImage(bufferedImage, 512, 512);
                    }

                    label.setIcon(new ImageIcon(bufferedImage));
                    label.revalidate();
                    label.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this, "NIfTI dosyası yüklenemedi.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Bir hata oluştu: " + e.getMessage());
        }
    }

    private BufferedImage scaleImage(BufferedImage original, int width, int height) {
        if (original == null) return null;

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();

        // Set better quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the image
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();

        return resized;
    }

    public void setSelectedFile(File selectedFile , File niftis_txt) throws Exception {
        this.selectedFile = selectedFile;
        jtextFile.setText(selectedFile.getName());
        nifti_file = niftis_txt;
        //displayNiftiOnLabel(selectedFile, labelPic);


    }

    public void renameFile(JButton modality_name , List<String> taggedNiftis ){
        if (selectedFile != null) {
            String parentDir = selectedFile.getParent(); // Dosyanın bulunduğu klasör
            String originalName = selectedFile.getName(); // Mevcut dosya adı
            String newName = modality_name.getText() + "_" + originalName; // Yeni dosya adı

            // Yeni dosya nesnesi
            File renamedFile = new File(parentDir, newName);

            // Dosyayı yeniden adlandır
            boolean success = selectedFile.renameTo(renamedFile);
            selectedFile = renamedFile; // Dosya nesnesini güncelle

            // İşlemin başarılı olup olmadığını kontrol et
            if (success) {


                try(BufferedWriter writer = new BufferedWriter(new FileWriter(nifti_file, true))){
                    writer.write(selectedFile.getAbsolutePath());
                    writer.newLine();

                    JOptionPane.showMessageDialog(this, "User data saved successfully");


                }catch(IOException ex){
                    JOptionPane.showMessageDialog(this, "Error saving user data.");
                    ex.printStackTrace();
                }


                jtextFile.setText("Yeni Nifti adı: " + renamedFile.getName());
                taggedNiftis.add(selectedFile.getAbsolutePath());
                System.out.println(taggedNiftis);

            } else {
                JOptionPane.showMessageDialog(this, "Dosya yeniden adlandırılamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen önce bir dosya seçin.");
        }
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        T1 = new javax.swing.JButton();
        T2 = new javax.swing.JButton();
        T1C = new javax.swing.JButton();
        FLAIR = new javax.swing.JButton();
        jtextFile = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        javax.swing.JTextField jtext1 = new javax.swing.JTextField();
        jtext2 = new javax.swing.JTextField();
        jtext3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelPic = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        T1.setText("T1");
        T1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T1ActionPerformed(evt);
            }
        });

        T2.setText("T2");
        T2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T2ActionPerformed(evt);
            }
        });

        T1C.setText("T1C");
        T1C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T1CActionPerformed(evt);
            }
        });

        FLAIR.setText("FLAIR");
        FLAIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FLAIRActionPerformed(evt);
            }
        });

        jtextFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextFileActionPerformed(evt);
            }
        });

        jLabel1.setText("File Name : ");

        jtext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtext2ActionPerformed(evt);
            }
        });

        jLabel2.setText("width");

        jLabel3.setText("heigth");

        jLabel4.setText("depth");

        labelPic.setText("jLabel5");

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(T1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(40, 40, 40)
                                                                .addComponent(jtextFile, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(8, 8, 8)
                                                                .addComponent(T2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(T1C, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(FLAIR, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(cancel)))
                                                .addContainerGap(15, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3)
                                                .addGap(125, 125, 125)
                                                .addComponent(jLabel4)
                                                .addGap(36, 36, 36))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(labelPic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(jtext1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                                                                .addComponent(jtext2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(96, 96, 96)
                                                                .addComponent(jtext3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(labelPic, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(298, 298, 298)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jtext1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jtext2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jtext3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jtextFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(T1)
                                        .addComponent(T2)
                                        .addComponent(T1C)
                                        .addComponent(FLAIR)
                                        .addComponent(cancel))
                                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>

    private void T1ActionPerformed(java.awt.event.ActionEvent evt) {
        renameFile(T1 ,taggedNiftis);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();


    }

    private void jtextFileActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jtext2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void T2ActionPerformed(java.awt.event.ActionEvent evt) {
        renameFile(T2,taggedNiftis);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();        // TODO add your handling code here:
    }

    private void T1CActionPerformed(java.awt.event.ActionEvent evt) {
        renameFile(T1C,taggedNiftis);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();// TODO add your handling code here:
    }

    private void FLAIRActionPerformed(java.awt.event.ActionEvent evt) {
        renameFile(FLAIR,taggedNiftis);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();// TODO add your handling code here:
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();// TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(NewJFrame3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton FLAIR;
    private javax.swing.JButton T1;
    private javax.swing.JButton T1C;
    private javax.swing.JButton T2;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jtext2;
    private javax.swing.JTextField jtext3;
    private javax.swing.JTextField jtextFile;
    private javax.swing.JLabel labelPic;
    // End of variables declaration
}