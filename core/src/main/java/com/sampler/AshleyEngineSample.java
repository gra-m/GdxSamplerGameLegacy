package com.sampler;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

// Engine == core class and centre of framework, typically only one instance.
// Engine is our world, entities are added and removed from our world with Engine.addEntity/removeEntity
public class AshleyEngineSample extends SampleBase
{
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(AshleyEngineSample.class);
    private static final Logger LOG = new Logger(AshleyEngineSample.class.getName( ), Logger.DEBUG);

    private static final float SPAWN_TIME = 1f;
    public static final float REMOVE_TIME = 3f;

    private Engine engine;

    private Array< Entity > bullets = new Array<>(  );

    private float spawnTimer;
    private float removetimer;

    @Override
    public void create( )
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        engine = new Engine();
        engine.addEntityListener(new EntityListener( )
        {
            @Override
            public void entityAdded( Entity entity )
            {
                LOG.debug(String.format("+ Entity %s added", entity.toString()));
                LOG.debug(String.format("+ %s total entities", engine.getEntities().size()));
            }

            @Override
            public void entityRemoved( Entity entity )
            {
                LOG.debug(String.format("- Entity %s removed", entity.toString()));
                LOG.debug(String.format("- %s total entities", engine.getEntities().size()));
            }
        });
        
        addBullet();
    }

    private void addBullet( ) {
        Entity bullet = new Entity();
        bullets.add(bullet);
        engine.addEntity(bullet);
    }

    @Override
    public void render( )
    {
        GdxUtils.clearScreen();
        float delta = Gdx.graphics.getDeltaTime( );
        engine.update(delta);

        spawnTimer += delta;

        if (spawnTimer > SPAWN_TIME) {
            spawnTimer = 0;
            addBullet();
        }

        removetimer += delta;

        if (removetimer > REMOVE_TIME) {
            removetimer = 0;
            if (bullets.size > 0) {
                Entity bullet = bullets.first();
                bullets.removeValue(bullet, true);
                engine.removeEntity(bullet);
            }
        }
    }
}
