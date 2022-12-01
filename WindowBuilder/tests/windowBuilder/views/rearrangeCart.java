package windowBuilder.views;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class rearrangeCart {
	

	@Before
	public void setUp() throws Exception {
		
		cartClass.cartPriceArray[0] = 25;
		cartClass.cartPriceArray[1] = 5;
		cartClass.cartPriceArray[2] = 55;
		cartClass.cartPriceArray[3] = 35;
		cartClass.cartPriceArray[4] = 10;
		
	}


	@Test
	public void testRearrangeCart() {
		
		cartClass.rearrangeCart(5);
		int test = 125;
		int addedUp = Arrays.stream(cartClass.cartPriceArray).sum();	
		assertEquals(test, addedUp);
		
	}
		

}
