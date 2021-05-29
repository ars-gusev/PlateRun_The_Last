package GameStates;

import com.ars.game.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import GameSprites.Plate;
import GameSprites.Barrier;

public class PlayState extends State {

    public static final int BARRIER_SPACING = 125;
    public static final int BARRIER_COUNT = 10;
    private static final int GROUND_Y_OFFSET = -90;
    public static int cur_record = 0, cur_coins = 0;
    public static int cur = 1;
    double last_pos = -1;
    int count_go = 0;
    int check1 = 0, check2 = 0;
    double lastCoinPos = -1;

    private Plate plate;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Sound fail;
    private Sound bonus;
    private Sound bingo;

    private Array<Barrier> tubes;

    public PlayState(GameManager gsm) {
        super(gsm);
        plate = new Plate(50, 200);
        camera.setToOrtho(false, MyGame.width / 2, MyGame.height / 2);
        bg = new Texture("fon.jpg");
        ground = new Texture("lava.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        fail = Gdx.audio.newSound(Gdx.files.internal("fail.mp3"));
        bonus = Gdx.audio.newSound(Gdx.files.internal("bonus.mp3"));
        bingo = Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));


        tubes = new Array<Barrier>();

        for (int i = 0; i < BARRIER_COUNT; i++) {
            tubes.add(new Barrier(i * (BARRIER_SPACING + Barrier.BARRIER_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (count_go % 2 == 0) plate.go1();
            else plate.go2();
            count_go++;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        plate.update(dt);
        camera.position.x = plate.getPos().x + 80;

        for (int i = 0; i < tubes.size; i++) {

            Barrier barrier = tubes.get(i);

            if (camera.position.x - (camera.viewportWidth / 2) > barrier.getPosTopBarrier().x + barrier.getTopBarrier().getWidth()) {
                barrier.reposition(barrier.getPosTopBarrier().x + ((Barrier.BARRIER_WIDTH + BARRIER_SPACING) * BARRIER_COUNT));
                if (check1 % 2 == 0) {
                    cur += 2;
                }
                check1++;
            }

            if (barrier.plus(plate.getBounds())) {
                if (Math.floor(plate.getBounds().x) - last_pos > 20) {
                    cur_record++;
                    bonus.play();
                    last_pos = Math.floor(plate.getBounds().x);
                }
            }


            if (barrier.collides(plate.getBounds())) {
                fail.play();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MyGame.music.stop();
                if (cur_record > MyGame.pref.getInteger("bestRecord")) {
                    MyGame.pref.putInteger("bestRecord", cur_record);
                    MyGame.pref.flush();
                }
                int k = MyGame.preferences.getInteger("coins");
                MyGame.preferences.putInteger("coins", cur_coins + k);
                MyGame.preferences.flush();
                gsm.set(new MenuOver(gsm));
                cur = 1;
            }

            if (barrier.money(plate.getBounds())) {
                if (Math.floor(plate.getBounds().x) - lastCoinPos > 100) {
                    bingo.play();
                    cur_coins++;
                    lastCoinPos = Math.floor(plate.getBounds().x);
                }
            }


        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(plate.getPlate(), plate.getPos().x, plate.getPos().y);
        for (Barrier barrier : tubes) {
            sb.draw(barrier.getTopBarrier(), barrier.getPosBotBarrier().x, barrier.getPosTopBarrier().y);
            sb.draw(barrier.getBottomBarrier(), barrier.getPosBotBarrier().x, barrier.getPosBotBarrier().y);
            sb.draw(barrier.getStone(), barrier.getPosStone().x, barrier.getPosStone().y);
            sb.draw(barrier.getCoin(), barrier.getPosCoin().x, barrier.getPosCoin().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        for (Barrier barrier : tubes) {
            if (check2 % 2 == 0) {
                if (cur < 10) {
                    sb.draw(MenuPlay.numbers.get(cur), barrier.getPosScore().x, barrier.getPosScore().y);
                } else if (cur < 100) {
                    int first = cur / 10;
                    int second = cur % 10;
                    sb.draw(MenuPlay.numbers.get(first), barrier.getPosScore().x - 10, barrier.getPosScore().y);
                    sb.draw(MenuPlay.numbers.get(second), barrier.getPosScore().x + 10, barrier.getPosScore().y);
                } else {
                    int first = PlayState.cur_record / 100;
                    int second = PlayState.cur_record % 100 / 10;
                    int third = PlayState.cur_record % 10;
                    sb.draw(MenuPlay.numbers.get(first), barrier.getPosScore().x - 20, barrier.getPosScore().y);
                    sb.draw(MenuPlay.numbers.get(second), barrier.getPosScore().x, barrier.getPosScore().y);
                    sb.draw(MenuPlay.numbers.get(third), barrier.getPosScore().x + 20, barrier.getPosScore().y);
                }
                check2 = 1;
            } else {
                check2 = 2;
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        plate.dispose();
        ground.dispose();
        for (Barrier barrier : tubes)
            barrier.dispose();
        System.out.println("PlayState Disposed");
    }

    private void updateGround() {
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }



}
