/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 */
package com.jiuqi.nr.snapshot.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.nr.snapshot.dataset.builder.SnapshotDSModelBuilder;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnapshotDSModelFactory
extends DSModelFactory {
    @Autowired
    private SnapshotDSModelBuilder snapshotDSModelBuilder;

    public DSModel createDataSetModel() {
        SnapshotDSModel snapshotDSModel = new SnapshotDSModel();
        snapshotDSModel.setSnapshotDSModelBuilder(this.snapshotDSModelBuilder);
        return snapshotDSModel;
    }

    public String getType() {
        return "SnapshotDataSet";
    }
}

