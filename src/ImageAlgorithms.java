

import java.util.*;
import acm.graphics.*;

public class ImageAlgorithms {
	
	/**
	 * Method to change an image to grey scale
	 * @param source image
	 * @return grey scale version of the source image
	 */
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
	/**
	 * Method to make a color negative of the image by inverting the colors. 
	 * @param source image
	 * @return negative version of the image
	 */
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
	
	/**
	 * Method to rotate the image to the left by 90 degrees.
	 * The method reverse the order of the pixels in each row, and then swaps the rows with the columns.
	 * @param source image
	 * @return source image rotated left by 90 degrees
	 */
	public GImage rotateLeft(GImage source) {
		// Extract the pixels of the image and save into a 2D array 
		int[][] pixels = source.getPixelArray();
		// create resized array
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
	
	/**
	 * Method to rotate the source image by 90 degrees to the right.
	 * The method rotates the image to the left three times.
	 * @param source image
	 * @return source image rotated 90 degrees to the right.
	 */
	public GImage rotateRight(GImage source) {
		GImage rotated = rotateLeft(source);
		rotated = rotateLeft(rotated);
		rotated = rotateLeft(rotated);
		return rotated;
		 
	}
	
	/**
	 * Method to move the image by a given number of pixels in the x and y directions.
	 * The image wraps around the canvas, so parts of the image that disappear at the bottom edge of the canvas appear and the top
	 * and vice - versa for left and right.
	 * @param source imge
	 * @param dx, the distance (+ or -) to move the image's column position
	 * @param dy, the distance (+ or -) to move the image's row position
	 * @return translated image
	 */
	public GImage translate(GImage source, int dx, int dy) {
		// Extract the pixels of the image and save into a 2D array 
		int[][] pixels = source.getPixelArray();
		// create resized array
		int[][] shiftPixels = new int[pixels.length][pixels[0].length];
		
		while (dx < 0){ // ensures dx is positive
			dx = pixels.length + dx;
		}
		// translates dx to the minimum value that achieves the same translation
		dx = dx % pixels.length; 
		
		while (dy < 0){ // ensures dx is positive
			dy = pixels[0].length + dy;
		}
		// translates dx to the minimum value that achieves the same translation
		dy = dy % pixels[0].length; 
		
		// loop through the 2D array moving each pixel to the new translated position
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

	/**
	 * Method to blur the image by making each pixel's color the average of its surrounding pixels 
	 * @param source image
	 * @return blurred image
	 */
	public GImage blur(GImage source) {
		// Extract the pixels of the image and save into a 2D array 
		int[][] pixels = source.getPixelArray();
		// create new array into which to save the blurred pixels, to avoid double-blurring 
		int[][] blurPixels = new int[pixels.length][pixels[0].length];
		
		// Loop through the 2D pixel array 
		for (int row = 0; row < pixels.length; row++){
			for (int col = 0; col < pixels[row].length; col++){
				
				// Initialize the total values of red, green and blue for the surrounding pixels to 0.
				int redTotal = 0;
				int greenTotal = 0;
				int blueTotal = 0;
				// counts how many pixels were used to calculate the redTotal, greenTotal and blueTotal values
				int numberOfPixelsAveraged = 0;
				
				// For each pixel in the 2D array, loop through its immediate neighboring pixels (9 pixels for pixels not on the edge)
				for(int rowOffset = -1; rowOffset < 2; rowOffset++){
					for(int colOffset = -1; colOffset < 2; colOffset++){
						
						// get the position of each pixel in the loop
						int yOffsetPosition = row + rowOffset;
						int xOffsetPosition = col + colOffset;
						
						// check whether the pixel is within the image bounds. If it is, add it's red, green and blue values to the totals.
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
				
				// creates average red, green and blue values for each pixel in the 2D pixel array.
				int averageRed = redTotal / numberOfPixelsAveraged;
				int averageGreen = greenTotal / numberOfPixelsAveraged;
				int averageBlue = blueTotal / numberOfPixelsAveraged;
				
				// creates a blurred pixel using averaged values of it's neighboring pixels and saves this pixel to a new array
				blurPixels[row][col] = GImage.createRGBPixel(averageRed, averageGreen, averageBlue);
			}
		}
		
		GImage blurImage = new GImage(blurPixels);
		return blurImage; 
	}
	
	/**
	 * Method to mix colors by swapping red with green, green with blue and blue with red for each pixel in the image
	 * @param source image
	 * @return image with mixed-up colors
	 */
	public GImage colorMashup(GImage source) {
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
				
				// make the inverted pixel
				pixels[row][col] = GImage.createRGBPixel(green, blue, red);
			}
		}
		GImage mashupImage = new GImage(pixels);
		return mashupImage; 
	}
}
