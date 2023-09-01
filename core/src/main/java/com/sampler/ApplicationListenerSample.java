package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

public class ApplicationListenerSample implements ApplicationListener {
	private static final Logger LOG = new Logger(ApplicationListenerSample.class.getName(), Logger.DEBUG);
	private boolean renderInterruptedByPauseOrResume = true;

	/**
	 * Initialises game and loads resources
	 * Called when the {@link } is first created.
	 */
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		LOG.debug("create()");

	}

	/**
	 * For setting new screen size 
	 * Called when the {@link } is resized. This can happen at any point during a non-paused state but will never
	 * happen before a call to {@link #create()}.
	 *
	 * @param width  the new width in pixels
	 * @param height the new height in pixels
	 */
	@Override
	public void resize(int width, int height) {
		LOG.debug(String.format("resize() width = %s height = %s", width, height));
	}

	/**
	 * Called 60 times per second to render the game
	 * Called when the {@link } should render itself.
	 */
	@Override
	public void render() {
		if (renderInterruptedByPauseOrResume) {
			LOG.debug("render()");
			renderInterruptedByPauseOrResume = false;
		}
	}

	/**
	 * Used to save the game state (pause gameplay) when it (for example) loses focus
	 * (android back-button, desktop -> other window takes focus?)
	 * Actually dev choice whether gameplay paused or not.
	 *
	 * Called when the {@link } is paused, usually when it's not active or visible on-screen. An Application is also
	 * paused before it is destroyed.
	 */
	@Override
	public void pause() {
		LOG.debug("pause()");
		renderInterruptedByPauseOrResume = true;
	}

	/** Restores game state following pause.
	 * Called when the {@link } is resumed from a paused state, usually when it regains focus.
	 */
	@Override
	public void resume() {
		LOG.debug("resume()");
		renderInterruptedByPauseOrResume = true;
	}

	/**
	 * Free resources and clean up game
	 * Called when the {@link } is destroyed. Preceded by a call to {@link #pause()}.
	 */
	@Override
	public void dispose() {
		LOG.debug("dispose()");
	}
}
