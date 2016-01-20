# DJ-Lights
DJ Lights program

This is a DJ Lights program I wrote using Java. There are currently 8 visual modes (Beams, Blades, Dots, Madness, RGB, Spinner, Strobe, Swirl), as well as 2 mode switching options (Cycle and Random). The program uses audio input to detect beats in the music that is playing. For optimal visual effects you should use a projector and a fog machine, but they look fine on a computer screen.

There is currently a bug where the dropdown menus in the control panel do not work in fullscreen. (More info can be found at https://bugs.openjdk.java.net/browse/JDK-8013547) This is seemingly due to the behavior of the java fullscreen API. The best workaround I have found is to set all desired settings in windowed mode, then enter fullscreen and enjoy the show.

***Code Libraries Used*** For beat detection, I used the Minim audio library (https://github.com/ddf/Minim). In order for Minim to work, I also used the org.tritons.share package (http://tritonus.sourceforge.net/apidoc/org/tritonus/share/package-tree.html), the javazoom.spi package (http://www.javazoom.net/mp3spi/mp3spi.html), and the javazoom.jl package (http://www.java2s.com/Code/Jar/j/Downloadjl10jar.htm).

Note: If you would prefer to choose an input other than the built-in microphone and are on OS X, there is an application called SoundFlower that will enable you to do this. Windows already has this feature built-in.


This program was inspired by MusicBeam (http://www.musicbeam.org/).

This product was created by Stoddard Studios.
(C) 2016 Tim Stoddard. All rights reserved.