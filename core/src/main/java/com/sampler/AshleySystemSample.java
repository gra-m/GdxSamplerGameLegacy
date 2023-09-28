package com.sampler;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.ashley.component.PositionComponent;
import com.sampler.ashley.component.SizeComponent;
import com.sampler.ashley.component.TextureComponent;
import com.sampler.ashley.system.RenderSystem;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class AshleySystemSample extends SampleBase
{
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(AshleySystemSample.class);
    private static final Logger LOG = new Logger(AshleySystemSample.class.getName( ), Logger.DEBUG);

    private static final float WORLD_WIDTH = 10.8f; // world units
    private static final float WORLD_HEIGHT = 7.2f;
    private static final String LEVEL_BG = "raw/level-bg.png";
    private static final String CHAR = "raw/character.png";

    private AssetManager assetManager;
    private Viewport viewport;
    private SpriteBatch batch;
    private Engine engine;


    @Override
    public void create( )
    {
        // app log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // initialising
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        engine = new Engine();

        // load assets
        assetManager.load(LEVEL_BG, Texture.class);
        assetManager.load(CHAR, Texture.class);
        assetManager.finishLoading();

        // how a background is composed of / has components in Ashley, rather than being e.g. a child class of e.g
        // GameObject
        composeAndAddPositionSizeAndTextureEntity(LEVEL_BG, true);
        composeAndAddPositionSizeAndTextureEntity(CHAR, false);
        

        engine.addSystem(new RenderSystem(viewport, batch));
    }

    private void composeAndAddPositionSizeAndTextureEntity(String texturePath, Boolean background ) {

        // initialize components that background will have:
        Array< Component> components = new Array<>(3);
        Array<Component> initializedComponents = initializeComponents(components, texturePath, background);

        // initialize backgroundEntity
        Entity  entity = new Entity();

        // add components background entity needs:
        for(Component c : initializedComponents) {
            entity.add(c);
            LOG.debug(String.format("Have just added: %s", c.getClass().getSimpleName()));
        }
        // add background entity to our world
        engine.addEntity(entity);

        LOG.debug("texture retrieved from the entity just added to the engine FORGOT TO RESIZE VIEWPORT = " +
                engine.getEntities().get(0).getComponent(TextureComponent.class).texture.toString());
    }

    private Array< Component> initializeComponents( Array<Component> components, String texturePath,
                                                    boolean background ) {

        PositionComponent position = new PositionComponent();
        position.x = 0;
        position.y = 0;
        SizeComponent size = new SizeComponent();
        if (background) {
            size.width = WORLD_WIDTH;
            size.height = WORLD_HEIGHT;
        }else {
            size.width = 2f;
            size.height = 2f;
        }
        TextureComponent texture = new TextureComponent();
        texture.texture = assetManager.get(texturePath);
        components.add(position, size, texture);

        return components;

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

        float delta = Gdx.graphics.getDeltaTime();
        engine.update(delta); // as in 2D update -> runs every frame
    }

    @Override
    public void dispose( )
    {
        assetManager.dispose();
        batch.dispose();
    }
}
