package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class NavigationIHM extends JPanel implements ActionListener {
    NavigationController navigationController;
    Dimension fenSize = new Dimension(300,600);
    JPanel header,gestionAction,gestionListes ,navigation, onglets, contenu, total, gestionItem;
    JLabel imageHeader, textHeader, totalBudget, selBudget;
    JButton ButtonListe = new JButton();
    JButton ButtonBudget = new JButton();
    JButton ButtonInfos = new JButton();
    JButton ButtonNewItem = new JButton();
    String currentListe = null;
    JButton ButtonNew = new JButton();
    JButton buttonShare = new JButton();
    JButton ButtonGo = new JButton();
    JMenuBar parametre;
    JMenuItem deconnection = new JMenuItem("disconnect");
    int idCurrentList =0;
    ArrayList<ListeCourse> mesListes;
    ArrayList<ItemCourse> currentList;

    public String getCurrentListe() {
        return currentListe;
    }

    public NavigationIHM(NavigationController navigationController) {
        this.navigationController = navigationController;
        super.setPreferredSize(fenSize);
        this.setLayout(new BorderLayout());
        this.setSize(fenSize);
        setHeader();
        setNavigation();
    }

    public void updateListes(){
        mesListes = new ArrayList<>(navigationController.getListe());
    }

    public void updateItem(){
        currentList = new ArrayList<ItemCourse>(navigationController.getselectItem(idCurrentList));
    }

    public void updateNavigation(){
        updateListes();
         ObjetListe newListe = new ObjetListe(mesListes.get(mesListes.size()-1).getNom(),mesListes.get(mesListes.size()-1).getBudget(),mesListes.get(mesListes.size()-1).getIdListe(),navigationController);
         gestionListes.add(newListe);
        repaint();
        navigationController.pack();
    }

    public void setNavigation() {
        updateListes();
        navigation = new JPanel(new BorderLayout());
        navigation.setPreferredSize(new Dimension(300,535));
        navigation.setBackground(Color.white);

        gestionAction = new JPanel(new BorderLayout());
        gestionAction.setBackground(Client.BACKGROUND_COLOR);
        gestionListes = new JPanel();
        gestionListes.setBackground(Color.WHITE);

        gestionListes.setPreferredSize(new Dimension(300,470));

        for(int i =0;i<mesListes.size();i++){
            ObjetListe pouet = new ObjetListe(mesListes.get(i).getNom(),mesListes.get(i).getBudget(),mesListes.get(i).getIdListe(),navigationController);
            gestionListes.add(pouet);
        }

        navigation.add(gestionListes,BorderLayout.NORTH);
        gestionAction.setPreferredSize(new Dimension(300,65));
        navigationController.persoButton("NewButton.png",ButtonNew);
        navigationController.persoButton("shareButton.png",buttonShare);
        gestionAction.add(ButtonNew, BorderLayout.EAST);
        gestionAction.add(buttonShare,BorderLayout.WEST);
        ButtonNew.addActionListener(this);
        buttonShare.addActionListener(this);
        navigation.add(gestionAction,BorderLayout.SOUTH);
        add(navigation, BorderLayout.CENTER);
        repaint();
        navigationController.pack();

    }

    private void setHeader() {
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(300,65));
        header.setBackground(Client.BACKGROUND_COLOR);
        JPanel panelWest = new JPanel(new FlowLayout());
        panelWest.setBackground(Client.BACKGROUND_COLOR);
        JPanel panelEast = new JPanel();
        panelEast.setBackground(Client.BACKGROUND_COLOR);

        imageHeader = new JLabel(new ImageIcon((new ImageIcon("butGreen.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT))));
        imageHeader.setBackground(Client.BACKGROUND_COLOR);
        textHeader = new JLabel(Client.getClient().getUserName());
        navigationController.persoLabel(textHeader,Client.BACKGROUND_COLOR);
        panelWest.add(imageHeader);
        panelWest.add(textHeader);
        header.add(panelWest,BorderLayout.WEST);

        parametre = new JMenuBar();
        JMenu pouet = new JMenu();
        pouet.add(deconnection);
        pouet.setIcon(new ImageIcon("res/Buttons/paramButton.png"));
        parametre.add(pouet);
        deconnection.addActionListener(this);
        parametre.setBackground(Client.BACKGROUND_COLOR);
        panelEast.add(parametre);
        header.add(panelEast,BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
    }


    public void removeNavigation() {
        remove(navigation);
    }

    public void updateHeader(int idButton) {
        idCurrentList = idButton;
        imageHeader.setIcon(new ImageIcon((new ImageIcon("res/Buttons/returnButton.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT))));
        imageHeader.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                remove(navigation);
                removeHeader();
                setHeader();
                setNavigation();
            }
        });

        for (int i =0;i<mesListes.size();i++){
            if (mesListes.get(i).getIdListe() == idButton){
                currentListe = mesListes.get(i).getNom();
                currentList = new ArrayList<>();
                textHeader.setText(currentListe);
            }
        }

    }

    private void removeHeader() {
        remove(header);
    }

    public void addDetailedPanel(int idButton) {
        //TODO avoir les onglets et un onglet ouvert
        idCurrentList = idButton;
        updateItem();
        navigation = new JPanel();
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setPreferredSize(new Dimension(300,500));
        navigation.setBackground(Color.WHITE);

        onglets = new JPanel(new FlowLayout());
        onglets.setBackground(Color.WHITE);
        onglets.setPreferredSize(new Dimension(300,60));
        contenu = new JPanel();
        contenu.setPreferredSize(new Dimension(300,400));
        contenu.setBackground(Color.WHITE);
        total = new JPanel(new BorderLayout());
        total.setBackground(Client.BACKGROUND_INV_COLOR);
        total.setPreferredSize(new Dimension(300,70));
        totalBudget = new JLabel("Budget total : 30€");
        navigationController.persoLabel(totalBudget, Client.BACKGROUND_INV_COLOR);
        selBudget = new JLabel("");
        navigationController.persoLabel(selBudget, Client.BACKGROUND_INV_COLOR);
        gestionItem = new JPanel(new BorderLayout());
        gestionItem.setPreferredSize(new Dimension(300,70));
        gestionItem.setBackground(Client.BACKGROUND_COLOR);
        ButtonListe.addActionListener(this);
        ButtonBudget.addActionListener(this);
        ButtonInfos.addActionListener(this);
        ButtonGo.addActionListener(this);
        onglets.add(ButtonListe);
        //onglets.add(ButtonBudget);
        onglets.add(ButtonInfos);

        for(int i =0;i<currentList.size();i++){
            ObjetItem pouet = new ObjetItem(currentList.get(i),navigationController);
            pouet.setModeSelect();
            contenu.add(pouet);
        }

        total.add(totalBudget, BorderLayout.WEST);
        total.add(selBudget, BorderLayout.CENTER);
        total.add(ButtonGo, BorderLayout.EAST);
        navigationController.persoButton("GoButton.png",ButtonGo);
        navigationController.persoButton("NewButton.png",ButtonNewItem);
        navigationController.persoButton("ListButton.png",ButtonListe);
        navigationController.persoButton("BudgetButton.png",ButtonBudget);
        navigationController.persoButton("InfoButton.png",ButtonInfos);
        ButtonNewItem.addActionListener(this);
        gestionItem.add(ButtonNewItem, BorderLayout.EAST);

        navigation.add(onglets);
        navigation.add(contenu);
        navigation.add(total);
        navigation.add(gestionItem);

        //contenu.setAutoscrolls(true);
        add(navigation);
        repaint();
        navigationController.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == ButtonNew){
            navigationController.setVisible(false);
            AjoutListe nouvelleListe = new AjoutListe(navigationController);
        }
        if(s==ButtonNewItem){
            navigationController.setVisible(false);
            AjoutItem nouveauItem = new AjoutItem(navigationController);
        }
        if(s==buttonShare){
            navigationController.setVisible(false);
            partageListe nouveauPartage = new partageListe(navigationController,mesListes);
        }
        if(s==ButtonGo){
            StartItineraire nouvelItineraire = new StartItineraire(navigationController,currentList);
        }
        if(s==deconnection){
            Client.getClient().disconnect(Client.getClient().getUserName());
            navigationController.dispose();
        }
        if(s==ButtonListe)
        {
            remove(navigation);
           addDetailedPanel(idCurrentList);
        }
        if(s==ButtonInfos){
            remove(navigation);
            updateToInfoPanel();
        }
    }

    private void updateToInfoPanel() {
        navigation = new JPanel();
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setPreferredSize(new Dimension(300,500));
        navigation.setBackground(new Color(255,255,255));

        onglets = new JPanel(new FlowLayout());
        onglets.setPreferredSize(new Dimension(300,60));
        ButtonListe.addActionListener(this);
        ButtonBudget.addActionListener(this);
        ButtonInfos.addActionListener(this);
        ButtonGo.addActionListener(this);
        onglets.add(ButtonListe);
        //onglets.add(ButtonBudget);
        onglets.add(ButtonInfos);
        navigation.add(onglets);
        JPanel completePanel = new JPanel();
        completePanel.setLayout(new BoxLayout(completePanel,BoxLayout.Y_AXIS));
        JScrollPane scrollFrame = new JScrollPane(completePanel);
        JLabel titre = new JLabel(currentListe);
        navigationController.persoLabel(titre,Client.BACKGROUND_INV_COLOR);
        JEditorPane map = new StartItineraire(navigationController,mesListes.get((idCurrentList-1)).getLieu());
        map.setPreferredSize(new Dimension(280,200));
        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(300,20));
        JTextArea description = new JTextArea(mesListes.get(idCurrentList-1).getDescription());
        description.setLineWrap(true);
        description.setColumns(10);
        description.setRows(10);
        description.setBackground(Color.WHITE);
        description.setBorder(BorderFactory.createLineBorder(Client.BACKGROUND_COLOR,3,true));
        Font myFont = new Font("Serif", Font.BOLD, 18);
        description.setFont(myFont);
        description.setForeground(Color.BLACK);

        completePanel.add(titre);
        completePanel.add(paddingPanel);
        completePanel.add(map);
        completePanel.add(paddingPanel);
        completePanel.add(description);
        completePanel.setAutoscrolls(true);
        completePanel.setBackground(Client.BACKGROUND_INV_COLOR);
        scrollFrame.setPreferredSize(new Dimension(300,440));
        scrollFrame.repaint();
        navigation.add(scrollFrame);
        add(navigation);
        repaint();
        navigationController.pack();
    }

    public void updatedetailedPanel() {
        updateItem();
        System.out.println("j'ai update les items");
        ObjetItem newItem = new ObjetItem(currentList.get(currentList.size()-1),navigationController);
        System.out.println(newItem.dataName+" a été updaté");
        contenu.add(newItem);
        contenu.repaint();
        navigation.repaint();
        contenu.removeAll();
        for(int i =0;i<currentList.size();i++){
            ObjetItem pouet = new ObjetItem(currentList.get(i),navigationController);
            pouet.setModeSelect();
            contenu.add(pouet);
        }
        repaint();
        navigationController.pack();
    }
}
