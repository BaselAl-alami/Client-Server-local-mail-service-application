package com.example.test;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final int PORT = 6500;
    private static BufferedReader reader ;
    private static BufferedReader consoleReader;
    private static Socket socket;

    public static void main(String[] args) {

        try {
            socket = new Socket("127.0.0.1", PORT);

            InputStream userInputStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(userInputStream));
            consoleReader = new BufferedReader(new InputStreamReader(System.in));


            System.out.println("Welcome to mail server , Please SignUp or Login to access the server:");
            System.out.println("--------------------------------");
            System.out.println("1.. SignUP");
            System.out.println("2.. LogIn");




            while (true){
                String response = consoleReader.readLine();
                if (response.equals(""))
                    continue;
                int choice = Integer.parseInt(response);
                sendToServer(response);
                switch (choice){
                    case 1:
                        // To read Fill name
                        readUserInfo();
                        // To read user name
                        readUserInfo();
                        // To read Password
                        readUserInfo();
                        // To read conf Password
                        readUserInfo();

                        receiveOptions();
                        chooseOptions();
                        break;

                    case 2:

                        int status = 1;
                        while (status != 0) {
                            String NameLogin = reader.readLine();
                            System.out.println(NameLogin);
                            String userNameLogin = consoleReader.readLine();
                            sendToServer(userNameLogin);

                            status = Integer.parseInt(reader.readLine());
                            if (status == 0) {
                                do {
                                    String Password = reader.readLine();
                                    System.out.println(Password);
                                    String userPassword = consoleReader.readLine();
                                    sendToServer(userPassword);
                                    status = Integer.parseInt(reader.readLine());
                                } while(status != 0);
                            }
                        }
                        receiveOptions();
                        chooseOptions();
                        break;


                }
            }



        }catch (NullPointerException | IOException e){
            System.out.println(e.getMessage());
        }

    }



    private static void receiveOptions(){
        System.out.println("Enter 1-4 for the option below...");
        System.out.println("1.. New");
        System.out.println("2.. Inbox");
        System.out.println("3.. Change Password");
        System.out.println("4.. Contacts");
        System.out.println("5.. Log Off");

    }

    private static void chooseOptions(){
        try {
            while (true) {
                String chooseOption = consoleReader.readLine();
                if (chooseOption.equals(""))
                    continue;
                int choose = Integer.parseInt(chooseOption);
                sendToServer(chooseOption);

                switch (choose) {
                    case 1:
                        // To
                        readUserInfo();
                        // Subject
                        System.out.println(reader.readLine());
                        String subject = consoleReader.readLine();
                        sendToServer(subject);

                        //Body
                        System.out.println(reader.readLine());
                        StringBuilder body = new StringBuilder();
                        String line;
                        while (!(line = consoleReader.readLine()).equals("end")) {
                            body.append(line);
                        }
                        sendToServer(body.toString());
                        System.out.println(reader.readLine());
                        break;
                    case 2:
                        System.out.println("Emails List:");
                        reciveEmailList();
                        break;
                    case 3:
                        System.out.println(reader.readLine());
                        String newPassword = consoleReader.readLine();
                        sendToServer(newPassword);
                        System.out.println(reader.readLine());
                        String confPassword = consoleReader.readLine();
                        sendToServer(confPassword);
                        System.out.println("your password has been changed successfully");
                        break;
                    case 4:
                        System.out.println(reader.readLine());
                        boolean while_cont = true;
                        while (while_cont) {
                            System.out.println("Please select one of these options:");
                            System.out.println("1.. Add contact");
                            System.out.println("2.. Show contact emails");
                            System.out.println("3.. back to previous menu");
                            String optionString = consoleReader.readLine();
                            if (optionString.equals(""))
                                continue;
                            int option = Integer.parseInt(optionString);
                            sendToServer(optionString);
                            switch (option) {
                                case 1:
                                    readUserInfo();
                                    System.out.println("Contact has been added successfully!");
                                    break;
                                case 2:
                                    String contact = readUserInfo();
                                    System.out.println(contact + " Emails List:");
                                    reciveEmailList();
                                    break;
                                case 3:
                                    while_cont = false;
                                    break;
                            }
                        }
                        break;
                    case 5:
                        System.out.println("Logged off");
                        System.exit(0);
                        break;
                }
                receiveOptions();
            }

        } catch (IOException e){
            System.out.println("Exception throw choosing options");
        }



    }

    private static void sendToServer(String string){
        try {
            OutputStream userOutputStream = socket.getOutputStream();
            PrintWriter output = new PrintWriter(userOutputStream);
            output.println(string);
            output.flush();

        }catch (IOException e){
            System.out.println("error sending to the server");
        }
    }


     private static String readUserInfo() throws IOException {
        int status;
        String s;
        do {
            System.out.println(reader.readLine());
            s = consoleReader.readLine();
            sendToServer(s);
            status = Integer.parseInt(reader.readLine());
        } while (status != 0);
        return s;
    }



     private static void reciveEmailList() throws IOException {
        String email;
        while (!(email = reader.readLine()).equals("0")) {
            System.out.println(email);
            System.out.println("next? ");
            consoleReader.readLine();
            sendToServer("");
        }
    }


}
