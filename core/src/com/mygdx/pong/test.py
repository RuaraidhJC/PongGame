from jnius import autoclass

Game = autoclass("com.mygdx.pong.MyPongGame")
printf(Game.getScore())
printf("hello")