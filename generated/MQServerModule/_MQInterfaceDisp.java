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

public abstract class _MQInterfaceDisp extends Ice.ObjectImpl implements MQInterface
{
    protected void
    ice_copyStateFrom(Ice.Object __obj)
        throws java.lang.CloneNotSupportedException
    {
        throw new java.lang.CloneNotSupportedException();
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::MQServerModule::MQInterface"
    };

    public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public boolean ice_isA(String s, Ice.Current __current)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public String[] ice_ids()
    {
        return __ids;
    }

    public String[] ice_ids(Ice.Current __current)
    {
        return __ids;
    }

    public String ice_id()
    {
        return __ids[1];
    }

    public String ice_id(Ice.Current __current)
    {
        return __ids[1];
    }

    public static String ice_staticId()
    {
        return __ids[1];
    }

    public final void UploadFile_async(AMD_MQInterface_UploadFile __cb, String sFile, int pos, int num, byte[] filecontent)
    {
        UploadFile_async(__cb, sFile, pos, num, filecontent, null);
    }

    public final int command(String cmd, String param, Ice.StringHolder outmsg)
    {
        return command(cmd, param, outmsg, null);
    }

    public final boolean desc(String sql, Ice.StringHolder set, Ice.StringHolder insertsql, Ice.StringHolder error)
    {
        return desc(sql, set, insertsql, error, null);
    }

    public final void execCmd_async(AMD_MQInterface_execCmd __cb, String cmd, String sqlcode, String param)
    {
        execCmd_async(__cb, cmd, sqlcode, param, null);
    }

    public final void execProc_async(AMD_MQInterface_execProc __cb, String sql, String param)
    {
        execProc_async(__cb, sql, param, null);
    }

    public final void execSQL_async(AMD_MQInterface_execSQL __cb, String sql, String param)
    {
        execSQL_async(__cb, sql, param, null);
    }

    public final void execSQLBatch_async(AMD_MQInterface_execSQLBatch __cb, String sqlblock)
    {
        execSQLBatch_async(__cb, sqlblock, null);
    }

    public final String getConfigure(String segment, String key)
    {
        return getConfigure(segment, key, null);
    }

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
    public final void getFileCompressed_async(AMD_MQInterface_getFileCompressed __cb, String path, int pos, int num)
    {
        getFileCompressed_async(__cb, path, pos, num, null);
    }

    public final boolean getFileInfo(String sFilePath, Ice.StringHolder sHelpInfo)
    {
        return getFileInfo(sFilePath, sHelpInfo, null);
    }

    public final boolean getFileInfoSeq(String sPath, Ice.StringHolder sHelpInfo)
    {
        return getFileInfoSeq(sPath, sHelpInfo, null);
    }

    public final boolean getRespone(String sID, Ice.StringHolder outinfo, Ice.StringHolder error)
    {
        return getRespone(sID, outinfo, error, null);
    }

    public final String getTime()
    {
        return getTime(null);
    }

    public final int plugin(String pname, String func, String param, Ice.StringHolder outmsg)
    {
        return plugin(pname, func, param, outmsg, null);
    }

    public final void select_async(AMD_MQInterface_select __cb, String sql, String param)
    {
        select_async(__cb, sql, param, null);
    }

    public final void selectCmd_async(AMD_MQInterface_selectCmd __cb, String cmd, String sqlcode, String param)
    {
        selectCmd_async(__cb, cmd, sqlcode, param, null);
    }

    public final void selectCompress_async(AMD_MQInterface_selectCompress __cb, String sql, String param)
    {
        selectCompress_async(__cb, sql, param, null);
    }

    public final boolean selectFinish(String sID)
    {
        return selectFinish(sID, null);
    }

    public final void selectNext_async(AMD_MQInterface_selectNext __cb, String sID)
    {
        selectNext_async(__cb, sID, null);
    }

    public final void selectPage_async(AMD_MQInterface_selectPage __cb, String sql, String param, int iStart, int iCount)
    {
        selectPage_async(__cb, sql, param, iStart, iCount, null);
    }

    public final void selectPrepare_async(AMD_MQInterface_selectPrepare __cb, String sql)
    {
        selectPrepare_async(__cb, sql, null);
    }

    public final void selectPrepareByParam_async(AMD_MQInterface_selectPrepareByParam __cb, String sql, String param)
    {
        selectPrepareByParam_async(__cb, sql, param, null);
    }

    public final boolean send(String msg)
    {
        return send(msg, null);
    }

    public final void sendOneway(String msg)
    {
        sendOneway(msg, null);
    }

    public final String version()
    {
        return version(null);
    }

    public final boolean writeBusiLog(String personid, String ip, String busiType, String comment)
    {
        return writeBusiLog(personid, ip, busiType, comment, null);
    }

    public static Ice.DispatchStatus ___version(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        String __ret = __obj.version(__current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getTime(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        String __ret = __obj.getTime(__current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getConfigure(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String segment;
        String key;
        segment = __is.readString();
        key = __is.readString();
        __inS.endReadParams();
        String __ret = __obj.getConfigure(segment, key, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___sendOneway(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String msg;
        msg = __is.readString();
        __inS.endReadParams();
        __obj.sendOneway(msg, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___send(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String msg;
        msg = __is.readString();
        __inS.endReadParams();
        boolean __ret = __obj.send(msg, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___command(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String cmd;
        String param;
        cmd = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        Ice.StringHolder outmsg = new Ice.StringHolder();
        int __ret = __obj.command(cmd, param, outmsg, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(outmsg.value);
        __os.writeInt(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___plugin(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String pname;
        String func;
        String param;
        pname = __is.readString();
        func = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        Ice.StringHolder outmsg = new Ice.StringHolder();
        int __ret = __obj.plugin(pname, func, param, outmsg, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(outmsg.value);
        __os.writeInt(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___selectCmd(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String cmd;
        String sqlcode;
        String param;
        cmd = __is.readString();
        sqlcode = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_selectCmd __cb = new _AMD_MQInterface_selectCmd(__inS);
        try
        {
            __obj.selectCmd_async(__cb, cmd, sqlcode, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___execCmd(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String cmd;
        String sqlcode;
        String param;
        cmd = __is.readString();
        sqlcode = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_execCmd __cb = new _AMD_MQInterface_execCmd(__inS);
        try
        {
            __obj.execCmd_async(__cb, cmd, sqlcode, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___select(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        String param;
        sql = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_select __cb = new _AMD_MQInterface_select(__inS);
        try
        {
            __obj.select_async(__cb, sql, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___selectCompress(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        String param;
        sql = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_selectCompress __cb = new _AMD_MQInterface_selectCompress(__inS);
        try
        {
            __obj.selectCompress_async(__cb, sql, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___selectPage(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        String param;
        int iStart;
        int iCount;
        sql = __is.readString();
        param = __is.readString();
        iStart = __is.readInt();
        iCount = __is.readInt();
        __inS.endReadParams();
        AMD_MQInterface_selectPage __cb = new _AMD_MQInterface_selectPage(__inS);
        try
        {
            __obj.selectPage_async(__cb, sql, param, iStart, iCount, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___execSQL(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        String param;
        sql = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_execSQL __cb = new _AMD_MQInterface_execSQL(__inS);
        try
        {
            __obj.execSQL_async(__cb, sql, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___execProc(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        String param;
        sql = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_execProc __cb = new _AMD_MQInterface_execProc(__inS);
        try
        {
            __obj.execProc_async(__cb, sql, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___execSQLBatch(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sqlblock;
        sqlblock = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_execSQLBatch __cb = new _AMD_MQInterface_execSQLBatch(__inS);
        try
        {
            __obj.execSQLBatch_async(__cb, sqlblock, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___writeBusiLog(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String personid;
        String ip;
        String busiType;
        String comment;
        personid = __is.readString();
        ip = __is.readString();
        busiType = __is.readString();
        comment = __is.readString();
        __inS.endReadParams();
        boolean __ret = __obj.writeBusiLog(personid, ip, busiType, comment, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getRespone(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sID;
        sID = __is.readString();
        __inS.endReadParams();
        Ice.StringHolder outinfo = new Ice.StringHolder();
        Ice.StringHolder error = new Ice.StringHolder();
        boolean __ret = __obj.getRespone(sID, outinfo, error, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(outinfo.value);
        __os.writeString(error.value);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___desc(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        sql = __is.readString();
        __inS.endReadParams();
        Ice.StringHolder set = new Ice.StringHolder();
        Ice.StringHolder insertsql = new Ice.StringHolder();
        Ice.StringHolder error = new Ice.StringHolder();
        boolean __ret = __obj.desc(sql, set, insertsql, error, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(set.value);
        __os.writeString(insertsql.value);
        __os.writeString(error.value);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___selectPrepareByParam(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        String param;
        sql = __is.readString();
        param = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_selectPrepareByParam __cb = new _AMD_MQInterface_selectPrepareByParam(__inS);
        try
        {
            __obj.selectPrepareByParam_async(__cb, sql, param, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___selectPrepare(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sql;
        sql = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_selectPrepare __cb = new _AMD_MQInterface_selectPrepare(__inS);
        try
        {
            __obj.selectPrepare_async(__cb, sql, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___selectNext(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sID;
        sID = __is.readString();
        __inS.endReadParams();
        AMD_MQInterface_selectNext __cb = new _AMD_MQInterface_selectNext(__inS);
        try
        {
            __obj.selectNext_async(__cb, sID, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___selectFinish(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sID;
        sID = __is.readString();
        __inS.endReadParams();
        boolean __ret = __obj.selectFinish(sID, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getFileInfo(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sFilePath;
        sFilePath = __is.readString();
        __inS.endReadParams();
        Ice.StringHolder sHelpInfo = new Ice.StringHolder();
        boolean __ret = __obj.getFileInfo(sFilePath, sHelpInfo, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(sHelpInfo.value);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getFileInfoSeq(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sPath;
        sPath = __is.readString();
        __inS.endReadParams();
        Ice.StringHolder sHelpInfo = new Ice.StringHolder();
        boolean __ret = __obj.getFileInfoSeq(sPath, sHelpInfo, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(sHelpInfo.value);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getFileCompressed(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Idempotent, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String path;
        int pos;
        int num;
        path = __is.readString();
        pos = __is.readInt();
        num = __is.readInt();
        __inS.endReadParams();
        AMD_MQInterface_getFileCompressed __cb = new _AMD_MQInterface_getFileCompressed(__inS);
        try
        {
            __obj.getFileCompressed_async(__cb, path, pos, num, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    public static Ice.DispatchStatus ___UploadFile(MQInterface __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String sFile;
        int pos;
        int num;
        byte[] filecontent;
        sFile = __is.readString();
        pos = __is.readInt();
        num = __is.readInt();
        filecontent = ByteSeqHelper.read(__is);
        __inS.endReadParams();
        AMD_MQInterface_UploadFile __cb = new _AMD_MQInterface_UploadFile(__inS);
        try
        {
            __obj.UploadFile_async(__cb, sFile, pos, num, filecontent, __current);
        }
        catch(java.lang.Exception ex)
        {
            __cb.ice_exception(ex);
        }
        return Ice.DispatchStatus.DispatchAsync;
    }

    private final static String[] __all =
    {
        "UploadFile",
        "command",
        "desc",
        "execCmd",
        "execProc",
        "execSQL",
        "execSQLBatch",
        "getConfigure",
        "getFileCompressed",
        "getFileInfo",
        "getFileInfoSeq",
        "getRespone",
        "getTime",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "plugin",
        "select",
        "selectCmd",
        "selectCompress",
        "selectFinish",
        "selectNext",
        "selectPage",
        "selectPrepare",
        "selectPrepareByParam",
        "send",
        "sendOneway",
        "version",
        "writeBusiLog"
    };

    public Ice.DispatchStatus __dispatch(IceInternal.Incoming in, Ice.Current __current)
    {
        int pos = java.util.Arrays.binarySearch(__all, __current.operation);
        if(pos < 0)
        {
            throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return ___UploadFile(this, in, __current);
            }
            case 1:
            {
                return ___command(this, in, __current);
            }
            case 2:
            {
                return ___desc(this, in, __current);
            }
            case 3:
            {
                return ___execCmd(this, in, __current);
            }
            case 4:
            {
                return ___execProc(this, in, __current);
            }
            case 5:
            {
                return ___execSQL(this, in, __current);
            }
            case 6:
            {
                return ___execSQLBatch(this, in, __current);
            }
            case 7:
            {
                return ___getConfigure(this, in, __current);
            }
            case 8:
            {
                return ___getFileCompressed(this, in, __current);
            }
            case 9:
            {
                return ___getFileInfo(this, in, __current);
            }
            case 10:
            {
                return ___getFileInfoSeq(this, in, __current);
            }
            case 11:
            {
                return ___getRespone(this, in, __current);
            }
            case 12:
            {
                return ___getTime(this, in, __current);
            }
            case 13:
            {
                return ___ice_id(this, in, __current);
            }
            case 14:
            {
                return ___ice_ids(this, in, __current);
            }
            case 15:
            {
                return ___ice_isA(this, in, __current);
            }
            case 16:
            {
                return ___ice_ping(this, in, __current);
            }
            case 17:
            {
                return ___plugin(this, in, __current);
            }
            case 18:
            {
                return ___select(this, in, __current);
            }
            case 19:
            {
                return ___selectCmd(this, in, __current);
            }
            case 20:
            {
                return ___selectCompress(this, in, __current);
            }
            case 21:
            {
                return ___selectFinish(this, in, __current);
            }
            case 22:
            {
                return ___selectNext(this, in, __current);
            }
            case 23:
            {
                return ___selectPage(this, in, __current);
            }
            case 24:
            {
                return ___selectPrepare(this, in, __current);
            }
            case 25:
            {
                return ___selectPrepareByParam(this, in, __current);
            }
            case 26:
            {
                return ___send(this, in, __current);
            }
            case 27:
            {
                return ___sendOneway(this, in, __current);
            }
            case 28:
            {
                return ___version(this, in, __current);
            }
            case 29:
            {
                return ___writeBusiLog(this, in, __current);
            }
        }

        assert(false);
        throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
    }

    protected void __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice(ice_staticId(), -1, true);
        __os.endWriteSlice();
    }

    protected void __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        __is.endReadSlice();
    }

    public static final long serialVersionUID = 0L;
}
