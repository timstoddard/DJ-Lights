# DJ-Lights
DJ Lights program

This is a DJ Lights program I wrote using Java. There are currently 5 visual modes (Beams, Dots, Madness, Spinner, Strobe), as well as 2 mode switching options (Cycle and Random). I recently added the ability for the program to detect beats via the microphone, and will soon be updating all of the visual effects to include reactivity to audio input.

There is currently a bug where the dropdown menus in the control panel do not work in fullscreen. (More info can be found at https://bugs.openjdk.java.net/browse/JDK-8013547) This is seemingly due to the behavior of the java fullscreen API. The best workaround I have found is to set all desired settings in windowed mode, then enter fullscreen and enjoy the show.

***Code Libraries Used***
   -For my range sliders, I modified the code from https://github.com/ernieyu/Swing-range-slider
   -For beat detection, I used the Minim audio library (https://github.com/ddf/Minim)
   -Along with Minim, I also used the org.tritons.share package (http://tritonus.sourceforge.net/apidoc/org/tritonus/share/package-tree.html), the javazoom.spi package (http://www.javazoom.net/mp3spi/mp3spi.html), and the javazoom.jl package (http://www.java2s.com/Code/Jar/j/Downloadjl10jar.htm).

Note: If you would prefer to use an input other than the built-in microphone and are on OS X, there is an application called SoundFlower that will enable you to do this. Windows already has this feature built-in.