package com.mygdx.pong.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ball {

    private Vector2 position;
    private Vector2 direction;

    private final int ballSpeedX = 6;
    private final int ballSpeedY = 4;

    private int ballHeight;
    private int ballWidth;

    private final Color ballColor = Color.WHITE;
    private final ShapeRenderer.ShapeType shape = ShapeRenderer.ShapeType.Filled;

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public int getBallSpeedX() {
        return ballSpeedX;
    }

    public int getBallSpeedY() {
        return ballSpeedY;
    }

    public int getBallHeight() {
        return ballHeight;
    }

    public void setBallHeight(int ballHeight) {
        this.ballHeight = ballHeight;
    }

    public int getBallWidth() {
        return ballWidth;
    }

    public void setBallWidth(int ballWidth) {
        this.ballWidth = ballWidth;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public ShapeRenderer.ShapeType getShape() {
        return shape;
    }

    public Ball(float posX, float posY, int height, int width) {
        this.position = new Vector2();
        this.direction = new Vector2();
        this.position.x = posX;
        this.position.y = posY;
        this.ballHeight = height;
        this.ballWidth = width;
        this.direction.x = 1;
        this.direction.y = 1;
    }

    public boolean update(boolean collisionX, boolean collisionY) {
        if (collisionX) {
            this.direction.x *= -1;
        }
        if (collisionY) {
            this.direction.y *= -1;
        }
        this.position.x += this.direction.x * this.ballSpeedX;
        this.position.y += this.direction.y * this.ballSpeedY;
        return (true);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(this.getBallColor());
        shapeRenderer.begin(this.getShape());
        shapeRenderer.circle(this.getPosition().x, this.getPosition().y, this.getBallWidth());
        shapeRenderer.end();
    }

}
