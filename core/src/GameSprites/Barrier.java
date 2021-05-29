package GameSprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Barrier {

    public static final int BARRIER_WIDTH = 52;

    public static final int FLUC = 130;
    public static final int BARRIER_GAP = 120;
    public static final int LOWER_OPENING = 120;

    private Texture topBarrier, bottomBarrier, stone, coin;
    private Vector2 posTopBarrier, posBotBarrier, posStone, posScore, posCoin;
    private Random rand;
    private Rectangle boundsTop, boundsBot, boundsStone, boundsCoin;


    public Vector2 getPosCoin() {
        return posCoin;
    }

    public Texture getCoin() {
        return coin;
    }

    public Texture getTopBarrier() {
        return topBarrier;
    }

    public Texture getBottomBarrier() {
        return bottomBarrier;
    }

    public Vector2 getPosTopBarrier() {
        return posTopBarrier;
    }

    public Vector2 getPosBotBarrier() {
        return posBotBarrier;
    }

    public Texture getStone() {
        return stone;
    }

    public Vector2 getPosScore() {
        return posScore;
    }

    public Vector2 getPosStone() {
        return posStone;
    }


    public Barrier(float x) {
        topBarrier = new Texture("verh.png");
        bottomBarrier = new Texture("niz.png");
        stone = new Texture("stone.png");
        coin = new Texture("m.png");

        rand = new Random();
        double niz = x - 100;
        double verh = x + 35;
        double a = (Math.random() * (verh - niz)) + niz;

        double niz1 = x - 130;
        double verh1 = x - 25;
        double a1 = (Math.random() * (verh1 - niz1)) + niz1;

        double b1 = (Math.random() * (380 - 70)) + 70;

        posTopBarrier = new Vector2(x + 400, rand.nextInt(FLUC) + BARRIER_GAP + LOWER_OPENING);
        posBotBarrier = new Vector2(x + 400, posTopBarrier.y - BARRIER_GAP - bottomBarrier.getHeight());
        double b = (Math.random() * (posTopBarrier.y - (posTopBarrier.y - BARRIER_GAP))) + posTopBarrier.y - BARRIER_GAP;
        posStone = new Vector2((int)a + 400, (int)b - 20);
        posScore = new Vector2(x + 420, 20);
        posCoin = new Vector2((int)a1 + 400, (int)b1);

        boundsTop = new Rectangle(posTopBarrier.x + 14, posTopBarrier.y + 5, topBarrier.getWidth() - 14, topBarrier.getHeight());
        boundsBot = new Rectangle(posBotBarrier.x + 14, posBotBarrier.y - 5, bottomBarrier.getWidth() - 14, bottomBarrier.getHeight());
        boundsStone = new Rectangle(posStone.x + 10, posStone.y + 10, stone.getWidth() - 10, stone.getHeight() - 18);
        boundsCoin = new Rectangle(posCoin.x + 10, posCoin.y + 10, coin.getWidth() - 15, coin.getHeight() - 15);
    }

    public void reposition(float x) {
        posTopBarrier.set(x + 400, rand.nextInt(FLUC) + BARRIER_GAP + LOWER_OPENING);
        posBotBarrier.set(x + 400, posTopBarrier.y - BARRIER_GAP - bottomBarrier.getHeight());
        posScore.set(x + 420, 20);

        double niz = x - 100;
        double verh = x + 35;
        double a = (Math.random() * (verh - niz)) + niz;
        double b = (Math.random() * (posTopBarrier.y - (posTopBarrier.y - BARRIER_GAP))) + posTopBarrier.y - BARRIER_GAP;
        posStone.set((int)a + 400, (int)b - 20);

        double niz1 = x - 130;
        double verh1 = x - 25;
        double a1 = (Math.random() * (verh1 - niz1)) + niz1;
        double b1 = (Math.random() * (380 - 70)) + 70;
        posCoin.set((int)a1 + 400, (int)b1);

        boundsTop.setPosition(posTopBarrier.x + 14, posTopBarrier.y + 5);
        boundsBot.setPosition(posBotBarrier.x + 14, posBotBarrier.y - 5);
        boundsStone.setPosition(posStone.x + 10, posStone.y + 10);
        boundsCoin.setPosition(posCoin.x + 10, posCoin.y + 10);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot) || player.overlaps(boundsStone);
    }

    public boolean plus(Rectangle player) {
        boolean sl1 = Math.floor(player.x) == Math.floor(boundsTop.x + topBarrier.getWidth() - 20);
        boolean sl2 = Math.floor(player.x) - 1 == Math.floor(boundsTop.x + topBarrier.getWidth() - 20);
        boolean sl3 = Math.floor(player.x) + 1 == Math.floor(boundsTop.x + topBarrier.getWidth() - 20);
        return sl1 || sl2 || sl3;
    }

    public boolean money(Rectangle player) {
        return player.overlaps(boundsCoin);
    }

    public void dispose() {
        topBarrier.dispose();
        bottomBarrier.dispose();
    }
}
