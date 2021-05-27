package GameSprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Barrier {

    public static final int TUBE_WIDTH = 52;

    public static final int FLUCTUATION = 130;
    public static final int TUBE_GAP = 120;
    public static final int LOWER_OPENING = 120;

    private Texture topTube, bottomTube, badBird;
    private Vector2 posTopTube, posBotTube, posBadBird, posScore1;
    private Random rand;
    private Rectangle boundsTop, boundsBot, boundsBadBird;

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Texture getBadBird() {
        return badBird;
    }

    public Vector2 getPosScore1() {
        return posScore1;
    }



    public Vector2 getPosBadBird() {
        return posBadBird;
    }


    public Barrier(float x) {
        topTube = new Texture("verh.png");
        bottomTube = new Texture("niz.png");
        badBird = new Texture("stone.png");

        rand = new Random();
        double niz = x - 100;
        double verh = x + 35;
        double a = (Math.random() * (verh - niz)) + niz;

        posTopTube = new Vector2(x + 400, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWER_OPENING);
        posBotTube = new Vector2(x + 400, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        double b = (Math.random() * (posTopTube.y - (posTopTube.y - TUBE_GAP))) + posTopTube.y - TUBE_GAP;
        posBadBird = new Vector2((int)a + 400, (int)b - 20);
        posScore1 = new Vector2(x + 420, 20);

        boundsTop = new Rectangle(posTopTube.x + 14, posTopTube.y + 5, topTube.getWidth() - 14, topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x + 14, posBotTube.y - 5, bottomTube.getWidth() - 14, bottomTube.getHeight());
        boundsBadBird = new Rectangle(posBadBird.x + 10, posBadBird.y + 10, badBird.getWidth() - 10, badBird.getHeight() - 18);
    }

    public void reposition(float x) {
        posTopTube.set(x + 400, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWER_OPENING);
        posBotTube.set(x + 400, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        posScore1.set(x + 420, 20);

        double niz = x - 100;
        double verh = x + 35;
        double a = (Math.random() * (verh - niz)) + niz;
        double b = (Math.random() * (posTopTube.y - (posTopTube.y - TUBE_GAP))) + posTopTube.y - TUBE_GAP;
        posBadBird.set((int)a + 400, (int)b - 20);

        boundsTop.setPosition(posTopTube.x + 14, posTopTube.y + 5);
        boundsBot.setPosition(posBotTube.x + 14, posBotTube.y - 5);
        boundsBadBird.setPosition(posBadBird.x + 10, posBadBird.y + 10);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot) || player.overlaps(boundsBadBird);
    }

    public boolean plus(Rectangle player) {
        boolean sl1 = Math.floor(player.x) == Math.floor(boundsTop.x + topTube.getWidth() - 20);
        boolean sl2 = Math.floor(player.x) - 1 == Math.floor(boundsTop.x + topTube.getWidth() - 20);
        boolean sl3 = Math.floor(player.x) + 1 == Math.floor(boundsTop.x + topTube.getWidth() - 20);
        return sl1 || sl2 || sl3;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
