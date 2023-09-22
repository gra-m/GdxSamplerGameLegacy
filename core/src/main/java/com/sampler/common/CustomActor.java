package com.sampler.common;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;

public class CustomActor extends Actor
{
    private static final Logger LOG = new Logger(CustomActor.class.getName(), Logger.DEBUG);
    private final TextureRegion region;


    public CustomActor( TextureRegion region ) { this.region = region; }

    @Override
    public void act( float delta ) {
        LOG.debug("act on actor = " + this);
        super.act(delta);
    }

    @Override
    public void draw( Batch batch, float parentAlpha )
    {
        Color color = getColor();
        batch.setColor(color);

        batch.draw(
            region,
            getX(),
            getY(),
            getOriginX(),
            getOriginY(),
            getWidth(),
            getHeight(),
            getScaleX(),
            getScaleY(),
            getRotation()
        );
    }



}
