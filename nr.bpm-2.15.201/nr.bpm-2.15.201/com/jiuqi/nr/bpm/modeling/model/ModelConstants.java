/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

public class ModelConstants {
    public static final String MODELTAG_DEFINTION = "definitions";
    public static final String MODELTAG_PROCESS = "process";
    public static final String MODELTAG_STARTEVENT = "startEvent";
    public static final String MODELTAG_ENDEVENT = "endEvent";
    public static final String MODELTAG_USERTASK = "userTask";
    public static final String MODELTAG_EXTENSIONELEMENTS = "extensionElements";
    public static final String MODELTAG_MULTINSTANCELOOP = "multiInstanceLoopCharacteristics";
    public static final String MODELTAG_ACTIVITIEXTENSION = "activiti:";
    public static final String MODELTAG_SEQUENCEFLOW = "sequenceFlow";
    public static final String MODELTAG_CONDITIONEXPRESSION = "conditionExpression";
    public static final String MODELATTRIBUTE_NAME = "name";
    public static final String MODELATTRIBUTE_CANDIDATEUSERS = "activiti:candidateUsers";
    public static final String MODELATTRIBUTE_CANDIDATEGROUPS = "activiti:candidateGroups";
    public static final String MODELATTRIBUTE_DEFAULTFLOW = "default";
    public static final String MODELATTRIBUTE_COMPLETE_CONDITION = "completionCondition";
    public static final String MODELATTRIBUTE_EXTENSIONTAG_ELEMENT = "activiti:elementVariable";
    public static final String MODELATTRIBUTE_EXTENSIONTAG_COLLECTION = "activiti:collection";
    public static final String MODELATTRIBUTE_EXTENSIONTAG_SEQUENTIAL = "isSequential";
    public static final String ACTIVITI_EXTENSIONTAG_TASKLISTENER = "taskListener";
    public static final String ACTIVITI_EXTENSIONTAG_EXECUTIONLISTENER = "executionListener";
    public static final String ACTIVITI_EXTENSIONTAG_ACTORSTRATEGY = "actorStrategy";
    public static final String ACTIVITI_EXTENSIONTAG_ACTION = "action";
    public static final String ACTIVITI_EXTENSIONTAG_RETRIVABLE = "retrivable";
    public static final String ACTIVITI_EXTENSIONTAG_FORMEDITABLE = "formEditable";
    public static final String ACTIVITI_EXTENSIONTAG_NOTICECHANGE = "noticeChannel";
    public static final String ACTIVITI_EXTENSIONTAG_TODO = "todoContent";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_ACTORSTRATEGY_TYPE = "type";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_ACTORSTRATEGY_PARAM = "param";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_ACTION_ID = "code";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_ACTION_NAME = "name";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_ACTION_COMMENT = "needComment";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_RETRIVABLE_VALUE = "value";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_FORMEDITABLE_VALUE = "value";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_NOTICECHANGE_VALUE = "value";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_TODO_VALUE = "value";
    public static final String ACTIVITI_EXTENSIONATTRIBUTE_MULTINSTANCELOOP_VALUE = "value";
    public static final String BEANNAME_DEFAULT_TASKLISTENER_CREATE = "ActivitiTaskCreateListenerBean";
    public static final String BEANNAME_DEFAULT_EXECUTIONLISTENER_END = "ActivtiExecutionListenerBean";
    public static final String BEANNAME_DEFAULT_EXECUTIONLISTENER_CLASS = "com.jiuqi.nr.bpm.impl.activiti6.ActivitiExecutionListener";
    public static final String BEANNAME_DEFAULT_COUNTSIGN_LISTENER = "ActivitiCounterSignListenerBean";
    public static final String BEANNAME_DEFAULT_MULTIINSTANCE_COMPLETE = "ActivitiMulitiInstanceListenerBean";
    public static final String VARIABLE_DEFAULT_ASSIGNEE_LIST = "assigneeList";
    public static final String VARIABLE_DEFAULT_ASSIGNEE = "assignee";
    public static final String VARIABLE_DEFAULT_SINGLEUSER = "singleUser";
    public static final String VARIABLE_USERTASKACTION_PRIFIX = "action_";
    public static final String VARIABLE_TAG_PRIFIX = "${";
    public static final String VARIABLE_TAG_SUFFIX = "}";
}

