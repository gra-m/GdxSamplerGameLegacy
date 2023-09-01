package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;


public class OrthographicCameraSample extends SampleBase {
    private static final Logger LOG = new Logger(OrthographicCameraSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(OrthographicCameraSample.class);

    private static final float WORLD_WIDTH = 10.8f; //World Units
    private static final float WORLD_HEIGHT = 7.2f;
    private static final float CAMERA_SPEED = 2.0f;
    private static final float CAMERA_ZOOM_SPEED = 2.0f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texture;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //GdxUtils.setGdxWorldView(camera, WORLD_WIDTH, WORLD_HEIGHT, 0);


        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/level-bg.png"));
    }

    @Override
    public void render() {
        queryInput();

        GdxUtils.clearScreen();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();

        batch.end();
    }

    private void queryInput() {
        // time between two frames 1/60
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            camera.position.x -= CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camera.position.x += CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            camera.position.y += CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            camera.position.y -= CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)){
            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)){
            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            LOG.debug(String.format("Position = %s\nZoom = %s", camera.position, camera.zoom));
        }

        // called after all of the above
        camera.update();

    }

    private void draw() {
        batch.draw(texture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

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
