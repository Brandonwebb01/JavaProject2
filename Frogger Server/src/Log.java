import javax.swing.JLabel;

//Player Character
public class Log extends Sprite implements Runnable {
	
	private Boolean visible, moving, reverseDirection;
	private Thread t;
	private JLabel logLabel;
	private Frog Frog;
	private int logID;
	
	//Constructor
	public Log() {
		super(0, 0, 50, 75, "log-sprite.png");
		this.visible = true;
		this.moving = false;
		this.reverseDirection = true;
	}

	public int getLogID() {
        return logID;
    }

    public void setLogID(int carID) {
        this.logID = carID;
    }
	
	public void setLogLabel(JLabel temp) {
		this.logLabel = temp;
	}
	
	public void setFrog(Frog temp) {
		this.Frog = temp;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	
	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = true;
	}

	public Boolean getReverseDirection() {
		return reverseDirection;
	}

	public void setReverseDirection(Boolean reverseDirection) {
		this.reverseDirection = reverseDirection;
	}
	
	public void Display () {
		System.out.println("x,y: " + this.x + "," + this.y);
		System.out.println("width,height: " + this.width + "," + height);
		System.out.println("image: " + this.image);
		System.out.println("visible: " + this.visible);
		System.out.println("moving: " + this.moving);
	}
	
	public void startMoving() {
		System.out.println("Move!");
		if (!this.moving) {
			t = new Thread(this, "Log Thread");
			t.start();
		}
	}

	//stop moving
	public void stopLog() {
		this.moving = false;
	}

	@Override
	public void run() {
		System.out.println("Thread started");
		this.moving = true;

		if (getLogID() == 1) {
            try {
				Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (getLogID() == 2) {
            try {
				Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (getLogID() == 3) {
            try {
				Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }			
        }
		
		while (this.moving) {

		if (this.reverseDirection == false) {
				
				//get current x
				int currentX = this.x;
	
				//increase x
				currentX += 20;
	
				//boundary check right-side
				if (currentX >= GameProperties.SCREEN_WIDTH) {
					currentX = -1 * this.width;
				}
				this.setX(currentX);
				
				//update Character2Label
				this.logLabel.setLocation(this.x, this.y);
				
				//pause
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
		} else {
				
				//get current x
				int currentX = this.x;
	
				//decrease x
				currentX -= 20;

	
				//boundary check left-side
				if (currentX <= -1 * this.width) {
					currentX = GameProperties.SCREEN_WIDTH;
				}
				this.setX(currentX);
			
				//update Character2Label
				this.logLabel.setLocation(this.x, this.y);
				
				//pause
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
			
			if ( this.visible ) {
				if (isColliding(Frog)) {
					System.out.println("On Log");
					Frog.setX(this.x);
					Frog.getFrogLabel().setLocation(getX() + 45, getY());
					if (this.reverseDirection == true) {
						Frog.getFrogLabel().setLocation(getX(), getY());
					}
				}
			}
		}
		
		System.out.println("End Thread");	
	}
}