/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nrdt.parampacket.manage.desktop.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nrdt.parampacket.manage.desktop.exception.ExceptionEnum;
import com.jiuqi.nrdt.parampacket.manage.desktop.service.TaskService;
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="syncTaskService")
public class TaskServiceImpl
implements TaskService {
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;

    @Override
    public List<FormSchemeDefine> queryFormSchemeInTask(String taskKey) throws JQException {
        Assert.notNull((Object)taskKey, (String)"taskKey \u4e0d\u80fd\u4e3a\u7a7a!");
        ArrayList<FormSchemeDefine> defines = new ArrayList();
        try {
            defines = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.QUERY_ERROR, "\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u65f6\u9519\u8bef\u3002");
        }
        return defines;
    }
}

