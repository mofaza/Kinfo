package elias.lind.kinfo;

public class GrownupUser {

    private String grownup;
    private String email;
    private String relationship;
    private String phonenumber;
    private String address;


    public GrownupUser(String grownup, String email, String relationship, String phonenumber, String address) {
        this.grownup = grownup;
        this.email = email;
        this.relationship = relationship;
        this.phonenumber = phonenumber;
        this.address = address;

    }

    public GrownupUser() {
    }

    public String getGrownup() {
        return grownup;
    }

    public String getEmail() {
        return email;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getAddress() {
        return address;
    }


}
