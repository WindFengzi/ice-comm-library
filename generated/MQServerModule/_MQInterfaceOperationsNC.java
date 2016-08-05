// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `mq_interface.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package MQServerModule;

public interface _MQInterfaceOperationsNC
{
    String version();

    String getTime();

    String getConfigure(String segment, String key);

    void sendOneway(String msg);

    boolean send(String msg);

    int command(String cmd, String param, Ice.StringHolder outmsg);

    int plugin(String pname, String func, String param, Ice.StringHolder outmsg);

    void selectCmd_async(AMD_MQInterface_selectCmd __cb, String cmd, String sqlcode, String param);

    void execCmd_async(AMD_MQInterface_execCmd __cb, String cmd, String sqlcode, String param);

    void select_async(AMD_MQInterface_select __cb, String sql, String param);

    void selectCompress_async(AMD_MQInterface_selectCompress __cb, String sql, String param);

    void selectPage_async(AMD_MQInterface_selectPage __cb, String sql, String param, int iStart, int iCount);

    void execSQL_async(AMD_MQInterface_execSQL __cb, String sql, String param);

    void execProc_async(AMD_MQInterface_execProc __cb, String sql, String param);

    void execSQLBatch_async(AMD_MQInterface_execSQLBatch __cb, String sqlblock);

    boolean writeBusiLog(String personid, String ip, String busiType, String comment);

    boolean getRespone(String sID, Ice.StringHolder outinfo, Ice.StringHolder error);

    boolean desc(String sql, Ice.StringHolder set, Ice.StringHolder insertsql, Ice.StringHolder error);

    void selectPrepareByParam_async(AMD_MQInterface_selectPrepareByParam __cb, String sql, String param);

    void selectPrepare_async(AMD_MQInterface_selectPrepare __cb, String sql);

    void selectNext_async(AMD_MQInterface_selectNext __cb, String sID);

    boolean selectFinish(String sID);

    boolean getFileInfo(String sFilePath, Ice.StringHolder sHelpInfo);

    boolean getFileInfoSeq(String sPath, Ice.StringHolder sHelpInfo);

    /**
     * Read the specified file. If the read operation fails, the
     * operation throws {@link FileAccessException}. This operation may only
     * return fewer bytes than requested in case there was an
     * end-of-file condition.
     * 
     * @param __cb The callback object for the operation.
     * @param path The pathname (relative to the data directory) for
     * the file to be read.
     * 
     * @param pos The file offset at which to begin reading.
     * 
     * @param num The number of bytes to be read.
     * 
     **/
    void getFileCompressed_async(AMD_MQInterface_getFileCompressed __cb, String path, int pos, int num);

    void UploadFile_async(AMD_MQInterface_UploadFile __cb, String sFile, int pos, int num, byte[] filecontent);
}
