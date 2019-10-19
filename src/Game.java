/**
 * This is a simple class
 * that merely handles storing
 * the stats of the Tic Tac Toe
 * game.
 * 
 * @author Spencer Ross
 *
 */

public class Game {
	private char player;
	private char Com;
	private boolean goFirst;
	private boolean gameStarted = false;
	private boolean turn;
	
	public  Game(boolean gameStarted) {
		this.setPlayer('x');
		this.setCom('o');
		this.setGoFirst(true);
		this.setTurn(true);
		this.setGameStarted(gameStarted);
	}

	public void setCom(char c) {
		this.Com = c;
	}
	
	public char getCom() {
		return this.Com;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public char getPlayer() {
		return player;
	}

	public void setPlayer(char player) {
		this.player = player;
	}

	public boolean isGoFirst() {
		return goFirst;
	}

	public void setGoFirst(boolean goFirst) {
		this.goFirst = goFirst;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void play(GamePanel gp) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(gp.getSpace(i,j) == ' ') { 
					gp.setSpace(i,j,this.Com);
					if(gp.checkWin(i,j) == true) {
						this.gameStarted = false;
						//gp.printLose();
						return;
					}
					this.turn = true;
					return;
				}
			}
			
		}
	}
	
	
}
