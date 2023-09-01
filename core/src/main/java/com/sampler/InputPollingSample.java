package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

import static com.badlogic.gdx.Gdx.input;


public class InputPollingSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(InputPollingSample.class);
    private final Logger LOG = new Logger(InputPollingSample.class.getName(), Logger.DEBUG);
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(1080, 720, camera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

    }

    // Polling for new input has to take place in render method, which is @ 60 fps.
    @Override
    public void render() {
        GdxUtils.clearScreen();

        // camera position and zoom
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // everything drawn on screen between these
        draw();
        batch.end();

    }

    private void draw() {
        // mouse/touch x/y
        int mouseX = input.getX();
        int mouseY = input.getY();
        boolean leftPressed = input.isButtonPressed(Input.Buttons.LEFT);
        boolean rightPressed = input.isButtonPressed(Input.Buttons.RIGHT);
        boolean wPressed = input.isKeyPressed(Input.Keys.W);
        boolean sPressed = input.isKeyPressed(Input.Keys.S);

        //Mouse position
        font.draw(batch,
                String.format("Mouse/Touch x = %s y = %s", mouseX, mouseY), 20f, 720 - 20f);
        //buttons
        font.draw(batch,
                leftPressed ? "Left button pressed" : "Left button not pressed", 20f, 720 - 60f);

        font.draw(batch,
                rightPressed ? "Right button pressed" : "Right button not pressed", 20f, 720 - 100f);
        //keys

        font.draw(batch,
                wPressed ? "W button pressed" : "W button not pressed", 20f, 720 - 140f);

        font.draw(batch,
                sPressed ? "S button pressed" : "S button not pressed", 20f, 720 - 180f);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();

    }
}
