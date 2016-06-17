package com.packtpub.libgdx.canyonbunny.game.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by leandro.silverio on 08/06/2016.
 */
public interface ScreenTransition {
    public float getDuration();

    public void render (SpriteBatch batch, Texture currScreen, Texture nextScreen, float alpha);
}
