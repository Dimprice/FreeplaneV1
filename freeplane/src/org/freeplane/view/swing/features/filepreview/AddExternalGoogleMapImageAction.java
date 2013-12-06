package org.freeplane.view.swing.features.filepreview;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import javax.swing.JOptionPane;

import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.core.ui.EnabledAction;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;
import org.freeplane.view.swing.features.progress.mindmapmode.ProgressUtilities;


/**
 * 
 * @author jahirul haq
 *
 *This action adds an google map image from a given link to a node
 */
@EnabledAction(checkOnNodeChange = true)
public class AddExternalGoogleMapImageAction extends AFreeplaneAction {
	private static final long serialVersionUID = 1L;

	public AddExternalGoogleMapImageAction() {
		super("ExternalGoogleMapImageAddAction");
	}

	public void actionPerformed(final ActionEvent arg0) {
		String destinationFile = null;
		
		String input =  (String)JOptionPane.showInputDialog(
                null,
                "Add a google link",
                "Display google link",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "http:://");
		System.out.println("Input " + input);
	
			
		
		if(input != "http:://" || input !="" )
		{
        try {
        
            String imageUrl = input;
            String publicDir = System.getProperty("user.home");
            
            System.out.println("PUBLIC DIR " +  publicDir);
            destinationFile = publicDir + "/image.jpg";
            
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            
        }

	}
		final ProgressUtilities progUtil = new ProgressUtilities();
		final MapController mapController = Controller.getCurrentModeController().getMapController();
		final Collection<NodeModel> nodes = mapController.getSelectedNodes();
		final ViewerController vc = ((ViewerController) Controller.getCurrentController().getModeController()
		    .getExtension(ViewerController.class));
		final NodeModel selectedNode = mapController.getSelectedNode();
		
		URL savedImageUrl = null;
		try {
			
			savedImageUrl = new URL("file://" + destinationFile);
			System.out.println("Url is " + savedImageUrl.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectedNode.getMap().setURL(savedImageUrl);
		
		
		
		final File file = new File(destinationFile);	
			//final File file = new File(extRes.getAbsoluteUri(selectedNode.getMap()));
			for (final NodeModel node : nodes) {
				if (!progUtil.hasExternalResource(node)) {
					vc.paste(file, node, node.isLeft());
				}
			
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
}
