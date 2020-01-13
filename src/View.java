import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * The View class maintains all GUI objects
 * and the Timer moving the clock forward
 * @author zacharyaamold
 *
 */
public class View implements ActionListener, MouseListener {

	private Timer t;
	private JFrame myFrame;
	private ClockPanel cPanel;
	private boolean isDigitalClockOn;

	/**
	 * Constructs our main GUI components
	 * @param width The width of the clock panel
	 * @param height The height of the clock panel
	 * @param isDigitalClockOn If true, the frame title should display the time digitally
	 */
	public View(int width, int height, boolean isDigitalClockOn) {
		
		this.isDigitalClockOn = isDigitalClockOn;
		
		myFrame = new JFrame("Zach's Clock");
		
		// Setting up frame parameters
		myFrame.setPreferredSize(new Dimension(width, height));
		myFrame.setResizable(false);
		myFrame.setBackground(Color.black);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initializing Clock Panel, connecting to MouseListener
		cPanel = new ClockPanel(myFrame.getWidth(), myFrame.getHeight());
		cPanel.addMouseListener(this);

		myFrame.getContentPane().add(cPanel);

		myFrame.pack();
		myFrame.setVisible(true);
		
		// Timer set to tick every second
		t = new Timer(1000, this);
		t.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == t) {
			// Clock Panel "ticks" and repaints once per second
			cPanel.tick();
			cPanel.repaint();
			
			// Updates frame title with digital clock if the boolean is true
			if(isDigitalClockOn) {
				
				myFrame.setTitle("Zach's Clock - " + 
						(new Timestamp(System.currentTimeMillis()).toString().substring(11, 19)));
				
			}

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		// Clicking the Clock Panel toggles the digital clock
		if(isDigitalClockOn) {

			myFrame.setTitle("Zach's Clock");
			isDigitalClockOn = false;
			
		} else {

			myFrame.setTitle("Zach's Clock - " + 
					(new Timestamp(System.currentTimeMillis()).toString().substring(11, 19)));
			isDigitalClockOn = true;
			
		}
		
	}
	
	// Methods inherited from MouseListener that are unused
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) {
		final int FRAME_SIZE = 500;

		new View(FRAME_SIZE, FRAME_SIZE + 22, false);  // The extra 22 is an offset for the top of the frame
	}

}
