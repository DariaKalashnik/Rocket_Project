package gui;

import com.baseclasses.ItemClass;
import com.baseclasses.ResultClass;
import com.baseclasses.Rocket;
import com.rocket.database.DBWorker;
import com.rocket.database.Dao;
import com.rocket.database.ResultDao;
import com.rockets.U1;
import com.rockets.U2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Mission implements ActionListener {
    private JPanel panelMain;
    private JButton btnLoadU2;
    private JButton btnLoadU1;
    private JButton btnRestart;
    private JList u1List;
    private JList u2List;
    private JTextField txtField1;
    private JTextField txtField2;
    private JScrollPane scroll2;
    private JScrollPane scroll1;
    private JLabel lbl1U1;
    private JLabel lbl2U1;
    private JLabel lbl3U1;
    private JLabel lbl1U2;
    private JLabel lbl2U2;
    private JLabel lbl3U2;
    private JFrame frame;
    private DefaultListModel<String> mModel;
    private ArrayList<Rocket> arrayU1 = new ArrayList<Rocket>();
    private ArrayList<Rocket> arrayU2 = new ArrayList<>();
    private Charset charset = Charset.forName("UTF-8");
    private List<ItemClass> arrayPhase1 = new ArrayList<>();
    private List<ItemClass> arrayPhase2 = new ArrayList<>();
    private BufferedReader bufferPhases;
    private ItemClass items;
    private Rocket rocket1, rocket2;
    private int numOfRockets = 0;
    private long budgetNeeded = 0;
    private int numberOfRocketsToSend = 0;
    private String numberOfRocketsMessage;
    private String extraRocketsMessage;
    private String budgetMessage;
    private Timer timer;
    private String warning = "warning: ";
    private String rocketSentAgain = "Rocket is sent again";
    private boolean isPressed;
    private DBWorker dbWorker;
    private Dao dao;
    private U1 rocketU1;
    private U2 rocketU2;
    private ResultClass resultClass;



    private Mission() {
        loadItems();
        btnRestart.setEnabled(false);
        btnLoadU1.addActionListener(this);
        btnLoadU2.addActionListener(this);
        btnRestart.addActionListener(this);
        btnLoadU1.setActionCommand("1");
        btnLoadU2.setActionCommand("2");
        btnRestart.setActionCommand("3");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int action = Integer.parseInt(e.getActionCommand());
        if (isPressed){
            btnRestart.setEnabled(true);
        }
        switch(action) {
            case 1:
                isPressed = true;
                loadU1Phase1();
                loadU1Phase2();
                lbl1U1.setText(numberOfRocketsMessage);
                U1simulation();
                u1List.setModel(mModel);
                btnLoadU1.setEnabled(false);
                break;
            case 2:
                isPressed = true;
                loadU2Phase1();
                loadU2Phase2();
                lbl1U2.setText(numberOfRocketsMessage);
                U2simulation();
                u2List.setModel(mModel);
                btnLoadU2.setEnabled(false);
                break;
            case 3:
                mModel.clear();
                arrayU1.clear();
                arrayU2.clear();
                u1List.repaint();
                u2List.repaint();
                for (Component component : panelMain.getComponents()) {
                    if (component instanceof JLabel) {
                        ((JLabel) component).setText(null);

                    }
                }
                btnLoadU1.setEnabled(true);
                btnLoadU2.setEnabled(true);

        }
    }

    public static void main(String[] args) {
        Mission mission = new Mission();
        mission.createUIComponents();
        mission.frame = new JFrame("Space Mission");
        mission.frame.setContentPane(new Mission().panelMain);
        mission.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mission.frame.pack();
        mission.frame.setVisible(true);
    }

    public void createUIComponents() {
        timer = new Timer(200000, e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        timer.start();

        frame = new JFrame();
        panelMain = new JPanel();
        btnLoadU1 = new JButton();
        btnLoadU2 = new JButton();
        btnRestart = new JButton();
        u1List = new JList();
        u2List = new JList();
    }

    private void loadItems() {
        try {
            FileInputStream phase1 = new FileInputStream("phase1.txt");
            FileInputStream phase2 = new FileInputStream("phase2.txt");
            SequenceInputStream sequence = new SequenceInputStream(phase1, phase2);
            bufferPhases = new BufferedReader(new InputStreamReader(sequence, charset));

            String line;
            String[] parts;
            String name;
            int weight;

            while ((line = bufferPhases.readLine()) != null && !line.isEmpty()) {
                parts = line.split("(?<=\\D)(?=\\d)");
                name = parts[0].trim();
                weight = Integer.parseInt(parts[1].trim());
                items = new ItemClass(name, weight);
                if (phase1.getChannel().isOpen()) {
                    arrayPhase1.add(items);
                } else if (phase2.getChannel().isOpen())
                    arrayPhase2.add(items);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File is missing!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Number format exception");
        } finally {
            if (bufferPhases != null) {
                try {
                    bufferPhases.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadU1(List<ItemClass> array, int numberOfRockets) {
        rocket1 = new U1();
        for (ItemClass mItem : array) {
            if (!rocket1.canCarry(mItem)) {
                arrayU1.add(rocket1);
                rocket1 = new U1();
                numberOfRockets++;
            }
            rocket1.carry(mItem);
        }
        numberOfRocketsMessage = (String.format("Number of rockets is: %d", numberOfRockets));
    }

    private void loadU2(List<ItemClass> array, int numberOfRockets) {
        rocket2 = new U2();
        for (ItemClass mItem : array) {
            if (!rocket2.canCarry(mItem)) {
                arrayU2.add(rocket2);
                rocket2 = new U2();
                numberOfRockets++;
            }
            rocket2.carry(mItem);
        }

        numberOfRocketsMessage = (String.format("Number of rockets is: %d", numberOfRockets));
    }

    private void loadU1Phase1() {
        loadU1(arrayPhase1, numOfRockets);
    }

    private void loadU1Phase2() {
        loadU1(arrayPhase2, numOfRockets);
    }

    private void loadU2Phase1() {
        loadU2(arrayPhase1, numOfRockets);
    }

    private void loadU2Phase2() {
        loadU2(arrayPhase2, numOfRockets);
    }

    private void runSimulation(ArrayList<Rocket> array, long budgetNeeded) {
        dbWorker = new DBWorker();
        mModel = new DefaultListModel<>();
        for (Rocket rocket : array) {
            if (!rocket.launch()) {
                numberOfRocketsToSend++;
                mModel.addElement("<html>"+warning.toUpperCase().concat(rocket.rocketName).concat(" has EXPLODED!").concat("<br>" + rocketSentAgain));
            }

            if (!rocket.land()) {
                numberOfRocketsToSend++;
                mModel.addElement("<html>"+warning.toUpperCase().concat(rocket.rocketName).concat(" has CRASHED!").concat("<br>" + rocketSentAgain));
            }
            budgetNeeded += rocket.rocketCost;

            try {
                recordData(rocket.rocketName, Math.toIntExact(budgetNeeded), numberOfRocketsToSend);
            } catch (Exception e) {
                System.out.println("Error while inserting data!");
            }
        }

        extraRocketsMessage = String.valueOf(numberOfRocketsToSend).concat(" rocket(s)");
        budgetMessage = String.format("Total budget needed: %d", budgetNeeded);

        System.out.println(budgetMessage);
        System.out.println(extraRocketsMessage);
    }

    private void U1simulation() {
        rocketU1 = new U1();
        runSimulation(arrayU1, budgetNeeded);
        lbl2U1.setText(budgetMessage);
        lbl3U1.setText(extraRocketsMessage);
    }

    private void U2simulation() {
        rocketU2 = new U2();
        runSimulation(arrayU2, budgetNeeded);
        lbl2U2.setText(budgetMessage);
        lbl3U2.setText(extraRocketsMessage);
    }


    private void recordData(String name, long budgetNeeded, int numberOfRocketsToSend) throws Exception {
        Connection connection = dbWorker.getConnection();
        dao = new ResultDao(connection);
        dao.createResultClass(new ResultClass(name, Math.toIntExact(budgetNeeded), numberOfRocketsToSend));
        System.out.println(name + " " + budgetNeeded + " " + numberOfRocketsToSend);
    }
}