/**
 * Created by W7HP on 2017-11-07.
 */
public class Grid {
    Node[] nods=new Node[GlobalData.nh];
    Element[] elements=new Element[GlobalData.ne];


    public Grid(){
        for(int i=0;i<GlobalData.nh;i++) {
            nods[i] = new Node(0, 0, 0, 0, 0);
        }
        for(int i=0;i<GlobalData.ne;i++)
            elements[i]=new Element(0,0,0,0,0,"1");


    }
    public Grid genGrid2D(){
        Grid Gr=new Grid();
        double dy=GlobalData.H/(GlobalData.nH-1);
        double dx=GlobalData.B/(GlobalData.nB-1);
        int ID=0;
        double x=0,y;
        //Nadanie węzłow
        for(int i=0;i<GlobalData.nB;i++){
            y=0;
            for(int j=0;j<GlobalData.nH;j++){
                ID++;
                Gr.nods[i+GlobalData.nB*j].id=ID;
                Gr.nods[i+GlobalData.nB*j].x=x;
                Gr.nods[i+GlobalData.nB*j].y=y;
                Gr.nods[i+GlobalData.nB*j].status=0;
                y=y+dy;

            }
            x=x+dx;

        }
        for(int i=GlobalData.nh-GlobalData.nB;i<GlobalData.nh;i++)
            Gr.nods[i].y=GlobalData.H;

        //Nadanie statusow
        //double delta=1.94;//((GlobalData.temBeginIn-GlobalData.temBeginOut)+2)/GlobalData.nB;
        //System.out.println(delta);
        int counter=0;
        for(int i=0;i<GlobalData.nh;i++){
            x=Gr.nods[i].x;
            y=Gr.nods[i].y;
           // if(i%GlobalData.nB == 0) {counter=0;}Gr.nods[i].temp=GlobalData.temBeginOut+delta*counter;counter++;
            Gr.nods[i].temp=21;
            //if(x>=GlobalData.B-0.00000001) Gr.nods[i].status=1; //Boczna powierzchnia
            if(x<=0.00000001) Gr.nods[i].status=1; //Boczna powerzchnia
            //if(y>=GlobalData.H-0.00000001) Gr.nods[i].status=1; //Gorna powierzchnia
            //if(y<=0.00000001) Gr.nods[i].status=1; //Dolna powierzchnia
        }

        //Nadanie elemtow
        int i1,i2,i3,i4;
        ID=0;
        for(int i=0;i<GlobalData.nB-1;i++){

            for(int j=0;j<GlobalData.nH-1;j++){
                i1=i*GlobalData.nH+j+1;
                i2=(i+1)*GlobalData.nH+j+1;
                i3=(i+1)*GlobalData.nH+j+1+1;
                i4=i*GlobalData.nH+j+1+1;
                ID++;
                Gr.elements[i+(GlobalData.nB-1)*j].ID=ID;
                Gr.elements[i+(GlobalData.nB-1)*j].nodeID[0]=i1;
                Gr.elements[i+(GlobalData.nB-1)*j].nodeID[1]=i2;
                Gr.elements[i+(GlobalData.nB-1)*j].nodeID[2]=i3;
                Gr.elements[i+(GlobalData.nB-1)*j].nodeID[3]=i4;

            }

        }
        Node tmpNodes[] =new Node[4];

        /*for (int i=0;i<GlobalData.ne;i++) {
            System.out.println("ID: "+Gr.elements[i].ID + "i: "+i);
            //System.out.println(Gr.nods[getNode(Gr.elements[i].nodeID[1]-1)].getXY());
            System.out.println(Gr.nods[Gr.elements[i].nodeID[2]-1].getXY());
            if(Gr.nods[Gr.elements[i].nodeID[1]-1].getY()<0.31 && Gr.nods[Gr.elements[i].nodeID[2]-1].getX()<0.31 ) Gr.elements[i].material="1";
             //else if(Gr.nods[Gr.elements[i].nodeID[0]].getX()<0.41 && Gr.nods[Gr.elements[i].nodeID[1]].getX()<0.41 && Gr.nods[Gr.elements[i].nodeID[2]].getX()<0.41 && Gr.nods[Gr.elements[i].nodeID[3]].getX()<0.41) Gr.elements[i].material="2";
             else Gr.elements[i].material="3";
            System.out.println("Materiał: "+Gr.elements[i].material);
            System.out.println();
             }*/


        return Gr;
    }

    public Node getNode(int id){
        Node node=null;
        for (Node n:nods) {
            if(n.id==id) node=n;
        }
        return node;
    }

    public void show(){
        System.out.println("\n\n  Współrzedne węzłów");
        for(int i=0;i<GlobalData.nh;i++){
            if(i%GlobalData.nB==0) System.out.println();
            System.out.format(nods[i].getXY()+" ");

        }
        System.out.println("\n\n  ID węzłów");
        for(int i=0;i<GlobalData.nh;i++){
            if(i%GlobalData.nB==0) System.out.println();
            System.out.print(nods[i].getId()+" ");

        }
        System.out.println("\n\n  Statusy węzłów");
        for(int i=0;i<GlobalData.nh;i++){
            if(i%GlobalData.nB==0) System.out.println();
            System.out.print(nods[i].getStatus()+" ");


        }

        System.out.println("\n\n  Elementy");
        for(int i=0;i<GlobalData.ne;i++){
            //if(i%GlobalData.nB==0) System.out.println();
            elements[i].show();

        }
        /*System.out.println("\n\n  Materiały");
        for (int i=0;i<elements.length;i++) {
            System.out.print(elements[i].material+" ");
        }*/

        System.out.println("\n\n  Materiały");
        for(int i=0;i<GlobalData.ne;i++){
            if(i%(GlobalData.nB-1)==0) System.out.println();

                System.out.print(elements[i].material+" ");



        }


    }

    public void showTemp(){
        //System.out.println("\n\n  Statusy węzłów");

        System.out.println("\n\n  Temperatura");

        for(int i=0;i<GlobalData.nh;i++){
            if(i%GlobalData.nB==0) System.out.println();

            System.out.format("%.2f\t",nods[i].temp);

        }System.out.println();
    }

    public Element[] getElement(){
        return this.elements;
    }

    public Node[] getNode(){
        return this.nods;
    }
}
