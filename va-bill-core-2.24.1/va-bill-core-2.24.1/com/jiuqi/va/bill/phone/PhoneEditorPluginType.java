/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.view.impl.ViewPluginType
 */
package com.jiuqi.va.bill.phone;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.view.impl.ViewPluginType;
import org.springframework.stereotype.Component;

@Component
public class PhoneEditorPluginType
extends ViewPluginType {
    public static final String NAME = "phone-editor";
    public static final String TITLE = "\u624b\u673a\u7f16\u8f91\u754c\u9762";

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }
}

