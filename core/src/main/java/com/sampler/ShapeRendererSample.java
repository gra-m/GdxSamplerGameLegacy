package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class ShapeRendererSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ShapeRendererSample.class);

    private static final float WORLD_HEIGHT = 40f;
    private static final float WORLD_WIDTH = 20f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private boolean drawGrid = true;
    private boolean drawCircles = true;
    private boolean drawRectangles = true;
    private boolean drawPoints = true;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        // NOT centering camera
        viewport.update(width, height);

    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        renderer.setProjectionMatrix(camera.combined);
        if(drawGrid) {
            drawGrid();
        }
    }

    private void drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

         int worldWidth = (int) WORLD_WIDTH;
         int worldHeight = (int) WORLD_HEIGHT;

        //verticals
        for (int x = -worldWidth; x < worldHeight ; x++) {
           renderer.line(x, -worldHeight, x, worldHeight);
        }
        //horizontals
        for (int y = -worldHeight; y < worldHeight ; y++) {
            renderer.line(-worldWidth, y, worldWidth, y);
        }

        renderer.setColor(Color.RED);
        renderer.line(-worldWidth, 0.0f, worldWidth, 0.0f);
        renderer.line(0.0f, -worldHeight, 0.0f, worldHeight);


        renderer.end();

    }

    @Override
    public void dispose() {
        renderer.dispose();

    }


}
