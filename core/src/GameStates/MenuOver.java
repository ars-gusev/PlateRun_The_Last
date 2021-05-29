package GameStates;

import com.ars.game.MyGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuOver extends State {

    private Texture background;
    private Texture over;
    private Texture score;
    private Texture over_monetka;

    public MenuOver(GameManager gsm) {
        super(gsm);
        camera.setToOrtho(false, MyGame.width / 2, MyGame.height / 2);
        background = new Texture("fon.jpg");
        over = new Texture("over.png");
        score = new Texture("score.png");
        over_monetka = new Texture("menu_coin.png");

    }


    @Override
    protected void handleInput() {
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gsm.push(new MenuPlay(gsm));
        MyGame.music.play();
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
        sb.draw(over, camera.position.x - over.getWidth() / 2, camera.position.y + 60);
        sb.draw(score, camera.position.x - score.getWidth() / 2, camera.position.y - 20);
        if (PlayState.cur_record < 10) {
            sb.draw(MenuPlay.numbers.get(PlayState.cur_record), camera.position.x - MenuPlay.numbers.get(PlayState.cur_record).getWidth() / 2, camera.position.y - 80);
        } else if (PlayState.cur_record < 100) {
            int first = PlayState.cur_record / 10;
            int second = PlayState.cur_record % 10;
            sb.draw(MenuPlay.numbers.get(first), camera.position.x - 15, camera.position.y - 80);
            sb.draw(MenuPlay.numbers.get(second), camera.position.x + 6, camera.position.y - 80);
        } else {
            int first = PlayState.cur_record / 100;
            int second = PlayState.cur_record % 100 / 10;
            int third = PlayState.cur_record % 10;
            sb.draw(MenuPlay.numbers.get(first), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2 - 15, camera.position.y - 80);
            sb.draw(MenuPlay.numbers.get(second), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2 + 5, camera.position.y - 80);
            sb.draw(MenuPlay.numbers.get(third), camera.position.x + 23, camera.position.y - 80);
        }

        sb.draw(over_monetka, 70, 10);
        if (PlayState.cur_coins < 10) {
            sb.draw(MenuPlay.numbers.get(PlayState.cur_coins), camera.position.x - MenuPlay.numbers.get(PlayState.cur_coins).getWidth() / 2, 10);
        } else if (PlayState.cur_coins < 100) {
            int first = PlayState.cur_coins / 10;
            int second = PlayState.cur_coins % 10;
            sb.draw(MenuPlay.numbers.get(first), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2, 10);
            sb.draw(MenuPlay.numbers.get(second), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2 + 22, 10);
        } else {
            int first = PlayState.cur_coins / 100;
            int second = PlayState.cur_coins % 100 / 10;
            int third = PlayState.cur_coins % 10;
            sb.draw(MenuPlay.numbers.get(first), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2, 10);
            sb.draw(MenuPlay.numbers.get(second), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2 + 22, 10);
            sb.draw(MenuPlay.numbers.get(third), camera.position.x - MenuPlay.numbers.get(first).getWidth() / 2 + 44, 10);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        over.dispose();
        score.dispose();

        System.out.println("GameOver Disposed");
    }
}
