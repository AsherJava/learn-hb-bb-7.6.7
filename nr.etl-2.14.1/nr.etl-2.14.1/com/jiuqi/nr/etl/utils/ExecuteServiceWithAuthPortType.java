/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jws.WebMethod
 *  javax.jws.WebParam
 *  javax.jws.WebResult
 *  javax.jws.WebService
 *  javax.xml.bind.annotation.XmlSeeAlso
 *  javax.xml.ws.RequestWrapper
 *  javax.xml.ws.ResponseWrapper
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.nr.etl.utils.AnyType2AnyTypeMap;
import com.jiuqi.nr.etl.utils.ArrayOfTask;
import com.jiuqi.nr.etl.utils.ObjectFactory;
import com.jiuqi.nr.etl.utils.TaskLog;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace="http://service.etl.jiuqi.com", name="ExecuteServiceWithAuthPortType")
@XmlSeeAlso(value={ObjectFactory.class, ObjectFactory.class})
public interface ExecuteServiceWithAuthPortType {
    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="getTaskLog", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.GetTaskLog")
    @WebMethod
    @ResponseWrapper(localName="getTaskLogResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.GetTaskLogResponse")
    public TaskLog getTaskLog(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3);

    @RequestWrapper(localName="cancel", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.Cancel")
    @WebMethod
    @ResponseWrapper(localName="cancelResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.CancelResponse")
    public void cancel(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3);

    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="getTaskState", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.GetTaskState")
    @WebMethod
    @ResponseWrapper(localName="getTaskStateResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.GetTaskStateResponse")
    public int getTaskState(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3);

    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="executeEx", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.ExecuteEx")
    @WebMethod
    @ResponseWrapper(localName="executeExResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.ExecuteExResponse")
    public String executeEx(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3, @WebParam(name="in3", targetNamespace="http://service.etl.jiuqi.com") String var4);

    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="execute", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.Execute")
    @WebMethod
    @ResponseWrapper(localName="executeResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.ExecuteResponse")
    public String execute(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3);

    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="getAllTask", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.GetAllTask")
    @WebMethod
    @ResponseWrapper(localName="getAllTaskResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.GetAllTaskResponse")
    public ArrayOfTask getAllTask(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2);

    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="findTaskByName", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.FindTaskByName")
    @WebMethod
    @ResponseWrapper(localName="findTaskByNameResponse", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.FindTaskByNameResponse")
    public ArrayOfTask findTaskByName(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") String var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3);

    @WebResult(name="out", targetNamespace="http://service.etl.jiuqi.com")
    @RequestWrapper(localName="execute1", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.Execute1")
    @WebMethod
    @ResponseWrapper(localName="execute1Response", targetNamespace="http://service.etl.jiuqi.com", className="com.jiuqi.etl.service.Execute1Response")
    public String execute1(@WebParam(name="in0", targetNamespace="http://service.etl.jiuqi.com") String var1, @WebParam(name="in1", targetNamespace="http://service.etl.jiuqi.com") AnyType2AnyTypeMap var2, @WebParam(name="in2", targetNamespace="http://service.etl.jiuqi.com") String var3, @WebParam(name="in3", targetNamespace="http://service.etl.jiuqi.com") String var4);
}

