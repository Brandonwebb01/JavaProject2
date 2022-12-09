import java.awt.Rectangle;

/*
	1. Create the class skeleton
	2. Identify all class attributes (data members)
	3. getters and setters (accessors, mutators)
	4. default constructor
	5. secondary constructors
	6. print/display function
	7. any other code
	8. run in an application
*/
public class Sprite {
	protected int x, y; //upper left, top position
	protected int height, width;
	protected String image;
	protected Rectangle r;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		this.updateRectanglePosition();
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.updateRectanglePosition();
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		this.updateRectangleSize();
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		this.updateRectangleSize();
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Sprite() {
		super();
		this.x = -1;
		this.y = -1;
		this.width = -1;
		this.height = -1;
		this.image = "";
		this.r = new Rectangle(0,0,0,0);
	}
	public Sprite(int x, int y, int height, int width, String image) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.image = image;
		this.r = new Rectangle(x,y,width,height);
	}
	
	public void Display () {
		System.out.println("x,y: " + this.x + "," + this.y);
		System.out.println("width,height: " + this.width + "," + height);
		System.out.println("image: " + this.image);
	}

	public void updateRectanglePosition() {
		this.r.x = this.x;
		this.r.y = this.y;
	}
	public void updateRectangleSize() {
		this.r.width = this.width;
		this.r.height = this.height;
	}
	public Rectangle getRectangle() {
		return this.r;
	}

	public boolean isColliding(Sprite other) {
        return this.r.intersects(other.getRectangle());
    }
}