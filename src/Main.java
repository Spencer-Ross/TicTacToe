import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.border.EtchedBorder;

/**
 * This Object will create the 
 * frame and run the necessary   
 * classes to make a window to 
 * play a game of Tic Tac Toe
 * @author Spencer Ross
 * 
 */

public class Main extends JFrame {
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	/*int up		= 15;
	int down	= 182;
	int left	= 3;
	int right	= 172;
	int leftL	= 58;
	int rightL	= 116;
	int topL	= 62;
	int bottomL = 124;*/
	char player = 'x';		//human player using X or O
	//boolean turn = false;		//If false it's CPU turn
	Game session = new Game(false);	//make a current session
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		//Labels for the radio buttons
		JLabel lblPlayer1 = new JLabel("<html><p>Who goes 1st?</p></html>");
		lblPlayer1.setBounds(338, 118, 74, 40);
		getContentPane().add(lblPlayer1);
		
		JLabel lblPlayer = new JLabel("Player");
		lblPlayer.setBounds(338, 47, 55, 15);
		getContentPane().add(lblPlayer);
		
		//The main panel
		Overview panel = new Overview();
		panel.setBounds(125, 12, 10, 10);
		getContentPane().add(panel);
		
		//The buttons to select X or O for the human player
		JRadioButton rdbtnO = new JRadioButton("O");
		rdbtnO.setBounds(250, 74, 55, 15);
		buttonGroup_1.add(rdbtnO);
		getContentPane().add(rdbtnO);
		
		JRadioButton rdbtnX = new JRadioButton("X");
		rdbtnX.setBounds(250, 47, 63, 15);
		rdbtnX.setSelected(true);
		buttonGroup_1.add(rdbtnX);
		getContentPane().add(rdbtnX);
		
		//This is the panel that is the game board
		GamePanel gamePanel = new GamePanel();
		//if(session.isGameStarted() == false) gamePanel.clearBoard();
		gamePanel.setBounds(30, 40, 207, 219);
		gamePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(session.isGameStarted() == false) {
					//System.out.println("you clicked the grid");	//DEBUG
					gamePanel.flag.setGameStarted(false);
					gamePanel.clearBoard();
					return;		//if game hasn't been started, leave this block
				}
				//if(session.isTurn() == false) return;
				if(arg0.getX() < gamePanel.leftL) {			//if clicked in left column
					System.out.println("you clicked the top left corner");
					if(arg0.getY() < gamePanel.topL) {		//if clicked in top left corner
						if(gameCheck(0,0,gamePanel) >= 0) return;	//if turn was taken or win
					} else if(arg0.getY() > gamePanel.bottomL) {	//if in bottom left corner
						if(gameCheck(0,2,gamePanel) >= 0) return;	//if turn was taken or win
					} else {								//if clicked in middle left
						if(gameCheck(0,1,gamePanel) >= 0) return;	//if turn was taken or win
					}
				} else if(arg0.getX() > gamePanel.rightL) {	//if clicked in right column
					if(arg0.getY() < gamePanel.topL) {		//if clicked in top right corner
						if(gameCheck(2,0,gamePanel) >= 0) return;	//if turn was taken or win
					} else if(arg0.getY() > gamePanel.bottomL) {	//if in bottom right corner
						if(gameCheck(2,2,gamePanel) >= 0) return;	//if turn was taken or win
					} else {								//if clicked in middle right
						if(gameCheck(2,1,gamePanel) >= 0) return;	//if turn was taken or win
					}
				} else {									//if clicked in middle column
					if(arg0.getY() < gamePanel.topL) {		//if clicked in top middle
						if(gameCheck(1,0,gamePanel) >= 0) return;	//if turn was taken or win
					} else if(arg0.getY() > gamePanel.bottomL) {	//if in bottom middle
						if(gameCheck(1,2,gamePanel) >= 0) return;	//if turn was taken or win
					} else {								//if clicked in center
						if(gameCheck(1,1,gamePanel) >= 0) return;	//if turn was taken or win
					}
				}
			}
		});
		gamePanel.setBorder(new TitledBorder(null, "Tic Tac Toe", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		getContentPane().add(gamePanel);
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setLayout(null);
		
		//These are the buttons for selecting who goes first
		JRadioButton rdbtnHuman = new JRadioButton("Human");
		rdbtnHuman.setSelected(true);
		rdbtnHuman.setBounds(250, 119, 74, 23);
		buttonGroup_2.add(rdbtnHuman);
		getContentPane().add(rdbtnHuman);
		
		JRadioButton rdbtnCpu = new JRadioButton("CPU");
		rdbtnCpu.setBounds(250, 146, 70, 23);
		buttonGroup_2.add(rdbtnCpu);
		getContentPane().add(rdbtnCpu);
		
		//This is the button to start the game
		//Once the game is started, the button will still react when clicked
		//but it's not starting a new game
		JButton btnStart = new JButton("START!");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Game Started!");		//DEBUG
				if(session.isGameStarted() == true) return;
				if(rdbtnX.isSelected() == true) {	//if set to X
					session.setPlayer('x');			//set Human to X
					session.setCom('o');			//set Computer to O
					System.out.println("x is selected");		//DEBUG
				} else {
					session.setPlayer('o');		//set Human to O
					session.setCom('x');		//set Computer to X
				}
				if(rdbtnHuman.isSelected() == true) {	//if Human is going first
					session.setGoFirst(true);			//set human first
					session.setTurn(true);				//make it your turn
					System.out.println("humans first");			//DEBUG
				} else {
					session.setTurn(false);
					session.setGoFirst(false);
					session.play(gamePanel);
				}
				session.setGameStarted(true);	
				gamePanel.flag.setGameStarted(true);
				System.out.println("game is set to TRUE");		//DEBUG
			}
		});
		btnStart.setBounds(264, 199, 94, 58);
		getContentPane().add(btnStart);
		
		JLabel lblOnceGameEnds = new JLabel("<html><p>Once game ends, click to clear board</p></html>");
		lblOnceGameEnds.setBounds(40, 260, 178, 35);
		getContentPane().add(lblOnceGameEnds);
	}

	protected int gameCheck(int x, int y, GamePanel gamePanel) {
		if(gamePanel.getSpace(x,y) == ' ') {		//check if X or O is placed
			gamePanel.setSpace(x,y,session.getPlayer()); 		//draw the X or O
			if(gamePanel.checkWin(x,y)) {
				
				session.setGameStarted(false);
				/*JLabel lblEnd = new JLabel("You Win!");
				lblEnd.setBounds(50, 50, 200, 100);
				getContentPane().add(lblEnd);*/
				return 1;
			} 
			session.setTurn(false);
			session.play(gamePanel);
			return 0;
		}
		return -1;
	}
}
