package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.CustomActor;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class TableSample extends SampleBase
{
    private static final Logger LOG = new Logger(TableSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(TableSample.class);
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

        initUi();
    }

    private void initUi( ) {
        Table table = new Table( );
        table.defaults().space(20);// set defaults for all table actors
        table.setFillParent(true);
        table.center(); // only in its own co-ordinates unless setFillParent
        Button button  = new Button(new Button.ButtonStyle(  ));

        for ( int i = 0; i < 6 ; i++ ) {
            CustomActor customActor = new CustomActor(new TextureRegion(texture));
            // default actor is 0,0
            customActor.setSize(180,50);
            table.add( customActor );
            table.row(); // sets it below last, default ==  right of
        }

        CustomActor wCustomActor = new CustomActor(new TextureRegion( texture ));
        wCustomActor.setSize(400, 30);
        table.add( wCustomActor ).expand(true, false).fill( true, false ).left();


        table.pack(); // best practice
        stage.setDebugAll(true);
        stage.addActor(table);
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
