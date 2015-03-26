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
import Model.WordObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author vinh
 */
public class ModelDictionary implements ListModel{

    // URL
    private final String URL                = "src/Resources/";
    private final String viEn_URL           = URL+"Viet_Anh.xml";
    private final String enVi_URL           = URL+"Anh_Viet.xml";
    private final String lastWord_URL       = URL+"LastWordlist.txt";
    private final String favouriteWord_URL  = URL+"FavouriteWordlist.txt";
    
    private FrameDictionary frameDictionary;
    private ViEnDict viEnDict = new ViEnDict(viEn_URL);
    private EnViDict enViDict = new EnViDict(enVi_URL);
    private ArrayList<String> listLastWord = new ArrayList<>();
    private ArrayList<String> listFavouriteWord = new ArrayList<>();
    private ArrayList<WordObject> enViDataAppanded = new ArrayList<>();
    private ArrayList<WordObject> viEnDataAppanded = new ArrayList<>();
    
    public String getViEn_URL(){
        return this.viEn_URL;
    }
    public String getEnVi_URL(){
        return this.enVi_URL;
    }
    public ArrayList<WordObject> getEnViDataAppanded(){
        return enViDataAppanded;
    }
    public ArrayList<WordObject> getViEnDataAppanded(){
        return viEnDataAppanded;
    }
    public ModelDictionary(FrameDictionary frameDictionary){
        this.frameDictionary = frameDictionary;
        
    }

    public ModelDictionary() {
        
    }
    
    //load dữ liệu dictionary
    public void loadData(){
        getEnViDict().loadFile(frameDictionary, 0, 50);
        getViEnDict().loadFile(frameDictionary, 50, 50);
       
    }
    //write dữ liệu dictionary
    public void appendData(){
        if(enViDataAppanded.size()>0)
            getEnViDict().appendFile(enViDataAppanded);
        if(viEnDataAppanded.size()>0)
            getViEnDict().appendFile(viEnDataAppanded);
        //Remove all
        enViDataAppanded.clear();
        viEnDataAppanded.clear();
    }
    
    @Override
    public int getSize() {
        return getDict().size();
    }

    public DictAbstract getDict(){
        if(frameDictionary.isEnViSelected())
            return getEnViDict();
        else 
            return getViEnDict();
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
        //getEnViDict().writeFile(word, meaning);
        WordObject wo = new WordObject(word, meaning, 1);
        enViDataAppanded.add(wo);
    }

    public void insertVietAnhVoca(String word, String meaning) {
        //getViEnDict().writeFile(word, meaning);
        WordObject wo = new WordObject(word, meaning, 1);
        viEnDataAppanded.add(wo);
    }
    
    public void writeLastWordlist(ArrayList<String> listLastWord) {
        writeTextFile(this.lastWord_URL, listLastWord);
    }

    public ArrayList<String> readLastWordlist(){
        try {
            this.listLastWord = readTextFile(this.lastWord_URL);
        } catch (IOException ex) {
            Logger.getLogger(ModelDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.listLastWord;
    }
    
    public Map<String, WordObject> readFavouriteWordlist() {
        //Read File
        try {
            this.listFavouriteWord = readTextFile(this.favouriteWord_URL);
        } catch (IOException ex) {
            Logger.getLogger(ModelDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mapping & Converting
        Map<String, WordObject> mapWO = new HashMap<>();
        for(int i=0; i<this.listFavouriteWord.size(); i++){
            String w = this.listFavouriteWord.get(i);
            WordObject wo = new WordObject(w, this.frameDictionary.getDict().get(w), 1);
            mapWO.put(w, wo);
        }
        return mapWO;
    }

    public void writeFavouriteWordlist(Map<String, WordObject> mapFavourite) {
        ArrayList<String> list = new ArrayList<>();
        for(String key : mapFavourite.keySet()){
            list.add(key);
        }
        writeTextFile(this.favouriteWord_URL, list);
    }

    private void writeTextFile(String fileUrl, ArrayList<String> list){
        try {
            File file = new File(fileUrl);

            if(!file.exists()){
                file.createNewFile();
            }
        
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                for (String str : list) {
                    bw.write(str + "\n");
                }
            }
        } catch (Exception e) {
        }
    }
    
    private ArrayList<String> readTextFile(String fileUrl) throws IOException {
        ArrayList<String> lstr = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileUrl));
            String str;
            while((str = br.readLine()) != null){
                lstr.add(str);
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstr;
    }

    /**
     * @return the viEnDict
     */
    public ViEnDict getViEnDict() {
        return viEnDict;
    }

    /**
     * @param viEnDict the viEnDict to set
     */
    public void setViEnDict(ViEnDict viEnDict) {
        this.viEnDict = viEnDict;
    }

    /**
     * @return the enViDict
     */
    public EnViDict getEnViDict() {
        return enViDict;
    }

    /**
     * @param enViDict the enViDict to set
     */
    public void setEnViDict(EnViDict enViDict) {
        this.enViDict = enViDict;
    }
    
    
}