package nssa.nm.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSocket {

	private String ip = "localhost";
	private int port = 8002;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void send(String message) {
		out.println(message);
	}

	public void connect() {

		try {
			socket = new Socket(ip, port);
			out = getWriter(socket);
			in = getReader(socket);
			String msg = null;
			while ((msg = in.readLine()) != null)
				System.out.println(msg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
