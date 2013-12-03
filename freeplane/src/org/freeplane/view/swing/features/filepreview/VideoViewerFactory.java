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
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.media.Manager;
//import javax.media.MediaLocator;
//import javax.media.Player;

import org.freeplane.core.ui.components.BitmapViewerComponent;
import org.freeplane.core.util.TextUtils;
import org.freeplane.view.swing.features.filepreview.AudioViewerFactory.AddTitleData;
import org.freeplane.view.swing.features.filepreview.AudioViewerFactory.PlayAudio;

import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

/**
 * @author Dimitry Polivaev
 * 19.11.20
 * @author ALLY BITEBO 
 * 19.11.2013
 */
public class VideoViewerFactory implements IViewerFactory {
	
	public VideoViewerFactory()
	{
		
	}
	
	public boolean accept(final URI uri) {
		return uri.getRawPath().endsWith(".mp4");
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
		JLabel FileName = new JLabel("VIDEO FILE");
		
		JLabel Title = new JLabel("TITLE: ");
		JLabel TitleData = new JLabel("________________________");
		
		JButton TitleButton = new JButton("Add Title");
		JButton playVideo = new JButton("Play Video");
		
		jc.add(FileType);
		jc.add(FileName);
		jc.add(Title);
		jc.add(TitleData);
		jc.add(TitleButton);
		jc.add(playVideo);
		
		playVideo.addActionListener(new PlayAudio(mediaPath, vlcPath));
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
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcPath);
			ourMediaPlayer = new EmbeddedMediaPlayerComponent();
			Point location = MouseInfo.getPointerInfo().getLocation(); 
			int x = (int) location.getX();
			int y = (int) location.getY();
			ourFrame.setLocation(x, y);
			ourFrame.setContentPane(ourMediaPlayer);
			ourFrame.setSize(800, 400);
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
		}
		
	}
	
	static class AddTitleData implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "Add title description");
//			codes to change description label
		}
		
	}


	public JComponent createViewer(final URI uri, final Dimension preferredSize) throws MalformedURLException,
	IOException {
		return null;
	}

	public String getDescription() {
		return TextUtils.getText("mp4");
	}

	public Dimension getOriginalSize(final JComponent viewer) {
		return null;
	}

	public void setFinalViewerSize(final JComponent viewer, final Dimension size) {
		viewer.setPreferredSize(size);
	}

	public void setDraftViewerSize(JComponent viewer, Dimension size) {
		viewer.setPreferredSize(size);
	}
}