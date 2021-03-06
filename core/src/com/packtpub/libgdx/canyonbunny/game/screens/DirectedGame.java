package com.packtpub.libgdx.canyonbunny.game.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.packtpub.libgdx.canyonbunny.game.screens.transitions.ScreenTransition;


/**
 * Created by leandro.silverio on 15/06/2016.
 */
public abstract class DirectedGame implements ApplicationListener{

    private boolean init;
    private AbstractGameScreen currScreen;
    private AbstractGameScreen nextScreen;
    private FrameBuffer currFbo;
    private FrameBuffer nextFbo;
    private SpriteBatch batch;
    private float t;
    private ScreenTransition screenTransition;

    public void setScreen(AbstractGameScreen screen) {
        setScreen(screen, null);
    }

    protected void setScreen(AbstractGameScreen screen, ScreenTransition screenTransition) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        if (!init) {
            currFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false );
            nextFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false );
            batch = new SpriteBatch();
            init = true;
        }
        //start new transition
        nextScreen = screen;
        nextScreen.show();; //activate next screen
        nextScreen.resize(w, h);
        nextScreen.render(0); // let screen update() once
        if (currScreen != null) currScreen.pause();
        nextScreen.pause();
        Gdx.input.setInputProcessor(null); // disable input
        this.screenTransition = screenTransition;
        t = 0;

    }

    @Override
    public void render() {
        //get delta time and ensure an upper limit of one 60th second
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
        if (nextScreen == null) {
            // no ongoing transition
            if (currScreen != null) currScreen.render(deltaTime);
        } else {
            //on going transition
            float duration = 0;
            if (screenTransition != null)
                duration = screenTransition.getDuration();
            //update progress of ongoing transition
            t = Math.min (t + deltaTime, duration);
            if (screenTransition == null || t >= duration) {
                //no transiction effect set or transition has just finished
                if (currScreen != null) currScreen.hide();
                nextScreen.resume();
                // enable input for next screen
                Gdx.input.setInputProcessor(nextScreen.getInputProcessor());

                //switch screens
                currScreen = nextScreen;
                nextScreen = null;
                screenTransition = null;
            } else {
                //render screens to FBOs
                currFbo.begin();
                if (currScreen != null) currScreen.render(deltaTime);
                currFbo.end();
                nextFbo.begin();
                nextScreen.render(deltaTime);
                nextFbo.end();

                //render transition effect to screen
                float alpha = t / duration;
                screenTransition.render(batch, currFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(), alpha);
            }
        }
    }
}
