package polskipawel.server;

import polskipawel.model.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) throws IOException {


        Model theModel = new Model();

        ServerSocket listener = null;
        String line;        String line1;
        BufferedReader is;
        BufferedWriter os;
        Socket socketOfServer = null;

        // Try to open a server socket on port 9999
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)


        try {
            listener = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            System.out.println("Server is waiting to accept user...");

            // Accept client connection request
            // Get new Socket at Server.
            socketOfServer = listener.accept();
            System.out.println("Accepted a client!");

            // Open input and output streams
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            line1 = is.readLine();
            System.out.println("Client says: " +line1);
            os.write("hello client");
            os.newLine();
            os.flush();
            while (true) {
                // Read data to the server (sent from client).
                line = is.readLine();
                System.out.println("Client says: " +line);

                os.write(">> " + line);

                os.newLine();

                os.flush();



            // If users send QUIT (To end conversation).
                if (line.equals("QUIT")) {
                    os.write(">> OK");
                    os.newLine();
                    os.flush();
                  //
                    //  break;
                }
           }
//
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("Sever stopped!");
    }


    }
