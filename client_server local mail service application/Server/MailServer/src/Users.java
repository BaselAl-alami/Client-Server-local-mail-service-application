import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class Users {
    private static Email email;
    private static Vector<Email> emails;
    private static Vector<Client> clients;

    public Users() {
        loadUsersFile();
    }

    public void saveUser(Client client) {

        if(clients == null) clients = new Vector<>();
        clients.add(client);
        saveUsersFile();
    }

    public void saveUsersFile() {
        try {
            for (Enumeration e = clients.elements(); e.hasMoreElements(); ) {
                System.out.println(e.nextElement());
            }
            FileOutputStream outputStream = new FileOutputStream("Users");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(clients);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadUsersFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Users");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object o = objectInputStream.readObject();
            clients = (Vector<Client>) o;
            if(clients == null) clients = new Vector<>();
            for (Enumeration e = clients.elements(); e.hasMoreElements(); ) {
                System.out.println(e.nextElement());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public boolean isValidAddress(String address) {
        for (Client client1 : clients) {
            if (client1.getUserName().equals(address)) {
                return true;
            }

        }
        return false;
    }

    public boolean isValidUserName(String username) {
        for (Client client1 : clients) {
            if (client1.getUserName().equals(username)) {
                return true;
            }

        }
        return false;
    }

    public Client checkUser(String username) {
        for (Client client1 : clients) {
            if (client1.getUserName().equals(username)) {
                return client1;
            }

        }
        return null;
    }



}
