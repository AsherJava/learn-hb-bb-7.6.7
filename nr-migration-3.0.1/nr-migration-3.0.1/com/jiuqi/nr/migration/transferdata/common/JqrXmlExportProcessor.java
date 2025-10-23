/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.sql.misc.SXElement
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 */
package com.jiuqi.nr.migration.transferdata.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.sql.misc.SXElement;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.migration.transferdata.bean.CheckErrorDesc;
import com.jiuqi.nr.migration.transferdata.bean.DataSynRange;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.FetchDataParam;
import com.jiuqi.nr.migration.transferdata.bean.JQRMappingCache;
import com.jiuqi.nr.migration.transferdata.bean.TransCheckError;
import com.jiuqi.nr.migration.transferdata.bean.TransDimension;
import com.jiuqi.nr.migration.transferdata.bean.TransMainBody;
import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import com.jiuqi.nr.migration.transferdata.bean.TransZbValue;
import com.jiuqi.nr.migration.transferdata.common.DataTransUtil;
import com.jiuqi.nr.migration.transferdata.dbservice.service.IQueryDataService;
import com.jiuqi.nr.migration.transferdata.log.XmlDataExportLog;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JqrXmlExportProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JqrXmlExportProcessor.class);
    private final IQueryDataService queryDataImpl;
    private final RuntimeViewController runTimeViewController;
    private final FieldRelationFactory fieldRelationFactory;
    private final TaskDefine taskDefine;
    private List<TransDimension> dimensions = new ArrayList<TransDimension>();
    private JQRMappingCache mappingCache;
    private final DataSynRange synRange;
    private Map<String, String> bdPropertyMap = null;
    private final boolean _synStartFlow;
    private final boolean isSynCommitMemo;
    private boolean isSynCheckErrorDes;
    private TransCheckError transCheckError;
    private final List<FormDefine> formDefines;
    private SXElement root;
    private SXElement e_data;
    private SXElement e_general;
    private byte[] xmlData = null;
    private XmlDataExportLog exportLog;

    public JqrXmlExportProcessor(IQueryDataService queryDataExecutorImpl, RuntimeViewController runTimeViewController, FieldRelationFactory fieldRelationFactory, TaskDefine taskDefine, List<FormDefine> formDefines, DataSynRange range, boolean isSynCommitMemo) {
        this.queryDataImpl = queryDataExecutorImpl;
        this.runTimeViewController = runTimeViewController;
        this.fieldRelationFactory = fieldRelationFactory;
        this.taskDefine = taskDefine;
        this.formDefines = formDefines;
        this.synRange = range;
        this.isSynCommitMemo = isSynCommitMemo;
        this._synStartFlow = this.synRange != DataSynRange.dataOnly;
    }

    public void setSynCheckErrorDes(boolean synCheckErrorDes) {
        this.isSynCheckErrorDes = synCheckErrorDes;
    }

    public void setTransCheckErrorDes(TransCheckError transCheckError) {
        this.transCheckError = transCheckError;
    }

    public void setBdPropertyMap(Map<String, String> bdPropertyMap) {
        this.bdPropertyMap = bdPropertyMap;
    }

    public void exportXmlFile() {
        this.createRootElements();
        if (this.synRange != DataSynRange.stateOnly) {
            this.createMainBodyElements();
        }
        if (this.synRange != DataSynRange.dataOnly) {
            try {
                this.createStateElement();
                if (this.isSynCommitMemo) {
                    this.createMemos();
                }
            }
            catch (ParseException e) {
                this.exportLog.addOtherErrorLog(this.exportLog.getCurUnitInfo() + ": \u6d41\u7a0b\u6570\u636e\u6709\u8bef\u3002");
                logger.error(this.exportLog.getCurUnitInfo() + ": \u6d41\u7a0b\u6570\u636e\u6709\u8bef\u3002");
            }
        }
        if (this.isSynCheckErrorDes && this.transCheckError != null) {
            this.createCheckErrorDes();
        }
        if (this.root != null) {
            this.xmlData = this.root.toUTF8();
        }
    }

    private void createCheckErrorDes() {
        SXElement checkErroDesc = SXElement.newElement((String)"checkerroDesc");
        checkErroDesc.setBoolean("checkerroDesc", this.isSynCheckErrorDes);
        SXElement dimensionElement = SXElement.newElement((String)"checkDimension");
        dimensionElement.setString("DATATIME", "DATATIME=\"" + this.transCheckError.getDataTime() + "\"");
        dimensionElement.setString("UNITCODE", this.transCheckError.getUnitCode());
        for (CheckErrorDesc checkErrorDesc : this.transCheckError.getCheckErrorDescList()) {
            SXElement checkErroElement;
            if (StringUtils.hasLength(checkErrorDesc.getFloatId())) {
                checkErroElement = SXElement.newElement((String)"checkErroElement");
                checkErroElement.setString("recid", checkErrorDesc.getRecid().toString());
                checkErroElement.setString("description", checkErrorDesc.getDescription());
                checkErroElement.setDate("modifyTime", new Date().getTime());
                checkErroElement.setString("formulaExp", checkErrorDesc.getFormulaExp());
                checkErroElement.setString("formulaCode", checkErrorDesc.getFormulaCode());
                checkErroElement.setString("floatId", checkErrorDesc.getFloatId());
                dimensionElement.append(checkErroElement);
                continue;
            }
            checkErroElement = SXElement.newElement((String)"checkFloatElement");
            checkErroElement.setString("recid", checkErrorDesc.getRecid().toString());
            checkErroElement.setString("description", checkErrorDesc.getDescription());
            checkErroElement.setDate("modifyTime", new Date().getTime());
            checkErroElement.setString("formulaExp", checkErrorDesc.getFormulaExp());
            checkErroElement.setString("formulaCode", checkErrorDesc.getFormulaCode());
            checkErroElement.setString("floatId", checkErrorDesc.getFloatId());
            checkErroElement.setString("floatCode", checkErrorDesc.getFloatCode());
            dimensionElement.append(checkErroElement);
        }
        checkErroDesc.append(dimensionElement);
        this.e_data.append(checkErroDesc);
    }

    public byte[] getExportData() {
        return this.xmlData;
    }

    public Map<String, byte[]> batchGetManageXml(Map<String, TransDimension> transferDimensions, TransDimension periodDim) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u7ba1\u7406\u53e3\u5f84\u5217\u8868\uff1a");
        SXElement periodSXE = this.getDimSXElement(periodDim);
        HashMap<String, byte[]> res = new HashMap<String, byte[]>();
        for (String management : transferDimensions.keySet()) {
            TransDimension dwDim = transferDimensions.get(management);
            SXElement dwSXE = this.getDimSXElement(dwDim);
            SXElement root = SXElement.newDoc();
            SXElement e_data = SXElement.newElement((String)"data");
            SXElement e_general = SXElement.newElement((String)"general");
            root.append(e_data);
            e_data.append(e_general);
            this.addSolutionElement(e_general, this.mappingCache.getSolutionCodeMapping());
            e_general.append(periodSXE);
            e_general.append(dwSXE);
            this.createOtherProperty(e_general);
            sb.append(dwDim.getValue()).append("_").append(dwDim.getTitle()).append(" ");
            String fileName = dwDim.getValue() + "_" + dwDim.getTitle() + ".xml";
            res.put(fileName, root.toUTF8());
        }
        logger.info("\u7ba1\u7406\u53e3\u5f84\u5217\u8868\uff1a" + sb);
        return res;
    }

    public JQRMappingCache getMappingCache() {
        return this.mappingCache;
    }

    public void setSolutionMapping(String solutionMapping) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setSolutionCodeMapping(solutionMapping);
    }

    public void setPeriodMapping(Map<String, String> periodMapping) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setPeriodMappingMap(periodMapping);
    }

    public void setOrgMapping(Map<String, String> orgMapping) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setOrgMappingMap(orgMapping);
    }

    public void setBDMapping(Map<String, Map<String, String>> bdMapping) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setBaseDataMappingMap(bdMapping);
    }

    public void setZbMapping(Map<String, String> zbMapping) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setZbMappingMap(zbMapping);
    }

    public void setTableNameMapping(String tableNameMapping) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setTableNameMapping(tableNameMapping);
    }

    public void setVersion(String version) {
        if (this.mappingCache == null) {
            this.mappingCache = new JQRMappingCache();
        }
        this.mappingCache.setVerion(version);
    }

    public void setDimensions(TransDimension periodDim, TransDimension dwDim) {
        this.dimensions = new ArrayList<TransDimension>();
        this.dimensions.add(periodDim);
        this.dimensions.add(dwDim);
    }

    public void setParentCode(String parentCode) {
        this.dimensions.get(1).setParentCode(parentCode);
    }

    public void setExportLog(XmlDataExportLog exportLog) {
        this.exportLog = exportLog;
    }

    public void setCurrentExportUnitInfo(String code) {
        this.exportLog.setCurUnitInfo("\u5355\u4f4d\uff1a" + code);
    }

    private SXElement getDimSXElement(TransDimension periodDim) {
        SXElement dim = SXElement.newElement((String)"dim");
        dim.setAttribute("name", periodDim.getName());
        dim.setInt("type", periodDim.getType());
        dim.setAttribute("value", periodDim.getValue());
        if (periodDim.getType() != 2) {
            dim.setString("tableName", periodDim.getTableName());
        }
        return dim;
    }

    private void createRootElements() {
        this.root = SXElement.newDoc();
        this.e_data = SXElement.newElement((String)"data");
        this.e_general = SXElement.newElement((String)"general");
        this.root.append(this.e_data);
        this.e_data.append(this.e_general);
        this.addSolutionElement(this.e_general, this.mappingCache.getSolutionCodeMapping());
        this.addDimElement();
        this.createOtherProperty(this.e_general);
    }

    private void addSolutionElement(SXElement e_general, String taskName) {
        SXElement e_solution = SXElement.newElement((String)"solution");
        e_solution.setString("name", taskName);
        e_general.append(e_solution);
    }

    private void addDimElement() {
        for (TransDimension transferDimension : this.dimensions) {
            SXElement dim = SXElement.newElement((String)"dim");
            dim.setAttribute("name", transferDimension.getName());
            dim.setInt("type", transferDimension.getType());
            if (transferDimension.getType() != 2) {
                Map<String, String> baseDataItemMapping;
                String itemValue = this.taskDefine.getDw().endsWith("@BASE") ? (this.mappingCache.getBaseDataMappingMap() == null ? transferDimension.getValue() : ((baseDataItemMapping = this.mappingCache.getBaseDataMappingMap().get(this.taskDefine.getDw())) != null ? baseDataItemMapping.getOrDefault(transferDimension.getValue(), transferDimension.getValue()) : transferDimension.getValue())) : (this.mappingCache.getOrgMappingMap() == null ? transferDimension.getValue() : this.mappingCache.getOrgMappingMap().getOrDefault(transferDimension.getValue(), transferDimension.getValue()));
                dim.setAttribute("value", itemValue);
                dim.setString("tableName", this.mappingCache.getTableNameMapping());
                if (transferDimension.isCreateXM()) {
                    dim.setBoolean("create", true);
                    dim.setString("title", transferDimension.getTitle());
                    if (StringUtils.hasLength(transferDimension.getManagement())) {
                        dim.setString("management", transferDimension.getManagement());
                    } else {
                        dim.setString("parent", transferDimension.getParentCode());
                    }
                    if (this.bdPropertyMap != null && this.bdPropertyMap.size() > 0) {
                        SXElement e_bd = SXElement.newElement((String)"basedataproperty");
                        for (String key : this.bdPropertyMap.keySet()) {
                            e_bd.setAttribute(key, this.bdPropertyMap.get(key));
                        }
                        dim.append(e_bd);
                    }
                }
            } else {
                Map<String, String> periodMappingMap = this.mappingCache.getPeriodMappingMap();
                String periodV = periodMappingMap == null ? transferDimension.getValue() : periodMappingMap.getOrDefault(transferDimension.getValue(), transferDimension.getValue());
                dim.setAttribute("value", periodV);
                dim.setAttribute("name", transferDimension.getName());
            }
            this.e_general.append(dim);
        }
    }

    private void createOtherProperty(SXElement e_general) {
        SXElement e_property = SXElement.newElement((String)"otherproperty");
        if (this._synStartFlow) {
            e_property.setBoolean("startflow", true);
        }
        if (e_property.getAttrCount() > 0) {
            e_general.append(e_property);
        }
    }

    private void createMainBodyElements() {
        for (int i = 0; i < this.formDefines.size(); ++i) {
            FormDefine formDefine = this.formDefines.get(i);
            try {
                if (formDefine.getFormType().getValue() == FormType.FORM_TYPE_FMDM.getValue()) {
                    logger.info("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u8868 {} \u7684\u6570\u636e.", (Object)formDefine.getFormCode());
                    TransMainBody fmdaXmlMb = this.queryDataImpl.queryFMDMData(this.buildFetchParam(formDefine));
                    fmdaXmlMb.setFormTitle(formDefine.getTitle());
                    this.createMbElement(fmdaXmlMb);
                } else {
                    logger.info("\u67e5\u8be2\u62a5\u8868 {} \u7684\u6570\u636e.", (Object)formDefine.getFormCode());
                    List<TransMainBody> mbNodes = this.getXmlMbInCommonForm(formDefine, i);
                    this.createMbElements(mbNodes);
                }
                this.exportLog.addSuccessReportLog("\u62a5\u8868\uff1a" + formDefine.getFormCode() + "\u53d6\u6570\u65e0\u5f02\u5e38\u3002");
                continue;
            }
            catch (RuntimeException e) {
                logger.error(e.getMessage());
                this.exportLog.addFailureReportLog("\u62a5\u8868\uff1a" + formDefine.getFormCode() + "\u53d6\u6570\u6709\u5f02\u5e38\u3002");
            }
        }
    }

    private FetchDataParam buildFetchParam(FormDefine formDefine) {
        FetchDataParam param = new FetchDataParam();
        param.setTaskKey(this.taskDefine.getKey());
        if (formDefine == null && this.formDefines.size() > 0) {
            formDefine = this.formDefines.get(0);
        }
        if (formDefine != null) {
            param.setFormKey(formDefine.getKey());
            param.setFormSchemeKey(formDefine.getFormScheme());
        }
        ArrayList<DimInfo> dimInfos = new ArrayList<DimInfo>();
        for (TransDimension dimension : this.dimensions) {
            DimInfo e = new DimInfo(dimension);
            dimInfos.add(e);
        }
        param.setDimInfos(dimInfos);
        return param;
    }

    private List<TransMainBody> getXmlMbInCommonForm(FormDefine formDefine, int mbStartIdx) {
        ArrayList<TransMainBody> elementMbs = new ArrayList<TransMainBody>();
        List regions = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
        for (int i = 0; i < regions.size(); ++i) {
            TransMainBody mb = new TransMainBody();
            mb.setIdx(mbStartIdx + i);
            mb.setFormTitle(formDefine.getTitle());
            DataRegionDefine region = (DataRegionDefine)regions.get(i);
            boolean isFloat = region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST || region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST;
            mb.setFloat(isFloat);
            List dataFieldKeys = this.runTimeViewController.getFieldKeysInRegion(region.getKey());
            if (dataFieldKeys.size() == 0) continue;
            FetchDataParam param = this.buildFetchParam(formDefine);
            param.setDataFieldKeys(dataFieldKeys);
            List<IRowData> iRowData = this.queryDataImpl.batchQueryReportData(param);
            if (iRowData.size() == 0) {
                logger.warn("\u62a5\u8868\uff1a{}\uff0c\u533a\u57df\uff1a{} \u7684\u6307\u6807\u6570\u636e\u4e3a\u7a7a\u3002", (Object)formDefine.getFormCode(), (Object)region.getCode());
            }
            this.createMbRegion(mb, iRowData);
            elementMbs.add(mb);
        }
        return elementMbs;
    }

    private void setZbCodeListToMb(TransMainBody mb, List<String> dataFieldKeys, Map<String, String> zbMapping) {
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation(dataFieldKeys.listIterator());
        List metaData = fieldRelation.getMetaData();
        List dataFieldCodes = metaData.stream().map(imd -> imd.getDataField().getCode()).collect(Collectors.toList());
        ArrayList<String> zbCodeList = new ArrayList<String>();
        for (String zbCode : dataFieldCodes) {
            String mappingZbCode = zbMapping != null ? zbMapping.getOrDefault(zbCode, zbCode) : zbCode;
            zbCodeList.add(mappingZbCode);
        }
        mb.setZbCodes(zbCodeList);
    }

    private TransMainBody createMbRegion(TransMainBody mb, List<IRowData> rowDataList) {
        if (rowDataList.size() > 0) {
            ArrayList<String> zbCodeList = new ArrayList<String>();
            Map<String, String> zbMapping = this.mappingCache.getZbMappingMap();
            for (int i = 0; i < rowDataList.size(); ++i) {
                IRowData rowData = rowDataList.get(i);
                List linkDataValues = rowData.getLinkDataValues();
                ArrayList<TransZbValue> fieldValues = new ArrayList<TransZbValue>();
                int zbIdx = 0;
                for (IDataValue iDataValue : linkDataValues) {
                    String mappingZbCode;
                    DataLinkDefine dataLinkDefine;
                    String zbCode = iDataValue.getMetaData().getCode();
                    if (mb.isFloat() && "FLOATORDER".equals(zbCode)) continue;
                    if (!StringUtils.hasLength(zbCode) && DataLinkType.DATA_LINK_TYPE_FORMULA == (dataLinkDefine = iDataValue.getMetaData().getDataLinkDefine()).getType()) {
                        zbCode = dataLinkDefine.getLinkExpression();
                    }
                    String string = mappingZbCode = zbMapping != null ? zbMapping.getOrDefault(zbCode, zbCode) : zbCode;
                    if (i == 0) {
                        zbCodeList.add(mappingZbCode);
                    }
                    TransZbValue fieldValue = new TransZbValue();
                    fieldValue.setZbCode(mappingZbCode);
                    fieldValue.setZbType(DataTransUtil.getJqrZbType(iDataValue.getMetaData().getDataType()));
                    fieldValue.setValue(DataTransUtil.getFieldValue(iDataValue));
                    fieldValue.setIdx(zbIdx++);
                    fieldValues.add(fieldValue);
                }
                mb.addFieldValues(fieldValues);
            }
            mb.setZbCodes(zbCodeList);
        }
        return mb;
    }

    private void createMbElements(List<TransMainBody> mbNodes) {
        if (this.e_data != null && mbNodes != null && mbNodes.size() > 0) {
            for (TransMainBody mbNode : mbNodes) {
                this.createMbElement(mbNode);
            }
        }
    }

    private void createMbElement(TransMainBody mbNode) {
        SXElement mb = SXElement.newElement((String)"mb");
        this.e_data.append(mb);
        mb.setInt("idx", mbNode.getIdx());
        mb.setBoolean("isfloat", mbNode.isFloat());
        mb.setAttribute("reporttitle", mbNode.getFormTitle());
        if (mbNode.getRows().size() > 0) {
            SXElement zbSet = SXElement.newElement((String)"zbset");
            mb.append(zbSet);
            this.createZbSetElements(zbSet, mbNode.getZbCodes());
            SXElement rows = SXElement.newElement((String)"rows");
            mb.append(rows);
            this.createRowsElements(rows, mbNode);
        }
    }

    private void createZbSetElements(SXElement zbSet, List<String> zbs) {
        for (int i = 0; i < zbs.size(); ++i) {
            SXElement zbDefine = SXElement.newElement((String)"zb");
            zbDefine.setAttribute("name", zbs.get(i));
            zbDefine.setInt("idx", i);
            zbSet.append(zbDefine);
        }
    }

    private void createRowsElements(SXElement rows, TransMainBody mbNode) {
        if (mbNode.isFloat()) {
            for (int rowIndex = 0; rowIndex < mbNode.getRows().size(); ++rowIndex) {
                SXElement row = SXElement.newElement((String)"row");
                row.setDouble("order", (double)rowIndex);
                rows.append(row);
                List<TransZbValue> floatRegionRowData = mbNode.getFieldValues(rowIndex);
                this.createZbValueElements(floatRegionRowData, row);
            }
        } else {
            SXElement row = SXElement.newElement((String)"row");
            row.setDouble("order", -1.0);
            rows.append(row);
            List<TransZbValue> fixRegionRowData = mbNode.getFieldValues(0);
            this.createZbValueElements(fixRegionRowData, row);
        }
    }

    private void createZbValueElements(List<TransZbValue> rowData, SXElement row) {
        for (TransZbValue fieldValue : rowData) {
            SXElement zbValue = SXElement.newElement((String)"zbvalue");
            zbValue.setInt("idx", fieldValue.getIdx());
            zbValue.setInt("type", fieldValue.getZbType());
            if (fieldValue.getValue() != null && !"".equals(fieldValue.getValue())) {
                zbValue.setAttribute("value", String.valueOf(fieldValue.getValue()));
            }
            row.append(zbValue);
        }
    }

    private void createStateElement() throws ParseException {
        logger.info("\u4e0a\u62a5\u72b6\u6001\u66f4\u65b0\uff1a");
        SXElement ds = SXElement.newElement((String)"datastate");
        String formSchemeKey = this.formDefines.get(0).getFormScheme();
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet();
        int state = this.queryDataImpl.queryDataState(formSchemeKey, dimensionValueSet);
        ds.setInt("state", state);
        this.e_data.append(ds);
    }

    private void createMemos() throws ParseException {
        logger.info("\u5bfc\u51fa\u4e0a\u62a5\u8bf4\u660e\uff1a");
        List<TransMemo> memos1 = this.getMemos();
        SXElement memos = SXElement.newElement((String)"memos");
        for (TransMemo memo : memos1) {
            SXElement xml_memo = SXElement.newElement((String)"memo");
            if (memo.getRpCode() != null && !"".equals(memo.getRpCode())) {
                xml_memo.setAttribute("rp", memo.getRpCode());
            }
            xml_memo.setAttribute("time", memo.getTime());
            xml_memo.setAttribute("user", memo.getUser());
            xml_memo.setAttribute("state", memo.getState());
            xml_memo.setAttribute("operatecode", memo.getOperateCode());
            xml_memo.setAttribute("status", memo.getStatus());
            xml_memo.setAttribute("content", memo.getContent());
            memos.append(xml_memo);
        }
        logger.info("\u6d41\u7a0b\u6570\u636e\u67e5\u8be2\u7ed3\u675f\u3002");
        this.e_data.append(memos);
    }

    private List<TransMemo> getMemos() throws ParseException {
        TaskFlowsDefine flowsSetting = this.taskDefine.getFlowsSetting();
        if (flowsSetting == null || FlowsType.NOSTARTUP.equals((Object)flowsSetting.getFlowsType())) {
            return new ArrayList<TransMemo>();
        }
        WorkFlowType wordFlowType = flowsSetting.getWordFlowType();
        String formSchemeKey = this.formDefines.get(0).getFormScheme();
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet();
        if (wordFlowType == WorkFlowType.ENTITY) {
            return this.queryDataImpl.queryHistoryMemos(formSchemeKey, dimensionValueSet, flowsSetting.isUnitSubmitForForce());
        }
        return new ArrayList<TransMemo>();
    }

    private DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (TransDimension dimension : this.dimensions) {
            if (!"DATATIME".equals(dimension.getName())) {
                dimensionValueSet.setValue(dimension.getEntityId().endsWith("@BASE") ? dimension.getEntityId().split("@")[0] : "MD_ORG", (Object)dimension.getValue());
                continue;
            }
            dimensionValueSet.setValue(dimension.getName(), (Object)dimension.getValue());
        }
        return dimensionValueSet;
    }

    public String getVersion() {
        return this.mappingCache.getVerion();
    }
}

