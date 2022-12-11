import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


//processing routine on server (B)
public class ServerService implements Runnable {
	final int CLIENT_PORT = 5656;

	private Socket s;
	private Scanner in;
	private Frog frog;
	private int carNum = 0;

	private CarServer[] cars = new CarServer[3];
	private CarServer[] cars2 = new CarServer[3];
	private CarServer[] cars3 = new CarServer[3];
	private CarServer[] cars4 = new CarServer[3];
	private CarServer[] cars5 = new CarServer[3];

	public ServerService (Socket aSocket) {
		this.s = aSocket;
	}

	public void createCars(CarServer[] car, int x, int y) {
        for (int i = 0; i < car.length; i++) {
            car[i] = new CarServer();
            car[i].setY(y);
            car[i].setX(x);
            car[i].setFrog(frog);
        }
    }

	public void showCarsArray(CarServer[] carArray) {
		for (int i = 0; i < carArray.length; i++) {
			carArray[i].setCarID(i);
			carArray[i].setCarNumber(carNum++);
			if ( carArray[i].getMoving()) {
				carArray[i].setVisible(false);
				carArray[i].setMoving(false);
			} else {
				carArray[i].setVisible(true);
				carArray[i].startMoving();
			}
		}
	}

	public void run() {
		
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//processing the requests
	public void processRequest () throws IOException {
		//if next request is empty then return
		while(true) {
			if(!in.hasNext( )){
				return;
			} else {
				executeCommand(in);
			}
		}
	}

	public void sendResult(String commandOut) throws UnknownHostException, IOException {
		//send a response
		Socket s2 = new Socket("localhost", CLIENT_PORT);

		//Initialize data stream to send data out
		OutputStream outstream = s2.getOutputStream();
		PrintWriter out = new PrintWriter(outstream);

		//String commandOut = "PLAYER "+ playerNo +" " + movementCommand + "\n";
		out.println(commandOut);
		out.flush();
			
		s2.close();
	}
	
	public void executeCommand(Scanner command) throws IOException{
		String PlayerName = command.next();
		if ( PlayerName.equals("PLAYER")) {
			int playerNo = in.nextInt();
			int movementCommand = in.nextInt();
			System.out.println("Player "+playerNo+" moves "+movementCommand);
			
			
			//send a response
			Socket s2 = new Socket("localhost", CLIENT_PORT);
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String commandOut = "PLAYER "+ playerNo +" " + movementCommand + "\n";
			out.println(commandOut);
			out.flush();
				
			s2.close();
		} else if (PlayerName.equals("START")) {

			createCars(cars, -75, 450);
			createCars(cars2, -75, 510);
			createCars(cars3, -75, 570);
			createCars(cars4, -75, 630);
			createCars(cars5, -75, 690);

			showCarsArray(cars);
			showCarsArray(cars2);
			showCarsArray(cars3);
			showCarsArray(cars4);
			showCarsArray(cars5);

		} else if (PlayerName.equals("KEYPRESSED")) {
			int movementCommand = command.nextInt();
			int frogX = command.nextInt();
			int frogY = command.nextInt();

			if (movementCommand == 38) {
				frogY -= GameProperties.CHARACTER_STEP;
				if (frogY + 39 <= 0) {
					frogY = GameProperties.SCREEN_HEIGHT;
				}
			} else if (movementCommand == 40) {
				frogY += GameProperties.CHARACTER_STEP;
				if (frogY >= GameProperties.SCREEN_HEIGHT) {
					frogY = -1 * 39;
				}
			} else if (movementCommand == 37) {
				frogX -= GameProperties.CHARACTER_STEP;
				if (frogX + 50 <= 0) {
					frogX = GameProperties.SCREEN_WIDTH;
				}
			} else if (movementCommand == 39) {
				frogX += GameProperties.CHARACTER_STEP;
				if (frogX >= GameProperties.SCREEN_WIDTH) {
					frogX = -1 * 50;
				}
			}
			String returnLocationResults = "FROGLOC " +  frogX + " " + frogY;
			System.out.println(returnLocationResults);
			sendResult(returnLocationResults);
		} else {
			System.out.println("Invalid command");
		}
	}
}
