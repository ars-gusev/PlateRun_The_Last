package GameSprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Plate {
    public static final int MOVEMENT = 100;
    private Vector3 pos;
    private Vector3 vel;
    private Rectangle bounds;
    private Animation plateAnimation;
    private Texture texture;

    public Plate(int x, int y) {
        pos = new Vector3(x, y, 0);
        vel = new Vector3(0, 0, 0);
        texture = new Texture("PlateAnimation.png");
        plateAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 4, texture.getHeight());
    }

    public TextureRegion getPlate() {
        return plateAnimation.getFrame();
    }

    public Vector3 getPos() {
        return pos;
    }

    public void update(float dt) {
        plateAnimation.update(dt);
        if (pos.y > 0)
            vel.add(0, 0, 0);
        vel.scl(dt);
        pos.add(MOVEMENT * dt, vel.y, 0);
        if (pos.y < 0)
            pos.y = 0;

        vel.scl(1 / dt);
        bounds.setPosition(pos.x, pos.y);

    }

    public void go1() {
        vel.y = 150;
    }

    public void go2() {
        vel.y = -150;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
