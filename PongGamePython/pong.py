import pygame
import random

WINDOW_WIDTH = 900
WINDOW_HEIGHT = 600

PADDLE_WIDTH = 10
PADDLE_HEIGHT = 60
PADDLE_BUFFER = 10

BALL_WIDTH = 10
BALL_HEIGHT = 10

PADDLE_SPEED = 4
BALL_X_SPEED = 6
BALL_Y_SPEED = 4

WHITE = (255, 255, 255)
BLACK = (0, 0, 0)

class View:
    def __init__(self):
        self.screen = screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
        self.ballColor = WHITE
        self.paddleColor = WHITE

    def drawScreen(self):
        self.screen.fill(BLACK)

    def drawBall(self, ball):
        ballShape = pygame.Rect(ball.posX, ball.posY, BALL_WIDTH, BALL_HEIGHT)
        pygame.draw.rect(self.screen, self.ballColor, ballShape)

    def drawPaddleAI(self, paddle):
        paddleShape = pygame.Rect(paddle.posX, paddle.posY, PADDLE_WIDTH, PADDLE_HEIGHT)
        pygame.draw.rect(self.screen, self.paddleColor, paddleShape)

    def drawPaddlePlayer(self, paddle):
        paddleShape = pygame.Rect(paddle.posX, paddle.posY, PADDLE_WIDTH, PADDLE_HEIGHT)
        pygame.draw.rect(self.screen, self.paddleColor, paddleShape)

class Paddle:
    def __init__(self, posX, posY):
        self.posX = posX
        self.posY = posY

    def updatePaddleAI(self, action):
        if (action[1] == 1 and self.posY < WINDOW_HEIGHT - PADDLE_HEIGHT):
            self.posY += PADDLE_SPEED
        elif (action[2] == 1 and self.posY > 0):
            self.posY -= PADDLE_SPEED
        return self.posY

    def updatePaddlePlayer(self, ball):
        if (self.posY + PADDLE_HEIGHT / 2 < ball.posY + BALL_HEIGHT / 2
                and self.posY < WINDOW_HEIGHT - PADDLE_HEIGHT):
            self.posY += PADDLE_SPEED
        elif (self.posY + PADDLE_HEIGHT / 2 > ball.posY + BALL_HEIGHT / 2
                and self.posY > 0):
            self.posY -= PADDLE_SPEED
        return self.posY

class Ball:
    def __init__(self):
        self.posX = 0
        self.posY = 0
        self.dirX = 0
        self.dirY = 0
        xNum = random.randint(1,10)
        yNum = random.randint(1,10)
        self.posX = WINDOW_WIDTH / 2
        self.posY = WINDOW_HEIGHT / 2
        if (xNum % 2 == 0):
            self.dirX = -1
        else:
            self.dirX = 1
        if (yNum % 2 == 0):
            self.dirY = 1
        else:
            self.dirY = -1


    def resetPos(self):
        xNum = random.randint(1,10)
        yNum = random.randint(1,10)
        self.posX = WINDOW_WIDTH / 2
        self.posY = WINDOW_HEIGHT / 2
        if (xNum % 2 == 0):
            self.dirX = -1
        else:
            self.dirX = 1
        if (yNum % 2 == 0):
            self.dirY = 1
        else:
            self.dirY = -1

    def checkCollisionsAI(self, paddle):
        if (self.posX <= paddle.posX + PADDLE_WIDTH
                and self.posY + BALL_HEIGHT >= paddle.posY
                and self.posY - BALL_HEIGHT <= paddle.posY + PADDLE_HEIGHT):
            return True
        return False

    def checkCollisionsPlayer(self, paddle):
        if (self.posX >= paddle.posX
                and self.posY + BALL_HEIGHT >= paddle.posY 
                and self.posY - BALL_HEIGHT <= paddle.posY + PADDLE_HEIGHT):
            return True
        return False
    
    def update(self, paddleAI, paddlePlayer):
        self.posX += self.dirX * BALL_X_SPEED
        self.posY += self.dirY * BALL_Y_SPEED

        # collisions
        if (self.checkCollisionsAI(paddleAI)):
            self.dirX = 1
        elif (self.checkCollisionsPlayer(paddlePlayer)):
            self.dirX = -1
        if (self.posY >= WINDOW_HEIGHT):
            self.dirY = -1
        elif (self.posY <= 0):
            self.dirY = 1

        #win conditions
        if (self.posX <= 0):
            self.dirX = 1
            #self.resetPos()
            return -1                       
        elif (self.posX >= WINDOW_WIDTH):            
            self.dirX = -1
            #self.resetPos()
            return 1            
        return 0        

class PongGame:
    def __init__(self):
        self.renderer = View()
        self.ball = Ball()
        self.paddleAI = Paddle(PADDLE_BUFFER, WINDOW_HEIGHT / 2 - PADDLE_HEIGHT / 2)
        self.paddlePlayer = Paddle(WINDOW_WIDTH - PADDLE_BUFFER - PADDLE_WIDTH, WINDOW_HEIGHT / 2 - PADDLE_HEIGHT / 2)
        self.tally = 0

    def updateFrame(self, action):
        self.paddleAI.updatePaddleAI(action)
        self.paddlePlayer.updatePaddlePlayer(self.ball)
        score = self.ball.update(self.paddleAI, self.paddlePlayer)
        return score

    def draw(self):
        pygame.event.pump()
        self.renderer.drawScreen()
        self.renderer.drawPaddleAI(self.paddleAI)
        self.renderer.drawPaddlePlayer(self.paddlePlayer)
        self.renderer.drawBall(self.ball)
        image_data = pygame.surfarray.array3d(pygame.display.get_surface())
        pygame.display.flip()
        return image_data
        

    def nextFrame(self, action):
        score = self.updateFrame(action)
        self.tally += score
        img = self.draw()
        return [score, img]
