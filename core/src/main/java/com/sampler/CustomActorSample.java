package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.CustomActor;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class CustomActorSample extends SampleBase
{
    private static final Logger LOG = new Logger(CustomActorSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(CustomActorSample.class);
    private static final float WORLD_WIDTH = 1080; //world units
    private static final float WORLD_HEIGHT = 720;

    private Viewport viewport;

    private Stage stage;
    private Texture texture;


    @Override
    public void create( )
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);  // creates camera for itself
        stage = new Stage(viewport); // creates internal batch for itself

        texture = new Texture(Gdx.files.internal("raw/custom-actor.png" ));

        CustomActor customActor = new CustomActor(new TextureRegion( texture ));
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
        Gdx.input.setInputProcessor(stage);
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
