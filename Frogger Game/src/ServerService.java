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

	public ServerService (Socket aSocket) {
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
			int movementCommand = in.nextInt();
			//String playerAction = in.next();
			System.out.println("Player "+playerNo+" moves "+movementCommand);
			//System.out.println("Command: " + command);
			
			
			//send a response
			Socket s2 = new Socket("localhost", CLIENT_PORT);
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String commandOut = "PLAYER "+ playerNo +" TESTESTEST 500 400\n";
			//System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();
				
			s2.close();

		}
	}
}
