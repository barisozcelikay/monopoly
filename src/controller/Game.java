package controller;

import com.monopoly.utils.Display; // diðer sýnýflarý çaðýrýyoruz.
import com.monopoly.model.Player;	// diðer sýnýflarý çaðýrýyoruz.
import com.monopoly.model.Square;// diðer sýnýflarý çaðýrýyoruz.
import com.monopoly.model.Chance;// diðer sýnýflarý çaðýrýyoruz.
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException; // Try Catch içim
import java.util.LinkedList; 
import java.util.Random; 
import javax.imageio.ImageIO; // Resimleri atamak için 
import javax.swing.ImageIcon;
import javax.swing.JFrame; // Pencere için
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
//703 E BAK

public class Game implements Runnable { // neden runnable

	public int width, height;
	private Display display;
	private boolean running = false;// bu true olursa oyun çalýþmaz.
	private Thread thread; // start() metodunu main e veriyor.
	private BufferStrategy bs;
	private Graphics g;
	private final int fps = 60;

	private BufferedImage monopolyImage;
	private BufferedImage TedImage;
	private BufferedImage[] diceImage;
	private BufferedImage start_image, jail_image, park_image, go_jail_image;
	private BufferedImage chance_image, chance1_image, chance2_image, chance3_image;
	private BufferedImage tax1_image, tax2_image;
	private BufferedImage train_image, electric_image, water_image;
	private BufferedImage brown1_image, brown2_image;
	private BufferedImage blue1_image, blue2_image, blue3_image;
	private BufferedImage pink1_image, pink2_image;
	private BufferedImage orange1_image, orange2_image, orange3_image;
	private BufferedImage red1_image, red2_image, red3_image;
	private BufferedImage green1_image, green2_image, green3_image;
	private BufferedImage player1_image, player2_image, player3_image, player4_image;
	private BufferedImage[] houseImage;
	private BufferedImage hotel_image;

	private Player[] player;
	private Square[] brown, blue, pink, orange, red, green, corner, chance, state;
	private LinkedList<Square> board;
	private LinkedList<Chance> chanceCard;

	private int turn = 0;
	private int dice_number = 0;
	private int cardNumber;
	private int total;
	private boolean rolledBefore = false, secondRoll = false; // DÝKKAT dice için kullanýyoruz.
	private boolean alreadyExecuted = false; // DÝKKAT 
	private int tickCount = 0, temp = 0; // DÝKKAT

	private int corner_width, corner_height, square_width_vertical, square_height_vertical, square_width_horizontal, square_height_horizontal;

	private final double rentFactor = 10, oneHouseFactor = 2.5, twoHouseFactor = 0.9, threeHouseFactor = 0.3, hotelFactor = 0.2;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		display = new Display(title, width, height);
	}

	private void init() {
		try {
			// Köþe, sað-sol ve alt-üst mekanlarý için canvas boyutuna orantýlý geniþlik ve yükseklik uzunluklarý atama
			corner_width = (width - 350) / 5; // köþelerin eni
			corner_height = height  / 5; // köþelerin boyu
			square_width_vertical = (width - 350) / 10; // alt ve üst satýrlarýn eni
			square_height_vertical = height / 5; // alt ve üst satýrlarýn uzunluðu
			square_width_horizontal = (width - 350) / 5; // sol ve sað sütunlarýn eni
			square_height_horizontal = height / 10;  // sol ve sað sütunlarýn uzunluðu
			
		//	Image img1 = new ImageIcon(Class1.class.getResource("/icons8-java-26.png")).getImage();
			
			chance_image = ImageIO.read(new FileInputStream("res/img/chance.png")); // ortadaki þans png'si
			monopolyImage = ImageIO.read(new FileInputStream("res/img/monopoly.png")); // ortadaki ponopoly png'si

			diceImage = new BufferedImage[6];
			diceImage[0] = ImageIO.read(new FileInputStream("res/img/dice1.png"));
			diceImage[1] = ImageIO.read(new FileInputStream("res/img/dice2.png"));
			diceImage[2] = ImageIO.read(new FileInputStream("res/img/dice3.png"));
			diceImage[3] = ImageIO.read(new FileInputStream("res/img/dice4.png"));
			diceImage[4] = ImageIO.read(new FileInputStream("res/img/dice5.png"));
			diceImage[5] = ImageIO.read(new FileInputStream("res/img/dice6.png"));

			start_image = ImageIO.read(new FileInputStream("res/img/zson.jpeg")); // png ye dönüþtürülebilir.
			jail_image = ImageIO.read(new FileInputStream("res/img/jail.png")); // köþelerdeki resimler
			park_image = ImageIO.read(new FileInputStream("res/img/park.png"));
			go_jail_image = ImageIO.read(new FileInputStream("res/img/go_jail.png"));

			chance1_image = ImageIO.read(new FileInputStream("res/img/chance1.png"));
			chance2_image = ImageIO.read(new FileInputStream("res/img/chance2.png")); //þans img
			chance3_image = ImageIO.read(new FileInputStream("res/img/chance3.png"));

			tax1_image = ImageIO.read(new FileInputStream("res/img/tax1.png"));
			tax2_image = ImageIO.read(new FileInputStream("res/img/tax2.png"));

			train_image = ImageIO.read(new FileInputStream("res/img/train.png"));
			electric_image = ImageIO.read(new FileInputStream("res/img/electric.png"));
			water_image = ImageIO.read(new FileInputStream("res/img/water.png"));

			brown1_image = ImageIO.read(new FileInputStream("res/img/brown1.png"));
			brown2_image = ImageIO.read(new FileInputStream("res/img/brown2.png"));

			blue1_image = ImageIO.read(new FileInputStream("res/img/blue1.png"));
			blue2_image = ImageIO.read(new FileInputStream("res/img/blue2.png"));
			blue3_image = ImageIO.read(new FileInputStream("res/img/blue3.png"));

			pink1_image = ImageIO.read(new FileInputStream("res/img/pink1.png"));
			pink2_image = ImageIO.read(new FileInputStream("res/img/pink2.png"));

			orange1_image = ImageIO.read(new FileInputStream("res/img/orange1.png"));
			orange2_image = ImageIO.read(new FileInputStream("res/img/orange2.png"));
			orange3_image = ImageIO.read(new FileInputStream("res/img/orange3.png"));

			red1_image = ImageIO.read(new FileInputStream("res/img/red1.png"));
			red2_image = ImageIO.read(new FileInputStream("res/img/red2.png"));
			red3_image = ImageIO.read(new FileInputStream("res/img/red3.png"));

			green1_image = ImageIO.read(new FileInputStream("res/img/green1.png"));
			green2_image = ImageIO.read(new FileInputStream("res/img/green2.png"));
			green3_image = ImageIO.read(new FileInputStream("res/img/green3.png"));

			player1_image = ImageIO.read(new FileInputStream("res/img/player1.png"));
			player2_image = ImageIO.read(new FileInputStream("res/img/player2.png"));
			player3_image = ImageIO.read(new FileInputStream("res/img/player3.png"));
			player4_image = ImageIO.read(new FileInputStream("res/img/player4.png"));

			houseImage = new BufferedImage[3];
			houseImage[0] = ImageIO.read(new FileInputStream("res/img/house1.png"));
			houseImage[1] = ImageIO.read(new FileInputStream("res/img/house2.png"));
			houseImage[2] = ImageIO.read(new FileInputStream("res/img/house3.png"));
			hotel_image = ImageIO.read(new FileInputStream("res/img/hotel.png"));
			
				// Square class ýnýn array içinde constructer dan alýyor.//
				// new Square(mekanýn id'si, mekanýn ismi, mekanýn x kordinatý , mekanýn y kordinatý,
				// mekanýn eni ,mekanýn boyu,mekanýn ücreti , mekanýn bina dikme ücreti)
			corner = new Square[5];
			corner[0] = new Square(101, "START", corner_width * 4, corner_height * 4, corner_width, corner_height, 0, 0);
			corner[1] = new Square(104, "JAIL VISIT", 0, corner_height * 4, corner_width, corner_height, 0, 0);
			corner[2] = new Square(105, "PARK", 0, 0, corner_width, corner_height, 0, 0);
			corner[3] = new Square(107, "GO JAIL", corner_width * 4, 0, corner_width, corner_height, 0, 0);
			
			chance = new Square[3];
			chance[0] = new Square(103, "CHANCE", square_width_vertical * 2, square_height_vertical * 4, square_width_vertical, square_height_vertical, 0, 0);
			chance[1] = new Square(106, "CHANCE", square_width_vertical * 5, 0, square_width_vertical, square_height_vertical, 0, 0);
			chance[2] = new Square(108, "CHANCE", square_width_horizontal * 4, square_height_horizontal * 4, square_width_horizontal, square_height_horizontal, 0, 0);

			state = new Square[5];
			state[0] = new Square(102, "INCOME TAX", square_width_vertical * 6, square_height_vertical * 4, square_width_vertical, square_height_vertical, 0, 0);
			state[1] = new Square(7, "KINGS CROSS STATION", square_width_vertical * 4, square_height_vertical * 4, square_width_vertical, square_height_vertical, 200, 0);
			state[2] = new Square(7, "ELECTRIC COMPANY", 0, square_height_horizontal * 4, square_width_horizontal, square_height_horizontal, 150, 0);
			state[3] = new Square(7, "WATER WORKS", square_width_horizontal * 4, square_height_horizontal * 2, square_width_horizontal, square_height_horizontal, 150, 0);
			state[4] = new Square(109, "SUPER TAX", square_width_horizontal * 4, square_height_horizontal * 6, square_width_horizontal, square_height_horizontal, 0, 0);

			brown = new Square[2];
			brown[0] = new Square(1, "Old Kent Road", square_width_vertical * 7, square_height_vertical * 4, square_width_vertical, square_height_vertical, 60, 100);
			brown[1] = new Square(1, "Whitechapel Road", square_width_vertical * 5, square_height_vertical * 4, square_width_vertical, square_height_vertical, 60, 100);

			blue = new Square[3];
			blue[0] = new Square( 2,"The Angel Islington", square_width_vertical * 3, square_height_vertical * 4, square_width_vertical, square_height_vertical, 100, 100);
			blue[1] = new Square(2, "Euston Road", 0, square_height_horizontal * 7, square_width_horizontal, square_height_horizontal, 100, 100);
			blue[2] = new Square( 2,"Pentonville Road", 0, square_height_horizontal * 6, square_width_horizontal, square_height_horizontal, 120, 100);

			pink = new Square[2];
			pink[0] = new Square( 3,"Whitehall", 0, square_height_horizontal * 5, square_width_horizontal, square_height_horizontal, 140, 150);
			pink[1] = new Square( 3,"Northumrld Avenue", 0, square_height_horizontal * 3, square_width_horizontal, square_height_horizontal, 160, 150);

			orange = new Square[3];
			orange[0] = new Square( 4,"Bow Street", 0, square_height_horizontal * 2, square_width_horizontal, square_height_horizontal, 180, 150);
			orange[1] = new Square( 4,"Marlborough Street", square_width_vertical * 2, 0, square_width_vertical, square_height_vertical, 180, 150);
			orange[2] = new Square( 4,"Vine Street", square_width_vertical * 3, 0, square_width_vertical, square_height_vertical, 200, 150);

			red = new Square[3];
			red[0] = new Square( 5,"Strand", square_width_vertical * 4, 0, square_width_vertical, square_height_vertical, 220, 200);
			red[1] = new Square( 5,"Fleet Street", square_width_vertical * 6, 0, square_width_vertical, square_height_vertical, 220, 200);
			red[2] = new Square( 5,"Trafalgar Square", square_width_vertical * 7, 0, square_width_vertical, square_height_vertical, 240, 200);

			green = new Square[3];
			green[0] = new Square( 6,"Regend Street", square_width_horizontal * 4, square_height_horizontal * 3, square_width_horizontal, square_height_horizontal, 300, 200);
			green[1] = new Square( 6,"Oxford Street", square_width_horizontal * 4, square_height_horizontal * 5, square_width_horizontal, square_height_horizontal, 300, 200);
			green[2] = new Square( 6,"Bond Street", square_width_horizontal * 4, square_height_horizontal * 7, square_width_horizontal, square_height_horizontal, 320, 200);

			// Mekanlarý !sýrasýyla! oyun alanýna yerleþtirilmesi ( linklist ile yapmazsak oyunun baþlangýç noktasý deðiþir)
			board = new LinkedList<>();
			board.add(corner[0]);
			board.add(brown[0]);
			board.add(state[0]);
			board.add(brown[1]);
			board.add(state[1]);
			board.add(blue[0]);
			board.add(chance[0]);                   //
			board.add(corner[1]);					// Burasý mekanlarýn yerlerini ayarladðýmýz yer List yapýsýyla sýraladýk.Buradaki deðiþiklik  büyük sorunlara sebep olur.
			board.add(blue[1]);						//
			board.add(blue[2]);
			board.add(pink[0]);
			board.add(state[2]);
			board.add(pink[1]);
			board.add(orange[0]);
			board.add(corner[2]);
			board.add(orange[1]);
			board.add(orange[2]);
			board.add(red[0]);
			board.add(chance[1]);
			board.add(red[1]);
			board.add(red[2]);
			board.add(corner[3]);
			board.add(state[3]);
			board.add(green[0]);
			board.add(chance[2]);
			board.add(green[1]);
			board.add(state[4]);
			board.add(green[2]);

			// Þans kartlarýnýn yaratýlmasý
			chanceCard = new LinkedList<>();
			chanceCard.add(new Chance("Advance to GO \n Collect $200"));
			chanceCard.add(new Chance("Advance to Whitechapel Road\nIf you pass GO, collect $200"));
			chanceCard.add(new Chance("Advance to Whitehall \n If you pass Go, collect $200"));
			chanceCard.add(new Chance("Advance to KINGS CROSS STATION and pay owner twice the rental \n If Railroad is unowned, you may buy it from the Bank"));
			chanceCard.add(new Chance("Bank pays you dividend of $50"));
			chanceCard.add(new Chance("Go Back Three {3} Spaces"));
			chanceCard.add(new Chance("Go directly to Jail \n Do not pass GO, do not collect $200"));
			chanceCard.add(new Chance(" Make general repairs on all your property <br> For each house pay $25\nFor each hotel pay $100 "));
			chanceCard.add(new Chance("Pay poor tax of $15"));
			chanceCard.add(new Chance("Take a trip to Fleet Street If you pass GO, DON'T collect $200 "));
			chanceCard.add(new Chance("You have been elected Chairman of the Board\nPay each player $50"));
			chanceCard.add(new Chance("Your building loan matures \n Collect $150"));
			chanceCard.add(new Chance("You have won a crossword competition\nCollect $100"));
			chanceCard.add(new Chance("You save a cat in a tree and the grateful owner gives you $25"));
			chanceCard.add(new Chance("Speed camera!!  Roll the dice\nIf you rolled 3 or higher pay a $90 fine\nIf you rolled 6, pay $300\nOtherwise nothing happens "));
			chanceCard.add(new Chance(new String("You capture a wanted criminal \n Go to the Just Visiting space by the Jail and collect a $100 reward\nDo not collect extra money for passing GO")));
			chanceCard.add(new Chance("You bought a sports car for $200"));
			chanceCard.add(new Chance("Take a trip to Oxford Street\nIf you pass GO, DON'T collect $200"));
			chanceCard.add(new Chance("Double or nothing!\nYou gamble with $200. Roll the dice\nEven numbers: Double your money\nOdd numbers: Lose all the bet"));
			chanceCard.add(new Chance("<Get Out Of Jail Free> \n This card may be kept until needed"));

			// Kullanýcý sayýsýna göre Player objesinin yaratýlýp, baþlangýç konumlarýnýn verilmesi.
			
			// add parametresi kullanýcýlarý temsil eden görsellerin üst üste gelmemesi için eklendi
			
			player = new Player[4];
			if (display.getPlayerName() != null) {
				for (int i = 0, add = 0; i < display.getPlayerCount(); i++, add += 50) {
					player[i] = new Player(display.getPlayerName()[i], 1450, 815 + add); // player(name,x=i,y=add)
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tüm mantýksal iþlemleri gerçekleþtirdiðimiz metod. Yenilenme hýzý fps parametresi ile deðiþtirilebilir
	 */
	private void tick() {  // 4 Ocak 
		if (player[turn] != null) { 
			// Roll Dice butonuna basýlmasý, daha önce zar atýlmamýþ olmasý ve oyuncunun hapiste olmamasýnýn kontrolü
			if (display.isDiceStatus() && !rolledBefore && player[turn].getInJail() == 0 && !secondRoll) {
				while (true) { // ZARIN LOOP U
					try {
						thread.sleep(1000 / 10); // zarýn dönme hýzýný belirlediðimiz yer.
					} catch (Exception e) {
						
					}
					++tickCount;

					temp = dice_number;
					dice_number = new Random().nextInt(6);

					// Arka arkaya ayný deðer geldiðinde zar animasyonunda takýlma oluyormuþ gibi görünüyor
					// Bunun olmamasý için ayný deðer gelmesi durumunda deðer 1 arttýrýlýyor
					if (temp == dice_number) {
						dice_number = (dice_number + 1) % 6;
					}
					if (tickCount > 6) {
						tickCount = 0;
						break;
					}
					this.render();
				}
				if (((player[turn].getCurrentPlace()+dice_number) % 27)==0 &&(player[turn].getCurrentPlace() + dice_number) /27 == 1) { // 1 TUR ATIP STARTTA DURMA ÞARTI
					player[turn].setCurrentPlace((player[turn].getCurrentPlace() + dice_number) % 27);
					player[turn].setMoney(player[turn].getMoney() + 200);
					display.setTextArea(" - " + player[turn].getName() + " completed the tour and \nwait on the start earned $200");
				
				
				}
				else if (((player[turn].getCurrentPlace() + dice_number) / 27) == 1 && (player[turn].getCurrentPlace() + dice_number) % 27 != 0) { // 1 TUR ATIP STARTTA DURMADAN GEÇME ÞARTI
					player[turn].setCurrentPlace((player[turn].getCurrentPlace() + dice_number) % 27);
					player[turn].setMoney(player[turn].getMoney() + 160);
					display.setTextArea(" - " + player[turn].getName() + " completed the tour and earned $160");
				} else {
					player[turn].setCurrentPlace(player[turn].getCurrentPlace() + dice_number +1 ); // 1 gelirse ayný yerde kalmasýn diye +1 yapýyoruz.
				}

				display.setTextArea(" - " + board.get(player[turn].getCurrentPlace()).getName()); // Geldiðimiz yerin ismini TextArea ya yazýyor.

				
				// Jail Visit harici alanlar için her bir kullanýcýnýn x ve y kooordinatlarýnýn belirlenmesi
			
				if (player[turn].getCurrentPlace() != 7) { // 
					
					switch (turn) { // kaçýncý oyuncudaysa onun x teki ilerlemesi ve yerinin belirlenmesi
					
					// oyuncunun bölgelerdeki yerini belirlemek için kullandýðýmýz alan(width kýsmý ile dikey mülkiyetlerdeki oyuncularýn görünümünü saðlýyoruz.
						case 0:
							player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5);
							break;
						case 1:
							player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + 35 + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * 2);
							break;
						case 2:
							player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + 70 + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * 3);
							break;
						case 3:
							player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + 105 + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * 4);
							break;
					}
					player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
				} 
				// Jail Visit alaný için her bir kullanýcýnýn x ve y kooordinatlarýnýn belirlenmesi
				else {
					switch (turn) {
						case 0:
							player[turn].setY(810);
							break;
						case 1:
							player[turn].setY(855);
							break;
						case 2:
							player[turn].setY(900);
							break;
						case 3:
							player[turn].setY(945);
							break;
					}
					player[turn].setX(20); // 20 BÝRÝM SAÐDA OLUCAK
				}

				// Oyuncu kendine ait bir alana gelirse
				if (board.get(player[turn].getCurrentPlace()).getOwned() == turn) {
					display.setTextArea(" - This place is yours!");
				} // Oyuncu baþkasýna ait bir alana gelirse 
				else if (board.get(player[turn].getCurrentPlace()).getOwned() != -1) {
					// Alana sahip oyuncunun ayný renge sahip diðer alanlara da sahip olma durumu kontrol ediliyor
					int count = 0;
					for (int i = 0; i < player[board.get(player[turn].getCurrentPlace()).getOwned()].getOwned_place().size(); i++) {
						if (player[board.get(player[turn].getCurrentPlace()).getOwned()].getOwned_place().get(i).getId() == board.get(player[turn].getCurrentPlace()).getId()) {
							count++;
						}
					}

					int cost = 0;

					// Full Set 
					if ((count == 2 && (board.get(player[turn].getCurrentPlace()).getId() == 1 || board.get(player[turn].getCurrentPlace()).getId() == 3)) || (count == 3 && board.get(player[turn].getCurrentPlace()).getId() != 7)) {
						switch (board.get(player[turn].getCurrentPlace()).getHouseCount()) {
							case 0:
								cost = (int) ((board.get(player[turn].getCurrentPlace()).getCost() / rentFactor * 2));
								break;
							case 1:
								cost = (int) ((board.get(player[turn].getCurrentPlace()).getCost() / oneHouseFactor));
								break;
							case 2:
								cost = (int) ((board.get(player[turn].getCurrentPlace()).getCost() / twoHouseFactor));
								break;
							case 3:
								cost = (int) ((board.get(player[turn].getCurrentPlace()).getCost() / threeHouseFactor));
								break;
							case 4:
								cost = (int) ((board.get(player[turn].getCurrentPlace()).getCost() / hotelFactor));
								break;
							default:
								break;
						}
					}
					
					// Tüm devlet mülklerinin ayný kiþiye ait olmasý durumunda devlet mülküne gelme durumu 
					
					else if (count == 3 && board.get(player[turn].getCurrentPlace()).getId() == 7) {
						cost = (dice_number + 1) * 100;
					} // Boþ arsa
					else {
						cost = (int) ((board.get(player[turn].getCurrentPlace()).getCost() / rentFactor));
					}

					player[turn].setMoney(player[turn].getMoney() - cost);
					player[board.get(player[turn].getCurrentPlace()).getOwned()].setMoney(player[board.get(player[turn].getCurrentPlace()).getOwned()].getMoney() + cost);
					display.setTextArea(" - You pay $" + cost + " to " + player[board.get(player[turn].getCurrentPlace()).getOwned()].getName());
				} else if (board.get(player[turn].getCurrentPlace()).getName().contains("INCOME TAX")) {
					//INCOME TAX IN KESILDIGI YER
					player[turn].setMoney(player[turn].getMoney() - 200); 
					display.setTextArea(" - You pay INCOME TAX: $200");
				} else if (board.get(player[turn].getCurrentPlace()).getName().contains("SUPER TAX")) {
					// SUPER TAXIN KESILDIGI YER
					player[turn].setMoney(player[turn].getMoney() - 100);
					display.setTextArea(" - You pay SUPER TAX: $100");
				} 
				
				else if (board.get(player[turn].getCurrentPlace()).getName().contains("CHANCE")) {
					// En üstte olacak þekilde yeni bir frame yaratýlýr ve þans kartýnýn içeriði görüntülenir
					JFrame f = new JFrame();
					//f.setBackground(Color.orange);
					
					
					f.setSize(1000, 300); // YAZILARIN HEPSÝ GÖZÜKSÜN DÝYE BÖYLE YAPTIK. // EÐER YENÝ BÝR LÝNE EKLEMEK ÝSTERSEK <html> kullanmamýz gerekti.
					f.setResizable(false);
					f.setLocationRelativeTo(null); // bunu silince tepeye giDER.// bunu sor
					f.setVisible(true);
					
					//JTextArea message = new JTextArea();
					JLabel message1 = new JLabel();
					message1.setBounds(0,0,500,100);
					
					message1.setFont(new Font("Times New Roman",Font.BOLD,15));
					message1.setVisible(true);
					cardNumber = new Random().nextInt(20);
					message1.setText(chanceCard.get(cardNumber).getMessage()); // yeni satýr ekleymedim.!!!!!
					f.add(message1);				
					f.setAlwaysOnTop(true); // açýlan framein en üstte olmasýný saðlýyor.
					f.repaint();
					display.getFrame().setEnabled(false);// Oyuncunun þans mesajýný gerçekleþtirmeden oyuna devam edememesi için oyun penceresi disable yapýldý
				
					// Þans mesajýnýn penceresinin kapatýlmasýna müteakip gerekli case gerçekleþtirilir
					
					f.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							switch (cardNumber) {
								case 0: // Advance to GO. Collect $200
									player[turn].setCurrentPlace(0);
									player[turn].setMoney(player[turn].getMoney() + 200);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									display.setTextArea(" - " + "You advanced to GO and earned $200");
									break;
								case 1: // Advance to Whitechapel Road. If you pass GO, collect $200
									player[turn].setMoney(player[turn].getMoney() + 200);
									display.setTextArea(" - " + "You advanced to \nWhitechapel Road and earned $200");
									player[turn].setCurrentPlace(3);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									break;
								case 2: // Advance to Whitehall. If you pass GO, collect $200
									if (player[turn].getCurrentPlace() > 4) {
										player[turn].setMoney(player[turn].getMoney() + 200);
										display.setTextArea(" - " + "You advanced to \nWhitehall and earned $200");
									} else {
										display.setTextArea(" - " + "You advanced to Whitehall");
									}
									player[turn].setCurrentPlace(10);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									break;
								case 3: // Advance to KINGS CROSS STATION and pay owner twice the rental. If Railroad is unowned, you may buy it from the Bank
									player[turn].setCurrentPlace(4);
									player[turn].setMoney(player[turn].getMoney() + 200);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									display.setTextArea(" - " + "You advanced to \nKINGS CROSS STATION and earned $200");
									if ((board.get(player[turn].getCurrentPlace()).getOwned() != -1)) {
										int cost = (int) (state[1].getCost() / rentFactor * 2);
										player[turn].setMoney(player[turn].getMoney() - cost);
										player[board.get(player[turn].getCurrentPlace()).getOwned()].setMoney(player[board.get(player[turn].getCurrentPlace()).getOwned()].getMoney() + cost);
										display.setTextArea(" - You paid $" + cost + " to " + player[board.get(player[turn].getCurrentPlace()).getOwned()].getName());
									}
									break;
								case 4: // Bank pays you dividend of $50
									player[turn].setMoney(player[turn].getMoney() + 50);
									break;
								case 5: // Go Back Three {3} Spaces
									player[turn].setCurrentPlace(player[turn].getCurrentPlace() - 3);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									 if (player[turn].getCurrentPlace() != 21) { // 
											break;
										} 
								case 6: // Go directly to Jail. Do not pass GO, do not collect $200
									player[turn].setCurrentPlace(7);
									switch (turn) {
										case 0:
											player[turn].setX(100);
											player[turn].setY(810);
											break;
										case 1:
											player[turn].setX(245);
											player[turn].setY(810);
											break;
										case 2:
											player[turn].setX(100);
											player[turn].setY(900);
											break;
										case 3:
											player[turn].setX(245);
											player[turn].setY(900);
											break;
									}
									player[turn].setInJail(4);
									display.setTextArea(" - " + "You will be in jail for 3 rounds");

									if (player[turn].getJailFree() > 0) {
										player[turn].setJailFree(player[turn].getJailFree() - 1);
										player[turn].setInJail(0);
										display.setTextArea(" - " + "You get out of jail with <Get Out Of Jail Free> card\n     Remaining card(s): " + player[turn].getJailFree());
									}
									break;
								case 7: // Make general repairs on all your property. For each house pay $25. For each hotel pay $100
									int countHouse;
									int cost = 0;

									for (int i = 0; i < board.size(); i++) {
										if (board.get(i).getOwned() == turn) {
											countHouse = board.get(i).getHouseCount();
											if (countHouse == 4) {
												cost += 100;
												countHouse = 0;
											}
											cost += countHouse * 25;
										}
									}
									if (cost == 0) {
										display.setTextArea(" - " + "You don't have any house or hotel");
									} else {
										display.setTextArea(" - " + "You paid $" + cost + " to make general repairs on all your property");
									}
									break;
								case 8: // Pay poor tax of $15
									player[turn].setMoney(player[turn].getMoney() - 15);
									break;
								case 9: // Take a trip to Fleet Street. If you pass GO, DON'T collect $200
									player[turn].setCurrentPlace(19);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									display.setTextArea(" - " + "You take a trip to Fleet Street");
									break;
								case 10: // You have been elected Chairman of the Board. Pay each player $50
									for (int i = 0; i < display.getPlayerCount(); i++) {
										player[turn].setMoney(player[turn].getMoney() - 50);
										player[i].setMoney(player[i].getMoney() + 50);
									}
									break;
								case 11: // Your building loan matures. Collect $150
									player[turn].setMoney(player[turn].getMoney() + 150);
									break;
								case 12: // You have won a crossword competition. Collect $100
									player[turn].setMoney(player[turn].getMoney() + 100);
									break;
								case 13: // You save a cat in a tree and the grateful owner gives you £25
									player[turn].setMoney(player[turn].getMoney() + 25);
									break;
								case 14: // Speed camera!! If you rolled 3 or higher pay a £90 fine. If you rolled 6, pay $300, otherwise nothing happens
									secondRoll = true;
									break;
								case 15: // You capture a wanted criminal. nGo to the Just Visiting space by the Jail and collect a $100 reward. Do not collect extra money for passing GO
									player[turn].setCurrentPlace(7);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									player[turn].setMoney(player[turn].getMoney() + 100);
									display.setTextArea(" - " + "You take a trip to Oxford Street");
									break;
								case 16: // You bought a sports car for £200
									player[turn].setMoney(player[turn].getMoney() - 200);
									break;
								case 17: // Take a trip to Oxford Street. If you pass GO, DON'T collect $200
									player[turn].setCurrentPlace(25);
									player[turn].setX(board.get(player[turn].getCurrentPlace()).getX() + (turn * 35) + (board.get(player[turn].getCurrentPlace()).getWidth() - 140) / 5 * (turn + 1));
									player[turn].setY(board.get(player[turn].getCurrentPlace()).getY());
									display.setTextArea(" - " + "You take a trip to Oxford Street");
									break;
								case 18: // Double or nothing! You gamble with $200. Roll the dice. Even numbers, double your money, odd numbers, lose all the bet
									secondRoll = true;
									break;
								case 19: // Get Out Of Jail Free. This card may be kept until needed
									player[turn].setJailFree(player[turn].getJailFree() + 1);
									break;
							}
							// Ana frame'i yeniden enable yapýyoruz ve görüntüde bozulma olmamasý için repaint metodu çaðýrýldý
							display.getFrame().setEnabled(true);
							display.getFrame().repaint();
						}
					});
				} else if (board.get(player[turn].getCurrentPlace()).getName().contains("GO JAIL")) {
					// Yeni bir pencere yaratýlýp "You are going to jail" mesajý görüntülenir
					JFrame fjail = new JFrame();
					fjail.setSize(300, 100);
					fjail.setResizable(false);
					fjail.setLocationRelativeTo(null);
					fjail.setVisible(true);
					fjail.setAlwaysOnTop(true);
					display.getFrame().setEnabled(false);
					
					JLabel message = new JLabel("You are going to jail!");
					message.setBounds(0, 0, 500, 100);
					message.setHorizontalAlignment(SwingConstants.CENTER);
					//message.setEditable(false);
					message.setFont(new Font("Tahoma", 3, 12));
					message.setVisible(true);
					fjail.add(message);
					//fjail.pack(); // YAZIYI KÜÇÜLTÜYOR
					
					// Pencere kapatýlýnca kullanýcýn x ve y koordinatlarý JAIL kýsmýnýn içerisinde olacak þekilde güncellenir
					// Kullanýcýn 3 tur hapiste kalmasý için Player objesindeki setInJail parametresi ayarlanýr
					// Kullanýcýn "Get Out Of Jail Free" kartýna sahip olma durumu kontrol edilir. Eðer sahipse "setInJail" 0 olarak ayarlanýr ve kullanýcý bir
					// sonraki elde oynamaya devam edebilir
					fjail.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							player[turn].setCurrentPlace(7);
							switch (turn) {
								case 0:
									player[turn].setX(100);
									player[turn].setY(810);
									break;
								case 1:
									player[turn].setX(245);
									player[turn].setY(810);
									break;
								case 2:
									player[turn].setX(100);
									player[turn].setY(900);
									break;
								case 3:
									player[turn].setX(245);
									player[turn].setY(900);
									break;
							}
							player[turn].setInJail(4);
							display.setTextArea(" - " + "You will be in jail for 3 rounds");

							if (player[turn].getJailFree() > 0) {
								player[turn].setJailFree(player[turn].getJailFree() - 1);
								player[turn].setInJail(0);
								display.setTextArea(" - " + "You get out of jail with <Get Out Of Jail Free> card\n     Remaining card(s): " + player[turn].getJailFree());
							}
							display.getFrame().setEnabled(true);
							display.getFrame().repaint();
						}
					});
				}

				// Sonsuz döngüye girmemesi için "Roll Dice" butonuna basýlma durumu güncellenir
				// Yeniden zar atýlmamasý için "rolledBefore" true yapýlýr
				display.setDiceStatus(false);
				rolledBefore = true;
			} // Bu koþul yalnýzca 15 ve 19. þans kartlarý içindir (Kullanýcýn yeniden zar atmasýnýn gerekli olduðu durumlar)
			else if (display.isDiceStatus() && rolledBefore && secondRoll) {
				dice_number = new Random().nextInt(6);

				if (cardNumber == 14) {
					if (dice_number == 5) {
						player[turn].setMoney(player[turn].getMoney() - 300);
						display.setTextArea(" - You pay $300 fine");
					} else if (dice_number > 2) {
						player[turn].setMoney(player[turn].getMoney() - 90);
						display.setTextArea(" - You pay $90 fine");
					} else {
						display.setTextArea(" - You are so lucky and going on without any fine");
					}
				} else if (cardNumber == 18) {
					if ((dice_number + 1) % 2 == 0) {
						player[turn].setMoney(player[turn].getMoney() + 200);
						display.setTextArea(" - You won the bet! (+$200)");
					} else {
						player[turn].setMoney(player[turn].getMoney() - 200);
						display.setTextArea(" - You lose the bet! (-$200)");
					}
				}

				display.setDiceStatus(false);
				secondRoll = false;
			} else if (display.isDiceStatus() && rolledBefore) {
				display.setTextArea(" - You have already rolled dice!");
				display.setDiceStatus(false);
			} else if (display.isDiceStatus() && player[turn].getInJail() != 0) {
				display.setTextArea(" - You are still in jail! Remaining: " + (player[turn].getInJail() - 1));
				display.setDiceStatus(false);
				rolledBefore = true;
			}
		}

		// Arsa satýn alma iþlemleri
		if (display.isBuyStatus() && rolledBefore) { // 
			// Uygun
			if ((board.get(player[turn].getCurrentPlace()).getOwned() == -1) && (board.get(player[turn].getCurrentPlace()).getId() < 100) && (player[turn].getMoney() >= ((Square) board.get(player[turn].getCurrentPlace())).getCost())) {
				board.get(player[turn].getCurrentPlace()).setOwned(turn);
				player[turn].insertToOwnedPlace(board.get(player[turn].getCurrentPlace()));
				player[turn].setMoney(player[turn].getMoney() - ((Square) board.get(player[turn].getCurrentPlace())).getCost());
				display.setTextArea(" - " + player[turn].getName() + " bought " + board.get(player[turn].getCurrentPlace()).getName() + "\n. Cost: $" + ((Square) board.get(player[turn].getCurrentPlace())).getCost());
			}
			// Satýlamaz alan
			
			else if (board.get(player[turn].getCurrentPlace()).getId() > 100) { // Köþeler satýn alýnamaz.
				display.setTextArea(" - This place can't be bought!");
			} // Yetersiz bakiye
			else if (player[turn].getMoney() < ((Square) board.get(player[turn].getCurrentPlace())).getCost()) {
				display.setTextArea(" - Insufficient Balance!");
			} // Kullanýcýya ait alan
			else if (board.get(player[turn].getCurrentPlace()).getOwned() == turn) {
				display.setTextArea(" - This place is already yours!");
			} // Baþka bir kullanýcýya ait alan
			else if (board.get(player[turn].getCurrentPlace()).getOwned() != -1) {
				display.setTextArea(" - This place is already owned!");
			}

			display.setBuyStatus(false);
		} else if (display.isBuyStatus() && !rolledBefore) {
			display.setTextArea(" - First roll dice!");
			display.setBuyStatus(false);
		}
	
		// Ev/Otel satýn alma iþlemleri
		if (display.isBuyHouseStatus() && (board.get(player[turn].getCurrentPlace()).getOwned() == turn) && rolledBefore && board.get(player[turn].getCurrentPlace()).getHouseCount() < 4) {
			// Alana sahip oyuncunun ayný renge sahip diðer alanlara da sahip olma durumu kontrol ediliyor
			int count = 0;
			for (int i = 0; i < player[board.get(player[turn].getCurrentPlace()).getOwned()].getOwned_place().size(); i++) {
				if (player[board.get(player[turn].getCurrentPlace()).getOwned()].getOwned_place().get(i).getId() == board.get(player[turn].getCurrentPlace()).getId()) {
					count++;
				}
			}

			boolean isOkey = false;

			if (count == 2 && (board.get(player[turn].getCurrentPlace()).getId() == 1 || board.get(player[turn].getCurrentPlace()).getId() == 3)) {
				isOkey = true;
			} else if (count == 3 && (board.get(player[turn].getCurrentPlace()).getId() != 1 || board.get(player[turn].getCurrentPlace()).getId() != 3)) {
				isOkey = true;
			}

			// Ev/Otel satýn alma iþlemi için onay alýnmasý
			if (isOkey) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Buy House?", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					if (player[turn].getMoney() >= board.get(player[turn].getCurrentPlace()).getBuildingCost()) {
						player[turn].setMoney(player[turn].getMoney() - board.get(player[turn].getCurrentPlace()).getBuildingCost());
						board.get(player[turn].getCurrentPlace()).setHouseCount(board.get(player[turn].getCurrentPlace()).getHouseCount() + 1);
						if (board.get(player[turn].getCurrentPlace()).getHouseCount() == 4) {
							display.setTextArea(" - You bought a hotel for "+board.get(player[turn].getCurrentPlace()).getBuildingCost() +"$");
						} else {
							display.setTextArea(" - You bought a house for "+ board.get(player[turn].getCurrentPlace()).getBuildingCost()+"$");// düzelt
						}
					} else if (player[turn].getMoney() < board.get(player[turn].getCurrentPlace()).getBuildingCost()) {
						display.setTextArea(" - Insufficient Balance!");
					}
				}
			} else {
				display.setTextArea(" - You must first buy all places in this color!");
			}

			display.setBuyHouseStatus(false);
		} // Alanda zaten mevcut bir otel varsa
		else if (display.isBuyHouseStatus() && board.get(player[turn].getCurrentPlace()).getHouseCount() >= 4) {
			display.setTextArea(" - You have reached the maximum level for the property!");
			display.setBuyHouseStatus(false);
		} // Kullanýcý alaný satýn almamýþsa veya alan baþkasýna aitse
		else if (display.isBuyHouseStatus() && board.get(player[turn].getCurrentPlace()).getOwned() != turn) {
			display.setTextArea(" - You need to buy the place before\n     you can buy a house!");
			display.setBuyHouseStatus(false);
		}

		// Otel/Ev/Arsa satma iþlemleri
		if (display.isSellStatus() && rolledBefore) {
			if (board.get(player[turn].getCurrentPlace()).getOwned() == turn) {
				if (board.get(player[turn].getCurrentPlace()).getHouseCount() == 0) {
					player[turn].setMoney(player[turn].getMoney() + (board.get(player[turn].getCurrentPlace()).getCost() / 2));
					board.get(player[turn].getCurrentPlace()).setOwned(-1);

					for (int i = 0; i < player[turn].getOwned_place().size(); i++) {
						if (player[turn].getOwned_place().get(i).getName().equalsIgnoreCase(board.get(player[turn].getCurrentPlace()).getName())) {
							player[turn].getOwned_place().remove(i);
							break;
						}
					}

					display.setTextArea(" - Place Sold: +$" + (board.get(player[turn].getCurrentPlace()).getCost() / 2));
				} else {
					player[turn].setMoney(player[turn].getMoney() + (board.get(player[turn].getCurrentPlace()).getBuildingCost() / 2));
					board.get(player[turn].getCurrentPlace()).setHouseCount(board.get(player[turn].getCurrentPlace()).getHouseCount() - 1);
					display.setTextArea(" - House Sold: +$" + (board.get(player[turn].getCurrentPlace()).getBuildingCost() / 2));
				}
			} else {
				display.setTextArea(" - This place is not belonged to you!");
			}

			display.setSellStatus(false);
		} else if (display.isSellStatus() && !rolledBefore) {
			display.setSellStatus(false);
			display.setTextArea(" - First roll dice!");
		}

		// Sýranýn sonladýrýlýp, bir sonraki oyuncuya geçilme iþlemleri
		if (display.isEndStatus() && rolledBefore && !secondRoll) {
			display.setTextArea(" - Remaining Money: $" + player[turn].getMoney());
			turn = ++turn % display.getPlayerCount();
			display.setTextArea(player[turn].getName() + "'s Turn:\n");
			if (player[turn].getInJail() != 0) {
				player[turn].setInJail(player[turn].getInJail() - 1);
			}
			display.setEndStatus(false);
			display.setDiceStatus(false);
			rolledBefore = false;
		} else if (display.isEndStatus() && rolledBefore && secondRoll) {
			display.setEndStatus(false);
			display.setDiceStatus(false);
			display.setTextArea(" - Please roll the dice again!");
		} else {
			display.setEndStatus(false);
			display.setDiceStatus(false);
		}

		// Game Over koþulu
		if (player[turn] != null) {
			if (player[turn].getMoney() < 0) {
				for (int i = 0; i < display.getPlayerCount(); i++) {
					total = 0;
					total += player[i].getMoney();
					for (int j = 0; j < board.size(); j++) {
						if (board.get(j).getOwned() == i) {
							total += board.get(j).getCost();
							if (board.get(j).getHouseCount() != 0) {
								total += (board.get(j).getHouseCount() * board.get(j).getBuildingCost());
							}
						}
					}
					player[i].setTotal(total);
				}

				display.setStarted(false);
				display.setGameOver(true);

				display.getTextArea().setBounds(1500, 0, 350, 1000);
				display.getDiceButton().setVisible(false);
				display.getEndButton().setVisible(false);
				display.getBuyButton().setVisible(false);
				display.getBuyHouseButton().setVisible(false);
				display.getSellButton().setVisible(false);
			}
		}
	}

	/**
	 * init() metodunda yüklenen görsellerin ilgili pozisyonlara yerleþtirilmesi 3 ekranýmýz var: 1: Karþýlama ekraný -
	 * Oyunculardan isimlerinin girilmesinin istenir 2: Oyun ekraný 3: Oyun Sonu ekraný - Oyunculardan herhangi birinin
	 * bakiyesi eksiye düþerse oyun sonlanýr.
	 */
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();

		// Karþýlama Ekraný
		if (!display.isStarted() && !display.isGameOver()) { // Graphics kýsmýnda nasýl setColor diyince birinin oyun ekraný diðerini yazý rengi algýlýyo.
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, width, height); // GÝRÝÞ 
			//drawImage ile resimlerin boyutlarýný ayarlýyabiliyoruz.
			g.drawImage(monopolyImage, 425, 25, 1000, 500, null); // baþlangýç erkanýnda monopolynin yerini belirliyoruz.
			g.setColor(Color.black);
			g.drawImage(TedImage, 1600, 20, 200, 200, null);
			g.setFont(new Font("Tahoma", 3, 35));
			g.drawString("Player1:", 700, 650);
			g.drawString("Player2:", 700, 700);
			g.drawString("Player3:", 700, 750);
			g.drawString("Player4:", 700, 800);
		}
		// Oyun Ekraný
		
		
		else if (display.isStarted() && !display.isGameOver()) {
			// Player yaratýlmadan kullanýlmaya çalýþýldýðý için NullPointerException atýyor
			// Bunu engellemek için init metodu çaðýrýlarak Player nesneleri yaratýlmasý saðlanýyor
			if (!alreadyExecuted) {
				init();
				display.setTextArea(player[turn].getName() + "'s Turn:\n");
				alreadyExecuted = true; // bunu false yaparsak textArea infinite loop a giriyor.
			}
			g.clearRect(0, 0, width, height);
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, width - 350, height); // ortanýn rengi
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(1500, 0, 350, height); // Butonlarýn yerinin rengi
			
			g.drawImage(chance_image, 830, 530, 350, 250, null);
			g.drawImage(monopolyImage, 500, 270, 500, 250, null);// ortadaki monopoly img inin ayarlanmasý
			g.drawImage(diceImage[dice_number], corner_width + 100, corner_height + 100, 50, 50, null);
			g.drawImage(start_image, corner[0].getX(), corner[0].getY(), corner[0].getWidth(), corner[0].getHeight(), null);
			g.drawImage(jail_image, corner[1].getX(), corner[1].getY(), corner[1].getWidth(), corner[1].getHeight(), null);
			g.drawImage(park_image, corner[2].getX(), corner[2].getY(), corner[2].getWidth(), corner[2].getHeight(), null);
			g.drawImage(go_jail_image, corner[3].getX(), corner[3].getY(), corner[3].getWidth(), corner[3].getHeight(), null);

			g.drawImage(chance1_image, chance[0].getX(), chance[0].getY(), chance[0].getWidth(), chance[0].getHeight(), null);
			g.drawImage(blue1_image, blue[0].getX(), blue[0].getY(), blue[0].getWidth(), blue[0].getHeight(), null);
			g.drawImage(train_image, state[1].getX(), state[1].getY(), state[1].getWidth(), state[1].getHeight(), null);
			g.drawImage(tax1_image, state[0].getX(), state[0].getY(), state[0].getWidth(), state[0].getHeight(), null);
			g.drawImage(brown2_image, brown[1].getX(), brown[1].getY(), brown[1].getWidth(), brown[1].getHeight(), null);
			g.drawImage(brown1_image, brown[0].getX(), brown[0].getY(), brown[0].getWidth(), brown[0].getHeight(), null);

			g.drawImage(orange1_image, orange[0].getX(), orange[0].getY(), orange[0].getWidth(), orange[0].getHeight(), null);
			g.drawImage(pink2_image, pink[1].getX(), pink[1].getY(), pink[1].getWidth(), pink[1].getHeight(), null);
			g.drawImage(electric_image, state[2].getX(), state[2].getY(), state[2].getWidth(), state[2].getHeight(), null);
			g.drawImage(pink1_image, pink[0].getX(), pink[0].getY(), pink[0].getWidth(), pink[0].getHeight(), null);
			g.drawImage(blue3_image, blue[2].getX(), blue[2].getY(), blue[2].getWidth(), blue[2].getHeight(), null);
			g.drawImage(blue2_image, blue[1].getX(), blue[1].getY(), blue[1].getWidth(), blue[1].getHeight(), null);

			g.drawImage(orange2_image, orange[1].getX(), orange[1].getY(), orange[1].getWidth(), orange[1].getHeight(), null);
			g.drawImage(orange3_image, orange[2].getX(), orange[2].getY(), orange[2].getWidth(), orange[2].getHeight(), null);
			g.drawImage(red1_image, red[0].getX(), red[0].getY(), red[0].getWidth(), red[0].getHeight(), null);
			g.drawImage(chance2_image, chance[1].getX(), chance[1].getY(), chance[1].getWidth(), chance[1].getHeight(), null);
			g.drawImage(red2_image, red[1].getX(), red[1].getY(), red[1].getWidth(), red[1].getHeight(), null);
			g.drawImage(red3_image, red[2].getX(), red[2].getY(), red[2].getWidth(), red[2].getHeight(), null);

			g.drawImage(water_image, state[3].getX(), state[3].getY(), state[3].getWidth(), state[3].getHeight(), null);
			g.drawImage(green1_image, green[0].getX(), green[0].getY(), green[0].getWidth(), green[0].getHeight(), null);
			g.drawImage(green2_image, green[1].getX(), green[1].getY(), green[1].getWidth(), green[1].getHeight(), null);
			g.drawImage(green3_image, green[2].getX(), green[2].getY(), green[2].getWidth(), green[2].getHeight(), null);
			g.drawImage(chance3_image, chance[2].getX(), chance[2].getY(), chance[2].getWidth(), chance[2].getHeight(), null);
			g.drawImage(tax2_image, state[4].getX(), state[4].getY(), state[4].getWidth(), state[4].getHeight(), null);

			switch (display.getPlayerCount()) { // Oyuncularýn yerlerini ve boyutlarýný belirleme
				case 4:
					g.drawImage(player4_image, player[3].getX(), player[3].getY(), player[3].getWidth(), player[3].getHeight(), null); 

				case 3:
					g.drawImage(player3_image, player[2].getX(), player[2].getY(), player[2].getWidth(), player[2].getHeight(), null);
				case 2:
					g.drawImage(player2_image, player[1].getX(), player[1].getY(), player[1].getWidth(), player[1].getHeight(), null);
				case 1:
					g.drawImage(player1_image, player[0].getX(), player[0].getY(), player[0].getWidth(), player[0].getHeight(), null);
			}

			for (int i = 0; i < board.size(); i++) { //ÖNEMLÝÝÝ
				Square temp = board.get(i);
				switch (temp.getHouseCount()) {
					case 1:
						if (temp.getWidth() == 150) {
							g.drawImage(houseImage[0], temp.getX() + (temp.getWidth() - 50) / 2, 35 + temp.getY() + (temp.getHeight() - 50) / 2, 50, 50, null);
						} else if (temp.getWidth() == 300) {
							if (temp.getX() == 0) {
								g.drawImage(houseImage[0], temp.getX() + 80, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							} else {
								g.drawImage(houseImage[0], temp.getX() + 170, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							}
						}
						break;
					case 2:
						if (temp.getWidth() == 150) {
							g.drawImage(houseImage[1], temp.getX() + (temp.getWidth() - 50) / 2, 35 + temp.getY() + (temp.getHeight() - 50) / 2, 50, 50, null);
						} else if (temp.getWidth() == 300) {
							if (temp.getX() == 0) {
								g.drawImage(houseImage[1], temp.getX() + 80, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							} else {
								g.drawImage(houseImage[1], temp.getX() + 170, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							}
						}
						break;
					case 3:
						if (temp.getWidth() == 150) {
							g.drawImage(houseImage[2], temp.getX() + (temp.getWidth() - 50) / 2, 35 + temp.getY() + (temp.getHeight() - 50) / 2, 50, 50, null);
						} else if (temp.getWidth() == 300) {
							if (temp.getX() == 0) {
								g.drawImage(houseImage[2], temp.getX() + 80, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							} else {
								g.drawImage(houseImage[2], temp.getX() + 170, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							}
						}
						break;
					case 4:
						if (temp.getWidth() == 150) {
							g.drawImage(hotel_image, temp.getX() + (temp.getWidth() - 50) / 2, 35 + temp.getY() + (temp.getHeight() - 50) / 2, 50, 50, null);
						} else if (temp.getWidth() == 300) {
							if (temp.getX() == 0) {
								g.drawImage(hotel_image, temp.getX() + 80, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							} else {
								g.drawImage(hotel_image, temp.getX() + 170, temp.getY() + 15 + (temp.getHeight() - 50) / 2, 50, 50, null);
							}
						}
						break;
					default:
						break;
				}
				
		
				
			}
		} // Oyun Sonu Ekraný
		else if (!display.isStarted() && display.isGameOver()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, width, height);

			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahoma", 3, 65));
			g.drawString("Game Over", 600, 200);
			g.setFont(new Font("Tahoma", 3, 30));
			for (int i = 0; i < display.getPlayerCount(); i++) {
				g.drawString(player[i].getName() + ": " + player[i].getTotal(), 750, 400 + i * 50);
			}
		}

		bs.show();
		g.dispose();
	}

	public void run() {
		init();

		while (running) {
			tick();
			render();
			try {
				thread.sleep(1000 / fps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		stop();
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
