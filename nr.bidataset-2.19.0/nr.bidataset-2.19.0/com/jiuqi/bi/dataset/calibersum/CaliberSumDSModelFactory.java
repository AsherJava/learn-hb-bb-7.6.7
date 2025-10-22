/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 */
package com.jiuqi.bi.dataset.calibersum;

import com.jiuqi.bi.dataset.calibersum.CaliberSumDSModelBuilder;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaliberSumDSModelFactory
extends DSModelFactory {
    public static final String TYPE = "CaliberSumDataSet";
    public static final String TITLE = "\u53e3\u5f84\u6c47\u603b\u6570\u636e\u96c6";
    @Autowired
    private CaliberSumDSModelBuilder modelBuilder;

    public DSModel createDataSetModel() {
        CaliberSumDSModel model = new CaliberSumDSModel();
        model.setModelBuilder(this.modelBuilder);
        return model;
    }

    public String getType() {
        return TYPE;
    }
}

