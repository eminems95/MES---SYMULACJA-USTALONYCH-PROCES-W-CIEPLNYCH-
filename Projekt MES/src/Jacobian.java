/**
 * Created by W7HP on 2017-11-23.
 */
public class Jacobian {
    Element element;
    int points_of_integrate;
    Universal_element universal_element;



    public Tmp calculate(Element element,Node[] nodes,int points_of_integrate,Universal_element universal_element,Tmp tmp)// double[][] jacobian,double[][] inversJacobian,double detJacobian)
    {
        this.element=element;
        this.points_of_integrate = points_of_integrate;
        this.universal_element = universal_element;

        double[][] array=new double[2][2];//tablica pomocnicza
        array[0][0] = universal_element.derativ_of_ksi[points_of_integrate][0] * nodes[0].getX()
                + universal_element.derativ_of_ksi[points_of_integrate][1] * nodes[1].getX()
                + universal_element.derativ_of_ksi[points_of_integrate][2] * nodes[2].getX()
                + universal_element.derativ_of_ksi[points_of_integrate][3] * nodes[3].getX();
        array[0][1] = universal_element.derativ_of_ksi[points_of_integrate][0] * nodes[0].getY()
                + universal_element.derativ_of_ksi[points_of_integrate][1] * nodes[1].getY()
                + universal_element.derativ_of_ksi[points_of_integrate][2] * nodes[2].getY()
                + universal_element.derativ_of_ksi[points_of_integrate][3] * nodes[3].getY();
        array[1][0] = universal_element.derativ_of_eta[points_of_integrate][0] * nodes[0].getX()
                + universal_element.derativ_of_eta[points_of_integrate][1] * nodes[1].getX()
                + universal_element.derativ_of_eta[points_of_integrate][2] * nodes[2].getX()
                + universal_element.derativ_of_eta[points_of_integrate][3] * nodes[3].getX();

        array[1][1] = universal_element.derativ_of_eta[points_of_integrate][0] * nodes[0].getY()
                + universal_element.derativ_of_eta[points_of_integrate][1] * nodes[1].getY()
                + universal_element.derativ_of_eta[points_of_integrate][2] * nodes[2].getY()
                + universal_element.derativ_of_eta[points_of_integrate][3] * nodes[3].getY();

        tmp.setJacobian(array);
        tmp.setDetJacobian(array[0][0] * array[1][1] - array[0][1] * array[1][0]);
        tmp.setInversJacobian(invers(array, points_of_integrate,tmp.getDetJacobian()));



        return tmp;
    }

    private double[][] invers(double[][] tab,int p,double det){
        double invers[][]=new double[2][2];
        invers[0][0]=tab[1][1]*1/det;
        invers[0][1]=-tab[0][1]*1/det;
        invers[1][0]=-tab[1][0]*1/det;
        invers[1][1]=tab[0][0]*1/det;
        return invers;
    }

    private double det(double array[][]){
       double wyznacznik=0;
       wyznacznik=array[0][0]*array[1][1]-array[0][1]*array[1][0];

       return wyznacznik;
    }


}
