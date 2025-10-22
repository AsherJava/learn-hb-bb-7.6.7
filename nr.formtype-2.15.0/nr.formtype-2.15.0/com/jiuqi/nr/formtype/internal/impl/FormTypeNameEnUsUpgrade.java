/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.va.domain.task.StorageSyncTask;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormTypeNameEnUsUpgrade
implements StorageSyncTask {
    @Autowired
    private IFormTypeService iFormTypeService;
    private static final Logger logger = LoggerFactory.getLogger(FormTypeNameEnUsUpgrade.class);

    public void execute() {
        List<FormTypeDefine> formTypeDefines = this.iFormTypeService.queryAllFormType();
        for (FormTypeDefine formTypeDefine : formTypeDefines) {
            try {
                this.iFormTypeService.updateFormType(formTypeDefine);
            }
            catch (JQException e) {
                logger.error("\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\uff1a{}\u6dfb\u52a0\u82f1\u6587\u540d\u79f0\u5b57\u6bb5\u5931\u8d25", (Object)formTypeDefine.getCode(), (Object)e);
            }
        }
    }

    public int getSortNum() {
        return 3100;
    }

    public String getVersion() {
        return "20221221-1600";
    }
}

