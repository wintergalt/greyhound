package com.vgs.greyhound.gui;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.vgs.greyhound.model.domain.Page;

@SuppressWarnings("serial")
public class GreyhoundTreeCellRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
		Page page = (Page) treeNode.getUserObject();

		if (page == null) {
			return this;
		}

		if (page.isCategory()) {
			if (expanded) {
				setIcon(openIcon);
			} else {
				setIcon(closedIcon);
			}
		} else {
			setIcon(leafIcon);
		}

		return this;
	}

}
