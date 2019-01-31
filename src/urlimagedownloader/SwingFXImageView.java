package urlimagedownloader;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JPanel;
  
/** 
 * SwingFXWebView 
 */  
public class SwingFXImageView extends JPanel {  
     
    private Stage stage;  
    public ImageView imageview;  
    private JFXPanel jfxPanel;  
  
    public SwingFXImageView(){  
        imageview = new ImageView();
        initComponents();  
    }  
     
    private void initComponents(){  
         
        jfxPanel = new JFXPanel();  
        createScene();  
         
        setLayout(new BorderLayout());  
        add(jfxPanel, BorderLayout.CENTER);           
    }     
     
    /** 
     * createScene 
     * 
     * Note: Key is that Scene needs to be created and run on "FX user thread" 
     *       NOT on the AWT-EventQueue Thread 
     * 
     */  
    private void createScene() {  
        PlatformImpl.startup(new Runnable() {  
            @Override
            public void run() {  
                 
                stage = new Stage();  
                 
                stage.setTitle("Hello Java FX");  
                stage.setResizable(true);  
   
                VBox root = new VBox();
                root.setAlignment(Pos.CENTER);
                Scene scene = new Scene(root,80,20);  
                stage.setScene(scene);  
                 
                // Set up the embedded imageview:
                imageview.setPreserveRatio(true);
                System.out.println("debug");
                
                ObservableList<Node> children = root.getChildren();
                children.add(imageview);                     
                 
                jfxPanel.setScene(scene);  
            }  
        });  
    }
}