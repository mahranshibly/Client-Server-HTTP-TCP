package server;


/**
 *
 * @author MahranSh
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;


public class chatServerGui {

	private Server server = null;

	private JFrame frame;
	private JButton btnStart;
	private JTextArea massagesBox;


	public JTextArea getMassagesBox() {
		return massagesBox;
	}


	public chatServerGui() {

		initialize();

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(btnStart.getText().equals("Start")){

					server = new   Server(45000,chatServerGui.this);
					new Thread(server).start();
					btnStart.setText("Stop");
					massagesBox.append("Server is ON and waiting to connect !.\n");
				}else{
					server.stop();
					btnStart.setText("Start");
					massagesBox.append("Stopping Server !.\n");

				}

			}
		});
	}


	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Chat - Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.setLayout(new BorderLayout(0, 0));

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		frame.add(desktopPane, BorderLayout.CENTER);

		btnStart = new JButton("Start");
		btnStart.setForeground(Color.BLACK);
		btnStart.setFont(new Font("Arial", Font.BOLD, 13));
		btnStart.setBounds(10, 11, 100, 33);
		desktopPane.add(btnStart);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(UIManager.getBorder("EditorPane.border"));
		scrollPane.setBounds(10, 55, 404, 186);
		scrollPane.getViewport().setBackground(Color.WHITE);

		massagesBox = new JTextArea();
		massagesBox.setFont(new Font("Arial", Font.PLAIN, 12));
		massagesBox.setEditable(false);
		massagesBox.setLineWrap(true);
		massagesBox.setWrapStyleWord(true);
		scrollPane.setViewportView(massagesBox);
		massagesBox.append("Pleasce press START to run server side.\n");

		desktopPane.add(scrollPane);
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatServerGui window = new chatServerGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
