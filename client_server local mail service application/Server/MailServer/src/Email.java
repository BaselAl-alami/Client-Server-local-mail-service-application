import java.io.Serializable;

public class Email implements Serializable {
    private String address;
    private String from;
    private String subject;
    private String body;

    public Email() {
    }

    public Email(String address,String from, String subject, String body) {
        this.address = address;
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public String getFrom() {
        return from;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                "address='" + address + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}
