/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vs;

import java.io.*;
import java.net.*;

class UDPClient
{
   public void sendData(String data, int port) throws Exception
   {
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      sendData = data.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
      clientSocket.send(sendPacket);
      clientSocket.close();
   }
}
