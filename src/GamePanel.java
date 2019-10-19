import java.awt.Color;
import java.awt.Graphics;

//import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This Class extends the JPanel class
 * and will create a Hash game board
 * for TicTacToe.
 * This class can draw X or O and
 * keeps track of the game's state
 * @author Spencer Ross
 *
 */

public class GamePanel extends JPanel {
	int up		= 15;	// y=0+15
	int down	= 216;	// y=219-3
	int left	= 3;	// x=0+3
	int right	= 204;	// x=207-3
	int leftL	= 70;	// (204-3)/3   //spaced by 67
	int rightL	= 137;	// (204-3)/3*2
	int topL	= 82;	//this changed	//spaced by 67
	int bottomL = 149;	// 
	int gap		= 67;
	Game flag = new Game(false);
	
	private char[][] space = new char[3][3];	//this is the game spaces
	
	
	public GamePanel() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				space[i][j] = ' ';		//initialize to blanks
			}
		}
	}
	
	public void paint(Graphics g) { 	//This will paint instead of JPanel's paint method
		char c='c', r='r', b='b', f='f';
		//System.out.println("GamePanel's Repaint is happening");
		super.paint(g);
		makeHash(g);			//draws game board
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(space[i][j] == 'x') drawX(i,j,g);
				if(space[i][j] == 'o') drawO(i,j,g);
			}
		}
		/*if(!flag.isGameStarted())
			clearBoard();*/
		
		//Not sure why when the board is full and A win occurs,
		//The Win Line isn't drawn
		//There's also a bug where the game can be continued after a win,
		//but it's not consistent, so Not sure what's causing it.
		if (flag.isGameStarted() == true) {
			if(isTie())
				flag.setGameStarted(false);
			if (this.vertWin(0))
				drawLine(0, 0, c, g);
			if (this.vertWin(1))
				drawLine(1, 0, c, g);
			if (this.vertWin(2))
				drawLine(2, 0, c, g);
			if (this.horzWin(0))
				drawLine(0, 0, r, g);
			if (this.horzWin(1))
				drawLine(0, 1, r, g);
			if (this.horzWin(2))
				drawLine(0, 2, r, g);
			if (this.diagWin(0, 0))
				drawLine(0, 0, b, g);
			if (this.diagWin(0, 2))
				drawLine(0, 2, f, g);
		}
		
		repaint();
	}	
	
	private boolean isTie() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(space[i][j] == ' ')
					return false;
			}
		}
		return true;
	}

	private void makeHash(Graphics g) {
		//g.fillRect(10, 50, 10, 175);
		g.drawLine(leftL, down, leftL, up);		//vertical left
		g.drawLine(rightL, down, rightL, up);	//vertical right
		g.drawLine(left, topL, right, topL);		//horizontal top
		g.drawLine(left, bottomL, right, bottomL);	//horizontal bottom
	}

	public char getSpace(int i, int j) {
		return space[i][j];
	}

	public void setSpace(int i, int j, char plrToken) {
		this.space[i][j] = plrToken;
	}

	public boolean checkWin(int x, int y) {
		if(this.vertWin(x) || this.horzWin(y) || this.diagWin(x,y)) {
			return true;
		}
		return false;
	}
	
	private boolean diagWin(int x, int y) {	//check for diagonal win '\' and '/'
		if(x == 1) {
			if(y == 0 || y == 2) return false;	//spaces where a diagWin can't happen
		} 
		if(y == 1) {
			if(x == 0 || x == 2) return false;	//this as well
		}
		if(x == y) {		//this is \ diagonal
			if(space[0][0] == space[1][1] && space[1][1] != ' ') {
				if(space[1][1] == space[2][2]) return true;
			}
		}
		else {				//this is / diagonal
			if(space[0][2] == space[1][1] && space[1][1] != ' ') {
				if(space[1][1] == space[2][0]) return true;
			}
		}
		return false;
	}

	private boolean horzWin(int y) {		//check for a horizontal win for the specified row
		if(space[0][y] == space[1][y] && space[1][y] != ' ') {	//if left == middle 
			if(space[1][y] == space[2][y]) {
				//g.drawLine(left, (up+gap/2+y*gap), right, (up+gap/2+y*gap));
				return true;		//== right //then true
			}
        }
		return false;
	}
	
	private boolean vertWin(int x) {		//check for a vertical win for the specified column
		if(space[x][0] == space[x][1] && space[x][1] != ' ') {	//if top == middle 
			if(space[x][1] == space[x][2]) {					//== bottom//then true
				//g.drawLine((left+gap/2+x*gap), up, (left+gap/2+x*gap), down);
				return true;		
			}
        }
		return false;				//not 3 in-a-row
	}
	
	private void drawX(int i, int j, Graphics g) {
		g.drawLine((left+gap*i), (up+gap*j), (leftL+gap*i), (topL+gap*j));
		g.drawLine((left+gap*i), (topL+gap*j), (leftL+gap*i), (up+gap*j));
	}
	
	private void drawO(int i, int j, Graphics g) {
		g.drawOval((left+10+gap*i), (up+10+gap*j), 50, 50);
	}

	/*public void printLose() {
		JLabel lblEnd = new JLabel("You LOSE!");
		lblEnd.setBounds(50, 50, 200, 100);
		//getContentPane().add(lblEnd);
	}*/

	public void clearBoard() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				space[i][j] = ' ';		//initialize to blanks
			}
		}
	}
	
	private void drawLine(int x, int y, char c_r, Graphics g) {
		g.setColor(Color.CYAN);
		if(c_r == 'c') g.drawLine((left+(gap/2)+x*gap), up, (left+(gap/2)+x*gap), down);
		if(c_r == 'r') g.drawLine(left, (up+(gap/2)+y*gap), right, (up+(gap/2)+y*gap));
		if(c_r == 'f') { 
			
			g.drawLine(left, down, right, up);
		}
		if(c_r == 'b') g.drawLine(left, up, right, down);
	}
}
