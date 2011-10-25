package jp.or.iidukat.example.pacman.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import jp.or.iidukat.example.pacman.PacmanGame;

public class PacmanCanvas extends BaseEntity {
    
    private Playfield playfield;
    private ScoreLabel scoreLabel;
    private Score score;
    private Sound sound;
    private Lives lives;
    private Level level;
    
    private CutsceneField cutsceneField;
    
    public PacmanCanvas(Bitmap sourceImage) {
        super(sourceImage, true);
    }

    public void init() {
        Presentation p = getPresentation();
        p.setWidth(554);
        p.setHeight(136);
        p.setBgColor(0x000000);
    }

    public void createPlayfield(PacmanGame game) {
        playfield = new Playfield(
                                getPresentation().getSourceImage(),
                                game);
        playfield.init();
        playfield.setParent(this);
    }

    public void createScoreLabel() {
        scoreLabel = new ScoreLabel(getPresentation().getSourceImage());
        scoreLabel.init();
        scoreLabel.setParent(this);
    }
    
    public void createScore() {
        score = new Score(getPresentation().getSourceImage());
        score.init();
        score.setParent(this);
    }
    
    public void createLevel() {
        level = new Level(getPresentation().getSourceImage());
        level.init();
        level.setParent(this);
    }

    public void createLives() {
        lives = new Lives(getPresentation().getSourceImage());
        lives.init();
        lives.setParent(this);
    }
    
    public void createSoundIcon() {
        sound = new Sound(getPresentation().getSourceImage());
        sound.init();
        sound.setParent(this);
    }
    
    public void showChrome(boolean b) {
        if (scoreLabel != null)
            scoreLabel.setVisibility(b); // showElementById("pcm-sc-1-l", b);

        if (score != null)
            score.setVisibility(b); // showElementById("pcm-sc-1", b);

        if (lives != null)
            lives.setVisibility(b);// showElementById("pcm-li", b);

        if (sound != null)
            sound.setVisibility(b);// showElementById("pcm-so", b);
    }
    
    public void createCutsceneField() {
        cutsceneField =
            new CutsceneField(getPresentation().getSourceImage());
        cutsceneField.init();
        cutsceneField.setParent(this);
    }
    
    @Override
    void doDraw(Canvas c) {
    }

    public Playfield getPlayfield() {
        return playfield;
    }

    public ScoreLabel getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(ScoreLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public Score getScore() {
        return score;
    }
    
    public void setScore(Score score) {
        this.score = score;
    }
    
    public void setPlayfield(Playfield playfield) {
        this.playfield = playfield;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Lives getLives() {
        return lives;
    }

    public void setLives(Lives lives) {
        this.lives = lives;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public CutsceneField getCutsceneField() {
        return cutsceneField;
    }

    public void setCutsceneField(CutsceneField cutsceneField) {
        this.cutsceneField = cutsceneField;
    }
    
    public void removeCutsceneField() {
        removeFromDrawQueue(cutsceneField);
        cutsceneField = null;
    }

    public void setTop(float top) {
        getPresentation().setTop(top);
    }
    
    public void setLeft(float left) {
        getPresentation().setLeft(left);
    }

    public void reset() {
        playfield = null;
        scoreLabel = null;
        score = null;
        sound = null;
        lives = null;
        level = null;
        
        cutsceneField = null;
    }
}
