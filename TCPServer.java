package vs;

// 22. 10. 10
/**
 *
 * @author Peter Altenberd (Translated into English by Ronald Moore) Computer
 * Science Dept. Fachbereich Informatik Darmstadt Univ. of Applied Sciences
 * Hochschule Darmstadt
 */
import java.io.*;
import java.net.*;

public class TCPServer extends Thread {

    String line;
    BufferedReader fromClient;
    DataOutputStream toClient;

    public void run() {
        try {
            EstablishTCP(9999);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void EstablishTCP(int socket) throws Exception {
        ServerSocket contactSocket = new ServerSocket(socket);
        while (true) {                            // Handle connection request
            Socket client = contactSocket.accept(); // creat communication socket
            System.out.println("Connection with: " + client.getRemoteSocketAddress());
            this.handleRequests(client);
        }
    }

    public void handleRequests(Socket s) {
        try {
            fromClient = new BufferedReader( // Datastream FROM Client
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
        for (int i = 0; i < HauskraftwerkServer.windValue.length; i++) {
            toClient.writeBytes("Sensor " + (i + 1) + "<br />");
            toClient.writeBytes("Wind: " + HauskraftwerkServer.windValue[i] + "<br />");
            toClient.writeBytes("Strom: " + HauskraftwerkServer.powerValue[i] + "<br />");
            toClient.writeBytes("<br />");
        }

        toClient.writeBytes("</body>\n</html>\n");
    }
}
