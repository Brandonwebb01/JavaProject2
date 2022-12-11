import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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

			//send x, y, car id, and car number to client
			Socket s2 = new Socket("localhost", CLIENT_PORT);

			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			for (int i = 0; i < cars.length; i++) {
				String commandOut = "CAR "+ cars[i].getX() +" " + cars[i].getY() + " " + cars[i].getCarID() + " " + cars[i].getCarNumber() + "\n";
				out.println(commandOut);
				out.flush();
			}

			s2.close();

		} else {
			System.out.println("Invalid command");
		}
	}
}
