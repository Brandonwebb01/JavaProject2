import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FrogClient implements KeyListener {

    private Frog frog;
    static OutputStream outputStream;
    static Socket s;
    static PrintWriter out;

    final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;

	public static void main(String[] args) throws IOException {


		final ServerSocket client = new ServerSocket(CLIENT_PORT);
		
		
		//set up listening server
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
					
					System.out.println("Waiting for server responses...");
					while(true) {
						Socket s2;
						try {
							s2 = client.accept();
							FrogClientService myService = new FrogClientService (s2);
							Thread t = new Thread(myService);
							t.start();
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("client connected");
						
					}

					
				}
			}
		});
		t1.start( );
        

        //set up a communication socket
        try {
            s = new Socket("localhost", SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initalize data stream to send data out
        try {
            outputStream = s.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out = new PrintWriter(s.getOutputStream(), true);
    }   

    //implement key listener
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
        
            try {
                OutputStream outputStream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream);
    
                String command = "UP";
                out.println("Sending: " +command);
                out.println(command);
                out.flush();
    
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                OutputStream outputStream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream);
    
                String command = "DOWN";
                out.println("Sending: " +command);
                out.println(command);
                out.flush();
    
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            try {
                OutputStream outputStream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream);
    
                String command = "LEFT";
                out.println("Sending: " +command);
                out.println(command);
                out.flush();
    
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            try {
                OutputStream outputStream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream);
    
                String command = "RIGHT";
                out.println("Sending: " +command);
                out.println(command);
                out.flush();
    
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}
}

