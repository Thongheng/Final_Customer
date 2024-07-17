public class Customer {
    private final int id;
    private final String lastname;
    private final String firstname;
    private final String phone;

    public Customer(int id, String lastname, String firstname, String phone) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhone() {
        return phone;
    }
}
