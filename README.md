# Grimoire

**Grimoire** is an app which allows you to perform actions with the wave of a wand.

# How It Works

**Grimoire** uses a camera, modified to view infrared light, to track the motions of objects that reflect infrared light. So far I've been using it with a magic wand I got at Ollivanders in Universal Studios. Though any reflective object will work with a little trial and error.

# Installation (Linux)

1) Download **Grimoire_Linux.zip** located in the root of this repository.
2) Unzip it to any location you like.
3) Open the terminal, cd into the Grimoire directory you just unzipped,  and add execute permissions to the start
script by executing the following

    ```sudo chmod +x ./start.sh```
    
4) Acquiring, modifying, and installing a camera is up to you.

# Installation (Windows)

1) Download **Grimoire_Windows.zip** located in the root of this repository.
2) Unzip it to any location you like.
3) Open the folder you just unzipped and double click the start.bat file to run the app. You could also cd into that
folder and run the .bat file from the Command Prompt.

# The Camera

The camera I used is a Playstation Eye camera https://en.wikipedia.org/wiki/PlayStation_Eye. They're pretty cheap
(I bought a few of them for 4 bucks each on ebay). I removed the IR filter (a wonderful tutorial to do this can be found
here: https://codelaboratories.com/research/view/ps3-eye-disassembly). And then I glued my visible light filter where the
IR filter used to be.

For my visible light filter I used the plastic lenses from some 3D glasses; the kind you get at the movies, not the old red
and blue ones. The lenses are polarized, so if you lay them on top of each other and rotate them a bit, they should block
out most visible light. They should look almost black to you, but to the modified camera they're as clear as glass.

# The Wand

I bought my wand at Ollivanders in Diagon Alley... at Universal Studios in Florida. :D But any reflective material will do.
The app is set up by default to detect small reflective objects, so whatever you use should be at or around 1cm in
 diameter. Or you could just stand farther away from the camera.

# The Light

As the app uses a camera that senses IR light bouncing off of an object made to reflect IR light, you'll need, wait for
it... An Infrared Light! I bought one of these bad boys: https://www.walmart.com/ip/48-LED-Illuminator-IR-Infrared-Night-Vision-Light-Lamp-For-Camera-1-Pack/822244792?wmlspartner=wlpa&selectedSellerId=7894&adid=22222222227072332454&wmlspartner=wmtlabs&wl0=&wl1=g&wl2=c&wl3=182941377254&wl4=pla-288021340318&wl5=9016851&wl6=&wl7=&wl8=&wl9=pla&wl10=115062034&wl11=online&wl12=822244792&wl13=&veh=sem
But any IR light source will do. I did my initial testing with some candles! Note: the light in my link has a convenient
light sensor that automatically shuts off the IR light when its bright out. Since that light is build to support a
night vision camera, this is great. For our purposes, not so much. I just covered that sensor with some tape.
