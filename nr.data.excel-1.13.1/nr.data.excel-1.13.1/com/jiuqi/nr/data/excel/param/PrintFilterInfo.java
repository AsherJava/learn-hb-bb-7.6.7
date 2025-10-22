/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintFilterInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, List<String>> taskFilterFormList = new HashMap<String, List<String>>();
    private Map<String, List<String>> taskNotFilterFormList = new HashMap<String, List<String>>();
    private String msg = "taskFilterFormList:\u4efb\u52a1\u5bf9\u5e94\u8fc7\u6ee4\u8868\u5355Map,key:taskKey(\u6307\u5b9a\u7684\u4efb\u52a1Key\u4fe1\u606f),value:formCode (List\u7c7b\u578b---\u8981\u8fc7\u6ee4\u7684\u8868\u5355Code\u4fe1\u606f);taskNotFilterFormList:\u4efb\u52a1\u5bf9\u5e94\u4e0d\u8fc7\u6ee4\u8868\u5355Map,key:taskKey(\u6307\u5b9a\u7684\u4efb\u52a1Key\u4fe1\u606f),value:formCode (List\u7c7b\u578b---\u8981\u5bfc\u51fa\u7684\u8868\u5355Code\u4fe1\u606f),\u4e0etaskFilterFormList\u4e0d\u5171\u540c\u89e3\u6790,\u4f18\u5148\u89e3\u6790\u8fc7\u6ee4\u8868\u5355";

    public Map<String, List<String>> getTaskFilterFormList() {
        return this.taskFilterFormList;
    }

    public void setTaskFilterFormList(Map<String, List<String>> taskFilterFormList) {
        this.taskFilterFormList = taskFilterFormList;
    }

    public Map<String, List<String>> getTaskNotFilterFormList() {
        return this.taskNotFilterFormList;
    }

    public void setTaskNotFilterFormList(Map<String, List<String>> taskNotFilterFormList) {
        this.taskNotFilterFormList = taskNotFilterFormList;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

