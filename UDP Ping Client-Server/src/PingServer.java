/*
 * Mark Pasquantonio
 * Comp 430 - Assignment 2: UDP Ping Client/Server Program
 * 4/1/2019
 * File Name: PingServer.java
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

class PingServer {

    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(2000);
        byte[] receiveData = new byte[1024]; //Processing loop

        while(true) {

            //create a random number generator for use in packet loss simulation
            Random random = new Random();

            //create a datagram packet to hold incoming UDP packet
            DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);

            // get the client message
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData(),0, receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress(); //get client's IP
            int port = receivePacket.getPort(); //get client's port #

            //print out the clients's IP address, port number and the message
            System.out.println("client's port # = " + port);
            System.out.println("client'sIP = " +IPAddress);
            System.out.println("client's message = " +sentence);

            //capitalize the message from the client
            String capitalizedSentence = sentence.toUpperCase();

            //a random number in the range of 0 to10
            //simulate the packet loss
            int rand = random.nextInt(10);

            // if rand is less than 4 we consider the packet lost and do not reply
            if (rand < 4) {
                System .out.println("Reply not sent");
                continue;
            }

            //otherwise, the server responds
            byte[] sendData = capitalizedSentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            System.out.println("Reply to the client sent");
        }// While
    }// Main
}// Class
