/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vs;

import java.util.Random;

/**
 *
 * @author debian
 */
public class Sensor extends Thread {

    static Random rand = new Random();
    static int sleepTime = 2000;
    public int id;
    public int ttl;
    
    public Sensor(int id, int ttl){
        this.id = id;
	this.ttl = ttl;
    }
    
    public void run() {
        try{    
	long startTime = System.currentTimeMillis();
        UDPClient client = new UDPClient();
            while(true){
                client.sendData(id + ";" + getValueWind() + ";" + getValuePower(), 9876);
                Thread.sleep(sleepTime);
                HauskraftwerkServer.send++;
                HauskraftwerkServer.sendTime = System.currentTimeMillis();
		//if (System.currentTimeMillis() - startTime > ttl) {
		//   break;
		//}            
	    }
	    
		//Thread.sleep(5000);
	        //client.sendData(id + ";" + getValueWind() + ";" + getValuePower(), 9876);
        }
        catch(Exception e){
          System.out.println("Exception: " + e);
      }
    }

    public static int getValueWind() {
        int value = randGaussian(50, 18);
        if (value > 140) {
            value = 140;
        }

        if (value < 0) {
            value = 0;
        }

        return value;
    }

    public static int getValuePower() {
        int value = randGaussian(3, 1.5);
        if (value > 10) {
            value = 10;
        }

        if (value < 0) {
            value = 0;
        }

        return value;
    }

    public static int randGaussian(double average, double deviation) {
        return (int) (rand.nextGaussian() * deviation + average);
    }
}
