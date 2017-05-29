package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class Board extends JLayeredPane {

   private JLabel boardLabel;
   
   public Board() throws Exception {
      boardLabel = new JLabel();
      
      Class cls = getClass();
      ImageIcon icon =
            new ImageIcon(ImageIO.read(cls.getResourceAsStream("board.jpg")));
            
      boardLabel.setIcon(icon);
      add(boardLabel, new Integer(0));
      boardLabel.setBounds(0, 0,
            icon.getIconWidth(),
            icon.getIconHeight());
   }

}
