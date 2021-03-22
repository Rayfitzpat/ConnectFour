import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class ConnectFour {

    private JFrame frame;

    public ConnectFour() { // Sets game frame and visibility
        frame = new JFrame("ConnectFour");
        frame.setSize(1800, 1300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new MultiDraw(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // centres board in monitor
        frame.setResizable(false); // stops frame from resizing
    }


    public static void main(String[] args) {
        new ConnectFour();
    }

    public static class MultiDraw extends JPanel  implements MouseListener {

        // Variables used throughout the aplication
        int numberofRows = 6;
        int numberofColumns = 7; // Game Board columns and Rows
        int yStartPosition;
        int posStartPosition;
        int redCounter; // stores wins for player Red
        int yellowCounter; // stores wins for player Yellow
        int redMoves; // counts amount of moves by red player
        int yellowMoves; // counts amount of moves by yellow player
        int moves;
        boolean winner=false;
        String playerColour = "";
        int gridCell = 190; // Size of grid Cells
        int playerTurn = 2; // ste to 2 so can be used with %2=0 later



        // Initialising the Grid.  Leave colour as white or counters dont work
        Color[][] grid = new Color[numberofRows][numberofColumns];
        public MultiDraw(Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
// for loops used to set grid colours
            int x = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if(x%2==0){
                        grid[i][j] = Color.WHITE;
                    }else{
                        grid[i][j] = Color.WHITE;
                    }
                    x++;
                }
            }
        }

        public void mousePressed(MouseEvent e) {

            // gets the current mouse position
            int posX = e.getX();
            int posY = e.getY();
            // if winner is false then checks the x position and drops in corresponding column
            if(!winner){
                if(posX<(gridCell*grid[0].length) && posY<(gridCell*grid.length)){
                    int clickedRow = posY/gridCell;
                    int clickedCol = posX/gridCell;

                    clickedRow = counterDrop(clickedCol);


                    // alternates the player turns and sets the counter to appropriate colour
                    if(clickedRow!=-1){

                        if(playerTurn%2==0){
                            grid[clickedRow][clickedCol]= Color.red;
                            playerColour =  "RED";
                            redMoves++;
                        } else{
                            grid[clickedRow][clickedCol]= Color.yellow;
                            playerColour =  "Yellow";
                            yellowMoves++;
                        }
                        playerTurn++; // increments player counter


                        // checks for a winner and increments the score of the winning player
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

                // if there is a winner then a dialogue box is displayed and the reset function is called to clear the board
            }else {
                JOptionPane.showMessageDialog(null, "Press Ok to Play Again", "Thanks For Playing", JOptionPane.PLAIN_MESSAGE);
                reset();
            }
        }


        // drops the counter on a white square only
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


        // Main section for graphics
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            Dimension d = getSize();
            g2d.setColor(new Color(0, 0, 0)); // sets background colour
            g2d.fillRect(0,0,d.width,d.height);
            posStartPosition = 0; // Leave at 0
            yStartPosition = 0;  // leave at 0

        // draws grid and sets colours
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {

                    g2d.setColor(grid[row][col]); // no colour set - leave white
                    g2d.fillRect(posStartPosition,yStartPosition,gridCell,gridCell);
                    g2d.setColor(Color.black); // Grid outline colour
                    g2d.drawRect(posStartPosition,yStartPosition,gridCell,gridCell);
                    posStartPosition += gridCell;
                }
                yStartPosition += gridCell;
                posStartPosition = 0;
            }

            // Sets colour and Font for text used
            Font textFont = new Font( null, 0, 32 );
            Font winnerFont = new Font( null, 0, 60 );
            g2d.setColor(new Color(255, 0, 0));

            g2d.setColor(new Color(255, 0, 0));
            g2d.setFont(textFont);
            g2d.drawString("Red Wins: " + redCounter, 1350, 240);

            g2d.setColor(new Color(203, 187, 16));
            g2d.setFont(textFont);
            g2d.drawString("Yellow Wins: " + yellowCounter, 1350, 280);

            g2d.setColor(new Color(255, 0, 0));
            g2d.setFont(textFont);
            g2d.drawString("Red Moves: " + redMoves, 1350, 640);

            g2d.setColor(new Color(203, 187, 16));
            g2d.setFont(textFont);
            g2d.drawString("Yellow Moves: " + yellowMoves, 1350, 680);


// font used in reset button and rectangle

            g2d.fillRect(1450,1150, 200,100);
            g2d.setColor(new Color(0, 0, 0));
            g2d.drawString("Reset", 1470, 1210);




//  if no winner yet,announces whose turn it is. Colours change to correspond to player colour
            if(winner){
                g2d.setFont(winnerFont);
                g2d.setColor(new Color(0, 153, 153));
                g2d.drawString("WINNER - "+ playerColour,700,1200);

            }

        }

        // checks if mouse is within the area of rectangle and then disposes of old window and calls a new one to act like a reset
        public void mouseClicked(MouseEvent e) {
            Ellipse2D rect = new Ellipse2D.Double(1450, 1150, 200, 100);
            if ((e.getButton() == 1) && rect.contains(e.getX(), e.getY())) {
                setVisible(false);
                new ConnectFour();
            }
        }


        public void mouseEntered(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }




// Checks left and right, up and down and diagonally for 4 in a row
        public boolean  checkForWinner(int i,int x, Color col){
            // Right
            int posStart = i;
            int count = 1;

            posStart--;
            while(posStart>=0){
                if(grid[x][posStart].equals(col)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;
                posStart--;
            }

            // Left
            posStart = i;
            posStart++;
            while(posStart<grid[0].length){

                if(grid[x][posStart].equals(col)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                posStart++;
            }


            // Down
            count = 1;
            int yStart = x;
            yStart--;
            while(yStart>0){
                if(grid[yStart][i].equals(col)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
            }

            // Up
            yStart = x;
            yStart++;
            while(yStart<grid.length){

                if(grid[yStart][i].equals(col)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
            }

             // Up Left
            count = 1;
            yStart = x;
            posStart = i;
            posStart--;
            yStart--;
            while(yStart>0 && posStart>0){
                if(grid[yStart][posStart].equals(col)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart--;
                posStart--;
            }

            // Down Right
            yStart = x;
            yStart++;
            posStart = i;
            posStart++;
            while(yStart<grid.length && posStart<grid.length){

                if(grid[yStart][posStart].equals(col)){

                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
                posStart++;
            }

            // down Left
            count = 1;
            yStart = x;
            posStart = i;
            posStart--;
            yStart++;
            while(yStart<grid.length && posStart>0){
                if(grid[yStart][posStart].equals(col)){
                    count++;
                }else{
                    break;
                }
                if(count==4)
                    return true;

                yStart++;
                posStart--;
            }

            // Up Right
            yStart = x;
            yStart--;
            posStart = i;
            posStart++;
            while(yStart>0 && posStart<grid.length){

                if(grid[yStart][posStart].equals(col)){

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


        // Resets the board values back to original values
        public void reset(){
            winner=false;
            playerTurn=2;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = Color.white;

                }
            }
        }

    }
}