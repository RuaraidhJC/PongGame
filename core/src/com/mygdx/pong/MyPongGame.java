package com.mygdx.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.pong.inputs.InputManager;
import com.mygdx.pong.objects.Ball;
import com.mygdx.pong.objects.Paddle;

public class MyPongGame extends ApplicationAdapter {

	/**
	 * Game constants
	 */

	final int FPS = 60;

	private int WINDOW_HEIGHT;
	private int WINDOW_WIDTH;

	private int PADDLE_HEIGHT;
	private int PADDLE_WIDTH;

	private int BALL_WIDTH;
	private int BALL_HEIGHT;

	final int PADDLE_SPEED = 2;
	final int BALL_X_SPEED = 3;
	final int BALL_Y_SPEED = 2;

	private final int NPADDLES = 2;


	/**
	 * Game assets
	 */

	private Ball ball;
	private Array<Paddle> paddles = new Array<Paddle>();

	private InputManager inputManager;
	private SpriteBatch batch;
	private Texture img;
	private OrthographicCamera camera;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private byte[] pixels;
	Pixmap pixmap = null;
	
	@Override
	public void create () {
		WINDOW_HEIGHT = Gdx.app.getGraphics().getHeight();
		WINDOW_WIDTH =  Gdx.app.getGraphics().getWidth();
		PADDLE_HEIGHT = WINDOW_HEIGHT / ((WINDOW_HEIGHT ) / 300);
		PADDLE_WIDTH = WINDOW_WIDTH / (WINDOW_WIDTH / 50);
		BALL_WIDTH = WINDOW_WIDTH / (WINDOW_WIDTH / 30);
		BALL_HEIGHT = WINDOW_HEIGHT / (WINDOW_HEIGHT / 30);

		ball = new Ball(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, BALL_HEIGHT, BALL_WIDTH);
		paddles.add(new Paddle(0, WINDOW_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT));
		paddles.add(new Paddle(WINDOW_WIDTH - PADDLE_WIDTH, WINDOW_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT));
		shapeRenderer = new ShapeRenderer();
		inputManager = new InputManager();
		font = new BitmapFont();
		font.getData().setScale(10, 10);
		Gdx.input.setInputProcessor(inputManager);
		camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		this.update();
		this.draw();
		this.inputManager.update();
	}

	private void update() {
		boolean collisionX = false;
		boolean collisionY = false;
		boolean moveUp = false;
		boolean moveDown = false;

		if (ball.getPosition().x - BALL_WIDTH / 2 <= 0 || ball.getPosition().x + BALL_WIDTH / 2 >= WINDOW_WIDTH) {
			if (ball.getPosition().x - BALL_WIDTH / 2 <= 0)
				paddles.get(1).setScore(paddles.get(1).getScore() + 1);
			else
				paddles.get(0).setScore(paddles.get(0).getScore() + 1);
			ball = new Ball(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, BALL_HEIGHT, BALL_WIDTH);
			return;
		}

		if (ball.getPosition().y - BALL_WIDTH / 2 <= 0 || ball.getPosition().y + BALL_WIDTH / 2 >= WINDOW_HEIGHT)
			collisionY = true;
		for (int i = 0; i < NPADDLES; i++) {
            if (paddles.get(i).checkCollisions(ball, i)) {
                collisionX = true;
            }
        }

		this.ball.update(collisionX, collisionY);
		if (inputManager.isTouchDown(0)){
			InputManager.TouchState t = inputManager.getTouchState(0);
			if (t.coordinates.y > WINDOW_HEIGHT / 2) {
				moveUp = true;
			} else {
				moveDown = true;
			}
			for (int i = 0; i < NPADDLES; i++) {
				Paddle p = paddles.get(i);
				p.update(moveUp, moveDown);
			}
		}
	}

	private void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, Integer.toString(paddles.get(0).getScore()), 150, WINDOW_HEIGHT - 50);
        font.draw(batch, Integer.toString(paddles.get(1).getScore()), WINDOW_WIDTH - 250, WINDOW_HEIGHT - 50);
		batch.end();
		ball.render(shapeRenderer);
		for (int i = 0; i < NPADDLES; i++) {
			Paddle p = paddles.get(i);
			p.render(shapeRenderer);
		}
		if (pixmap != null)
			pixmap.dispose();
		pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(),Gdx.graphics.getBackBufferHeight(), true);
		pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
	}

	@Override
	public void dispose () {
		batch.dispose();
		pixmap.dispose();
		img.dispose();
		font.dispose();
	}


	public void fakeUpTouch() {
		inputManager.touchDown(0, 0, 0, 0);
	}

	public int getScore() {
		return paddles.get(0).getScore();
	}

	public byte[] getPixels() {
		return pixels;
	}
}
