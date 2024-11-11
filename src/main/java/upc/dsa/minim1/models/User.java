package upc.dsa.minim1.models;

import upc.dsa.minim1.util.RandomUtils;
import java.util.Date;
public class User {
    String idU;
    String name;
    String sname;
    String mail;
    Date date;

    public User() {
        this.setIdU(RandomUtils.getId());
    }

    public User(String name, String sname, String mail, Date date) {
        this.idU= null;
        this.name = name;
        this.sname = sname;
        this.mail = mail;
        this.date = date;
    }

    public User(String idU, String name, String sname, String mail, Date date) {
        if(idU != null) this.setIdU(idU);
        this.name = name;
        this.sname = sname;
        this.mail = mail;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Track [id="+idU+", nombre=" + name + ", apellido=" + sname +", email="+ mail+ "fecha nacimiento = "+ date +"]";
    }
}
