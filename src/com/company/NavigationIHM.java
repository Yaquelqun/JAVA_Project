package com.company;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class NavigationIHM extends JPanel implements ActionListener {
    NavigationController navigationController;
    Dimension fenSize = new Dimension(300,600);
    JPanel header,gestionAction,gestionListes ,navigation, onglets, contenu, total, gestionItem;
    JLabel imageHeader, textHeader, totalBudget, selBudget;
    JButton ButtonListe = new JButton("Produits");
    JButton ButtonBudget = new JButton("Budget");
    JButton ButtonInfos = new JButton("Infos");
    JButton ButtonNewItem = new JButton("Ajouter un produit");
    String currentListe = null;
    JButton ButtonNew = new JButton("Nouvelle Liste");
    JButton ButtonGo = new JButton("Allons-y !");
    JMenu parametre;
    int idCurrentList =0;
    ArrayList<ListeCourse> mesListes;
    ArrayList<ItemCourse> currentList;

    public String getCurrentListe() {
        return currentListe;
    }

    public NavigationIHM(NavigationController navigationController) {
        this.navigationController = navigationController;
        super.setPreferredSize(fenSize);
        this.setSize(fenSize);
        setHeader();
        setNavigation();
    }

    public void updateListes(){
        mesListes = new ArrayList<>(navigationController.getListe());
    }

    public void updateItem(){
        currentList = new ArrayList<ItemCourse>(navigationController.getselectItem(idCurrentList));
        System.out.println(currentList.get(currentList.size()-1).getNom()+" a été ajouté à la liste");
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
        navigation.setPreferredSize(new Dimension(300,550));
        navigation.setBackground(new Color(255,255,255));

        gestionAction = new JPanel(new BorderLayout());
        gestionListes = new JPanel();

        gestionListes.setPreferredSize(new Dimension(300,500));

        for(int i =0;i<mesListes.size();i++){
            ObjetListe pouet = new ObjetListe(mesListes.get(i).getNom(),mesListes.get(i).getBudget(),mesListes.get(i).getIdListe(),navigationController);
            gestionListes.add(pouet);
        }

        navigation.add(gestionListes,BorderLayout.NORTH);
        gestionAction.setPreferredSize(new Dimension(300,50));
        navigationController.persoButton("res/NewButton.png",ButtonNew);
        gestionAction.add(ButtonNew, BorderLayout.EAST);
        ButtonNew.addActionListener(this);
        navigation.add(gestionAction,BorderLayout.SOUTH);
        add(navigation);
        repaint();
        navigationController.pack();

    }

    private void setHeader() {
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(300,50));
        JPanel panelWest = new JPanel(new FlowLayout());
        JPanel panelEast = new JPanel();

        imageHeader = new JLabel(new ImageIcon((new ImageIcon("butGreen.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT))));
        textHeader = new JLabel(navigationController.client.userName);
        panelWest.add(imageHeader);
        panelWest.add(textHeader);
        header.add(panelWest,BorderLayout.WEST);

        parametre = new JMenu();
        parametre.setIcon(new ImageIcon("res/paramButton.png"));
        panelEast.add(parametre);
        header.add(panelEast,BorderLayout.EAST);
        add(header);
    }


    public void removeNavigation() {
        remove(navigation);
    }

    public void updateHeader(int idButton) {
        idCurrentList = idButton;
        imageHeader.setIcon(new ImageIcon((new ImageIcon("butGrey.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT))));
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
        updateItem();
        navigation = new JPanel();
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setPreferredSize(new Dimension(300,550));
        navigation.setBackground(new Color(255,255,255));

        onglets = new JPanel(new FlowLayout());
        onglets.setPreferredSize(new Dimension(300,50));
        contenu = new JPanel();
        contenu.setPreferredSize(new Dimension(300,450));
        total = new JPanel(new BorderLayout());
        total.setPreferredSize(new Dimension(300,50));
        totalBudget = new JLabel("budget total");
        selBudget = new JLabel("budget items sel");
        gestionItem = new JPanel(new BorderLayout());
        gestionItem.setPreferredSize(new Dimension(300,50));

        onglets.add(ButtonListe);
        onglets.add(ButtonBudget);
        onglets.add(ButtonInfos);

        for(int i =0;i<currentList.size();i++){
            ObjetItem pouet = new ObjetItem(currentList.get(i),navigationController);
            pouet.setModeSelect();
            contenu.add(pouet);
        }

        total.add(totalBudget, BorderLayout.WEST);
        total.add(selBudget, BorderLayout.CENTER);
        total.add(ButtonGo, BorderLayout.EAST);

        navigationController.persoButton("res/NewButton.png",ButtonNewItem);
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
            //navigationController.setVisible(false);
            AjoutItem nouveauItem = new AjoutItem(navigationController);
        }

    }

    public void updatedetailedPanel() {
        updateItem();
        System.out.println("j'ai update les items");
        ObjetItem newItem = new ObjetItem(currentList.get(currentList.size()-1),navigationController);
        System.out.println(newItem.dataName+" a été updaté");
        contenu.add(newItem);
        contenu.repaint();
        navigation.repaint();
        repaint();
        navigationController.pack();
    }
}
