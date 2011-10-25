package jp.or.iidukat.example.pacman.entity;

import static jp.or.iidukat.example.pacman.Direction.Move;
import static jp.or.iidukat.example.pacman.PacmanGame.GameplayMode;

import jp.or.iidukat.example.pacman.Direction;
import jp.or.iidukat.example.pacman.PacmanGame;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Actor extends BaseEntity {

    static final int DEFAULT_DISPLAY_ORDER = 110;
    
    public static enum CurrentSpeed {
        NONE(-1), NORMAL(0), PACMAN_EATING_DOT(1), PASSING_TUNNEL(2);
        
        private final int mode;
        
        private CurrentSpeed(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

    }
    
    static class InitPosition {
        final float x;
        final float y;
        final Direction dir;
        final float scatterX;
        final float scatterY;

        InitPosition(float x, float y, Direction dir) {
            this(x, y, dir, 0, 0);
        }
        
        InitPosition(float x, float y, Direction dir, float scatterX, float scatterY) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.scatterX = scatterX;
            this.scatterY = scatterY;
        }
        
        static InitPosition createPlayerInitPosition(float x, float y, Direction dir) {
            return new InitPosition(x, y, dir);
        }
        
        static InitPosition createGhostInitPosition(
                                                float x,
                                                float y,
                                                Direction dir,
                                                float scatterX,
                                                float scatterY) {
            return new InitPosition(x, y, dir, scatterX, scatterY);
        }
    }

    static final int[] s = {32, 312}; // モンスターの巣の入り口の位置
    
    final int id;
    final PacmanGame g;
    float[] pos;
    float[] posDelta;
    int[] tilePos;
    int[] lastGoodTilePos;
    private float[] elPos;
    int[] elBackgroundPos;
    Direction dir = Direction.NONE;
    Direction lastActiveDir = Direction.NONE;
    float speed;
    float physicalSpeed;
    Direction nextDir = Direction.NONE;
    CurrentSpeed currentSpeed = CurrentSpeed.NONE;
    float fullSpeed;
    float tunnelSpeed;
    Boolean[] speedIntervals;

    public Actor(Bitmap sourceImage, int b, PacmanGame g) {
        super(sourceImage);
        this.id = b;
        this.g = g;
    }
    
    // Actorを再配置
    public abstract void A();
    
    abstract InitPosition getInitPosition();
    
    public void init() {
        Presentation p = getPresentation();
        p.setWidth(16);
        p.setHeight(16);
        p.setTopOffset(-4);
        p.setLeftOffset(-4);
        p.prepareBkPos(0, 0);
        p.setOrder(DEFAULT_DISPLAY_ORDER);
        
        this.elPos = new float[] {0, 0};
        this.elBackgroundPos = new int[] {0, 0};
    }
    
    // tilePosとposの差分が有意になったとき呼び出される
    abstract void p(int[] b);

    abstract void n();

    // posの値がtilePosと一致(pos が8の倍数)したときに呼び出される
    abstract void u();

    abstract void o();

    // Actorの速度設定(currentSpeedプロパティを利用)
    public abstract void d();
    
    // Actorの速度設定変更
    public void c(CurrentSpeed b) {
        this.currentSpeed = b;
        this.d();
    }
    // Actorの移動(ルーチン以外)
    void e() {
        if (this.dir != Direction.NONE)
            if (this.speedIntervals[g.getIntervalTime()]) { // この判定で速度を表現
                Move b = this.dir.getMove();
                this.pos[b.getAxis()] += b.getIncrement();
                this.o();
                this.b();
            }
    }

    public abstract void move();
    
    // 位置移動
    public void k() {
        float b = Playfield.getPlayfieldX(this.pos[1] + this.posDelta[1]);
        float c = Playfield.getPlayfieldY(this.pos[0] + this.posDelta[0]);
        if (this.elPos[0] != c || this.elPos[1] != b) {
            this.elPos[0] = c;
            this.elPos[1] = b;
            Presentation el = getPresentation();
            el.setLeft(b);
            el.setTop(c);
        }
    }
    
    abstract int[] getImagePos();
    

    // Actor表示画像切り替え(アニメーション対応)&位置移動
    public void b() {
        this.k(); //位置移動 
        int[] b = { 0, 0 };
        b = g.getGameplayMode() == GameplayMode.GAMEOVER
            || g.getGameplayMode() == GameplayMode.KILL_SCREEN
                ? new int[] { 0, 3 }
                : getImagePos();
        if (this.elBackgroundPos[0] != b[0] || this.elBackgroundPos[1] != b[1]) {
            this.elBackgroundPos[0] = b[0];
            this.elBackgroundPos[1] = b[1];
            b[0] *= 16;
            b[1] *= 16;
            getPresentation().changeBkPos(b[1], b[0], true);
        }
    }
    
    @Override
    void doDraw(Canvas c) {
        getPresentation().drawBitmap(c);
    }

    public int[] getTilePos() {
        return tilePos;
    }


    public float[] getPos() {
        return pos;
    }

    public void setPos(float[] pos) {
        this.pos = pos;
    }

    public float[] getPosDelta() {
        return posDelta;
    }

    public void setPosDelta(float[] posDelta) {
        this.posDelta = posDelta;
    }

    public float[] getElPos() {
        return elPos;
    }

    public void setElPos(float[] elPos) {
        this.elPos = elPos;
    }

    public int[] getElBackgroundPos() {
        return elBackgroundPos;
    }

    public void setElBackgroundPos(int[] elBackgroundPos) {
        this.elBackgroundPos = elBackgroundPos;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public float getFullSpeed() {
        return fullSpeed;
    }

    public void setFullSpeed(float fullSpeed) {
        this.fullSpeed = fullSpeed;
    }

    public float getTunnelSpeed() {
        return tunnelSpeed;
    }

    public void setTunnelSpeed(float tunnelSpeed) {
        this.tunnelSpeed = tunnelSpeed;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
    
    public void resetDisplayOrder() {
        getPresentation().setOrder(DEFAULT_DISPLAY_ORDER);
    }
}
