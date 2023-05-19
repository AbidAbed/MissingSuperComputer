package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Program4 extends Program1 {
    private Runnable listener;

    private String message = "";


    Program4(int port){
        listener = new Runnable() {

            @Override

            public void run() {

                    try {
                        udpSocket = new DatagramSocket(port);

                        byteDataReceived = new byte[100];

                        packetReceived = new DatagramPacket(byteDataReceived, byteDataReceived.length);

                        udpSocket.receive(packetReceived);

                        message += "(super computer x , super computer y )"+"("+packetReceived.getData()+")";

                        printMessage();

                    } catch (SocketException socketException) {

                        System.out.println("ERROR WHILE LISTENING" + "--" + socketException);

                    } catch (IOException ioException) {

                        System.out.println("ERROR WHILE RECEIVING PACKET" + "--" + ioException);

                    }
                }
        };
    }
}
