/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBElement
 *  javax.xml.bind.annotation.XmlElementDecl
 *  javax.xml.bind.annotation.XmlRegistry
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.nr.etl.utils.AnyType2AnyTypeMap;
import com.jiuqi.nr.etl.utils.ArrayOfProcessLog;
import com.jiuqi.nr.etl.utils.ArrayOfTask;
import com.jiuqi.nr.etl.utils.Cancel;
import com.jiuqi.nr.etl.utils.CancelResponse;
import com.jiuqi.nr.etl.utils.Execute;
import com.jiuqi.nr.etl.utils.Execute1;
import com.jiuqi.nr.etl.utils.Execute1Response;
import com.jiuqi.nr.etl.utils.ExecuteEx;
import com.jiuqi.nr.etl.utils.ExecuteExResponse;
import com.jiuqi.nr.etl.utils.ExecuteResponse;
import com.jiuqi.nr.etl.utils.FindTaskByName;
import com.jiuqi.nr.etl.utils.FindTaskByNameResponse;
import com.jiuqi.nr.etl.utils.GetAllTask;
import com.jiuqi.nr.etl.utils.GetAllTaskResponse;
import com.jiuqi.nr.etl.utils.GetTaskLog;
import com.jiuqi.nr.etl.utils.GetTaskLogResponse;
import com.jiuqi.nr.etl.utils.GetTaskState;
import com.jiuqi.nr.etl.utils.GetTaskStateResponse;
import com.jiuqi.nr.etl.utils.ProcessLog;
import com.jiuqi.nr.etl.utils.Task;
import com.jiuqi.nr.etl.utils.TaskLog;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private static final QName _ProcessLogMessage_QNAME = new QName("http://service.server.etl.jiuqi.com", "message");
    private static final QName _ProcessLogGuid_QNAME = new QName("http://service.server.etl.jiuqi.com", "guid");
    private static final QName _ProcessLogSource_QNAME = new QName("http://service.server.etl.jiuqi.com", "source");
    private static final QName _ProcessLogErrorCode_QNAME = new QName("http://service.server.etl.jiuqi.com", "errorCode");
    private static final QName _ProcessLogStackTrace_QNAME = new QName("http://service.server.etl.jiuqi.com", "stackTrace");
    private static final QName _TaskTaskGuid_QNAME = new QName("http://service.server.etl.jiuqi.com", "taskGuid");
    private static final QName _TaskTaskDescription_QNAME = new QName("http://service.server.etl.jiuqi.com", "taskDescription");
    private static final QName _TaskTaskName_QNAME = new QName("http://service.server.etl.jiuqi.com", "taskName");
    private static final QName _TaskLogControlFlowGuid_QNAME = new QName("http://service.server.etl.jiuqi.com", "controlFlowGuid");
    private static final QName _TaskLogDataFlowName_QNAME = new QName("http://service.server.etl.jiuqi.com", "dataFlowName");
    private static final QName _TaskLogList_QNAME = new QName("http://service.server.etl.jiuqi.com", "list");

    public GetAllTask createGetAllTask() {
        return new GetAllTask();
    }

    public ArrayOfProcessLog createArrayOfProcessLog() {
        return new ArrayOfProcessLog();
    }

    public GetTaskLog createGetTaskLog() {
        return new GetTaskLog();
    }

    public AnyType2AnyTypeMap.Entry createAnyType2AnyTypeMapEntry() {
        return new AnyType2AnyTypeMap.Entry();
    }

    public ExecuteEx createExecuteEx() {
        return new ExecuteEx();
    }

    public Execute1 createExecute1() {
        return new Execute1();
    }

    public Task createTask() {
        return new Task();
    }

    public Execute1Response createExecute1Response() {
        return new Execute1Response();
    }

    public TaskLog createTaskLog() {
        return new TaskLog();
    }

    public FindTaskByNameResponse createFindTaskByNameResponse() {
        return new FindTaskByNameResponse();
    }

    public GetTaskLogResponse createGetTaskLogResponse() {
        return new GetTaskLogResponse();
    }

    public GetTaskStateResponse createGetTaskStateResponse() {
        return new GetTaskStateResponse();
    }

    public AnyType2AnyTypeMap createAnyType2AnyTypeMap() {
        return new AnyType2AnyTypeMap();
    }

    public Execute createExecute() {
        return new Execute();
    }

    public GetTaskState createGetTaskState() {
        return new GetTaskState();
    }

    public Cancel createCancel() {
        return new Cancel();
    }

    public ProcessLog createProcessLog() {
        return new ProcessLog();
    }

    public CancelResponse createCancelResponse() {
        return new CancelResponse();
    }

    public ArrayOfTask createArrayOfTask() {
        return new ArrayOfTask();
    }

    public GetAllTaskResponse createGetAllTaskResponse() {
        return new GetAllTaskResponse();
    }

    public ExecuteExResponse createExecuteExResponse() {
        return new ExecuteExResponse();
    }

    public ExecuteResponse createExecuteResponse() {
        return new ExecuteResponse();
    }

    public FindTaskByName createFindTaskByName() {
        return new FindTaskByName();
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="message", scope=ProcessLog.class)
    public JAXBElement<String> createProcessLogMessage(String value) {
        return new JAXBElement(_ProcessLogMessage_QNAME, String.class, ProcessLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="guid", scope=ProcessLog.class)
    public JAXBElement<String> createProcessLogGuid(String value) {
        return new JAXBElement(_ProcessLogGuid_QNAME, String.class, ProcessLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="source", scope=ProcessLog.class)
    public JAXBElement<String> createProcessLogSource(String value) {
        return new JAXBElement(_ProcessLogSource_QNAME, String.class, ProcessLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="errorCode", scope=ProcessLog.class)
    public JAXBElement<String> createProcessLogErrorCode(String value) {
        return new JAXBElement(_ProcessLogErrorCode_QNAME, String.class, ProcessLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="stackTrace", scope=ProcessLog.class)
    public JAXBElement<byte[]> createProcessLogStackTrace(byte[] value) {
        return new JAXBElement(_ProcessLogStackTrace_QNAME, byte[].class, ProcessLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="taskGuid", scope=Task.class)
    public JAXBElement<String> createTaskTaskGuid(String value) {
        return new JAXBElement(_TaskTaskGuid_QNAME, String.class, Task.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="taskDescription", scope=Task.class)
    public JAXBElement<String> createTaskTaskDescription(String value) {
        return new JAXBElement(_TaskTaskDescription_QNAME, String.class, Task.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="taskName", scope=Task.class)
    public JAXBElement<String> createTaskTaskName(String value) {
        return new JAXBElement(_TaskTaskName_QNAME, String.class, Task.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="guid", scope=TaskLog.class)
    public JAXBElement<String> createTaskLogGuid(String value) {
        return new JAXBElement(_ProcessLogGuid_QNAME, String.class, TaskLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="controlFlowGuid", scope=TaskLog.class)
    public JAXBElement<String> createTaskLogControlFlowGuid(String value) {
        return new JAXBElement(_TaskLogControlFlowGuid_QNAME, String.class, TaskLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="taskGuid", scope=TaskLog.class)
    public JAXBElement<String> createTaskLogTaskGuid(String value) {
        return new JAXBElement(_TaskTaskGuid_QNAME, String.class, TaskLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="dataFlowName", scope=TaskLog.class)
    public JAXBElement<String> createTaskLogDataFlowName(String value) {
        return new JAXBElement(_TaskLogDataFlowName_QNAME, String.class, TaskLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="list", scope=TaskLog.class)
    public JAXBElement<ArrayOfProcessLog> createTaskLogList(ArrayOfProcessLog value) {
        return new JAXBElement(_TaskLogList_QNAME, ArrayOfProcessLog.class, TaskLog.class, (Object)value);
    }

    @XmlElementDecl(namespace="http://service.server.etl.jiuqi.com", name="taskName", scope=TaskLog.class)
    public JAXBElement<String> createTaskLogTaskName(String value) {
        return new JAXBElement(_TaskTaskName_QNAME, String.class, TaskLog.class, (Object)value);
    }
}

