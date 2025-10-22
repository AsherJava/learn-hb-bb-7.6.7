/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.impl.updater.TableUpdaterFullMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class TableUpdaterFullModeByData
extends TableUpdaterFullMode {
    private static final Logger logger = LoggerFactory.getLogger(TableUpdaterFullModeByData.class);
    protected Set<DimensionValueSet> deleteDimensionValueSet = new HashSet<DimensionValueSet>();
    protected Set<DimensionValueSet> currDimensionValueSet = new HashSet<DimensionValueSet>();

    public TableUpdaterFullModeByData(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        super(saveInfo, fieldRelation);
    }

    @Override
    public ReturnRes addRow(DimensionValueSet masterKeys, DimensionValueSet rowKeys) throws CrudOperateException {
        String dw = this.getDwFromMasterKeys(masterKeys);
        if (this.failDw.contains(dw)) {
            return (ReturnRes)this.failRes.get(dw);
        }
        if (this.hasAccessPermission(masterKeys)) {
            this.processDw.add(dw);
            this.addRowToAllRows(rowKeys);
            if (!this.currDimensionValueSet.contains(masterKeys) && !this.deleteDimensionValueSet.contains(masterKeys)) {
                this.currDimensionValueSet.add(masterKeys);
            }
            this.checkAndPerformBatchCommit();
            this.logAddRowWithPermission(masterKeys);
            return ReturnRes.ok(null);
        }
        Set<DimensionValueSet> noAccessMasterKeys = this.accessDTO.getNoAccessMasterKeys();
        if (noAccessMasterKeys.contains(masterKeys)) {
            this.logAddRowWithoutPermission(masterKeys);
            this.noPermissionDw.add(dw);
            return ReturnRes.build((int)1101);
        }
        this.logAddRowWithOutOfRange(masterKeys);
        return ReturnRes.build((int)1103);
    }

    @Override
    protected boolean checkAndPerformBatchCommit() throws CrudOperateException {
        boolean commited = super.checkAndPerformBatchCommit();
        if (commited) {
            this.currDimensionValueSet.clear();
        }
        return commited;
    }

    @Override
    protected void initDataUpdaterForBatchCommit() throws CrudOperateException {
        if (CollectionUtils.isEmpty(this.currDimensionValueSet)) {
            throw new CrudOperateException("\u6570\u636e\u884c\u6570\u4e3a0,\u65e0\u9700\u5904\u7406\u6570\u636e");
        }
        DimensionValueSet currKeys = this.currDimensionValueSet.size() == 1 ? this.currDimensionValueSet.iterator().next() : DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList<DimensionValueSet>(this.currDimensionValueSet));
        this.fileMark(currKeys);
        this.initIDataUpdater(currKeys, true);
        this.deleteDimensionValueSet.addAll(this.currDimensionValueSet);
    }

    @Override
    public void commit() throws CrudOperateException {
        if (CollectionUtils.isEmpty(this.allRows)) {
            return;
        }
        if (CollectionUtils.isEmpty(this.currDimensionValueSet)) {
            return;
        }
        super.commit();
    }
}

