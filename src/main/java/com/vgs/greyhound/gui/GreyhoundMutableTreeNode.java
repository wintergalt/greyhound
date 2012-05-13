package com.vgs.greyhound.gui;

import java.util.Collections;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

@SuppressWarnings("serial")
public class GreyhoundMutableTreeNode extends DefaultMutableTreeNode implements Comparable<GreyhoundMutableTreeNode> {

	public GreyhoundMutableTreeNode() {
		super();
	}

	public GreyhoundMutableTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public GreyhoundMutableTreeNode(Object userObject) {
		super(userObject);
	}

	@Override
	public void insert(final MutableTreeNode newChild, final int childIndex) {
		super.insert(newChild, childIndex);
		Collections.sort(this.children);
	}

	public int compareTo(final GreyhoundMutableTreeNode o) {
		return this.toString().compareToIgnoreCase(o.toString());
	}

}
