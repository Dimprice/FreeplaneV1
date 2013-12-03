package org.freeplane.view.swing.features.filepreview;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.freeplane.core.util.TextUtils;
import org.freeplane.view.swing.features.filepreview.AudioViewerFactory.AddTitleData;
import org.freeplane.view.swing.features.filepreview.AudioViewerFactory.PlayAudio;
import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class PDFViewerFactory implements IViewerFactory{

	public boolean accept(URI uri) {
		// TODO Auto-generated method stub
		return uri.getRawPath().endsWith(".pdf");
	}

	public JComponent createViewer(ExternalResource resource, final URI uri,int maximumWidth) throws MalformedURLException, IOException {
		
		final URI mediaPath = uri;
		
		JComponent jc = new JPanel();
		jc.setLayout(new GridLayout(3,2,5,10));
		JLabel FileType = new JLabel("FILE TYPE: ");
		JLabel FileName = new JLabel("PDF FILE");
		
		JLabel Title = new JLabel("TITLE: ");
		JLabel TitleData = new JLabel("________________________");
		
		JButton TitleButton = new JButton("Add Title");
		JButton playAudio = new JButton("Display PDF");
		
		jc.add(FileType);
		jc.add(FileName);
		jc.add(Title);
		jc.add(TitleData);
		jc.add(TitleButton);
		jc.add(playAudio);
		
		playAudio.addActionListener(new DisplayPDF());
		TitleButton.addActionListener(new AddTitleData());
		
		jc.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
	    return jc;
	}
	
	static class DisplayPDF implements ActionListener{

		private URI mediaPath = null;
		private JFrame ourFrame = new JFrame();
		
//		public PlayAudio(URI mediaPath,String vlcPath) {
//			this.mediaPath = mediaPath;
//			this.vlcPath = vlcPath;
//		}

		public void actionPerformed(ActionEvent e) {
			
			// TODO Auto-generated method stub
			Point location = MouseInfo.getPointerInfo().getLocation(); 
			int x = (int) location.getX();
			int y = (int) location.getY();
			ourFrame.setLocation(x, y);
		    ourFrame.getContentPane().setLayout(new BorderLayout());
		    JInternalFrame rootContainer = new JInternalFrame("INTERNAL FRAME 1");
		    Viewer viewer = new Viewer(rootContainer, null);
		    viewer.setupViewer();
		    ourFrame.add(rootContainer, BorderLayout.CENTER);
		    rootContainer.setVisible(true);
		    ourFrame.setTitle("Viewer in External Viewer");
		    ourFrame.setSize(800, 600);
		    ourFrame.addWindowListener(new WindowListener(){
		      public void windowActivated(WindowEvent e) {}
		      public void windowClosed(WindowEvent e) {}
		      public void windowClosing(WindowEvent e) {System.exit(1);}
		      public void windowDeactivated(WindowEvent e) {}
		      public void windowDeiconified(WindowEvent e) {}
		      public void windowIconified(WindowEvent e) {}
		      public void windowOpened(WindowEvent e) {}
		    });
		    
		    //Display Frame
		    ourFrame.setVisible(true);
		    
		    Object[] input;
		    
		    //Specify file you wish to open (JPedal handles getting the byte data)
//		    input = new Object[]{"/PDFData/Hand_Test/crbtrader.pdf"};
		    input = new Object[]{"C:\\my.pdf"};
		    viewer.executeCommand(Commands.OPENFILE, input);
		}
	}
	
	static class AddTitleData implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "Add title description");
//			codes to change description label
		}
		
	}

	public Dimension getOriginalSize(JComponent viewer) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFinalViewerSize(JComponent viewer, Dimension size) {
		// TODO Auto-generated method stub
		
	}

	public void setDraftViewerSize(JComponent viewer, Dimension size) {
		// TODO Auto-generated method stub
		
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return TextUtils.getText("pdf");
	}

	public JComponent createViewer(URI uri, Dimension preferredSize)
			throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
