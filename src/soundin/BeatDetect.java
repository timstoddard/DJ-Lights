package soundin;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.*;

public class BeatDetect {
	
	public BeatDetect() {
		
	}
	
	public static void main(String[] args) {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
	    try {
			TargetDataLine line = AudioSystem.getTargetDataLine(format);
			// format is an AudioFormat object
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				// Handle the error ...
			}
			// Obtain and open the line.
			try {
				line = (TargetDataLine) AudioSystem.getLine(info);
				line.open(format);
				ByteArrayOutputStream out  = new ByteArrayOutputStream();
				int numBytesRead;
				byte[] data = new byte[line.getBufferSize() / 5];

				// Begin audio capture.
				line.start();

				// Here, stopped is a global boolean set by another thread.
				while (true) {
				   // Read the next chunk of data from the TargetDataLine.
				   numBytesRead =  line.read(data, 0, data.length);
				   // Save this chunk of data.
				   out.write(data, 0, numBytesRead);
				   for (int i = 0; i < data.length; i++) {
					   System.out.print(data[i] + " ");
				   }
				   System.out.println();
				}     
			} catch (LineUnavailableException ex) {
				// Handle the error ...
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		//*/
		  
		/*Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info info : mixerInfos) {
			Mixer m = AudioSystem.getMixer(info);
			Line.Info[] lineInfos = m.getSourceLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				System.out.println(info.getName() + "---" + lineInfo);
				Line line;
				try {
					line = m.getLine(lineInfo);
					System.out.println("\t-----" + line);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
			lineInfos = m.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				System.out.println(m + "---" + lineInfo);
				Line line;
				try {
					line = m.getLine(lineInfo);
					System.out.println("\t-----" + line);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}

			}
		}//*/
	}
}