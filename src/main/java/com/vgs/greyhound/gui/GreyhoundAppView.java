package com.vgs.greyhound.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application.ExitListener;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

import com.vgs.greyhound.model.domain.Content;
import com.vgs.greyhound.model.domain.Page;
import com.vgs.greyhound.model.exception.ModelException;
import com.vgs.greyhound.model.facade.GreyhoundFacade;
import com.vgs.greyhound.model.util.ApplicationContextUtil;

/**
 * The application's main frame.
 */
@SuppressWarnings("serial")
public class GreyhoundAppView extends FrameView {

	private final static Log logger = LogFactory.getLog(GreyhoundAppView.class);

	private JScrollPane scrollPaneMainTree;
	private JScrollPane scrollPaneMainEditor;
	private JSplitPane jSplitPaneMain;
	private JEditorPane mainEditor;
	private UndoManager undoManager;
	private JPanel mainPanel;
	private JTree mainTree;
	private JMenuBar menuBar;
	private JMenu mnuAdmin;
	private JMenuItem mnuItemBackup;
	private JDialog aboutBox;
	private GreyhoundFacade facade;
	private MainTreePopup mainTreePopup;
	private JMenuItem mainTreePopupMenuItem;
	private RenameAction renameAction;
	private NewPageAction newNodeAction;
	private NewCategoryAction newCategoryAction;
	private DeletePageAction deletePageAction;
	private DeleteCategoryAction deleteCategoryAction;
	private SavePageAction savePageAction;
	private MoveAction moveAction;
	private UndoAction undoAction;
	private RedoAction redoAction;

	public GreyhoundAppView(SingleFrameApplication app) {
		super(app);
		initComponents();
		initMyComponents();
	}

	@Action
	public void showAboutBox() {
		if (aboutBox == null) {
			JFrame mainFrame = GreyhoundApp.getApplication().getMainFrame();
			aboutBox = new GreyhoundAppAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		GreyhoundApp.getApplication().show(aboutBox);
	}

	private void initComponents() {

		mainPanel = new javax.swing.JPanel();
		jSplitPaneMain = new javax.swing.JSplitPane();
		scrollPaneMainTree = new javax.swing.JScrollPane();
		mainTree = new javax.swing.JTree();
		scrollPaneMainEditor = new javax.swing.JScrollPane();
		mainEditor = new javax.swing.JEditorPane();
		menuBar = new javax.swing.JMenuBar();
		javax.swing.JMenu fileMenu = new javax.swing.JMenu();
		javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
		mnuAdmin = new javax.swing.JMenu();
		mnuItemBackup = new javax.swing.JMenuItem();
		javax.swing.JMenu helpMenu = new javax.swing.JMenu();
		javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

		mainPanel.setName("mainPanel");
		mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel,
				javax.swing.BoxLayout.LINE_AXIS));

		jSplitPaneMain.setDividerSize(5);
		jSplitPaneMain.setName("jSplitPane1");

		scrollPaneMainTree.setName("jScrollPane1");

		mainTree.setName("mainTree");
		scrollPaneMainTree.setViewportView(mainTree);

		jSplitPaneMain.setLeftComponent(scrollPaneMainTree);

		scrollPaneMainEditor.setName("jScrollPane2");

		mainEditor.setName("mainEditor");
		scrollPaneMainEditor.setViewportView(mainEditor);

		jSplitPaneMain.setRightComponent(scrollPaneMainEditor);

		mainPanel.add(jSplitPaneMain);

		menuBar.setName("menuBar");

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.vgs.greyhound.gui.GreyhoundApp.class)
				.getContext().getResourceMap(GreyhoundAppView.class);
		fileMenu.setText(resourceMap.getString("fileMenu.text"));
		fileMenu.setName("fileMenu");

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
				.getInstance(com.vgs.greyhound.gui.GreyhoundApp.class)
				.getContext().getActionMap(GreyhoundAppView.class, this);
		exitMenuItem.setAction(actionMap.get("quit"));
		exitMenuItem.setName("exitMenuItem");
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		mnuAdmin.setText(resourceMap.getString("mnuAdmin.text"));
		mnuAdmin.setName("mnuAdmin");

		mnuItemBackup.setAction(actionMap.get("backupDatabase"));
		mnuItemBackup.setText("Backup");
		mnuItemBackup.setName("mnuItemBackup");
		mnuAdmin.add(mnuItemBackup);

		menuBar.add(mnuAdmin);

		helpMenu.setText(resourceMap.getString("helpMenu.text"));
		helpMenu.setName("helpMenu");

		aboutMenuItem.setAction(actionMap.get("showAboutBox"));
		aboutMenuItem.setName("aboutMenuItem");
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		setComponent(mainPanel);
		setMenuBar(menuBar);
	}

	private void initMyComponents() {
		facade = ApplicationContextUtil.getGreyhoundFacade();
		try {
			initMainFrame();
			initMainTree();
			initMainEditor();
		} catch (Exception e) {
			logger.error("Error initializing the application", e);
			JOptionPane.showMessageDialog(this.getComponent(),
					"There's been an error");
		}
	}

	private void initMainFrame() {

		getApplication().addExitListener(new ExitListener() {

			@Override
			public void willExit(EventObject event) {
				ApplicationContextUtil.closeApplicationContext();
			}

			@Override
			public boolean canExit(EventObject event) {
				return canProceed(getSelectedNode());
			}
		});

		jSplitPaneMain.setDividerLocation(.3);

	}

	private void initMainEditor() {
		initEditorActions();
		initMainEditorKeyBindings();
		initDocument();
		undoManager = new UndoManager();
		mainEditor.setEnabled(getSelectedNode() != null);
		
//		Font font = UIManager.getFont("Label.font");
//		Font font = new Font("monospaced", Font.PLAIN, 12);
//		mainEditor.setFont(font);
	}

	private void initDocument() {
		Document doc = mainEditor.getDocument();
		if (doc instanceof PlainDocument) {
			doc.putProperty(PlainDocument.tabSizeAttribute, 4);
		}
		doc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
				GreyhoundMutableTreeNode selectedNode = getSelectedNode();
				Page selectedPage = (Page) selectedNode.getUserObject();
				try {
					selectedPage.getContent().setText(
							mainEditor.getDocument().getText(0,
									mainEditor.getDocument().getLength()));
				} catch (BadLocationException ble) {
					logger.error(ble);
					JOptionPane.showMessageDialog(getFrame(),
							"Error changing text");
				}
				selectedPage.setDirty(true);
			}
		});
	}

	private void initMainEditorKeyBindings() {
		InputMap mainEditorInputMap = mainEditor.getInputMap();

		// UNDO ACTION
		KeyStroke undoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				Event.CTRL_MASK);
		mainEditorInputMap.put(undoKey, undoAction);

		// REDO ACTION
		KeyStroke redoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				Event.CTRL_MASK);
		mainEditorInputMap.put(redoKey, redoAction);

		// SAVE ACTION
		KeyStroke saveKey = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Event.CTRL_MASK);
		mainEditorInputMap.put(saveKey, savePageAction);
	}

	private void initEditorActions() {
		undoAction = new UndoAction();
		redoAction = new RedoAction();
	}

	private void initTreeActions() {
		renameAction = new RenameAction();
		newCategoryAction = new NewCategoryAction();
		newNodeAction = new NewPageAction();
		deletePageAction = new DeletePageAction();
		deleteCategoryAction = new DeleteCategoryAction();
		savePageAction = new SavePageAction();
		moveAction = new MoveAction();
	}

	private void initMainTree() throws ModelException {
		initTreeActions();
		populateTree(mainTree, facade.retrieveAllPages());

		mainTree.setSelectionModel(new VetoableTreeSelectionModel());
		mainTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		mainTree.setCellRenderer(new GreyhoundTreeCellRenderer());

		mainTree.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent tse) {
				GreyhoundMutableTreeNode selectedNode = getSelectedNode();
				if (selectedNode == null) { // nothing is selected
					mainEditor.setEnabled(false);
					return;
				} else {
					Page selectedPage = (Page) selectedNode.getUserObject();
					mainEditor.setEnabled(!selectedPage.isCategory());
					mainEditor.getEditorKit().createDefaultDocument();
				}

				Page node = (Page) selectedNode.getUserObject();
				StringReader reader = new StringReader(node.getContent()
						.getText());
				try {
					// this line replaces the previous document
					mainEditor.read(reader, null);
					// so now we have to add the undoable listener again
					initDocument();
				} catch (IOException ioe) {
					logger.fatal("Error setting mainEditor's content", ioe);
					throw new RuntimeException(ioe);
				}
			}
		});

		mainTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				super.mousePressed(me);
				if (SwingUtilities.isRightMouseButton(me)) {
					Point p = me.getPoint();
					TreePath path = mainTree.getPathForLocation(p.x, p.y);
					mainTree.setSelectionPath(path);
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				super.mouseReleased(me);
				if (SwingUtilities.isRightMouseButton(me)) {
					TreePath path = mainTree.getSelectionPath();
					if (path != null) {
						GreyhoundMutableTreeNode node = (GreyhoundMutableTreeNode) path
								.getLastPathComponent();
						maybeShowPopup(me, node);
					}
				}
			}

			private void maybeShowPopup(MouseEvent me,
					GreyhoundMutableTreeNode node) {
				if (SwingUtilities.isRightMouseButton(me)) {
					mainTreePopup = new MainTreePopup(node);
					mainTreePopup.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		});

		mainTree.setSelectionRow(0);
	}

	private class VetoableTreeSelectionModel extends DefaultTreeSelectionModel {

		@Override
		public void setSelectionPath(TreePath path) {
			TreePath currentPath = mainTree.getSelectionPath();
			if (currentPath == path) { // user clicked on same (current) node,
										// so no actual change
				return;
			}
			if (currentPath != null) {
				GreyhoundMutableTreeNode currentTreeNode = (GreyhoundMutableTreeNode) currentPath
						.getLastPathComponent();
				if (canProceed(currentTreeNode)) {
					super.setSelectionPath(path);
				}
			} else {
				super.setSelectionPath(path);
			}

		}

	}

	private void populateTree(JTree tree, List<Page> pages)
			throws ModelException {
		Page rootPage = new Page(null, "Pages", true, new Content(), null);
		GreyhoundMutableTreeNode rootNode = new GreyhoundMutableTreeNode(
				rootPage);
		buildNodes(rootNode, pages);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		tree.setModel(model);
	}

	private void buildNodes(GreyhoundMutableTreeNode rootNode, List<Page> pages) {
		Map<Integer, GreyhoundMutableTreeNode> pagesMap = new HashMap<Integer, GreyhoundMutableTreeNode>();
		for (Iterator<Page> iterator = pages.iterator(); iterator.hasNext();) {
			Page page = (Page) iterator.next();
			GreyhoundMutableTreeNode node = new GreyhoundMutableTreeNode(page);
			pagesMap.put(page.getPageId(), node);
		}

		for (Iterator<Page> iterator = pages.iterator(); iterator.hasNext();) {
			Page page = (Page) iterator.next();
			GreyhoundMutableTreeNode parent = null;
			if (page.getParent() == null) {
				parent = rootNode;
			} else {
				parent = pagesMap.get(page.getParent().getPageId());
			}
			parent.add(pagesMap.get(page.getPageId()));
		}
	}

	final class MainTreePopup extends JPopupMenu {

		GreyhoundMutableTreeNode treeNode;
		Page node;

		public MainTreePopup(GreyhoundMutableTreeNode theTreeNode) {
			if (theTreeNode == null) {
				throw new IllegalArgumentException("Node cannot be null");
			}
			this.treeNode = theTreeNode;
			this.node = (Page) treeNode.getUserObject();

			// treat root uniquely and return
			if (treeNode.isRoot()) {
				mainTreePopupMenuItem = new JMenuItem("New category");
				mainTreePopupMenuItem.addActionListener(newCategoryAction);
				this.add(mainTreePopupMenuItem);
				mainTreePopupMenuItem = new JMenuItem("New page");
				mainTreePopupMenuItem.addActionListener(newNodeAction);
				this.add(mainTreePopupMenuItem);
				return;
			}

			if (this.node.isCategory()) {
				mainTreePopupMenuItem = new JMenuItem("New category");
				mainTreePopupMenuItem.addActionListener(newCategoryAction);
				this.add(mainTreePopupMenuItem);
				mainTreePopupMenuItem = new JMenuItem("New page");
				mainTreePopupMenuItem.addActionListener(newNodeAction);
				this.add(mainTreePopupMenuItem);
				mainTreePopupMenuItem = new JMenuItem("Delete category");
				mainTreePopupMenuItem.addActionListener(deleteCategoryAction);
				this.add(mainTreePopupMenuItem);
			} else {
				mainTreePopupMenuItem = new JMenuItem("Delete page");
				mainTreePopupMenuItem.addActionListener(deletePageAction);
				this.add(mainTreePopupMenuItem);
				if (this.node.isDirty()) {
					mainTreePopupMenuItem = new JMenuItem("Save");
					mainTreePopupMenuItem.addActionListener(savePageAction);
					this.add(mainTreePopupMenuItem);
				}
			}
			// these options apply for both pages and categories
			mainTreePopupMenuItem = new JMenuItem("Move");
			mainTreePopupMenuItem.addActionListener(moveAction);
			this.add(mainTreePopupMenuItem);
			mainTreePopupMenuItem = new JMenuItem("Rename");
			mainTreePopupMenuItem.addActionListener(renameAction);
			this.add(mainTreePopupMenuItem);
		}
	}

	final class DeletePageAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GreyhoundMutableTreeNode node = getSelectedNode();
			GreyhoundMutableTreeNode parentNode = (GreyhoundMutableTreeNode) node
					.getParent();
			Page p = (Page) node.getUserObject();
			int choice = JOptionPane.showConfirmDialog(getFrame(),
					"Confirm page delete?", "Delete page",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (choice == JOptionPane.YES_OPTION) {
				// delete page
				try {
					facade.deletePage(p);
					((DefaultTreeModel) mainTree.getModel())
							.removeNodeFromParent(node);
				} catch (ModelException me) {
					logger.error("Error deleting page", me);
					JOptionPane.showMessageDialog(getFrame(),
							"Error deleting page");
				}
				// notify tree model
				((DefaultTreeModel) mainTree.getModel())
						.nodeStructureChanged(node.getParent() != null ? node
								.getParent() : (TreeNode) mainTree.getModel()
								.getRoot());
				mainTree.expandPath(new TreePath(parentNode.getPath()));
			} // else do nothing
		}
	}

	final class DeleteCategoryAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GreyhoundMutableTreeNode node = getSelectedNode();
			GreyhoundMutableTreeNode parentNode = (GreyhoundMutableTreeNode) node
					.getParent();
			Page p = (Page) node.getUserObject();
			int choice = JOptionPane.showConfirmDialog(getFrame(),
					"Confirm category delete?", "Delete category",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (choice == JOptionPane.YES_OPTION) {
				// delete category
				try {
					facade.deletePage(p);
					((DefaultTreeModel) mainTree.getModel())
							.removeNodeFromParent(node);
				} catch (ModelException me) {
					logger.error("Error deleting category", me);
					JOptionPane.showMessageDialog(getFrame(),
							"Error deleting category");
				}
				// notify tree model
				((DefaultTreeModel) mainTree.getModel())
						.nodeStructureChanged(node.getParent() != null ? node
								.getParent() : (TreeNode) mainTree.getModel()
								.getRoot());
				// mainTree.scrollPathToVisible(new
				// TreePath(parentNode.getPath()));
				mainTree.expandPath(new TreePath(parentNode.getPath()));
			} // else do nothing
		}

	}

	final class MoveAction extends AbstractAction {

		private GreyhoundMutableTreeNode targetNode;
		private boolean okToMove;

		public GreyhoundMutableTreeNode getTargetNode() {
			return targetNode;
		}

		public void setTargetNode(GreyhoundMutableTreeNode theTargetNode) {
			this.targetNode = theTargetNode;
		}

		public boolean isOkToMove() {
			return okToMove;
		}

		public void setOkToMove(boolean okToMove) {
			this.okToMove = okToMove;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			GreyhoundMutableTreeNode node = getSelectedNode();
			Page p = (Page) node.getUserObject();
			logger.debug("You want to move this node: " + p.getTitle());

			JDialog dialog = new JDialog(getFrame(), true);
			dialog.setSize(400, 400);
			Container tempContainer = createTempContainer(dialog);
			dialog.getContentPane().add(tempContainer);
			dialog.setVisible(true);

			if (okToMove) {
				Page targetParent = null;
				if (!targetNode.isRoot()) {
					targetParent = (Page) targetNode.getUserObject();
				}
				p.setParent(targetParent);

				// move node
				try {
					facade.createOrUpdatePage(p);
					((DefaultTreeModel) mainTree.getModel())
							.removeNodeFromParent(node);
					populateTree(mainTree, facade.retrieveAllPages());
				} catch (ModelException me) {
					logger.error("Error deleting category", me);
					JOptionPane.showMessageDialog(getFrame(),
							"Error deleting category");
				}
			}
		}

		private Container createTempContainer(final JDialog dialog) {
			Container mainPane = new JPanel();
			mainPane.setLayout(new BorderLayout());
			Container buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout());
			final JTree tempTree = new JTree();

			try {
				populateTree(tempTree, facade.retrieveAllCategories());
			} catch (ModelException e) {
				logger.error("Error initializing the application", e);
				JOptionPane.showMessageDialog(getFrame(),
						"There's been an error");
			}
			tempTree.setSelectionModel(new VetoableTreeSelectionModel());
			tempTree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			tempTree.setCellRenderer(new GreyhoundTreeCellRenderer());
			JButton okButton = new JButton("Ok");
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					moveAction.setOkToMove(false);
					dialog.setVisible(false);
					dialog.dispose();
				}
			});
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GreyhoundMutableTreeNode selectedNode = (GreyhoundMutableTreeNode) tempTree
							.getLastSelectedPathComponent();
					moveAction.setTargetNode(selectedNode);
					moveAction.setOkToMove(true);
					dialog.setVisible(false);
					dialog.dispose();
				}
			});

			buttonPane.add(okButton);
			buttonPane.add(cancelButton);
			
			final JScrollPane tempScrollPane = new JScrollPane();
			tempScrollPane.setViewportView(tempTree);
			mainPane.add(tempScrollPane, BorderLayout.CENTER);
			mainPane.add(buttonPane, BorderLayout.SOUTH);
			return mainPane;
		}
	}

	final class RenameAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ae) {
			GreyhoundMutableTreeNode node = getSelectedNode();
			Page page = (Page) node.getUserObject();
			String newTitle = (String) JOptionPane.showInputDialog(getFrame(),
					"New title: ", "Rename node", JOptionPane.PLAIN_MESSAGE,
					null, null, page.getTitle());

			if (newTitle == null) { // this means user pressed the Cancel button
				return;
			} else if (newTitle.length() == 0) {
				JOptionPane.showMessageDialog(getFrame(), "Invalid node title");
				return;
			}

			try {
				page.setTitle(newTitle);
				facade.createOrUpdatePage(page);
				((DefaultTreeModel) mainTree.getModel()).nodeChanged(node);
			} catch (ModelException me) {
				logger.error("Error renaming category", me);
				JOptionPane.showMessageDialog(getFrame(),
						"Error renaming category");
			}

		}

	}

	final class NewCategoryAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ae) {
			GreyhoundMutableTreeNode node = getSelectedNode();
			Page parentCategory = null;
			if (!node.isRoot()) {
				parentCategory = (Page) node.getUserObject();
			}

			String newCategoryTitle = (String) JOptionPane.showInputDialog(
					getFrame(), "New category: ", "New category",
					JOptionPane.PLAIN_MESSAGE, null, null, "");

			if (newCategoryTitle == null) { // this means user pressed the
				// Cancel button
				return;
			} else if (newCategoryTitle.length() == 0) {
				JOptionPane.showMessageDialog(getFrame(),
						"Invalid category title");
				return;
			}

			Page newCategory = new Page(null, newCategoryTitle, true,
					new Content(), parentCategory);

			GreyhoundMutableTreeNode newCategoryNode = new GreyhoundMutableTreeNode(
					newCategory);
			((DefaultTreeModel) mainTree.getModel()).insertNodeInto(
					newCategoryNode, node, 0);
			try {
				facade.createOrUpdatePage(newCategory);
				// notify tree model
				((DefaultTreeModel) mainTree.getModel())
						.nodeStructureChanged(node.getParent() != null ? node
								.getParent() : (TreeNode) mainTree.getModel()
								.getRoot());
				mainTree.scrollPathToVisible(new TreePath(newCategoryNode
						.getPath()));
			} catch (ModelException me) {
				logger.error("Error creating category", me);
				JOptionPane.showMessageDialog(getFrame(),
						"Error creating category");
			}
		}

	}

	final class NewPageAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ae) {
			GreyhoundMutableTreeNode node = getSelectedNode();
			Page parentCategory = null;
			if (!node.isRoot()) {
				parentCategory = (Page) node.getUserObject();
			}

			String newNodeTitle = (String) JOptionPane.showInputDialog(
					getFrame(), "New node: ", "New node",
					JOptionPane.PLAIN_MESSAGE, null, null, "");

			if (newNodeTitle == null) { // this means user pressed the Cancel
				// button
				return;
			} else if (newNodeTitle.length() == 0) {
				JOptionPane.showMessageDialog(getFrame(), "Invalid node title");
				return;
			}

			Page newNode = new Page(null, newNodeTitle, false, new Content(),
					parentCategory);
			GreyhoundMutableTreeNode newTreeNode = new GreyhoundMutableTreeNode(
					newNode);
			((DefaultTreeModel) mainTree.getModel()).insertNodeInto(
					newTreeNode, node, 0);
			// notify tree model
			((DefaultTreeModel) mainTree.getModel()).nodeStructureChanged(node
					.getParent() != null ? node.getParent()
					: (TreeNode) mainTree.getModel().getRoot());
			mainTree.scrollPathToVisible(new TreePath(newTreeNode.getPath()));

			try {
				facade.createOrUpdatePage(newNode);
			} catch (ModelException me) {
				logger.error("Error creating node", me);
				JOptionPane
						.showMessageDialog(getFrame(), "Error creating node");
			}
		}
	}

	final class UndoAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.undo();
			} catch (CannotUndoException cue) {
				// no problem, do nothing
			}
		}
	}

	final class RedoAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.redo();
			} catch (CannotRedoException cre) {
				// no problem, do nothing
			}
		}
	}

	final class SavePageAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.debug("Saving page from action");
			GreyhoundMutableTreeNode node = getSelectedNode();
			Page p = (Page) node.getUserObject();
			try {
				facade.createOrUpdatePage(p);
			} catch (ModelException me) {
				logger.error("Error saving page", me);
				JOptionPane.showMessageDialog(getFrame(), "Error saving page");
			}
		}
	}

	@Action
	public void backupDatabase() {
		String backupLocation = JOptionPane.showInputDialog(getFrame(),
				"Enter backup file:", "Backup database",
				JOptionPane.QUESTION_MESSAGE);
		if (StringUtils.isBlank(backupLocation)) {
			JOptionPane.showMessageDialog(getFrame(), "Invalid file location");
			return;
		}
		try {
			facade.backupDatabase(backupLocation);
		} catch (ModelException me) {
			logger.error("Error backing up database", me);
			JOptionPane.showMessageDialog(getFrame(),
					"Error backing up database");
		}
	}

	private GreyhoundMutableTreeNode getSelectedNode() {
		return (GreyhoundMutableTreeNode) mainTree
				.getLastSelectedPathComponent();
	}

	private boolean canProceed(GreyhoundMutableTreeNode currentTreeNode) {
		if(currentTreeNode == null) {
			return true;	
		}
		
		Page currentPage = (Page) currentTreeNode.getUserObject();
		if (currentPage.isDirty()) {
			int n = JOptionPane.showConfirmDialog(getFrame(), "Save changes?",
					"Content changed", JOptionPane.YES_NO_CANCEL_OPTION);
			if (n == JOptionPane.CANCEL_OPTION) {
				return false;
			} else if (n == JOptionPane.YES_OPTION) {
				savePageAction.actionPerformed(null);
			} else if (n == JOptionPane.NO_OPTION) {
				// re-reads this node data from the database
				reloadCurrentNode(currentTreeNode);
				currentPage.setDirty(false);
			}
		}
		return true;
	}

	private void reloadCurrentNode(GreyhoundMutableTreeNode currentTreeNode) {
		Page currentPage = (Page) currentTreeNode.getUserObject();
		Integer id = currentPage.getPageId();
		try {
			currentPage = facade.retrievePageById(id);
			currentTreeNode.setUserObject(currentPage);
		} catch (ModelException me) {
			logger.error("Error reloading node", me);
			JOptionPane.showMessageDialog(getFrame(), "Error loading node");
		}
	}

}
