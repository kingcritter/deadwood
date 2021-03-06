import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;


public class DeadwoodGUI {

   private static class Closer extends WindowAdapter {
      public void windowClosing (WindowEvent e){
         System.exit(0);
      }
   }
   
   public static void main (String[] args) throws Exception {
      deadwood.GameBoard game = new deadwood.GameBoard(2);
      ArrayList<deadwood.Scene> scenes = game.getSceneList();
      JFrame frame = new JFrame();
      view.Board board = new view.Board();
      
      frame.setTitle("Deadwood");
      frame.setPreferredSize(new Dimension(1200, 900));
      frame.setResizable(false);
      frame.addWindowListener(new Closer());
      
      frame.add(board);

      for (deadwood.Scene s : scenes) {
         view.Scene sceneView = new view.Scene(s);
         frame.add(sceneView);
      }
      
      frame.pack();
      frame.setVisible(true);
   }

}
