/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.event.BaseDataEvent
 */
package com.jiuqi.gcreport.offsetitem.listener;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.event.BaseDataEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MdAgingEventListener
implements ApplicationListener<BaseDataEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MdAgingEventListener.class);
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    private static final String SUBJECTAGING = "MD_SUBJECT_AGING";
    private static final String MD_AGING = "MD_AGING";
    private static final String FN_AGINGCODE = "agingcode";
    private static final String AGEING_PRIFIX = "AGE_";

    @Override
    public void onApplicationEvent(BaseDataEvent BaseDataEvent2) {
        if (!BaseDataEvent2.getBaseDataDTO().getTableName().equals(SUBJECTAGING) || BaseDataEvent2.getEventType() == BaseDataOption.EventType.DELETE) {
            return;
        }
        List ageCodeList = (List)BaseDataEvent2.getBaseDataDTO().get((Object)FN_AGINGCODE);
        if (!CollectionUtils.isEmpty((Collection)ageCodeList)) {
            List oldAgeCodeList = new ArrayList();
            if (BaseDataEvent2.getBaseDataOldDTO() != null) {
                oldAgeCodeList = (List)BaseDataEvent2.getBaseDataOldDTO().get((Object)FN_AGINGCODE);
            }
            for (String ageCode : ageCodeList) {
                DesignColumnModelDefine columnModelDefine;
                if (oldAgeCodeList.contains(ageCode)) continue;
                String columnTitle = GcBaseDataCenterTool.getInstance().queryBasedataByCode(MD_AGING, ageCode).getTitle();
                String columnCode = AGEING_PRIFIX + ageCode;
                TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName("GC_OFFSETVCHRITEM");
                try {
                    columnModelDefine = this.designDataModelService.getColumnModelDefineByCode(tableModel.getID(), columnCode);
                }
                catch (Exception e) {
                    logger.error("\u67e5\u8be2\u8868\u62b5\u9500\u5206\u5f55\u8868\u7684\u5b57\u6bb5\u201c{}\u201d\u5b9a\u4e49\u5931\u8d25\u3002", (Object)columnCode, (Object)e);
                    throw new BusinessRuntimeException("\u67e5\u8be2\u8868GC_OFFSETVCHRITEM\u7684\u5b57\u6bb5\u201c" + columnCode + "\u201d\u5b9a\u4e49\u5931\u8d25\u3002");
                }
                if (columnModelDefine != null) continue;
                columnModelDefine = this.designDataModelService.createColumnModelDefine();
                columnModelDefine.setCode(columnCode);
                columnModelDefine.setName(columnCode);
                columnModelDefine.setCode(columnCode);
                columnModelDefine.setTitle(columnTitle);
                columnModelDefine.setDesc(columnTitle);
                columnModelDefine.setNullAble(true);
                columnModelDefine.setColumnType(ColumnModelType.BIGDECIMAL);
                columnModelDefine.setPrecision(19);
                columnModelDefine.setDecimal(2);
                columnModelDefine.setTableID(tableModel.getID());
                try {
                    this.designDataModelService.insertColumnModelDefine(columnModelDefine);
                }
                catch (ModelValidateException e) {
                    logger.error("\u5b57\u6bb5\u201c{}\u201d \u63d2\u5165\u6216\u66f4\u65b0\u8868GC_OFFSETVCHRITEM\u5931\u8d25\u3002", (Object)columnCode, (Object)e);
                    throw new BusinessRuntimeException("\u5b57\u6bb5\u201c" + columnCode + "\u201d\u63d2\u5165\u6216\u66f4\u65b0\u8868\u201cGC_OFFSETVCHRITEM\u201d\u5931\u8d25\u3002", (Throwable)e);
                }
                try {
                    this.dataModelDeployService.deployTable(tableModel.getID());
                }
                catch (Exception e) {
                    logger.error("\u7ef4\u5ea6\u53d1\u5e03\u5931\u8d25\uff0c\u8868\u201c{}\u201d \u53d1\u5e03\u5931\u8d25\u3002", (Object)tableModel.getTitle(), (Object)e);
                    throw new BusinessRuntimeException("\u7ef4\u5ea6\u53d1\u5e03\u5931\u8d25\uff0c\u8868\u201c" + tableModel.getTitle() + "\u201d\u53d1\u5e03\u5931\u8d25\u3002" + e.getMessage(), (Throwable)e);
                }
            }
        }
    }
}

