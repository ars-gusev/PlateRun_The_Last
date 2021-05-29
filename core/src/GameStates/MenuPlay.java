package GameStates;

import com.ars.game.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.awt.Button;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class MenuPlay extends State {

    private Texture back;
    private Texture playButton;
    public Texture record;
    private Texture monetka;
//    Stage stage;
//    TextButton button;
//    Skin skin;

    public static Texture n0, n1, n2, n3, n4, n5, n6, n7, n8, n9;
    public static Array<Texture> numbers;
//    private ExtendViewport viewport;
//    private Table table;
//    private OrthographicCamera cam;

    public MenuPlay(final GameManager gsm) {
        super(gsm);
        camera.setToOrtho(false, MyGame.width / 2, MyGame.height / 2);
        back = new Texture("fon.jpg");
        playButton = new Texture("play.png");
        monetka = new Texture("menu_coin.png");
//        playButton = new Actor();
        record = new Texture("record.png");


        n0 = new Texture("0.png");
        n1 = new Texture("1.png");
        n2 = new Texture("2.png");
        n3 = new Texture("3.png");
        n4 = new Texture("4.png");
        n5 = new Texture("5.png");
        n6 = new Texture("6.png");
        n7 = new Texture("7.png");
        n8 = new Texture("8.png");
        n9 = new Texture("9.png");

        numbers = new Array<Texture>();
        numbers.add(n0);
        numbers.add(n1);
        numbers.add(n2);
        numbers.add(n3);
        numbers.add(n4);
        numbers.add(n5);
        numbers.add(n6);
        numbers.add(n7);
        numbers.add(n8);
        numbers.add(n9);

////        cam = new OrthographicCamera(1280, 720);
//
//        viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);
//        stage = new Stage(viewport);
//        Gdx.input.setInputProcessor(stage);
//        table = new Table();
//        table.setFillParent(true);
//        table.setDebug(false);
//        stage = new Stage();
//        skin = new Skin(Gdx.files.internal("Skins/pixthulhu-ui.json"));
//        button = new TextButton("Play", skin);
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                PlayState.cur_record = 0;
//                gsm.set(new PlayState(gsm));
//            }
//        });
//
//
//        table.add(button).fillX().width(250).height(100);
//        table.add(button);
//        stage.addActor(table);
//        stage.setViewport(viewport);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            PlayState.cur_record = 0;
            PlayState.cur_coins = 0;
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
        sb.draw(back, 0, 0);
//        stage.act();
//        stage.draw();
        sb.draw(playButton, camera.position.x - playButton.getWidth() / 2, camera.position.y + 20);
        sb.draw(record, camera.position.x - record.getWidth() / 2, camera.position.y - 50);
        sb.draw(monetka, 5, 360);
        if (MyGame.preferences.getInteger("coins") < 10) {
            sb.draw(numbers.get(MyGame.preferences.getInteger("coins")), 45, 360);
        } else if (MyGame.preferences.getInteger("coins") < 100) {
            int first = MyGame.preferences.getInteger("coins") / 10;
            int second = MyGame.preferences.getInteger("coins") % 10;
            sb.draw(numbers.get(first), 45, 360);
            sb.draw(numbers.get(second), 67, 360);
        } else {
            int first = MyGame.preferences.getInteger("coins") / 100;
            int second = MyGame.preferences.getInteger("coins") % 100 / 10;
            int third = MyGame.preferences.getInteger("coins") % 10;
            sb.draw(numbers.get(first), 45, 360);
            sb.draw(numbers.get(second), 67, 360);
            sb.draw(numbers.get(third), 89, 360);
        }

        if (MyGame.pref.getInteger("bestRecord") < 10) {
            sb.draw(numbers.get(MyGame.pref.getInteger("bestRecord")), camera.position.x - numbers.get(MyGame.pref.getInteger("bestRecord")).getWidth() / 2, camera.position.y - 100);
        } else if (MyGame.pref.getInteger("bestRecord") < 100) {
            int first = MyGame.pref.getInteger("bestRecord") / 10;
            int second = MyGame.pref.getInteger("bestRecord") % 10;
            sb.draw(numbers.get(first), camera.position.x - numbers.get(first).getWidth() / 2 - 12, camera.position.y - 100);
            sb.draw(numbers.get(second), camera.position.x - numbers.get(first).getWidth() / 2 + 8, camera.position.y - 100);
        } else {
            int first = MyGame.pref.getInteger("bestRecord") / 100;
            int second = MyGame.pref.getInteger("bestRecord") % 100 / 10;
            int third = MyGame.pref.getInteger("bestRecord") % 10;
            sb.draw(numbers.get(first), camera.position.x - 23, camera.position.y - 100);
            sb.draw(numbers.get(second), camera.position.x - 2, camera.position.y - 100);
            sb.draw(numbers.get(third), camera.position.x + 23, camera.position.y - 100);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        back.dispose();
        playButton.dispose();
        record.dispose();
        monetka.dispose();
        System.out.println("MenuState Disposed");
    }
}
