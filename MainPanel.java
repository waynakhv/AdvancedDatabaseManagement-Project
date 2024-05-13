package panels;


import javax.swing.*;

import enums.IsolationLevel;
import handlers.Main;
import handlers.SimulationHandler;
import objs.Simulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    Integer[] userSelectorValues = {1, 2, 5, 10, 20, 50, 100};


    JSpinner typeACountSelector = new JSpinner(new SpinnerListModel(userSelectorValues));
    JSpinner typeBCountSelector = new JSpinner(new SpinnerListModel(userSelectorValues));
    JSpinner transactionCountSelector = new JSpinner(new SpinnerListModel(userSelectorValues));
    JComboBox<IsolationLevel> isolationLevelSelector = new JComboBox<>(IsolationLevel.values());
    JButton executeButton = new JButton("<Execute>");
    JButton stopButton = new JButton("<Stop>");
    JLabel textALabel = new JLabel("Type A:");
    JLabel textBLabel = new JLabel("Type B:");
    JLabel textTransactionLabel = new JLabel("Transaction:");
    JLabel textIsolationLabel = new JLabel("Isolation:");
    JTextArea logger = new JTextArea("Simulation Results:\n");
    JScrollPane loggerScroll = new JScrollPane(logger, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JProgressBar fullProgress = new JProgressBar();

    public MainPanel(){

        super();

        init();

    }

    private void init(){

        //typeACountSelector ++
        textALabel.setBounds(40, 40 , 50 ,30);
        typeACountSelector.setBounds(120, 40, 40, 30);
        //typeACountSelector --

        //typeBCounterSelector ++
        textBLabel.setBounds(40, 120, 50, 30);
        typeBCountSelector.setBounds(120 , 120, 40, 30);
        //typeBCounterSelector --

        //transactionCount ++
        textTransactionLabel.setBounds(40 , 200 , 150 ,30 );
        transactionCountSelector.setBounds(120 , 200, 40, 30);
        //transactionCount--

        //System.out.println(javax.swing.UIManager.getDefaults().getFont("Label.font"));

        //isolationLevel ++
        textIsolationLabel.setBounds(40, 280, 100, 30);
        isolationLevelSelector.setBounds(120, 280, 180, 30);
        isolationLevelSelector.setFont(new Font("Dialog", Font.BOLD, 12));
        //isolationLevel --

        //executeButton ++
        executeButton.setBounds(40 , 410 , 100 , 30);
        executeButton.addActionListener(e -> {

            startSimulation();

        });
        //executeButton --

        //stopButton ++
        stopButton.setBounds(180 , 410 , 100 , 30);
        stopButton.addActionListener(e -> {

            stopSimulation();

        });
        //stopButton --

        //logger ++

        loggerScroll.setBounds(340, 40, 480, 400);

        //logger --


        //ProgressBar ++
        fullProgress.setBounds(40, 480, 800, 40);
        fullProgress.setValue(0);
        fullProgress.setStringPainted(true);
        //ProgressBar --

        //JPANEL ++
        this.setLayout(null);

        this.add(typeACountSelector);
        this.add(textALabel);

        this.add(typeBCountSelector);
        this.add(textBLabel);

        this.add(transactionCountSelector);
        this.add(textTransactionLabel);

        this.add(textIsolationLabel);
        this.add(isolationLevelSelector);

        this.add(executeButton);
        this.add(stopButton);

        this.add(loggerScroll);

        this.add(fullProgress);
        //JPANEL --


    }


    public void startSimulation(){

        fullProgress.setValue(0);
        fullProgress.setMaximum(((int) typeACountSelector.getValue() * (int) transactionCountSelector.getValue()) + ((int) typeBCountSelector.getValue() * (int) transactionCountSelector.getValue()));
        SimulationHandler.startSimulation((int) typeACountSelector.getValue(), (int) typeBCountSelector.getValue(), (int) transactionCountSelector.getValue(), (IsolationLevel) isolationLevelSelector.getSelectedItem());
        executeButton.setEnabled(false);
    }

    public void stopSimulation(){
        SimulationHandler.stopSimulation();
        fullProgress.setValue(0);
        activateExecuteButton();
    }

    public void completeATransaction(){

        fullProgress.setValue(fullProgress.getValue() + 1);

    }

    public void log(String text){
        logger.append(text + "\n");
    }

    public void activateExecuteButton(){
        executeButton.setEnabled(true);
    }


}
