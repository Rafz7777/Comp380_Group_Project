 package windowBuilder.views;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JList;

import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.Font;



/**
 * The goal of the Checkout class is to do what for many people is at the end of the shopping experience where its will show<br>
 * a customer's cart and allow a customer to input their information. This class interacts with eCommerceMain in order to be called upon and <br> 
 *  display the cart and several textfield boxes for the user to interact with.<p>
 * 
 * The class is virtually unused until the user wants to checkout an actual cart, but allows the user to go to the tab nonetheless.<p>
 * The class should be having an interaction with the emailClass that Matthew is responsible for in order to be able to pull the information needed<br>
 * for the class for a user friendly interaction between the two. Unsure if this class will contain the operations in which Christian is responsible for<br>   
 *  it will either be implemented within this class or called upon from another class.<p>
 *  
 *  
 * @author Aaron Flores
 *date Started 10/28
 *@version 2022.11.12
 */
 //Customer-Specifics

public class checkoutClass extends JPanel {
	

	/**
	 * Create the panel.
	 * the methods initLabel() and createEvents are called upon to actually display to the user<br> 
	 * the Interface they will be using and familiarizing themselves with.<p> 
	 * 
	 * Two list are then initialized to display information received from cartClass to display<br>
	 * for the customer.<p>
	 */
	public checkoutClass() {
		
		initLabel();
		createEvents();
		
		shoppingList = new DefaultListModel<Object>();
		quantityList = new DefaultListModel<Object>();
		//setLayout(groupLayout);

	}
/**
 * a substantial number of variables and textfields is are created an initialized.<p>
 * created globally to be accessed by all methods as needed and each textfield has a corresponding<br>
 * string in which the input will be extracted for easy use.<p>
 * 
 * The boolean variables created are used as the simple flag to be check to ensure user input is legible.<p>
 * 
 * all instances will be carrying the value they are describe by the name to make life easier for<br>
 * then programmer
 */
	private  String firstName;
	private  String lastName;
	private  String address;
	private  String city;
	private  String state;
	private  String zipCode;
	private  String phoneNum;
	private  String email;

	
	private JTextField first;
	private JTextField last;
	private JTextField address1;
	private JTextField city1;
	private JTextField state1;
	private JTextField zipNum;
	private JTextField cellNum;
	
	boolean zipCheck;
	boolean phoneCheck;
	boolean firstCheck;
	boolean lastCheck;
	boolean addressCheck;
	boolean cityCheck;
	boolean stateCheck;
	boolean emailCheck;
	
	private JButton enter;
 
	private static JList checkoutList;
	private static JList checkoutQuantity;
	private JTextArea textArea_2;
	private JTextField emailAddress;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextArea textArea_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JRadioButton rdbtnNewRadioButton_3;
	private JRadioButton rdbtnNewRadioButton_4;
	private JLabel lblNewLabel_7;

	private JList shopList;
	
	public static DefaultListModel<Object> shoppingList;  
	public static DefaultListModel<Object> quantityList;
	private static JTextArea totalPrice;
	private static JTextArea taxArea;
	
	
	/*public void addListeners()
	{
		enter.addActionListener(new ActionListener())
		{
			@Override
        	public void actionPerformed(ActionEvent e) 
			{
        	
			}
		
		}
	}*/
	
	/**
	 * This transferCart method is created to get the shopping cart from cartClass when a certain flag is<br> 
	 * processed in this case the checkout button is pressed by the user thus calling this method transferring<br> 
	 * the data from one list to this list display the contents of which to the customer when they head on to the<br>
	 * to the checkout tab in the program.<p>
	 */
		public static void transferCart()
		{
			addTo(cartClass.CartList_items_2,shoppingList);
			addTo(cartClass.ToCartQuantityList_items_4, quantityList);
			
			checkoutList.setModel(shoppingList);
			checkoutQuantity.setModel(quantityList);
			
			totalPrice.setText("$"+String.valueOf(cartClass.sum)+".00");
			double taxTot = cartClass.sum *.0725;
			DecimalFormat shorten = new DecimalFormat("#.00");
			
			String taxTransfer = shorten.format(taxTot);
			taxArea.setText("$"+ taxTransfer);
			
		}
		
		/**
		 * Used to add one DefaultListModel to another.<br>
		 * 
		 * @param <T>     used to represent the type of object stored
		 * @param from    the list that represents the change coming 'from'
		 * @param to      the list that represents the change going 'to'
		 */
		protected static <T> void addTo(ListModel<T> from, DefaultListModel<T> to) { //method used to add one ListModel to another DefaultListModel
		    for (int index = 0; index < from.getSize(); index++) {
		        to.addElement(from.getElementAt(index));
		    }
		}
		
		/**
		 * this method initializes event elements in the case of the user pressing a button, once the button is pressed<br>
		 * it calls the equalize() method as well as the boolean methods used to check user input.<p>
		 * It later reviews the results of the methods and if any problems are run into a pop-up message is displayed<br>
		 * for the customer to explain the issue that was run into.<p>  
		 */
		private void createEvents() 
		{ //this method initializes all event elements of the panel
		
			enter.addActionListener(new ActionListener()
			{ //this action method for button: 'enter' pays and checks inputs 
				public void actionPerformed(ActionEvent e) 
				{
					
					
					try
					{
						Equalize();
						 zipCheck = checkZip();
						 phoneCheck = checkPhone();
						 firstCheck = checkFirst();
						 lastCheck = checkLast();
						 addressCheck = checkAddress();
						 cityCheck = checkCity();
						 stateCheck = checkState();
						 emailCheck = checkEmail();
						 
					}
					 finally
					 {
						 if(zipCheck == false)
							{
								JOptionPane.showMessageDialog(null, "Zip Code Must Be 5 Digits, Input Again", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up messa
							}
						 else if(phoneCheck == false)
							{
								JOptionPane.showMessageDialog(null, "Phone Number Must Be 10 Digits, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up messa
							}
						 else if(firstCheck == false)
						 {
							 JOptionPane.showMessageDialog(null, "First Name Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						 }
						 else if(lastCheck == false)
						 {
							 JOptionPane.showMessageDialog(null, "Last Name Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						 }
						 else if(addressCheck == false)
						 {
							 JOptionPane.showMessageDialog(null, "Address Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						 }	
						 else if(cityCheck == false)
						 {
							 JOptionPane.showMessageDialog(null, "City Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						 }
						 else if(stateCheck == false)
						 {
							 JOptionPane.showMessageDialog(null, "State Not Accepted , Input 2-Letter Abbreviation ", "Alert", JOptionPane.ERROR_MESSAGE);
						 }
						 else if(emailCheck == false)
						 {
							 JOptionPane.showMessageDialog(null, "Email Cannot be left Empty", "Alert", JOptionPane.ERROR_MESSAGE);
						 }
					 }
					
				}
			});	
		}	
		/**
		 * The Equalize() method is used to just set the values of the variables to whatever the user<br>
		 * had input into the textfields for later use.<p>  
		 */
	public void Equalize()
	{
		firstName = first.getText();
		lastName = last.getText();
		address = address1.getText();;
		city = city1.getText();
		state = state1.getText();
		zipCode = zipNum.getText();
		phoneNum = cellNum.getText();
		email = emailAddress.getText();
		
        //JOptionPane.showMessageDialog(null, "Woah! Bad input, numbers only!");     
	}
			//int.parseint(cellNum)
			/**
			 * the next string of methods exist to check the various inputs of the user within the text fields<br>
			 * most of the inputs check to ensure the user did type some sort of input then checkZip, checkPhone,<br>
			 * and checkState to prevent wrong inputs so checkPhone ensure the user types 10 digits, checkZip is for<br>
			 * 5 digits and checkState is for State 2-Letter abbreviations.<p>
			 * @return
			 */
	public boolean checkEmail()
	{
		if(email.length() == 0)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	public boolean checkZip()
	{				 
		if(zipCode.length() != 5 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkPhone()
	{
		if(phoneNum.length() != 10 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkFirst()
	{
		if(firstName.length() == 0 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkLast()
	{
		if(lastName.length() == 0 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkAddress()
	{
		if(address.length() == 0 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkCity()
	{
		if(city.length() == 0 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkState()
	{
		if(state.length() != 2 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * These methods exists as easy getMethods intended for use by other classes, that may need<br>
	 * to use the customer specifics like the emailClass.<p>
	 * @return
	 */
	public String getFullName()
	{
		return firstName + " " + lastName;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getState()
	{
		return state;
	}

	public String getEmail()
	{
		return email;
	}

	/**
	 * This method performs the built of the GUI creation and displays that the user interacts with <br>
	 * labels are created and placed above the textfields to show guide the user in which to input what set<br>
	 * of info.<p>
	 * 
	 * Along with the labels the corresponding textfield is also displayed and await interaction, everything else<br> 
	 * like the pay button, or payment options will be available and displayed as radio buttons as a temporary look.<p>
	 * Everything is set to a group layout as its looks like a better UI.
	 * 
	 * The shopping list is then displayed as was in the cart class tab, with its price and any other little<br>
	 * additions like tax or shipping to display a grand Total for the User that is easy to understand.<p>
	 */
	private void initLabel()
	{
		JLabel firstName = new JLabel("First Name");
		
		JLabel lastName = new JLabel("Last Name");
		
		JLabel address = new JLabel("Address");
		
		JLabel stateInitials = new JLabel("State");
		
		JLabel phone = new JLabel("Phone");
		
		JLabel cityName = new JLabel("City");
		
		JLabel zip = new JLabel("Zip");
		
		first = new JTextField();
		first.setColumns(10);
		
		last = new JTextField();
		last.setColumns(10);
		
		address1 = new JTextField();
		address1.setColumns(10);
		
		state1 = new JTextField();
		state1.setColumns(10);
		
		cellNum = new JTextField();
		cellNum.setColumns(10);
		
		city1 = new JTextField();
		city1.setColumns(10);
		
		
		zipNum = new JTextField();
		zipNum.setColumns(10);
		
		
		enter = new JButton("Pay");
		
		checkoutList = new JList<Object>();
		checkoutList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		checkoutQuantity = new JList<Object>();
		checkoutQuantity.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		totalPrice = new JTextArea();
		totalPrice.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		taxArea = new JTextArea();
		taxArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		textArea_2 = new JTextArea();
		textArea_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		emailAddress = new JTextField();
		emailAddress.setColumns(10);
		
		lblNewLabel = new JLabel("E-Mail");
		
		lblNewLabel_1 = new JLabel("Total:");
		
		lblNewLabel_2 = new JLabel("Tax:");
		
		lblNewLabel_3 = new JLabel("Shipping:");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("7-10 Days Standard Shipping");
		rdbtnNewRadioButton.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("3-Day Shipping");
		rdbtnNewRadioButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Next Day Shipping");
		rdbtnNewRadioButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		textArea_3 = new JTextArea();
		textArea_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		lblNewLabel_4 = new JLabel("Grand Total:");
		
		lblNewLabel_5 = new JLabel("Qty:");
		
		lblNewLabel_6 = new JLabel("Products to Checkout");
		
		rdbtnNewRadioButton_3 = new JRadioButton("Credit/Debit Card");
		rdbtnNewRadioButton_3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		rdbtnNewRadioButton_4 = new JRadioButton("Electronic Check");
		rdbtnNewRadioButton_4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		lblNewLabel_7 = new JLabel("* Secure payment info will be entered after selection is made from above");
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.ITALIC, 9));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(33)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnNewRadioButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnNewRadioButton_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnNewRadioButton_2))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(firstName, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
												.addComponent(first, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
											.addGap(14)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(lastName, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
													.addGap(18))
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(last, 0, 0, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.UNRELATED)))
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(address1, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
												.addComponent(address, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(phone, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
												.addComponent(cellNum, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(emailAddress, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewLabel))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(cityName, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
													.addGap(47))
												.addComponent(city1, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(rdbtnNewRadioButton_3)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(rdbtnNewRadioButton_4)
											.addGap(52)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(zip, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(state1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
											.addComponent(stateInitials, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
										.addComponent(enter, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addComponent(zipNum, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
									.addGap(68))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(86)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_4)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(textArea_3, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_3)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(textArea_2, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_2)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(taxArea, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_1)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(totalPrice, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
										.addComponent(checkoutList, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_6)
									.addGap(74)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_5)
								.addComponent(checkoutQuantity, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addGap(141))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addComponent(lblNewLabel_7)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(lblNewLabel_5))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(checkoutList, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkoutQuantity, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(totalPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(taxArea, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textArea_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textArea_3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4))
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnNewRadioButton)
						.addComponent(rdbtnNewRadioButton_1)
						.addComponent(rdbtnNewRadioButton_2))
					.addGap(26, 89, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstName, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lastName, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(stateInitials, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(address, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(first, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(address1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(last, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(state1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(zip, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(phone, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(cityName, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(zipNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(cellNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(emailAddress, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(city1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(87)
							.addComponent(enter, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnNewRadioButton_3)
								.addComponent(rdbtnNewRadioButton_4))
							.addGap(18)
							.addComponent(lblNewLabel_7)))
					.addGap(41))
		);
		setLayout(groupLayout);
		
		/*JList shopList = new JList();
		shopList = cartClass.addTo(JListCartList ,shopList);
		shopList.setBounds(10, 23, 475, 104);
		add(shopList);
		
		JList list = new JList();
		list.setBounds(495, 23, 32, 104);
		add(list);
		
		*/
		
		
		
		
	}
}