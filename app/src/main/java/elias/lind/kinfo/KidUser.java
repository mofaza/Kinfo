package elias.lind.kinfo;


public class KidUser {

    private String kidsname;
    private String foodallergies;
    private String animalallergies;
    private String message;
    private String password;

    /*private String grownup;
    private String email;
    private String relationship;
    private String phonenumber;
    private String address;*/


    private KidUser(String kidsname, String foodallergies, String animalallergies, String message, String password) {
        this.kidsname = kidsname;
        this.foodallergies = foodallergies;
        this.animalallergies = animalallergies;
        this.message = message;
        this.password = password;

    }

    public KidUser() {
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


}
