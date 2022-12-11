import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//processing routine on server (B)
public class FrogClientService implements Runnable {

	private Socket s;
	private Scanner in;

	public FrogClientService (Socket aSocket) {
		this.s = aSocket;
	}
	public void run() {
		
		try {
			in = new Scanner(s.getInputStream());
			processRequest();
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
			String test = in.next();
			System.out.println(test);
		} else if (PlayerName.equals("CARLOC")) {
			//System.out.println("X Position: " + command.next() + " Y Position: " + command.next() + " Car ID: " + command.next() + " Car Number: " + command.next());
			//update car position
			int carX = command.nextInt();
			int carY = command.nextInt();

			GamePrep.updateCarLabelPosition(carX, carY);
			GamePrep.setCarX(carX);
			GamePrep.setCarY(carY);
			
		 }else if (PlayerName.equals("FROGLOC")) {
			int frogX = command.nextInt();
			int frogY = command.nextInt();

			GamePrep.updateFrogLabelPosition(frogX, frogY);
			GamePrep.setFrogX(frogX);
			GamePrep.setFrogY(frogY);
		}


	}
}
