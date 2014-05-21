// TODO: comment this file

import java.util.*;
import acm.program.*;
import java.io.*;


public class Melody extends ConsoleProgram {
	
	// Array instance variable of Note objects
	private Note[] notesArray;
	private String artist;
	private String title;
	private Double totalDuration = 0.0;
	
	public Melody(String fileName) {
		try {
			// read from lyrics file
			Scanner input = new Scanner(new File(fileName));
			
			// get artist, title and number of notes
			title = input.nextLine();
			System.out.println(title);
			artist = input.nextLine();
			System.out.println(artist);
			int numberOfNotes = input.nextInt();
			String nothing = input.nextLine();
			System.out.println(numberOfNotes);
			// initialize array to the length numberOfNotes
			notesArray = new Note[numberOfNotes];
			// initialize counter for array index
			int counter = 0;
			totalDuration = 0.0;
			// for each line in the file after the first 3 lines, create a note object and save it to the array.
			while(input.hasNextLine()){
				String nextLine = input.nextLine();
				Scanner tokens = new Scanner(nextLine);
				// read duration
				double duration = tokens.nextDouble();
				// read pitch
				String p = tokens.next();
				Note songNote;
				if(p.equals("R")){
					// read repeat
					boolean repeat = tokens.nextBoolean();
					songNote = new Note(duration, repeat);
				} else {
					Pitch pitch = Pitch.valueOf(p);
					// read octave
					int octave = tokens.nextInt();
					// read accidental 
					String s = tokens.next();
					Accidental accidental = Accidental.valueOf(s);
					// read repeat
					boolean repeat = tokens.nextBoolean();
					songNote  = new Note(duration, pitch, octave, accidental, repeat);
				}
				
				// Save songNote object into notesArray array.
				notesArray[counter] = songNote;
				counter++;
				totalDuration += duration;
			}
			System.out.println(totalDuration);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Error reading file " + fnfe);
		}
	}
	
	public void changeDuration(double ratio) {
		for (int i = 0; i < notesArray.length; i++){
			notesArray[i].setDuration(notesArray[i].getDuration() * ratio);
		}
	}
	
	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}
	
	public double getTotalDuration() {
		return totalDuration;
	}
	
	public boolean octaveDown() {
		// check if any notes have octave = 1
		boolean isOctaveOne = false;
		for (int i = 0; i < notesArray.length; i++){
			if(notesArray[i].getOctave() == 1){
				isOctaveOne = true;
			}
		}
		
		if(!isOctaveOne) {
			for (int i = 0; i < notesArray.length; i++){
				if(!notesArray[i].isRest()){
					notesArray[i].setOctave(notesArray[i].getOctave() - 1);
				}
				
			}
			return true;
		} else {
			return false;
		}

		
	}
	
	public boolean octaveUp() {
		// check if any notes have octave = 1
		boolean isOctaveOne = false;
		for (int i = 0; i < notesArray.length; i++){
			if(notesArray[i].getOctave() == 10){
				isOctaveOne = true;
			}
		}
		
		if(!isOctaveOne) {
			for (int i = 0; i < notesArray.length; i++){
				if(!notesArray[i].isRest()){
					notesArray[i].setOctave(notesArray[i].getOctave() + 1);
				}
				
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void play() {
		for (int i = 0; i < notesArray.length; i++){
			// play the note.
			notesArray[i].play();
		}
	}
	
	public void reverse() {
		// TODO: implement this method
		for(int i = 0; i < (notesArray.length)/2; i++){
			System.out.println(i);
			// move i to notesArray.length - i
			// move notesArray.length - i to i
			Note temp = notesArray[i];
			notesArray[i] = notesArray[notesArray.length - 1 - i]; 
			notesArray[notesArray.length - 1 - i] = temp;
			
		}
		
	}
	
	public String toString() {
		// TODO: implement this method (optional)
		return "";
	}
}
