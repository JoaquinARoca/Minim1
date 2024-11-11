package upc.dsa.minim1.models;

public class UserPasses {
    String idU;
    int x,y;

    public UserPasses(String idU, int x, int y) {
        this.idU = idU;
        this.x = x;
        this.y = y;
    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
