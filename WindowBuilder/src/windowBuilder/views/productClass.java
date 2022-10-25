package windowBuilder.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class productClass {
	
	public static Object[] descriptionsArray = new String[10];
	public Integer[] trackImages = new Integer[10];
	
	public int track = 0;
	
	public productClass() throws FileNotFoundException {
		
		loadProductsFromTxtFile();
		loadProductDescriptions();
		
	}
	
	public void loadProductsFromTxtFile() throws FileNotFoundException { //method that loads the combobox
		
		java.net.URL url = getClass().getResource("/productDatabase/productNames.txt");
		File file = new File(url.getPath());
		//String namepath = "/Users/Zeina/Desktop/productNames.txt"; //path for the Product Names file stored on my computer locally
		//File file = new File(namepath);
			
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			productSearchClass.products = br.lines().toArray(); //loads products[] with Product Names 
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		java.net.URL url2 = getClass().getResource("/productDatabase/productIDs.txt");
		File file2 = new File(url2.getPath());
		//String IDpath = "/Users/Zeina/Desktop/productIDs.txt"; //path for the Product IDs file stored on my computer locally
		//File file2 = new File(IDpath);
			
		try (BufferedReader br2 = new BufferedReader(new FileReader(file2))) {
			productSearchClass.productIDs = br2.lines().toArray(); //loads Product IDs[] with Product IDs
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		java.net.URL url3 = getClass().getResource("/productDatabase/prices.txt");
		File file3 = new File(url3.getPath());
		//String pricespath = "/Users/Zeina/Desktop/prices.txt"; //path for Product Prices file stored on my computer locally
		//File file3 = new File(pricespath);
			
		try (BufferedReader br3 = new BufferedReader(new FileReader(file3))) {
			productSearchClass.prices = br3.lines().toArray(); //loads prices[] with Product Prices
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadProductDescriptions() throws FileNotFoundException { //method used to load the Descriptions of Products into array
		
		int i; //declares int i
		java.net.URL url = getClass().getResource("/productDescriptions/descriptions.txt"); //sets a java.net.URL variable: 'url' to the .txt file found at the specified path
		File file = new File(url.getPath()); //creats a File Object from the 'url' variable
		
		for (i = 0; i < descriptionsArray.length; i++) { //for loop using the length of descriptionsArray[]
			try (BufferedReader br = new BufferedReader(new FileReader(file))) { //BufferedReader to begin reading lines of descriptions.txt
				descriptionsArray = br.lines().toArray(); //loads the descriptionsArray[] with each line from the descriptions.txt file
			} catch (FileNotFoundException e) {
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public String setDescriptions(int x) { //method used to return a description String using parameter 'x' descriptionsArray.  Converts to String too
		
		String descrip = descriptionsArray[x].toString();
		return descrip;
		
	}
	
	public void setImageIndex(Integer number) {
		
		trackImages[track] = number;
		track++;
		
	}
	
	public void removeImageIndex(Integer number) { //'number' = the index of the image that is to be removed
		
		ArrayList<Integer> arr_new = new ArrayList<>();
		
		for (int i=0; i<trackImages.length; i++) {	
			
			if (trackImages[i]!=number) {				
				arr_new.add(trackImages[i]);				
			}
			
		}
		
		trackImages = arr_new.toArray(new Integer[0]);
	}
	
	public void loadImages(int number) throws IOException { //method used to load panel_3 (Image Display Panel) and panel_4 (Product Description Panel)
		
		String display; //string variable that is used in switch/case below
		
		switch (number) { //switch case that chooses which image and description are displayed
		  case 0:	
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/tv.jpg"))); //dispays the image in panel_3			  		  
			  display = setDescriptions(number); //dispays the description in panel_4 based on the value of the parameter: 'number'
			  productSearchClass.lblImageDescrip.setText("    Vintage TV"); //sets the small label used in the image display area (panel_3)
			  productSearchClass.txtpnProductDescription.setText(display);	//sets the desciption String to be displayed in panel_4	  		  
			  break;
		  case 1:		  
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/camera.jpg"))); 
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Vintage Camera");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 2:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/atari.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Atari Games");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 3:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/boombox.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("1980s Boombox");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 4:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/phone.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Rotary Phone");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 5:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/cassette.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Cassette Tape");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 6:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/camera2.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Film Camera");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 7:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/top.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Vintage Top");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 8:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/sign.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Rustic Sign");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		  case 9:
			  productSearchClass.displayLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/nintendo.jpg")));
			  display = setDescriptions(number);
			  productSearchClass.lblImageDescrip.setText("Nintendo Console");
			  productSearchClass.txtpnProductDescription.setText(display);
			  break;
		
		}

	}
	
	
}
