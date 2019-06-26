package edu.ufp.esocial.api.util;

public class Point {

	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Create a new point from a String with the axis separated by a comma.<br />
	 * Param example: 2.5,3<br />
	 * This means x = 2.5 and y = 3.0
	 *
	 * @param coord string containing x and y axis, accordingly
	 * @return new Point with specified values
	 */
	public static Point fromString(String coord) {
		String[] axis = coord.split(",");
		if (axis.length != 2) {
			throw new InvalidParsingStringException(Point.class.getName());
		}
		return new Point(new Float(axis[0]), new Float(axis[1]));
	}

	public static Point randomize(double maxX, double maxY) {
		return new Point(Util.RAND.nextDouble() * maxX, Util.RAND.nextDouble() * maxY);
	}

	@Override
	public String toString() {
		return "(" + this.x + "; " + this.y + ')';
	}

	public String toFileString() {
		return Double.toString(this.x) + "," + Double.toString(this.y);
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double distX(Point p) {
		return this.x - p.x;
	}

	public double distY(Point p) {
		return this.y - p.y;
	}

	public double dist(Point p) {
		return Math.sqrt(Math.pow(this.distX(p), 2) + Math.pow(this.distY(p), 2));
	}

	public boolean xBetweenPoints(Point leftPt, Point rightPt) {
		return this.x > leftPt.x && this.x < rightPt.x;
	}

	public boolean yBetweenPoints(Point upperPt, Point lowerPt) {
		return this.y > lowerPt.y && this.y < upperPt.y;
	}
}