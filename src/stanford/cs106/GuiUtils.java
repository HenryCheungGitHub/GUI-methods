/*
 * CS 106A
 *
 * This instructor-provided file contains utility functions related to GUIs.
 *
 * Author : Marty Stepp
 * Version: Tue 2014/05/11
 * 
 * This file and its contents are copyright (C) Stanford University and Marty Stepp,
 * licensed under Creative Commons Attribution 2.5 License.  All rights reserved.
 */


// TO DO: This file's documentation is insufficient due to time constraints.
// Improve documentation in future quarters for better style and maintainability.

package stanford.cs106;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class GuiUtils {
	/*
	 * ...
	 */
	private GuiUtils() {
		// empty
	}
	
	/*
	 * ...
	 */
	public static void centerWindow(Window window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(screenSize.width/2 - window.getWidth()/2,
				screenSize.height/2 - window.getHeight()/2);
	}

	/*
	 * ...
	 */
	public static JButton createButton(String text, String icon, char mnemonic, ActionListener listener) {
		JButton button = new JButton(text);
		if (mnemonic != '\0' && mnemonic != ' ') {
			button.setMnemonic(mnemonic);
		}
		if (icon != null && icon.length() > 0) {
			try {
				if (ResourceUtils.fileExists(icon)) {
					button.setIcon(new ImageIcon(ResourceUtils.filenameToURL(icon)));
				}
			} catch (Exception e) {
				try {
					button.setIcon(new ImageIcon(ResourceUtils.filenameToURL(icon)));
				} catch (IOException ioe) {
					// empty
				}
			}
		}
		button.addActionListener(listener);
		return button;
	}
	
	/*
	 * adds a JPanel to the layout
	 */
	public static JPanel createPanel(Component... components) {
		return createPanel(new FlowLayout(FlowLayout.CENTER), components);
	}
	
	/*
	 * adds a JPanel to the layout
	 */
	public static JPanel createPanel(LayoutManager layout, Component... components) {
		JPanel panel = new JPanel(layout);
		for (Component comp : components) {
			panel.add(comp);
		}
		return panel;
	}
	
	/*
	 * ...
	 */
	public static FileFilter getExtensionFileFilter(String description, String... extensions) {
		return new ExtensionFileFilter(description, extensions);
	}
	
	/*
	 * ...
	 */
	private static class ExtensionFileFilter extends FileFilter {
		private String description;
		private String[] extensions;
		
		/*
		 * ...
		 */
		public ExtensionFileFilter(String description, String[] extensions) {
			this.description = description;
			this.extensions = extensions;
		}
		
		/*
		 * ...
		 */
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			String filename = file.getName().toLowerCase();
			for (String extension : extensions) {
				extension = "." + extension.toLowerCase();
				if (filename.endsWith(extension)) {
					return true;
				}
			}
			return false;
		}
		
		/*
		 * ...
		 */
		@Override
		public String getDescription() {
			return description;
		}
	}
}
