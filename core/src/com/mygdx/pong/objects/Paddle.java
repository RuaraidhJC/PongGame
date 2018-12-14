package com.mygdx.pong.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Paddle {

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ShapeRenderer.ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeRenderer.ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    Vector2 position;
    int speed;
    int score;
    int height;
    int width;

    Color color = Color.WHITE;
    ShapeRenderer.ShapeType shapeType = ShapeRenderer.ShapeType.Filled;

    public Paddle(float posX, float posY, int sizeX, int sizeY) {
        this.position = new Vector2();
        this.position.x = posX;
        this.position.y = posY;
        this.width = sizeX;
        this.height = sizeY;
        this.speed = 5;
        this.score = 0;
    }

    public void update(boolean up, boolean down) {
        if (up) {
            this.position.y += this.speed;
        }
        if (down) {
            this.position.y -= this.speed;
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(this.color);
        shapeRenderer.begin(this.shapeType);
        shapeRenderer.rect(this.position.x, this.position.y, this.width, this.height);
        shapeRenderer.end();
    }

    public boolean checkCollisions(Ball ball, int n) {
        int ballRad = ball.getBallHeight() / 2;
        int mult = (n == 0) ? (-1) : (1);
        //check_y
        if (ball.getPosition().y - ballRad >= this.position.y
                && ball.getPosition().y + ballRad <= this.position.y + this.height) {
            return ball.getPosition().x + ballRad * mult >= this.position.x
                    && ball.getPosition().x + ballRad * mult <= this.position.x + this.width;
        }
        return false;
    }

}
