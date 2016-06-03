package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.canyonbunny.game.WorldController;
import com.packtpub.libgdx.canyonbunny.game.WorldRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.canyonbunny.game.Assets;
import com.packtpub.libgdx.canyonbunny.game.screens.MenuScreen;

public class CanyonBunnyMain extends Game {
    private static final String TAG = CanyonBunnyMain.class.getName();


    @Override
    public void create() {
        //Set Libgdx Log Level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //Load Assets
        Assets.instance.init(new AssetManager());
        //Start game at menu screen
        setScreen(new MenuScreen(this));
    }
}