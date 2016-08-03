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

public abstract class AMI_MQInterface_select extends Callback_MQInterface_select
{
    public abstract void ice_response(int __ret, String set, String error);

    /**
     * ice_exception indicates to the caller that
     * the operation completed with an exception.
     * @param ex The Ice run-time exception to be raised.
     **/
    public abstract void ice_exception(Ice.LocalException ex);

    public final void response(int __ret, String set, String error)
    {
        ice_response(__ret, set, error);
    }

    public final void exception(Ice.LocalException __ex)
    {
        ice_exception(__ex);
    }

    @Override public final void sent(boolean sentSynchronously)
    {
        if(!sentSynchronously && this instanceof Ice.AMISentCallback)
        {
            ((Ice.AMISentCallback)this).ice_sent();
        }
    }
}
