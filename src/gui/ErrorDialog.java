package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ErrorDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField errField;
	private JPanel buttonPane;

	/**
	 * Create the dialog.
	 */
	public ErrorDialog(String errMsg, String stackTrace, boolean exitOnClose) {
		setResizable(false);
		setTitle("ERROR");
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(400, 150, 812, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			errField = new JTextField();
			errField.setEditable(false);
			errField.setColumns(10);
			errField.setText(errMsg);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.err.println(errMsg);
						System.err.println(stackTrace);
						if(exitOnClose) {
							System.exit(1);
						}else {
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(contentPanel);
		
		JTextArea errTextArea = new JTextArea();
		errTextArea.setEditable(false);
		errTextArea.setText(stackTrace);

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(errTextArea, GroupLayout.PREFERRED_SIZE, 781, GroupLayout.PREFERRED_SIZE)
						.addComponent(errField, 651, 651, 651))
					.addGap(38))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(errField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(errTextArea, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(39))
		);
		contentPanel.setLayout(gl_contentPanel);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}
}
