package com.vgs.greyhound.model.domain;

public class Content {

	private String text = "";

	public Content() {
	}

	public Content(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = "" + text; // avoid null values
	}

	@Override
	public String toString() {
		// return ToStringBuilder.reflectionToString(this,
		// ToStringStyle.DEFAULT_STYLE);
		if (this.text != null) {
			return "text with " + this.text.length() + " chars";
		} else {
			return "empty text";
		}
	}

}
