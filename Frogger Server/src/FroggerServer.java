import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FroggerServer {
    final static int SERVER_PORT = 5556;

    private Frog frog;
	Background background;

	public void startgame() {
		background = new Background();
		background.setWidth(1000);
		background.setHeight(800);
		background.setImage("background.png");

		frog = new Frog();
		frog.setX(450);
		frog.setY(750);
		frog.setWidth(50);
		frog.setHeight(39);
		frog.setImage("frog-sprite.png");
	}

    public static void main(String[] args) throws IOException {


		ServerSocket server = new ServerSocket(SERVER_PORT);
		System.out.println("Waiting for clients to connect...");
		while(true) {
			Socket s = server.accept();
			System.out.println("client connected");
			
			FroggerServerService myService = new FroggerServerService (s);
			Thread t = new Thread(myService);
			t.start();
		}
	}
}
