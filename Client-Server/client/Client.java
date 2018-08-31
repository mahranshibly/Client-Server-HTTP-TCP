package client;


/**
 *
 * @author MahranSh
 */

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class Client{


	public Socket socket =null;
	public BufferedReader in = null;
	public PrintWriter out = null;

	Boolean isStopped=true;

	private JFrame frame;
	private JTextField textField0,  textField;
	private JTextArea massagesBox ;
	private JButton btnConnect, btnSend, btnShowOnline;




	public Client() {

		initialize();
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				out.println(textField.getText());
				textField.setText("");
			}
		});
		
		btnConnect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {  

				if(IsStopped()) {
					try { 
						socket = new Socket("localhost", 45000);
						out = new PrintWriter( socket.getOutputStream(), true);  //open a PrintWriter on the socket
						in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
						isStopped=false; 
						btnConnect.setText("Disconnect");
						textField.setEditable(true);
						btnSend.setEnabled( true);	
					} catch (IOException e) {
						 
						e.printStackTrace();
					}  //open a BufferedReader on the socket


				} else {
					try {
						out.close();
						in.close();
						socket.close();
						isStopped=true;
						btnConnect.setText("Connect");
						out.println("endWhile\n");
						out.println(   " leaved.");
					} 
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}); 

	}
	/*
	@Override
	private void run(){

		btnConnect.setEnabled(false);
		Socket socket = new Socket(host, 45000);

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		// Process all messages from server, according to the protocol.
		while (true) {
			String line = in.readLine();
			if (line.startsWith("SUBMITNAME")) {
				out.println(userName);
			} else if (line.startsWith("NAMEACCEPTED")) {
				textField.setEditable(true);
				btnConnect.setEnabled(true);
				btnSend.setEnabled(true);
				btnShowOnline.setEnabled(true);
			} else if (line.startsWith("MESSAGE")) {
				messagesArea.append(line.substring(8) + "\n");
			}
		}
	}*/
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 859, 524);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		btnConnect = new JButton("Connect");
		btnConnect.setEnabled( false);

		JLabel lblName = new JLabel("name");
		JTextField userName = new JTextField();
		userName.setText("unnamed");
		userName.setColumns(10);
		userName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect.setEnabled( true);
				//Client.this.userName = userName.getText();
			}
		});

		JLabel lblAddress = new JLabel("address");
		JTextField host = new JTextField();
		host.setText("localhost");
		host.setColumns(10);
		host.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket(host.getText(), 45000);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		massagesBox = new JTextArea();
		massagesBox.setEditable(false);

		textField0 = new JTextField();
		textField0.setText("0");
		textField0.setColumns(10);

		textField = new JTextField();
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				out.println(textField.getText());
				textField.setText("");
			}
		});
		textField.setEditable(false);

		btnSend = new JButton("Send");
		
		btnSend.setEnabled( false);


		btnShowOnline = new JButton("show online");
		btnShowOnline.setEnabled( false);


		JButton btnClear = new JButton("Clear");
		JCheckBox checkBox = new JCheckBox("");

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnConnect)
										.addGap(56)
										.addComponent(lblName)
										.addGap(38)
										.addComponent(userName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
										.addComponent(lblAddress)
										.addGap(41)
										.addComponent(host, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(73)
										.addComponent(btnShowOnline)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnClear)
										.addGap(27))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(textField0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(95)
												.addComponent(textField, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnSend))
										.addComponent( massagesBox, GroupLayout.PREFERRED_SIZE, 787, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(checkBox)
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(checkBox)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnConnect)
										.addComponent(btnShowOnline)
										.addComponent(userName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(host, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblName)
										.addComponent(lblAddress)
										.addComponent(btnClear)))
						.addGap(36)
						.addComponent(massagesBox, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSend))
						.addContainerGap(31, Short.MAX_VALUE))
				);
		frame.getContentPane().setLayout(groupLayout);
	}



	public synchronized Boolean IsStopped() {
		return isStopped;
	}



	public static void main(String[] args) throws Exception {
		
		Client client= new Client ();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					client.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		while(client.IsStopped()){
			Thread.currentThread();
			Thread.sleep(1000);
			System.out.println("..");
		}


		String userInput=client.in.readLine();
		client.textField.setEnabled(true);
		//client.out.println("**00000000");
		//in.println("0000000000000000000");

		while ((userInput ) != null) {
			client. out.println(" ### "+userInput);     //sends to the server immediately
			client. out.println("system: <connected> "  );  // waits until the server sends back data,reads and prints it
			client.massagesBox.append("got a connection " + client.in.readLine()+"\n"); 
			if (userInput.equals("****bye"))
				break;

			userInput=client.in.readLine();
		}

		client. out.println("******CLIENT: I LEAVED");

		//closing any streams connected to a socket  before closing this socket
		client.  out.close();
		client.   in.close();
		//br.close();
		client.   socket.close();

	 

}
}
/*
	@Override
	public void run() {
		btnConnect.setEnabled(false);
		Socket socket = null;
		try {
			socket = new Socket(host, 45000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Process all messages from server, according to the protocol.
		while (true) {
			String line = null;
			try {
				line = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line.startsWith("SUBMITNAME")) {
				out.println(userName);
			} else if (line.startsWith("NAMEACCEPTED")) {
				textField.setEditable(true);
				btnConnect.setEnabled(true);
				btnSend.setEnabled(true);
				btnShowOnline.setEnabled(true);
			} else if (line.startsWith("MESSAGE")) {
				messagesArea.append(line.substring(8) + "\n");
			}
		}

	} 

}
 */