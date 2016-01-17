package styles.beam;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class Arc {
	
	private double mult, theta1, theta2;
	private Color c;
	
	public Arc(double theta1, double theta2, Color c) {
		this.mult = 1.75 + 0.75 * Math.random();
		this.theta1 = theta1;
		this.theta2 = theta2;
		this.c = c;
	}
	
	public void draw(Graphics2D g, int x, int y, int maxRadius, boolean fillArc, boolean drawBorder) {
		// fill the arc
		if (fillArc) {
			RadialGradientPaint arc = new RadialGradientPaint(
					new Point(x, y),
					(int)(maxRadius / 2 * mult),
					new float[]{0f, 0.25f, 1f},
					new Color[]{Color.WHITE, c, Color.BLACK});
			g.rotate(theta1, x, y);
			g.setPaint(arc);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.7));
			g.fill(new Arc2D.Float(x - (int)(maxRadius / 2 * mult), y - (int)(maxRadius / 2 * mult),
					(int)(maxRadius * mult), (int)(maxRadius * mult),
					-90, -(int)((theta2 - theta1) * 180 / Math.PI), Arc2D.PIE));
		}
		
		// draw the borders
		RadialGradientPaint border = new RadialGradientPaint(
				new Point(x, y),
				maxRadius,
				new float[]{0f, 0.15f},
				new Color[]{Color.WHITE, c});
		if (drawBorder) {
			g.setPaint(border);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.7));
			if (!fillArc) {
				g.rotate(theta1, x, y);
			}
			g.fillRect(x, y, 5, maxRadius);
			g.rotate((int)((theta2 - theta1) * 180 / Math.PI) * Math.PI / 180, x, y);
			g.fillRect(x, y, 5, maxRadius);
		}
		g.setTransform(new AffineTransform());
	}
	
	public double getTheta1() {
		return theta1;
	}
	
	public double getTheta2() {
		return theta2;
	}
	
	public double[] getThetas() {
		return new double[]{theta1, theta2};
	}
	
	public void setTheta1(double d) {
		theta1 = d;
	}
	
	public void setTheta2(double d) {
		theta2 = d;
	}
	
	public void setThetas(double[] d) {
		theta1 = d[0];
		theta2 = d[1];
	}
	
	public Color getColor() {
		return c;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
 }