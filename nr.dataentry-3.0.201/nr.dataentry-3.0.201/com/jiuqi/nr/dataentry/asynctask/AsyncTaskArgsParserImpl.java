/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskArgsparser
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableBase
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskArgsparser;
import com.jiuqi.nr.dataentry.bean.AsyncUploadParam;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableBase;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AsyncTaskArgsParserImpl
implements AsyncTaskArgsparser {
    public String getTaskKey(Object args) {
        if (args == null) {
            return null;
        }
        String taskKey = null;
        if (args instanceof IJtableBase) {
            IJtableBase jsonObject = (IJtableBase)args;
            JtableContext jtableContext = jsonObject.getContext();
            if (jtableContext != null) {
                taskKey = jtableContext.getTaskKey();
            }
        } else if (args instanceof JtableContext) {
            JtableContext jtableContext = (JtableContext)args;
            taskKey = jtableContext.getTaskKey();
        } else if (args instanceof AsyncUploadParam) {
            AsyncUploadParam asyncUploadParam = (AsyncUploadParam)args;
            taskKey = asyncUploadParam.getParam().getTaskKey();
        }
        return taskKey;
    }

    public String getFormSchemeKey(Object args) {
        if (args == null) {
            return null;
        }
        String taskKey = null;
        if (args instanceof IJtableBase) {
            IJtableBase jsonObject = (IJtableBase)args;
            JtableContext jtableContext = jsonObject.getContext();
            if (jtableContext != null) {
                taskKey = jtableContext.getFormSchemeKey();
            }
        } else if (args instanceof JtableContext) {
            JtableContext jtableContext = (JtableContext)args;
            taskKey = jtableContext.getFormSchemeKey();
        } else if (args instanceof AsyncUploadParam) {
            AsyncUploadParam asyncUploadParam = (AsyncUploadParam)args;
            taskKey = asyncUploadParam.getParam().getFormSchemeKey();
        }
        return taskKey;
    }

    public Map<String, String> getTaskKeyAndFormSchemeKey(Object args) {
        if (args == null) {
            return null;
        }
        HashMap<String, String> keyMap = new HashMap<String, String>();
        if (args instanceof IJtableBase) {
            IJtableBase jsonObject = (IJtableBase)args;
            JtableContext jtableContext = jsonObject.getContext();
            if (jtableContext != null) {
                keyMap.put("taskkey", jtableContext.getTaskKey());
                keyMap.put("formschemekey", jtableContext.getFormSchemeKey());
            }
        } else if (args instanceof JtableContext) {
            JtableContext jtableContext = (JtableContext)args;
            keyMap.put("taskkey", jtableContext.getTaskKey());
            keyMap.put("formschemekey", jtableContext.getFormSchemeKey());
        } else if (args instanceof AsyncUploadParam) {
            AsyncUploadParam asyncUploadParam = (AsyncUploadParam)args;
            keyMap.put("taskkey", asyncUploadParam.getParam().getTaskKey());
            keyMap.put("formschemekey", asyncUploadParam.getParam().getFormSchemeKey());
        }
        return keyMap;
    }
}

