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
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JList;
//Ralph's-Branch---Contains-all-classes-I'm-working-on
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;
import java.util.Formatter;


/**
 * The goal of the Checkout class is to do what for many people is at the end of the shopping experience
 * 
 * 
 *  
 * @author Aaron Flores, Christatian Sanchez, Matthew Bellman
 * @version 2022.12.XX
 */

//Customer-Specifics
public class checkoutClass extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create the panel.
	 */
	public checkoutClass() {

		initLabel();
		createEvents();

		shoppingList = new DefaultListModel<Object>();
		quantityList = new DefaultListModel<Object>();
		//setLayout(groupLayout);

	}

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String phoneNum;
	private String email;

	private String rNum = null;
	private String aNum = null;
	private String lastNumCard ="";
	private String lastNumCheck= "";
	private String add;

	private double ship;
	private double total;

	private String nameCard = null;
	private String dateCard = null;
	private String numberCard = null;
	private String cvvCard = null;
	private JTextField first;
	private JTextField last;
	private JTextField address1;
	private JTextField city1;
	private JTextField state1;
	private JTextField zipNum;
	private JTextField cellNum;
	private JTextField emailAddress;

	private boolean zipCheck;
	private boolean phoneCheck;
	private boolean firstCheck;
	private boolean lastCheck;
	private boolean addressCheck;
	private boolean cityCheck;
	private boolean stateCheck;
	private boolean emailCheck;
	
	private JButton enter;
	
	private static JTextArea shipArea;
	private static JTextArea totalPrice;
	private static JTextArea taxArea;
	private static JTextArea totalArea;
	
	private static JList<Object> checkoutList;
	private static JList<Object> checkoutQuantity;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;

	//private JList<Object> shopList;

	public static DefaultListModel<Object> shoppingList;  
	public static DefaultListModel<Object> quantityList;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	////////////////////////////////
	private JButton creditCard;
	private JButton eCheck;
	private JRadioButton standard;
	private JRadioButton fast;
	private JRadioButton rush;
	/////////////////////////////////
	
	//Customer-Specifics

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
	 * Moves the Cart list to the checkout List (same with quantity)
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

	
	private void updateShip(double s) {
		
		// Checks to see if the checkout list is empty. (also applies to "fast" and "rush")
		if (checkoutList.getModel().getSize() == 0) {
			buttonGroup.clearSelection();
			return;
		}
		
		ship = s;
		System.out.println("$" + (int)s + " Shipping");
		shipArea.setText("$" + ship + "0");
		
		addTotal();
	}

	private void createEvents() 
	{ //this method initializes all event elements of the panel

		// Move the ship updates to their own function.
		standard.addActionListener(new ActionListener() { //Shipping Button
			public void actionPerformed(ActionEvent e) {

				if (standard.isSelected()) {
					
					updateShip(5);
					
					// Checks to see if the checkout list is empty. (also applies to "fast" and "rush")
					/*if (checkoutList.getModel().getSize() == 0) {
						buttonGroup.clearSelection();
						return;
					}
					
					ship = 5.00;
					System.out.println("$5 Shipping");
					shipArea.setText("$"+ ship+ "0");

					addTotal();*/

				}

			}
		});

		fast.addActionListener(new ActionListener() {//Shipping Button
			public void actionPerformed(ActionEvent e) {
				
				if (fast.isSelected()) {
					
					updateShip(15);
					
					/*
					if (checkoutList.getModel().getSize() == 0) {
						buttonGroup.clearSelection();
						return;
					}
					
					ship = 15.00;
					System.out.println("$15 Shipping");
					shipArea.setText("$"+ ship+ "0");

					addTotal();*/

				}
			}
		});

		rush.addActionListener(new ActionListener() {//Shipping Button
			public void actionPerformed(ActionEvent e) {
				if (rush.isSelected()) {
					
					updateShip(25);
					/*
					if (checkoutList.getModel().getSize() == 0) {
						buttonGroup.clearSelection();
						return;
					}
					
					ship = 25.00;
					System.out.println("$25 Shipping");

					shipArea.setText("$"+ ship+ "0");

					addTotal();*/
					

				}

			}
		});

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
					if(firstCheck == false)
					{
						JOptionPane.showMessageDialog(null, "First Name Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(lastCheck == false)
					{
						JOptionPane.showMessageDialog(null, "Last Name Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(addressCheck == false)
					{
						JOptionPane.showMessageDialog(null, "Address Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(stateCheck == false)
					{
						JOptionPane.showMessageDialog(null, "State Not Accepted , Input 2-Letter Abbreviation ", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(phoneCheck == false)
					{
						JOptionPane.showMessageDialog(null, "Phone Number Must Be 10 Digits, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(emailCheck == false)
					{
						JOptionPane.showMessageDialog(null, "Email Cannot Be Empty", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(cityCheck == false)
					{
						JOptionPane.showMessageDialog(null, "City Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(zipCheck == false)
					{
						JOptionPane.showMessageDialog(null, "Zip Code Must Be 5 Digits, Input Again", "Alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				//////////////////////////////////////////
				
				if (!standard.isSelected() && !fast.isSelected() && !rush.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please Select A Shipping Option", "Alert", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!overallCheck())
					return;
				
				conformationEmail();
				
				resetPanel();

			} //end of Action

			private void resetPanel() {
				
				// Resets JLists and Default List Modules
				checkoutList.removeAll();
				checkoutQuantity.removeAll();
				shoppingList.removeAllElements();
				quantityList.removeAllElements();
				
				// Resets JTextFields
				first.setText("");
				last.setText("");
				address1.setText("");
				city1.setText("");
				state1.setText("");
				zipNum.setText("");
				cellNum.setText("");
				emailAddress.setText("");
				
				// Reset Radio Button Group
				buttonGroup.clearSelection();
				
				// Reset Purchase Method
				nameCard = null;
				cvvCard = null;
				numberCard = null;
				dateCard = null;
				aNum = null;
				rNum = null;
				
				// Reset Prices
				shipArea.setText("");
				totalPrice.setText("");
				taxArea.setText("");
				totalArea.setText("");
				
			}
			
		});	//End of Listener


		eCheck.addActionListener(new ActionListener() { //Electronic Check Button
			public void actionPerformed(ActionEvent e) {
				
				// Removes the Card Values
				nameCard = null;
				cvvCard= null;
				numberCard= null;
				dateCard = null;
				
				aNum= "";
				rNum= "";

				while (rNum.length() == 0 || rNum.length() != 9) {

					rNum = JOptionPane.showInputDialog("Routing Number", null);

					if (rNum.length()== 0) {
						JOptionPane.showMessageDialog(null, "Cannot Be Empty, Input Again", "Alert", JOptionPane.ERROR_MESSAGE);
					}
					else if (rNum.length() != 9) {
						JOptionPane.showMessageDialog(null, "Routing Number Must be 9 Digits", "Alert", JOptionPane.ERROR_MESSAGE);	
					}
					else {
						break;
					}

				}//End of While Loop


				while (aNum.length() == 0 || aNum.length() != 15) {

					aNum = JOptionPane.showInputDialog("Account Number", null);

					if (aNum.length()== 0) {
						JOptionPane.showMessageDialog(null, "Cannot Be Empty, Input Again ", "Alert", JOptionPane.ERROR_MESSAGE);
					}
					else if (aNum.length() != 15) {
						JOptionPane.showMessageDialog(null, "Account Number Must be 15 Digits", "Alert", JOptionPane.ERROR_MESSAGE);	
					}
					else {
						lastNumCheck = aNum.substring(aNum.length()-4);
						break;
					}

				}//End of While Loop


				/*	System.out.println("Routing Number: " + rNum); //Debugging
						System.out.println("Account Number: " + aNum); //Debugging
						System.out.println("Last 4 digits of Account Number: " + lastNumCheck); //Debugging */


			}//End Of Action Event
		});//End of Action Listener

		creditCard.addActionListener(new ActionListener() { //Credit Card Button
			public void actionPerformed(ActionEvent e) {

				// Removes the check Values
				aNum= null;
				rNum= null;
				
				nameCard = "";
				cvvCard= "";
				numberCard= "";
				dateCard = "";	

				while (nameCard.length() == 0) {

					nameCard = JOptionPane.showInputDialog("Name On Card", null);

					if (nameCard.length()== 0) {
						JOptionPane.showMessageDialog(null, "Cannot Be Empty, Input Again", "Alert", JOptionPane.ERROR_MESSAGE);
					}
					else {
						break;
					}

				}//End of While Loop



				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				while (numberCard.length() == 0 || numberCard.length() != 16) {

					numberCard = JOptionPane.showInputDialog("Card Number", null);

					if (numberCard.length()== 0) {
						JOptionPane.showMessageDialog(null, "Cannot Be Empty, Input Again", "Alert", JOptionPane.ERROR_MESSAGE);
					}
					else if (numberCard.length() != 16) {
						JOptionPane.showMessageDialog(null, "Card Number Must be 16 Digits", "Alert", JOptionPane.ERROR_MESSAGE);	
					}
					else {
						lastNumCard = numberCard.substring(numberCard.length()-4);
						break;
					}

				}//End of While Loop

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				while (dateCard.length() == 0) {

					dateCard = JOptionPane.showInputDialog("Date On Card", null);

					if (dateCard.length()== 0) {
						JOptionPane.showMessageDialog(null, "Cannot Be Empty, Input Again", "Alert", JOptionPane.ERROR_MESSAGE);
					}
					else {
						break;
					}

				}//End of While Loop


				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				while (cvvCard.length() == 0 || cvvCard.length() != 3) {

					cvvCard = JOptionPane.showInputDialog("CVV", null);

					if (cvvCard.length()== 0) {
						JOptionPane.showMessageDialog(null, "Cannot Be Empty, Input Again", "Alert", JOptionPane.ERROR_MESSAGE);
					}
					else if (cvvCard.length() != 3) {
						JOptionPane.showMessageDialog(null, "CVV Must be 3 Digits", "Alert", JOptionPane.ERROR_MESSAGE);	
					}
					else {
						break;
					}

				}//End of While Loop			
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


				/*	System.out.println("Card Name: " + nameCard); //Debugging
			System.out.println("Card Number: " + numberCard); //Debugging
			System.out.println("Card Date: " + dateCard); //Debug
			System.out.println("Card CVV: " + cvvCard); //Debug
			System.out.println("Last 4 Digits of Credit Card: "+ lastNumCard);//Debug */


			}//End Of Action Event
		});//End of Action Listener


	}	//End of Event
	
	private void Equalize()
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


	private String addTotal() {

		String str = shipArea.getText(); 
		String str1 = taxArea.getText();
		String str2 = totalPrice.getText();

		String one = str.substring(1);
		String two = str1.substring(1);
		String three = str2.substring(1);

		double x = Double.valueOf(one);
		double y = Double.valueOf(two);
		double z = Double.valueOf(three);

		//	System.out.println(x);
		//	System.out.println(y);
		//System.out.println(z);

		total = x+y+z;

		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		formatter.format("%.2f", total);

		System.out.println(formatter.toString());

		totalArea.setText("$" + formatter.toString());
		add = totalArea.getText();
		return add;
	} //End of Add total method


	private boolean overallCheck() {

		if (rNum == null && nameCard == null) {
			JOptionPane.showMessageDialog(null, "Payment Cannot be left Empty", "Alert", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (nameCard != null && rNum == null) {

			if (cvvCard == null) {
				JOptionPane.showMessageDialog(null, "CVV Cannot be left Empty", "Alert", JOptionPane.ERROR_MESSAGE);		
				return false;
			}
			if (dateCard == null) {
				JOptionPane.showMessageDialog(null, "Card Date Cannot be left Empty", "Alert", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else if (numberCard == null) {
				JOptionPane.showMessageDialog(null, "Card Number Cannot be left Empty", "Alert", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else {
				System.out.println("Card Name: " + nameCard); //Debugging
				System.out.println("Card Number: " + numberCard); //Debugging
				System.out.println("Card Date: " + dateCard); //Debug
				System.out.println("Card Cvv: " + cvvCard); //Debug
				System.out.println("Last 4 Digits: "+ lastNumCard); //Debug
				return true;
			}

		}

		else if (rNum != null && nameCard == null) {
			if(aNum == null) {
				JOptionPane.showMessageDialog(null, "Account Number Cannot be left Empty", "Alert", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else {
				System.out.println("Routing Number: " + rNum); //Debugging
				System.out.println("Account Number: " + aNum); //Debugging
				System.out.println("Last 4 digits of Account Number: " + lastNumCheck); //Debugging
				return true;
			}
		} 
		
		else { // (rNum != null && nameCard != null)

			rNum=null;
			aNum=null;
			nameCard=null;
			numberCard=null;
			dateCard=null;
			cvvCard=null;

			JOptionPane.showMessageDialog(null, "Please only Pick One Payment Type!", "Alert", JOptionPane.ERROR_MESSAGE);	
			return false;
		}
		
	}//end of overallCheck

	/// TODO: (R) Re-factor This ///
	private void conformationEmail() {

		// (Internal) E-Check
		if (rNum != null && nameCard == null) {
			System.out.println("Thank You "+ getFullName()+"!");//First and Last name
			System.out.println(getAddress()); //Address
			System.out.println(getCity()); //City
			System.out.println(getState()); //State
			System.out.println(getEmail()); //Email
			System.out.println("Payment Type is E-check: " + lastNumCheck);  //Last 4 Numbers of check
			System.out.println(add); //Overall total

			JOptionPane.showMessageDialog(null, 
					"Thank You "+ getFullName()+"!\n" + "Your Items Will be Sent to This address: \n" +
							getAddress() + " " + getCity()+ ", "+ getState() +"\n" +
							"A conformation email will be sent to: "+ getEmail() +"\n" + 
							"Payment Type is E-check ending in: " + lastNumCheck + "\n" +
							"The Grand Total: " + add);


		}

		// (Internal) Credit-Card
		else if (nameCard != null && rNum == null) {

			System.out.println("Thank You "+ getFullName()+"!"); //First and Last name
			System.out.println(getAddress()); //Address
			System.out.println(getCity()); //City
			System.out.println(getState()); //State
			System.out.println(getEmail()); //Email

			System.out.println("Payment Type is Credit Card: "+ lastNumCard); //Last 4 Numbers of card
			System.out.println("The Grand Total: "+ add); //Overall total

			JOptionPane.showMessageDialog(null, 
					"Thank You "+ getFullName()+"!\n" + "Your Items Will be Sent to This address: \n" +
							getAddress() + " " + getCity()+ ", "+ getState() +"\n" +
							"A conformation email will be sent to: "+ getEmail() +"\n" + 
							"Payment Type is Credit Card ending in: " + lastNumCard + "\n" +
							"The Grand Total: " + add);
		}
		
		

	}//End if conformation Email

	
	// TODO: Re-factor the checks
	public boolean checkEmail() // !email.isEmpty()
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
	public boolean checkZip() // zipCode.length() == 5
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

	public boolean checkPhone() // phoneNum.length() == 10
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

	public boolean checkFirst() // !firstName.isEmpty()
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

	public boolean checkLast() // !lastName.isEmpty()
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

	public boolean checkAddress() // !address.isEmpty()
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

	public boolean checkCity() // !city.isEmpty()
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

	public boolean checkState() // state.length() == 2
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
		totalPrice.setEditable(false);
		totalPrice.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		taxArea = new JTextArea();
		taxArea.setEditable(false);
		taxArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		shipArea = new JTextArea();
		shipArea.setEditable(false);
		shipArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		emailAddress = new JTextField();
		emailAddress.setColumns(10);

		lblNewLabel = new JLabel("E-Mail");

		lblNewLabel_1 = new JLabel("Total:");

		lblNewLabel_2 = new JLabel("Tax:");

		lblNewLabel_3 = new JLabel("Shipping:");

		standard = new JRadioButton("7-10 Days Standard Shipping");
		buttonGroup.add(standard);
		standard.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		fast = new JRadioButton("3-Day Shipping");

		buttonGroup.add(fast);
		fast.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		rush = new JRadioButton("Next Day Shipping");

		buttonGroup.add(rush);
		rush.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		totalArea = new JTextArea();
		totalArea.setEditable(false);
		totalArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		lblNewLabel_4 = new JLabel("Grand Total:");

		lblNewLabel_5 = new JLabel("Qty:");

		lblNewLabel_6 = new JLabel("Products to Checkout");

		lblNewLabel_7 = new JLabel("* Secure payment info will be entered after selection is made from above");
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.ITALIC, 9));

		creditCard = new JButton("Credit Card");
		creditCard.setFont(new Font("Tahoma", Font.PLAIN, 13));

		eCheck = new JButton("E-Check");
		eCheck.setFont(new Font("Tahoma", Font.PLAIN, 13));


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
														.addComponent(standard)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(fast)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(rush))
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
																				.addComponent(cellNum, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
																				.addGroup(groupLayout.createSequentialGroup()
																						.addGap(4)
																						.addComponent(creditCard)))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																				.addComponent(eCheck, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
																				.addGroup(groupLayout.createSequentialGroup()
																						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																								.addComponent(emailAddress, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
																								.addComponent(lblNewLabel))
																						.addPreferredGap(ComponentPlacement.UNRELATED)
																						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																								.addGroup(groupLayout.createSequentialGroup()
																										.addComponent(cityName, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
																										.addGap(47))
																								.addComponent(city1, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))))
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
										.addGap(106)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(lblNewLabel_4)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(totalArea, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(lblNewLabel_3)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(shipArea, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(lblNewLabel_2)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(taxArea, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(lblNewLabel_1)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(totalPrice, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
																.addComponent(checkoutList, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE))
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
								.addComponent(shipArea, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(totalArea, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_4))
						.addGap(24)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(standard)
								.addComponent(fast)
								.addComponent(rush))
						.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(firstName, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
										.addComponent(lastName, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
										.addComponent(stateInitials, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
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
												.addComponent(creditCard)
												.addComponent(eCheck, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
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