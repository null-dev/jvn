package com.nulldev.jvn;

/*
 * Allows the writing of cross-platform code in their separate modules.
 */

public interface JVNNative {
	abstract boolean popup(String title, String message);
}