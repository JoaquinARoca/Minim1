package upc.dsa.minim1.models;

public class InterPoint {
    int x,y;
    ElemType type;

    public InterPoint(int x, int y, ElemType type) {
        this.x = x;
        this.y = y;
        this.type = type;
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

    public ElemType getE() {
        return type;
    }

    public void setE(ElemType e) {
        this.type = e;
    }

    @Override
    public String toString() {
        return "Punto de Interes [X="+x+", y=" + y + ", type=" + type +"]";
    }
}
