package client;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientGUI {

	private JFrame frame;
	private JTextField hostNameTextField;
	private JTextField portTextField;
	private JComboBox<String> operationComboBox;
	private JTextField wordTextField;
	private JTextArea meaningsTextArea;
	private JTextArea feedbackTextArea;
	private JButton btnSubmit;
	
	private ButtonListener btnListener;
	

	/**
	 * Create the application.
	 */
	public ClientGUI(ButtonListener btnListener) {
		this.btnListener = btnListener;
		initialize();
		btnListener.initialize(hostNameTextField, portTextField, operationComboBox, wordTextField, meaningsTextArea, feedbackTextArea);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{113, 217, 112, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 27, 26, 44, 29, 49, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		// Host name
		JLabel lblHostName = new JLabel("Host name");
		GridBagConstraints gbc_lblHostName = new GridBagConstraints();
		gbc_lblHostName.anchor = GridBagConstraints.WEST;
		gbc_lblHostName.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostName.gridx = 0;
		gbc_lblHostName.gridy = 0;
		panel.add(lblHostName, gbc_lblHostName);
		
		hostNameTextField = new JTextField();
		hostNameTextField.setText("localhost");
		GridBagConstraints gbc_hostNameTextField = new GridBagConstraints();
		gbc_hostNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_hostNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_hostNameTextField.gridx = 1;
		gbc_hostNameTextField.gridy = 0;
		panel.add(hostNameTextField, gbc_hostNameTextField);
		hostNameTextField.setColumns(10);
		
		// Port number
		JLabel lblPortNumber = new JLabel("Port number");
		GridBagConstraints gbc_lblPortNumber = new GridBagConstraints();
		gbc_lblPortNumber.anchor = GridBagConstraints.EAST;
		gbc_lblPortNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortNumber.gridx = 2;
		gbc_lblPortNumber.gridy = 0;
		panel.add(lblPortNumber, gbc_lblPortNumber);
		
		portTextField = new JTextField();
		portTextField.setText("8162");
		GridBagConstraints gbc_portTextField = new GridBagConstraints();
		gbc_portTextField.insets = new Insets(0, 0, 5, 0);
		gbc_portTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_portTextField.gridx = 3;
		gbc_portTextField.gridy = 0;
		panel.add(portTextField, gbc_portTextField);
		portTextField.setColumns(10);
		
		// Operation
		JLabel lblOperation = new JLabel("Operation");
		GridBagConstraints gbc_lblOperation = new GridBagConstraints();
		gbc_lblOperation.anchor = GridBagConstraints.WEST;
		gbc_lblOperation.insets = new Insets(0, 0, 5, 5);
		gbc_lblOperation.gridx = 0;
		gbc_lblOperation.gridy = 1;
		panel.add(lblOperation, gbc_lblOperation);
		
		operationComboBox = new JComboBox<String>();
		operationComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Search", "Add", "Remove"}));
		GridBagConstraints gbc_operationComboBox = new GridBagConstraints();
		gbc_operationComboBox.gridwidth = 3;
		gbc_operationComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_operationComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_operationComboBox.gridx = 1;
		gbc_operationComboBox.gridy = 1;
		panel.add(operationComboBox, gbc_operationComboBox);
		
		// Word text field
		JLabel lblWord = new JLabel("word");
		GridBagConstraints gbc_lblWord = new GridBagConstraints();
		gbc_lblWord.anchor = GridBagConstraints.WEST;
		gbc_lblWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblWord.gridx = 0;
		gbc_lblWord.gridy = 2;
		panel.add(lblWord, gbc_lblWord);
		
		wordTextField = new JTextField();
		GridBagConstraints gbc_wordTextField = new GridBagConstraints();
		gbc_wordTextField.gridwidth = 3;
		gbc_wordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_wordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_wordTextField.gridx = 1;
		gbc_wordTextField.gridy = 2;
		panel.add(wordTextField, gbc_wordTextField);
		wordTextField.setColumns(10);
		
		// Meanings text field
		JLabel lblMeanings = new JLabel("Meanings");
		GridBagConstraints gbc_lblMeanings = new GridBagConstraints();
		gbc_lblMeanings.anchor = GridBagConstraints.WEST;
		gbc_lblMeanings.insets = new Insets(0, 0, 5, 5);
		gbc_lblMeanings.gridx = 0;
		gbc_lblMeanings.gridy = 3;
		panel.add(lblMeanings, gbc_lblMeanings);
		
		JScrollPane meaningsScrollPane = new JScrollPane();
		GridBagConstraints gbc_meaningsScrollPane = new GridBagConstraints();
		gbc_meaningsScrollPane.gridwidth = 3;
		gbc_meaningsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_meaningsScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_meaningsScrollPane.gridx = 1;
		gbc_meaningsScrollPane.gridy = 3;
		panel.add(meaningsScrollPane, gbc_meaningsScrollPane);
		
		meaningsTextArea = new JTextArea(8,10);
		meaningsScrollPane.setViewportView(meaningsTextArea);
		
		// Submit button (Send message to server)
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridwidth = 4;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 4;
		panel.add(btnSubmit, gbc_btnSubmit);
		
		// Feedback from server
		JScrollPane feedbackScrollPane = new JScrollPane();
		feedbackScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_feedbackScrollPane = new GridBagConstraints();
		gbc_feedbackScrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_feedbackScrollPane.fill = GridBagConstraints.BOTH;
		gbc_feedbackScrollPane.gridwidth = 4;
		gbc_feedbackScrollPane.gridx = 0;
		gbc_feedbackScrollPane.gridy = 5;
		panel.add(feedbackScrollPane, gbc_feedbackScrollPane);
		
		feedbackTextArea = new JTextArea();
		feedbackTextArea.setLineWrap(true);
		feedbackTextArea.setEditable(false);
		feedbackScrollPane.setViewportView(feedbackTextArea);
		
		// button listener
		btnSubmit.addActionListener(btnListener);
	}
	
	
	public void displayFeedback(String feedback)
	{
		SimpleDateFormat format = new SimpleDateFormat("[HH:mm:ss]");
		String timeStamp = format.format(new Date());
		feedbackTextArea.append(timeStamp + " " + feedback + "\n");
	}
	
	public void disableSubmit()
	{
		btnSubmit.setEnabled(false);
	}
	
	public void enableSubmit()
	{
		btnSubmit.setEnabled(true);
	}

}


