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
			// if(!in.hasNext( )){
			// 	return;
			// }
			// String command = in.next();
			// if (command.equals("Quit")) {
			// 	return;
			// } else {
			// 	executeCommand(command);
			// }

			if(!in.hasNext( )){
				return;
			} else {
				executeCommand(in);
			}
		}
	}
	
	public void executeCommand(Scanner command) throws IOException{
		String PlayerNum = command.next();
		if ( PlayerNum.equals("PLAYER")) {
			int playerNo = in.nextInt();
			String test = in.next();
			System.out.println(test);
		}
	}
}
