package com.fred.utils;

import java.io.ByteArrayOutputStream;

public class DirectByteArrayOutputStream extends ByteArrayOutputStream {
	public DirectByteArrayOutputStream() {
		super();		
	}
	
	// return the reference of the original byte array instead of allocating a new one.
	public byte[] getArray() {
		return buf;
	}
} 
