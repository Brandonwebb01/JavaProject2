import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Player Character
public class Frog extends Sprite {

	private JLabel frogLabel = new JLabel();
	private ImageIcon frogImage;
	private ImageIcon frogImageDown;
	private ImageIcon frogImageRight;
	private ImageIcon frogImageLeft;
	//private Thread t;
	private ArrayList<Log[]> logs;

	//Constructor
	public Frog() {
		super(0, 0, 39, 50, "frog-sprite.png");	
		this.setX(450);
		this.setY(750);
		this.setWidth(50);
		this.setHeight(39);
		this.setImage("frog-sprite.png");

		frogImage = new ImageIcon(getClass().getResource(this.getImage()));
		frogImageDown = new ImageIcon(getClass().getResource("frog-sprite-down.png"));
		frogImageRight = new ImageIcon(getClass().getResource("frog-sprite-right.png"));
		frogImageLeft = new ImageIcon(getClass().getResource("frog-sprite-left.png"));
		frogLabel.setIcon(frogImage);
		frogLabel.setSize(50, 39);
		frogLabel.setLocation(this.getX(), this.getY());
	}

	//getters and setters for imageicons
	public ImageIcon getFrogImage() {
		return frogImage;
	}

	public void setFrogImage(ImageIcon frogImage) {
		this.frogImage = frogImage;
	}

	public ImageIcon getFrogImageRight() {
		return frogImageRight;
	}

	public void setFrogImageRight(ImageIcon frogImageRight) {
		this.frogImageRight = frogImageRight;
	}

	public ImageIcon getFrogImageDown() {
		return frogImageDown;
	}

	public void setFrogImageDown(ImageIcon frogImageDown) {
		this.frogImageDown = frogImageDown;
	}

	public ImageIcon getFrogImageLeft() {
		return frogImageLeft;
	}

	public void setFrogImageLeft(ImageIcon frogImageLeft) {
		this.frogImageLeft = frogImageLeft;
	}

	// setter and getter for frogLabel
	public JLabel getFrogLabel() {
		return frogLabel;
	}

	public void setFrogLabel(JLabel frogLabel) {
		this.frogLabel = frogLabel;
	}

	// getter and setter for logs
	public ArrayList<Log[]> getLogs() {
		return logs;
	}

	public void setLogs(ArrayList<Log[]> logs) {
		this.logs = logs;
	}

	// @Override
	// public void run() {			
	// 	t = new Thread(this, "Frog Logic Thread");
	// 	t.start();
	// }
	
	// public void startFrogLogic() {
	// 	t = new Thread(this, "Frog Logic Thread");
	// 	t.start();
	// }

	// method to check if frog is in water
	public boolean isInWater() {
		if (getY() > 35 && getY() < 350) {
			return true;
		}
		return false;
	}

	//check if frog is on a log
	public boolean isOnLog() {
        //check if frog iscolliding with it
        for (int i = 0; i < logs.size(); i++) {
            for (int j = 0; j < logs.get(i).length; j++) {
                // if log is colloding with frog return true
                if (this.isColliding(logs.get(i)[j])) {
                    return true;
                }
            }
        }
        return false;
    }

	public void moveFrog(KeyEvent e) {
        int xPos = getX();
        int yPos = getY();

        //modify position
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            yPos -= GameProperties.CHARACTER_STEP;
            frogLabel.setIcon(getFrogImage());
            if (yPos + getHeight() <= 0) {
                yPos = GameProperties.SCREEN_HEIGHT;
            }

            // set x and y
            setX(xPos);
            setY(yPos);

            //splash();


        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            yPos += GameProperties.CHARACTER_STEP;
            frogLabel.setIcon(getFrogImageDown());
            if (yPos >= GameProperties.SCREEN_HEIGHT) {
                yPos = -1 * getHeight();
            }

            // set x and y
            setX(xPos);
            setY(yPos);

            //splash();

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            xPos -= GameProperties.CHARACTER_STEP;
            frogLabel.setIcon(getFrogImageLeft());
            if (xPos + getWidth() <= 0) {
                xPos = GameProperties.SCREEN_WIDTH;
            }

            // set x and y
            setX(xPos);
            setY(yPos);

            //splash();

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            xPos += GameProperties.CHARACTER_STEP;
            frogLabel.setIcon(getFrogImageRight());
            if (xPos >= GameProperties.SCREEN_WIDTH) {
                xPos = -1 * getWidth();
            }

            // set x and y
            setX(xPos);
            setY(yPos);

            //splash();

        } else {
            System.out.println("Invalid operation");
        }

        //update graphic
        frogLabel.setLocation(getX(), getY());
    }

	public void splash() {
        if (!isOnLog() && isInWater()) {
			System.out.println("SPASH!!!");
			resetFrog();
		}
    }

	//reset frog to starting position
	public void resetFrog() {
		setX(450);
		setY(750);
		frogLabel.setLocation(450, 750);
	}
}