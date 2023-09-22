package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;

public class CustomActorSample extends SampleBase
{
    private static final Logger LOG = new Logger(CustomActorSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(CustomActorSample.class);
    private final float WIDTH = 1080; //world units
    private final float HEIGHT = 720;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Stage stage;
    private Texture texture;


    @Override
    public void create( )
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera(  );
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        stage = new Stage(viewport, batch );
    }

    @Override
    public void resize( int width, int height )
    {
        viewport.update(width, height, true);
    }

    @Override
    public void render( )
    {
        viewport.apply();
    }

    @Override
    public void dispose( )
    {
        batch.dispose();
        stage.dispose();
    }
}
