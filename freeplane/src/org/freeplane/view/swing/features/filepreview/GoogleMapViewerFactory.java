package org.freeplane.view.swing.features.filepreview;

import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.freeplane.core.ui.components.BitmapViewerComponent;
import org.freeplane.core.util.TextUtils;



/**
 * @author Dimitry Polivaev
 * 22.08.2009
 * @author Ally Bitebo
 * 22.11.2013
 */
public class GoogleMapViewerFactory implements IViewerFactory{
	public boolean accept(final URI uri) {
		final Iterator<ImageReader> readers = getImageReaders(uri);
		return readers.hasNext();
	}

	private Iterator<ImageReader> getImageReaders(final URI uri) {
        String path = uri.getRawPath();
		final int suffixPos = path.lastIndexOf('.') + 1;
		if (suffixPos == 0) {
			final List<ImageReader> empty = Collections.emptyList();
			return empty.iterator();
		}
		final String suffix = path.substring(suffixPos);
		final Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(suffix);
		return readers;
	}

	public JComponent createViewer(final ExternalResource resource, final URI uri, int maximumWidth) throws MalformedURLException,
	        IOException {
//		JOptionPane.showMessageDialog(null, "Hej outside googlemap frame");
//		final BitmapViewerComponent bitmapViewerComponent = new BitmapViewerComponent(uri);
//		final Dimension originalSize = bitmapViewerComponent.getOriginalSize();
//		float zoom = resource.getZoom();
//		if(zoom == -1){
//			zoom = resource.setZoom(originalSize.width, maximumWidth);
//		}
//		originalSize.width = (int) (originalSize.width * zoom);
//		originalSize.height = (int) (originalSize.height * zoom);
//		setFinalViewerSize(bitmapViewerComponent, originalSize);
//		bitmapViewerComponent.setSize(originalSize);
//		bitmapViewerComponent.setLayout(new ViewerLayoutManager(1f));
		
		JComponent jc = new JPanel();
		JFrame test = new JFrame("Google Maps");

        try {
        	//JOptionPane.showMessageDialog(null, "Hej inside googlemap frame");
//            String imageUrl = "http://maps.google.com/staticmap?center=40,26&zoom=1&size=150x112&maptype=satellite&key=ABQIAAAAgb5KEVTm54vkPcAkU9xOvBR30EG5jFWfUzfYJTWEkWk2p04CHxTGDNV791-cU95kOnweeZ0SsURYSA&format=jpg";
            String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=true";
            String destinationFile = "image.jpg";
            String str = destinationFile;
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
            System.exit(1);
        }

//        test.add(new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600,java.awt.Image.SCALE_SMOOTH))));

//        test.setVisible(true);
//        test.pack();
		
//		bitmapViewerComponent.add(new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600,
//                java.awt.Image.SCALE_SMOOTH))));
//		return bitmapViewerComponent;
        jc.add(new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600,java.awt.Image.SCALE_SMOOTH))));
        return jc;
	}

	public JComponent createViewer(final URI uri, final Dimension preferredSize) throws MalformedURLException,
	        IOException {
				return null;
//		final BitmapViewerComponent bitmapViewerComponent = new BitmapViewerComponent(uri);
//		setFinalViewerSize(bitmapViewerComponent, preferredSize);
//		bitmapViewerComponent.setSize(preferredSize);
//		return bitmapViewerComponent;
	}

	public String getDescription() {
		return TextUtils.getText("bitmaps");
	}

	public Dimension getOriginalSize(final JComponent viewer) {
		return ((BitmapViewerComponent) viewer).getOriginalSize();
	}

	public void setFinalViewerSize(final JComponent viewer, final Dimension size) {
		viewer.setPreferredSize(size);
		((BitmapViewerComponent) viewer).setScaleEnabled(true);
	}

	public void setDraftViewerSize(JComponent viewer, Dimension size) {
		viewer.setPreferredSize(size);
		((BitmapViewerComponent) viewer).setScaleEnabled(false);
	}
}
