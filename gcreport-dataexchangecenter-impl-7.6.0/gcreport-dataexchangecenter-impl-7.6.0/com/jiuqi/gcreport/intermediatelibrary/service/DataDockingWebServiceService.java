/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jws.WebMethod
 *  javax.jws.WebParam
 *  javax.jws.WebResult
 *  javax.jws.WebService
 *  javax.jws.soap.SOAPBinding
 *  javax.jws.soap.SOAPBinding$ParameterStyle
 */
package com.jiuqi.gcreport.intermediatelibrary.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace="urn:jiuqi:document:gcreport:functions", name="DataDockingWebServiceService")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface DataDockingWebServiceService {
    @WebMethod(operationName="saveData")
    @WebResult(name="jsonResp")
    public String saveData(@WebParam(name="jsonParam") String var1);
}

