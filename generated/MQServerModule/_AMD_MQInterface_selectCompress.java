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

final class _AMD_MQInterface_selectCompress extends IceInternal.IncomingAsync implements AMD_MQInterface_selectCompress
{
    public _AMD_MQInterface_selectCompress(IceInternal.Incoming in)
    {
        super(in);
    }

    public void ice_response(int __ret, String set, String error)
    {
        if(__validateResponse(true))
        {
            try
            {
                IceInternal.BasicStream __os = this.__startWriteParams(Ice.FormatType.DefaultFormat);
                __os.writeString(set);
                __os.writeString(error);
                __os.writeInt(__ret);
                this.__endWriteParams(true);
            }
            catch(Ice.LocalException __ex)
            {
                __exception(__ex);
                return;
            }
            __response();
        }
    }
}
