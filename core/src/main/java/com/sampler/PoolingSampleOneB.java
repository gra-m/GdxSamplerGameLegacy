package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;

public class PoolingSampleOneB extends SampleBase
{
    private static final Logger LOG = new Logger(PoolingSampleOneB.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(PoolingSampleOneB.class);

    private static final float BULLET_ALIVE_TIME = 15f;
    private static final float BULLET_SPAWN_TIME = 1f;
    private Array<Bullet> bullets = new Array<>();
    private float timer;

    @Override
    public void dispose() {
        bulletPool.freeAll(bullets);
        bulletPool.clear();
        bullets.clear();
    }

    private final Pool<Bullet> bulletPool = new Pool<  >( )
    {
        @Override
        protected Bullet newObject( )
        {
            LOG.debug("Creating newObject( ) in non overridden method of  Pool class creates new Bullet via " +
                "ClassReflection.newInstance(Bullet.class)");
            return new Bullet();
        }
        @Override
        public void free(Bullet object) {
            LOG.debug("This is how and when Pool free's an object (returns it to Pool) before: " + object + " free = " + getFree());
            super.free(object);
            LOG.debug("This after Pool has freed that object: " + object + " free decrease by 1 = " + getFree());
        }
        @Override
        public Bullet obtain() {

            LOG.debug("Before obtain free = " + getFree());
            Bullet ret = super.obtain();
            LOG.debug("After obtain free = " + getFree());
            return ret;
        }
        @Override
        public void reset(Bullet ob) {
            LOG.debug("Resetting object " + ob);
            super.reset(ob);

        }
    };

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

}
