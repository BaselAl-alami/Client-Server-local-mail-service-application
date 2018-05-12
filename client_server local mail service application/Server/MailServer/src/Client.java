import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;


public class Client implements Serializable {
    private String fullName;
    private String userName;
    private String password;
    private String confPassword;

    private Vector<Email> emails = new Vector<>();
    private Vector<Client> contacts = new Vector<>();

    public Client() {
    }

    public Client(String fullName, String userName, String password, String confPassword) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.confPassword = confPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    @Override
    public String toString() {
        String s = "Client{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", confPassword='" + confPassword + '\'' +
                ", emails=\n";
        if (emails != null)
            for (Enumeration e = emails.elements(); e.hasMoreElements(); ) {
                s += (e.nextElement()) + "\n";
            }
        s += ", contacts=\n";
        if (contacts != null)
            for (Client contact :
                    contacts) {
                s += contact.getUserName() + ", ";
            }
        s += "\n}";
        return s;
    }

    public void addEmail(Email email) {
        emails.add(email);
    }

    public void addContact(Client contact) {
        contacts.add(contact);
    }

    public String getContacts() {
        StringBuilder s = new StringBuilder();
        if (contacts != null)
            for (Client contact :
                    contacts) {
                s.append(contact.getUserName());
                s.append(", ");
            }
        return s.toString();
    }

    public Vector<Email> getEmails() {
        return emails;
    }

    public Vector<Email> getEmailsFrom(Client contact) {
        Vector<Email> out = new Vector<>();
        for (Email email : emails)
            if (contact.getUserName().equals(email.getFrom()))
                out.add(email);
        return out;
    }
}
