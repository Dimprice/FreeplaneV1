package org.freeplane.view.swing.features.filepreview;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;

import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.core.ui.EnabledAction;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;
import org.freeplane.view.swing.features.progress.mindmapmode.ProgressUtilities;


/**
 * 
 * @author Stefan Ott & Ally Bitebo
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
		final ProgressUtilities progUtil = new ProgressUtilities();
		final MapController mapController = Controller.getCurrentModeController().getMapController();
		final Collection<NodeModel> nodes = mapController.getSelectedNodes();
		final ViewerController vc = ((ViewerController) Controller.getCurrentController().getModeController()
		    .getExtension(ViewerController.class));
		final NodeModel selectedNode = mapController.getSelectedNode();
		final ExternalResource extRes = (ExternalResource) vc.createExtension(selectedNode);
		if (extRes != null) {
			final File file = new File(extRes.getAbsoluteUri(selectedNode.getMap()));
			for (final NodeModel node : nodes) {
				if (!progUtil.hasExternalResource(node)) {
					vc.paste(file, node, node.isLeft());
				}
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
