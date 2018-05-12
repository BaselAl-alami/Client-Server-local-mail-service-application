import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class Connection extends Thread {


    private static final int SIGNUP = 1;
    private static final int LOGIN = 2;
    private static final int NEW = 1;
    private static final int INBOX = 2;
    private static final int CHANGE_PASSWORD = 3;
    private static final int CONTACT = 4;
    private static final int LOG_OFF = 5;


    private Socket client;
    private BufferedReader reader;
    private Users users;

    private Client current_user;

     Connection(Socket client) {
        this.client = client;
        users = new Users();
    }

    @Override
    public void run() {
        System.out.println("Connection established");

        while (true) {


            String response = receive();
            int choice = Integer.parseInt(response);

            switch (choice) {
                case SIGNUP:
                    send("Full Name:");
                    String userFullName = receive();
                    while (userFullName.equals("")){
                        send("1");
                        send("The field is empty , please enter the full name: ");
                        userFullName = receive();
                    }
                    send("0");

                    send("User Name:");
                    String userName = receive();
                    while (userName.equals("")){
                        send("1");
                        send("The field is empty , please enter the user name: ");
                        userName = receive();
                    }
                    send("0");

                    send("Password:");
                    String userPassword = receive();
                    while (userPassword.equals("")){
                        send("1");
                        send("The field is empty , please enter the Password: ");
                        userPassword = receive();
                    }
                    send("0");

                    send("Enter Password Again:");
                    String userConfPassword = receive();
                    while (userConfPassword.equals("")){
                        send("1");
                        send("The field is empty , please enter the Conf Password: ");
                        userConfPassword = receive();
                    }
                    send("0");

                    Client user = new Client(userFullName, userName, userPassword, userConfPassword);
                    current_user = user;
                    users.saveUser(user);
                    break;

                case LOGIN:
                    send("User Name:");
                    userName = receive();
                    while (!users.isValidUserName(userName)) {
                        send("1");
                        send("the user name does not exist , please enter another user name: ");
                        userName = receive();
                    }
                    send("0");
                    Client loginClint = users.checkUser(userName);
                    send("PassWord:");
                    userPassword = receive();
                    while (!userPassword.equals(loginClint.getPassword())) {
                        send("1");
                        send("re-Enter the Password");
                        userPassword = receive();
                    }
                    send("0");
                    current_user = loginClint;

                    break;

            }
            if(!chooseOption())
                return;
        }

    }


    private void send(String string) {
        try {
            OutputStream serverOutput = client.getOutputStream();
            PrintWriter output = new PrintWriter(serverOutput);
            output.println(string);
            output.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private String receive() {
        String string = "error";
        try {
            InputStream clientInput = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(clientInput));
            string = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return string;
    }

    private boolean chooseOption() {

        while (true) {
            String option = receive();
            int choiceOption = Integer.parseInt(option);

            switch (choiceOption) {
                case NEW:
                    send("TO:");
                    String address = receive();
                    while (!users.isValidAddress(address)) {
                        send("1");
                        send("address does not exist, please try again:");
                        address = receive();
                    }
                    send("0");

                    send("Subject:");
                    String subject = receive();
                    send("Body:");
                    String body = receive();
                    send("your email has been sent ..");

                    Client to = users.checkUser(address);
                    Email email = new Email(address, current_user.getUserName(), subject, body);
                    to.addEmail(email);
                    users.saveUsersFile();
                    break;

                case INBOX:
                    Vector<Email> myEmails = current_user.getEmails();
                    sendEmails(myEmails);
                    break;

                case CHANGE_PASSWORD:
                    send("enter the new password");
                    String newPassword = receive();
                    send("re-Enter the password");
                    String confPassword = receive();

                    Client c = users.checkUser(current_user.getUserName());
                    c.setPassword(newPassword);
                    c.setConfPassword(confPassword);
                    users.saveUsersFile();
                    break;

                case CONTACT:
                    send(current_user.getContacts());

                    boolean whileCheck = true;
                    while (whileCheck) {
                        int contactOption = Integer.parseInt(receive());
                        switch (contactOption) {
                            case 1:
                                send("Please enter contact username: ");
                                String contact_uname = receive();
                                while (!users.isValidAddress(contact_uname)) {
                                    send("1");
                                    send("contact does not exist, please try again:");
                                    contact_uname = receive();
                                }
                                Client contact = users.checkUser(contact_uname);
                                current_user.addContact(contact);
                                send("0");
                                users.saveUsersFile();
                                break;
                            case 2:
                                send("Please enter contact username: ");
                                contact_uname = receive();
                                while (!users.isValidAddress(contact_uname)) {
                                    send("1");
                                    send("contact does not exist, please try again:");
                                    contact_uname = receive();
                                }
                                send("0");
                                contact = users.checkUser(contact_uname);
                                Vector<Email> contactEmails = current_user.getEmailsFrom(contact);
                                System.out.println("emails count: "+contactEmails.size());
                                sendEmails(contactEmails);
                                break;
                            case 3:
                                whileCheck = false;
                                break;
                        }
                    }

                    break;
                case LOG_OFF:
                    users.saveUsersFile();
                    return false;
            }
        }
    }

    private void sendEmails(Vector<Email> emails) {
        if (emails.size() > 0)
            for (Email email1 :
                    emails) {
                send(email1.toString());
                try {
                    reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        send("0");
    }
}
