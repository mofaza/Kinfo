package elias.lind.kinfo;


public class User {

    private String kidsname;
    private String foodallergies;
    private String animalallergies;
    private String message;
    private String password;
    private String grownup;
    private String email;
    private String relationship;
    private String phonenumber;
    private String address;
    private String kidpassword;


    public User(String kidsname, String foodallergies, String animalallergies, String message, String password, String grownup, String email, String relationship, String phonenumber, String address, String kidpassword) {
        this.kidsname = kidsname;
        this.foodallergies = foodallergies;
        this.animalallergies = animalallergies;
        this.message = message;
        this.password = password;

        this.grownup = grownup;
        this.email = email;
        this.relationship = relationship;
        this.phonenumber = phonenumber;
        this.address = address;
        this.kidpassword = kidpassword;


    }

    public User() {
    }

    public String getKidsname() {
        return kidsname;
    }

    public String getFoodallergies() {
        return foodallergies;
    }

    public String getAnimalallergies() {
        return animalallergies;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
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

    public String getKidpassword() {
        return kidpassword;
    }


}
