/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2010 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitry Polivaev
 *
 *  This file is created by Stefan Ott in 2011.
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
package org.freeplane.view.swing.features.filepreview;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.freeplane.core.resources.ResourceController;
import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.core.ui.EnabledAction;
import org.freeplane.core.ui.components.UITools;
import org.freeplane.features.icon.IIconInformation;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;
import org.freeplane.view.swing.features.progress.mindmapmode.ProgressUtilities;

/**
 * 
 * @author Joffrey Diebold
 *
 *This action adds a user icon to left bar. It is used for the left bar button.
 */
@EnabledAction(checkOnNodeChange = true)
public class AddIconActionLeftBar extends AFreeplaneAction implements IIconInformation {
	private static final long serialVersionUID = 1L;

	public AddIconActionLeftBar() {
		super("AddIconActionLeftBar");
	}

	public void actionPerformed(final ActionEvent arg0) {
		final ProgressUtilities progUtil = new ProgressUtilities();
		final MapController mapController = Controller.getCurrentModeController().getMapController();
		final Collection<NodeModel> nodes = mapController.getSelectedNodes();
		final ViewerController vc = ((ViewerController) Controller.getCurrentController().getModeController()
		    .getExtension(ViewerController.class));
		final NodeModel selectedNode = mapController.getSelectedNode();
		final ExternalResource extRes = (ExternalResource) vc.createExtension(selectedNode);
		long startTime = System.currentTimeMillis();
		if (extRes != null) {
			final File file = new File(extRes.getAbsoluteUri(selectedNode.getMap()));
			final File userDir = new File(ResourceController.getResourceController().getFreeplaneUserDirectory());
			final String iconDir = userDir.getAbsolutePath()+"\\icons";
			final String filename = file.getName();
			final String finalpath = iconDir+"\\"+filename;
			final File fileCopy = new File(finalpath);
			CopyFile(file,fileCopy);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("Execution time: "+elapsedTime+" ms");
			JOptionPane.showMessageDialog(null, "Restart the application so that changes apply.");
		}
		
	}

	@Override
	public void setEnabled() {
		boolean enable = false;
		final ProgressUtilities progUtil = new ProgressUtilities();
		final Collection<NodeModel> nodes = Controller.getCurrentModeController().getMapController().getSelectedNodes();
		for (final NodeModel node : nodes) {
			if (node != null && !progUtil.hasExternalResource(node)) {
				enable = true;
				break;
			}
		}
		setEnabled(enable);
	}
	
	private boolean CopyFile(File Source, File Destination){
        boolean result=false;
        FileInputStream filesource=null;
        FileOutputStream fileDestination=null;
        try{
            filesource=new FileInputStream(Source);
            fileDestination=new FileOutputStream(Destination);
            byte buffer[]=new byte[512*1024];
            int nblecture;
            while((nblecture=filesource.read(buffer))!=-1){
                fileDestination.write(buffer,0,nblecture);
            }
            result=true;
        }catch(FileNotFoundException nf){
            nf.printStackTrace();
        }catch(IOException io){
            io.printStackTrace();
        }finally{
            try{
                filesource.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                fileDestination.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        } 
        return result;
    }

	public String getDescription() {
		return (String) getValue(Action.NAME);
	}

	public Icon getIcon() {
		return (ImageIcon) getValue(Action.SMALL_ICON);
	}

	public KeyStroke getKeyStroke() {
		return UITools.getKeyStroke(ResourceController.getResourceController().getProperty(getShortcutKey()));
	}

	public String getShortcutKey() {
		return getKey() + ".shortcut";
	}
}

