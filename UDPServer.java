/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vs;

import java.io.*;
import java.net.*;

/**
 *
 * @author debian
 */
public class UDPServer extends Thread {

    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            long startTime = System.currentTimeMillis();
            long requestCount = 0;
            long avg = 0;
            long avgCount = 0;
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                sentence = sentence.trim();
                //System.out.println("RECEIVED: " + sentence);
                //System.out.println("TIME: " + (System.currentTimeMillis() - HauskraftwerkServer.sendTime));
                String[] splitData = sentence.split(";");
                int sensorId = Integer.parseInt(splitData[0]);
                HauskraftwerkServer.windValue[sensorId - 1] = Integer.parseInt(splitData[1]);
                HauskraftwerkServer.powerValue[sensorId - 1] = Integer.parseInt(splitData[2]);
                requestCount++;
                HauskraftwerkServer.received++;
                if (System.currentTimeMillis() - startTime > 14000) {
                    avg += requestCount;
                    avgCount++;
                    System.out.println("Count: " + requestCount);
                    //System.out.println("AvgCount: " + (avg / avgCount));
                    System.out.println("Lost: " + (HauskraftwerkServer.send - HauskraftwerkServer.received));
                    requestCount = 0;
                    HauskraftwerkServer.send = 0;
                    HauskraftwerkServer.received = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
