package com.sampler.ashley.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Logger;
import com.sampler.ashley.component.PositionComponent;
import com.sampler.ashley.component.SizeComponent;
import com.sampler.ashley.component.TextureComponent;

// used for rendering objects, usually extend existing system class
public class RenderSystem extends EntitySystem
{
    private static final Logger LOG = new Logger(RenderSystem.class.getName(), Logger.DEBUG);
    private static final Family FAMILY =  Family.all(
            PositionComponent.class,
            SizeComponent.class,
            TextureComponent.class
    ).get();



    @Override
    public void addedToEngine( Engine engine )
    {
    }
}
