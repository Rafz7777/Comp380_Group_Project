package windowBuilder.views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/**
 * <p>This class allows the user to 'send an email' to the store. It has two input fields the<br>
 * user can input text into and a send button to send the email. One of the input fields is<br>
 * for the email address and the other one is for the email message itself.</p>
 * 
 * <p>The send button checks to make sure that (a) both the email address and the email message<br>
 * aren't blank and (b) the email address is valid. After those tests, the email gets 'sent'<br>
 * and a message appears to let the user know that it was sent.</p>
 * 
 * <p>The 'send' is actually just a file output. The file's location is outside the directory.</p>
 * 
 * @author Matthew Bellman
 * @version 2022.11.17
 */
public class helpClass extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 *  User input to store the email address to send to.
	 */
	private JTextField addressField;
	
	/**
	 * User input to store the email contents itself.
	 */
	private JTextArea emailArea;

	/**
	 *  <p>The help class itself. Includes the GUI, and the code for the send button.<br>
	 *  Does not have any public methods inside though, just private helper ones.</p>
	 */
	public helpClass() {
		
		JLabel lblNewLabel = new JLabel("Phone Number: 1-271-581-2221");
		
		JLabel lblNewLabel_1 = new JLabel("Email: support@vintage.curios");
		
		JTextArea txtrIfYouHave = new JTextArea();
		txtrIfYouHave.setWrapStyleWord(true);
		txtrIfYouHave.setLineWrap(true);
		txtrIfYouHave.setBackground(SystemColor.control);
		txtrIfYouHave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrIfYouHave.setText("If you have any questions about our store or our products, you can contact us using one of the methods below.");
		txtrIfYouHave.setEditable(false);
		
		JButton btnSend = new JButton("Send");
		
		JLabel lblNewLabel_2 = new JLabel("Or you can contact us directly by filling out the form below:");
		
		addressField = new JTextField();
		
		JLabel lblNewLabel_3 = new JLabel("* Email Address:");
		
		JLabel lblNewLabel_4 = new JLabel("* Required Field");
		
		JLabel lblNewLabel_5 = new JLabel("* Email Meassage:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		emailArea = new JTextArea();
		emailArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		emailArea.setWrapStyleWord(true);
		emailArea.setLineWrap(true);
		scrollPane.setViewportView(emailArea);
		
		/// CODE FOR SEND BUTTON ///
		btnSend.addActionListener(new ActionListener() {
			
			// Send Button Clicked (4.8/5)
			public void actionPerformed(ActionEvent e) {
				
				// Checks to see if the email address is valid.
				if (!addressCheck())
					return;
				
				// Checks to see if the email message is not blank.
				if (emailArea.getText().isEmpty()) {
					JOptionPane.showInternalMessageDialog(null, "Please enter an email message.", "Error", 0);
					return;
				}
				
				// 'Send'
				try {
					writeEmail();
				} catch (IOException e1) {
					// Pop up to tell the user that something went wrong with the send.
					JOptionPane.showInternalMessageDialog(null, "Something went wrong with the conection...\n\nPlease try sending it again.", "Error", 0);
					e1.printStackTrace();
					return;
				}
				
				// Pop up to tell the user that the email is sent.
				JOptionPane.showInternalMessageDialog(null, "Your meassage has been sent.", "Meassage sent", -1);
				
				// Clear fields and open Home Pane
				emailArea.setText("");
				addressField.setText("");
				
				//eCommerceMain.setPane(0); // TODO: Request Ralph to allow me to add this method to eCommerceMain]
				
			}
					
			// Generates the email
			private void writeEmail() throws IOException {

				try (FileWriter emailWriter = new FileWriter((System.getProperty("user.dir")) + "helpEmail.txt")) {
					emailWriter.write("To: " + addressField.getText() + "\n\n\n"); // For address field
					emailWriter.write("Hello customer,\n");
					emailWriter.write("Thank you for contacting us, this auto generated email is here to let you know that we got your message.\n\n");
					emailWriter.write("One of our employees will reply to you shortly. If there is any additional information you need to send to us, please reply to this email.\n\n");
					emailWriter.write("Below is what you've sent:\n\n");
					emailWriter.write(emailArea.getText());
				}
				
			}

			// Checks to see if the email address is valid.
			private boolean addressCheck() {
				
				String email = addressField.getText();
				boolean check = true;
				
				/* Valid Email address:
				 * 1. Exactly one '@'
				 * 2. At least one '.' after '@'
				 * 3. Not empty after all '.'s
				 */
				if (email.isEmpty()) { // Pop up to tell the user that the email address field is empty
					JOptionPane.showInternalMessageDialog(null, "Please enter an email address.", "Error", 0);
					return false;
				}
				
				if (email.contains("@")) {
					email = email.substring(email.indexOf("@") + 1);
					
					if (email.contains("@") || !email.contains("."))
						check = false; // Invalid
					
					do {
						email = email.substring(email.indexOf(".") + 1);
						
						if (email.isEmpty()) {
							check = false; // Invalid
						}
					} while ((email.indexOf(".") != -1));
					
					
				} else {
					check = false; // Invalid
				}
				
				// Pop up to tell the user that the email address is not valid
				if (!check) {
					JOptionPane.showInternalMessageDialog(null, "Invalid email address.", "Error", 0);
				}
				
				return check;
			}
			
		});
				

		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_4, Alignment.LEADING)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSend)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)))
						.addComponent(txtrIfYouHave, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(lblNewLabel_5)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addressField, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
						.addComponent(lblNewLabel_2))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrIfYouHave, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(addressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(btnSend))
					.addContainerGap())
		);
		
		setLayout(groupLayout);
	
	}
}
