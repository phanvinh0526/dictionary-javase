/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import App.ModelDictionary;
import Model.Updateable;
import Model.WordObject;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
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
public class FrameDictionary extends javax.swing.JFrame implements Updateable{

    //ModelDictionary modelDictionary;
    ModelDictionary modelDict = new ModelDictionary(this);
    
    //Frame loading
    JFrame frameLoading = new JFrame("Frame loading ...");
    JLabel lblLoadingPercent = new JLabel("    (0%) Đang load dữ liệu, vui lòng chờ ... ");
    
    //Dictionary Data
    SortedMap<String,String> listEnViSorted = new TreeMap<String,String>();
    SortedMap<String,String> listViEnSorted = new TreeMap<String,String>();
    
    DefaultListModel<String> modelEnVi = new DefaultListModel<String>();
    DefaultListModel<String> modelViEn = new DefaultListModel<String>();
    
    //History, LastWord and Favourite
    private DefaultTableModel tableModelHistory;
    private DefaultTableModel tableModelLastword;
    private DefaultTableModel tableModelFavourite;
    private DefaultListModel listModelNewWord;
    Map<String, WordObject> mapHistory = new HashMap<>();
    ArrayList<String> listLastWord = new ArrayList<>();
    Map<String, WordObject> mapFavourite = new HashMap<>();
    WordObject currentWord = new WordObject();
    
    /**
     * Creates new form FrameDictionary
     */
    public FrameDictionary() {
        initComponents();
        this.setResizable(false);
        
        /*  ---Command Lines---   */
        //Loading Frame
        initFrameLoading();   
        
        //Add radio to groupp
        grpDictType.add(rdoAnhViet);
        grpDictType.add(rdoVietAnh);
        //Disable edit word mean
        txtWordMean.setEditable(false);
        //Load data
        new Thread(new Runnable() {
            @Override
            public void run() {
               load("src/Resources/Viet_Anh.xml", 0, 50, listViEnSorted, modelViEn);
               load("src/Resources/Anh_Viet.xml", 50, 50, listEnViSorted, modelEnVi);
               lstWords.setModel(modelEnVi);
               rdoAnhViet.setSelected(true);
               lstWords.ensureIndexIsVisible(50);
               lstWords.ensureIndexIsVisible(0);
            }
        }).start();
        
        
        
    }

    /*  ---Functions---   */
    public void load(String fileName, int startPercent, int maxPercent, 
            SortedMap<String,String> listMap, DefaultListModel<String> model){
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
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
                listMap.put(word, mean);
                model.addElement(word);
                if(i%20 == 0 || i == size - 1){
                    int tmp = i + 1;
                    float percent = startPercent + (float)tmp/size*100/(100/maxPercent);
                    //if(i == size - 2)
                    //    percent = 50f;
                    //DecimalFormat df = new DecimalFormat("###.##");
                    updatePercentLoaded(percent);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initFrameLoading(){
        Dimension windowSize =  java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        
        frameLoading.setSize(new Dimension(300, 70));
        frameLoading.setAlwaysOnTop(true);
        frameLoading.setLocation(windowSize.width/2 - frameLoading.getWidth()/2, windowSize.height/2 - frameLoading.getHeight()/2 - 100);
        frameLoading.setUndecorated(true); // Remove title bar
        frameLoading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLoading.getContentPane().add(lblLoadingPercent);
        
        //frame.pack();
        frameLoading.setVisible(true);
    }
    public DefaultListModel getDictModel(){
        if(isEnViSelected())
            return modelEnVi;
        else 
            return modelViEn;
    }
    public SortedMap<String,String> getDict(){
        if(isEnViSelected())
            return listEnViSorted;
        else 
            return listViEnSorted;
    }
    public void search(){
        int indexSelected = lstWords.getSelectedIndex();
        
        //Kiểm tra có chọn 1 từ hay không
        if(lstWords.getSelectedIndex() < 0)
            return;
        //Return Wordlist (AV/VA)
        DefaultListModel model = getDictModel();
        
        String word = model.get(indexSelected).toString();
        txtWordMean.setText(getDict().get(word));
        
    }
    public WordObject searchWord(String word){
        String mean = getDict().get(word);
        WordObject wo = new WordObject(word, mean, 1);
        return wo;
    }
    public boolean isEnViSelected(){
        return grpDictType.isSelected(rdoAnhViet.getModel());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpDictType = new javax.swing.ButtonGroup();
        grpDictType1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jpMenu = new javax.swing.JPanel();
        btSearch = new javax.swing.JButton();
        btLastWord = new javax.swing.JButton();
        btHistory = new javax.swing.JButton();
        btFavourite = new javax.swing.JButton();
        btInsert = new javax.swing.JButton();
        jpMain = new javax.swing.JPanel();
        jpSearch = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtWordMean = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jbAddFav = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rdoAnhViet = new javax.swing.JRadioButton();
        rdoVietAnh = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstWords = new javax.swing.JList();
        txtSearch = new javax.swing.JTextField();
        jpFavourite = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtFavourite = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jtextFavouriteAdd = new javax.swing.JTextField();
        jbFavouriteAdd = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jtextFavouriteDel = new javax.swing.JTextField();
        jbFavouriteDel = new javax.swing.JButton();
        jpHistory = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtHistory = new javax.swing.JTable();
        jpLastWord = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtLastWord = new javax.swing.JTable();
        jpInsert = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtextNewWord = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtextNewMean = new javax.swing.JTextArea();
        rdoVietAnh1 = new javax.swing.JRadioButton();
        rdoAnhViet1 = new javax.swing.JRadioButton();
        btnSubmit = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlNewWord = new javax.swing.JList();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        jpMenu.setBackground(new java.awt.Color(0, 0, 51));
        jpMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        btSearch.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btSearch.setForeground(new java.awt.Color(0, 0, 51));
        btSearch.setText("Search");
        btSearch.setMaximumSize(new java.awt.Dimension(100, 100));
        btSearch.setMinimumSize(new java.awt.Dimension(100, 100));
        btSearch.setPreferredSize(new java.awt.Dimension(100, 100));
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });
        jpMenu.add(btSearch);

        btLastWord.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btLastWord.setForeground(new java.awt.Color(0, 0, 51));
        btLastWord.setText("Last words");
        btLastWord.setMaximumSize(new java.awt.Dimension(100, 100));
        btLastWord.setMinimumSize(new java.awt.Dimension(100, 100));
        btLastWord.setPreferredSize(new java.awt.Dimension(100, 100));
        btLastWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLastWordActionPerformed(evt);
            }
        });
        jpMenu.add(btLastWord);

        btHistory.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btHistory.setForeground(new java.awt.Color(0, 0, 51));
        btHistory.setText("History");
        btHistory.setMaximumSize(new java.awt.Dimension(100, 100));
        btHistory.setMinimumSize(new java.awt.Dimension(100, 100));
        btHistory.setPreferredSize(new java.awt.Dimension(100, 100));
        btHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btHistoryMouseClicked(evt);
            }
        });
        btHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHistoryActionPerformed(evt);
            }
        });
        jpMenu.add(btHistory);

        btFavourite.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btFavourite.setText("Favourite");
        btFavourite.setMaximumSize(new java.awt.Dimension(100, 100));
        btFavourite.setMinimumSize(new java.awt.Dimension(100, 100));
        btFavourite.setPreferredSize(new java.awt.Dimension(100, 100));
        btFavourite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btFavouriteMouseClicked(evt);
            }
        });
        btFavourite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFavouriteActionPerformed(evt);
            }
        });
        jpMenu.add(btFavourite);

        btInsert.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btInsert.setText("Insert");
        btInsert.setMaximumSize(new java.awt.Dimension(100, 100));
        btInsert.setMinimumSize(new java.awt.Dimension(100, 100));
        btInsert.setPreferredSize(new java.awt.Dimension(100, 100));
        btInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInsertActionPerformed(evt);
            }
        });
        jpMenu.add(btInsert);

        jpMain.setLayout(new java.awt.CardLayout());

        jpSearch.setBackground(new java.awt.Color(0, 0, 51));
        jpSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Searching");
        jpSearch.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 16, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 0, 51));
        jPanel3.setForeground(new java.awt.Color(0, 0, 51));

        txtWordMean.setColumns(20);
        txtWordMean.setRows(5);
        jScrollPane2.setViewportView(txtWordMean);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 204));
        jLabel1.setText("Definition");

        jbAddFav.setBackground(new java.awt.Color(0, 255, 102));
        jbAddFav.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jbAddFav.setForeground(new java.awt.Color(255, 0, 0));
        jbAddFav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/star.png"))); // NOI18N
        jbAddFav.setText("Add Favourite");
        jbAddFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddFavActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbAddFav))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jbAddFav))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpSearch.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 46, -1, 518));

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        rdoAnhViet.setBackground(new java.awt.Color(0, 0, 51));
        grpDictType.add(rdoAnhViet);
        rdoAnhViet.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoAnhViet.setForeground(new java.awt.Color(255, 255, 255));
        rdoAnhViet.setText("Anh-Việt");
        rdoAnhViet.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdoAnhVietStateChanged(evt);
            }
        });
        rdoAnhViet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoAnhVietItemStateChanged(evt);
            }
        });
        rdoAnhViet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAnhVietActionPerformed(evt);
            }
        });

        rdoVietAnh.setBackground(new java.awt.Color(0, 0, 51));
        grpDictType.add(rdoVietAnh);
        rdoVietAnh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoVietAnh.setForeground(new java.awt.Color(255, 255, 255));
        rdoVietAnh.setText("Việt-Anh");
        rdoVietAnh.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdoVietAnhStateChanged(evt);
            }
        });
        rdoVietAnh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoVietAnhItemStateChanged(evt);
            }
        });

        lstWords.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstWords.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstWords.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstWordsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstWords);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rdoAnhViet, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoVietAnh))
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoVietAnh)
                    .addComponent(rdoAnhViet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jpSearch.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 46, -1, -1));

        jpMain.add(jpSearch, "card3");

        jpFavourite.setBackground(new java.awt.Color(0, 0, 51));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Favourite Wordlist");

        jtFavourite.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtFavourite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtFavouriteMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jtFavourite);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 204));
        jLabel6.setText("Add your favourite word");

        jtextFavouriteAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtextFavouriteAddKeyReleased(evt);
            }
        });

        jbFavouriteAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbFavouriteAdd.setText("ADD");
        jbFavouriteAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFavouriteAddActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 255, 204));
        jLabel7.setText("Delete your favourite word");

        jtextFavouriteDel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtextFavouriteDelKeyReleased(evt);
            }
        });

        jbFavouriteDel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbFavouriteDel.setText("DEL");
        jbFavouriteDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFavouriteDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpFavouriteLayout = new javax.swing.GroupLayout(jpFavourite);
        jpFavourite.setLayout(jpFavouriteLayout);
        jpFavouriteLayout.setHorizontalGroup(
            jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFavouriteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGroup(jpFavouriteLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jpFavouriteLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtextFavouriteDel, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(jtextFavouriteAdd))
                .addGap(18, 18, 18)
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbFavouriteDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbFavouriteAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpFavouriteLayout.setVerticalGroup(
            jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFavouriteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtextFavouriteAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbFavouriteAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtextFavouriteDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbFavouriteDel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpMain.add(jpFavourite, "card2");

        jpHistory.setBackground(new java.awt.Color(0, 0, 51));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Historical Wordlist");

        jtHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jtHistory);

        javax.swing.GroupLayout jpHistoryLayout = new javax.swing.GroupLayout(jpHistory);
        jpHistory.setLayout(jpHistoryLayout);
        jpHistoryLayout.setHorizontalGroup(
            jpHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGroup(jpHistoryLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpHistoryLayout.setVerticalGroup(
            jpHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpMain.add(jpHistory, "card3");

        jpLastWord.setBackground(new java.awt.Color(0, 0, 51));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Last Words");

        jtLastWord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jtLastWord);

        javax.swing.GroupLayout jpLastWordLayout = new javax.swing.GroupLayout(jpLastWord);
        jpLastWord.setLayout(jpLastWordLayout);
        jpLastWordLayout.setHorizontalGroup(
            jpLastWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLastWordLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpLastWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGroup(jpLastWordLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpLastWordLayout.setVerticalGroup(
            jpLastWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLastWordLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpMain.add(jpLastWord, "card3");

        jpInsert.setBackground(new java.awt.Color(0, 0, 51));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Insert new words");

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("New Word");

        btnClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnInsert.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Meaning");

        jtextNewMean.setColumns(20);
        jtextNewMean.setRows(5);
        jScrollPane6.setViewportView(jtextNewMean);

        rdoVietAnh1.setBackground(new java.awt.Color(0, 0, 51));
        grpDictType1.add(rdoVietAnh1);
        rdoVietAnh1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoVietAnh1.setForeground(new java.awt.Color(255, 255, 255));
        rdoVietAnh1.setText("Việt-Anh");
        rdoVietAnh1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdoVietAnh1StateChanged(evt);
            }
        });
        rdoVietAnh1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoVietAnh1ItemStateChanged(evt);
            }
        });

        rdoAnhViet1.setBackground(new java.awt.Color(0, 0, 51));
        grpDictType1.add(rdoAnhViet1);
        rdoAnhViet1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoAnhViet1.setForeground(new java.awt.Color(255, 255, 255));
        rdoAnhViet1.setSelected(true);
        rdoAnhViet1.setText("Anh-Việt");
        rdoAnhViet1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdoAnhViet1StateChanged(evt);
            }
        });
        rdoAnhViet1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoAnhViet1ItemStateChanged(evt);
            }
        });
        rdoAnhViet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAnhViet1ActionPerformed(evt);
            }
        });

        btnSubmit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(0, 0, 255));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jlNewWord.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jlNewWord);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("New Words");
        jLabel11.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtextNewWord, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdoAnhViet1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoVietAnh1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoVietAnh1)
                    .addComponent(rdoAnhViet1)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jtextNewWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpInsertLayout = new javax.swing.GroupLayout(jpInsert);
        jpInsert.setLayout(jpInsertLayout);
        jpInsertLayout.setHorizontalGroup(
            jpInsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInsertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpInsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpInsertLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addContainerGap(514, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jpInsertLayout.setVerticalGroup(
            jpInsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInsertLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(85, 85, 85)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jpMain.add(jpInsert, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jpMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpMain, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jpMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpSearch);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btSearchActionPerformed

    private void btInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInsertActionPerformed
        // TODO add your handling code here:
        drawListNewWord();
        
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpInsert);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btInsertActionPerformed

    private void btFavouriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFavouriteActionPerformed
        // Load Favourite Wordlist
        loadFavourite();
        
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpFavourite);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btFavouriteActionPerformed

    private void rdoVietAnhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoVietAnhItemStateChanged
        lstWords.setModel(modelViEn);
        lstWords.ensureIndexIsVisible(50);
        lstWords.ensureIndexIsVisible(0);
        search();
    }//GEN-LAST:event_rdoVietAnhItemStateChanged

    private void rdoAnhVietItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoAnhVietItemStateChanged
        lstWords.setModel(modelEnVi);
        lstWords.ensureIndexIsVisible(50);
        lstWords.ensureIndexIsVisible(0);
        search();
    }//GEN-LAST:event_rdoAnhVietItemStateChanged

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        if(evt.getKeyCode() == 10){
            search();
            saveHistory();
            return;
        }

        String word = txtSearch.getText().trim();
        ListModel model = lstWords.getModel();
        for(int i = 0; i < model.getSize(); i++){
            if(model.getElementAt(i).toString().startsWith(word)){
                lstWords.setSelectedIndex(i);
                lstWords.ensureIndexIsVisible(i);
                return;
            }
        }

    }//GEN-LAST:event_txtSearchKeyReleased

    private void lstWordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstWordsMouseClicked
        search();
        saveHistory();
    }//GEN-LAST:event_lstWordsMouseClicked

    private void btLastWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLastWordActionPerformed
        // Load Last Wordlist
        loadLastWord();
        
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpLastWord);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btLastWordActionPerformed

    private void btHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHistoryActionPerformed
        // Load History in current Session
        loadHistory();
        
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpHistory);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btHistoryActionPerformed

    private void jbFavouriteAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFavouriteAddActionPerformed
        // TODO add your handling code here:
        this.addFavouriteFunc(jtextFavouriteAdd.getText());
    }//GEN-LAST:event_jbFavouriteAddActionPerformed

    private void jbFavouriteDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFavouriteDelActionPerformed
        // TODO add your handling code here:
        String word = jtextFavouriteDel.getText();
        removeFavouriteFunc(word);
    }//GEN-LAST:event_jbFavouriteDelActionPerformed

    private void rdoAnhVietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAnhVietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhVietActionPerformed

    private void rdoAnhVietStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoAnhVietStateChanged
    }//GEN-LAST:event_rdoAnhVietStateChanged

    private void rdoVietAnhStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoVietAnhStateChanged
    }//GEN-LAST:event_rdoVietAnhStateChanged

    private void btHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btHistoryMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btHistoryMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Write Files
        modelDict.writeLastWordlist(this.listLastWord);
        modelDict.writeFavouriteWordlist(this.mapFavourite);
        
        // Write XML
        modelDict.appendData();
        
    }//GEN-LAST:event_formWindowClosing

    private void jtextFavouriteAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextFavouriteAddKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==10)
            this.addFavouriteFunc(jtextFavouriteAdd.getText());
    }//GEN-LAST:event_jtextFavouriteAddKeyReleased

    private void btFavouriteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btFavouriteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btFavouriteMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //Draw History Panel
        drawHistoryPanel();
        
        //Draw Favourite Panel
        drawFavouritePanel();
        listLastWord = modelDict.readLastWordlist();
        
        //Draw Lastword Panel   
        drawLastWordPanel();
        mapFavourite = modelDict.readFavouriteWordlist();
        
        
    }//GEN-LAST:event_formWindowOpened

    private void jtextFavouriteDelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextFavouriteDelKeyReleased
        // TODO add your handling code here:
         if(evt.getKeyCode()==10)
             removeFavouriteFunc(jtextFavouriteDel.getText());
    }//GEN-LAST:event_jtextFavouriteDelKeyReleased

    private void jbAddFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddFavActionPerformed
        // TODO add your handling code here:
         this.addFavouriteFunc(currentWord.getWord());
         JOptionPane.showMessageDialog(this, "Add Favourite succeed!");
    }//GEN-LAST:event_jbAddFavActionPerformed

    private void jtFavouriteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtFavouriteMouseClicked
        // TODO add your handling code here:
        int rowIndex = this.jtFavourite.getSelectedRow();
        String word = this.jtFavourite.getValueAt(rowIndex, 1).toString();
        this.jtextFavouriteDel.setText(word);
        
    }//GEN-LAST:event_jtFavouriteMouseClicked

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        // Add list
        insertList_Realtime();
        
        // Write file
        modelDict.appendData();
        this.listModelNewWord.clear();
        JOptionPane.showMessageDialog(this, "Submit succeed!");
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void rdoAnhViet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAnhViet1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhViet1ActionPerformed

    private void rdoAnhViet1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoAnhViet1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhViet1ItemStateChanged

    private void rdoAnhViet1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoAnhViet1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhViet1StateChanged

    private void rdoVietAnh1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoVietAnh1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoVietAnh1ItemStateChanged

    private void rdoVietAnh1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoVietAnh1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoVietAnh1StateChanged

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        Integer flag = JOptionPane.showConfirmDialog(this,
            "Do you wanna insert a new word into your dictionary?",
            "Inserting Confirm", JOptionPane.YES_NO_OPTION);

        if(flag.equals(0)){
            if(grpDictType1.isSelected(rdoAnhViet1.getModel())){
                modelDict.insertAnhVietVoca(jtextNewWord.getText(), jtextNewMean.getText().toString());
                //Add List
                this.listModelNewWord.addElement(jtextNewWord.getText());
            }
            else{
                modelDict.insertVietAnhVoca(jtextNewWord.getText(), jtextNewMean.getText().toString());
                //Add List
                this.listModelNewWord.addElement(jtextNewWord.getText());
            }
            jtextNewMean.setText("");
            jtextNewWord.setText("");
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        jtextNewWord.setText("");
        jtextNewMean.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameDictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameDictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameDictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameDictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameDictionary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFavourite;
    private javax.swing.JButton btHistory;
    private javax.swing.JButton btInsert;
    private javax.swing.JButton btLastWord;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnSubmit;
    private javax.swing.ButtonGroup grpDictType;
    private javax.swing.ButtonGroup grpDictType1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JButton jbAddFav;
    private javax.swing.JButton jbFavouriteAdd;
    private javax.swing.JButton jbFavouriteDel;
    private javax.swing.JList jlNewWord;
    private javax.swing.JPanel jpFavourite;
    private javax.swing.JPanel jpHistory;
    private javax.swing.JPanel jpInsert;
    private javax.swing.JPanel jpLastWord;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpMenu;
    private javax.swing.JPanel jpSearch;
    private javax.swing.JTable jtFavourite;
    private javax.swing.JTable jtHistory;
    private javax.swing.JTable jtLastWord;
    private javax.swing.JTextField jtextFavouriteAdd;
    private javax.swing.JTextField jtextFavouriteDel;
    private javax.swing.JTextArea jtextNewMean;
    private javax.swing.JTextField jtextNewWord;
    private javax.swing.JList lstWords;
    private javax.swing.JRadioButton rdoAnhViet;
    private javax.swing.JRadioButton rdoAnhViet1;
    private javax.swing.JRadioButton rdoVietAnh;
    private javax.swing.JRadioButton rdoVietAnh1;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextArea txtWordMean;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updatePercentLoaded(final float percent) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if(percent == 100f){
                    FrameDictionary.this.setVisible(true);
                    FrameDictionary.this.frameLoading.setVisible(false);
                    
                }else
                    lblLoadingPercent.setText("    ("+percent+"%) Đang load dữ liệu, vui lòng chờ ... ");
            }
        }).start();
    }

    private void saveHistory() {
        
        int indexSelected = lstWords.getSelectedIndex();
        
        //Kiểm tra có chọn 1 từ hay không
        if(lstWords.getSelectedIndex() < 0)
            return;
        //Return Wordlist (AV/VA)
        DefaultListModel model = getDictModel();
        
        String word = model.get(indexSelected).toString();
        String meaning = getDict().get(word);

        //Check WordList
        Integer times = 1;
        for(String key : mapHistory.keySet()){
            if(key.equals(word)){
                times += mapHistory.get(key).getTimes();
            }
        }
        
        //Save History
        WordObject wo = new WordObject(word, meaning, times);
        mapHistory.put(word, wo);
        
        //Save Last Word
        if(this.listLastWord.indexOf(word) == -1){
            this.listLastWord.add(word);
            if(this.listLastWord.size()>10){
                this.listLastWord.remove(0);
            }
        }
        
        //Save Favourite
        currentWord.setWord(word);
        currentWord.setMeaning(meaning);
        currentWord.setTimes(times);
    }

    private void drawHistoryPanel() {
        this.tableModelHistory= new DefaultTableModel();
        this.tableModelHistory.addColumn("#");
        this.tableModelHistory.addColumn("Word");
        this.tableModelHistory.addColumn("Meaning");
        this.tableModelHistory.addColumn("Time");
        this.jtHistory.setModel(tableModelHistory);
    }
    private void drawLastWordPanel() {
        this.tableModelLastword = new DefaultTableModel();
        this.tableModelLastword.addColumn("#");
        this.tableModelLastword.addColumn("Word");
        this.tableModelLastword.addColumn("Meaning");
        this.jtLastWord.setModel(tableModelLastword);
    }
    private void drawFavouritePanel() {
        this.tableModelFavourite = new DefaultTableModel();
        this.tableModelFavourite.addColumn("#");
        this.tableModelFavourite.addColumn("Word");
        this.tableModelFavourite.addColumn("Meaning");
        this.jtFavourite.setModel(tableModelFavourite);
    }
    private void drawListNewWord(){
        this.listModelNewWord = new DefaultListModel();
        this.jlNewWord.setModel(listModelNewWord);
    }
    private void loadLastWord() {
        removeRowJTable(this.tableModelLastword);
        Integer stt = 1;
        for(String str : this.listLastWord){
            Object[] o = new Object[]{stt, str, getDict().get(str)};
            this.tableModelLastword.addRow(o);
            stt++;
        }
    }
    private void loadHistory() {
        removeRowJTable(this.tableModelHistory);
        Integer stt = 1;
        for(String key : mapHistory.keySet()){
            WordObject wo = mapHistory.get(key);
            Object[] o = new Object[]{stt, wo.getWord(), wo.getMeaning(), wo.getTimes()};
            stt++;
            this.tableModelHistory.addRow(o);
        }
    }
    private void loadFavourite() {
        updateFavouriteTable();
    }
    
    private void removeFavouriteFunc(String word){
        this.mapFavourite.remove(word);
        updateFavouriteTable();
    }
    
    private void addFavouriteFunc(String word) {
        mapFavourite.put(word, searchWord(word));
        updateFavouriteTable();
    }
    private void updateFavouriteTable() {
        removeRowJTable(this.tableModelFavourite);
        Integer stt = 1;
        for(String key : mapFavourite.keySet()){
            WordObject wo = mapFavourite.get(key);
            Object[] o = new Object[]{stt, wo.getWord(), wo.getMeaning()};
            this.tableModelFavourite.addRow(o);
            stt++;
        }
    }

    private void removeRowJTable(DefaultTableModel dm){
        int rowCount = dm.getRowCount();
        //Notes: Phai duyen tu tren xuong (n->0) ?
        for(int i=rowCount-1; i>=0; i--)
            dm.removeRow(i);
    }

    private void insertList_Realtime() {
        ArrayList<WordObject> listEn = modelDict.getEnViDataAppanded();
        for( WordObject w : listEn){
            this.listEnViSorted.put(w.getWord(), w.getMeaning());
            this.modelEnVi.addElement(w.getWord());
        }
        
        ArrayList<WordObject> listVi = modelDict.getViEnDataAppanded();
        for( WordObject w2 : listVi){
            this.listViEnSorted.put(w2.getWord(), w2.getMeaning());
            this.modelViEn.addElement(w2.getWord());
        }
    }
}
