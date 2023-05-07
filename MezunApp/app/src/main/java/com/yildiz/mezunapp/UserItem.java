package com.yildiz.mezunapp;

public class UserItem {
    String nameSurname;
    String eMail;

    public UserItem(String nameSurname, String eMail) {
        this.nameSurname = nameSurname;
        this.eMail = eMail;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


}
