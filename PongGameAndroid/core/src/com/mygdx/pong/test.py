from jnius import autoclass

Game = autoclass("MyPongGame")
printf(Game.getScore())
printf("hello")
