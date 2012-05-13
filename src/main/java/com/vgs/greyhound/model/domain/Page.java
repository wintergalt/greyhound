package com.vgs.greyhound.model.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

public class Page {

	private Integer pageId = null;
	private String title;
	private Content content = new Content();;
	private boolean dirty;
	private boolean category;
	private Page parent;
	private Set<Page> children = new HashSet<Page>();

	private final static Log logger = LogFactory.getLog(Page.class);

	public Page() {

	}

	public Page(Integer thePageId, String theTitle, boolean theCategory,
			Content theContent, Page theParent) {
		this.pageId = thePageId;
		this.title = theTitle;
		this.category = theCategory;
		this.content = theContent;
		this.setParent(theParent);
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = (content == null ? new Content() : content);
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean isCategory() {
		return category;
	}

	public void setCategory(boolean category) {
		this.category = category;
	}

	public Page getParent() {
		return parent;
	}

	public void setParent(Page theParent) {
		this.parent = theParent;
		try {
			if (theParent != null && !theParent.getChildren().contains(this)) {
				theParent.addChild(this);
			}
		} catch (LazyInitializationException lie) {
			// do nothing
			logger.warn("Exception setting parent " + theParent + " for node " + this.getTitle());
		}
	}

	public void addChild(Page aChild) {
		if (!this.getChildren().contains(aChild)) {
			this.getChildren().add(aChild);
			aChild.setParent(this);
		}
	}

	public Set<Page> getChildren() {
		return children;
	}

	public void setChildren(Set<Page> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		// return ToStringBuilder.reflectionToString(this,
		// ToStringStyle.DEFAULT_STYLE);
		return this.title;
	}

}
