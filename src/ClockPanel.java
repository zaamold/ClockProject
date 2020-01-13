import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.JPanel;

/**
 * The ClockPanel class holds the logic for
 * drawing the clock itself, and maintains the
 * current time using the Calendar object
 * @author zacharyaamold
 *
 */
public class ClockPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int hour, minute, second;
	
	private int centerX;
	private int centerY;
	
	/**
	 * Constructs the panel itself, and 
	 * initializes member variables
	 * @param width The width of the ClockPanel
	 * @param height The height of the ClockPanel
	 */
	public ClockPanel(int width, int height) {
		
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		
		Calendar cal = Calendar.getInstance();
		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
		
		centerX = this.getWidth()/2;
		centerY = this.getHeight()/2;
		
	}
	
	/**
	 * Called every second in order to update
	 * variables holding the time
	 */
	public void tick() {
		
		Calendar cal = Calendar.getInstance();
		
		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
				
	}
	
	public void paintComponent(Graphics page) {
		
		// Setting relevant values
		centerX = this.getWidth()/2;
		centerY = this.getHeight()/2;
		int radius = this.getWidth()/2;
		
		// Creating clock face
		page.setColor(Color.white);
		page.fillOval(0, 0, radius*2, radius*2);
		
		// Calculating angles for each clock hand
		double hAngle = (hour - 3)*2*Math.PI/12;
		double mAngle = (minute - 15)*2*Math.PI/60;
		double sAngle = (second - 15)*2*Math.PI/60;
		
		// Setting proportional sizes for each clock hand
		final int hSize = (int)(radius * 0.4);
		final int mSize = (int)(radius * 0.75);
		final int sSize = (int)(radius * 0.95);
		
		drawTicks(page, radius);
		drawLabels(page);
		
		drawHand(page, hSize, hAngle, Color.black);
		drawHand(page, mSize, mAngle, Color.black);
		drawHand(page, sSize, sAngle, Color.red);
		
	}
	
	/**
	 * Draws a single hand of the clock from the global center values
	 * to (x,y), derived from the parameter values
	 * @param page The Graphics object for drawing
	 * @param size The size of the hand
	 * @param angle The angle of the hand
	 * @param color The color of the hand
	 */
	public void drawHand(Graphics page, int size, double angle, Color color) {
		
		// Finding the coordinate of the end of the hand, based on the
		// size and angle of the given hand
		int x = (int)(Math.round(centerX + size * Math.cos(angle)));
		int y = (int)(Math.round(centerY + size * Math.sin(angle)));
		
		// Drawing the hand with several lines to give the illusion
		// of a "needle", like one would see in a clock
		page.setColor(color);
		page.drawLine(centerX, centerY, x, y);
		page.drawLine(centerX, centerY-1, x, y);
		page.drawLine(centerX, centerY+1, x, y);
		page.drawLine(centerX-1, centerY, x, y);
		page.drawLine(centerX+1, centerY, x, y);
		
	}
	
	/**
	 * Draws the 12 tick marks associated
	 * with each hour onto the clock face
	 * @param page The Graphics object for drawing
	 * @param outerSize The radius of the clock face
	 */
	public void drawTicks(Graphics page, int outerSize) {
		
		page.setColor(Color.black);
		
		// Making a proportional size for the inner coordinates of our tick marks
		int innerSize = (int) (outerSize * .8);
		double angle = 0;
		
		// Iterates through the tick marks, calculating the points and moving the angle as we go
		for(int i = 0; i < 12; i++) {
			
			int innerX = (int)(Math.round(centerX) + innerSize * Math.cos(angle));
			int innerY = (int)(Math.round(centerY) + innerSize * Math.sin(angle));
			
			int outerX = (int)(Math.round(centerX) + outerSize * Math.cos(angle));
			int outerY = (int)(Math.round(centerY) + outerSize * Math.sin(angle));

			page.drawLine(innerX, innerY, outerX, outerY);
			
			// Adding 30 degrees (in radians) to our angle
			angle += (30*Math.PI/180);
			
		}
		
	}
	
	/**
	 * Labels the 4 hours in the corners of the clock
	 * @param page The Graphics object for drawing
	 */
	public void drawLabels(Graphics page) {
		// A manually determined value for how far inward the labels should be
		final int BUFFER = 85;
		
		page.setFont(new Font("Times New Roman", Font.PLAIN, 30));
				
		page.drawString("XII", this.getWidth() / 2 - (page.getFontMetrics().stringWidth("XII") / 2), BUFFER);
		
		page.drawString("III", this.getWidth() - BUFFER, this.getHeight() / 2 + (page.getFontMetrics().getHeight() / 4));
		
		page.drawString("VI", this.getWidth() / 2 - (page.getFontMetrics().stringWidth("VI") / 2), this.getHeight() - BUFFER);
		
		page.drawString("IX", BUFFER, this.getHeight() / 2 + (page.getFontMetrics().getHeight() / 4));
		
	}
	
	
	
}
