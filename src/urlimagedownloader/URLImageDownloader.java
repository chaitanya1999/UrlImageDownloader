/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urlimagedownloader;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Chaitanya V
 */
public class URLImageDownloader extends javax.swing.JFrame {
    volatile boolean downloading = false;
    Thread downloader;
    final JTextArea lbl;
    File lastImage = null;
    String str1234="";
    Runnable r_downloader = new Runnable(){
            @Override
            public void run(){
                long count=0;
                Scanner sc = new Scanner(urls.getText());
                str1234="";
                while (sc.hasNext() && downloading) {
                    lbl.setText("");
                    count=0;
                    status.setBackground(Color.YELLOW);
                    String strUrl = sc.nextLine();
                    strUrl = strUrl.replace("\n", "");
                    status.setText("<html><center>Downloading | 0 files downloaded </center><br/>"+strUrl+"</html>");
                    System.out.println(strUrl);
                    StringBuilder folderName = new StringBuilder();
                    char[] chars = strUrl.toCharArray();
                    for(int i=7;i<chars.length;i++){
                        if(chars[i]=='/'){
                            if(i==7) {
                            } else break;
                        }
                        else folderName.append(chars[i]);
                    }
                    new File("./Images/"+folderName+"/").mkdirs();
                    HTMLDocument hDoc = null;
                    InputStream is = null;
                    try {
                        URL url = new URL(strUrl);
                        URLConnection urlC = url.openConnection();
                        urlC.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                        is = urlC.getInputStream();

                        HTMLEditorKit hk = new HTMLEditorKit();
                        hDoc = (HTMLDocument) hk.createDefaultDocument();
                        hDoc.putProperty("IgnoreCharsetDirective", true);

                        hk.read(is, hDoc, 0);
                        
                        System.out.println("##"+hDoc.getLength());

                        for (HTMLDocument.Iterator iterator = hDoc.getIterator(HTML.Tag.IMG); iterator.isValid() && downloading; iterator.next()) {
                            System.out.println("img tag");
                            String imgSrc = iterator.getAttributes().getAttribute(HTML.Attribute.SRC).toString();
                            if((!imgSrc.startsWith("http://"))&&(!imgSrc.startsWith("https://"))){
                                if(imgSrc.charAt(0)=='/'){
                                    imgSrc = "http://"+folderName+imgSrc;
                                } else {
                                    imgSrc = "http://"+folderName+"/"+imgSrc;
                                }
                            }
                            System.out.println("href="+imgSrc);

                            if (imgSrc != null && (imgSrc.toLowerCase().endsWith(".jpg") || (imgSrc.endsWith(".png")) || (imgSrc.endsWith(".jpeg")) || (imgSrc.endsWith(".bmp")) || (imgSrc.endsWith(".gif")))) {
                                try {
                                    if(lastImage!=null){
                                        img.imageview.setImage(new javafx.scene.image.Image(new FileInputStream(lastImage)));
                                    }
                                    URL iUrl = new URL(imgSrc);
                                    URLConnection iUrlC = iUrl.openConnection();
                                    iUrlC.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                                    InputStream istr = iUrlC.getInputStream();
                                    if (istr != null) {
                                        File ffff = new File("./Images/"+ folderName + "/" + imgSrc.substring(imgSrc.lastIndexOf('/') + 1, imgSrc.length()));
                                        if(ffff.exists())ffff.delete();
                                        Files.copy(istr, ffff.toPath());
                                        lastImage = ffff;
                                        count++;
                                        status.setText("<html><center>Downloading | "+count+" files downloaded </center><br/> " + strUrl + "</html>");
                                    }

                                } catch (IOException ex) {
                                    System.out.println("#" + ex);
                                }
                            }
                        }
                    } catch (MalformedURLException ex) {
                        JOptionPane.showMessageDialog(URLImageDownloader.this, ex);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(URLImageDownloader.this, ex);
                        ex.printStackTrace();
                    } catch (BadLocationException ex) {
                        Logger.getLogger(URLImageDownloader.class.getName()).log(Level.SEVERE, null, ex);
                    } catch(RuntimeException ex){
                        ex.printStackTrace();
                    }
                    
                    final long c = count;
                    final String strstr = strUrl;
                    
                    str1234+="\n"+c+" files | "+strstr;
                    
                    
                }
                lastImage=null;
                if(downloading){
                    status.setBackground(Color.GREEN);
                    status.setText("Finished | " + count + " files downloaded");
                }
//                preview.setIcon(null);
                downloading = false;
                if(str1234.isEmpty())str1234="  ";
                lbl.setText(str1234.substring(1));
            }
            
        };
    SwingFXImageView img = new SwingFXImageView();
    public URLImageDownloader() {
        initComponents();
        lbl=list;
        previewOuterPanel.add(img);
        img.setPreferredSize(new Dimension(previewOuterPanel.getHeight(),previewOuterPanel.getHeight()));
        img.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
        img.imageview.setFitHeight(previewOuterPanel.getHeight());
        
        File f = new File("./Images/");
        if(!f.exists())f.mkdirs();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        urlPanel = new javax.swing.JPanel();
        status = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        urls = new javax.swing.JTextArea();
        toolsPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JTextArea();
        btnShowFiles = new javax.swing.JButton();
        previewOuterPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UrlImageDownloader");

        status.setBackground(new java.awt.Color(0, 255, 0));
        status.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status.setText("STOPPED");
        status.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel1.setText("URL List(Each url in seperate line):");

        jScrollPane1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        urls.setColumns(20);
        urls.setRows(5);
        jScrollPane1.setViewportView(urls);

        javax.swing.GroupLayout urlPanelLayout = new javax.swing.GroupLayout(urlPanel);
        urlPanel.setLayout(urlPanelLayout);
        urlPanelLayout.setHorizontalGroup(
            urlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(urlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(urlPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(urlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                        .addGroup(urlPanelLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(275, 275, 275)))
                    .addContainerGap()))
        );
        urlPanelLayout.setVerticalGroup(
            urlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
            .addGroup(urlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(urlPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        toolsPanel.setPreferredSize(new java.awt.Dimension(121, 123));

        jButton1.setText("Stop");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 102));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Preview");
        jCheckBox1.setPreferredSize(new java.awt.Dimension(55, 23));
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jButton2.setText("Download");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        list.setEditable(false);
        list.setColumns(20);
        list.setRows(5);
        jScrollPane2.setViewportView(list);

        btnShowFiles.setText("Show Files");
        btnShowFiles.setMaximumSize(new java.awt.Dimension(55, 23));
        btnShowFiles.setMinimumSize(new java.awt.Dimension(55, 23));
        btnShowFiles.setPreferredSize(new java.awt.Dimension(55, 23));
        btnShowFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowFilesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toolsPanelLayout = new javax.swing.GroupLayout(toolsPanel);
        toolsPanel.setLayout(toolsPanelLayout);
        toolsPanelLayout.setHorizontalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolsPanelLayout.createSequentialGroup()
                .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toolsPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnShowFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
                    .addGroup(toolsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2))
                    .addGroup(toolsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );
        toolsPanelLayout.setVerticalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolsPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(toolsPanelLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(7, 7, 7)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnShowFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        previewOuterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        previewOuterPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        previewOuterPanel.setPreferredSize(new java.awt.Dimension(496, 270));
        previewOuterPanel.setLayout(new javax.swing.BoxLayout(previewOuterPanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(urlPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(toolsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
            .addComponent(previewOuterPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(urlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(previewOuterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(!downloading){
            downloading = true;
            downloader = new Thread(r_downloader);
            downloader.start();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(downloading){
            downloading=false;
            try {
                downloader.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(URLImageDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
            status.setText("STOPPED");
            status.setBackground(Color.GREEN);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        if(jCheckBox1.isSelected()){
            img.setVisible(true);
        } else {
            img.setVisible(false);
        }
    }//GEN-LAST:event_jCheckBox1StateChanged

    private void btnShowFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowFilesActionPerformed
        try {
            Runtime.getRuntime().exec("cmd /c start Images\\");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: Cannot show files.\nException: "+ex);
        }
    }//GEN-LAST:event_btnShowFilesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
            */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //</editor-fold>
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(URLImageDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new URLImageDownloader().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShowFiles;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea list;
    private javax.swing.JPanel previewOuterPanel;
    private javax.swing.JLabel status;
    private javax.swing.JPanel toolsPanel;
    private javax.swing.JPanel urlPanel;
    private javax.swing.JTextArea urls;
    // End of variables declaration//GEN-END:variables
}
