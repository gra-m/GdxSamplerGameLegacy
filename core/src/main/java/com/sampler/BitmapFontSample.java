package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;

public class BitmapFontSample extends SampleBase
{
    private static final Logger LOG = new Logger(BitmapFontSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(BitmapFontSample.class);

    public static final float WIDTH = 1080f; // world units 1 == pixel 1
    public static final float HEIGHT = 720f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont uIFont;
    private BitmapFont uiEffectFont;

    @Override
    public void create( )
    {
        camera = new OrthographicCamera(  );
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();
        uIFont = new BitmapFont( Gdx.files.internal("fonts/ui_font_32.fnt") );
        uiEffectFont = new BitmapFont( Gdx.files.internal("fonts/ui_eff_font_32.fnt") );
    }

    @Override
    public void render( )
    {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw( ) {
        String string1 = "Using bitmap font created in Hiero";
        uiEffectFont.draw(batch, string1, 0, 32);
    }

    @Override
    public void resize( int width, int height )
    {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose( )
    {
        batch.dispose();
        uiEffectFont.dispose();
        uIFont.dispose();
    }
}
