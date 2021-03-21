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
        this.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void setLocationRelativeTo(Object o) {
    }

    public static void main(String... argv) {
        new ConnectFour();
    }

    public static class MultiDraw extends JPanel  implements MouseListener {
        int startX = 10;
        int startY = 10;
        int cellSize = 190;
        int turn = 2;
        int rows = 6;
        int cols = 7;
        int redCounter;
        int yellowCounter;
        int moves;
        boolean winner=false;
        String ccolor = "";


        Color[][] grid = new Color[rows][cols];
        public MultiDraw(Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            //1. initialize array here
            int x = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    Color c;
                    if(x%2==0){
                        grid[row][col] = Color.white;
                    }else{
                        grid[row][col] = Color.white;
                    }
                    x++;

                }

            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Dimension d = getSize();
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0,0,d.width,d.height);
            startX = 0;
            startY = 0;

            //2) draw grid here
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {

                    g2.setColor(grid[row][col]);
                    g2.fillRect(startX,startY,cellSize,cellSize);
                    g2.setColor(Color.black);
                    g2.drawRect(startX,startY,cellSize,cellSize);
                    startX += cellSize;
                }
                startY += cellSize;
                startX = 0;
            }
            Font stringFont = new Font( "SansSerif", Font.PLAIN, 32 );
            Font winnerFont = new Font( "SansSerif", Font.PLAIN, 60 );
            g2.setColor(new Color(190, 54, 54));

            g2.setColor(new Color(190, 54, 54));
            g2.setFont(stringFont);
            g2.drawString("Red Wins: " + redCounter, 1350, 40);

            g2.setColor(new Color(188, 175, 55));
            g2.setFont(stringFont);
            g2.drawString("Yellow Wins: " + yellowCounter, 1350, 80);
//            g2.setFont(stringFont);
//            JButton play = new JButton("Play Again");
//            JPanel panel = new JPanel();
//            panel.add(play);
//            panel.setVisible(true);

//           g2.drawRect(1450,1100, 200,100);

            g2.fillRect(1450,1150, 200,100);
            g2.setColor(new Color(0, 0, 0));
            g2.drawString("Play Again", 1470, 1210);



            if(winner==false){
                if(turn%2==0) {
                    g2.setColor(new Color(190, 54, 54));
                    g2.setFont(stringFont);
                    g2.drawString("Red's Turn", 700, 1200);
                }
                else {
                    g2.setColor(new Color(188, 175, 55));
                    g2.setFont(stringFont);
                    g2.drawString("Yellow's Turn", 700, 1200);
                }
            }else{

                g2.setFont(winnerFont);
                g2.setColor(new Color(91, 146, 53));
                g2.drawString("WINNER - "+ ccolor,700,1200);

            }

        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if(winner==false){
                if(x<(cellSize*grid[0].length) && y<(cellSize*grid.length)){
                    int clickedRow = y/cellSize;
                    int clickedCol = x/cellSize;

                    clickedRow = dropP(clickedCol);

                    if(clickedRow!=-1){

                        if(turn%2==0){
                            grid[clickedRow][clickedCol]= Color.red;
                            ccolor =  "RED";
                        } else{
                            grid[clickedRow][clickedCol]= Color.yellow;
                            ccolor =  "Yellow";
                        }
                        turn++;
                        if(checkForWinner(clickedCol,clickedRow, grid[clickedRow][clickedCol])){
                            winner=true;
                            if(ccolor.equals("RED") ) {

                                redCounter++;
                            }
                            else if(ccolor.equals("Yellow")) {
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

        public int dropP(int cc){
            int cr = grid.length-1;

            while(cr>=0){

                if(grid[cr][cc].equals(Color.white)){
                    return cr;
                }
                cr--;
            }

            return -1;

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

        //Marvel

        public boolean  checkForWinner(int cc,int cr, Color c){
            //search west and east
            int xStart = cc;
            int count = 1;
            //check west
            xStart--;
            while(xStart>=0){
                if(grid[cr][xStart].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                xStart--;
            }

            //check east
            xStart = cc;
            xStart++;
            while(xStart<grid[0].length){

                if(grid[cr][xStart].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                xStart++;
            }

            /*
             * More searches here
             */

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
            xStart = cc;
            xStart--;
            yStart--;
            while(yStart>0 && xStart>0){
                if(grid[yStart][xStart].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
                xStart--;
            }

            //check Southeast
            yStart = cr;
            yStart++;
            xStart = cc;
            xStart++;
            while(yStart<grid.length && xStart<grid.length){

                if(grid[yStart][xStart].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
                xStart++;
            }

            /*
             * More Searches
             */

            //check southWest
            count = 1;
            yStart = cr;
            xStart = cc;
            xStart--;
            yStart++;
            while(yStart<grid.length && xStart>0){
                if(grid[yStart][xStart].equals(c)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
                xStart--;
            }

            //check Northeast
            yStart = cr;
            yStart--;
            xStart = cc;
            xStart++;
            while(yStart>0 && xStart<grid.length){

                if(grid[yStart][xStart].equals(c)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
                xStart++;
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