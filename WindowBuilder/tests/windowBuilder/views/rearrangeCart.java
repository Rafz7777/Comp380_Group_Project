package windowBuilder.views;

import static org.junit.Assert.*;
import java.util.Arrays;
import javax.swing.JTextArea;
import org.junit.Before;
import org.junit.Test;

public class rearrangeCart {
	
	//public static int[] cartPriceArray = new int[15];
	

	@Before
	public void setUp() throws Exception {
		
		//initial loading of cartPriceArray
		
		cartClass.cartPriceArray[0] = 25;
		cartClass.cartPriceArray[1] = 5;
		cartClass.cartPriceArray[2] = 55;
		cartClass.cartPriceArray[3] = 35;
		cartClass.cartPriceArray[4] = 10;
		
		//initial loading of textAreaCartTotal, counter, and sum
		
		cartClass.textAreaCartTotal = new JTextArea();
		cartClass.textAreaCartTotal.setText("$130.00");
		cartClass.counter = 4; 
		cartClass.sum = 130;
	}

	
	@Test 
	public void testRearrangeCartPath1() {
		
		//Path will not traverse through nodes 4 (does not exist in array) and will not 
		//traverse through node 7 (array does not add up to 0)
		
		cartClass.rearrangeCart(2);
		
		int test = 130;
		int addedUp = Arrays.stream(cartClass.cartPriceArray).sum();	
		assertEquals(test, addedUp);
		
		assertEquals("$130.00", cartClass.textAreaCartTotal.getText());
		assertEquals(14, cartClass.cartPriceArray.length);
		assertEquals(20, cartClass.trackPrices.length);
		assertEquals(4, cartClass.counter);
		assertEquals(130, cartClass.sum);
		
			
	}
	
	@Test
	public void testRearrangeCartPath2() {
		
		//Path that will remove the price from array but will not be 0, skipping node 7
		
		cartClass.rearrangeCart(5);
		
		int test = 125;
		int addedUp = Arrays.stream(cartClass.cartPriceArray).sum();	
		assertEquals(test, addedUp);
		
		assertEquals("$130.00", cartClass.textAreaCartTotal.getText());
		assertEquals(13, cartClass.cartPriceArray.length);
		assertEquals(20, cartClass.trackPrices.length);
		assertEquals(4, cartClass.counter);
		assertEquals(130, cartClass.sum);
		
	}

	
	@Test 
	public void testRearrangeCartPath3() {
		
		//Path will not traverse through nodes 4 (does not exist in array on first call) but will
		//traverse through the rest of the nodes (because on subsequent calls, it does add up to 0)
		
		cartClass.rearrangeCart(2);
		cartClass.rearrangeCart(25);
		cartClass.rearrangeCart(5);
		cartClass.rearrangeCart(55);
		cartClass.rearrangeCart(35);
		cartClass.rearrangeCart(10);
		
		int test = 0;
		int addedUp = Arrays.stream(cartClass.cartPriceArray).sum();	
		assertEquals(test, addedUp);
		
		assertEquals("$0.00", cartClass.textAreaCartTotal.getText());
		assertEquals(15, cartClass.cartPriceArray.length);
		assertEquals(20, cartClass.trackPrices.length);
		assertEquals(0, cartClass.counter);
		assertEquals(0, cartClass.sum);
		
	}
	
	@Test
	public void testRearrangeCartPath4() {
		
		//Path that will remove the price from array and will add up to 0, traversing through all nodes
		
		cartClass.rearrangeCart(25);
		cartClass.rearrangeCart(5);
		cartClass.rearrangeCart(55);
		cartClass.rearrangeCart(35);
		cartClass.rearrangeCart(10);
		
		int test = 0;
		int addedUp = Arrays.stream(cartClass.cartPriceArray).sum();	
		assertEquals(test, addedUp);
		assertEquals("$0.00", cartClass.textAreaCartTotal.getText());
		assertEquals(15, cartClass.cartPriceArray.length);
		assertEquals(20, cartClass.trackPrices.length);
		assertEquals(0, cartClass.counter);
		assertEquals(0, cartClass.sum);
		
	}
		

}
