package com.sampler;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class InputListeningSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(InputListeningSample.class);
    private static final Logger LOG = new Logger(InputListeningSample.class.getName(), Logger.DEBUG);
    private static final int MAX_MESSAGE_COUNT = 15;
    private final Array<String> messages = new Array<>();
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    //region ApplicationListenerMethods

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(1080, 720, camera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));

        Gdx.input.setInputProcessor(this);
        //createInputMultiplexer();


    }

    /**
     * Chaining -> any InputProcessor returning false means the next is called.
     * || return of true stops movement up the chain
     */
    private void createInputMultiplexer() {
        InputMultiplexer multiplexer = new InputMultiplexer();

        InputAdapter firstProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                LOG.debug("first - keyDown keycode = " + keycode + " true return means no second keyDown");
                return true;
            }
            @Override
            public boolean keyUp(int keycode) {
                LOG.debug("first - keyUp keycode = " + keycode);
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                LOG.debug("first - keyTyped character = " + character);
                return false;
            }
        };

        InputAdapter secondProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                LOG.debug("second - keyDown keycode = " + keycode);
                return true;
            }
            @Override
            public boolean keyUp(int keycode) {
                LOG.debug("second - keyUp keycode = " + keycode);
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                LOG.debug("second - keyTyped character = " + character);
                return false;
            }
        };

        multiplexer.addProcessor(firstProcessor);
        multiplexer.addProcessor(secondProcessor);

        Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

    }

    // Listening ==> constant
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
        for (int i = 0; i < messages.size; i++) {
            font.draw(batch, messages.get(i),
                    20.0f,
                    720 - 40.0f * (i + 1)
            );
        }
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
    //endregion

    private void addMessage(String message) {
        if ( message == null || message.isEmpty() )
           LOG.info("Message null or empty");

        messages.add(message);

        if (messages.size > MAX_MESSAGE_COUNT)
            messages.removeIndex(0);
    }

    //region InputProcessorMethods
    @Override
    public boolean keyDown(int keycode) {
        String message = "keyDown keycode= " + keycode;
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        String message = "keyUp keycode= " + keycode;
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        String message = "keyTyped character= " + character;
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        String message = String.format("touchDown screenX= %d screenY= %d", screenX, screenY);
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        String message = String.format("touchUp screenX= %d screenY= %d", screenX, screenY);
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        String message = String.format("touchCancelled screenX= %d screenY= %d", screenX, screenY);
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        String message = String.format("touchDragged screenX= %d screenY= %d", screenX, screenY);
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        String message = String.format("mouseMoved screenX= %d screenY= %d", screenX, screenY);
        LOG.debug(message);
        addMessage(message);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        String message = String.format("scrolled screenX= %s screenY= %s", amountX, amountY);
        LOG.debug(message);
        addMessage(message);
        return true;
    }
    //endregion
}
