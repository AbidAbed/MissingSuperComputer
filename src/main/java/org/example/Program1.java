package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
//port 3000 : superComputer
public class Program1 {
    private static Runnable listener;
    protected DatagramSocket udpSocket;
    protected byte byteDataReceived[];
    protected DatagramPacket packetReceived;
    protected DatagramPacket packetSent;

    private static Boolean keepRunning;
    protected byte byteDataSent[];

    private static String message;

    public static void main(String[] args) {

        Program1 program1 = new Program1();

        Thread superComputerThread = new Thread(listener);

        superComputerThread.start();

        Scanner inputScanner = new Scanner(System.in);

        String superComputerMind = "";

        while (!superComputerMind.equals("EXIT")) {

            superComputerMind = inputScanner.nextLine().trim();

        }
        keepRunning = false;
    }

    public void printMessage() {
        System.out.println(message);
    }

    Program1() {

        keepRunning = true;

        listener = new Runnable() {

            @Override

            public void run() {

                while (keepRunning) {

                    try {
                        udpSocket = new DatagramSocket(3000);

                        byteDataReceived = new byte[100];

                        packetReceived = new DatagramPacket(byteDataReceived, byteDataReceived.length);

                        udpSocket.receive(packetReceived);

                        byteDataReceived = packetReceived.getData();

                        String dataReceivedStringRep = "";


                        for (byte singleByte : byteDataReceived)
                            dataReceivedStringRep += (char) singleByte;

                        message = "Connection made from :" + packetReceived.getAddress()
                                + "\n" + "Message is :" + dataReceivedStringRep + "\n";
                        if (dataReceivedStringRep.equals("STOP")) {

                            byteDataSent = ("OK\n"+udpSocket.getLocalAddress()+"\n").getBytes();

                            packetSent = new DatagramPacket(byteDataSent, byteDataSent.length);

                            udpSocket.send(packetSent);

                            message += "Message sent successfully , message is :\n" +
                                    ("OK\n"+udpSocket.getLocalAddress()+"\n");
                        } else {
                            message += "Message didn't sent , unknown command\n";
                        }

                        printMessage();

                        message = "";
                    } catch (SocketException socketException) {

                        System.out.println("ERROR WHILE LISTENING" + "--" + socketException);

                    } catch (IOException ioException) {

                        System.out.println("ERROR WHILE RECEIVING PACKET" + "--" + ioException);

                    }
                }
            }
        };
    }

}