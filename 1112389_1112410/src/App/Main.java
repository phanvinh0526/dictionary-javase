/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Frame.FrameDictionary;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author vinh
 */
public class Main  {
    
    public static void main (String[] args){
        
       FrameDictionary fd = new FrameDictionary();
       
       
    }
    
    static private String getKeyOfSortedMap(Map<String,String> list, int index){
        Iterator<String> iter = list.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if(index == 0)
                return key;
            index--;
        }
        return null;
    }
}
