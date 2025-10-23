/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.dto.ITransferContext
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.face.IResourceDeploy
 *  com.jiuqi.nr.task.api.face.IResourceIOProvider
 */
package com.jiuqi.nr.print.ext;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.print.ext.PrintResourceDataProvider;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.dto.ITransferContext;
import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceDeploy;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintManageExt
extends AbstractFormSchemeResourceFactory {
    public static final String PRINT_CODE = "PRINT_MANAGE";
    public static final String PRINT_TITLE = "\u6253\u5370\u65b9\u6848";
    @Autowired
    private IDesignTimePrintController designTimeController;

    public String code() {
        return PRINT_CODE;
    }

    public String title() {
        return PRINT_TITLE;
    }

    public double order() {
        return 2.0;
    }

    public boolean enable(String formSchemeKey) {
        return true;
    }

    public IResourceDataProvider dataProvider() {
        return new PrintResourceDataProvider(this.designTimeController);
    }

    public IResourceIOProvider transferProvider(ITransferContext context) {
        return null;
    }

    public IResourceDeploy deployProvider() {
        return null;
    }

    public ComponentDefine component() {
        return new ComponentDefine("printManage", "@nr", "reportPrintScheme");
    }
}

