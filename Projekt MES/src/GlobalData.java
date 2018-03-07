import java.io.File;
import java.util.Scanner;

/**
 * Created by W7HP on 2017-11-07.
 */
public   class GlobalData {
    static double temBeginOut=0,temBeginIn=0;    //Poczatkowa temperatura
    static double time;        //Czas procesu
    static double dTime;       //Poczatkowy przyrost czasu
    static double Tau=0;         //Czas bierzacy
    static double temSurr;     //Temperatura otoczenia
    static double alfa1,alfa2,alfa3;        //Współczyni wymiany ciepła
    static double H;           //Wysokość przekroju
    static double B;           //Szerokość przekroju
    static int nH;             //Liczba węzłów na wyskokość
    static int nB;             //Liczba węzłów na szerokość
    static double C1,C2,C3;           //Pojemność cieplna
    static double K1,K2,K3;           //Współczynik przewodzenia ciepła
    static double R1,R2,R3;           //Gęstość


    static int nh;              //Liczba wezłów nB*nH
    static int ne;              //Liczba elementow (nB-1)*(nH-1)
    public void loadData() {
        try {
            Scanner scaner = new Scanner(new File("data.txt"));
            H=scaner.nextDouble(); scaner.nextLine();
            B=scaner.nextDouble(); scaner.nextLine();
            nH=scaner.nextInt(); scaner.nextLine();
            nB=scaner.nextInt();scaner.nextLine();
            time=scaner.nextDouble();scaner.nextLine();
            dTime=scaner.nextDouble();scaner.nextLine();
            temBeginOut=scaner.nextDouble();scaner.nextLine();
            temBeginIn=scaner.nextDouble();scaner.nextLine();
            temSurr=scaner.nextDouble();scaner.nextLine();scaner.nextLine();

            alfa1=scaner.nextDouble();scaner.nextLine();
            C1=scaner.nextDouble();scaner.nextLine();
            R1=scaner.nextDouble();scaner.nextLine();
            K1=scaner.nextDouble();scaner.nextLine();scaner.nextLine();

            alfa2=scaner.nextDouble();scaner.nextLine();
            C2=scaner.nextDouble();scaner.nextLine();
            R2=scaner.nextDouble();scaner.nextLine();
            K2=scaner.nextDouble();scaner.nextLine();scaner.nextLine();

            alfa3=scaner.nextDouble();scaner.nextLine();
            C3=scaner.nextDouble();scaner.nextLine();
            R3=scaner.nextDouble();scaner.nextLine();
            K3=scaner.nextDouble();

            scaner.close();

            ne=(nB-1)*(nH-1);
            nh=nB*nH;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    public void showData(){
        System.out.println("    Wczytane dane");
        System.out.println("Wysokość przekroju H: "+H);
        System.out.println("Szerokość przekroju B: "+B);
        System.out.println("Liczba węzłów w wyskokości  nH: "+nH);
        System.out.println("Liczba węzłów w szerokości nB: "+nB);
        System.out.println("Czas procesu time: "+time);
        System.out.println("Poczatkowy przyrost czasu dTime: "+dTime);
        System.out.println("Poczatkowa temperatura temBeginOut: "+temBeginOut);
        System.out.println("Poczatkowa temperatura temBeginIn: "+temBeginIn);
        System.out.println("Temperatura otoczenia temSurr: "+temSurr);
        System.out.println("Współczyni wymiany ciepła alfa1: "+alfa1);
        System.out.println("Pojemność cieplna C1: "+C1);
        System.out.println("Współczynik przewodzenia ciepła K1: "+K1);
        System.out.println("Gęstość R2: "+R2);
        System.out.println("Współczyni wymiany ciepła alfa2: "+alfa2);
        System.out.println("Pojemność cieplna C2: "+C2);
        System.out.println("Współczynik przewodzenia ciepła K2: "+K2);
        System.out.println("Gęstość R1: "+R3);
        System.out.println("Współczyni wymiany ciepła alfa1: "+alfa3);
        System.out.println("Pojemność cieplna C1: "+C3);
        System.out.println("Współczynik przewodzenia ciepła K1: "+K3);
        System.out.println("Gęstość R1: "+R3);
    }
}
