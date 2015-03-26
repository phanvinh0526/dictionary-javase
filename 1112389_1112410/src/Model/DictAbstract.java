/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import App.ModelDictionary;
import Frame.FrameDictionary;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author vinh
 */
public abstract class DictAbstract {
    private String fileName;
    
    //Danh sách từ
    SortedMap<String,String> listSorted = new TreeMap<String,String>();
    
    //Danh sách để lấy model
    LinkedList<String> listLinked = new LinkedList<String>();
    
    public DictAbstract(String fileName){
        this.fileName = fileName;
    }
    
    // Append Dictionary Data
    public void appendFile(ArrayList<WordObject> dataAppended) {
        String fileUrl = fileName;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileUrl);
            Element root = document.getDocumentElement();
             // Root Element
            Element rootElement = document.getDocumentElement();
        
            try {
                for (WordObject i : dataAppended) {
                    // server elements
                    Element record = document.createElement("record");
                    rootElement.appendChild(record);

                    Element name = document.createElement("word");
                    name.appendChild(document.createTextNode(i.getWord()));
                    record.appendChild(name);

                    Element port = document.createElement("meaning");
                    port.appendChild(document.createTextNode(i.getMeaning()));
                    record.appendChild(port);

                    root.appendChild(record);
                }
                
                DOMSource source = new DOMSource(document);
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                StreamResult result = new StreamResult(fileUrl);
                transformer.transform(source, result);
                
            } catch (DOMException | TransformerException ex) {
                Logger.getLogger(ModelDictionary.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(EnViDict.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // oad Dictionary Data
    public void loadFile(Updateable update, int startPercent, int maxPercent){
        File file = new File(this.getFileName());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory 
               .newInstance();
        DocumentBuilder documentBuilder;
        Document document ;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
            NodeList list = document.getElementsByTagName("record");
            //list.item(index)
            int size = list.getLength();
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                Element ele = (Element)node;
                String word = ele.getElementsByTagName("word").item(0).getTextContent();
                String mean = ele.getElementsByTagName("meaning").item(0).getTextContent();
                listSorted.put(word, mean);
                if(i%20 == 0 || i == size - 1){
                    int tmp = i + 1;
                    float percent = (float)tmp/size*100/(100/maxPercent);
                    //if(i == size - 2)
                    //    percent = 50f;
                    DecimalFormat df = new DecimalFormat("###.##");
                    update.updatePercentLoaded(startPercent + Float.parseFloat(df.format(percent)));
                }
            }
            Iterator<String> iter = listSorted.keySet().iterator();
            while(iter.hasNext()){
                String key = iter.next();
                listLinked.add(key);
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Tra nghĩa của một từ
    
    //Lấy key tại phần từ vị trí i
    public String getKeyElementAt(int idx){
        if(listLinked.size() > idx)
            return listLinked.get(idx);
        else
            return "";
    }
    
    //Lấy value tại key k
    public String getValueFromKey(String key){
        return listSorted.get(key);
    }
    
    //Lấy value tại index
    public String getValueFromIndex(int index){
        String word = listLinked.get(index);
        return getValueFromKey(word);
    }
    
    //Lấy size
    public int size(){
        return listLinked.size();
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
