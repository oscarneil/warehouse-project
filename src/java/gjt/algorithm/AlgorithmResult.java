package gjt.algorithm;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmResult{
    public ArrayList<ItemPath> paths = new ArrayList<>();
    public int index = 0;
    public String LEDcolor;
    public AlgorithmResult(String color){
        this.paths.clear();
        this.LEDcolor = color;
    }

    public void addPath(int from,int to,int RiNo, List<Integer> paths){
        ItemPath ip = new ItemPath(from, to, RiNo);
        ip.setPath(paths);
        this.paths.add(ip);
    }

    public boolean test(){
        return index< paths.size();
    }

    public boolean isLast(){
        return index+1 >= paths.size();
    }

    public ItemPath getCurrent(){
        //System.out.println(index + " / " + paths.size());
        if (!test()) return null;
        return paths.get(index);
    }

    public boolean next(){
        index++;
        if (index >= paths.size()){
            return false;
        }
        return true;
    }

}