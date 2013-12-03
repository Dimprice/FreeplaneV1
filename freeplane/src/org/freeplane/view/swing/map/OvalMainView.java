package org.freeplane.view.swing.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.nodestyle.NodeStyleModel;

public class OvalMainView extends MainView {

    final static Stroke DEF_STROKE = new BasicStroke();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public
	Point getLeftPoint() {
		final Point in = new Point(0, getHeight() / 2);
		return in;
	}

	@Override
    public
	Point getRightPoint() {
		final Point in = getLeftPoint();
		in.x = getWidth() - 1;
		return in;
	}

	/*
	 * (non-Javadoc)
	 * @see freeplane.view.mindmapview.NodeView#getStyle()
	 */
	@Override
    public
	String getShape() {
		return NodeStyleModel.STYLE_OVAL;
	}

	@Override
	public void paintComponent(final Graphics graphics) {
		final Graphics2D g1 = (Graphics2D) graphics;
		final NodeView nodeView = getNodeView();
		final NodeModel model = nodeView.getModel();
		if (model == null) {
			return;
		}
		final ModeController modeController = getNodeView().getMap().getModeController();
		final Object renderingHint = modeController.getController().getViewController().setEdgesRenderingHint(g1);
		paintBackgound(g1);
		paintDragOver(g1);
		final Color edgeColor = nodeView.getEdgeColor();
		g1.setColor(edgeColor);
		g1.setStroke(OvalMainView.DEF_STROKE);
		g1.drawOval(0, 0, getWidth() -1, getHeight() -1);
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, renderingHint);
		super.paintComponent(g1);
	}

	@Override
	protected void paintBackground(final Graphics2D graphics, final Color color) {
		graphics.setColor(color);
		graphics.fillOval(0, 0, getWidth() -1, getHeight() -1);
		
	}
    private static Insets insets = new Insets(3, 3, 3, 3);
    
    @Override
    public Insets getInsets() {
        return OvalMainView.insets;
    }

    @Override
    public Insets getInsets(Insets insets) {
        return OvalMainView.insets;
    }

}
