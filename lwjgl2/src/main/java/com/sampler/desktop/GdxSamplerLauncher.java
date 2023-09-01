package com.sampler.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.sampler.common.SampleFactory;
import com.sampler.common.SampleInfoStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This was only possible with the use of gdx-liftoff-1.12.0.3 allowing legacy lwjgl2 to be used.
 */
public class GdxSamplerLauncher extends JFrame {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int CELL_WIDTH_CONTROL_PANEL = 200;
    private static final int CANVAS_WIDTH = WIDTH - CELL_WIDTH_CONTROL_PANEL;


    // enables embedding of LibJdx into Java desktop app. Legacy not available in 3
    private LwjglAWTCanvas lwjglAWTCanvas;
    private JList sampleList;
    private JPanel controlPanel;

    public static void main(String[] args) {
        initializeJframeAfterAwtEventsCompleted();
    }

    private static void initializeJframeAfterAwtEventsCompleted() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GdxSamplerLauncher();
            }
        });
    }

    public GdxSamplerLauncher() throws HeadlessException {
        init();
    }

    private void init(){
        createControlPanel();
        Container container = getContentPane();
        container.add(controlPanel, BorderLayout.WEST);

        configureJFrame();

    }

    private void createControlPanel() {
        controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0; //col
        c.gridy = 0; //row
        c.fill =GridBagConstraints.VERTICAL;
        c.weighty = 1; // has some row weight

        sampleList = new JList(SampleInfoStore.getSampleNames().toArray());
        sampleList.setFixedCellWidth(CELL_WIDTH_CONTROL_PANEL);
        sampleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sampleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                    launchSelectedSample();
            }
        });

        JScrollPane scrollPane = new JScrollPane(sampleList);
        controlPanel.add(scrollPane, c);

        // button
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0;

        JButton launchButton = new JButton("Launch Sample");
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchSelectedSample();
            }
        });

        controlPanel.add(launchButton, c);
    }

    private void launchSelectedSample() {
        String sampleName = (String)sampleList.getSelectedValue();

        if(sampleName == null || sampleName.isEmpty()) {
            System.out.println("Sample name is empty cannot launch");
            return;
        }
        launchSample(sampleName);

    }

    private void configureJFrame() {
        addListenerThatStopsCanvasIfClosedButNotNull();

        setTitle(GdxSamplerLauncher.class.getSimpleName());
        setFocusableWindowState(true);
        setFocusable(true);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addListenerThatStopsCanvasIfClosedButNotNull() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (lwjglAWTCanvas != null){
                    lwjglAWTCanvas.stop();
                }
            }
        });
    }

    private void launchSample(String name) {
        System.out.printf("0.Launching sample name = %s", name);

        Container container = getCleanRootJframeContainer();

        //ApplicationListener sample = retrieveSampleByClassName(name);
        ApplicationListener sample = SampleFactory.newSample(name);

        lwjglAWTCanvas = new LwjglAWTCanvas(sample);

        lwjglAWTCanvas.getCanvas().setSize(CANVAS_WIDTH, HEIGHT);

        container.add(lwjglAWTCanvas.getCanvas(), BorderLayout.CENTER);

        pack();
    }

    private Container getCleanRootJframeContainer() {
        System.out.println("1. Getting clean container.");
        Container container = getContentPane();

        if (lwjglAWTCanvas != null) {
            lwjglAWTCanvas.stop();
            container.remove(lwjglAWTCanvas.getCanvas());
        }
        return container;
    }

    private ApplicationListener retrieveSampleByClassName(String name) {
        ApplicationListener sample;

        try {
            Class<ApplicationListener> clazz = ClassReflection.forName(name);

            // instantiate new instance of retrieved class
            sample = ClassReflection.newInstance(clazz);
        } catch (ReflectionException e) {
            throw new RuntimeException(String.format("Cannot create sample with name= %s", name), e);
        }

        return sample;
    }


}
