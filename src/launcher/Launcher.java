package launcher;

import controller.Game;

public class Launcher {

	public static void main(String[] args) {
		Game game = new Game("Monopoly", 1850, 1000);
		game.start();
	}
}
