package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class Program3 extends Program1{

    private Runnable listener;

    private String message;

    private double x[], y[], r[], rssi;

    private int ports[];

    private  int counter = 1;

    Program3(double x1,double y1,double r1) {

        ports = new int[2];
        x = new double[3];

        y = new double[3];

        r = new double[3];

        x[0] = x1;

        y[0] = y1;

        r[0] = r1;

        listener = new Runnable() {

            @Override
            public void run() {
                while (counter < 3){

                    try {
                        udpSocket = new DatagramSocket(3002);

                        byteDataReceived = new byte[100];

                        packetReceived = new DatagramPacket(byteDataReceived,byteDataReceived.length);


                        udpSocket.receive(packetReceived);

                        ports[counter-1] = packetReceived.getPort();

                        int singleByteCounter = 0;

                        for(byte singleByte:packetReceived.getData()){

                            if((char)singleByte != '\n') {

                                ++singleByteCounter;

                                switch (singleByteCounter){
                                    case 1:
                                        x[counter] = singleByte;
                                        break;
                                    case 2:
                                        y[counter] = singleByte;
                                        break;

                                    case 3:
                                        r[counter] = singleByte;
                                }
                            }
                        }
                        ++counter;

                        if(counter == 3){
                            Random random = new Random();
                            for(int port : ports){

                                double x_super = random.nextDouble();

                                double y_super = random.nextDouble();

                                byteDataSent = (x_super+"\n"+y_super).getBytes();

                                packetSent = new DatagramPacket(byteDataSent,byteDataSent.length,
                                        InetAddress.getByName("localhost"),port);

                                udpSocket.send(packetSent);
                            }
                        }

                    } catch (SocketException socketException) {

                        System.out.println("ERROR WHILE LISTENING" + "--" + socketException);

                    } catch (IOException ioException) {

                        System.out.println("ERROR WHILE RECEIVING PACKET" + "--" + ioException);

                    }
                }
            }

        };
        Thread program3Thread = new Thread(listener);

        program3Thread.start();
    }
}
