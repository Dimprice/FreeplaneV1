/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2009 Dimitry
 *
 *  This file author is Dimitry
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.freeplane.core.ui.components.BitmapViewerComponent;
import org.freeplane.core.util.TextUtils;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.binding.internal.libvlc_logo_position_e;
import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_player_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_stats_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;
import uk.co.caprica.vlcj.player.AudioOutputDeviceType;
import uk.co.caprica.vlcj.player.DeinterlaceMode;
import uk.co.caprica.vlcj.player.MediaDetails;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.TrackDescription;
import uk.co.caprica.vlcj.player.TrackInfo;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

/**
 * @author Dimitry Polivaev
 * 22.08.2009
 * @author ALLY BITEBO 
 * 19.11.2013
 */
public class AudioViewerFactory implements IViewerFactory {
	private static TextField tField;
	public AudioViewerFactory()
	{
		
	}

	public boolean accept(final URI uri) {
		return uri.getRawPath().endsWith(".mp3");
	}
	
	public JComponent createViewer(final ExternalResource resource, final URI uri, int maximumWidth) throws MalformedURLException,
	        IOException {
		String vlcPath = "C:\\Program Files\\VideoLAN\\VLC";
		NativeLibrary.addSearchPath("libvlc", vlcPath);
		System.setProperty("jna.library.path",vlcPath);
		
		
		final URI mediaPath = uri;
		
		JComponent jc = new JPanel();
		jc.setLayout(new GridLayout(3,2,5,10));
		JLabel FileType = new JLabel("FILE TYPE: ");
		JLabel FileName = new JLabel("AUDIO FILE");
		
		JLabel Title = new JLabel("TITLE: ");
		tField = new TextField(20);
		tField.setEditable(true);
		//tField.setText(new Scanner(new File("fieldSave.txt")).useDelimiter("\\A").next());
	//	JLabel TitleData = new JLabel("________________________");
		
		JButton TitleButton = new JButton("Save Title");
		JButton playAudio = new JButton("Play Audio");
		
		jc.add(FileType);
		jc.add(FileName);
		jc.add(Title);
		jc.add(tField);
//		jc.add(TitleData);
		jc.add(TitleButton);
		jc.add(playAudio);
		
		playAudio.addActionListener(new PlayAudio(mediaPath, vlcPath));
		TitleButton.addActionListener(new AddTitleData());
		
		jc.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
	    return jc;
	}
	
	static class PlayAudio implements ActionListener{

		private URI mediaPath = null;
		private String vlcPath = null;
		private JFrame ourFrame = new JFrame();
		private EmbeddedMediaPlayerComponent ourMediaPlayer;
		
		public PlayAudio(URI mediaPath,String vlcPath) {
			this.mediaPath = mediaPath;
			this.vlcPath = vlcPath;
		}

		public void actionPerformed(ActionEvent e) {
			
			// TODO Auto-generated method stub
			long startTime = System.currentTimeMillis();
			//time behavior: start

			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcPath);
			ourMediaPlayer = new EmbeddedMediaPlayerComponent();
			Point location = MouseInfo.getPointerInfo().getLocation(); 
			int x = (int) location.getX();
			int y = (int) location.getY();
			ourFrame.setLocation(x, y);
			ourFrame.setContentPane(ourMediaPlayer);
			ourFrame.setSize(300, 150);
			ourFrame.setVisible(true);
			ourFrame.addWindowListener(new WindowListener() {

				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub
					
					
				}

				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					ourMediaPlayer.release();
					ourFrame.setVisible(false);
					
				}
			});
			ourMediaPlayer.getMediaPlayer().playMedia(mediaPath.toString());
			long stopTime = System.currentTimeMillis();
			//time behavior: stop
			long elapsedTime = stopTime - startTime;
			System.out.println("Execution time: "+elapsedTime+" ms");
		}
		
	}
	
	static class AddTitleData implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			tField.setEditable(false);
			try {
				FileUtils.writeStringToFile(new File("fieldSave.txt"), tField.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			JOptionPane.showMessageDialog(null, "Add title description");
//			JTextField yourInputField = new JTextField(16);
			//content.add(yourInputField);
//			codes to change description label
		}
		
	}
	class SaveTitle implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		 tField.setEditable(false);
			
		}
		
	}

	public JComponent createViewer(final URI uri, final Dimension preferredSize) throws MalformedURLException,
	        IOException {
				return null;
	}

	public String getDescription() {
		return TextUtils.getText("mp3");
	}

	public Dimension getOriginalSize(final JComponent viewer) {
		return null;
//		return ((BitmapViewerComponent) viewer).getOriginalSize();
	}

	public void setFinalViewerSize(final JComponent viewer, final Dimension size) {
		viewer.setPreferredSize(size);
//		((BitmapViewerComponent) viewer).setScaleEnabled(true);
	}

	public void setDraftViewerSize(JComponent viewer, Dimension size) {
		viewer.setPreferredSize(size);
//		((BitmapViewerComponent) viewer).setScaleEnabled(false);
	}
}
