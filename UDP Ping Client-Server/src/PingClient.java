/*
* Mark Pasquantonio
* Comp 430 - Assignment 2: UDP Ping Client/Server Program
* 4/1/2019
* File Name: PingClient.java
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;

public class PingClient {

	public static void main(String[] args) throws IOException {
		for (int pingNumber = 1; pingNumber <= 10; ++pingNumber) {
			// print current time and ping
			System.out.format("Ping #%d, Current Time: %s\n", pingNumber, new Date().toString());

			// prompt user for message
			System.out.print("Enter a message to send: ");

			// initialize input reader
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

			// create socket
			DatagramSocket clientSocket = new DatagramSocket();

			// specify ip address
			InetAddress IPAddress = InetAddress.getByName("localhost");

			// arrays to hold send and receive data
			byte[] sendData;
			byte[] receiveData = new byte[1024];

			// reading input from user
			String sentence = inFromUser.readLine();

			// converting it to bytes
			sendData = sentence.getBytes();

			// create packet to send
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 2000);

			// sending time
			long start = System.currentTimeMillis();

			// send packet through socket
			clientSocket.send(sendPacket);

			// create receivable packet
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			// set time out of 1 second
			clientSocket.setSoTimeout(1000);

			try {
				// try to receive packet
				clientSocket.receive(receivePacket);

				// receiving time
				long finish = System.currentTimeMillis();

				// print RTT
				System.out.println("Round trip time(RTT): " + (finish - start));

				String modifiedSentence = new String(receivePacket.getData());
				System.out.println("FROM SERVER: " + modifiedSentence);
				clientSocket.close();
			} catch (SocketTimeoutException ex) {
				System.out.println("Request timed out.");
			}
		}
	}
}
