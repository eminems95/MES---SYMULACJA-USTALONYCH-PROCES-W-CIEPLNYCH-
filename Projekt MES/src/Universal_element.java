import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * Created by W7HP on 2017-11-23.
 */
public class Universal_element {
    double[][] derativ_of_eta;
    double[][] derativ_of_ksi;
    double[][] shape_function;
    double[][][] shape_function2D;

    double point=1.0/Math.sqrt(3);

    public Universal_element(){
        derativ_of_eta=new double[4][4];        //  dN/deta
        derativ_of_ksi=new double[4][4];        //  dN/dksi
        shape_function=new double[4][4];        //  dN
        shape_function2D=new double[4][2][4];

        for(int i=0;i<4;i++){
            shape_function[i][0]=0.25*(1-point)*(1-point);
            shape_function[i][1]=0.25*(1+point)*(1-point);
            shape_function[i][2]=0.25*(1+point)*(1+point);
            shape_function[i][3]=0.25*(1-point)*(1+point);
        }




        //////
        derativ_of_eta[0][0]=-0.25*(1+point);
        derativ_of_eta[0][1]=-0.25*(1-point);
        derativ_of_eta[0][2]=0.25*(1-point);
        derativ_of_eta[0][3]=0.25*(1+point);

        derativ_of_eta[1][0]=-0.25*(1-point);
        derativ_of_eta[1][1]=-0.25*(1+point);
        derativ_of_eta[1][2]=0.25*(1+point);
        derativ_of_eta[1][3]=0.25*(1-point);

        derativ_of_eta[2][0]=-0.25*(1-point);
        derativ_of_eta[2][1]=-0.25*(1+point);
        derativ_of_eta[2][2]=0.25*(1+point);
        derativ_of_eta[2][3]=0.25*(1-point);

        derativ_of_eta[3][0]=-0.25*(1+point);
        derativ_of_eta[3][1]=-0.25*(1-point);
        derativ_of_eta[3][2]=0.25*(1-point);
        derativ_of_eta[3][3]=0.25*(1+point);

        //////
        derativ_of_ksi[0][0]=-0.25*(1+point);
        derativ_of_ksi[0][1]=0.25*(1+point);
        derativ_of_ksi[0][2]=0.25*(1-point);
        derativ_of_ksi[0][3]=-0.25*(1-point);

        derativ_of_ksi[1][0]=-0.25*(1+point);
        derativ_of_ksi[1][1]=0.25*(1+point);
        derativ_of_ksi[1][2]=0.25*(1-point);
        derativ_of_ksi[1][3]=-0.25*(1-point);

        derativ_of_ksi[2][0]=-0.25*(1-point);
        derativ_of_ksi[2][1]=0.25*(1-point);
        derativ_of_ksi[2][2]=0.25*(1+point);
        derativ_of_ksi[2][3]=-0.25*(1+point);

        derativ_of_ksi[3][0]=-0.25*(1-point);
        derativ_of_ksi[3][1]=0.25*(1-point);
        derativ_of_ksi[3][2]=0.25*(1+point);
        derativ_of_ksi[3][3]=-0.25*(1+point);

        //////
        shape_function[0][0]=0.25*(1+point)*(1+point);
        shape_function[0][1]=0.25*(1-point)*(1+point);
        shape_function[0][2]=0.25*(1-point)*(1-point);
        shape_function[0][3]=0.25*(1+point)*(1-point);

        shape_function[1][0]=0.25*(1-point)*(1+point);
        shape_function[1][1]=0.25*(1+point)*(1+point);
        shape_function[1][2]=0.25*(1+point)*(1-point);
        shape_function[1][3]=0.25*(1-point)*(1-point);

        shape_function[2][0]=0.25*(1-point)*(1-point);
        shape_function[2][1]=0.25*(1+point)*(1-point);
        shape_function[2][2]=0.25*(1+point)*(1+point);
        shape_function[2][3]=0.25*(1-point)*(1+point);

        shape_function[3][0]=0.25*(1+point)*(1-point);
        shape_function[3][1]=0.25*(1-point)*(1-point);
        shape_function[3][2]=0.25*(1-point)*(1+point);
        shape_function[3][3]=0.25*(1+point)*(1+point);
        ///////

        shape_function2D[0][0][0]=0.25*(1+point)*(1+1);
        shape_function2D[0][0][1]=0.25*(1-point)*(1+1);
        shape_function2D[0][0][2]=0.25*(1-point)*(1-1);
        shape_function2D[0][0][3]=0.25*(1+point)*(1-1);

        shape_function2D[0][1][0]=0.25*(1-point)*(1+1);
        shape_function2D[0][1][1]=0.25*(1+point)*(1+1);
        shape_function2D[0][1][2]=0.25*(1+point)*(1-1);
        shape_function2D[0][1][3]=0.25*(1-point)*(1-1);

        shape_function2D[1][0][0]=0.25*(1-1)*(1+point);
        shape_function2D[1][0][1]=0.25*(1+1)*(1+point);
        shape_function2D[1][0][2]=0.25*(1+1)*(1-point);
        shape_function2D[1][0][3]=0.25*(1-1)*(1-point);

        shape_function2D[1][1][0]=0.25*(1-1)*(1-point);
        shape_function2D[1][1][1]=0.25*(1+1)*(1-point);
        shape_function2D[1][1][2]=0.25*(1+1)*(1+point);
        shape_function2D[1][1][3]=0.25*(1-1)*(1+point);

        shape_function2D[2][0][0]=0.25*(1-point)*(1-1);
        shape_function2D[2][0][1]=0.25*(1+point)*(1-1);
        shape_function2D[2][0][2]=0.25*(1+point)*(1+1);
        shape_function2D[2][0][3]=0.25*(1-point)*(1+1);

        shape_function2D[2][1][0]=0.25*(1+point)*(1-1);
        shape_function2D[2][1][1]=0.25*(1-point)*(1-1);
        shape_function2D[2][1][2]=0.25*(1-point)*(1+1);
        shape_function2D[2][1][3]=0.25*(1+point)*(1+1);

        shape_function2D[3][0][0]=0.25*(1+1)*(1-point);
        shape_function2D[3][0][1]=0.25*(1-1)*(1-point);
        shape_function2D[3][0][2]=0.25*(1-1)*(1+point);
        shape_function2D[3][0][3]=0.25*(1+1)*(1+point);

        shape_function2D[3][1][0]=0.25*(1+1)*(1+point);
        shape_function2D[3][1][1]=0.25*(1-1)*(1+point);
        shape_function2D[3][1][2]=0.25*(1-1)*(1-point);
        shape_function2D[3][1][3]=0.25*(1+1)*(1-point);



        ///////
    }
    public void show(){
        System.out.println("Pochodne po eta");
        for (int j=0;j<4;j++){       // petla po wierszach
            for (int i=0;i<4;i++) {   //petla po kolumnach
                System.out.print(derativ_of_eta[j][i]+"\t");
                }
            System.out.print("\n");
            }

            System.out.println("Pochodne po ksi");
        for (int j=0;j<4;j++){       // petla po wierszach
            for (int i=0;i<4;i++) {   //petla po kolumnach
                System.out.print(derativ_of_ksi[j][i]+"\t");
            }
            System.out.print("\n");
        }

        System.out.println("Funkcje kształtu");
        for (int j=0;j<4;j++){       // petla po wierszach
            for (int i=0;i<4;i++) {   //petla po kolumnach
                System.out.print(shape_function[j][i]+"\t");
            }
            System.out.print("\n");
        }
    }
}
