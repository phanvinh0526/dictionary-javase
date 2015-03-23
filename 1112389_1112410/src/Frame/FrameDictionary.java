/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import App.ModelDictionary;
import Model.Updateable;
import java.awt.Dimension;
import java.io.File;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
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
    ModelDictionary modelDict = new ModelDictionary();
    
    //Frame loading
    JFrame frameLoading = new JFrame("Frame loading ...");
    JLabel lblLoadingPercent = new JLabel("    (0%) Đang load dữ liệu, vui lòng chờ ... ");
    
    SortedMap<String,String> listEnViSorted = new TreeMap<String,String>();
    SortedMap<String,String> listViEnSorted = new TreeMap<String,String>();
    
    DefaultListModel<String> modelEnVi = new DefaultListModel<String>();
    DefaultListModel<String> modelViEn = new DefaultListModel<String>();
    
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
    public void load(String fileName, int startPercent, int maxPercent, SortedMap<String,String> listMap, DefaultListModel<String> model){
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
            
//            Iterator<String> iter = listSorted.keySet().iterator();
//            while(iter.hasNext()){
//                String key = iter.next();
//                listLinked.add(key);
//            }
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
        DefaultListModel model = getDictModel();
        
        String word = model.get(indexSelected).toString();
        txtWordMean.setText(getDict().get(word));
        
        
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
        jPanel4 = new javax.swing.JPanel();
        rdoAnhViet = new javax.swing.JRadioButton();
        rdoVietAnh = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstWords = new javax.swing.JList();
        txtSearch = new javax.swing.JTextField();
        jpFavourite = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jpHistory = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jpLastWord = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jpInsert = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtNewWord = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jaMeaning = new javax.swing.JTextArea();
        rdoVietAnh1 = new javax.swing.JRadioButton();
        rdoAnhViet1 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#", "Vocabulary", "Times"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable2);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 204));
        jLabel6.setText("Add your favourite word");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 255, 204));
        jLabel7.setText("Delete your favourite word");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("DEL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpFavouriteLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jpFavouriteLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jpFavouriteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpMain.add(jpFavourite, "card2");

        jpHistory.setBackground(new java.awt.Color(0, 0, 51));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Historical Wordlist");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList1);

        javax.swing.GroupLayout jpHistoryLayout = new javax.swing.GroupLayout(jpHistory);
        jpHistory.setLayout(jpHistoryLayout);
        jpHistoryLayout.setHorizontalGroup(
            jpHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(473, Short.MAX_VALUE))
        );
        jpHistoryLayout.setVerticalGroup(
            jpHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpMain.add(jpHistory, "card3");

        jpLastWord.setBackground(new java.awt.Color(0, 0, 51));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Last Words");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#", "Vocabulary", "Times"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

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

        jaMeaning.setColumns(20);
        jaMeaning.setRows(5);
        jScrollPane6.setViewportView(jaMeaning);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdoAnhViet1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoVietAnh1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtNewWord, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoVietAnh1)
                    .addComponent(rdoAnhViet1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtNewWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpInsertLayout = new javax.swing.GroupLayout(jpInsert);
        jpInsert.setLayout(jpInsertLayout);
        jpInsertLayout.setHorizontalGroup(
            jpInsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInsertLayout.createSequentialGroup()
                .addGroup(jpInsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpInsertLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(jpInsertLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jpInsertLayout.setVerticalGroup(
            jpInsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInsertLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(61, 61, 61)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
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
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpInsert);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btInsertActionPerformed

    private void btFavouriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFavouriteActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_lstWordsMouseClicked

    private void btLastWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLastWordActionPerformed
        // TODO add your handling code here:
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpLastWord);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btLastWordActionPerformed

    private void btHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHistoryActionPerformed
        // TODO add your handling code here:
        jpMain.removeAll();
        jpMain.repaint();
        jpMain.revalidate();
        
        jpMain.add(jpHistory);
        jpMain.repaint();
        jpMain.revalidate();
    }//GEN-LAST:event_btHistoryActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        jtNewWord.setText("");
        jaMeaning.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        Integer flag = JOptionPane.showConfirmDialog(this, 
                "Do you wanna insert a new word into your dictionary?", 
                "Inserting Confirm", JOptionPane.YES_NO_OPTION);
        if(flag.equals(1)){
            if(grpDictType1.isSelected(rdoAnhViet1.getModel())){
                modelDict.insertAnhVietVoca(jtNewWord.getText(), jaMeaning.getText().toString());
                
            }
            else{
                modelDict.insertVietAnhVoca(jtNewWord.getText(), jaMeaning.getText().toString());
                
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void rdoAnhVietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAnhVietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhVietActionPerformed

    private void rdoAnhVietStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoAnhVietStateChanged
    }//GEN-LAST:event_rdoAnhVietStateChanged

    private void rdoVietAnhStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoVietAnhStateChanged
    }//GEN-LAST:event_rdoVietAnhStateChanged

    private void rdoVietAnh1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoVietAnh1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoVietAnh1StateChanged

    private void rdoVietAnh1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoVietAnh1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoVietAnh1ItemStateChanged

    private void rdoAnhViet1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoAnhViet1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhViet1StateChanged

    private void rdoAnhViet1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoAnhViet1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhViet1ItemStateChanged

    private void rdoAnhViet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAnhViet1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoAnhViet1ActionPerformed

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
    private javax.swing.ButtonGroup grpDictType;
    private javax.swing.ButtonGroup grpDictType1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextArea jaMeaning;
    private javax.swing.JPanel jpFavourite;
    private javax.swing.JPanel jpHistory;
    private javax.swing.JPanel jpInsert;
    private javax.swing.JPanel jpLastWord;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpMenu;
    private javax.swing.JPanel jpSearch;
    private javax.swing.JTextField jtNewWord;
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
}
