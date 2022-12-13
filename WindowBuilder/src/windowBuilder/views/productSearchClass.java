package windowBuilder.views;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.AbstractListModel;
import javax.swing.SwingConstants;

/**
 * This class handles the bulk of tasks associated with the user searching for a product.<br>
 * It features two modes that are selected by the user: Fast shopping that does not use a <br>
 * Shopping List and another option that does.<p>
 * 
 * The 'fast' shopping feature alows the user to browse a combobox which holds all products <br>
 * for sale.  The combobox displays ID, Name, and Price of product. Once an item is selected, <br>
 * an 'Add to Cart' button will move the selected product to the Cart. <p>
 * 
 * The 'Shopping List' option is selected by a radio button at the top of the panel.  Once <br>
 * selected, the 'Add to cart' button for 'fast' shopping is toggled off and new features toggle on. <br>
 * These features include: Shopping List, Quantity List, Price Total text area, and several buttons <br>
 * that manipulate the products in the Shopping List.<p>
 * 
 * The 'Shopping List' option also includes two radio buttons that toggle on and off two more panels<br>
 * within the Shopping List area.  One of these panels display a description of the selected product<br>
 * and another displays the image of the product with a small title caption.<p>
 * 
 * @author Ralph Ramirez
 * @version 2022.10.31
 */
public class productSearchClass extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static Object[] products; //array that is loaded from file containing Product Names, used to load combobox (simulated database)
	public static Object[] productIDs; //array that is loaded from read file containing Product IDs, used to load combobox (simulated database)
	public static Object[] prices = new String[25]; //array that is loaded from file containing prices, used to load combobox (simulated database)
	
	public static int[] pricesIndex = new int[25]; ////used to track the order of indexes that were added to the shopping list	
	public static int[] priceArray = new int[15]; //array used to store the prices in order of added to the shopping list after pressing add to list button
	public static Object[] trackPrices = new String[20];
		
	public static DefaultListModel<Object> ToProductSearchList_items_1; //DefaultListModel list used to create list containing items added to the Search List
	public static DefaultListModel<Object> ToCartShopList_items_3; //DefaultListModel list used to create list containing items added to Cart List from Shopping List
	public static DefaultListModel<Object> ToQuantityList_items_4; //DefaultListModel list used to create list containing quantity of products in the Shopping List
	
	public static JComboBox<String> cbProducts; //Combobox that lists all products for sale
	
	private boolean check = false; // boolean variable that controls visibility to see the product image (rdbtnSeeImage)
	private boolean check2 = false; // boolean variable that controls visibility to see and use the the Shopping List (rdbtnUseList)
	private boolean check3 = false; // boolean variable that controls visibility to see the product description (rdbtnSeeDescription)
	private static boolean addedOne = false; //boolean to establish AT LEAST ONE product has been added to the Shopping List or not
	private boolean checkRepeats  = false;  //boolean used to check if the selected product has ALREADY BEEN added to Shopping List
	private boolean checkCartRepeats = false; //boolean used to check if the product is already in the Cart, fast shop method (no Shopping List)
	private boolean checkMax = false; //temp...will be deleted later after debugging
	private boolean shopMethod = true; //boolean use to track whattype of shopping method is being used. True for fast shopping, false for Shopping List
	private boolean cartCheck = false;
	
	private static int add = 0; //incremented variable used as index for priceArray, used for btnAddList_1 action event 
	private static int sum; // the sum total of the Shopping List
	private static int counter = 0; //incremented variable used as index for addPrices() 
	public static int increment = 0; //incremented variable used for trackPrices[] in this class and in cartClass.trackPrices[] 
	
	private JPanel panel_1; //panel that is used to hold all elements for the Shopping List option
	private JPanel panel_2; //panel that is used to hold the btnAddCart button and lblAdd2Cart label
	private JPanel panel_3; //panel that is used to hold the image display area
	private JPanel panel_4; //panel that is used to hold the product desciption area
	
	private JScrollPane scrollPaneShopList; //scrollpane element for the Shopping List
	private JScrollPane scrollPaneQuantity; //scrollpane element for the Quantity List
	
	private JButton btnAddToList; //button to add products from combobox to the Shopping List
	private JButton btnAddToCart; //button to add products straight to cart (radio button -> rdbtnUseList not selected), 'fast' shop method
	private JButton btnAddAllToCart; //button used to add all contents of Shopping List to Cart
	private JButton btnRemoveAll; //button to remove all contents of the Shopping List
	private JButton btnAddOneToList; //button to add one selected item from Shopping List to Cart
	private JButton btnRemoveOneItem; //button to remove one item from the Shopping List
	
	private JLabel lblRemoveAllFromList;  ///////////////////
	private JLabel lblNewLabel;	          //               //
	private JLabel lblAddAllToList;       //               //
	private JLabel lblAddOneToList;       //               //
	public static JLabel displayLabel;    //  ALL LABELS   //
	private JLabel lblNewLabel_2;         //               //
	private JLabel lblProductDescrip;     //               //
	public static JLabel lblImageDescrip; //               //
	private JLabel lblNoShipNotaxes;      ///////////////////
	
	private JList<Object> JListShopList; //JList element for Shopping List
	private JList<Object> JListQuantity; //JList that lists the quantity of products
	
	private JRadioButton rdbtnUseList; //radio button used to list the Shopping List option
	private JRadioButton rdbtnSeeImage; //radio button to see the product image
	private JRadioButton rdbtnSeeDescription; //radio button to see the product image
	
	public static JTextPane txtpnProductDescription; //text pane for product description
	
	private static JTextArea textAreaTotal; //text area to display the total cost of the products added to the Shopping List
	
	productClass productObject = new productClass(); //instantiates an object of productClass() called 'productObject'
	
	/**
	 * Calls initComponents() which is a method that contains all initialized (structural) GUI components of the JPanel.<br>
	 * Calls createEvents() which is a method that holds all 'action' events (listeners) in the GUI.<br>
	 * Calls loadProductCombobox() which is a method that loads the combobox with products from the (simulated) database.<br>
	 * Sets the Shopping List (panel_1) visibility to off upon application load.<br>
	 * Sets the 'fast' shipping 'Add to Cart' (panel_2) visibility to on upon application load.<br>
	 * Turns off visibility to panel_3 (display image area) and panel_4 (product description) upon application load.<br>
	 * 
	 *@throws FileNotFoundException
	 */
	public productSearchClass() throws FileNotFoundException{ //constructor
		
		initComponents(); //calls initComponents() which holds/builds all the code for the GUI
		createEvents(); //calls createEvents();				       	
		loadProductCombobox();	//calls loadCombobox()		
		panel_1.setVisible(false); //hides all elements in panel 1 (Shopping List option)
		panel_2.setVisible(true); //reveals all elemnts in panel_2 (btnAddCart button and associated label)
		panel_3.setVisible(false); //hides the elements in panel_3 (display image area)
		panel_4.setVisible(false); //hides the elements in panel_4 (product description area)		
	}
	
	/**
	 * Sets variable 'sum' to 0 before adding up the total amount of all products in priceArray[].<br>
	 * This loop resets 'sum' to the newly added up total.<br>
	 * 
	 */
	public static void setSum() {
		
		sum = 0; //sets the variable initially to 0
		for(int i = 0; i < priceArray.length; i++){ //loop to add up the total price that is in priceArray
			sum += priceArray[i]; //adds up the priceArray[] and stores it in the variable sum
		}	
		
	}
		
	/**
	 * Adds the prices of the products added to the Shopping List from combobox.<br>
	 * Finds the price of the selected product from prices[] and adds it to priceArray[].<br>
	 * Manages a field variable: 'sum' that is the total amount of all prices in priceArray[].<br>
	 * 
	 * @param index   used as the index for prices[]
	 */
	public void addPrices(int index) { //method to add up the prices
		
		Object first = prices[index]; //this creates an object variable that is initialized from the passed parameter, used to get selected index and match to price
		int second = Integer.parseInt(first.toString()); //this converts the object to integer
		priceArray[counter] = second; //this loads the priceArray[]		
		counter++; //increments counter variable
		setSum();	
	}
	
	/**
	 * Method that adds a quantity of one for the selected product in the Shopping List.<br>
	 * Uses the array trackPrices[] with passed parameter as the index to know how much to add to the priceArray[].<br>
	 * Updates the variable 'sum' to the new overall total of all product prices in priceArray[].<br>
	 * 
	 * @param index   used to set index for both trackPrices[] and priceArray[]
	 */
	public void addOne(int index) {
		
		Object priceFound = trackPrices[index];
		int priceOfProduct = Integer.parseInt(priceFound.toString()); 				
		Object adjust = priceArray[index]; //sets Object variable: 'adjust' to the element found at cartPriceArray[] using parameter: 'index'
		int currentPrice = Integer.parseInt(adjust.toString()); //this converts the object to integer
		int added = currentPrice + priceOfProduct;
		priceArray[index] = added; //this loads the cartPriceArray[]
		setSum();
		
	}
	
	/**
	 * Method that removes a quantity of one for the selected product in the Shopping List.<br>
	 * Uses the array trackPrices[] with passed parameter as the index to know how much to remove from the priceArray[].<br>
	 * Updates the variable 'sum' to the new overall total of all product prices in priceArray[].<br>
	 * 
	 * @param index   used to set index for both trackPrices[] and priceArray[]
	 */
	public void removeOne(int index) {
		
		Object priceFound = trackPrices[index];
		int priceOfProduct = Integer.parseInt(priceFound.toString()); 				
		Object adjust = priceArray[index]; //sets Object variable: 'adjust' to the element found at cartPriceArray[] using parameter: 'index'
		int currentPrice = Integer.parseInt(adjust.toString()); //this converts the object to integer
		int remove = currentPrice - priceOfProduct;
		priceArray[index] = remove; //this loads the cartPriceArray[]
		setSum();
		
	}
	
	/**
	 * Adjusts the Shopping List prices after an item that is selected to remove only has a quantity of 1.<br>
	 * Finds the price (using the passed parameter 'element') in the priceArray[] and builds a temp<br>
	 * array without that price in it.<p>
	 * 
	 * Resets priceArray[] to the newly built temp array.<br>
	 * Rebuilds trackPrices[] to match priceArray[].<br>
	 * Sets the variable 'sum' to the new, total price in the Cart.<p>
	 * 
	 * Checks to see (after the selected product is removed) if the Shopping List is completely empty.<br>
	 * If the Shopping List is empty, resets crucial arrays, variables, and GUI elements.<br>
	 * 
	 * @param element   the price of the selected product to be removed from the Shopping List
	 */
	public static void rearrangeList(int element) { //method that rearranges the priceArray when an item is removed
		
		int[] tempArray = new int[priceArray.length -1]; //initializes the tempArrayto the length of priceArray.length -1]
		for(int i = 0, k = 0; i < priceArray.length; i++){ //loop each element of priceArray
			if(priceArray[i] != element){ //if statement; if priceArray at index i does not equal parameter element
				tempArray[k] = priceArray[i];	//if statement is true, then load tempArray at index k with priceArray at index i
				k++; //increment k
			}			
		}
		priceArray = tempArray; //after for loop, set priceArray[] to tempArray[]	
		
		for (int i = 0; i < priceArray.length; i++) {  //for-loop to match the priceArray[] to trackPrices[]
            trackPrices[i] = String.valueOf(priceArray[i]);
        }
		
		setSum();		
		
		int addedUp = Arrays.stream(priceArray).sum(); //add  up the total sum of priceArray and set it to temp int variable: addedUp		
		if (addedUp == 0) {	//if addUp is equal to 0			
			textAreaTotal.setText("$0.00"); //this resets the textAreaTotal box back to empty
			priceArray = new int[15]; // this resets the priceArray[]
			trackPrices = new String[20];
			addedOne = false; //resets the boolean variable: 'addedOne' to false. this establishes that no product has been added to the Shopping List			
			counter = 0;
			sum = 0; //this resets the variable sum	
		}
	}
		
	/**
	 * Loads the combobox with products using three arrays (simulated database).<br>
	 * 
	 */
	public void loadProductCombobox() { //method to load the combobox
		
		for(int i = 0; i < products.length; i++) { //for-loop
			String line = productIDs[i].toString(); //pulls Object element from productIDs[] and converts to String variable line
			String line2 = products[i].toString(); //pulls Object element from products[] and converts to String variable line2
			cbProducts.addItem(line + " - " + line2 + " - " + "Price: " + "$" + prices[i] + ".00"); //loads the JComboBox cbProducts_1 with Products IDs, Product Names, and Prices			
		}		
	}
	
	/**
	 * Sets the textAreaTotal with the total price of all products in the Shopping List<br>
	 * 
	 */
	public void setPriceTotal() { //method that sets the total price text area (textAreaTotal_1) in Shopping List
		
		textAreaTotal.setText(""); //clears text from textAreaTotal
		String total = Integer.toString(sum); //converts integer to String needed to display in textAreaTotal box
		textAreaTotal.append("$" + total + ".00"); //displays the current total price from the shopping list in the textAreaTotal box		
	}
	
	/**
	 * Used to add one DefaultListModel to another.<br>
	 * 
	 * @param <T>     used to represent the type of object stored
	 * @param from    the list that represents the change 'from'
	 * @param to      the list that represents the change'to'
	 */
	protected static <T> void addTo(ListModel<T> from, DefaultListModel<T> to) { //method used to add one ListModel to another DefaultListModel
	    for (int index = 0; index < from.getSize(); index++) {
	        to.addElement(from.getElementAt(index));
	    }
	}
	
	/**
	 * Method used to reset all crucial GUI elements, arrays, and variables after the 'Remove All' button is pressed.<br>
	 * 
	 */
	public void removeAll() {
		
		txtpnProductDescription.setText(null); //clears out the description text area
		displayLabel.setIcon(null); //clears out the product image from panel_03					
		ToQuantityList_items_4.removeAllElements(); //clears out the DefaultListModel -> ToQuantityList_items_4 (numbers displayed in the quanity box)
		ToProductSearchList_items_1.removeAllElements(); //this clears all elements from DefaultListModel -> ToProductSearchList_items_1					
		ToCartShopList_items_3.removeAllElements();
		textAreaTotal.setText("$0.00"); //this resets the textAreaTotal box back to displaying $0.00
		priceArray = new int[15]; //this resets the priceArray[]
		trackPrices = new String[20];
		pricesIndex = new int[25];
		productObject.trackImages = new Integer[25]; //this resets the trackImages[] in productClass					
		productObject.track = 0; //resets track variable to 0 in productClass
		add = 0;
		sum = 0;
		counter = 0;
		increment = 0;			
		
	}
	
	/**
	 * Used to copy arrays from productSearchClass to cartClass.<br>
	 * The copied array: priceArray[] to cartClass.cartPriceArray[] is used to tabulate the total 
	 * amount of the products that were added to Cart.<br>
	 * The copied array: trackPrices[] to cartClass.trackPrices[] is used to track the prices and 
	 * index of the products as a reference for quantities added or removed when in the Cart.<br>
	 * 
	 */
	public void copyArrays() {
		
		for (int i = 0, j = 0; i < cartClass.cartPriceArray.length; i++) {
			
			if (cartClass.cartPriceArray[i] == 0) {				
				cartClass.cartPriceArray[i] = priceArray[j];
				j++;
			}			
		}
		
		for (int i = 0, j = 0; i < cartClass.trackPrices.length; i++) {
			
			if (cartClass.trackPrices[i] == null) {				
				cartClass.trackPrices[i] = trackPrices[j];
				j++;
			}			
		}		
	}
	
	/**
	 * Holds all 'action' events (listeners).<br>
	 * Primarily used for cleaner organization and management.<br>
	 * Contains a MouseListener that detects one click in the Shopping List and displays image and description for that selected product.<br> 
	 * 	 
	 */
	private void createEvents() { //method that stores all action events
		
		MouseListener mouseListener = new MouseAdapter() { //mouse action listener to detect when a user clicks on an item in the Shopping List
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) { //if detected only one click, run the code      	           
		           	
					int selectedIx = JListShopList.getSelectedIndex(); //creates an array that stores the index of the clicked on product. Will be only one index			    				 
					int image = productObject.trackImages[selectedIx]; //sets the temp variable: 'image' to the index found at trackImages[image]								
					productObject.loadImages(image); //calls the loadImages() using the temp variable: 'image' (index of trackImages[image]) to display correct image in panel_3
		        }	        		        
		    }
		};
			
		btnAddToList.addActionListener(new ActionListener() { //button action method that adds item from combobox to Shopping List
			public void actionPerformed(ActionEvent e) {
				
				if (JListShopList.getModel().getSize() > 7) {
					JOptionPane.showMessageDialog(null, "Maximum products in Shopping List!", "Alert", JOptionPane.ERROR_MESSAGE);
					checkMax = true;
				}
				
				cartCheck = false; //sets the boolean to false when button is pressed.  This is used to check if the selected product has already been added to the Cart
				checkRepeats = false; //sets the boolean to false when button is pressed. This is used to check if the selected product has already been added to Shopping List
				addedOne = true; //sets the boolean variable:'addedOne' to true.  This is to establish at least one product has been added to the Shopping List 				
				int cbIndex = cbProducts.getSelectedIndex(); //creates variable send to pass to addPrices() method from cbProducts_1					
				String verify = cbProducts.getItemAt(cbIndex); //sets variable: 'verify' to the selected product usine variable: 'cbIndex'
				
				for (int i=0; i<cartClass.JListCartList.getModel().getSize(); i++) {
					
					if (cartClass.CartList_items_2.get(i) == verify) {
						cartCheck = true;
						JOptionPane.showMessageDialog(null, "Product already in Cart!", "Alert", JOptionPane.ERROR_MESSAGE); //display pop-up message
					}				
				}
							
				for (int i=0; i<JListShopList.getModel().getSize(); i++) { //for-loop using the size of the 'JListShopList_1'					
					if (ToProductSearchList_items_1.get(i) == verify) { //if-statement that checks to see if the selected item in the combobox that the user is trying to add to Shopping List is already there						
						checkRepeats = true; //if the product is already in the Shopping list, set 'checkRepeats' to true
						JOptionPane.showMessageDialog(null, "Already added to Shopping List!", "Alert", JOptionPane.ERROR_MESSAGE); //display pop-up message						
					}										
				}
				//JOptionPane.showMessageDialog(null,verify);
				
				if (checkRepeats == false & checkMax == false & cartCheck == false) {
					
					trackPrices[increment] = prices[cbIndex];					
					cartClass.trackPrices[increment] = prices[cbIndex];
					increment++;
					
					productObject.setImageIndex(cbIndex); //uses cbIndex variable as a parameter to call setImageIndex().  This tracks the order of indexes to be used for the description and image display						
					productObject.loadImages(cbIndex); //calls the loadImages() using 'cbIndex' as a parameter			
					pricesIndex[add] = cbIndex; //used to track price index
					add++; //increments the add variable for prices[] for the next use	
					
					addPrices(cbIndex); //calls addPrices method				
					setPriceTotal(); //calls setPriceTotal method							
					ToCartShopList_items_3.addElement(cbProducts.getSelectedItem()); //	//This adds the selected element from cbProducts to DefaultListModel ToCartShopList_items_3			
					ToProductSearchList_items_1.addElement(cbProducts.getSelectedItem());  //This adds the selected element from cbProducts to DefaultListModel ToProductSearchList_items_1								
					ToQuantityList_items_4.addElement("1"); //adds a 1 to the DefaultListModel: 'ToQuantityList_items_4'
					JListQuantity.setModel(ToQuantityList_items_4); //sets the model of the Shopping List quantity box					
					JListShopList.setModel(ToProductSearchList_items_1); //this lists the selected DefaultListModel items in the JListShopList shopping list							
					JListShopList.addMouseListener(mouseListener); //a listener that detects when a product is selected in the shopping list
					//JOptionPane.showMessageDialog(null,S);
				}
				
//				for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes						
//					System.out.println("Added to List priceArray[]: " + priceArray[i] + "   trackPrices[] Array: " + trackPrices[i] + "   Sum: " + sum);												
//					}
				
			} 
		});
		
		btnAddToCart.addActionListener(new ActionListener() { //button action method that adds product from combobox to Cart
			public void actionPerformed(ActionEvent e) {
				
				checkCartRepeats = false; //always sets the boolean to false when button is pressed				
				cartClass.check = true; //always sets the boolean to true when button is pressed
				int index = cbProducts.getSelectedIndex(); //creates variable index to pass to addPrices() method.  Used to get the selected product from combobox			
				String verify = cbProducts.getItemAt(index); //sets variable: 'verify' to the selected product using variable: 'index'
				
				for (int i=0; i<cartClass.JListCartList.getModel().getSize(); i++) { //for loop that checks the cartClass.CartList_items_2 for a product (variable: 'verify') already added to the Cart					
					if (cartClass.CartList_items_2.get(i) == verify) { //if statement that looks for a product already added to the Cart						
						checkCartRepeats = true; //if there is already a product a user is attempting to add again, set the boolean to true
						JOptionPane.showMessageDialog(null, "Already Added to Cart! Add quantity from Cart tab...", "Alert", JOptionPane.ERROR_MESSAGE); //display pop-up message						
					}								
				}			
				if (checkCartRepeats == false) { //if the boolean is false, run the code
					cartClass.addCartprice(index); //calls the	addCartprice() from cartClass with index variable as parameter			
					cartClass.setCartPriceTotal(); //calls the setCartPriceTotal() from cartClass					
					cartClass.ToCartQuantityList_items_4.addElement("1"); //adds a 1 to the DefaultListModel: 'ToCartQuantityList_items_4' in the cartClass
					cartClass.JListCartQuantity.setModel(cartClass.ToCartQuantityList_items_4); //sets the model (cartClass quantity box)										
					cartClass.trackPrices[increment] = prices[index];
					increment++;										
					cartClass.CartList_items_2.addElement(cbProducts.getSelectedItem()); //adds the product stored in combobox (cbProducts_1) to DefaultListModel -> CartList_items_2
					cartClass.JListCartList.setModel(cartClass.CartList_items_2); //places the items in the cart (cartClass.JListCartList) from CartList_items_2
					JOptionPane.showMessageDialog(null, "Successfully added to Cart!", "Product Added", JOptionPane.INFORMATION_MESSAGE); //Displays a pop-up message
				}
			}
		});
		
		btnAddOneToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (! JListShopList.isSelectionEmpty()){ //if an item is selected in the Shopping List, then run the following code					
						
					int selectedIx = JListShopList.getSelectedIndex(); //creates a temp array that stores the selected index in the Shopping List. Will only be one item in array			    										
					addOne(selectedIx); 					
					setPriceTotal(); //calls setPriceTotal method										
					Object number = ToQuantityList_items_4.getElementAt(selectedIx); //sets Object variable: 'number' to the price stored at ToQuantityList_items_4
					int convertedNumber = Integer.parseInt(number.toString()); //converts the Object: 'number' to int: 'convertedNumber2'
					int addedUp = convertedNumber + 1; //adds the price of convertedNumber2	+ 1		
					ToQuantityList_items_4.setElementAt(addedUp, selectedIx); //sets the quantity displayed in quantity box for selected item at the right spot
					//JOptionPane.showMessageDialog(null, "Added one more -> " + products[selectedIx], "Added One", JOptionPane.INFORMATION_MESSAGE);
					
//					for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes							
//						System.out.println("Added one to priceArray[]: " + priceArray[i] + "   trackPrices[] Array: " + trackPrices[i] + "   Sum: " + sum);											
//						}
					
				}				
				else { //if the button is pushed and nothing is selected in the Shopping List, display the pop-up message
					JOptionPane.showMessageDialog(null, "Please select an item to add!", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message		
				}
				
			}
		});
		
		btnAddAllToCart.addActionListener(new ActionListener() { //button action method that adds all selected Products from Shopping List to Cart
			public void actionPerformed(ActionEvent e) {
				
				if (ToProductSearchList_items_1.isEmpty() & ! cartClass.ToCartQuantityList_items_4.isEmpty()) { 
					JOptionPane.showMessageDialog(null, "Please add products to the Shopping List first!", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message					
				}
				
				if (ToProductSearchList_items_1.isEmpty() & cartClass.ToCartQuantityList_items_4.isEmpty()) { 
					JOptionPane.showMessageDialog(null, "Please add products to the Shopping List first!", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message					
				}
											
				if (! ToProductSearchList_items_1.isEmpty() & cartClass.ToCartQuantityList_items_4.isEmpty()){ //checks to see if there is at least one item in the Shopping List to add to Cart
					
//					for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes							
//						System.out.println("List priceArray[]: " + priceArray[i] + "   List trackPrices[] Array: " + trackPrices[i]);											
//					}
					
					cartClass.check = false;
					cartClass.cartPriceArray = priceArray;
					cartClass.trackPrices = trackPrices;
					cartClass.sum = sum;
					cartClass.setCartPriceTotal();					
					addTo(ToCartShopList_items_3, cartClass.CartList_items_2);
					addTo(ToQuantityList_items_4 , cartClass.ToCartQuantityList_items_4); //calls the addTo() which takes a list 'from' and adds it 'to' another list. Basically, sets the quanity in the cart from Shopping List															
					cartClass.JListCartList.setModel(cartClass.CartList_items_2); //sets the Cart List (JListCartList)  in cartClass with all items from DefaultListModel ToCartShopList_items_3				
										
					ToCartShopList_items_3.removeAllElements();
					ToQuantityList_items_4.removeAllElements(); //clears out the quantity box from the Shopping List
					ToProductSearchList_items_1.clear(); //clears all items from DefaultListModel -> ToProductSearchList_items_1
					textAreaTotal.setText(""); //this resets the textAreaTotal box back to empty
					priceArray = new int[15]; //resets the priceArray[]
					trackPrices = new String[20]; //resets the trackPrices[]
					txtpnProductDescription.setText(null); //resets the description area
					displayLabel.setIcon(null); //resets the display image area
					add = 0;
					counter = 0;
					productObject.track = 0;
					JOptionPane.showMessageDialog(null, "Moved all items in shopping list to Cart!", "Products Moved", JOptionPane.INFORMATION_MESSAGE);
					
//					for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes							
//						System.out.println("Cart priceArray[]: " + cartClass.cartPriceArray[i] + "   Cart trackPrices[] Array: " + cartClass.trackPrices[i]);											
//					}					
					
				}				
				if (! ToProductSearchList_items_1.isEmpty() & ! cartClass.ToCartQuantityList_items_4.isEmpty()){
					
//					for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes							
//						System.out.println("List priceArray[]: " + priceArray[i] + "   List trackPrices[] Array: " + trackPrices[i]);											
//					}
					
					cartClass.check = false;
					copyArrays();
					cartClass.sum = cartClass.sum + sum;
					cartClass.setCartPriceTotal();	
					addTo(ToCartShopList_items_3, cartClass.CartList_items_2);
					addTo(ToQuantityList_items_4 , cartClass.ToCartQuantityList_items_4); //calls the addTo() which takes a list 'from' and adds it 'to' another list. Basically, sets the quanity in the cart from Shopping List															
					cartClass.JListCartList.setModel(cartClass.CartList_items_2); //sets the Cart List (JListCartList)  in cartClass with all items from DefaultListModel ToCartShopList_items_3				
					
					ToCartShopList_items_3.removeAllElements();
					ToQuantityList_items_4.removeAllElements(); //clears out the quantity box from the Shopping List
					ToProductSearchList_items_1.clear(); //clears all items from DefaultListModel -> ToProductSearchList_items_1
					textAreaTotal.setText(""); //this resets the textAreaTotal box back to empty
					priceArray = new int[15]; //resets the priceArray[]
					trackPrices = new String[20]; //resets the trackPrices[]
					txtpnProductDescription.setText(null); //resets the description area
					displayLabel.setIcon(null); //resets the display image area
					add = 0;
					counter = 0;
					productObject.track = 0;
					JOptionPane.showMessageDialog(null, "Moved all items in shopping list to Cart!", "Products Moved", JOptionPane.INFORMATION_MESSAGE);
					
//					for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes							
//						System.out.println("Cart priceArray[]: " + cartClass.cartPriceArray[i] + "   Cart trackPrices[] Array: " + cartClass.trackPrices[i] );											
//					}
										
				}		
								
			}
		});
		
		btnRemoveOneItem.addActionListener(new ActionListener() { //button action method that removes selected Product from Shopping List
			public void actionPerformed(ActionEvent e) {
				
				checkMax = false; //boolean variable used to check if max products are in the Shopping List.
				
				if (! JListShopList.isSelectionEmpty()){ //if the Shopping List is not empty, run the code
											
					int selectedIx = JListShopList.getSelectedIndex(); //creates a temp array that stores the selected index in the Shopping List. Will only be one item in array			    					
					Object quantity = ToQuantityList_items_4.get(selectedIx);
					int quantity2 = Integer.parseInt(quantity.toString());
					
					if (quantity2 == 1) {
					
						int image = productObject.trackImages[selectedIx]; //sets int variable: 'w' to the index found in 'trackImages[]'
						productObject.removeImageIndex(image); //calls the removeImageIndex() from productClass using parameter 'w'
						txtpnProductDescription.setText(null); //resets the description area
						displayLabel.setIcon(null); //resets the display image area										
						productObject.track--; //decrements the variable: 'track' that is used in 'productObject.trackImages[]' to account for the product removed
															
						int remove = priceArray[selectedIx];
						rearrangeList(remove);						
						setPriceTotal(); //adjust the Shopping List price total	
												
						ToCartShopList_items_3.removeElementAt(selectedIx);
						ToProductSearchList_items_1.removeElementAt(selectedIx); //removes the Product from Shopping List at index specified by variable: 'index'
						ToQuantityList_items_4.removeElementAt(selectedIx); //removes the quantity amounf from quantity box at index specified by variable: 'index'																			
					
					}
					else { //if the quantity of the product to be removed is greater than 1, run the code
						
						Object evaluatedValue = ToQuantityList_items_4.getElementAt(selectedIx); //assign Object: 'evaluatedValue' to the element using index variable: 'image'				
						int valueInt2 = Integer.parseInt(evaluatedValue.toString()); //convert Object to int
						valueInt2 = valueInt2 - 1; //subract 'valueInt2' by 1 
						ToQuantityList_items_4.set( selectedIx, valueInt2); //adjust the quantity using index 'image' by inserting 'valueInt2'					
												
						removeOne(selectedIx); 			
						setPriceTotal(); //adjust the Shopping List price total
						
						//JOptionPane.showMessageDialog(null, "Removed one -> " + products[selectedIx], "Removed One", JOptionPane.INFORMATION_MESSAGE);
					}
										
//					for(int i = 0; i < priceArray.length; i++) { //for-loop for de-bugging purposes						
//						System.out.println("Removed one from priceArray[]: " + priceArray[i] + "   trackPrices[] Array: " + trackPrices[i] + "   Sum: " + sum);												
//						}
					
				}
				else { //if the Shopping List is empty or if an item in the Shopping List is not selected, display the pop-up
					JOptionPane.showMessageDialog(null, "Please select an item to remove!", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message				
				}
			}
		});
		
		btnRemoveAll.addActionListener(new ActionListener() { //button action method that removes all selected Products in Shopping List
			public void actionPerformed(ActionEvent e) {
				
				if ( addedOne == true){ //checks to see if at least one product is in the Shopping List before trying to clear the Shopping List
					
					addedOne = false; //resets the boolean variable: 'addedOne' to false. this establishes that no product has been added to the Shopping List				
					removeAll();
					JOptionPane.showMessageDialog(null, "Removed all products from Shopping List", "Products Removed", JOptionPane.INFORMATION_MESSAGE);//Displays a pop-up message
					
				}
				else { //if there is nothing added to the Shopping List yet...
					JOptionPane.showMessageDialog(null, "Nothing in Shopping List to remove!", "Alert", JOptionPane.ERROR_MESSAGE); //Displays a pop-up message					
				}
			}
		});
	
		rdbtnUseList.addActionListener(new ActionListener() { //radio button action method that toggles on or off the Shopping List 
			public void actionPerformed(ActionEvent e) {
				
				textAreaTotal.setText("$0.00"); //sets the inital value of Shopping List (textAreaTotal_1) upon first use
				
				String[] options = {"No", "Yes"};
				int x = JOptionPane.showOptionDialog(null, "This switch will remove any products already added to Cart. Continue?",
		                "ALERT", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				
				if (x == 0 & shopMethod == true) { //NO, don't empty the Cart while using the 'Fast" shopping method...
					
					shopMethod = true;
					check2 = true;
					rdbtnUseList.setSelected(false);
				}
				if (x == 0 & shopMethod == false) { //NO, don't empty the Cart while using the Shopping List...
					
					shopMethod = false;			
					check2 = false;
					rdbtnUseList.setSelected(false);
				}
				
				//Otherwise, YES, empty the Cart and switch shopping method...
				
				cartClass.emptyCart();
					
				if (check2 == false) { //if check2 is false (off), run the code				
					panel_1.setVisible(true); //turn on panel_1 (Shopping List option)
					check2 = true; //set the check2 variable to true (on)				
					panel_2.setVisible(false); //turn off panel_2 (btnAddCart button and associated label)	
					rdbtnUseList.setSelected(true);
					shopMethod = false;
				}else {	//if check2 is true (on)				
					panel_1.setVisible(false); //turn off panel_1 (Shopping List option)
					check2 = false;	 //set the check2 variable to false (off)				
					panel_2.setVisible(true); //turn on panel_2 (btnAddCart button and associated label)
					shopMethod = true;
				}
				
			}
		});
		
		rdbtnSeeDescription.addActionListener(new ActionListener() { //radio button action method that togles on or off the Product Desciption area
			public void actionPerformed(ActionEvent e) {
				
				if (check3 == false) {	//if check3 is false (off), run the code				
					panel_4.setVisible(true); //turn on panel_4 (product description area)
					check3 = true; //set the check3 variable to true (on)									
				}else { //if check3 is true (on)
					panel_4.setVisible(false); //turn off panel_4 (product description area)
					check3 = false;	 //set the check3 variable to false (off)									
				}			
			}
		});
	
		rdbtnSeeImage.addActionListener(new ActionListener() { //radio button action method that toggles on or off the 'See Image' area
			public void actionPerformed(ActionEvent e) {
				
				if (check == false) {	//if check is false (off), run the code				
					panel_3.setVisible(true); ////turn on panel_3 (display image area)
					check = true;	//set the check variable to true (on)								
				}else {//if check is true (on)
					panel_3.setVisible(false); //turn off panel_3 (display image area)
					check = false; //set the check variable to false (off)										
				}				
			}
		});		
	}
	
	/**
	 * Contains all initialized (structural) components of the JPanel.<br>
	 * 
	 * @throws FileNotFoundException
	 */
	private void initComponents() throws FileNotFoundException { ////method that stores components
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(9, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		cbProducts = new JComboBox<String>();
		cbProducts.setMaximumRowCount(20);
		
		ToProductSearchList_items_1 = new DefaultListModel<Object>();
		ToCartShopList_items_3 = new DefaultListModel<Object>();
		ToQuantityList_items_4 = new DefaultListModel<Object>();
		
		JLabel lblProducts = new JLabel("Products:");
		lblProducts.setIcon(new ImageIcon(productSearchClass.class.getResource("/icons/Gear.png")));
		
		panel_1 = new JPanel();
		
		rdbtnUseList = new JRadioButton("Shop using List with Description and Image options");
		
		panel_2 = new JPanel();
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblProducts)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbProducts, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE))
						.addComponent(rdbtnUseList))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnUseList)
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbProducts, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblProducts))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 556, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		JLabel lblAdd2Cart = new JLabel("Add to Cart:");
		
		btnAddToCart = new JButton("Add To Cart");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(274, Short.MAX_VALUE)
					.addComponent(lblAdd2Cart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddToCart, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddToCart)
						.addComponent(lblAdd2Cart))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		scrollPaneShopList = new JScrollPane();
		scrollPaneShopList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		btnAddAllToCart = new JButton("Add All");
		
		btnRemoveAll = new JButton("Remove All");
		
		lblRemoveAllFromList = new JLabel("Remove all:");
		lblRemoveAllFromList.setIcon(new ImageIcon(productSearchClass.class.getResource("/icons/Remove from basket.png")));
		
		lblNewLabel = new JLabel("Total = ");
		
		textAreaTotal = new JTextArea();
		textAreaTotal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		btnAddToList = new JButton("Add Now");
		
		JLabel lblAdd2List = new JLabel("Add to Shopping List:");
		lblAdd2List.setIcon(new ImageIcon(productSearchClass.class.getResource("/icons/Price list.png")));
		
		btnAddOneToList = new JButton("Add One");
		
		lblAddAllToList = new JLabel("Add all to Cart:");
		lblAddAllToList.setIcon(new ImageIcon(productSearchClass.class.getResource("/icons/basket2.png")));
		
		lblAddOneToList = new JLabel("Add one to List:");
		lblAddOneToList.setIcon(new ImageIcon(productSearchClass.class.getResource("/icons/basket2.png")));
		
		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		rdbtnSeeDescription = new JRadioButton("See Description");
		
		rdbtnSeeImage = new JRadioButton("See Image");
		
		panel_4 = new JPanel();
		
		scrollPaneQuantity = new JScrollPane();
		scrollPaneQuantity.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblQuantity = new JLabel("Qty:");
		
		btnRemoveOneItem = new JButton("Remove");
		
		lblNewLabel_2 = new JLabel("Remove one item:");
		lblNewLabel_2.setIcon(new ImageIcon(productSearchClass.class.getResource("/icons/Remove from basket.png")));
		
		lblNoShipNotaxes = new JLabel("*Total does not include S&H or taxes");
		lblNoShipNotaxes.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		
		JLabel lblNewLabel_1 = new JLabel("*Maximum 8 Products (for now)");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
	
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblAddOneToList)
										.addComponent(lblAddAllToList)
										.addComponent(lblRemoveAllFromList)
										.addComponent(lblNewLabel_2))
									.addGap(4))
								.addComponent(rdbtnSeeDescription)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(rdbtnSeeImage)
									.addGap(35)))
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(2)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(btnRemoveOneItem)
												.addComponent(btnRemoveAll, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
											.addGap(18)
											.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(btnAddAllToCart)
												.addComponent(btnAddOneToList))
											.addGap(34)
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_1.createSequentialGroup()
													.addGap(12)
													.addComponent(lblNewLabel)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(textAreaTotal, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
													.addGap(54))
												.addComponent(lblNoShipNotaxes, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
											.addGap(11))))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPaneShopList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblQuantity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(scrollPaneQuantity, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblAdd2List)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddToList, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1)))
					.addGap(21))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAdd2List)
								.addComponent(btnAddToList)
								.addComponent(lblNewLabel_1))
							.addGap(49)
							.addComponent(rdbtnSeeDescription)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnSeeImage)
							.addGap(47))
						.addComponent(scrollPaneShopList, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblQuantity)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneQuantity, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAddOneToList)
						.addComponent(btnAddOneToList)
						.addComponent(textAreaTotal, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblAddAllToList)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(btnAddAllToCart)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnRemoveOneItem)
										.addComponent(lblNewLabel_2))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnRemoveAll)
										.addComponent(lblRemoveAllFromList))
									.addGap(12)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNoShipNotaxes, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(33, GroupLayout.PREFERRED_SIZE))
		);
		
		JListQuantity = new JList<Object>();
		JListQuantity.setModel(new AbstractListModel<Object>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneQuantity.setViewportView(JListQuantity);
		
		txtpnProductDescription = new JTextPane();
		txtpnProductDescription.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		lblProductDescrip = new JLabel("Product Description");
		lblProductDescrip.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnProductDescription, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(77)
							.addComponent(lblProductDescrip, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addComponent(lblProductDescrip, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnProductDescription, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(49))
		);
		panel_4.setLayout(gl_panel_4);
	
		
		displayLabel = new JLabel("");
		
		lblImageDescrip = new JLabel("Product Image");
		lblImageDescrip.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageDescrip.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap(18, Short.MAX_VALUE)
					.addComponent(displayLabel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
				.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
					.addGap(54)
					.addComponent(lblImageDescrip, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(57, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblImageDescrip, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(displayLabel, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		
		JListShopList = new JList<Object>();
		JListShopList.setVisibleRowCount(10);
		JListShopList.setToolTipText("");
		scrollPaneShopList.setViewportView(JListShopList);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
		
		
	}
}



