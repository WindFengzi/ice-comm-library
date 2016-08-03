package com.winso.comm_library.upload;

import java.io.File;

import org.apache.http.entity.mime.content.FileBody;

public class UploadFile extends FileBody {

	private String name;

	public UploadFile(final String name, File file) {

		super(file);
		this.name = name;
	}

	public UploadFile(final String name, final File file, final String mimeType) {

		super(file, mimeType);
		this.name = name;
	}

	public UploadFile(final String name, final File file,
			final String mimeType, final String charset) {

		super(file, mimeType, charset);
		this.name = name;
	}

	public UploadFile(final String name, final File file,
			final String filename, final String mimeType, final String charset) {

		super(file, filename, mimeType, charset);
		this.name = name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

}
