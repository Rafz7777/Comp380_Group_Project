package windowBuilder.views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
* @author Matthew Bellman
* @version 2022.11.07
*/
public class helpClass extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField addressField;

	public helpClass() {
		
		JLabel lblNewLabel = new JLabel("Phone Number: X-XXX-XXX-XXXX");
		
		JLabel lblNewLabel_1 = new JLabel("Email: support@oldvintage.shop");
		
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
		
		JLabel lblNewLabel_3 = new JLabel("Email Address: *");
		
		JTextArea emailArea = new JTextArea();
		emailArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		emailArea.setLineWrap(true);
		emailArea.setWrapStyleWord(true);
		
		JLabel lblNewLabel_4 = new JLabel("* Required Field");
		
		JLabel lblNewLabel_5 = new JLabel("Email Meassage: *");
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_5)
							.addContainerGap(467, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addContainerGap(271, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtrIfYouHave)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_4)
									.addPreferredGap(ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
									.addComponent(btnSend))
								.addComponent(emailArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_3)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(addressField, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)))
							.addGap(24))))
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
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(addressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addGap(7)
					.addComponent(lblNewLabel_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailArea, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(btnSend))
					.addContainerGap())
		);
		setLayout(groupLayout);
	
	}
}
