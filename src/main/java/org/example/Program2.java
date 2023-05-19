package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

//port 3002 : hussam's computer
//constructor port : lab's or kamal computer

public class Program2 extends Program1 {


    private Runnable listener;

    private String message;

    private double x, y, r, rssi;

    private String name;

    Program2(int port, String name) {

        this.name = name;

        listener = new Runnable() {

            @Override
            public void run() {

                try {
                    udpSocket = new DatagramSocket(port);

                    byteDataSent = "STOP".getBytes();

                    packetSent = new DatagramPacket(byteDataSent, byteDataSent.length,
                            InetAddress.getByName("localhost"), 3000);

                    udpSocket.send(packetSent);

                    byteDataReceived = new byte[100];

                    packetReceived = new DatagramPacket(byteDataReceived, byteDataReceived.length);

                    udpSocket.receive(packetReceived);

                    Random random = new Random();

                    x = random.nextDouble();

                    y = random.nextDouble();

                    rssi = random.nextDouble();

                    r = random.nextDouble();

                    byteDataSent = (x + "\n" + y + "\n" + r + "\n").getBytes();

                    if (!getName().equals("hussam")) {

                        packetSent = new DatagramPacket(byteDataSent, byteDataSent.length,
                                InetAddress.getByName("localhost"), 3002);

                        udpSocket.send(packetSent);
                    }


                } catch (SocketException socketException) {

                    System.out.println("ERROR WHILE LISTENING" + "--" + socketException);

                } catch (IOException ioException) {

                    System.out.println("ERROR WHILE RECEIVING PACKET" + "--" + ioException);

                }
            }

        };
        Thread program1Thread = new Thread(listener);

        program1Thread.start();
    }


    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }
}
