package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class SkinSample extends SampleBase
{
    private static final Logger LOG = new Logger(SkinSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(SkinSample.class);
    private static final float WORLD_WIDTH = 1080; //world units
    private static final float WORLD_HEIGHT = 720;
    private static final String UI_SKIN =  "ui/uiskin.json";

    private Viewport viewport;

    private Stage stage;
    private AssetManager assetManager;
    private Skin skin;


    @Override
    public void create( )
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        assetManager = new AssetManager(  );
        assetManager.getLogger().setLevel(Logger.DEBUG);

        assetManager.load(UI_SKIN, Skin.class);
        assetManager.finishLoading();




        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);  // creates camera for itself
        stage = new Stage(viewport); // creates internal batch for itself
        skin = assetManager.get(UI_SKIN);

        Gdx.input.setInputProcessor(stage); // for listenersb
        initUi();
    }

    private void initUi( ) {
        Table table = new Table(  );
        table.defaults().pad(20);

        // ROW 1:
        for ( int i = 0; i < 4; i++ ) {
            TextButton textButton = new TextButton("Button" + i, skin);
            textButton.addListener(new ChangeListener( )
            {
                @Override
                public void changed( ChangeEvent event, Actor actor )
                {
                    LOG.debug(String.format("event = %s actor = %s", event, actor));

                }
            });
            table.add( textButton );
        }

        table.row();
        //ROW 2
        for ( int i = 0; i < 2; i++ ) {
            CheckBox checkBox = new CheckBox("check" + i, skin);
            checkBox.addListener(new ChangeListener( )
            {
                @Override
                public void changed( ChangeEvent event, Actor actor )
                {
                    LOG.debug(String.format("event = %s, actor = %s, status = %s", event, actor,
                        checkBox.isChecked() ));
                }
            });
            table.add( checkBox );
        }

        for ( int i = 2; i < 4; i++ ) {
            CheckBox checkBox = new CheckBox("check" + i, skin, "custom");
            checkBox.addListener(new ChangeListener( )
            {
                @Override
                public void changed( ChangeEvent event, Actor actor )
                {
                    LOG.debug(String.format("event = %s, actor = %s, status = %s", event, actor,
                        checkBox.isChecked() ));
                }
            });
            table.add( checkBox );
        }
        table.row();

        //ROW3
        for ( int i = 4; i < 6; i++ ) {
            CheckBox checkBox = new CheckBox("check" + i, skin, "custom2");
            checkBox.addListener(new ChangeListener( )
            {
                @Override
                public void changed( ChangeEvent event, Actor actor )
                {
                    LOG.debug(String.format("event = %s, actor = %s, status = %s", event, actor,
                        checkBox.isChecked() ));
                }
            });
            table.add( checkBox );
        }

        table.center();
        table.setFillParent(true);
        table.pack();

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
    }
}
