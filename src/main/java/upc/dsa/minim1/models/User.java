package upc.dsa.minim1.models;

import upc.dsa.minim1.util.RandomUtils;
import java.util.Date;
public class User {
    String idU;
    String name;
    String sname;
    String mail;
    String date;

    public User() {
        this.setIdU(RandomUtils.getId());
    }

    public User(String name, String sname, String mail, String date) {
        this(null,name,sname,mail,date);
    }

    public User(String idU, String name, String sname, String mail, String date) {
        if(idU != null) this.setIdU(idU);
        setName(name);
        setSname(sname);
        setMail(mail);
        setDate(date);
    }


    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User [id="+idU+", nombre=" + name + ", apellido=" + sname +", email="+ mail+ ", fecha nacimiento = "+ date +"]";
    }
}
