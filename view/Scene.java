package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class Scene extends JLayeredPane 
implements deadwood.Scene.Listener {

   private JLabel SceneLabel;
   
   public Scene(deadwood.Scene scene) throws Exception {
      deadwood.Card card = scene.getCard();

      JLabel cardLabel = new JLabel();
      
      Class cls = getClass();
      ImageIcon icon =
            new ImageIcon(ImageIO.read(cls.getResourceAsStream("cards/" + String.valueOf(card.getCardNumber()) +".png")));
      
      deadwood.Room.Area area = scene.getArea();
      System.out.println(area);
      cardLabel.setIcon(icon);
      add(cardLabel, new Integer(0));
      cardLabel.setBounds(area.x, area.y,
            area.w,
            area.h);
   }

   public void changed(deadwood.Scene s) {

   }

   // private static ImageIcon getIcon(String file) {
   //    ImageIcon icon = null;
   //    try {

   //    }
   //}

}