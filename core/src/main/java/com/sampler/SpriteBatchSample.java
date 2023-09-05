package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class SpriteBatchSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(SpriteBatchSample.class);

    // All in WUs -> world units
    private static final float WORLD_WIDTH = 10.8f;
    private static final float WORLD_HEIGHT = 7.2f;
    private int width = 1;
    private int height = 1;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texture;
    private Color oldColor;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        oldColor = new Color();
        texture = new Texture(Gdx.files.internal("raw/character.png"));
    }

    @Override
    public void render() {
        // Spritebatch == high performance rendering via optimisation of render requests
        GdxUtils.clearScreen();
        // Tell Spritebatch about camera configuration == geometry worked out position/rotation/zoom

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        draw();

        batch.end();

    }

    private void draw() {
         batch.draw(texture, 1, 1,                // draw origin 0,0 bottom left 1, 1 would be whatever 1 wu is
             // as 1 == wu
             width / 2f, height / 2f,            // point around which sprite rotates
             width, height,                              // 1, 1  width and height
             1.0f, 1.0f,                          // no scaling
             0.0f,                                        // no rotation
             0, 0,                                  // source x and y of texture
             texture.getWidth(), texture.getHeight(),       // actual texture width/height?
             false, false);              // flip x /flip y

        batch.draw(texture, 4, 2,
            width / 2f, height / 2f,
            width, height,
            2.0f, 2.0f,
            0.0f,
            0, 0,
            texture.getWidth(), texture.getHeight(),
            false, true);

        // save batch colour:
        oldColor.set(batch.getColor());

        // new  color
        batch.setColor(Color.GREEN);

        batch.draw(texture, 8, 1,
            width / 2f, height / 2f,
            width, height,
            1.0f, 1.0f,
            0.0f,
            0, 0,
            texture.getWidth(), texture.getHeight(),
            false, false);

        batch.setColor(oldColor);

    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
