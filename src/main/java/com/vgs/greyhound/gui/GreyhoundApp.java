package com.vgs.greyhound.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class GreyhoundApp extends SingleFrameApplication {

	/**
	 * At startup create and show the main frame of the application.
	 */
	@Override
	protected void startup() {
		show(new GreyhoundAppView(this));
	}

	@Override
	protected void initialize(String[] args) {
		super.initialize(args);
		try {
			MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(GreyhoundApp.class.getName()).log(Level.SEVERE,
					null, ex);
			System.exit(1);
		} catch (InstantiationException ex) {
			Logger.getLogger(GreyhoundApp.class.getName()).log(Level.SEVERE,
					null, ex);
			System.exit(1);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(GreyhoundApp.class.getName()).log(Level.SEVERE,
					null, ex);
			System.exit(1);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(GreyhoundApp.class.getName()).log(Level.SEVERE,
					null, ex);
			System.exit(1);
		}
	}

	/**
	 * This method is to initialize the specified window by injecting resources.
	 * Windows shown in our application come fully initialized from the GUI
	 * builder, so this additional configuration is not needed.
	 */
	@Override
	protected void configureWindow(java.awt.Window root) {
	}

	/**
	 * A convenient static getter for the application instance.
	 * 
	 * @return the instance of GreyhoundApp
	 */
	public static GreyhoundApp getApplication() {
		return Application.getInstance(GreyhoundApp.class);
	}

	/**
	 * Main method launching the application.
	 */
	public static void main(String[] args) {
		launch(GreyhoundApp.class, args);
	}
}
