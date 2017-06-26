package polskipawel.server;

import polskipawel.model.Model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String args[]) throws IOException {


        Model theModel = new Model();
        ArrayList<String> equipmentsTypes = new ArrayList<>();


        ServerSocket listener = null;
        String line;        String line1;
        BufferedReader is;
        BufferedWriter os;
        Socket socketForClient = null;

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
            System.out.println("# Server is waiting to accept user...");

            //theModel.getDataFromFileAndAddToTableList();

            // Accept client connection request
            // Get new Socket at Server.
            socketForClient = listener.accept();
            System.out.println("# Accepted a client on port: " + socketForClient.getPort());
            // Open input and output streams
            is = new BufferedReader(new InputStreamReader(socketForClient.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketForClient.getOutputStream()));




            ObjectOutputStream out = new ObjectOutputStream(socketForClient.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socketForClient.getInputStream());


            line1 = is.readLine();
            System.out.println("<< Client sends welcome message: " +line1);
            os.write("Welcome " + socketForClient.getRemoteSocketAddress());
            os.newLine();
            os.flush();


            while (true) {
                // Read data to the server (sent from client).
                line = is.readLine();
                System.out.println("loop: " +line);



                if (line.equals("GETEQTYPES")) {


                    equipmentsTypes.add("Pace DCR7111");
                    equipmentsTypes.add("Cisco HD8485");
                    equipmentsTypes.add("Cisco HD8685");
                    equipmentsTypes.add("Horizon HD High");
                    equipmentsTypes.add("Horizon DVR High");
                    equipmentsTypes.add("Horizon DVR Low");
                    equipmentsTypes.add("Mediamudul CI+");
                    equipmentsTypes.add("Modem");
                    equipmentsTypes.add("Router");

                    out.writeObject(equipmentsTypes);

                    out.flush();
//                    os.write(">> OK");
//                    os.newLine();
//                    os.flush();
                } else {
                    os.write(">> " + line);

                    os.newLine();

                    os.flush();
                }



            // If users send QUIT (To end conversation).
                if (line.equals("Hello2")) {
                    os.write(">> OK");
                    os.newLine();
                    os.flush();


                  //
                      break;
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
