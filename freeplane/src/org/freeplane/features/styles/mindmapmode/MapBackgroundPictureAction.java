/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2009 Dimitry Polivaev
 *
 *  This file author is Dimitry Polivaev
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.features.styles.mindmapmode;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.freeplane.core.resources.ResourceController;
import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.core.ui.ColorTracker;
import org.freeplane.core.util.ColorUtils;
import org.freeplane.core.util.TextUtils;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.styles.MapStyle;
import org.freeplane.features.styles.MapStyleModel;
import org.freeplane.features.ui.ViewController;
import org.freeplane.features.url.UrlManager;
import org.freeplane.view.swing.features.filepreview.BitmapViewerFactory;
import org.freeplane.view.swing.features.filepreview.IViewerFactory;
import org.freeplane.view.swing.features.filepreview.ImagePreview;


/**
 * @author Yiming Gong	
 * Dec 1, 2013
 */
class MapBackgroundPictureAction extends AFreeplaneAction {

	private static final long serialVersionUID = 1L;
	MapBackgroundPictureAction() {
		super("MapBackgroundPictureAction");
	}

	public void actionPerformed(final ActionEvent e) {
		final Controller controller = Controller.getCurrentController();
		MapStyle mapStyle = (MapStyle) controller.getModeController().getExtension(MapStyle.class);
		final MapStyleModel model = (MapStyleModel) mapStyle.getMapHook();
		final String oldBackgroundPicture;
		final String picturePropertyString = ResourceController.getResourceController().getProperty(
		    MapStyle.RESOURCES_BACKGROUND_PICTURE);
		final String defaultBgPicture = picturePropertyString;
		if (model != null) {
			oldBackgroundPicture = model.getBackgroundPicture();
		}
		else {
			oldBackgroundPicture = defaultBgPicture;
		}
		
		// launch the file chooser dialogue
		final ViewController viewController = controller.getViewController();
		final UrlManager urlManager = controller.getModeController().getExtension(UrlManager.class);
		final JFileChooser chooser = urlManager.getFileChooser(null, false);
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileFilter filter = new FileNameExtensionFilter("bmp files", "bmp");
		chooser.addChoosableFileFilter(filter);
		filter = new FileNameExtensionFilter("gif files", "gif");
		chooser.addChoosableFileFilter(filter);
		filter = new FileNameExtensionFilter("jpg files", "jpg");
		chooser.addChoosableFileFilter(filter);
		filter = new FileNameExtensionFilter("png files", "png");
		chooser.addChoosableFileFilter(filter);
		
		int ret = chooser.showDialog(viewController.getContentPane(), "Select Background Picture");
		if (ret != JFileChooser.APPROVE_OPTION) {
			return;
	    }
		
		long startTime = System.currentTimeMillis();
		final String actionPicture = chooser.getSelectedFile().getAbsolutePath();
		mapStyle.setBackgroundColor(model, Color.WHITE);
		mapStyle.setBackgroundPicture(model, actionPicture);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Execution time: "+elapsedTime+" ms");
	}
}
