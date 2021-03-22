import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ConnectFour {

    private JFrame frame;

    public ConnectFour() {
        frame = new JFrame("ConnectFour");
        frame.setSize(1800, 1300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new MultiDraw(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }


    public static void main(String[] args) {
        new ConnectFour();
    }

    public static class MultiDraw extends JPanel  implements MouseListener {

        int numberofRows = 6;
        int numberofColumns = 7;
        int yStartPosition = 10;
        int posStartPosition = 10;
        int redCounter;
        int yellowCounter;
        int moves;
        boolean winner=false;
        String playerColour = "";
        int gridCell = 190;
        int turn = 2;


        Color[][] grid = new Color[numberofRows][numberofColumns];
        public MultiDraw(Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            //1. initialize array here
            int x = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
//                    Color c;
                    if(x%2==0){
                        grid[row][col] = Color.white;
                    }else{
                        grid[row][col] = Color.white;
                    }
                    x++;

                }
            }
        }

        public void mousePressed(MouseEvent e) {
            int posX = e.getX();
            int posY = e.getY();
            if(winner==false){
                if(posX<(gridCell*grid[0].length) && posY<(gridCell*grid.length)){
                    int clickedRow = posY/gridCell;
                    int clickedCol = posX/gridCell;

                    clickedRow = counterDrop(clickedCol);

                    if(clickedRow!=-1){

                        if(turn%2==0){
                            grid[clickedRow][clickedCol]= Color.red;
                            playerColour =  "RED";
                        } else{
                            grid[clickedRow][clickedCol]= Color.yellow;
                            playerColour =  "Yellow";
                        }
                        turn++;
                        if(checkForWinner(clickedCol,clickedRow, grid[clickedRow][clickedCol])){
                            winner=true;
                            if(playerColour.equals("RED") ) {
                                redCounter++;
                            }
                            else if(playerColour.equals("Yellow")) {
                                yellowCounter++;
                            }
                            else {

                            }
                        }
                    }
                }

                repaint();
            }else {
                JOptionPane.showMessageDialog(null, "Press Ok to Play Again", "Thanks For Playing", JOptionPane.PLAIN_MESSAGE);
                reset();
            }
        }

        public int counterDrop(int c){
            int drop = grid.length-1;

            while(drop>=0){

                if(grid[drop][c].equals(Color.white)){
                    return drop;
                }
                drop--;
            }

            return -1;

        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Dimension d = getSize();
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0,0,d.width,d.height);
            posStartPosition = 0;
            yStartPosition = 0;

            //2) draw grid here
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {

                    g2.setColor(grid[row][col]);
                    g2.fillRect(posStartPosition,yStartPosition,gridCell,gridCell);
                    g2.setColor(Color.black);
                    g2.drawRect(posStartPosition,yStartPosition,gridCell,gridCell);
                    posStartPosition += gridCell;
                }
                yStartPosition += gridCell;
                posStartPosition = 0;
            }
            Font textFont = new Font( null, 0, 32 );
            Font winnerFont = new Font( null, 0, 60 );
            g2.setColor(new Color(255, 0, 0));

            g2.setColor(new Color(255, 0, 0));
            g2.setFont(textFont);
            g2.drawString("Red Wins: " + redCounter, 1350, 40);

            g2.setColor(new Color(203, 187, 16));
            g2.setFont(textFont);
            g2.drawString("Yellow Wins: " + yellowCounter, 1350, 80);
//
            g2.fillRect(1450,1150, 200,100);
            g2.setColor(new Color(0, 0, 0));
            g2.drawString("Play Again", 1470, 1210);



            if(!winner){
                if(turn%2==0) {
                    g2.setColor(new Color(255, 0, 0));
                    g2.setFont(textFont);
                    g2.drawString("Red's Turn", 700, 1200);
                }
                else {
                    g2.setColor(new Color(203, 187, 16));
                    g2.setFont(textFont);
                    g2.drawString("Yellow's Turn", 700, 1200);
                }
            }else{

                g2.setFont(winnerFont);
                g2.setColor(new Color(0, 153, 153));
                g2.drawString("WINNER - "+ playerColour,700,1200);

            }

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {
            reset();
        }

        public void mouseClicked(MouseEvent e) {

        }

     

        public boolean  checkForWinner(int cc,int cr, Color c){
            //search west and east
            int posStart = cc;
            int count = 1;
            //check west
            posStart--;
            while(posStart>=0){
                if(grid[cr][posStart].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                posStart--;
            }

            //check east
            posStart = cc;
            posStart++;
            while(posStart<grid[0].length){

                if(grid[cr][posStart].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                posStart++;
            }
            

            //check North
            count = 1;
            int yStart = cr;
            yStart--;
            while(yStart>0){
                if(grid[yStart][cc].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
            }

            //check east
            yStart = cr;
            yStart++;
            while(yStart<grid.length){

                if(grid[yStart][cc].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
            }
            /*
             * More Searches
             */

            //check NorthWest
            count = 1;
            yStart = cr;
            posStart = cc;
            posStart--;
            yStart--;
            while(yStart>0 && posStart>0){
                if(grid[yStart][posStart].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
                posStart--;
            }

            //check Southeast
            yStart = cr;
            yStart++;
            posStart = cc;
            posStart++;
            while(yStart<grid.length && posStart<grid.length){

                if(grid[yStart][posStart].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
                posStart++;
            }

            /*
             * More Searches
             */

            //check southWest
            count = 1;
            yStart = cr;
            posStart = cc;
            posStart--;
            yStart++;
            while(yStart<grid.length && posStart>0){
                if(grid[yStart][posStart].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
                posStart--;
            }

            //check Northeast
            yStart = cr;
            yStart--;
            posStart = cc;
            posStart++;
            while(yStart>0 && posStart<grid.length){

                if(grid[yStart][posStart].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
                posStart++;
            }

            return false;
        }

        public void reset(){
            winner=false;
            turn=2;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = Color.white;

                }
            }
        }

    }//end of class
}