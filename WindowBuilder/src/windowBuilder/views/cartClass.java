
package windowBuilder.views;

import javax.swing.JPanel;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

/**
 * This class handles the bulk of tasks associated with viewing the cart.<br>
 * This includes displaying all products added to Cart from the 'fast' method<br>
 * from productSearchClass (not using the Shopping List) or products added from <br>
 * the Shopping List.  This class also displays the quantity added of each product <br>
 * as well as the total price of the Cart.<p>
 * 
 * This class has buttons that allow the user to add a quantity, remove<br>
 * a selected product, empty the Cart, or proceed to the checkout area.<p>
 * 
 * @author Ralph Ramirez, Aaron Flores
 * @version 2022.10.31 
 */
public class cartClass extends JPanel { 
	
	private static final long serialVersionUID = 1L;
	
	public static DefaultListModel<Object> CartList_items_2; //DefaultListModel list used to create list containing items added to Cart List from 'fast' shopping method in productSearchClass
	public static DefaultListModel<Object> ToCartQuantityList_items_4; //DefaultListModel list used to track the quantity of items in the Cart List
	                                                                                                                                 
	public static JList<Object> JListCartList; //JList element that displays the Products added to the Cart							 
	public static JTextArea textAreaCartTotal;  // JTextArea that displays the current total of Products added to the Shopping List  
	public static JList<Object> JListCartQuantity; //JList element that displays the total quantity of products in the Cart          
																																		
	public static int[] cartPriceArray = new int[15]; //an arrray that is used to tabulate the total amount of the Cart
	public static Object[] trackPrices = new String[20]; //used to track the order of indexes that were added to the Cart	
	
	private JButton btnEmptyCart; //button that empties the Cart
	private JButton btnRemoveItem; //button that removes selected product from Cart
	private JButton btnAddItem; //button that adds a quanity of one to a product in the Cart
	private JButton btnCheckoutNow; //button that initiates the checkout process
	
	private static int counter = 0; //int variable that is used in the addCartprice() method
	public static int sum; ////int variable that is used in the addCartprice() method and rearrangeArray() method	
	public static boolean check = true; //boolean variable used for the 'btnRemoveItem' action method
	
	/**
	 * Calls initComponents() which is a method that contains all initialized (structural) components of the JPanel.<br>
	 * Calls createEvents() which is a method that holds all 'action' events (listeners).<br>
	 * Sets the initial total price (visible text only) of the textAreaCartTotal to $0.00.<br>
	 * Sets the JListCartQuantity to DefaultListModel: 'ToCartQuantityList_items_4'.<br>
	 * 
	 */
	public cartClass() { //constructor 
		
		initComponents(); //calls initComponents() method; builds all structural elements of the panel
		createEvents(); //calls createEvents()() method; builds all events that happen (actions)
		textAreaCartTotal.setText("$0.00"); //sets the initial total of the Shopping List to $0.00
		JListCartQuantity.setModel(ToCartQuantityList_items_4); //sets the Cart quantity using 'ToCartQuantityList_items_4'
				
	}
	
	/**
	 * Sets variable 'sum' to 0 before adding up the total amount of all products in priceArray[].<br>
	 * This loop resets 'sum' to the newly added up total.<br> * 
	 * 
	 */
	public static void setSum() {
		
		sum = 0; //sets the variable initially to 0
		for(int i = 0; i < cartPriceArray.length; i++){ //loop to add up the total price that is in cartPriceArray
			sum += cartPriceArray[i]; //adds up the cartPriceArray[] and stores it in the variable sum
		}		
		
	}

	/**
	 * Adds the prices of the products added to the Cart from combobox (via productSearchClass).<br>
	 * Finds the price of the selected product from productSearchClass.prices[index] and adds it to cartPriceArray[].<br>
	 * Manages a field variable: 'sum' that is used in setPriceTotal() method.<br>
	 * 
	 * @param index   price of the selected product to add
	 */
	public static void addCartprice(int index) { //method that adds up the Cart price
		
		Object cost = productSearchClass.prices[index]; //this creates an object variable that is initialized from the passed parameter/prices[]
		int cost_value = Integer.parseInt(cost.toString()); //this converts the object to integer
		cartPriceArray[counter] = cost_value; //this loads the cartPriceArray[]
		counter++; //increments counter variable
		setSum();
		
	}
	
	/**
	 * Adjusts the total price in the Cart after the 'Add One to Cart' button is pushed for selected product.<br>
	 * Finds the price of the selected product from cartPriceArray[index] and adds it to cartPriceArray[].<br>
	 * Manages a field variable: 'sum' that is used in setPriceTotal() method.<br>
	 * 
	 * @param index   price of the selected product to add
	 */
	public void adjCartPricesAdd(int index) { //method to add up the prices after adding a quantity of one from the Cart
		
		Object priceFound = trackPrices[index];
		int priceOfProduct = Integer.parseInt(priceFound.toString()); 				
		Object adjust = cartPriceArray[index]; //sets Object variable: 'adjust' to the element found at cartPriceArray[] using parameter: 'index'
		int currentPrice = Integer.parseInt(adjust.toString()); //this converts the object to integer
		int added = currentPrice + priceOfProduct;
		cartPriceArray[index] = added; //this loads the cartPriceArray[]
		setSum();
		
	}
	
	/**
	 * Adjusts the total price in the Cart after the 'Remove One Item' button is pushed for selected product.<br>
	 * Finds the price of the selected product from cartPriceArray[index] and removes it from cartPriceArray[].<br>
	 * Manages a field variable: 'sum' that is used in setPriceTotal() method.<br>
	 * 
	 * @param index    price of the selected product to remove
	 */
	public void adjCartPricesRemove(int index) {
		
		Object priceFound = trackPrices[index];
		int priceOfProduct = Integer.parseInt(priceFound.toString()); 				
		Object adjust = cartPriceArray[index]; //sets Object variable: 'adjust' to the element found at cartPriceArray[] using parameter: 'index'
		int currentPrice = Integer.parseInt(adjust.toString()); //this converts the object to integer
		int remove = currentPrice - priceOfProduct;
		cartPriceArray[index] = remove; //this loads the cartPriceArray[]
		setSum();
			
	}
	
	/**
	 * Sets the textAreaCartTotal with the total price of all products in the Cart.<br>
	 * 
	 */
	public static void setCartPriceTotal() { //method which sets the Cart Total
	
		textAreaCartTotal.setText(""); //clears text from textAreaTotal
		String total = Integer.toString(sum); //converts integer to String needed to display in textAreaTotal box
		textAreaCartTotal.append("$" + total + ".00"); //displays the current total price from the shopping list in the textAreaTotal box

	}
	/**
	 * Rearranges the cartPriceArray when an item is removed.<br>
	 * Used only if product was added straight from the 'fast' shipping method (No Shopping List) or if the quantity of selected product is 1.<br>
	 * Creates temp array to add all prices in cartPriceArray[] minus the price which is passed as a parameter: 'element'.<br>
	 * Resets textAreaCartTotal, cartPriceArray[], counter, and sum if the total price in the Cart equals 0.<br>
	 * 
	 * @param element   price of the selected product that is to be removed 
	 */
	public static void rearrangeCart(int element) { //method that rearranges the cartPriceArray when an item is removed
		
		int[] cartPriceArray2 = new int[cartPriceArray.length -1]; //initializes the temp cartPriceArray2 to the length of cartPriceArray.length -1]
		for(int i = 0, k = 0; i < cartPriceArray.length; i++){ //loop each element of cartPriceArray
			if(cartPriceArray[i] != element){ //if statement; if cartPriceArray at index i does not equal parameter x
				cartPriceArray2[k] = cartPriceArray[i];	//if statement is true, then load cartPriceArray2 at index k with cartPriceArray at index i
				k++; //increment k
			}			
		}
		cartPriceArray = cartPriceArray2; //after for loop, set cartPriceArray[] to temp cartPriceArray2[]	
		int addedUp = Arrays.stream(cartPriceArray).sum(); //add  up the total sum of cartPriceArray and set it to temp int variable: addedUp		
		if (addedUp == 0) {	//if addUp is equal to 0			
			textAreaCartTotal.setText("$0.00"); //this resets the textAreaCartTotal box back to empty
			cartPriceArray = new int[15]; // this resets the cartPriceArray[]
			trackPrices = new String[20];
			counter = 0; //this resets the variabale counter
			sum = 0; //this resets the variable sum			
		}

	}
	
	/**
	 * This method is run if the product that is selected to remove was added from the 'fast' method in productSearchClass (No Shopping List).<br>
	 * Includes two 'if' checks that determine if the quantity of the selected product is equal or greater than 1.<br>
	 * If the quantity is greater than one, decrement the quantity by 1 and then call adjCartPricesRemove() to adjust the total price of the Cart.<br>
	 * If the quantity equals one, then remove that product from Cart list and clear the associated quantity amount.<br>
	 * Calls rearrangeCart() to adjust the total price of the cart after product is removed.<br>
	 * 
	 */
	public void removeItemFastShop() {
		
		int select = JListCartList.getSelectedIndex(); //sets temp int variable: select to the selected index from JListCartList
		Object value = ToCartQuantityList_items_4.getElementAt(select);
		int valueInt = Integer.parseInt(value.toString());
		
		if ( valueInt > 1) { //if 'valueInt' is greater than 1, run the code
			
			Object evaluatedValue = ToCartQuantityList_items_4.getElementAt(select); 
			int valueInt2 = Integer.parseInt(evaluatedValue.toString());
			valueInt2 = valueInt2 - 1; 
			ToCartQuantityList_items_4.set( select, valueInt2); 
			adjCartPricesRemove(select);
			setCartPriceTotal();
					
		}
		
		if ( valueInt == 1) { 
								
			ToCartQuantityList_items_4.removeElementAt(select); //removes the quantity from ToCartQuantityList_items_4 using temp variable: 'select'
			int remove = cartPriceArray[select];	//uses select variable as the index for cartPriceArray and assigns to temp variable remove									
			rearrangeCart(remove); //calls rearrangeArray() methd and passes variable remove as parameter
			CartList_items_2.remove(select); //removes the selected product from CartList_items_2
			int added = Arrays.stream(cartPriceArray).sum(); //adds up the total sum of cartPriceArray and assigns it to temp int variable: added		
			textAreaCartTotal.setText(""); //empties the textAreaCartTotal text
			textAreaCartTotal.append("$" + added + ".00"); //sets the textAreaCartTotal text with the variable: added 

		}
		
//		for(int i = 0; i < cartPriceArray.length; i++) { //for-loop used for de-bugging						
//			System.out.println("Removed Array (fast shop method): " + cartPriceArray[i]);					
//		}
		
	}
	
	/**
	 * This method is run if the product that is selected to remove was added from the Shopping List in productSearchClass.<br>
	 * Includes two 'if' checks that determine if the quantity of the selected product is equal or greater than 1.<br>
	 * If the quantity is greater than one, decrement the quantity by 1 and then call adjCartPricesRemove() to adjust the total price of the Cart.<br>
	 * If the quantity equals one, then remove that product from Cart list and clear the associated quantity amount.<br>
	 * Calls rearrangeCart() to adjust the total price of the cart after product is removed.<br>
	 * 
	 */
	public void removeItemShopList() {
		
		int select2 = JListCartList.getSelectedIndex(); //get the selected index from the Cart list and assign to variable: 'select2'
		Object value = ToCartQuantityList_items_4.getElementAt(select2); //use the variable: 'select2' as an index to get the element from 'ToCartQuantityList_items_4' and assign that to Object: 'value' 
		int valueInt = Integer.parseInt(value.toString()); //convert the Object: 'value' to the int variable: 'valueInt'
		//JOptionPane.showMessageDialog(null, valueInt);
		
		if ( valueInt > 1) { //if 'valueInt' is greater than 1, run the code
			
			Object evaluatedValue = ToCartQuantityList_items_4.getElementAt(select2); //assign Object: 'evaluatedValue' to the element using index variable: 'select2'				
			int valueInt2 = Integer.parseInt(evaluatedValue.toString()); //convert Object to int
			valueInt2 = valueInt2 - 1; //subract 'valueInt2' by 1 
			ToCartQuantityList_items_4.set( select2, valueInt2); //adjust the quantity using index 'select2' by inserting 'valueInt2'					
			adjCartPricesRemove(select2); //call the adjustCartTotal() using the parameter: 'select2'
			setCartPriceTotal(); //adjust the cart price total
			//JOptionPane.showMessageDialog(null, select2);
										
		}	
			
		if ( valueInt == 1) { //when the quantity of the selected product in the Cart List = 1, run this code
			
			CartList_items_2.removeElementAt(select2); //remove the selected element from 'ToCartShopList_items_3'
			ToCartQuantityList_items_4.removeElementAt(select2); //remove the selected element from 'ToCartQuantityList_items_4'
			int remove2 = cartPriceArray[select2];	//uses select variable as the index for cartPriceArray and assigns to temp variable remove									
			rearrangeCart(remove2);						
			int added = Arrays.stream(cartPriceArray).sum(); 												
			textAreaCartTotal.setText(""); //empties the textAreaCartTotal text
			textAreaCartTotal.append("$" + added + ".00"); //sets the textAreaCartTotal text with the variable: added 
		
		}
		
//		for(int i = 0; i < cartPriceArray.length; i++) { //for-loop used for de-bugging							
//			System.out.println("Removed Array (Shop List method): " + cartPriceArray[i]);					
//		}
	}
	
	/**
	 * Removes all content of the Cart.<br>
	 * Resets crucial code for the operation of Cart such as lists, arrays, and variables.<br>
	 *
	 */
	public static void emptyCart() {
		
		ToCartQuantityList_items_4.removeAllElements(); //empties out the quantity box
		CartList_items_2.removeAllElements(); //this clears all elements from DefaultListModel: CartList_items_2
		productSearchClass.ToCartShopList_items_3.removeAllElements(); //this clears all elements from DefaultListModel: ToCartShopList_items_3
		textAreaCartTotal.setText("$0.00"); //this resets the textAreaCartTotal box back to empty
		cartPriceArray = new int[15]; // this resets the cartPriceArray[]
		trackPrices = new String[20];
		productSearchClass.priceArray = new int[15];
		productSearchClass.trackPrices = new String[20];
		productSearchClass.pricesIndex = new int[25];
		productSearchClass.increment = 0;
		sum = 0;//this resets the variable sum	
		
	}
	
	/**
	 * Holds all 'action' events (listeners) for the GUI.<br>
	 * Primarily used for cleaner organization and management.<br>
	 * As of this current release, it holds three actions for three buttons: 'Add One', 'Remove', and 'Empty'.<p>
	 * 
	 * 'Add One' button first checks to see if there is at least one product in the Cart.  If there is, the total<br>
	 * price of the cart is calculated by calling adjCartPricesAdd() and then sdisplays the new total.<p>
	 * 
	 * 'Remove' button first checks to see if there is at least one product in the Cart. If there is, the selected<br>
	 * product is checked to see if it was added from the 'fast' shop method (no Shopping List used) or if it was<br>
	 * added using the Shopping List. the item is then removed from the Cart and the total price is recalculated.<p>
	 * 
	 * 'Empty' button removes all products from the Cart and resets all associated variables, lists, and arrays.<br>
	 * 
	 */
	private void createEvents() { //this method initializes all event elements of the panel
		
		btnAddItem.addActionListener(new ActionListener() { //this action method for button: 'btnAddItem' adds a quantity of one to Cart 
			public void actionPerformed(ActionEvent e) {
								
				if (! JListCartList.isSelectionEmpty()) { //if the Cart List is not empty, run the code
								
					int index = JListCartList.getSelectedIndex(); //assigns the variable: 'index' with the index selected from the Cart list											
						
						adjCartPricesAdd(index); 
						setCartPriceTotal(); 
						
						Object number = ToCartQuantityList_items_4.getElementAt(index); //assigns Object varaiable: 'number' with the product selected
						int convertedNumber2 = Integer.parseInt(number.toString()); //converts Object to int
						int addedUp = convertedNumber2+ 1; //increments the variable: 'addedUp' by 1
						ToCartQuantityList_items_4.setElementAt(addedUp, index); //sets the Quantity 					
					
//					for(int i = 0; i < cartPriceArray.length; i++) { //loop for de-bugging purposes						
//						System.out.println("Added Array: " + cartPriceArray[i]);												
//					}
										
				}
				else { //if the Cart List is empty or no product is selected in the Cart, display the pop-up
					
					JOptionPane.showMessageDialog(null, "Cart is empty or no product selected", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message
					
				}
			}
		});
		
		btnRemoveItem.addActionListener(new ActionListener() { //this action method for button: btnRemoveItem removes a selected product from cart
			public void actionPerformed(ActionEvent e) {
				
				if (! JListCartList.isSelectionEmpty()){ //if the Cart List is not empty, run the code
				 
					if ( check == true) { //if the item to be removed from cart was added straight from the 'add to cart' button from productSearchClass (not using the Shopping List)								
						removeItemFastShop();						
					}
					else { //if the item to be removed was added from the Shopping list from productSearchClass					
						removeItemShopList();											
					}																	
				}
				else { //if no product is selected in the Cart List, display the pop-up
					
					JOptionPane.showMessageDialog(null, "Please select an item to remove", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message
					
				}
			}
		});
		
		btnEmptyCart.addActionListener(new ActionListener() { //this action method for button: btnEmptyCart emptys the cart
			public void actionPerformed(ActionEvent e) {	
							
				emptyCart();
			
			}
		});
		
		btnCheckoutNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				checkoutClass.transferCart();
				emptyCart();
				JOptionPane.showMessageDialog(null, "Cart Transferred To Checkout Tab", "Please Head To Checkout", JOptionPane.INFORMATION_MESSAGE); //Displays a pop-up message
				
			}
		});
	
	}
	
	/**
	 * Contains all initialized (structural) components of the JPanel.<br>
	 * This code largely generated by WindowBuilder.<br>
	 * 
	 */
	private void initComponents() { //this method initializes all structural elements of the panel
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(401, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		ToCartQuantityList_items_4 = new DefaultListModel<Object>();
		
		JLabel lblItemsInCart = new JLabel("Items currently in Cart");
		lblItemsInCart.setIcon(new ImageIcon(cartClass.class.getResource("/icons/Full basket.png")));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		textAreaCartTotal = new JTextArea();
		textAreaCartTotal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblTotal = new JLabel("Total = ");
		
		btnEmptyCart = new JButton("Empty");
		
		JLabel lblNoShipOrTaxes = new JLabel("*Does not include S&H or taxes");
		lblNoShipOrTaxes.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		
		JLabel lblEmptyCart = new JLabel("Empty Cart:");
		lblEmptyCart.setIcon(new ImageIcon(cartClass.class.getResource("/icons/Remove from basket.png")));
		
		btnRemoveItem = new JButton("Remove");
	
		JLabel lblRemoveItem = new JLabel("Remove one Item:");
		lblRemoveItem.setIcon(new ImageIcon(cartClass.class.getResource("/icons/Remove from basket.png")));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblQuantity = new JLabel("Qty:");
		
		JLabel lblAddOneToCart = new JLabel("Add one to Cart:");
		lblAddOneToCart.setIcon(new ImageIcon(cartClass.class.getResource("/icons/basket2.png")));
		
		btnAddItem = new JButton("Add One");
		
		btnCheckoutNow = new JButton("Checkout");
		
		JLabel lblNewLabel = new JLabel("To Checkout:");
		lblNewLabel.setIcon(new ImageIcon(cartClass.class.getResource("/icons/Full basket.png")));
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(15)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblRemoveItem)
								.addComponent(lblEmptyCart)
								.addComponent(lblAddOneToCart)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnEmptyCart, 0, 0, Short.MAX_VALUE)
										.addComponent(btnRemoveItem, 0, 0, Short.MAX_VALUE)
										.addComponent(btnAddItem, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblTotal)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textAreaCartTotal, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblNoShipOrTaxes, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnCheckoutNow, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(79)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblItemsInCart)
									.addGap(64))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblQuantity, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(98, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblQuantity)
						.addComponent(lblItemsInCart))
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaCartTotal, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTotal))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNoShipOrTaxes, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAddOneToCart)
								.addComponent(btnAddItem))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRemoveItem)
								.addComponent(lblRemoveItem))
							.addGap(9)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnEmptyCart)
								.addComponent(lblEmptyCart))
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCheckoutNow)
								.addComponent(lblNewLabel))))
					.addContainerGap(226, Short.MAX_VALUE))
		);
		
		JListCartQuantity = new JList<Object>();
		scrollPane_1.setViewportView(JListCartQuantity);
		CartList_items_2 = new DefaultListModel<Object>();
		JListCartList = new JList<Object>(); //this creates a new JList element that will display the Products added to the Shopping List
		JListCartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JListCartList.setModel(new AbstractListModel<Object>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		JListCartList.setVisibleRowCount(5);
		JListCartList.setName("");
		scrollPane.setViewportView(JListCartList);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
	}	
}
