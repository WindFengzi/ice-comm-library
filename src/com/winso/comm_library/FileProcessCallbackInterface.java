package com.winso.comm_library;

/**
 * 文件处理回调类
 * 
 * @author eric goo
 * @version 1.0
 * @created 2014-9-21
 */

public interface FileProcessCallbackInterface {
	public void processFile(String sPath,float fPercent);

	public void processFileFinished(String sPath,int iResult,String sError);

}
