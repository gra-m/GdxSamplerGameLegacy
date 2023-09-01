package com.sampler.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This was only possible with the use of gdx-liftoff-1.12.0.3 allowing legacy lwjgl2 to be used.
 */
public class GdxSamplerLauncher extends JFrame {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    // enables embedding of LibJdx into Java desktop app. Legacy not available in 3
    private LwjglAWTCanvas lwjglAWTCanvas;

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
        configureJFrame();

        launchSample("com.sampler.InputPollingSample");
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

        ApplicationListener sample = retrieveSampleByClassName(name);

        lwjglAWTCanvas = new LwjglAWTCanvas(sample);

        lwjglAWTCanvas.getCanvas().setSize(WIDTH, HEIGHT);

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
