package windowBuilder.views;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * This class holds the main method and is the launching point of the software.<br>
 * It creates a tabbedPane via WindowBuilder which holds essential panels for the<br>
 * user to navigate. All JPanels are instantiated from this main class and exist as<br>
 * panels (accessible via the tabs) for easy navigation for the user. <p>
 * 
 * At the release of this version, the tabs are: Home, Product Search, View Cart,<br>
 * Checkout, and Help.<p>
 * 
 * The 'Product Search' tab uses a JPanel object of productSearchClass which holds the constructor<br>
 * and methods necessary to allow a user to search for a product with added features. The <br>
 * 'View Cart' tab uses a JPanel object of cartClass which holds the constructor and methods<br>
 * necessary to allow a user to view products added to the cart with some features and ability to begin checkout process.<p>
 * 
 * @author Ralph Ramirez, Aaron Flores, Matthew Bellman
 * @version 2022.11.13
 */ 
public class eCommerceMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JPanel search_panel = new productSearchClass(); //instantiates an object of productSearchClass() which is a JPanel
	JPanel cart_panel = new cartClass(); //instantiates an object of cartClass() which is a JPanel
	JPanel checkout_panel = new checkoutClass();   
	JPanel help_panel = new helpClass();
	
	ImageIcon homeIcon = new ImageIcon(this.getClass().getResource("/icons/Home.png"));            /////////////////////////////////////
	ImageIcon searchIcon = new ImageIcon(this.getClass().getResource("/icons/Search2.png"));       //                                 //
	ImageIcon cartIcon = new ImageIcon(this.getClass().getResource("/icons/basket.png"));          //sets the icons for the TabbedPane//
	ImageIcon checkoutIcon = new ImageIcon(this.getClass().getResource("/icons/Dollar.png"));      //                                 //
	ImageIcon helpIcon = new ImageIcon(this.getClass().getResource("/icons/Help.png"));            /////////////////////////////////////
	
	private final JLabel lblLogo = new JLabel("");
	
	/**
	 * Launches the application.<br>
	 * Manages 'Look and Feel' of the running application<br>
	 * Creates the tabbedPane frame that holds the major panels of the application.<br>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					eCommerceMain frame = new eCommerceMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initializes all components of the GUI tabbedPane and creates the working frame with tabbedPane.<br>
	 * Code largely written by WindowBuilder design.<br>
	 * Only modified code exists for custom tabs with icons.<br>
	 *
	 * @throws FileNotFoundException 
	 */
	public eCommerceMain() throws FileNotFoundException {
		
		setResizable(false);
		setTitle("Vintage Curios");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(325, 50, 560, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 638, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		
		JPanel home_panel = new JPanel();	
		
		GroupLayout gl_home_panel = new GroupLayout(home_panel);
		gl_home_panel.setHorizontalGroup(
			gl_home_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_home_panel.createSequentialGroup()
					.addContainerGap(34, Short.MAX_VALUE)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 474, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		lblLogo.setIcon(new ImageIcon(this.getClass().getResource("/images/logo.png")));
		
		gl_home_panel.setVerticalGroup(
			gl_home_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_home_panel.createSequentialGroup()
					.addGap(17)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 573, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		
		home_panel.setLayout(gl_home_panel); //home tab	
		
		/////Add panels below/////////
		tabbedPane.addTab("Home", homeIcon, home_panel,"Home");
		tabbedPane.addTab("Product Search", searchIcon, search_panel, "Click to search for products"); //Product Search tab
		tabbedPane.addTab("View Cart", cartIcon, cart_panel, "Click to view Cart"); //View Cart tab
		tabbedPane.addTab("Checkout", checkoutIcon, checkout_panel, "Click to view Checkout items"); //Checkout tab
		tabbedPane.addTab("Help", helpIcon, help_panel, "Click for help"); //help tab
		contentPane.setLayout(gl_contentPane);
	}
}