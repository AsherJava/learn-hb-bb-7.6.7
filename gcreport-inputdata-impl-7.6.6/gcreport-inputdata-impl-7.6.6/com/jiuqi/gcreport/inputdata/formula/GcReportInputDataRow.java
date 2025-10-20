/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.gcreport.inputdata.formula;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.List;

public class GcReportInputDataRow
extends GcReportDataRow {
    private IRunTimeViewController runTimeViewController;
    private Metadata<FieldDefine> metadatas;
    private DefaultTableEntity data = null;
    private List<? extends DefaultTableEntity> datas;

    public GcReportInputDataRow(Metadata<FieldDefine> metadatas) {
        super(metadatas);
        this.metadatas = metadatas;
    }

    public void setData(DefaultTableEntity data) {
        this.data = data;
    }

    public void setDatas(List<? extends DefaultTableEntity> datas) {
        this.datas = datas;
    }

    public Object getValue(int index) {
        String key;
        Column metadata = this.metadatas.getColumn(index);
        if ("FORMID".equals(metadata.getName()) && this.data instanceof InputDataEO) {
            String formId = ((InputDataEO)this.data).getFormId();
            FormDefine currFormDefine = this.getRunTimeViewController().queryFormById(formId);
            if (currFormDefine != null) {
                return currFormDefine.getFormCode();
            }
        }
        String string = key = metadata.getInfo() == null ? metadata.getName() : ((FieldDefine)metadata.getInfo()).getCode();
        if ("SUBJECTOBJ".equalsIgnoreCase(key) && this.data instanceof InputDataEO) {
            return ((InputDataEO)this.data).getSubjectCode();
        }
        if (this.data.getFields().containsKey(key)) {
            return this.data.getFieldValue(key);
        }
        if (!CollectionUtils.isEmpty(this.datas)) {
            return this.datas.get(0).getFieldValue(key);
        }
        return null;
    }

    private IRunTimeViewController getRunTimeViewController() {
        if (null == this.runTimeViewController) {
            this.runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        }
        return this.runTimeViewController;
    }

    public boolean commit() {
        return true;
    }
}

