package examples;

// 22. 10. 10

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPServer {

  String line;
  BufferedReader fromClient;
  DataOutputStream toClient;
  static Random rand = new Random();

  public static void main(String[] args) throws Exception {
    TCPServer server = new TCPServer();
    server.EstablishTCP(9999);
  }

  public void EstablishTCP(int socket)  throws Exception {
   ServerSocket contactSocket = new ServerSocket(socket);
      while (true) {                            // Handle connection request
        Socket client = contactSocket.accept(); // creat communication socket
        System.out.println("Connection with: "+client.getRemoteSocketAddress());
        this.handleRequests(client);
      }
  }

  public void handleRequests(Socket s) {
    try {
      fromClient = new BufferedReader(        // Datastream FROM Client
        new InputStreamReader(s.getInputStream()));
      toClient = new DataOutputStream(
        s.getOutputStream());                 // Datastream TO Client
      while (receiveRequest()) {              // As long as connection exists
      }
      sendResponse();
      fromClient.close();
      toClient.close();
      s.close();
      System.out.println("Session ended, Server remains active");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public boolean receiveRequest() throws IOException {
    boolean holdTheLine = true;
    System.out.println("Received: " + (line = fromClient.readLine()));
    if (line.equals("")) {                         // End of session?
      holdTheLine = false;
    }
    return holdTheLine;
  }

  public void sendResponse() throws IOException {
    //toClient.writeBytes(line.toUpperCase() + '\n');  // Send answer
    toClient.writeBytes("HTTP/1.x 200 OK\nContent-Type: text/html; charset=utf-8\n\n");
    toClient.writeBytes("<html>\n<header>\n<title>Testseite</title>\n</header>\n<body>\n");
    toClient.writeBytes("Wind: " + getValueWind() + "<br />");
    toClient.writeBytes("Strom: " + getValuePower() + "<br />");
    toClient.writeBytes("</body>\n</html>\n");
  }
  
  public static int getValueWind() {
      int value = randGaussian(50, 18);
      if(value > 140) {
          value = 140;
      }
      
      if(value < 0) {
          value = 0;
      }
      
      return value;
  }
  
  public static int getValuePower() {
      int value = randGaussian(3, 1.5);
      if(value > 10) {
          value = 10;
      }
      
      if(value < 0) {
          value = 0;
      }
      
      return value;
  }
  
  public static int randGaussian(double average, double deviation) {
    return (int)(rand.nextGaussian() * deviation + average);
  }
}
