package com.helix;

import android.net.Uri;

public class Song {
	private Uri uri;
	private String name;
	
	public Song(Uri u, String n) {
		uri = u;
		name = n;
	}
	
	public Uri uri() {
		return uri;
	}
	
	public String name() {
		return name;
	}
	
	public String toString() {
		return name();
	}
}
