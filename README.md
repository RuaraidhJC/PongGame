# Mobile Development Course #
## Project 2 ##

### Overview ###
* The project aim was to develop an application that used machine learning.
* My idea was to develop a clone of the game _Pong_, one of the very first video games to appear. One of the main reasons for this choice was that _Pong_ is an easy game to develop and it's controls are also very simple. A player can only move their paddle up or down, which limits the amount of choices a deep learning algorithm must make.
* I chose to use the _Tensforflow_ API for the machine learning part of the project.
* I originally decided to use _LibGDX_ to develop an Android game in Java. One of the advantages of _LibGDX_ is that it is a cross-platform solution, so I would be able to train my _Tensorflow_ model on the Desktop version before moving it to the mobile game.
* Python was used to develop a second version after too many difficulties were encoutered during the mobile app development.

I have included both versions for you to examine.

### Android/Java version ###
This project is developed using _LibGDX_. You can build it using the Gradle build system.
Two gradle jobs are of interest:
* __:android run__: This will build and run the project on a connected Android device (can be an emulator)
* __:desktop run__: This will build and run the project on the desktop in a window. Both Windows and Linux are supported.

After developing the first version of the game, I then developed my ___Python___ _Tensorflow_ machine learning model. However, I had ___severe___ issues getting _Python_ to be able to run _Java_ code in a scalable way that could easily be ported to a mobile application. 
This is why I decided to develop a second version, purely in _Python_, that would __not__ run on _Android_.
