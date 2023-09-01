package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

public class GdxModuleInfoSample implements ApplicationListener {
	static final Logger LOG =  new Logger(GdxModuleInfoSample.class.getName(), Logger.DEBUG);

	/**
	 * Called when the {@link Application} is first created.
	 */
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		LOG.debug("app=" + Gdx.app);
		LOG.debug("app=" + Gdx.audio);
		LOG.debug("app=" + Gdx.input);
		LOG.debug("app=" + Gdx.files);
		LOG.debug("app=" + Gdx.graphics);
		LOG.debug("app=" + Gdx.net);
	}

	/**
	 * Called when the {@link Application} is resized. This can happen at any point during a non-paused state but will never
	 * happen before a call to {@link #create()}.
	 *
	 * @param width  the new width in pixels
	 * @param height the new height in pixels
	 */
	@Override
	public void resize(int width, int height) {

	}

	/**
	 * Called when the {@link Application} should render itself.
	 */
	@Override
	public void render() {

	}

	/**
	 * Called when the {@link Application} is paused, usually when it's not active or visible on-screen. An Application is also
	 * paused before it is destroyed.
	 */
	@Override
	public void pause() {

	}

	/**
	 * Called when the {@link Application} is resumed from a paused state, usually when it regains focus.
	 */
	@Override
	public void resume() {

	}

	/**
	 * Called when the {@link Application} is destroyed. Preceded by a call to {@link #pause()}.
	 */
	@Override
	public void dispose() {

	}
}
