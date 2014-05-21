// TODO: comment this file

import java.util.*;
import acm.graphics.*;

public class ImageAlgorithms {
	public GImage grayscale(GImage source) {
		// for every pixel, set it's set R, G, B to the average of the three values, rounded down.
		// Extract the pixels of the image and save into a 2D array 
		int[][] pixels = source.getPixelArray();

		// loop through image array getting every pixel into a 2D array 
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				int pixel = pixels[row][col];
				
				// get the R, G, B value of each pixel
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				
				// Average the R, G, B values
				int grey = (int) Math.floor((red + green + blue)/3.0);
				
				// make the pixel grey
				pixels[row][col] = GImage.createRGBPixel(grey, grey, grey);
				
			}
		}
		GImage greyScale = new GImage(pixels);
		return greyScale; 
	}

	public GImage negative(GImage source) {
		// Extract the pixels of the image and save into a 2D array 
		int[][] pixels = source.getPixelArray();

		// loop through image array getting every pixel into a 2D array 
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				int pixel = pixels[row][col];
				
				// get the R, G, B value of each pixel
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				
				// Invert the color of each pixel
				int invertRed = 255 - red;
				int invertGreen = 255 - green;
				int invertBlue = 255 - blue;
				
				// make the inverted pixel
				pixels[row][col] = GImage.createRGBPixel(invertRed, invertGreen, invertBlue);
			}
		}
		GImage negativeImage = new GImage(pixels);
		return negativeImage; 
	}

	public GImage rotateLeft(GImage source) {
		// Extract the pixels of the image and save into a 2D array 
		int[][] pixels = source.getPixelArray();
		// resized array
		int[][] rotatedLeftPixels = new int[pixels[0].length][pixels.length];

		// loop through image array getting every pixel into a 2D array 
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length/2; col++){
				
				// for each row, reverse the pixels in that row
				int temp = pixels[row][col];
				pixels[row][col] = pixels[row][pixels[row].length -1 - col];
				pixels[row][pixels[row].length -1 - col] = temp;
			}
		}
		
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				// swap the rows and columns
				rotatedLeftPixels[col][row] = pixels[row][col];
			}
		}
		
		GImage rotatedLeft = new GImage(rotatedLeftPixels);
		return rotatedLeft; 
	}

	public GImage rotateRight(GImage source) {
		GImage rotated = rotateLeft(source);
		rotated = rotateLeft(rotated);
		rotated = rotateLeft(rotated);
		return rotated;
		 
	}
	
	public GImage translate(GImage source, int dx, int dy) {
		int[][] pixels = source.getPixelArray();
		int[][] shiftPixels = new int[pixels.length][pixels[0].length];
		
		while (dx < 0){ // ensures dx is positive
			dx = pixels.length + dx;
		}
		dx = dx % pixels.length;
		
		while (dy < 0){ // ensures dx is positive
			dy = pixels[0].length + dy;
		}
		dy = dy % pixels[0].length;
		
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				int rowPosition = (row + dy) % pixels[row].length;
				int colPosition = (col + dx) % pixels.length;
				shiftPixels[rowPosition][colPosition] = pixels[row][col]; 
			} 
		}
		GImage shifted = new GImage(shiftPixels);
		return shifted; 
	}

	public GImage blur(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] blurPixels = new int[pixels.length][pixels[0].length];
		
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				
				int redTotal = 0;
				int greenTotal = 0;
				int blueTotal = 0;
				int numberOfPixelsAveraged = 0;
				
				for(int rowOffset = -1; rowOffset < 2; rowOffset++){
					for(int colOffset = -1; colOffset < 2; colOffset++){
						
						int yOffsetPosition = row + rowOffset;
						int xOffsetPosition = col + colOffset;
						
						if(yOffsetPosition >= 0 && yOffsetPosition < pixels.length && xOffsetPosition >= 0 && xOffsetPosition < pixels[row].length){
							int red = GImage.getRed(pixels[yOffsetPosition][xOffsetPosition]);
							redTotal += red;
							int green = GImage.getGreen(pixels[yOffsetPosition][xOffsetPosition]);
							greenTotal += green;
							int blue = GImage.getBlue(pixels[yOffsetPosition][xOffsetPosition]);
							blueTotal += blue;
							numberOfPixelsAveraged++;
						}
					}
				}
				
				int averageRed = redTotal / numberOfPixelsAveraged;
				int averageGreen = greenTotal / numberOfPixelsAveraged;
				int averageBlue = blueTotal / numberOfPixelsAveraged;
				
				blurPixels[row][col] = GImage.createRGBPixel(averageRed, averageGreen, averageBlue);
			}
		}
		
		GImage blurImage = new GImage(blurPixels);
		return blurImage; 
	}
	
	/**
	 * Method to mix colors by swapping red with green, green with blue and blue with red for each pixel in the image
	 */
	public GImage colorMashup(GImage source) {
		
		int[][] pixels = source.getPixelArray();

		// loop through image array getting every pixel into a 2D array 
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				int pixel = pixels[row][col];
				
				// get the R, G, B value of each pixel
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				
				// make the inverted pixel
				pixels[row][col] = GImage.createRGBPixel(green, blue, red);
			}
		}
		GImage mashupImage = new GImage(pixels);
		return mashupImage; 
	}
}
