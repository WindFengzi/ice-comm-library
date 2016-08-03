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

public abstract class Callback_MQInterface_writeBusiLog extends Ice.TwowayCallback
{
    public abstract void response(boolean __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        MQInterfacePrx __proxy = (MQInterfacePrx)__result.getProxy();
        boolean __ret = false;
        try
        {
            __ret = __proxy.end_writeBusiLog(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
