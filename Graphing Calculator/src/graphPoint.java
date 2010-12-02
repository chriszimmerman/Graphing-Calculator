//The Point class holds an x and y value for a point on the graph.
//There are set and get methods for the x and y points
public class graphPoint {

	double x; //x coordinate of the point
	double y; //y coordinate of the point
	
	public graphPoint()
	{
		x = 0;
		y = 0;
	}
	
	public graphPoint(int i, int j) {
		// TODO Auto-generated constructor stub
	}

	public void setX(double newX)
	{
		x = newX;
	}
	
	public void setY(double newY)
	{
		y = newY;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
}
