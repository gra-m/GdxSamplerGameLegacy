package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.assets.AssetPaths;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

import static com.sampler.assets.AssetPaths.*;

public class TextureAtlasSample extends SampleBase
{
    private static final Logger LOG = new Logger(TextureAtlasSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(TextureAtlasSample.class);
    private static final String ATLAS = "images/atlasSample.atlas";

    // Extracting atlas names from constant paths. Atlas uses: e.g. background-blue, quicker to type but..
    private static final String BACKGROUND_BLUE =
        AssetPaths.BACKGROUND_BLUE.substring(  (AssetPaths.BACKGROUND_BLUE.indexOf('/') + 1),
        AssetPaths.BACKGROUND_BLUE.lastIndexOf('.'));
    private static final String CIRCLE_GREEN =
        AssetPaths.CIRCLE_GREEN.substring(  (AssetPaths.CIRCLE_GREEN.indexOf('/') + 1),
            AssetPaths.CIRCLE_GREEN.lastIndexOf('.'));
    private static final String CIRCLE_RED =
        AssetPaths.CIRCLE_RED.substring(  (AssetPaths.CIRCLE_RED.indexOf('/') + 1),
            AssetPaths.CIRCLE_RED.lastIndexOf('.'));

    private BitmapFont oswald32;
    private TextureRegion backgroundBlue;
    private TextureRegion greenCircle;
    private TextureRegion redCircle;


    private AssetManager assetManager;
    private Camera camera;
    private SpriteBatch batch;
    private Viewport viewport;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        LOG.debug("Value of substrings : " + BACKGROUND_BLUE + CIRCLE_GREEN + CIRCLE_RED);
        camera = new OrthographicCamera(  );
        viewport = new FitViewport(1080, 720, camera);
        batch = new SpriteBatch(  );
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        // lets look at our AssetManager before load:
        LOG.debug("asset Manager contains: " + assetManager.getLoadedAssets() + " assetManager " +
            "diagnostics: " + assetManager.getDiagnostics());


        // load atlas of textures in one go
        assetManager.load(ATLAS, TextureAtlas.class);
        assetManager.load(OSWALD_FONT_32, BitmapFont.class);

        // blocks until all loaded
        assetManager.finishLoading();

        // after load
        LOG.debug("asset Manager contains: " + assetManager.getLoadedAssets() + " assetManager diagnostics: " + assetManager.getDiagnostics());
        // get assets after they've been loaded
        TextureAtlas atlas = assetManager.get(ATLAS); // Asset manager will dispose of this

        backgroundBlue = atlas.findRegion(BACKGROUND_BLUE);
        redCircle = atlas.findRegion(CIRCLE_RED);
        greenCircle = atlas.findRegion(CIRCLE_GREEN);
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
