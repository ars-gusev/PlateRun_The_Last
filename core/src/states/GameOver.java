package states;

import com.ars.game.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends State {

    private Texture background;
    private Texture gameover;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, MyGame.width / 2, MyGame.height / 2);
        background = new Texture("fon.jpg");
        gameover = new Texture("ov.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y - 40);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();

        System.out.println("GameOver Disposed");
    }
}
