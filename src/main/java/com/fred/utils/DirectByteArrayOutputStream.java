package com.fred.utils;

import java.io.ByteArrayOutputStream;

public class DirectByteArrayOutputStream extends ByteArrayOutputStream {
	public DirectByteArrayOutputStream() {
		super();		
	}
	
	public byte[] getArray() {
		return buf;
	}
} 