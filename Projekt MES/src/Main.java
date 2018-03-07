
public class Main {

    static public double funkcja(double x, double y) {
        return 2 * x * x * y * y + 6 * x + 5;
    }

    static GlobalData globalData;

    public static void main(String[] args) {
        globalData = new GlobalData();
        globalData.loadData();
        //globalData.showData();

        Grid grid = new Grid();
        grid = grid.genGrid2D();
        //grid.show();

        Universal_element universal_element = new Universal_element();
        //universal_element.show();

        //Zdeklaracja zmiennych
        double constC=0,constK=0,constR=0,constalfa=0;
        Jacobian jacobian = new Jacobian();
        Node[] tmpNodes = new Node[4];
        double[][] Hglob = new double[GlobalData.nh][GlobalData.nh];      //Macierz końcowa H dla całego ukladu
        double[] Pglob = new double[GlobalData.nh];           //Wektor obiążeń P dla całego ukladu
        double[][] H = new double[4][4];          //Macierz H dla pojedynczego elementu
        double[][] HC = new double[4][4];         //Macierz H+C dla pojedynczego elementu
        double[][] C = new double[4][4];          //Macierz C dla pojedynczego elementu
        double[] P = new double[4];               //Wektor obiążeń P dla pojedynczego elementu
        double[] PC = new double[4];              //Wektor końcowy P^ dla pojedynczego elementu
        double[] T0 = new double[4];              //Wektor temperatur w czasie 0
        double[] T1 = new double[GlobalData.nh];              //Wektor temperatur w czasie +dTime
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                H[j][k] = 0;
                C[j][k] = 0;
            }
        }
        for (int j = 0; j < GlobalData.nh; j++) {
            for (int k = 0; k < GlobalData.nh; k++) {
                Hglob[j][k] = 0;
                Pglob[j] = 0;
            }
        }
        Tmp tmpData = new Tmp();
        double detTmp = 0;    //wyznacznik dla elementu z warunku brzegowego
        double Ni = 0, Nn = 0, Pn = 0;
        int id = 0;

        //////////TEST////////////////
        double[][] HH = new double[GlobalData.nh][GlobalData.nh];
        double[][] CC = new double[GlobalData.nh][GlobalData.nh];
        /////

        //Nadanie statsow powierzchni kontaktowych
        for (Element element : grid.getElement()) {
            for (int i = 0; i < 4; i++) {
                tmpNodes[i] = grid.getNode(element.nodeID[i]); //przpisanie wezlow danego elementu
                //System.out.print(tmpNodes[i].getXY()+"--");

            }

            if(tmpNodes[1].getX()<0.11 && tmpNodes[2].getX()<0.11) element.material="2";
            if((tmpNodes[1].getX()>0.11 && tmpNodes[1].getX()<0.41) && (tmpNodes[2].getX()>0.11 && tmpNodes[2].getX()<0.41)) element.material="1";
            if(tmpNodes[1].getX()>0.41  && tmpNodes[2].getX()>0.41) element.material="3";

            if (tmpNodes[0].status == 1 && tmpNodes[1].status == 1) {
                element.nContactSurface++;
                if (element.contactSurface[0] == 0) element.contactSurface[0] = 0;
                else element.contactSurface[1] = 0;
            }
            if (tmpNodes[1].status == 1 && tmpNodes[2].status == 1) {
                element.nContactSurface++;
                if (element.contactSurface[0] == 0) element.contactSurface[0] = 1;
                else element.contactSurface[1] = 1;
            }
            if (tmpNodes[2].status == 1 && tmpNodes[3].status == 1) {
                element.nContactSurface++;
                if (element.contactSurface[0] == 0) element.contactSurface[0] = 2;
                else element.contactSurface[1] = 2;
            }
            if (tmpNodes[3].status == 1 && tmpNodes[0].status == 1) {
                element.nContactSurface++;
                if (element.contactSurface[0] == 0) element.contactSurface[0] = 3;
                else element.contactSurface[1] = 3;
            }

        }
        ///////////////////////////////////////////


        for (int t = 0; t < GlobalData.time; t += GlobalData.dTime) {      //petla po czasie
            for (int j = 0; j < GlobalData.nh; j++) {
                for (int k = 0; k < GlobalData.nh; k++) {
                    HH[j][k] = 0;
                    CC[j][k] = 0;
                    Hglob[j][k] = 0;
                    Pglob[j] = 0;
                }
            }  //zerowanie macierzy
            for (Element element : grid.getElement()) {  //petla po elementach
                if(element.material.equals("1")){constC=GlobalData.C1;constK=GlobalData.K1;constR=GlobalData.R1;constalfa=GlobalData.alfa1;};
                if(element.material.equals("2")){constC=GlobalData.C2;constK=GlobalData.K2;constR=GlobalData.R2;constalfa=GlobalData.alfa2;};
                if(element.material.equals("3")){constC=GlobalData.C3;constK=GlobalData.K3;constR=GlobalData.R3;constalfa=GlobalData.alfa3;};
                //System.out.print("--------------------Id elementu: "+element.ID+"\t");

                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        H[j][k] = 0;
                        C[j][k] = 0;
                        P[k] = 0;
                        HC[j][k] = 0;
                        T0[k] = 0;
                        PC[k] = 0;
                    }
                }  //zerowanie macierzy H i C;

                for (int i = 0; i < 4; i++) {
                    tmpNodes[i] = grid.getNode(element.nodeID[i]); //przpisanie wezlow danego elementu

                    //System.out.print(tmpNodes[i].getXY()+"--");

                }


                //////TEMPERATURY////////
                for (int i = 0; i < 4; i++) {
                    for (int k = 0; k < 4; k++) {

                        T0[i] += universal_element.shape_function[i][k] * tmpNodes[k].getTemp();
                        //System.out.println("F kszt " + i + " " + k + " " + element.ID + " " + universal_element.shape_function[i][k] + "       " + tmpNodes[k].getTemp());//?
                    }
                    //System.out.println("Tmpertura " + i + " " + element.ID + " " + T0[i]);//?
                }
                /////////////
                // System.out.print("----------------------\n");

                for (int i = 0; i < 4; i++) {                   //petla po pkt calkowania
                    tmpData.reset();


                    jacobian.calculate(element, tmpNodes, i, universal_element, tmpData); //wyliczenie jacobianu/ jacobian^1 oraz detJacobian

                    for (int j = 0; j < 4; j++) {
                        tmpData.dNdx[j] = (tmpData.getInversJacobian()[0][0] * universal_element.derativ_of_ksi[i][j] - tmpData.getInversJacobian()[0][1] * universal_element.derativ_of_eta[i][j]);
                        tmpData.dNdy[j] = (-tmpData.getInversJacobian()[1][0] * universal_element.derativ_of_ksi[i][j] + tmpData.getInversJacobian()[1][1] * universal_element.derativ_of_eta[i][j]);
                    }

                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            tmpData.H[j][k] = tmpData.getdNdx()[j] * tmpData.getdNdx()[k];
                            tmpData.H[j][k] += tmpData.getdNdy()[j] * tmpData.getdNdy()[k];
                            tmpData.H[j][k] *= tmpData.getDetJacobian() * constK;
                            H[j][k] += tmpData.getH()[j][k];

                            tmpData.C[j][k] = constC * constR * universal_element.shape_function[i][j] * universal_element.shape_function[i][k] * tmpData.getDetJacobian();
                            //?System.out.println(GlobalData.C +"***"+GlobalData.R+"***"+universal_element.shape_function[i][j]+"***"+universal_element.shape_function[i][k]+"***"+tmpData.getDetJacobian());
                            //?System.out.println(tmpData.C[j][k]);
                            C[j][k] += tmpData.getC()[j][k];
                            //C[j][k]/=GlobalData.dTime;          //Pomnozenie macierzy C prze 1/dTime ?? uzwzglednione później


                        }


                    }

                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            //System.out.println("C["+j+"]["+k+"]"+ tmpData.C[j][k]);
                            //System.out.println("bez war: "+PC[j]);
                            PC[j] += tmpData.C[j][k] / GlobalData.dTime * T0[i];

                            //System.out.println("    "+C[j][k]+"    /   "+GlobalData.dTime+"    *   "+T0[i]+"    |   "+ PC[j]);
                        }
                    }
                    //tmpData.show();

                }

                double pom[] = new double[4];
                ////War brzegowy////
                for (int j = 0; j < element.nContactSurface; j++) {
                    //System.out.println(element.nContactSurface);
                    id = element.contactSurface[j];

                    switch (id) {
                        case 0:
                            detTmp = Math.sqrt(Math.pow(tmpNodes[1].getX() - tmpNodes[0].getX(), 2) + Math.pow(tmpNodes[1].getY() - tmpNodes[0].getY(), 2)) / 2;
                            break;
                        case 1:
                            detTmp = Math.sqrt(Math.pow(tmpNodes[2].getX() - tmpNodes[1].getX(), 2) + Math.pow(tmpNodes[2].getY() - tmpNodes[1].getY(), 2)) / 2;
                            break;
                        case 2:
                            detTmp = Math.sqrt(Math.pow(tmpNodes[3].getX() - tmpNodes[2].getX(), 2) + Math.pow(tmpNodes[3].getY() - tmpNodes[2].getY(), 2)) / 2;
                            break;
                        case 3:
                            detTmp = Math.sqrt(Math.pow(tmpNodes[0].getX() - tmpNodes[3].getX(), 2) + Math.pow(tmpNodes[0].getY() - tmpNodes[3].getY(), 2)) / 2;
                            break;
                    }
                    for (int k = 0; k < 2; k++) {   //pkt calkowania
                        for (int l = 0; l < 4; l++) {   //petla po wierszach
                            for (int m = 0; m < 4; m++) {  //petla po kolumnach

                                Ni = universal_element.shape_function2D[id][k][m];
                                Nn = universal_element.shape_function2D[id][k][l];
                                H[l][m] += constalfa * Nn * Ni * detTmp;            //Dodanie warunku brzegowego
                            }

                            Pn = constalfa * GlobalData.temSurr * Nn * detTmp;    //?minus?
                            pom[l] += Pn;
                            P[l] += Pn;

                            //System.out.println("asd "+GlobalData.alfa+"-/-"+GlobalData.temSurr+"-/-"+Nn+"-/-"+detTmp);

                        }
                    }
                    //for(int z=0;z<P.length;z++)System.out.println("war "+P[z]);
                }
                //for(int l=0;l<4;l++){   System.out.println("pom "+pom[l]);   }
                for (int j = 0; j < 4; j++) {                  //Utworzenie macierzy H z ^ dla 1 elem
                    for (int k = 0; k < 4; k++) {
                        //System.out.println(H[j][k]+"+"+C[j][k]);
                        HC[j][k] = H[j][k] + C[j][k] / GlobalData.dTime;

                    }
                }


                for (int j = 0; j < 4; j++) {                  //Utworzenie wektora P z ^ dla 1 elem
                    for (int k = 0; k < 4; k++) {

                        //PC[j]+=C[j][k]/GlobalData.dTime*T0[j];
                        //System.out.println("    "+C[j][k]+"    /   "+GlobalData.dTime+"    *   "+T0[k]);
                    }
                    //System.out.println("bez war: "+PC[j]);
                    PC[j] += P[j];
                    //System.out.println("war: "+P[j]);

                }
                //////////////////////////

                ///////AGREGACJA////////

                /*for (int i=0;i<4;i++){
                    for (int j=0;j<4;j++) {
                        Hglob[element.nodeID[0]][element.nodeID[0]]+=H[i][j];
                    }

                }*/

                Hglob[element.nodeID[0] - 1][element.nodeID[0] - 1] += HC[0][0];
                Hglob[element.nodeID[0] - 1][element.nodeID[1] - 1] += HC[0][1];
                Hglob[element.nodeID[0] - 1][element.nodeID[2] - 1] += HC[0][2];
                Hglob[element.nodeID[0] - 1][element.nodeID[3] - 1] += HC[0][3];
                Hglob[element.nodeID[1] - 1][element.nodeID[0] - 1] += HC[1][0];
                Hglob[element.nodeID[1] - 1][element.nodeID[1] - 1] += HC[1][1];
                Hglob[element.nodeID[1] - 1][element.nodeID[2] - 1] += HC[1][2];
                Hglob[element.nodeID[1] - 1][element.nodeID[3] - 1] += HC[1][3];
                Hglob[element.nodeID[2] - 1][element.nodeID[0] - 1] += HC[2][0];
                Hglob[element.nodeID[2] - 1][element.nodeID[1] - 1] += HC[2][1];
                Hglob[element.nodeID[2] - 1][element.nodeID[2] - 1] += HC[2][2];
                Hglob[element.nodeID[2] - 1][element.nodeID[3] - 1] += HC[2][3];
                Hglob[element.nodeID[3] - 1][element.nodeID[0] - 1] += HC[3][0];
                Hglob[element.nodeID[3] - 1][element.nodeID[1] - 1] += HC[3][1];
                Hglob[element.nodeID[3] - 1][element.nodeID[2] - 1] += HC[3][2];
                Hglob[element.nodeID[3] - 1][element.nodeID[3] - 1] += HC[3][3];

                Pglob[element.nodeID[0] - 1] += PC[0];
                Pglob[element.nodeID[1] - 1] += PC[1];
                Pglob[element.nodeID[2] - 1] += PC[2];
                Pglob[element.nodeID[3] - 1] += PC[3];

                //////TEST/////
                HH[element.nodeID[0] - 1][element.nodeID[0] - 1] += H[0][0];
                HH[element.nodeID[0] - 1][element.nodeID[1] - 1] += H[0][1];
                HH[element.nodeID[0] - 1][element.nodeID[2] - 1] += H[0][2];
                HH[element.nodeID[0] - 1][element.nodeID[3] - 1] += H[0][3];
                HH[element.nodeID[1] - 1][element.nodeID[0] - 1] += H[1][0];
                HH[element.nodeID[1] - 1][element.nodeID[1] - 1] += H[1][1];
                HH[element.nodeID[1] - 1][element.nodeID[2] - 1] += H[1][2];
                HH[element.nodeID[1] - 1][element.nodeID[3] - 1] += H[1][3];
                HH[element.nodeID[2] - 1][element.nodeID[0] - 1] += H[2][0];
                HH[element.nodeID[2] - 1][element.nodeID[1] - 1] += H[2][1];
                HH[element.nodeID[2] - 1][element.nodeID[2] - 1] += H[2][2];
                HH[element.nodeID[2] - 1][element.nodeID[3] - 1] += H[2][3];
                HH[element.nodeID[3] - 1][element.nodeID[0] - 1] += H[3][0];
                HH[element.nodeID[3] - 1][element.nodeID[1] - 1] += H[3][1];
                HH[element.nodeID[3] - 1][element.nodeID[2] - 1] += H[3][2];
                HH[element.nodeID[3] - 1][element.nodeID[3] - 1] += H[3][3];

                CC[element.nodeID[0] - 1][element.nodeID[0] - 1] += C[0][0];
                CC[element.nodeID[0] - 1][element.nodeID[1] - 1] += C[0][1];
                CC[element.nodeID[0] - 1][element.nodeID[2] - 1] += C[0][2];
                CC[element.nodeID[0] - 1][element.nodeID[3] - 1] += C[0][3];
                CC[element.nodeID[1] - 1][element.nodeID[0] - 1] += C[1][0];
                CC[element.nodeID[1] - 1][element.nodeID[1] - 1] += C[1][1];
                CC[element.nodeID[1] - 1][element.nodeID[2] - 1] += C[1][2];
                CC[element.nodeID[1] - 1][element.nodeID[3] - 1] += C[1][3];
                CC[element.nodeID[2] - 1][element.nodeID[0] - 1] += C[2][0];
                CC[element.nodeID[2] - 1][element.nodeID[1] - 1] += C[2][1];
                CC[element.nodeID[2] - 1][element.nodeID[2] - 1] += C[2][2];
                CC[element.nodeID[2] - 1][element.nodeID[3] - 1] += C[2][3];
                CC[element.nodeID[3] - 1][element.nodeID[0] - 1] += C[3][0];
                CC[element.nodeID[3] - 1][element.nodeID[1] - 1] += C[3][1];
                CC[element.nodeID[3] - 1][element.nodeID[2] - 1] += C[3][2];
                CC[element.nodeID[3] - 1][element.nodeID[3] - 1] += C[3][3];
                //////////////

                /////////////////////////
                /////WYPISYWANIE////////
              /*  System.out.println("\n\nH:");
                for (int i=0;i<4;i++){
                    for (int j=0;j<4;j++) {
                        System.out.print(H[i][j]+"\t\t");
                    }
                    System.out.println("\n");
                }
                System.out.println("\n\nC:");
                for (int i=0;i<4;i++){
                    for (int j=0;j<4;j++) {
                        System.out.print(C[i][j]+"\t\t");
                    }
                    System.out.println("\n");
                }
                System.out.println("\n\nHC:");
                for (int i=0;i<4;i++){
                    for (int j=0;j<4;j++) {
                        System.out.print(HC[i][j]+"\t\t");
                    }
                    System.out.println("\n");
                }
                System.out.println("\n\nP:");
                for (int j=0;j<4;j++) {
                    System.out.print(P[j]+"\t\t");
                }
                System.out.println("\n");



                System.out.println("\n");*/
                /////////////////////////


                //System.out.print("--------------------------------------------------------------------------------------------------------\n");


            }

            //////////////////LICZERNIE T1/////////////// ??????????????????????????????????????
            GaussianElimination gaussianElimination = new GaussianElimination();
        /*System.out.println("\n\nHH_GLOB:");
        for (int i=0;i<GlobalData.nh;i++){
            for (int j=0;j<GlobalData.nh;j++) {
                System.out.printf(Hglob[i][j]+"\t\t");
            }
            System.out.println("\n");
        }*/

       /* System.out.println("\n\nP_GLOB:");
        for (int j=0;j<GlobalData.nh;j++) {
            System.out.print(Pglob[j]+"\t\t");
        }
        System.out.println("\n");*/


            T1 = gaussianElimination.lsolve(Hglob, Pglob);
            System.out.println("--------------------------------------------------------------------------");
            System.out.print("\t\t\t\tRozkład temperatur po czasie: " + (t+GlobalData.dTime));

            /* for (int i = 0; i < GlobalData.nh; i++) {
                if (i % GlobalData.nB == 0) System.out.println();
                System.out.print(T1[i] + " ");

            }*/
            for (Node n : grid.getNode()) {
                n.setTemp(T1[n.getId() - 1]);
            }
            grid.showTemp();
            System.out.format("\n\t\tTemperatura maksymalna: %.2f\t\t\t\t",max(T1));
            System.out.format("\t\tTemperatura minimalna: %.2f\t",min(T1));



            System.out.println();

            /////////////////////////////////////
            /////////////////////WYPISANIE//////////////
            grid.show();

            //////////////////////

            /////////////////INTERPOLACJA TEMPERATUR/////////////////////

        }
    } //!kroki czsowe


    public static double max(double[] tab) {
        double max = 0;
        for (int i = 1; i < tab.length; i++) //wczytanie pozostałych n-1 liczb
        {

            if(max<tab[i])
                max = tab[i];
        }
        return max;
    }

    public static double min(double[] tab) {
        double min=1000000;
        for (int i = 1; i < tab.length; i++) //wczytanie pozostałych n-1 liczb
        {
            if(min>tab[i])
                min = tab[i];
        }
        return min;
    }
}
//grid.getNode(20).getXY() //zwraca wezel 20

//PYTANIA
//1) Czy maceirz H w przykladzei jest razem z warunkiem brzegowym
//2) Czy ma pan błąd w macierzy H +C/dt