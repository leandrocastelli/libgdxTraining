package com.packtpub.libgdx.canyonbunny.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.canyonbunny.game.WorldController;
import com.packtpub.libgdx.canyonbunny.game.WorldRenderer;

/**
 * Created by leandro.silverio on 01/06/2016.
 */
public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;
    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        //Do not update game world when paused.
        if (!paused) {
            //Update Game world by the time that has passed since last rendered frame
            worldController.update(delta);
        }
        //Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff /255.0f);
        //Clears the Screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Render the Game World
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void resume() {
        super.resume();

        paused = false;
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);

    }
}
