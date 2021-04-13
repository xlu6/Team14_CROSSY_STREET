Readme

Crossy Street

Requirements:
An Android device or emulator is required to test or play this game. Player are therefore expected to have either.

	An Android device with OS 11.0 (Google APIs)
API: 30 or above
 
	A PC with Android Developer Studio and Android emulator with Android OS 11.0 installed
API: 30 or above also

Screen Resolution:
Recommend: 1080 x 2220 @440dpi (in order to prevent any display issue)

How to run:
I.	Download the full project folder from github: 
II.	Open Android Studio and select open an existing project from your download path.
III.	Ensure the following components are installed and updated to the latest version.
•	Android SDK Platform-Tools
•	Android Support Repository
•	Google Repository
 

Feature:
Tutorial Level for entry-level players
Leaderboard for tracking highest record
Vibration
Pause/ Return/ Back to main screen

Support:
Most of requirements and procedure can be found by this README. If your question is not answered by this document. We encourage you to contact us via email or on class. Thank you.

Release Note
April 12, 2021

Product Name: Crossy Street [Release# 003]
Version: 1.2

Overview
The idea of this game came from a classical old video game “frogger”, the one where you have to maneuver the frog across the road without getting squashed. The idea is simple, the hard time was applying a similar concept on android studio platform in our way. The game is getting our character (a handsome boy) as a pedestrian to cross in every different traffic of road, including two three or more lanes, and even cross in romantic night time! In Canada, cars may stop for you; in our game, it never happens…

Purpose
New soundtrack of traffic and background, making a realistic and intense scenario of game.
New UI for high levels, adding midnight city and vehicles in different kinds with headlights’ on.
Adjusting the collision body of the character in ordering to make more realistic and making sense when the boy close to the lane.
New vibration feature added, there is a sound effect with a vibration when getting hit by cars.
Merge the entry level as a tutorial level.
Improving the traffic flow smoother by modifying the boundary condition decision.

Known issues
Crossing speed is too fast and not adjustable, it looks weird the character keeps stomping and nonstop.
Button Animation could not be archived on game’s UI, for example, we can only use a regular button for PAUSE instead of an image button.
The character cannot walk backward yet in this version.
Same background music can overlap due to threads.
Need a better score rating system, as of now, if a level is passed, no matter what level or how long it took, it will show same score.
The change character feature was not implemented.

Bugs Fixes
•	The program crashes or freezes once the character gets hit by any cars.
Steps to Reproduce: The player hit a car on any level
Resolution: Fix an error code [java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare(); [duplicate]] by adding a “runOnUiThread”.
End-User Impact: The program will be crash or freeze unless user terminate the whole program by force.

•	The scores cannot be displayed properly on the top left of screen.
Steps to Reproduce: N/A
Resolution: Using a parameter called “getSharedPreferences” to gaining the highest data of each level.
End-User Impact: The player never knows how many score they can get in any time.
	
•	The leaderboard cannot record any score even any player (No data).
Steps to Reproduce: N/A
Resolution: Using a parameter called “getSharedPreferences” to gaining the highest data of each level.
End-User Impact: The player never knows how many score they can get in any time.

•	All cars are in the same speed in each level, so all velocities of cars are predictable and the game will be too easy. 
Step to Reproduce: entering any level on the main menu.
Resolution: Using a java utility called “random()” to create a random number generator of speed, with highest speed limit by 20. All cars’ speed become variably.
End-User Impact: All velocities of cars are predictable and the game will be too easy.

About
This application is a freeware and developed by Team member: Lixuan Bao, Xianhao Lu, Zikai Huang. If you have any problem regarding this application, please contact us via Class or Email.


Please be noted that:
The game is still in development. This means that bugs and crashes are to be expected. We hope you have fun during your time on Crossy Street and look forward to reading your feedback!


Test Case
We mainly focused on functional testing, i.e. alignment errors, display, popup windows, audio, game rules (what is considered to be game over, in our case, it is when the character gets hit by a car).
