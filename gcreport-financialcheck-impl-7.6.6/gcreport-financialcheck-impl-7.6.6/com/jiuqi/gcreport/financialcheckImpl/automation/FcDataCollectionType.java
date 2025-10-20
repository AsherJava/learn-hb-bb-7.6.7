/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.core.automation.annotation.WriteOperation
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo
 *  com.jiuqi.nvwa.framework.automation.result.ValueResult
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.automation;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcIncrementCollectService;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.core.automation.annotation.WriteOperation;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo;
import com.jiuqi.nvwa.framework.automation.result.ValueResult;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@AutomationType(category="gcreport", id="gcreport-related-data-collection", title="\u589e\u91cf\u6570\u636e\u91c7\u96c6", icon="icon-64-xxx")
@CommonAutomation(path="/\u5173\u8054\u4ea4\u6613/\u589e\u91cf\u6570\u636e\u91c7\u96c6")
public class FcDataCollectionType {
    private Logger logger = LoggerFactory.getLogger(FcDataCollectionType.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private FcIncrementCollectService fcIncrementCollectService;

    @WriteOperation
    @Transactional(rollbackFor={Exception.class})
    public IOperationInvoker<ValueResult> incrementDataCollect() {
        return (instance, context) -> {
            TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM");
            List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModelDefineByCode.getID());
            Map<String, List<ColumnModelDefine>> columnModelDefineGroups = columnModelDefinesByTable.stream().collect(Collectors.groupingBy(ColumnModelDefine::getName));
            try {
                ArrayList fieldInfoList = new ArrayList();
                List fieldNameList = context.getFieldNameList();
                fieldNameList.forEach(fieldName -> {
                    switch (fieldName) {
                        case "DELETEFLAG": {
                            fieldInfoList.add(new Column("DELETEFLAG", 6, "\u5220\u9664\u6807\u8bb0", (Object)new DatasetColumnInfo()));
                            break;
                        }
                        default: {
                            List columnModelDefines = (List)columnModelDefineGroups.get(fieldName);
                            if (CollectionUtils.isEmpty(columnModelDefines)) {
                                throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u6570\u636e\u91c7\u96c6\u81ea\u52a8\u5316\u5bf9\u8c61\u7684\u53c2\u6570\u5df2\u53d1\u751f\u53d8\u5316\uff0c\u8bf7\u91cd\u65b0\u4fdd\u5b58\u81ea\u52a8\u5316\u5bf9\u8c61\u5b9a\u4e49");
                            }
                            ColumnModelDefine column = (ColumnModelDefine)columnModelDefines.get(0);
                            fieldInfoList.add(new Column(column.getName(), column.getColumnType().getValue(), column.getTitle(), (Object)new DatasetColumnInfo()));
                        }
                    }
                });
                MemoryDataSet data = context.getParameterValueAsDataset("data", fieldInfoList);
                List<GcRelatedItemEO> itemsFromContext = this.getItemsFromContext((MemoryDataSet<DatasetColumnInfo>)data);
                this.fcIncrementCollectService.collect(itemsFromContext);
            }
            catch (Exception e) {
                this.logger.error("\u6570\u636e\u91c7\u96c6\u53d1\u751f\u5f02\u5e38", e);
                throw new AutomationExecuteException("\u6570\u636e\u91c7\u96c6\u53d1\u751f\u5f02\u5e38,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage());
            }
            return new ValueResult((Object)"\u589e\u91cf\u6570\u636e\u91c7\u96c6\u7ed3\u675f", AutomationValueResultDataTypeEnum.STRING);
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            AutomationParameter dataSetParameter = new AutomationParameter("data", "\u589e\u91cf\u5206\u5f55\u6570\u636e\u96c6", AutomationParameterDataTypeEnum.FILE, null);
            parameterList.add(dataSetParameter);
            metaInfo.setParameterList(parameterList);
            TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM");
            List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModelDefineByCode.getID());
            ArrayList<AutomationFieldInfo> fieldInfoList = new ArrayList<AutomationFieldInfo>();
            columnModelDefinesByTable.forEach(column -> fieldInfoList.add(new AutomationFieldInfo(column.getName(), column.getTitle(), Integer.valueOf(column.getColumnType().getValue()))));
            fieldInfoList.add(new AutomationFieldInfo("DELETEFLAG", "\u5220\u9664\u6807\u8bb0", Integer.valueOf(6)));
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }

    private List<GcRelatedItemEO> getItemsFromContext(MemoryDataSet<DatasetColumnInfo> data) {
        ArrayList<GcRelatedItemEO> items = new ArrayList<GcRelatedItemEO>();
        Metadata metadata = data.getMetadata();
        Iterator iterator = data.iterator();
        ArrayList itemMaps = new ArrayList();
        while (iterator.hasNext()) {
            DataRow next = (DataRow)iterator.next();
            HashMap<String, Object> currMap = new HashMap<String, Object>();
            for (int i = 0; i < metadata.getColumns().size(); ++i) {
                currMap.put(metadata.getColumn(i).getName(), next.getValue(i));
            }
            itemMaps.add(currMap);
        }
        if (!CollectionUtils.isEmpty(itemMaps)) {
            itemMaps.forEach(item -> {
                try {
                    items.add(this.convertMap2EO((Map<String, Object>)item));
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u589e\u91cf\u6570\u636e\u505a\u6570\u636e\u8f6c\u6362\u65f6\u51fa\u73b0\u5f02\u5e38", (Throwable)e);
                }
            });
        }
        return items;
    }

    private GcRelatedItemEO convertMap2EO(Map<String, Object> itemMap) throws ParseException {
        GcRelatedItemEO item = new GcRelatedItemEO();
        item.resetFields(itemMap);
        item.setSrcItemId(Objects.isNull(itemMap.get("SRCITEMID")) ? null : String.valueOf(itemMap.get("SRCITEMID")));
        item.setAcctYear((Integer)itemMap.get("ACCTYEAR"));
        item.setAcctPeriod((Integer)itemMap.get("ACCTPERIOD"));
        item.setUnitId(Objects.isNull(itemMap.get("UNITID")) ? null : String.valueOf(itemMap.get("UNITID")));
        item.setOppUnitId(Objects.isNull(itemMap.get("OPPUNITID")) ? null : String.valueOf(itemMap.get("OPPUNITID")));
        item.setSubjectCode(Objects.isNull(itemMap.get("SUBJECTCODE")) ? null : String.valueOf(itemMap.get("SUBJECTCODE")));
        item.setOriginalCurr(Objects.isNull(itemMap.get("ORIGINALCURR")) ? null : String.valueOf(itemMap.get("ORIGINALCURR")));
        item.setCurrency(Objects.isNull(itemMap.get("CURRENCY")) ? null : String.valueOf(itemMap.get("CURRENCY")));
        item.setDebitOrig(Objects.isNull(itemMap.get("DEBITORIG")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("DEBITORIG"))));
        item.setCreditOrig(Objects.isNull(itemMap.get("CREDITORIG")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("CREDITORIG"))));
        item.setDebit(Objects.isNull(itemMap.get("DEBIT")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("DEBIT"))));
        item.setCredit(Objects.isNull(itemMap.get("CREDIT")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("CREDIT"))));
        item.setBillCode(Objects.isNull(itemMap.get("BILLCODE")) ? null : String.valueOf(itemMap.get("BILLCODE")));
        item.setCreateDate(Objects.isNull(itemMap.get("CREATEDATE")) ? null : ((GregorianCalendar)itemMap.get("CREATEDATE")).getTime());
        item.setDigest(Objects.isNull(itemMap.get("DIGEST")) ? null : String.valueOf(itemMap.get("DIGEST")));
        item.setGcNumber(Objects.isNull(itemMap.get("GCNUMBER")) ? null : String.valueOf(itemMap.get("GCNUMBER")));
        item.setItemOrder(Objects.isNull(itemMap.get("ITEMORDER")) ? null : String.valueOf(itemMap.get("ITEMORDER")));
        item.setMemo(Objects.isNull(itemMap.get("MEMO")) ? null : String.valueOf(itemMap.get("MEMO")));
        item.setUpdateTime(Objects.isNull(itemMap.get("UPDATETIME")) ? null : String.valueOf(itemMap.get("UPDATETIME")));
        item.setVchrType(Objects.isNull(itemMap.get("VCHRTYPE")) ? null : String.valueOf(itemMap.get("VCHRTYPE")));
        item.setVchrNum(Objects.isNull(itemMap.get("VCHRNUM")) ? null : String.valueOf(itemMap.get("VCHRNUM")));
        item.setVchrSourceType(Objects.isNull(itemMap.get("VCHRSOURCETYPE")) ? null : String.valueOf(itemMap.get("VCHRSOURCETYPE")));
        item.setRealGcNumber(Objects.isNull(itemMap.get("REALGCNUMBER")) ? null : String.valueOf(itemMap.get("REALGCNUMBER")));
        item.setSrcVchrId(Objects.isNull(itemMap.get("SRCVCHRID")) ? null : String.valueOf(itemMap.get("SRCVCHRID")));
        item.addFieldValue("DELETEFLAG", Objects.isNull(itemMap.get("DELETEFLAG")) ? null : String.valueOf(itemMap.get("DELETEFLAG")));
        item.setId(Objects.isNull(itemMap.get("ID")) ? null : String.valueOf(itemMap.get("ID")));
        item.setCfItemCode(Objects.isNull(itemMap.get("CFITEMCODE")) ? null : String.valueOf(itemMap.get("CFITEMCODE")));
        item.setVchrId(Objects.isNull(itemMap.get("VCHRID")) ? null : String.valueOf(itemMap.get("VCHRID")));
        item.setRuleChangeHandlerFlag((Integer)itemMap.get("RULECHANGEHANDLERFLAG"));
        item.setReclassifySubjCode(Objects.isNull(itemMap.get("RECLASSIFYSUBJCODE")) ? null : String.valueOf(itemMap.get("RECLASSIFYSUBJCODE")));
        item.setSrcIteMassId(Objects.isNull(itemMap.get("SRCITEMASSID")) ? null : String.valueOf(itemMap.get("SRCITEMASSID")));
        return item;
    }
}

