/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface OriTableModelInfo {
    public String getTableName();

    default public TableModelDefine getOriTableModel() {
        return null;
    }

    public boolean isSimpleTable();

    public TableModelDefine getTableModel();

    public BSTableColumn getDWColumn();

    public BSTableColumn getPeriodColumn();

    public List<BSBizKeyColumn> getSituationColumns();

    public List<BSBizKeyColumn> getBizKeyColumns();

    public List<BSBizKeyColumn> getBuildColumns();

    public List<BSTableColumn> getZBColumns();
}

