/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 */
package com.jiuqi.nr.query.dataset.impl;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.nr.query.dataset.QueryDSModel;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrQueryDSModelFactory
extends DSModelFactory {
    public static final String TYPE = "QueryDataSet";
    public static final String TITLE = "\u8fc7\u5f55\u67e5\u8be2\u6570\u636e\u96c6";
    @Autowired
    private NrQueryDSModelBuilder modelBuilder;

    public DSModel createDataSetModel() {
        QueryDSModel model = new QueryDSModel(this.modelBuilder);
        return model;
    }

    public String getType() {
        return TYPE;
    }
}

