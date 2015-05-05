/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vs;

/**
 *
 * @author debian
 */
public class HauskraftwerkServer {
    
    public static int countSensoren = 4;
    public static int[] windValue = new int[countSensoren];
    public static int[] powerValue = new int[countSensoren];
    public static long send = 0;
    public static long received = 0;
    public static long sendTime;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < countSensoren; i++) {
            Sensor s = new Sensor(i + 1, 10000);
            s.start();
        }
        
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();

        UDPServer udpServer = new UDPServer();
        udpServer.start();
    }
}
