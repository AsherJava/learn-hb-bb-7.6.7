/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

import com.jiuqi.nr.definition.option.core.TaskOptionDefineDTO;
import com.jiuqi.nr.definition.option.core.TaskOptionPageDTO;
import java.util.List;
import java.util.Map;

public class TaskOptionVO {
    private List<TaskOptionPageDTO> list;
    private Map<String, TaskOptionDefineDTO> keyMap;
    private Map<String, List<String>> relationMap;

    public List<TaskOptionPageDTO> getList() {
        return this.list;
    }

    public void setList(List<TaskOptionPageDTO> list) {
        this.list = list;
    }

    public Map<String, TaskOptionDefineDTO> getKeyMap() {
        return this.keyMap;
    }

    public void setKeyMap(Map<String, TaskOptionDefineDTO> keyMap) {
        this.keyMap = keyMap;
    }

    public Map<String, List<String>> getRelationMap() {
        return this.relationMap;
    }

    public void setRelationMap(Map<String, List<String>> relationMap) {
        this.relationMap = relationMap;
    }
}

