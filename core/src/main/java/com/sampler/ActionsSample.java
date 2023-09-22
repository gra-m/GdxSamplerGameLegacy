package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.CustomActor;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class ActionsSample extends SampleBase
{
    private static final Logger LOG = new Logger(ActionsSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ActionsSample.class);
    private static final float WORLD_WIDTH = 1080; //world units
    private static final float WORLD_HEIGHT = 720;

    private Viewport viewport;

    private Stage stage;
    private Texture texture;
    private CustomActor customActor;


    @Override
    public void create( )
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);  // creates camera for itself
        stage = new Stage(viewport); // creates internal batch for itself

        texture = new Texture(Gdx.files.internal("raw/custom-actor.png" ));

        customActor = new CustomActor(new TextureRegion( texture ));
        customActor.setSize(160, 80);
        customActor.setPosition(
            (WORLD_WIDTH - customActor.getWidth()) / 2f,
            (WORLD_HEIGHT - customActor.getHeight()) / 2f);

        customActor.addListener(new ClickListener(  ) {
            @Override
            public void clicked( InputEvent event, float x, float y )
            {
            LOG.debug("customActor clicked event = " + "event = " + event +  " x = " + x + " y = " + y);
            }
        });

        stage.addActor(customActor);
        Gdx.input.setInputProcessor(this);

        String LS = System.getProperty("line.separator");
        String TAB = "\t";


        LOG.debug(LS + "Press Keys" + LS +
                TAB + "1 - RotateBy Action"  + LS +
                TAB + "2 - FadeOUt Action"  + LS +
                TAB + "3 - FadeIn Action"  + LS +
                TAB + "4 - ScaleTo Action"  + LS +
                TAB + "5 - Moveto Action"  + LS +
                TAB + "6 - Sequential Action"  + LS +
                TAB + "7 - Parallel Action"  + LS
            );
    }

    @Override
    public boolean keyDown( int keycode )
    {
        customActor.clearActions(); // clear == listeners and actions cleared

        if (keycode == Input.Keys.NUM_1) {
            LOG.debug("RotateBy Action");
            customActor.addAction(Actions.rotateBy(90f, 2f ));
        } else if ( keycode == Input.Keys.NUM_2 ) {
            LOG.debug("FadeOut Action");
            customActor.addAction(Actions.fadeOut(2f ));
        } else if ( keycode == Input.Keys.NUM_3 ) {
             LOG.debug("FadeIn Action");
            customActor.addAction(Actions.fadeIn(3f ));
        } else if ( keycode == Input.Keys.NUM_4 ) {
            LOG.debug("ScaleTo Action");
            customActor.addAction(Actions.scaleTo(1.5f, 1.5f, 2 ));
        } else if ( keycode == Input.Keys.NUM_5 ) {
            LOG.debug("MoveTo Action");
            customActor.addAction(Actions.moveTo(100, 100, 3 ));
        } else if ( keycode == Input.Keys.NUM_6 ) {
            LOG.debug("Sequential Action");
            customActor.addAction(
                Actions.sequence(Actions.moveTo(200,200, 3),
                Actions.fadeOut(3f),
                Actions.fadeIn(1f)) );
        } else if ( keycode == Input.Keys.NUM_7 ) {
            LOG.debug("Parallel Action");
            customActor.addAction(Actions.parallel(
                Actions.moveTo(0,0, 3f),
                Actions.fadeOut(3f),
                Actions.rotateBy(360, 3f)));
        }

        return true;
    }

    @Override
    public void resize( int width, int height )
    {
        viewport.update(width, height, true);
    }

    @Override
    public void render( )
    {
        GdxUtils.clearScreen();
        viewport.apply();

        stage.act();
        stage.draw();// begin, end, projection matrix all handled by stage
    }

    @Override
    public void dispose( )
    {
        stage.dispose();
        texture.dispose();
    }
}
