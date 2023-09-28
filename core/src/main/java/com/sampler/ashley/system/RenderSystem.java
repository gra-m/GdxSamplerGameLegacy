package com.sampler.ashley.system;

import com.badlogic.ashley.core.*;
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
    private static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);
    private static final ComponentMapper<SizeComponent> SIZE =
            ComponentMapper.getFor(SizeComponent.class);
    private static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);


    private Viewport viewport;
    private SpriteBatch batch;
    private ImmutableArray< Entity > entities;


    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

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
        for (Entity e : entities) {
            TextureComponent texture = TEXTURE.get(e);
            PositionComponent position = POSITION.get(e);
            SizeComponent size = SIZE.get(e);
            batch.draw(texture.texture,
                    position.x, position.y,
                    size.width, size.height);
        }
    }
}
