package com.monopoly.utils;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Display {

	private JFrame frame;
	private JTextField player[];
	private Color eskirenk;
	private JButton startButton;
	private JTextArea textArea, warning ,made_by;
	private JButton diceButton, endButton, buyButton, buyHouseButton, sellButton;
	private Canvas canvas;
	private boolean diceStatus, endStatus, buyStatus, buyHouseStatus, sellStatus;
	private boolean started = false, gameOver = false;
	private String[] playerName;
	private int playerCount;

	public Display(String title, int width, int height) {
		diceStatus = false;

		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);// BU NE ��E YARIYO ��REN
		frame.setVisible(true);

		canvas = new Canvas();
		canvas.setSize(width, height);

		player = new JTextField[4];
		player[0] = new JTextField();
		player[0].setBounds(880, 625, 200, 30);
		player[1] = new JTextField();
		player[1].setBounds(880, 675, 200, 30);
		player[2] = new JTextField();
		player[2].setBounds(880, 725, 200, 30);
		player[3] = new JTextField();
		player[3].setBounds(880, 775, 200, 30);

		warning = new JTextArea("The game can be played with at least 2 people!");
		warning.setBounds(575, 920, 700, 50);
		warning.setEditable(false);
		warning.setFont(new Font("Tahoma", 3, 25));
		warning.setForeground(Color.red);
		warning.setBackground(Color.LIGHT_GRAY);
		warning.setVisible(false); // Buna false diyoruz ��nk� oyun a��ld��� anda bu hatay� g�rmek istemiyoruz.

		made_by = new JTextArea("The game made by\nBar�� �z�elikay\nOnur Alad�\nErberk Ceminay Artanta�");
		made_by.setBounds(10, 10, 300, 100);
		made_by.setEditable(false);
		made_by.setFont(new Font("Tahoma", 3, 20));
		made_by.setBackground(Color.LIGHT_GRAY);
		
		
		
		
		startButton = new JButton("Start Game!");//Buton u olu�turuyoruz.
		startButton.setBounds(780, 825, 200, 50);
		startButton.addActionListener((ActionEvent e) -> {
			
			playerCount = 0;
			playerName = new String[4];
			for (int i = 0; i < 4; i++) {
				if (player[i] != null && !player[i].getText().isEmpty()) { // bo� de�ilse ve bir yaz� varsa
					playerName[i] = player[i].getText();// oyuncular�n isimlerinin al�nd��� yer.
					playerCount++;
				}
			}

			if (playerCount >= 2) { // oyuncu say�s� 2 ve 2 den b�y�kse oyunu ba�lat.
				
				gameOver = false; // oyun ba�lang�c� oldu�u i�in GameOver false.
				player[0].setVisible(false);
				player[1].setVisible(false);
				player[2].setVisible(false);
				player[3].setVisible(false);
				startButton.setVisible(false);
				// yukar�daki i�lemlerde oyuncu Start Game e bast���nda ba�lang�� ekran�ndakilerin gitmesi i�in false dedik
				
				// a�a��daki i�lemlerde oyuncu Start Game e bast���nda oyun tahtas�n�n g�z�kmesi i�in true dedik.
				started = true; // ikinci ekrana ge�meyi sa�lar.
				textArea.setVisible(true);// Oyuncunun neler yapt���n� g�rd���m�z yerdir.
				diceButton.setVisible(true);
				endButton.setVisible(true);
				buyButton.setVisible(true);
				buyHouseButton.setVisible(true);
				sellButton.setVisible(true);
				warning.setVisible(false); // gerek olmayabilir.
				made_by.setLocation(300, 700);
				made_by.setBackground(Color.green);
				made_by.setVisible(true);
			} else {//oyuncu say�s� 2 den k���k olursa uyar� ver.
				warning.setVisible(true);
			}
		});
		
		
		
		eskirenk = startButton.getBackground();
		
		startButton.addMouseListener(new MouseAdapter() { // burda lamda kullanam�yoruz ��nk� lamda mulltiple metodlarda �al��m�yor.

			@Override
			public void mouseEntered(MouseEvent arg0) {
				startButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setBackground(eskirenk);
			}

		});

		textArea = new JTextArea();// Oyuncun neler yapt���n� g�rd���m Label
		textArea.setBounds(1500, 0, 350, 800);
		textArea.setEditable(false);//BUNA BAK
		textArea.setFont(new Font("Tahoma", Font.BOLD, 15));
		textArea.setBackground(Color.GRAY);
		textArea.setVisible(false); // ba�lang�� ekran�nda g�z�kmesin diye

		diceButton = new JButton("ROLL DICE");
		diceButton.setBounds(1535, 820, 125, 50);
		diceButton.addActionListener((ActionEvent e) -> {
			diceStatus = true; // zar�n �al��mas�n� sa�layan boolean ifadesi
		});
		
		diceButton.addMouseListener(new MouseAdapter() { // burda lamda kullanam�yoruz ��nk� lamda mulltiple metodlarda �al��m�yor.

			@Override
			public void mouseEntered(MouseEvent arg0) {
				diceButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				diceButton.setBackground(eskirenk);
			}

		});
		
		diceButton.setVisible(false); // ana ekranda g�z�kmesin diye false

		endButton = new JButton("END");
		endButton.setBounds(1690, 820, 125, 50);
		endButton.addActionListener((ActionEvent e) -> {
			endStatus = true;
		});
		endButton.addMouseListener(new MouseAdapter() { // burda lamda kullanam�yoruz ��nk� lamda mulltiple metodlarda �al��m�yor.

			@Override
			public void mouseEntered(MouseEvent arg0) {
				endButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				endButton.setBackground(eskirenk);
			}

		});
		endButton.setVisible(false);// ana ekranda g�z�kmesin diye false

		buyButton = new JButton("BUY");
		buyButton.setBounds(1535, 880, 125, 50);
		buyButton.addActionListener((ActionEvent e) -> {
			buyStatus = true;
		});
		buyButton.addMouseListener(new MouseAdapter() { // burda lamda kullanam�yoruz ��nk� lamda mulltiple metodlarda �al��m�yor.

			@Override
			public void mouseEntered(MouseEvent arg0) {
				buyButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				buyButton.setBackground(eskirenk);
			}

		});
		buyButton.setVisible(false);// ana ekranda g�z�kmesin diye false

		buyHouseButton = new JButton("BUY HOUSE");
		buyHouseButton.setBounds(1690, 880, 125, 50);
		buyHouseButton.addActionListener((ActionEvent e) -> {
			buyHouseStatus = true;
		});
		buyHouseButton.addMouseListener(new MouseAdapter() { // burda lamda kullanam�yoruz ��nk� lamda mulltiple metodlarda �al��m�yor.

			@Override
			public void mouseEntered(MouseEvent arg0) {
				buyHouseButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				buyHouseButton.setBackground(eskirenk);
			}

		});
		buyHouseButton.setVisible(false);// ana ekranda g�z�kmesin diye false

		sellButton = new JButton("SELL");
		sellButton.setBounds(1612, 940, 125, 50);
		sellButton.addActionListener((ActionEvent e) -> {
			sellStatus = true;
		});
		
		sellButton.addMouseListener(new MouseAdapter() { // burda lamda kullanam�yoruz ��nk� lamda mulltiple metodlarda �al��m�yor.

			@Override
			public void mouseEntered(MouseEvent arg0) {
				sellButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sellButton.setBackground(eskirenk);
			}

		});
		sellButton.setVisible(false);// ana ekranda g�z�kmesin diye false

		frame.add(player[0]); // TextFieldlar� frame e yerle�tiriyoruz.
		frame.add(player[1]);// TextFieldlar� frame e yerle�tiriyoruz.
		frame.add(player[2]);// TextFieldlar� frame e yerle�tiriyoruz.
		frame.add(player[3]);// TextFieldlar� frame e yerle�tiriyoruz.
		frame.add(warning);	// warning yaz�s�n� frame e yer�le�tiriyoruz.
		frame.add(made_by);
		frame.add(startButton);
		frame.add(textArea); // oyuncular�n yapt�klar�n�n g�z�kt��� label � yerle�tiriyoruz.
		frame.add(diceButton);
		frame.add(endButton);
		frame.add(buyButton);
		frame.add(buyHouseButton);
		frame.add(sellButton);
		frame.add(canvas); // bu olmassa �l�r�z.
		frame.pack(); // bu olmassa sell butonu g�z�km�yor.
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField[] getPlayer() {
		return player;
	}

	public void setPlayer(JTextField[] player) {
		this.player = player;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(String text) {
		this.textArea.setText(text + "\n" + this.getTextArea().getText());
	}//�NEML� BU OLMASSA OYUNCUN HAREKETLER� OLMAZ OYUN PATLAR.

	public JButton getDiceButton() {
		return diceButton;
	}

	public void setDiceButton(JButton diceButton) {
		this.diceButton = diceButton;
	}

	public JButton getEndButton() {
		return endButton;
	}

	public void setEndButton(JButton endButton) {
		this.endButton = endButton;
	}

	public JButton getBuyButton() {
		return buyButton;
	}

	public void setBuyButton(JButton buyButton) {
		this.buyButton = buyButton;
	}

	public JButton getBuyHouseButton() {
		return buyHouseButton;
	}

	public void setBuyHouseButton(JButton buyHouseButton) {
		this.buyHouseButton = buyHouseButton;
	}

	public JButton getSellButton() {
		return sellButton;
	}

	public void setSellButton(JButton sellButton) {
		this.sellButton = sellButton;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public boolean isDiceStatus() {
		return diceStatus;
	}

	public void setDiceStatus(boolean diceStatus) {
		this.diceStatus = diceStatus;
	}

	public boolean isEndStatus() {
		return endStatus;
	}

	public void setEndStatus(boolean endStatus) {
		this.endStatus = endStatus;
	}

	public boolean isBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(boolean buyStatus) {
		this.buyStatus = buyStatus;
	}

	public boolean isBuyHouseStatus() {
		return buyHouseStatus;
	}

	public void setBuyHouseStatus(boolean buyHouseStatus) {
		this.buyHouseStatus = buyHouseStatus;
	}

	public boolean isSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(boolean sellStatus) {
		this.sellStatus = sellStatus;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public JTextArea getWarning() {
		return warning;
	}

	public void setWarning(JTextArea warning) {
		this.warning = warning;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
}
