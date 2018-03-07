/**
 * Created by W7HP on 2017-12-06.
 */
public class Tmp {                              //Klasa pomocnicza przechowujaca dane dotyczace jacobianu
    double[][] jacobian;
    double[][] inversJacobian;
    double detJacobian;
    double[] dNdx;
    double[] dNdy;
    double[][] H;
    double[][] C;



    public double[][] getJacobian() {
        return jacobian;
    }

    public void setJacobian(double[][] jacobian) {
        this.jacobian = jacobian;
    }

    public double[][] getInversJacobian() {
        return inversJacobian;
    }

    public void setInversJacobian(double[][] inversJacobian) {
        this.inversJacobian = inversJacobian;
    }

    public void setDetJacobian(double detJacobian) {
        this.detJacobian = detJacobian;
    }

    public double getDetJacobian() {
        return detJacobian;
    }

    public double[] getdNdx() { return dNdx; }

    public void setdNdx(double[] dNdx) { this.dNdx = dNdx; }

    public double[] getdNdy() { return dNdy; }

    public void setdNdy(double[] dNdy) { this.dNdy = dNdy; }

    public double[][] getH() {
        return H;
    }

    public void setH(double[][] H) {
        this.H = H;
    }

    public double[][] getC() {
        return C;
    }

    public void setC(double[][] C) {
        this.C = C;
    }

    public Tmp(){
        jacobian=new double[2][2];
        inversJacobian=new double[2][2];
        detJacobian=0;
        dNdx=new double[4];
        dNdy=new double[4];
        H=new double[4][4];
        C=new double[4][4];



    }
    public void reset(){
        this.detJacobian=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++) {
                this.H[i][j]=0;
                this.C[i][j]=0;
            }
            this.dNdx[i]=0;
            this.dNdy[i]=0;
        }

        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++) {
               this.jacobian[i][j]=0;
               this.inversJacobian[i][j]=0;
            }

        }
    }
    public void show(){
        System.out.println("Jacobian:");
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++) {
                System.out.print(jacobian[i][j]+"\t");
            }
            System.out.println("\n");
        }
        System.out.println("Jacobian^-1:");
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++) {
                System.out.print(inversJacobian[i][j]+"\t");
            }
            System.out.println("\n");
        }
        System.out.println("detJacobian:");
        System.out.println(detJacobian);
        System.out.println("dNdx: ");for (int i=0;i<4;i++) System.out.print(dNdx[i]+" ");
        System.out.println("\ndNdy: ");for (int i=0;i<4;i++) System.out.print(dNdy[i]+" ");
        System.out.println("\nH lokalne:");
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++) {
                System.out.print(H[i][j]+"\t\t");
            }
            System.out.println("\n");
        }
        System.out.println("C lokalne:");
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++) {
                System.out.print(C[i][j]+"\t\t");
            }
            System.out.println("\n");
        }
    }
}
