/**
 * Cell.class
 * <p>
 * An object used to contain one cell of the MineSweeper board
 * <p>
 * CODES: <p> Real -1 = Not initialized, Real - 9 = Bomb, Player - 11 = Not clicked
 * @author Eric G.D
 * @version 1.0
 * @since 1.0	
 *
 */
public class Cell {
	private int real;
	private int player;
	private boolean opened;
	
	public Cell(int real, int player) {
		this.real = real;
		this.player = player;
		this.opened = false;
	}
	
	public Cell(int real) {
		this(real, 11);
	}
	
	public Cell() {
		this(-1);
	}
	
	public void open() {
		this.opened = true;
		this.player = this.real;
	}
	
	public int getReal() {return real;}
	public int getPlayer() {return player;}
	public boolean isOpened() {return opened;}
	public void setReal(int real) {this.real = real;}
	public void setPlayer(int player) {this.player = player;}
	public void setOpened(boolean opened) {this.opened = opened;}
	public String toString() {return "" + this.player;}
}
