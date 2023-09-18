package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    private GlyphLayout glyphLayout;

    @Override
    public void create( )
    {
        camera = new OrthographicCamera(  );
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();
        glyphLayout = new GlyphLayout();
        uIFont = new BitmapFont( Gdx.files.internal("fonts/ui_font_32.fnt") );
        uIFont.getData().markupEnabled = true;
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
        String string1 = "USING CAPS ONLY BITMAP FONT CREATED IN HIERO!";
        uiEffectFont.draw(batch, string1, 20, HEIGHT);
        String string2 = "GLYPHLAYOUT.SET-TEXT == PLACE CORRECTLY ON SCREEN!";
        glyphLayout.setText(uIFont, string2);
        uiEffectFont.draw(batch, string2,
            (WIDTH - glyphLayout.width) / 2f,
            (HEIGHT -  glyphLayout.height) / 2f );

        String string3 = "[#FF0000]WRAPPING [BLUE]TEXT [GREEN]170 W [YELLOW]U MARKUP ENABLED";
        uIFont.draw(batch, string3,40, 200, 170, -1, true);
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
