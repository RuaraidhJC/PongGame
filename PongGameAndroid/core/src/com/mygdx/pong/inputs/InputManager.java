package com.mygdx.pong.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {

    private Array<KeyState> keyStates = new Array<KeyState>();
    private Array<TouchState> touchStates = new Array<TouchState>();

    public InputManager() {
        for (int i = 0; i < 256; i++) {
            keyStates.add(new KeyState(i));
        }
        touchStates.add(new TouchState(0, 0, 0, 0));
    }

    @Override
    public boolean keyDown(int keycode) {
        keyStates.get(keycode).pressed = true;
        keyStates.get(keycode).down = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyStates.get(keycode).down = false;
        keyStates.get(keycode).released = true;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public boolean isKeyPressed(int key) {
        return keyStates.get(key).pressed;
    }

    public boolean isKeyDown(int key) {
        return keyStates.get(key).down;
    }

    public boolean isKeyReleased(int key) {
        return keyStates.get(key).released;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean pointerFound = false;
        int coordX = coordinateX(screenX);
        int coordY = coordinateY(screenY);

        for (int i = 0; i < touchStates.size; i++) {
            TouchState t = touchStates.get(i);
            if (t.pointer == pointer) {
                t.down = true;
                t.pressed = true;
                t.coordinates.x = coordX;
                t.coordinates.y = coordY;
                t.button = button;
                t.lastPosition.x = coordX;
                t.lastPosition.y = coordY;
                pointerFound = true;
            }
        }
        if (!pointerFound) {
            touchStates.add(new TouchState(coordX, coordY, pointer, button));
            TouchState t = touchStates.get(pointer);
            t.down = true;
            t.pressed = true;
            t.lastPosition.x = coordX;
            t.lastPosition.y = coordY;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        TouchState t = touchStates.get(pointer);
        t.down = false;
        t.released = true;

        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int coordX = coordinateX(screenX);
        int coordY = coordinateY(screenY);

        TouchState t = touchStates.get(pointer);
        t.coordinates.x = coordX;
        t.coordinates.y = coordY;
        t.displacement.x = coordX - t.lastPosition.x;
        t.displacement.y = coordY - t.lastPosition.y;
        t.lastPosition.x = coordX;
        t.lastPosition.y = coordY;

        return false;
    }

    public TouchState getTouchState(int pointer){
        if (touchStates.size > pointer) {
            return touchStates.get(pointer);
        } else {
            return null;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isTouchPressed(int pointer){
        return touchStates.get(pointer).pressed;
    }

    public boolean isTouchDown(int pointer){
        return touchStates.get(pointer).down;
    }

    public boolean isTouchReleased(int pointer){
        return touchStates.get(pointer).released;
    }

    public Vector2 touchCoordinates(int pointer){
        return touchStates.get(pointer).coordinates;
    }

    public Vector2 touchDisplacement(int pointer){
        return touchStates.get(pointer).displacement;
    }

    private int coordinateX(int screenX) {
        return screenX - Gdx.graphics.getWidth() / 2;
    }

    private int coordinateY(int screenY) {
        return Gdx.graphics.getWidth() / 2 - screenY;
    }

    public void update() {
        for (int i = 0; i < 256; i++) {
            KeyState k = keyStates.get(i);
            k.pressed = false;
            k.released = false;
        }
        for (int i = 0; i < touchStates.size; i++) {
            TouchState t = touchStates.get(i);
            t.pressed = false;
            t.released = false;
            t.displacement.x = 0;
            t.displacement.y = 0;
        }
    }

    class InputState {
        boolean pressed = false;
        boolean down = false;
        boolean released = false;
    }

    public class KeyState extends InputState {
        int key;

        KeyState(int key) {
            this.key = key;
        }
    }

    public class TouchState extends InputState {
        public int pointer;
        public Vector2 coordinates;
        int button;
        private Vector2 lastPosition;
        Vector2 displacement;

        TouchState(int coordX, int coordY, int pointer, int button) {
            this.pointer = pointer;
            coordinates = new Vector2(coordX, coordY);
            this.button = button;
            lastPosition = new Vector2(0,0);
            displacement = new Vector2(lastPosition.x, lastPosition.y);
        }
    }

}
