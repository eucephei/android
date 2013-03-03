package com.example.expandablelist;

public class Child {
	
	private String text;
	private final String button1Text;
	private final String button2Text;
		 
	public Child(String text, String button1Text, String button2Text){
		this.text = text;
		this.button1Text = button1Text;
		this.button2Text = button2Text;
	}
		 
	public void setText(String text) {
		this.text = text;
	}
		 
	public String getText() {
		return text;
	}
		 
	public String getButton1Text() {
		return button1Text;
	}
		 
	public String getButton2Text() {
		return button2Text;
	}
}
