package gjt.algorithm;

import java.util.ArrayList;
import java.util.List;

public class ItemPath{
    public int from; // org path
    public int to;   // the item we want
    public int RiNo; // item no
    public List<Integer> paths = new ArrayList<>();
    public ItemPath(int from,int to,int RiNo){
        this.from = from;
        this.to = to;
        this.RiNo = RiNo;
        //System.out.println("+ "+from+" - "+to);
    }
    public void setPath( List<Integer> p){
        this.paths = p;
            /*for (Integer i:this.paths){
                System.out.print(i + " >");
            }
            System.out.println();*/
    }
}