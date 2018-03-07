/**
 * Created by W7HP on 2017-11-07.
 */
public class Element {

    int[] nodeID=new int[4];
    int ID;
    String material;

    int nContactSurface;
    int[] contactSurface;



    public Element(int n1,int n2,int n3,int n4,int ID,String material){
        this.nodeID[0]=n1;
        this.nodeID[1]=n2;
        this.nodeID[2]=n3;
        this.nodeID[3]=n4;
        nContactSurface=0;
        contactSurface=new int[2];
        this.ID=ID;
        this.material=material;




    }

    public void show(){
        System.out.println("Element: "+ ID);
        System.out.println(nodeID[3]+"  "+nodeID[2]);
        System.out.println(nodeID[0]+"  "+nodeID[1]);

        System.out.println("Liczba powierzchni kontaktowych: " + nContactSurface);
        for(int i=0;i<2;i++)  System.out.println((i+1)+". powierzchnia: " +contactSurface[i]);



    }


}
