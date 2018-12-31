package elias.lind.kinfo;


public class KidUser {

    private String kidsname;
    private String uid;
    private String kidpassword;


    public KidUser(String kidsname,
                   String uid,
                   String kidpassword) {
        this.kidsname = kidsname;
        this.uid = uid;
        this.kidpassword = kidpassword;


    }

    public KidUser() {
    }

    public String getKidsname() {
        return kidsname;
    }

    public String getUid() {
        return uid;
    }

    public String getKidpassword() {
        return kidpassword;
    }




}
