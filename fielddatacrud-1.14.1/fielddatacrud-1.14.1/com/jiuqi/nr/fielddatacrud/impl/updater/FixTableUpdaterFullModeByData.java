/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.ReturnResInstance
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.dto.DimField;
import com.jiuqi.nr.fielddatacrud.impl.updater.FixTableUpdaterFullMode;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class FixTableUpdaterFullModeByData
extends FixTableUpdaterFullMode
implements TableUpdater {
    private static final Logger logger = LoggerFactory.getLogger(FixTableUpdaterFullModeByData.class);

    public FixTableUpdaterFullModeByData(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        super(saveInfo, fieldRelation);
    }

    @Override
    public ReturnRes addRow(Object[] values) throws CrudOperateException {
        DimensionValueSet masterKeys = new DimensionValueSet();
        HashSet<Integer> dimIndex = new HashSet<Integer>();
        for (DimField dimField : this.dimFields) {
            int index = dimField.getIndex();
            dimIndex.add(index);
            if (values.length <= index) {
                throw new CrudOperateException(1202, "\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e");
            }
            Object value = values[index];
            if (dimField.getType() != DimField.P_DIM) continue;
            masterKeys.setValue(dimField.getDimName(), value);
        }
        boolean flag = false;
        for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
            if (dimIndex.contains(i) || ObjectUtils.isEmpty(values[i])) continue;
            flag = true;
            break;
        }
        if (!flag) {
            return ReturnResInstance.success();
        }
        ReturnRes returnRes = this.addRow(masterKeys, masterKeys);
        if (returnRes.getCode() != 0) {
            return returnRes;
        }
        for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
            ReturnRes res = this.setData(i, values[i]);
            if (res.getCode() == 0) continue;
            String dw = masterKeys.getValue(this.tableDimSet.getDwDimName()).toString();
            this.failDw.add(dw);
            this.failRes.put(dw, res);
            return res;
        }
        return ReturnResInstance.success();
    }

    @Override
    public void commit() throws CrudOperateException {
        if (!this.currentBatch.isEmpty()) {
            this.currentBatchCommit();
        }
        if (logger.isTraceEnabled()) {
            logger.trace("\u6309\u5bfc\u5165\u6570\u636e\u8303\u56f4\u8fdb\u884c\u5168\u91cf\u5bfc\u5165\uff0c\u65e0\u9700\u5220\u9664\u7a7a\u8868\u5355\u4f4d\u4e0b\u7684\u6570\u636e\uff1a{}", (Object)this.accessMasterKeys);
        }
    }
}

