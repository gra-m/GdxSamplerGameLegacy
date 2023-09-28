package com.sampler.ashley.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.ashley.component.PositionComponent;
import com.sampler.ashley.component.SizeComponent;
import com.sampler.ashley.component.TextureComponent;

// used for rendering objects, usually extend existing system class
public class RenderSystem extends EntitySystem
{
    private static final Logger LOG = new Logger(RenderSystem.class.getName(), Logger.DEBUG);
    // Family == a group of things that have these characteristics that can be acted upon.
    private static final Family FAMILY =  Family.all(
            PositionComponent.class,
            SizeComponent.class,
            TextureComponent.class
    ).get();
    private Viewport viewport;
    private SpriteBatch batch;
    private ImmutableArray< Entity > entities;



    // entities array matching this family is created once this method is called, that is once this system is added
    // to engine
    @Override
    public void addedToEngine( Engine engine )
    {
        LOG.debug(String.format("%s was added to engine", this.getClass().getSimpleName()));
        entities = engine.getEntitiesFor(FAMILY);
        LOG.debug(String.format("family entities =  %s, \n family size = %d \n all entity size = %d",
                entities.toString( "," ), entities.size(), engine.getEntities().size()));
    }

    // update in System  called every frame EngineUpdate -> All added systemsUpdate
    @Override
    public void update( float deltaTime )
    {
        // called every frame

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw( );

        batch.end();

    }

    private void draw( ) {
        for (Entity e : entities)// drawing without component mapper is slow..
    }


}
