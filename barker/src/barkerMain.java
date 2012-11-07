import javax.swing.*;

/**
 * 
 */

/**
 * @author Colin Mackey and David Justis 
 * As of right now, this is a shell of the
 *         stuff to come
 * 
 */
public class barkerMain extends JFrame {

	JPanel userPanel;
	/**
	 * Constructor
	 */
	public barkerMain() {
		super();
		setMaximumSize(new Dimension(500,500));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initGui();
	}

	/**
	 * GUI initialization method
	 */
	public void initGui() {
		
		userPanel = new JPanel();
	}

	/**
	 * method for the logic
	 */
	public void logic() {
		// name can change
		// there's a loop in here for getting pings and storing them in a queue
	}

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}

		//new SwingApplication(); // Create and show the GUI

	}

}
