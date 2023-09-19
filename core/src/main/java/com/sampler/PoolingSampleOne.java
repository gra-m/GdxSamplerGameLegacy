package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;

public class PoolingSampleOne extends SampleBase
{
    private static final Logger LOG = new Logger(PoolingSampleOne.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(PoolingSampleOne.class);

    private static final float BULLET_ALIVE_TIME = 3f;
    private static final float BULLET_SPAWN_TIME = 1f;
    private Array<Bullet> bullets = new Array<>();
    private float timer;

    private final Pool<Bullet> bulletPool = Pools.get(Bullet.class, 15);

    // called every frame
    @Override
    public void render() {

        // Pool lifecycle spawning bullets
        float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        if (timer > BULLET_SPAWN_TIME) {
            timer = 0;
            Bullet bullet = bulletPool.obtain();
            bullets.add(bullet);
            LOG.debug("After creating new bullet = " + bullets.size);
        }

        // Bullet lifecycle updating and removing bullets

        for ( int i = 0; i < bullets.size ; i++ ) {
            Bullet bulletToUpdate = bullets.get(i);
            bulletToUpdate.update(delta);

            if (!bulletToUpdate.alive) {
                bullets.removeIndex(i);
                bulletPool.free(bulletToUpdate); // returns it to pool
                LOG.debug("After freeing to pool bullets = " + bullets.size);
            }
        }
    }

    public static class Bullet implements Pool.Poolable
    {
        boolean alive = true;
        float timer;

        Bullet(){}

        public void update(float delta) {
            timer += delta;

            if (alive && timer > BULLET_ALIVE_TIME) {
                alive = false;
            }
        }


        // Automatically called by pool, you need to set your object back to new-minted state
        @Override
        public void reset( )
        {
            alive = true;
            timer = 0f;
        }
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }
    @Override
    public void dispose() {
        bulletPool.freeAll(bullets);
        bulletPool.clear();
        bullets.clear();
    }
}
