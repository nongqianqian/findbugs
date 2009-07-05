/*
 * FindBugs - Find Bugs in Java programs
 * Copyright (C) 2006, University of Maryland
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.umd.cs.findbugs.gui2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


/**
 * @author pugh
 */
public class SplitLayout implements FindBugsLayoutManager {

	final MainFrame frame;
	JLabel sourceTitle;
	JSplitPane topLeftSPane;
	JSplitPane topSPane;
	JSplitPane summarySPane;
	JSplitPane mainSPane;

	JButton viewSource = new JButton("View in browser");
	/**
	 * @param frame
	 */
	public SplitLayout(MainFrame frame) {
		this.frame = frame;
	}

	public JMenu createWindowMenu() {
		return null;
	}

	public void resetCommentsInputPane() {
		int position = topLeftSPane.getDividerLocation();
		topLeftSPane.setRightComponent(frame.createCommentsInputPanel());
		topLeftSPane.setDividerLocation(position);
	}

	public void initialize() {

		Font buttonFont = viewSource.getFont();
		viewSource.setFont(buttonFont.deriveFont(buttonFont.getSize()/2));
		viewSource.setPreferredSize(new Dimension(150,10));
		viewSource.setEnabled(false);

		topLeftSPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				frame.bugListPanel(), frame.createCommentsInputPanel());
		topLeftSPane.setOneTouchExpandable(true);
		topLeftSPane.setDividerLocation(GUISaveState.getInstance().getSplitTreeComments());

		JPanel sourceTitlePanel = new JPanel();
		sourceTitlePanel.setLayout(new BorderLayout());

		JPanel sourcePanel = new JPanel();
		sourcePanel.setLayout(new BorderLayout());
		sourceTitle = new JLabel();
		sourceTitle.setText(edu.umd.cs.findbugs.gui2.L10N.getLocalString("txt.source_listing", "<source listing>"));

		sourceTitlePanel.add(viewSource, BorderLayout.EAST);
		sourceTitlePanel.add(sourceTitle, BorderLayout.CENTER);

		sourcePanel.add(sourceTitlePanel, BorderLayout.NORTH);
		sourcePanel.add(frame.createSourceCodePanel(), BorderLayout.CENTER);
		sourcePanel.add(frame.createSourceSearchPanel(), BorderLayout.SOUTH);
		topSPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				topLeftSPane, sourcePanel);
		topSPane.setOneTouchExpandable(true);
		topSPane.setDividerLocation(GUISaveState.getInstance().getSplitTop());

		summarySPane = frame.summaryTab();
		mainSPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				topSPane, summarySPane);
		mainSPane.setOneTouchExpandable(true);
		mainSPane.setDividerLocation(GUISaveState.getInstance().getSplitMain());

		frame.setLayout(new BorderLayout());
		frame.add(mainSPane, BorderLayout.CENTER);
		frame.add(frame.statusBar(), BorderLayout.SOUTH);

	}

	public void makeSourceVisible() {
		// not implemented yet
	}

	public void saveState() {
		GUISaveState.getInstance().setSplitTreeComments(topLeftSPane.getDividerLocation());
		GUISaveState.getInstance().setSplitTop(topSPane.getDividerLocation());
		GUISaveState.getInstance().setSplitSummary(summarySPane.getDividerLocation());
		GUISaveState.getInstance().setSplitMain(mainSPane.getDividerLocation());
	}

	public void setSourceTitle(String title) {
		sourceTitle.setText(title);

	}

	public JComponent getSourceViewComponent() {
		return viewSource;
	}

}