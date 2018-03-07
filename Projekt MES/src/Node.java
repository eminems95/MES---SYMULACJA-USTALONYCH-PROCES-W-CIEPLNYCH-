/**
 * Created by W7HP on 2017-11-07.
 */
public class Node {
    double x;          //Współrzedna X węzła
    double y;          //Współrzedna Y węzła
    double temp;    //Temperatura w węźle
    int id;         //Identyfikator węzła
    int status;     //Status węzła 1-posiada warunek brzegowy 0-nie posiada warunku brzegowego

    public Node(int id,double x,double y,int status,int temp){
        this.id=id;
        this.status=status;
        this.temp=temp;
        this.x=x;
        this.y=y;

    }

    public String getXY() {
        return String.format("(%.2f,%.2f)",x,y);
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }




}
