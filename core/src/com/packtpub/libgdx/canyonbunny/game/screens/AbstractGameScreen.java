package com.packtpub.libgdx.canyonbunny.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * Created by leandro.silverio on 31/05/2016.
 */
public abstract class AbstractGameScreen implements Screen{
    protected Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public abstract void render(float delta) ;

    @Override
    public abstract void resize(int width, int height) ;

    @Override
    public abstract void pause() ;

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }

    @Override
    public abstract void hide();

    @Override
    public void dispose() {
    Assets.instance.dispose();
    }
}
