/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Frame.FrameDictionary;
import Model.DictAbstract;
import Model.EnViDict;
import Model.ViEnDict;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author vinh
 */
public class ModelDictionary implements ListModel{

    FrameDictionary frameDictionary;
    ViEnDict viEnDict = new ViEnDict("src/Resources/Viet_Anh.xml");
    EnViDict enViDict = new EnViDict("src/Resources/Anh_Viet.xml");
    
    public ModelDictionary(FrameDictionary frameDictionary){
        this.frameDictionary = frameDictionary;
    }

    public ModelDictionary() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //load dữ liệu
    public void loadData(){
       enViDict.loadFile(frameDictionary, 0, 50);
       viEnDict.loadFile(frameDictionary, 50, 50);
    }
  
    
    @Override
    public int getSize() {
        return getDict().size();
    }

    public DictAbstract getDict(){
        if(frameDictionary.isEnViSelected())
            return enViDict;
        else 
            return viEnDict;
    }
    
    //Lấy nghĩa của từ
    public String getMeanFromWord(String word){
        return getDict().getValueFromKey(word);
    }
    
    //Lấy nghĩa từ index
    public String getMeanFromIndex(int index){
        return getDict().getValueFromIndex(index);
    }
    
    //Lay phan tu tai
    @Override
    public Object getElementAt(int index) {
        return getDict().getKeyElementAt(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void insertAnhVietVoca(String word, String meaning) {
        enViDict.writeFile(word, meaning);
        
    }

    public void insertVietAnhVoca(String word, String meaning) {
        viEnDict.writeFile(word, meaning);
        
    }
    
    
}
