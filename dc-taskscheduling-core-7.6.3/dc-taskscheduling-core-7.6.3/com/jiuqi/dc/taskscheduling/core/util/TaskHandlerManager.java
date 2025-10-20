/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.tree.BaseTreeNode
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.core.util.ITaskHandlerGather
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.taskscheduling.core.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.tree.BaseTreeNode;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandlerTreeNode;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.util.ITaskHandlerGather;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerTreeProvider;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TaskHandlerManager {
    private static TaskHandlerManager innerInstance;
    private static ReadWriteLock lock;
    public static final String LEVEL_BINDDING_NAME = "taskHandle.%1$s.Level%2$d";
    public static final String POST_BINDDING_NAME = "taskHandle.%1$s.Post%2$d";
    public static final String SPECIAL_BINDDING_NAME = "taskHandle.%1$s.%2$s";
    private static final String LEVEL_BINDDING_NAME_SUFFIX = "taskHandle.%1$s.Level%2$d-out-0";
    private static final String POST_BINDDING_NAME_SUFFIX = "taskHandle.%1$s.Post%2$d-out-0";
    private static final String SPECIAL_BINDDING_NAME_SUFFIX = "taskHandle.%1$s.%2$s-out-0";
    private static Map<String, TaskHandlerTreeNode> nodeMap;
    private static List<TaskHandlerVO> handlerList;

    private TaskHandlerManager() {
        this.initTreeNode();
    }

    public static TaskHandlerManager getInstance() {
        return innerInstance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initTreeNode() {
        lock.writeLock().lock();
        try {
            nodeMap = new HashMap<String, TaskHandlerTreeNode>();
            handlerList = new ArrayList<TaskHandlerVO>();
            List handlers = ((ITaskHandlerGather)ApplicationContextRegister.getBean(ITaskHandlerGather.class)).getHandlerList();
            handlerList.addAll(handlers);
            TaskHandlerTreeProvider provider = new TaskHandlerTreeProvider(handlers);
            TaskHandlerTreeNode root = new TaskHandlerTreeNode(provider.getRoot());
            nodeMap.put("-", root);
            for (TaskHandlerVO taskHandler : provider.getTopHandler()) {
                TaskHandlerTreeNode topNode = new TaskHandlerTreeNode(taskHandler);
                topNode.setParentCode("-");
                nodeMap.put(taskHandler.getName(), topNode);
                TaskHandlerManager.getChildNode(nodeMap, provider, topNode);
            }
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void refreshTreeNode(List<TaskHandlerVO> handlers) {
        lock.writeLock().lock();
        try {
            nodeMap = new HashMap<String, TaskHandlerTreeNode>();
            handlerList = new ArrayList<TaskHandlerVO>();
            handlerList.addAll(handlers);
            TaskHandlerTreeProvider provider = new TaskHandlerTreeProvider(handlers);
            TaskHandlerTreeNode root = new TaskHandlerTreeNode(provider.getRoot());
            nodeMap.put("-", root);
            for (TaskHandlerVO taskHandler : provider.getTopHandler()) {
                TaskHandlerTreeNode topNode = new TaskHandlerTreeNode(taskHandler);
                topNode.setParentCode("-");
                nodeMap.put(taskHandler.getName(), topNode);
                TaskHandlerManager.getChildNode(nodeMap, provider, topNode);
            }
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    private static void getChildNode(Map<String, TaskHandlerTreeNode> nodeMap, TaskHandlerTreeProvider provider, TaskHandlerTreeNode currentNode) {
        if (provider.hasChildren(currentNode.getTaskHandler())) {
            for (TaskHandlerVO child : provider.getChildren(currentNode.getTaskHandler())) {
                TaskHandlerTreeNode childNode = new TaskHandlerTreeNode(child);
                childNode.setParentCode(currentNode.getCode());
                currentNode.getChildren().add(childNode);
                nodeMap.put(child.getName(), childNode);
                TaskHandlerManager.getChildNode(nodeMap, provider, childNode);
            }
        }
        currentNode.setLeaf(currentNode.getChildren().isEmpty());
    }

    public static List<TaskHandlerVO> getHandlers() {
        return new ArrayList<TaskHandlerVO>(handlerList);
    }

    public static TaskHandlerVO getTaskHandlerByCode(String taskName) {
        lock.readLock().lock();
        try {
            if (nodeMap.containsKey(taskName)) {
                TaskHandlerVO taskHandlerVO = nodeMap.get(taskName).getTaskHandler();
                return taskHandlerVO;
            }
            TaskHandlerVO taskHandlerVO = null;
            return taskHandlerVO;
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public static String getTaskNameByCode(String taskName) {
        TaskHandlerVO handler = TaskHandlerManager.getTaskHandlerByCode(taskName);
        if (!Objects.isNull(handler)) {
            return handler.getTitle();
        }
        return taskName;
    }

    public static String getDimTypeTitleByName(String taskName) {
        TaskHandlerVO handler = TaskHandlerManager.getTaskHandlerByCode(taskName);
        if (!Objects.isNull(handler)) {
            return nodeMap.get(taskName).getTaskHandler().getDimType().getTitle();
        }
        return taskName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<TaskHandlerVO> getChildrenTaskHandlerByCode(String taskName) {
        lock.readLock().lock();
        try {
            ArrayList<TaskHandlerVO> handlers = new ArrayList<TaskHandlerVO>();
            for (BaseTreeNode baseNode : nodeMap.get(taskName).getChildren()) {
                TaskHandlerTreeNode node = (TaskHandlerTreeNode)baseNode;
                handlers.add(node.getTaskHandler());
            }
            ArrayList<TaskHandlerVO> arrayList = handlers;
            return arrayList;
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public static int getQueueNum(TaskHandlerVO handler, TaskHandleMsg msg) {
        return TaskTypeEnum.LEVEL.equals((Object)handler.getTaskType()) ? msg.getLevel() : new BigDecimal(Math.random() * 5.0).intValue() + 1;
    }

    public static String getBindingName(TaskHandlerVO handler, int queueNum) {
        if (!StringUtils.isEmpty((String)handler.getSpecialQueueFlag())) {
            return String.format(SPECIAL_BINDDING_NAME_SUFFIX, handler.getModule(), handler.getSpecialQueueFlag());
        }
        switch (handler.getTaskType()) {
            case LEVEL: {
                return String.format(LEVEL_BINDDING_NAME_SUFFIX, handler.getModule(), queueNum);
            }
            case POST: {
                return String.format(POST_BINDDING_NAME_SUFFIX, handler.getModule(), queueNum);
            }
        }
        throw new RuntimeException(String.format("\u4efb\u52a1\u5904\u7406\u5668\u3010%1$s\u3011\u4efb\u52a1\u7c7b\u578b\u5c5e\u6027\u5f02\u5e38", handler.getName()));
    }

    public static String getBindingDestination(TaskHandlerVO handler, int queueNum) {
        if (!StringUtils.isEmpty((String)handler.getSpecialQueueFlag())) {
            return String.format(SPECIAL_BINDDING_NAME, handler.getModule(), handler.getSpecialQueueFlag());
        }
        switch (handler.getTaskType()) {
            case LEVEL: {
                return String.format(LEVEL_BINDDING_NAME, handler.getModule(), queueNum);
            }
            case POST: {
                return String.format(POST_BINDDING_NAME, handler.getModule(), queueNum);
            }
        }
        throw new RuntimeException(String.format("\u4efb\u52a1\u5904\u7406\u5668\u3010%1$s\u3011\u4efb\u52a1\u7c7b\u578b\u5c5e\u6027\u5f02\u5e38", handler.getName()));
    }

    static {
        lock = new ReentrantReadWriteLock();
        innerInstance = new TaskHandlerManager();
    }
}

