package UDP_Client;

/**
 *
 * @author MahranSh
 */
// Client that sends and receives packets to/from a server.
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client extends JFrame
{
   private JTextField enterField; // for entering messages
   private JTextArea displayArea; // for displaying messages
   private DatagramSocket socket; // socket to connect to server
   private boolean isStopped    = false;

   // set up GUI and DatagramSocket
   public Client()
   {
      super( "Client" );

      enterField = new JTextField( "Enter your message here, to end write 'bye'" );
      enterField.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent event )
            {
               try // create and send packet
               {
                  // get message from textfield
                  String message = event.getActionCommand();
                  displayArea.append( "\nSending packet containing: " +
                     message + "\n" );

                  byte data[] = message.getBytes(); // convert to bytes

                  // create sendPacket
                  DatagramPacket sendPacket = new DatagramPacket( data,
                     data.length, InetAddress.getLocalHost(), 5000 );

                  socket.send( sendPacket ); // send packet
                  displayArea.append( "Packet sent\n" );
                  displayArea.setCaretPosition(
                     displayArea.getText().length() );
               } // end try
               catch ( IOException ioException )
               {
                  displayMessage( ioException.toString() + "\n" );
                  ioException.printStackTrace();
               } // end catch
            } // end actionPerformed
         } // end inner class
      ); // end call to addActionListener

      add( enterField, BorderLayout.NORTH );

      displayArea = new JTextArea();
      add( new JScrollPane( displayArea ), BorderLayout.CENTER );

      setSize( 400, 300 ); // set window size
      setVisible( true ); // show window

      try // create DatagramSocket for sending and receiving packets
      {
         socket = new DatagramSocket();
      } // end try
      catch ( SocketException socketException )
      {
         socketException.printStackTrace();
         System.exit( 1 );
      } // end catch
   } // end Client constructor

   // wait for packets to arrive from Server, display packet contents
   public void waitForPackets()
   {
      while ( !isStopped )
      {
         try // receive packet and display contents
         {
            byte data[] = new byte[ 100 ]; // set up packet
            DatagramPacket receivePacket = new DatagramPacket(
               data, data.length );

            socket.receive( receivePacket ); // wait for packet

            // display packet contents
            displayMessage( "\nPacket received:" +
               "\nFrom host: " + receivePacket.getAddress() +
               "\nHost port: " + receivePacket.getPort() +
               "\nData Length: " + receivePacket.getLength() +
               "\nData containing:\n\t" + new String( receivePacket.getData(),
                  0, receivePacket.getLength() ) );

            String message = new String( receivePacket.getData(),
                  0, receivePacket.getLength() );
            if (message.equals("bye"))
                break;
         } // end try
         catch ( IOException exception )
         {
            displayMessage( exception.toString() + "\n" );
            exception.printStackTrace();
         } // end catch
      } // end while
      isStopped=true;
      socket.close();
      System.out.println( "\nI LEAVED:");
      System.exit( 1 );
   } // end method waitForPackets

   // manipulates displayArea in the event-dispatch thread
   private void displayMessage( final String messageToDisplay )
   {
      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run() // updates displayArea
            {
               displayArea.append( messageToDisplay );
            } // end method run
         }  // end inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method displayMessage
   // execute application
 public static void main( String args[] )
 {
 Client application = new Client();

 application.setDefaultCloseOperation(
 JFrame.EXIT_ON_CLOSE );

 application.waitForPackets();
 }
}  // end class Client
