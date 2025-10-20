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
package com.jiuqi.gcreport.webserviceclient.service;

import com.jiuqi.gcreport.webserviceclient.vo.RequestGcWsDataParam;
import com.jiuqi.gcreport.webserviceclient.vo.saptozjk.SapToZjkParam;
import com.jiuqi.gcreport.webserviceclient.vo.saptozjk.SapToZjkResult;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace="urn:jiuqi:document:gcreport:functions", name="GCREPORT_WS_SERVER")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface WebserviceServerService {
    @WebMethod(operationName="SAP_TO_ZJK_DATA")
    @WebResult(name="SAP_TO_ZJK_DATA.Response", targetNamespace="urn:jiuqi:document:gcreport:functions", partName="sapToZjkParam")
    public SapToZjkResult sapDataToZjk(@WebParam(partName="sapToZjkParam", name="SAP_TO_ZJK_DATA", targetNamespace="urn:jiuqi:document:gcreport:functions") SapToZjkParam var1);

    @WebMethod(operationName="REQUEST_GC_WS_DATA")
    @WebResult(name="REQUEST_GC_WS_DATA.Response", targetNamespace="urn:jiuqi:document:gcreport:functions", partName="requestGcWsDataParam")
    public String requestGcWsData(@WebParam(partName="requestGcWsDataParam", name="REQUEST_GC_WS_DATA", targetNamespace="urn:jiuqi:document:gcreport:functions") RequestGcWsDataParam var1);
}

