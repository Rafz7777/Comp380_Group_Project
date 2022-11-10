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
 * The 'fast' shopping feature allows the user to browse a combo box which holds all products <br>
 * for sale.  The combo box displays ID, Name, and Price of product. Once an item is selected, <br>
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
 * @author Ralph Ramirez, Matthew Bellman
 * @version 2022.11.09
 */
public class productSearchClass extends JPanel {
	
	private static int priceArraySIZE = 10; // Size of priceArray[]
	
	private static final long serialVersionUID = 1L;
	public static Object[] products; // Array that is loaded from file containing Product Names, used to load combo box
	public static Object[] productIDs; // Array that is loaded from read file containing Product IDs, used to load combo box
	public static Object[] prices = new String[25]; // Array that is loaded from file containing prices, used to load combo box
	public static int[] pricesIndex = new int[25]; // Used to track the order of indexes that were added to the shopping list
	public static int[] priceArray = new int[priceArraySIZE]; // Array used to store the prices in order of added to the shopping list after pressing add to list button
	public static int[] imagesIndex = new int[25];
	public static Object[] trackPrices = new String[10];
	
	public static DefaultListModel<Object> ToProductSearchList_items_1; // DefaultListModel list used to create list containing items added to the Search List
	public static DefaultListModel<Object> ToCartShopList_items_3; // DefaultListModel list used to create list containing items added to Cart List from Shopping List
	public static DefaultListModel<Object> ToQuantityList_items_4; // DefaultListModel list used to create list containing quantity of products in the Shopping List
	
	public static JComboBox<String> cbProducts; // Combo box that lists all products for sale
	
	private boolean check = false; // boolean variable that controls visibility to see the product image (rdbtnSeeImage)
	private boolean check2 = false; // boolean variable that controls visibility to see and use the the Shopping List (rdbtnUseList)
	private boolean check3 = false; // boolean variable that controls visibility to see the product description (rdbtnSeeDescription)
	private static boolean addedOne = false;
	private boolean checkRepeats  = false;
	private boolean checkCartRepeats = false;
	private boolean checkMax = false;
	
	public static int add = 0; // Incremented variable used as index for priceArray, used for btnAddList_1 action event 
	private static int sum; // Incremented variable used as index for addPrices() 
	private static int grandtotal; // Variable used in the addPrices2() method
	private static int counter = 0; // Incremented variable used as index for addPrices() 
	public static int quantAdded;
	private static int increment = 0;
	
	private JPanel panel_1; // Panel that is used to hold all elements for the Shopping List option
	private JPanel panel_2; // Panel that is used to hold the btnAddCart button and lblAdd2Cart label
	private JPanel panel_3; // Panel that is used to hold the image display area
	private JPanel panel_4; // Panel that is used to hold the product description area
	
	private JScrollPane scrollPaneShopList; // ScrollPane element for the Shopping List
	private JScrollPane scrollPaneQuantity; // ScrollPane element for the Quantity List
	
	private JButton btnAddToList; // Button to add products from combo box to the Shopping List
	private JButton btnAddToCart; // Button to add products straight to cart (radio button -> rdbtnUseList not selected)
	private JButton btnAddAllToCart; // Button used to add all contents of Shopping List to Cart
	private JButton btnRemoveAll; // Button to remove all contents of the Shopping List
	private JButton btnAddOneToList; // Button to add one selected item from Shopping List to Cart
	private JButton btnRemoveOneItem; // Button to remove one item from the Shopping List
	
	private JLabel lblRemoveAllFromList;  ///////////////////
	private JLabel lblNewLabel;	          //               //
	private JLabel lblAddAllToList;       //               //
	private JLabel lblAddOneToList;       //               //
	public static JLabel displayLabel;    //  ALL LABELS   //
	private JLabel lblNewLabel_2;         //               //
	private JLabel lblProductDescrip;     //               //
	public static JLabel lblImageDescrip; //               //
	private JLabel lblNoShipNotaxes;      ///////////////////
	
	private JList<Object> JListShopList_1; // JList element for Shopping List
	private JList<Object> JListQuantity; // JList that lists the quantity of products
	
	private JRadioButton rdbtnUseList; // Radio button used to list the Shopping List option
	private JRadioButton rdbtnSeeImage; // Radio button to see the product image
	private JRadioButton rdbtnSeeDescription; // Radio button to see the product image
	
	public static JTextPane txtpnProductDescription; // Text pane for product description
	
	private static JTextArea textAreaTotal; // Text area to display the total cost of the products added to the Shopping List
	
	productClass productObject = new productClass(); // Instantiates an object of productClass() called 'productObject'
	
	/**
	 * Calls initComponents() which is a method that contains all initialized (structural) components of the JPanel.<br>
	 * Calls createEvents() which is a method that holds all 'action' events (listeners).<br>
	 * Calls loadProductCombobox() which is a method that loads the combo box with products.<br>
	 * Sets the Shopping List (panel_1) visibility to off upon application load.<br>
	 * Sets the 'fast' shipping 'Add to Cart' (panel_2) visibility to on upon application load.<br>
	 * Turns off visibility to panel_3 (display image area) and panel_4 (product description) upon application load.<br>
	 * 
	 *@throws FileNotFoundException
	 */
	public productSearchClass() throws FileNotFoundException{ // Constructor
		
		initComponents(); // Calls initComponents() 
		createEvents(); // Calls createEvents();				       	
		loadProductCombobox();	// Calls loadCombobox()		
		panel_1.setVisible(false); // Hides all elements in panel 1 (Shopping List option)
		panel_2.setVisible(true); // Reveals all elements in panel_2 (btnAddCart button and associated label)
		panel_3.setVisible(false); // Hides the elements in panel_3 (display image area)
		panel_4.setVisible(false); // Hides the elements in panel_4 (product description area)		
	}
		
	/**
	 * Adds the prices of the products added to the Shopping List from combo box.<br>
	 * Finds the price of the selected product from prices[] and adds it to priceArray[].<br>
	 * Manages a field variable: 'sum' that is used in setPriceTotal() method.<br>
	 * 
	 * @param index   used as the index for prices[]
	 */
	public void addPrices(int index) { // Method to add up the prices
		
		Object first = prices[index]; // This creates an object variable that is initialized from the passed parameter, used to get selected index and match to price
		int second = Integer.parseInt(first.toString()); // This converts the object to integer
		priceArray[counter] = second; // This loads the priceArray[]		
		counter++; // Increments counter variable
		sum = 0; // Sets the variable initially to 0
		for (int i = 0; i < priceArray.length; i++) { // Loop to add up the total price that is in priceArray
			sum += priceArray[i]; // Adds up the priceArray[] and stores it in the variable sum
		}		
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param index
	 */
	public void addOne(int index) {
		
		Object priceFound = trackPrices[index];
		int priceOfProduct = Integer.parseInt(priceFound.toString()); 				
		Object adjust = priceArray[index]; // Sets Object variable: 'adjust' to the element found at cartPriceArray[] using parameter: 'index'
		int currentPrice = Integer.parseInt(adjust.toString()); // This converts the object to integer
		int added = currentPrice + priceOfProduct;
		priceArray[index] = added; // This loads the cartPriceArray[]
		sum = 0; // Sets the variable initially to 0
		for (int i = 0; i < priceArray.length; i++) { // Loop to add up the total price that is in priceArray
			sum += priceArray[i]; // Adds up the priceArray[] and stores it in the variable sum
		}
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param index
	 */
	public void removeOne(int index) {
		
		Object priceFound = trackPrices[index];
		int priceOfProduct = Integer.parseInt(priceFound.toString()); 				
		Object adjust = priceArray[index]; // Sets Object variable: 'adjust' to the element found at cartPriceArray[] using parameter: 'index'
		int currentPrice = Integer.parseInt(adjust.toString()); // This converts the object to integer
		int remove = currentPrice - priceOfProduct;
		priceArray[index] = remove; // This loads the cartPriceArray[]
		sum = 0; // Sets the variable initially to 0
		for (int i = 0; i < priceArray.length; i++) { // Loop to add up the total price that is in priceArray
			sum += priceArray[i]; // Adds up the priceArray[] and stores it in the variable sum
		}
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param element
	 */
	public static void rearrangeCart(int element) { // Method that rearranges the cartPriceArray when an item is removed
		
		int[] cartPriceArray2 = new int[priceArray.length -1]; // Initializes the temp cartPriceArray2 to the length of cartPriceArray.length -1]
		for (int i = 0, k = 0; i < priceArray.length; i++) { // Loop each element of cartPriceArray
			if(priceArray[i] != element){ // if statement; if cartPriceArray at index i does not equal parameter x
				cartPriceArray2[k] = priceArray[i];	// if statement is true, then load cartPriceArray2 at index k with cartPriceArray at index i
				k++; // Increment k
			}			
		}
		
		// Corrects the trackPrices and pricesIndex arrays
		for (int i = 0; i < trackPrices.length - 1; i++) {
			if (trackPrices[i] == null) {
				if (trackPrices[i+1] != null) { // Basic position swap
					
					// TrackPrices swap
					Object temp = trackPrices[i];
					trackPrices[i] = trackPrices[i+1];
					trackPrices[i+1] = temp;
					
					// pricesIndex swap
					int temp1 = pricesIndex[i];
					pricesIndex[i] = pricesIndex[i+1];
					pricesIndex[i+1] = temp1;
					
				} else {
					break; // Swap complete
				}
				
			}
		}
		
		priceArray = cartPriceArray2; // After for loop, set cartPriceArray[] to temp cartPriceArray2[]	
		int addedUp = Arrays.stream(priceArray).sum(); // Add up the total sum of cartPriceArray and set it to temp int variable: addedUp		
		
		if (addedUp == 0) {	// If addedUp is equal to 0			
			textAreaTotal.setText("$0.00"); // This resets the textAreaCartTotal box back to empty
			priceArray = new int[15]; // This resets the cartPriceArray[]
			prices = new String[20];
			addedOne = false; // Resets the boolean variable: 'addedOne' to false. this establishes that no product has been added to the Shopping List
			ToProductSearchList_items_1.removeAllElements(); // This clears all elements from DefaultListModel -> ToProductSearchList_items_1
			grandtotal = 0;
			counter = 0;
			sum = 0; // This resets the variable sum	
		} else {
			counter--; // Decrements the counter value
			sum = 0; // This resets the variable sum
			for (int i = 0; i < priceArray.length; i++) { // Loop to add up the total price that is in priceArray
				sum += priceArray[i]; // Adds up the priceArray[] and stores it in the variable sum
			}
		}

	}
	
	
	/**
	 * Loads the combo box with products using three arrays (simulated database).<br>
	 * 
	 */
	public void loadProductCombobox() { // Method to load the combo box
		
		for (int i = 0; i < products.length; i++) { // For-loop
			String line = productIDs[i].toString(); // Pulls Object element from productIDs[] and converts to String variable line
			String line2 = products[i].toString(); // Pulls Object element from products[] and converts to String variable line2
			cbProducts.addItem(line + " - " + line2 + " - " + "Price: " + "$" + prices[i] + ".00"); // Loads the JComboBox cbProducts_1 with Products IDs, Product Names, and Prices			
		}		
	}
	
	/**
	 * Sets the textAreaTotal with the total price of all products in the Shopping List<br>
	 * 
	 */
	public void setPriceTotal() { // Method that sets the total price text area (textAreaTotal_1) in Shopping List
		
		textAreaTotal.setText(""); // Clears text from textAreaTotal
		String z = Integer.toString(sum); // Converts integer to String needed to display in textAreaTotal box
		textAreaTotal.append("$" + z + ".00"); // Displays the current total price from the shopping list in the textAreaTotal box		
	}
	
	/**
	 * Used to add one DefaultListModel to another.<br>
	 * 
	 * @param <T>     used to represent the type of object stored
	 * @param from    the list that represents the change 'from'
	 * @param to      the list that represents the change'to'
	 */
	protected static <T> void addTo(ListModel<T> from, DefaultListModel<T> to) { // Method used to add one ListModel to another DefaultListModel
	    for (int index = 0; index < from.getSize(); index++) {
	        to.addElement(from.getElementAt(index));
	    }
	}
	
	/**
	 * Holds all 'action' events (listeners).<br>
	 * Primarily used for cleaner organization and management.<br>
	 *
	 */
	private void createEvents() { // Method that stores all action events
		
		MouseListener mouseListener = new MouseAdapter() { // Mouse action listener to detect when a user clicks on an item in the Shopping List
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) { // if detects only one click, run the code      	           
		            int x = 0;		
					int[] selectedIx = JListShopList_1.getSelectedIndices(); // Creates an array that stores the index of the clicked on product. Will be only one index			    
					int image = selectedIx[x]; // Assigns temp variable: 'image' to the index found at selectedIx[x]					 
					image = productObject.trackImages[image]; // Sets the temp variable: 'image' to the index found at trackImages[image]								
					productObject.loadImages(image); // Calls the loadImages() using the temp variable: 'image' (index of trackImages[image]) to display correct image in panel_3
		        }	        		        
		    }
		};
			
		btnAddToList.addActionListener(new ActionListener() { // Button action method that adds item from combo box to Shopping List
			public void actionPerformed(ActionEvent e) {
				
				if (JListShopList_1.getModel().getSize() > 7) {
					JOptionPane.showMessageDialog(null, "Maximum products in Shopping List!", "Alert", JOptionPane.ERROR_MESSAGE);
					checkMax = true;
				}			
				checkRepeats = false; // Sets the boolean to false when button is pressed. This is used to check if the selected product has already been added to Shopping List
				addedOne = true; // Sets the boolean variable:'addedOne' to true.  This is to establish at least one product has been added to the Shopping List 				
				int cbIndex = cbProducts.getSelectedIndex(); // Creates variable send to pass to addPrices() method from cbProducts_1					
				String verify = cbProducts.getItemAt(cbIndex); // Sets variable: 'verify' to the selected product using variable: 'cbIndex'
				
				for (int i = 0; i < JListShopList_1.getModel().getSize(); i++) { // for-loop using the size of the 'JListShopList_1'					
					if (ToProductSearchList_items_1.get(i) == verify) { // if-statement that checks to see if the selected item in the combo box that the user is trying to add to Shopping List is already there						
						checkRepeats = true; // if the product is already in the Shopping list, set 'checkRepeats' to true
						JOptionPane.showMessageDialog(null, "Already added to Shopping List!", "Alert", JOptionPane.ERROR_MESSAGE); // Display pop-up message						
					}										
				}
				//JOptionPane.showMessageDialog(null,verify);
				
				if (checkRepeats == false && checkMax == false) {
					
					trackPrices[getIncrement()] = prices[cbIndex];
					
					cartClass.prices[getIncrement()] = prices[cbIndex];
					setIncrement(getIncrement() + 1);
					
					productObject.setImageIndex(cbIndex); // Uses cbIndex variable as a parameter to call setImageIndex().  This tracks the order of indexes to be used for the description and image display						
					productObject.loadImages(cbIndex); // Calls the loadImages() using 'cbIndex' as a parameter			
					pricesIndex[add] = cbIndex; // Used to track price index
					add++; // Increments the add variable for prices[] for the next use	
					
					addPrices(cbIndex); // Calls addPrices method				
					setPriceTotal(); // Calls setPriceTotal method							
					ToCartShopList_items_3.addElement(cbProducts.getSelectedItem()); // This adds the selected element from cbProducts to DefaultListModel ToCartShopList_items_3			
					ToProductSearchList_items_1.addElement(cbProducts.getSelectedItem());  // This adds the selected element from cbProducts to DefaultListModel ToProductSearchList_items_1								
					ToQuantityList_items_4.addElement("1"); // Adds a 1 to the DefaultListModel: 'ToQuantityList_items_4'
					JListQuantity.setModel(ToQuantityList_items_4); // Sets the model of the Shopping List quantity box					
					JListShopList_1.setModel(ToProductSearchList_items_1); // This lists the selected DefaultListModel items in the JListShopList shopping list							
					JListShopList_1.addMouseListener(mouseListener); // A listener that detects when a product is selected in the shopping list
					//JOptionPane.showMessageDialog(null,S);

				}
				
				for (int i = 0; i < priceArray.length; i++) {						
					System.out.println("Added to List priceArray[]: " + priceArray[i] + "  prices2[] Array: " + trackPrices[i]);												
					}
				System.out.println();
				
			} 
		});
		
		btnAddToCart.addActionListener(new ActionListener() { // Button action method that adds product from combo box to Cart
			public void actionPerformed(ActionEvent e) {
				
				checkCartRepeats = false; // Always sets the boolean to false when button is pressed				
				cartClass.check = true; // Always sets the boolean to true when button is pressed
				int index = cbProducts.getSelectedIndex(); // Creates variable index to pass to addPrices() method.  Used to get the selected product from combo box			
				String verify = cbProducts.getItemAt(index); // Sets variable: 'verify' to the selected product using variable: 'index'
				
				for (int i = 0; i < cartClass.JListCartList.getModel().getSize(); i++) { // for loop that checks the cartClass.CartList_items_2 for a product (variable: 'verify') already added to the Cart					
					if (cartClass.CartList_items_2.get(i) == verify) { // if statement that looks for a product already added to the Cart						
						checkCartRepeats = true; // If there is already a product a user is attempting to add again, set the boolean to true
						JOptionPane.showMessageDialog(null, "Already Added to Cart! Add quantity from Cart tab...", "Alert", JOptionPane.ERROR_MESSAGE); // Display pop-up message						
					}								
				}			
				if (checkCartRepeats == false) { // If the boolean is false, run the code
					cartClass.addCartprice(index); // Calls the	addCartprice() from cartClass with index variable as parameter			
					cartClass.setCartPriceTotal(); // Calls the setCartPriceTotal() from cartClass
					
					cartClass.ToCartQuantityList_items_4.addElement("1"); // Adds a 1 to the DefaultListModel: 'ToCartQuantityList_items_4' in the cartClass
					cartClass.JListCartQuantity.setModel(cartClass.ToCartQuantityList_items_4); // Sets the model (cartClass quantity box)
					
					
					cartClass.prices[getIncrement()] = prices[index];
					setIncrement(getIncrement() + 1);
					
					
					cartClass.CartList_items_2.addElement(cbProducts.getSelectedItem()); // Adds the product stored in combo box (cbProducts_1) to DefaultListModel -> CartList_items_2
					cartClass.JListCartList.setModel(cartClass.CartList_items_2); // Places the items in the cart (cartClass.JListCartList) from CartList_items_2
					JOptionPane.showMessageDialog(null, "Successfully added to Cart!", "Product Added", JOptionPane.INFORMATION_MESSAGE); // Displays a pop-up message
				}
			}
		});
		
		btnAddOneToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (! JListShopList_1.isSelectionEmpty()) { // If an item is selected in the Shopping List, then run the following code					
						
					int selectedIx = JListShopList_1.getSelectedIndex(); // Creates a temp int that stores the selected index in the Shopping List.			    
										
					addOne(selectedIx); 
										
					setPriceTotal(); // Calls setPriceTotal method	
									
					Object number = ToQuantityList_items_4.getElementAt(selectedIx); // Sets Object variable: 'number' to the price stored at ToQuantityList_items_4
					int convertedNumber = Integer.parseInt(number.toString()); // Converts the Object: 'number' to int: 'convertedNumber2'
					int addedUp = convertedNumber + 1; // Adds the price of convertedNumber2 by 1		
					ToQuantityList_items_4.setElementAt(addedUp, selectedIx); // Sets the quantity displayed in quantity box for selected item at the right spot
					//JOptionPane.showMessageDialog(null, "Added one more -> " + products[image]); //pop-up message displaying the product which the user added a quantity
					
					for (int i = 0; i < priceArray.length; i++) {						
						System.out.println("Added one to priceArray[]: " + priceArray[i] + "  prices2[] Array: " + trackPrices[i]);												
					}
					
				} else { // If the button is pushed and nothing is selected in the Shopping List, display the pop-up message
					JOptionPane.showMessageDialog(null, "Please select an item to add!", "Alert", JOptionPane.ERROR_MESSAGE); // Displays a pop-up message		
				}
				
			}
		});
		
		btnAddAllToCart.addActionListener(new ActionListener() { // Button action method that adds all selected Products from Shopping List to Cart
			public void actionPerformed(ActionEvent e) {
				
				cartClass.check = false;
				
				int begin = 0; // Sets the beginning point of the selection value
				int end = JListShopList_1.getModel().getSize() - 1; // Sets the end point of the selection value
				if (end >= 0) { // Loop to select all items in the shopping using begin and end variables
					JListShopList_1.setSelectionInterval(begin, end); // Selects all items in the Shopping List (JListShopList_1) at once					
				}
				
				int[] selectedIx = JListShopList_1.getSelectedIndices(); // Creates an array that stores the selected items in the Shopping List				
				
				if (! JListShopList_1.isSelectionEmpty()){ // Checks to see if there is at least one item in the Shopping List to add to Cart
					for (int i = 0; i < selectedIx.length; i++) { // Loop 
					
						int temp = pricesIndex[i]; // Sets variable temp for each element in prices[]
						cartClass.addCartprice(temp); // Calls addCartprice() from cartClass passing u variable as a parameter
						cartClass.setCartPriceTotal(); // Calls setCartPriceTotal() from cartClass							
					}
					
					quantAdded = cartClass.sum + grandtotal; // Sets temp variable: 'quantAdded' to the total of 'cartClass.sum' and 'grandtotal'
					
					cartClass.textAreaCartTotal.setText(""); // Resets the textAreaCartTotal in case there has been a quantity added
					cartClass.textAreaCartTotal.append("$" + quantAdded + ".00"); // Sets the textAreaCartTotal to the variable: 'quantAdded' to account for any added items from the Shopping List				
					addTo(ToQuantityList_items_4 , cartClass.ToCartQuantityList_items_4); // Calls the addTo() which takes a list 'from' and adds it 'to' another list. Basically, sets the quanity in the cart from Shopping List								
					cartClass.JListCartList.setModel(ToCartShopList_items_3); // Sets the Cart List (JListCartList)  in cartClass with all items from DefaultListModel ToCartShopList_items_3
					
					ToQuantityList_items_4.removeAllElements(); // Clears out the quantity box from the Shopping List
					ToProductSearchList_items_1.clear(); // Clears all items from DefaultListModel -> ToProductSearchList_items_1
					textAreaTotal.setText(""); // This resets the textAreaTotal box back to empty
					priceArray = new int[priceArraySIZE]; // Resets the priceArray[]
					txtpnProductDescription.setText(null); // Resets the description area
					displayLabel.setIcon(null); // Resets the display image area
					JOptionPane.showMessageDialog(null, "Moved all items in shopping list to Cart!", "Products Moved", JOptionPane.INFORMATION_MESSAGE);
					add = 0; // Resets the add variable for prices[] for the next use
					
				} else { // If there is nothing in the Shopping List, then display pop-up message
					JOptionPane.showMessageDialog(null, "Please add products to the Shopping List first!", "Alert", JOptionPane.ERROR_MESSAGE); // Displays a pop-up message					
				}				
			}
		});
		
		btnRemoveOneItem.addActionListener(new ActionListener() { // Button action method that removes selected Product from Shopping List
			public void actionPerformed(ActionEvent e) {
				
				checkMax = false; // Boolean variable used to check if max products are in the Shopping List.
				
				if (! JListShopList_1.isSelectionEmpty()){ // If the Shopping List is not empty, run the code
											
					int selectedIx = JListShopList_1.getSelectedIndex(); // Creates a temp int that stores the selected index in the Shopping List.
					
					Object quantity = ToQuantityList_items_4.get(selectedIx);
					int quantity2 = Integer.parseInt(quantity.toString());
					
					if (quantity2 == 1) {
						
						trackPrices[selectedIx] = null;
						setIncrement(getIncrement() - 1);
					
						int image = productObject.trackImages[selectedIx]; // Sets int variable: 'w' to the index found in 'trackImages[]'
						productObject.removeImageIndex(image); // Calls the removeImageIndex() from productClass using parameter 'w'
						txtpnProductDescription.setText(null); // Resets the description area
						displayLabel.setIcon(null); // Resets the display image area										
						
						ToCartShopList_items_3.removeElementAt(selectedIx);
						ToProductSearchList_items_1.removeElementAt(selectedIx); // Removes the Product from Shopping List at index specified by variable: 'index'
						ToQuantityList_items_4.removeElementAt(selectedIx); // Removes the quantity amount from quantity box at index specified by variable: 'index'
						
						productObject.track--; // Decrements the variable: 'track' that is used in 'productObject.trackImages[]' to account for the product removed
						
						add--; // Decrements the add variable for prices[] for the next use
						
						int remove = priceArray[selectedIx];
						rearrangeCart(remove);
														
					
					} else { // If the quantity of the product to be removed is greater than 1, run the code
						
						Object evaluatedValue = ToQuantityList_items_4.getElementAt(selectedIx); // Assign Object: 'evaluatedValue' to the element using index variable: 'image'				
						int valueInt2 = Integer.parseInt(evaluatedValue.toString()); // Convert Object to int
						valueInt2 = valueInt2 - 1; // Subtract 'valueInt2' by 1 
						ToQuantityList_items_4.set( selectedIx, valueInt2); // Adjust the quantity using index 'image' by inserting 'valueInt2'					
						
						removeOne(selectedIx); 
						
						
					}
					
					setPriceTotal(); // Adjust the Shopping List price total
					
					for (int i = 0; i < priceArray.length - 1; i++) {						
						System.out.println("Removed one from priceArray[]: " + priceArray[i] + "  prices2[] Array: " + trackPrices[i]);												
					}
					System.out.println();
					
				} else { // If the Shopping List is empty or if an item in the Shopping List is not selected, display the pop-up
					JOptionPane.showMessageDialog(null, "Please select an item to remove!", "Alert", JOptionPane.ERROR_MESSAGE); // Displays a pop-up message				
				}
			}
		});
		
		btnRemoveAll.addActionListener(new ActionListener() { // Button action method that removes all selected Products in Shopping List
			public void actionPerformed(ActionEvent e) {
				
				if (addedOne == true){ // Checks to see if at least one product is in the Shopping List before trying to clear the Shopping List
					
					addedOne = false; // Resets the boolean variable: 'addedOne' to false. this establishes that no product has been added to the Shopping List
					txtpnProductDescription.setText(null); // Clears out the description text area
					displayLabel.setIcon(null); // Clears out the product image from panel_03
					ToQuantityList_items_4.removeAllElements(); // Clears out the DefaultListModel -> ToQuantityList_items_4 (numbers displayed in the quantity box)
					ToProductSearchList_items_1.removeAllElements(); // This clears all elements from DefaultListModel -> ToProductSearchList_items_1
					textAreaTotal.setText("$0.00"); // This resets the textAreaTotal box back to empty
					priceArray = new int[priceArraySIZE]; // This resets the priceArray[]
					productObject.trackImages = new Integer[20]; // This resets the trackImages[]
					productObject.track = 0; // Resets track variable to 0
					add = 0; // Resets the add variable for prices[] for the next use
					JOptionPane.showMessageDialog(null, "Removed all products from Shopping List", "Products Removed", JOptionPane.INFORMATION_MESSAGE); // Displays a pop-up message
					
				} else { // if there is nothing added to the Shopping List yet...
					JOptionPane.showMessageDialog(null, "Nothing in Shopping List to remove!", "Alert", JOptionPane.ERROR_MESSAGE); // Displays a pop-up message					
				}
			}
		});
	
		rdbtnUseList.addActionListener(new ActionListener() { // Radio button action method that toggles on or off the Shopping List 
			public void actionPerformed(ActionEvent e) {
				
				textAreaTotal.setText("$0.00"); // Sets the initial value of Shopping List (textAreaTotal_1) upon first use
				
				if (check2 == false) { // If check2 is false (off), run the code				
					panel_1.setVisible(true); // Turn on panel_1 (Shopping List option)
					check2 = true; // Set the check2 variable to true (on)				
					panel_2.setVisible(false); // Turn off panel_2 (btnAddCart button and associated label)				
				} else {	// If check2 is true (on)				
					panel_1.setVisible(false); // Turn off panel_1 (Shopping List option)
					check2 = false;	// Set the check2 variable to false (off)				
					panel_2.setVisible(true); // Turn on panel_2 (btnAddCart button and associated label)
				}								
			}
		});
		
		rdbtnSeeDescription.addActionListener(new ActionListener() { // Radio button action method that toggles on or off the Product Description area
			public void actionPerformed(ActionEvent e) {
				
				if (check3 == false) {	// If check3 is false (off), run the code				
					panel_4.setVisible(true); // Turn on panel_4 (product description area)
					check3 = true; // Set the check3 variable to true (on)									
				} else { // If check3 is true (on)
					panel_4.setVisible(false); // Turn off panel_4 (product description area)
					check3 = false;	// Set the check3 variable to false (off)									
				}			
			}
		});
	
		rdbtnSeeImage.addActionListener(new ActionListener() { // Radio button action method that toggles on or off the 'See Image' area
			public void actionPerformed(ActionEvent e) {
				
				if (check == false) { // If check is false (off), run the code				
					panel_3.setVisible(true); // Turn on panel_3 (display image area)
					check = true; // Set the check variable to true (on)								
				} else { // if check is true (on)
					panel_3.setVisible(false); // Turn off panel_3 (display image area)
					check = false; // Set the check variable to false (off)										
				}				
			}
		});		
	}
	
	/**
	 * Contains all initialized (structural) components of the JPanel.<br>
	 * 
	 * @throws FileNotFoundException
	 */
	private void initComponents() throws FileNotFoundException { // Method that stores components
		
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
		
		ToProductSearchList_items_1= new DefaultListModel<Object>();
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
		textAreaTotal.setEditable(false);
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
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnSeeDescription)
										.addComponent(rdbtnSeeImage))
									.addGap(13)))
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(2)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(btnRemoveOneItem)
												.addComponent(btnRemoveAll, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
											.addGap(45)
											.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(btnAddAllToCart)
												.addComponent(btnAddOneToList))
											.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_1.createSequentialGroup()
													.addGap(12)
													.addComponent(lblNewLabel)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(textAreaTotal, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
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
					.addGap(19))
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
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNoShipNotaxes, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
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
		txtpnProductDescription.setEditable(false);
		txtpnProductDescription.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		lblProductDescrip = new JLabel("Product Description");
		lblProductDescrip.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(77)
							.addComponent(lblProductDescrip, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnProductDescription, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addComponent(lblProductDescrip, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnProductDescription, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(37, Short.MAX_VALUE))
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
		
		JListShopList_1 = new JList<Object>();
		JListShopList_1.setVisibleRowCount(10);
		JListShopList_1.setToolTipText("");
		scrollPaneShopList.setViewportView(JListShopList_1);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
	}

	public static int getIncrement() {
		return increment;
	}

	public static void setIncrement(int increment) {
		productSearchClass.increment = increment;
	}
}
