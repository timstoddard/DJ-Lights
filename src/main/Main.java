package main;

import java.awt.GraphicsEnvironment;

import javax.swing.JOptionPane;

import soundin.BeatDetector;

public class Main {
	public static void main(String[] args) {
		Frame[] frames;
		if (GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length > 0) {
			String numOfFrames = "";
			int failedAttempts = 0;
			while (!isInt(numOfFrames)) {
				numOfFrames = (String)JOptionPane.showInputDialog(
		                null, "How many frames do you want?\n" +
		                (failedAttempts > 0 ? "Please enter in integer greater than zero."
		                : "WARNING: If you make a lot of frames,\nyour computer may run very slowly!"),
		                "Number of Frames", JOptionPane.PLAIN_MESSAGE, null, null, "1");
				if (numOfFrames == null) {
					System.exit(1);
				}
				failedAttempts++;
			}
			frames = new Frame[Integer.parseInt(numOfFrames)];
			if (frames.length == 1) {
				frames[0] = new Frame("Turn Up!");
			} else {
				for (int i = 0; i < frames.length; i++) {
					frames[i] = new Frame("Turn Up " + (i + 1));				}
			}
		} else {
			frames = new Frame[]{new Frame("Turn Up!")};
		}
		for (Frame frame : frames) {
			BeatDetector bd = new BeatDetector(frame);
			bd.start();
		}
	}
	
	private static boolean isInt(String s) {
		if (s == null || s.equals("")) {
			return false;
		}
		for (char c : s.toCharArray()) {
	        if (!Character.isDigit(c)) {
	        	return false;
	        }
	    }
	    return true;
	}
}