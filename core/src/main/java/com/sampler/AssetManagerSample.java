package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;


import static com.sampler.assets.AssetPaths.*;

public class AssetManagerSample extends SampleBase
{
    private static final Logger LOG = new Logger(AssetManagerSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(AssetManagerSample.class);

    private AssetManager assetManager;
    private Camera camera;
    private SpriteBatch batch;
    private Viewport viewport;

    private Texture backgroundBlue;
    private Texture greenCircle;
    private Texture redCircle;
    private BitmapFont oswald32;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        camera = new OrthographicCamera(  );
        viewport = new FitViewport(1080, 720, camera);
        batch = new SpriteBatch(  );
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);


        // load assets one by one
        assetManager.load(BACKGROUND_BLUE, Texture.class);
        assetManager.load(CIRCLE_GREEN, Texture.class);
        assetManager.load(CIRCLE_RED, Texture.class);
        assetManager.load(OSWALD_FONT_32, BitmapFont.class);

        // blocks until all loaded
        assetManager.finishLoading();

        // get assets after they've been loaded
        backgroundBlue = assetManager.get(BACKGROUND_BLUE);
        redCircle = assetManager.get(CIRCLE_RED);
        greenCircle = assetManager.get(CIRCLE_GREEN);
        oswald32 = assetManager.get(OSWALD_FONT_32);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

    }

    @Override
    public void render( )
    {
        GdxUtils.clearScreen();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundBlue, 0, 0);
        batch.draw(redCircle, 75, 75);
        batch.draw(greenCircle, 200, 200);

        oswald32.draw(batch, "Asset Manager Sample", 500, 50);


        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        /*
        Disposed in one line in AssetManager:
        backgroundBlue.dispose();
        blueCircle.dispose();
        redCircle.dispose();
        font.dispose();*/

        assetManager.dispose();

    }

}
