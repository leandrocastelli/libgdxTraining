package com.packtpub.libgdx.canyonbunny.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.canyonbunny.util.Constants;

/**
 * Created by leandro.silverio on 13/05/2016.
 */
public class WorldRenderer implements Disposable{
    private OrthographicCamera camera;
    private OrthographicCamera cameraGui;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        cameraGui = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGui.position.set(0, 0, 0);
        cameraGui.setToOrtho(true); //flip y-axis
        cameraGui.update();
    }

    public void render() {
        renderWorld(batch);
        renderGui(batch);
    }

    private void renderWorld (SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        cameraGui.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGui.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float) width;
        cameraGui.position.set(cameraGui.viewportWidth / 2, cameraGui.viewportHeight / 2, 0 );
        cameraGui.update();
    }
    @Override
    public void dispose() {
        batch.dispose();
    }

    private void renderGuiScore (SpriteBatch batch) {
        float x = -15;
        float y = -15;

        batch.draw(Assets.instance.goldCoin.goldCoin, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
        Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
    }

    private void renderGuiExtraLive (SpriteBatch batch) {
        float x = cameraGui.viewportWidth - 50 - Constants.LIVES_START * 50;
        float y = -15;
        for (int i = 0; i < Constants.LIVES_START; i++) {
            if (worldController.lives <= i) {
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);

            }
            batch.draw(Assets.instance.bunny.head,
                    x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
            batch.setColor(1, 1, 1, 1);
        }
    }
    private void renderGuiFpsCounter (SpriteBatch batch) {
        float x = cameraGui.viewportWidth - 55;
        float y = cameraGui.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
            //45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show yp in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 fps show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: "+ fps, x , y);
        fpsFont.setColor(1, 1, 1, 1);
    }

    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGui.combined);
        batch.begin();
        renderGuiScore(batch);
        renderGuiExtraLive(batch);
        renderGuiFpsCounter(batch);
        batch.end();
    }
}
