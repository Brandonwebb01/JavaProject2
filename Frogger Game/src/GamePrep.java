import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GamePrep extends JFrame implements KeyListener, ActionListener, Runnable {
	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;
	//instances of our data classes (store position, etc here)
	private Frog frog;
	private Background background;
	private int score, finalScore;
	private SqlDatabase sql = new SqlDatabase();

	//array of cars
	private Car[] cars = new Car[3];
	private Car[] cars2 = new Car[3];
	private Car[] cars3 = new Car[3];
	private Car[] cars4 = new Car[3];
	private Car[] cars5 = new Car[3];

	//array of logs
	private Log[] logs = new Log[3];
	private Log[] logs2 = new Log[3];
	private Log[] logs3 = new Log[3];
	private Log[] logs4 = new Log[3];
	private Log[] logs5 = new Log[3];
	private Log[] logs6 = new Log[3];
	
	//graphic elements
	private Container content;
	private JLabel frogLabel, carLabel, backgroundLabel, logLabel, scoreLabel, oldScoreLabel;
	private ImageIcon carImage, backgroundImage, logImage;
	private Boolean runThread = true;
	
	//buttons
	private JButton StartButton, RestartButton, SaveScoreButton;
	
	public GamePrep() {
		
		int oldScore = sql.getScore();
			
		//set up background
		background = new Background();
		background.setX(0);
		background.setY(0);
		background.setImage("background.png");
		background.setWidth(1000);
		background.setHeight(800);

		//graphic element instantiation and add to screen
		backgroundLabel = new JLabel();
		backgroundImage = new ImageIcon(getClass().getResource(background.getImage()));
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setSize(background.getWidth(), background.getHeight());
		backgroundLabel.setLocation(background.getX(),background.getY());

		frog = new Frog();
		frogLabel = frog.getFrogLabel();

		//set up screen
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		content.setBackground(Color.gray);
		setLayout(null);
		
		createCars(cars, -75, 450);
		createCars(cars2, -75, 510);
		createCars(cars3, -75, 570);
		createCars(cars4, -75, 630);
		createCars(cars5, -75, 690);

		createLogs(logs, -75, 50, true);
		createLogs(logs2, -75, 100, false);
		createLogs(logs3, -75, 150, true);
		createLogs(logs4, -75, 200, false);
		createLogs(logs5, -75, 250, true);
		createLogs(logs6, -75, 300, false);
		
		//add a start button
		StartButton = new JButton ("Start");
		StartButton.setSize(100, 100);
		StartButton.setLocation(GameProperties.SCREEN_WIDTH/2 - StartButton.getWidth()/2,
								GameProperties.SCREEN_HEIGHT/2 - StartButton.getHeight()/2);
		StartButton.setFocusable(false);

		//add a restart button
		RestartButton = new JButton ("Restart");
		RestartButton.setSize(100, 25);
		RestartButton.setLocation(900, 762);
		RestartButton.setFocusable(false);
		RestartButton.setVisible(false);
		
		//add a Save Score Button
		SaveScoreButton = new JButton ("Save Score");
		SaveScoreButton.setSize(100, 25);
		SaveScoreButton.setLocation(900, 737);
		SaveScoreButton.setFocusable(false);
		SaveScoreButton.setVisible(false);

		//add score label
		scoreLabel = new JLabel("Score: " + score);
		scoreLabel.setSize(100, 25);
		scoreLabel.setLocation(800, 0);
		scoreLabel.setVisible(true);
		
		oldScoreLabel = new JLabel("Saved Score: " + oldScore);
		oldScoreLabel.setSize(150, 25);
		oldScoreLabel.setLocation(30, 0);
		oldScoreLabel.setVisible(true);

		

		//populate screen
		add(StartButton);
		add(scoreLabel);
		StartButton.addActionListener(this);
		add(RestartButton);
		add(SaveScoreButton);
		add(oldScoreLabel);
		SaveScoreButton.addActionListener(this);
		RestartButton.addActionListener(this);
		add(frogLabel);
		add(carLabel);
		add(logLabel);
		add(backgroundLabel);
		
		content.addKeyListener(this);
		content.setFocusable(true);
		content.setComponentZOrder(frogLabel, 0);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// function to start the clientlistener thread
	public void startClientListener() throws UnknownHostException, IOException {
		final ServerSocket client = new ServerSocket(CLIENT_PORT);
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
					while(true) {
						Socket s2 = null;
						try {
							s2 = client.accept();
						} catch (IOException e) {
							e.printStackTrace();
						}
						FrogClientService myService = new FrogClientService (s2);
						Thread t = new Thread(myService);
						t.start();
					}
				}
			}
		});
		t1.start();
	}

	
	public static void main( String args []) throws UnknownHostException, IOException {
		GamePrep myGame = new GamePrep();
		myGame.setVisible(true);
		myGame.startClientListener();
	}

	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyPressed(KeyEvent e) {
		frog.moveFrog(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {}	

	@Override
	public void actionPerformed(ActionEvent e) {
		//distinguish among buttons
		if (e.getSource() == StartButton) {
			
//			SqlDatabase sql = new SqlDatabase();
//			System.out.println(sql.getScore());
			
			//hide start button when game starts
			StartButton.setVisible(false);
			RestartButton.setVisible(true);
			SaveScoreButton.setVisible(false);
			oldScoreLabel.setVisible(false);
			runThread = true;
			// start counter
			score = 5000;
			scoreLabel.setText("Score: " + score);
			scoreLogic();

			showCarsArray(cars);
			showCarsArray(cars2);
			showCarsArray(cars3);
			showCarsArray(cars4);
			showCarsArray(cars5);

			//list of log arrays for frog class
			ArrayList<Log[]> logsList = new ArrayList<Log[]>();
			logsList.add(logs);
			logsList.add(logs2);
			logsList.add(logs3);
			logsList.add(logs4);
			logsList.add(logs5);
			logsList.add(logs6);

			showLogsArray(logs);
			showLogsArray(logs2);
			showLogsArray(logs3);
			showLogsArray(logs4);
			showLogsArray(logs5);
			showLogsArray(logs6);

			frog.setLogs(logsList);
			// frog.run();
		}
		else if (e.getSource() == RestartButton) {
			StartButton.setVisible(true);
			RestartButton.setVisible(false);
			SaveScoreButton.setVisible(false);
			frog.resetFrog();
			//stop all threads
			for (int i = 0; i < cars.length; i++) {
				cars[i].stopCar();
				cars2[i].stopCar();
				cars3[i].stopCar();
				cars4[i].stopCar();
				cars5[i].stopCar();
			}

			for (int i = 0; i < logs.length; i++) {
				logs[i].stopLog();
				logs2[i].stopLog();
				logs3[i].stopLog();
				logs4[i].stopLog();
				logs5[i].stopLog();
				logs6[i].stopLog();
			}
		} else if (e.getSource() == SaveScoreButton){
			sql.setScore(Integer.toString(finalScore));
		}
	}

	//create logs
	public void createCars(Car[] car, int x, int y) {
        for (int i = 0; i < car.length; i++) {
            car[i] = new Car();
            car[i].setY(y);
            car[i].setX(x);
            car[i].setFrog(frog);

            carLabel = new JLabel();
            carImage = new ImageIcon(getClass().getResource(car[i].getImage()));
            carLabel.setIcon(carImage);
            carLabel.setSize(car[i].getWidth(), car[i].getHeight());
            carLabel.setLocation(car[i].getX(), car[i].getY());
            car[i].setCarLabel(carLabel);
            content.add(carLabel);
        }
    }

	//create logs
	public void createLogs(Log[] log, int x, int y, boolean rDirection) {
		for (int i = 0; i < log.length; i++) {
			log[i] = new Log();
			log[i].setY(y);
			log[i].setX(x);
			log[i].setFrog(frog);
			log[i].setReverseDirection(rDirection);

			logLabel = new JLabel();
			logImage = new ImageIcon(getClass().getResource(log[i].getImage()));
			logLabel.setIcon(logImage);
			logLabel.setSize(log[i].getWidth(), log[i].getHeight());
			logLabel.setLocation(log[i].getX(), log[i].getY());
			log[i].setLogLabel(logLabel);
			content.add(logLabel);
		}
	}

	//show car array
	public void showCarsArray(Car[] carArray) {
		for (int i = 0; i < carArray.length; i++) {
			carArray[i].setCarID(i);
			if ( carArray[i].getMoving()) {
				carArray[i].setVisible(false);
				carArray[i].setMoving(false);
			} else {
				carArray[i].setVisible(true);
				carArray[i].startMoving();
			}
		}
	}

	//show log array
	public void showLogsArray(Log[] logArray) {
		for (int i = 0; i < logArray.length; i++) {
			logArray[i].setLogID(i);
			if ( logArray[i].getMoving()) {
				logArray[i].setVisible(false);
				logArray[i].setMoving(false);
			} else {
				logArray[i].setVisible(true);
				logArray[i].startMoving();
			}
		}
	}

	//start thread function
	public void scoreLogic() {
		Thread t = new Thread(this);
		t.start();
	}


	//runnable method
	@Override
	public void run() {
		while (runThread && score != 0) {
			try {
				score = score - 150;
				scoreLabel.setText("Score: " + score);
				if (frog.getY() < 20) {
					finalScore = score;
					SaveScoreButton.setVisible(true);
					scoreLabel.setText("You Win! " + finalScore);
					frog.resetFrog();
					runThread = false;
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (score < 1) {
				scoreLabel.setText("You Lose!");
				frog.resetFrog();
				runThread = false;
			}
		}
	}
}

