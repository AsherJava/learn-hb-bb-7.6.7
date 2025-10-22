/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.calculate.formula.service.GcFormulaEvalService
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.util.DataFieldUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO
 *  com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.multicriteria.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.calculate.formula.service.GcFormulaEvalService;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.util.DataFieldUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterAmtDao;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterFormDao;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterZbDao;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriBeforeZbDao;
import com.jiuqi.gcreport.multicriteria.dao.GcMultiCriteriaDao;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterAmtEO;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterFormEO;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterZbEO;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriBeforeZbEO;
import com.jiuqi.gcreport.multicriteria.entity.GcMultiCriteriaEO;
import com.jiuqi.gcreport.multicriteria.enums.MulCriShowTypeEnum;
import com.jiuqi.gcreport.multicriteria.enums.MulCriTypeEnum;
import com.jiuqi.gcreport.multicriteria.service.GcMultiCriteriaService;
import com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcMultiCriteriaServiceImpl
implements GcMultiCriteriaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GcMulCriAfterFormDao mulCriAfterFormDao;
    @Autowired
    private GcMultiCriteriaDao multiCriteriaDao;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionDesignTimeController;
    @Autowired
    private GcMulCriBeforeZbDao mulCriBeforeZbDao;
    @Autowired
    private GcMulCriAfterZbDao mulCriAfterZbDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    private GcFormulaEvalService formulaEvalService;
    @Autowired
    private GcMulCriAfterAmtDao mulCriAfterAmtDao;
    @Autowired
    private GCFormTabSelectService formTabSelectService;

    @Override
    public FormTree queryFormTree(String schemeId) {
        try {
            return this.formTabSelectService.getFormTree(schemeId, null);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u51fa\u9519", (Throwable)e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveAfterForm(GcMulCriAfterFormVO mulCriAfterFormVO) {
        List<GcMulCriAfterFormEO> originAfterForms = this.mulCriAfterFormDao.queryMulCriAfterForms(mulCriAfterFormVO.getTaskId(), mulCriAfterFormVO.getSchemeId());
        List originFormKeys = originAfterForms.stream().map(eo -> eo.getFormKey()).collect(Collectors.toList());
        List formKeys = mulCriAfterFormVO.getFormKeys() == null ? new ArrayList() : mulCriAfterFormVO.getFormKeys();
        List<String> deleteFormKeys = originFormKeys.stream().filter(formkey -> !formKeys.contains(formkey)).collect(Collectors.toList());
        List<String> newFormKeys = formKeys.stream().filter(formKey -> !originFormKeys.contains(formKey)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteFormKeys) || !CollectionUtils.isEmpty(newFormKeys)) {
            ArrayList<String> deleteMcids = new ArrayList<String>();
            Set<String> afterMcids = this.mulCriAfterZbDao.queryMulCriAfterDataByFormKeys(mulCriAfterFormVO.getSchemeId(), deleteFormKeys);
            deleteMcids.addAll(afterMcids);
            Set<String> beforeMcids = this.mulCriBeforeZbDao.queryMulCriBeforeMcidsByFormKeys(mulCriAfterFormVO.getSchemeId(), newFormKeys);
            deleteMcids.addAll(beforeMcids);
            if (!CollectionUtils.isEmpty(deleteMcids)) {
                this.mulCriAfterZbDao.deleteMulCriAfterZb(deleteMcids);
                this.mulCriBeforeZbDao.deleteMulCriBeforeZb(deleteMcids);
                this.multiCriteriaDao.deleteSubjectMapping(deleteMcids);
            }
        }
        this.mulCriAfterFormDao.deleteMulCriAfterForms(mulCriAfterFormVO.getTaskId(), mulCriAfterFormVO.getSchemeId());
        ArrayList<GcMulCriAfterFormEO> mulCriAfterFormEOList = new ArrayList<GcMulCriAfterFormEO>();
        if (mulCriAfterFormVO.getFormKeys() != null && mulCriAfterFormVO.getFormKeys().size() > 0) {
            for (String formKey2 : mulCriAfterFormVO.getFormKeys()) {
                GcMulCriAfterFormEO eo2 = new GcMulCriAfterFormEO();
                eo2.setId(UUIDOrderUtils.newUUIDStr());
                eo2.setTaskId(mulCriAfterFormVO.getTaskId());
                eo2.setSchemeId(mulCriAfterFormVO.getSchemeId());
                eo2.setFormKey(formKey2);
                mulCriAfterFormEOList.add(eo2);
            }
            this.mulCriAfterFormDao.addBatch(mulCriAfterFormEOList);
        }
    }

    @Override
    public List<String> queryMulCriAfterForms(String taskId, String schemeId) {
        List<GcMulCriAfterFormEO> eos = this.mulCriAfterFormDao.queryMulCriAfterForms(taskId, schemeId);
        List<String> formKeys = eos.stream().map(GcMulCriAfterFormEO::getFormKey).collect(Collectors.toList());
        return formKeys;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveSubjectMapping(List<GcMultiCriteriaVO> multiCriterias) {
        ArrayList<GcMultiCriteriaEO> insertEos = new ArrayList<GcMultiCriteriaEO>();
        ArrayList<GcMultiCriteriaEO> updateEos = new ArrayList<GcMultiCriteriaEO>();
        for (GcMultiCriteriaVO multiCriteria : multiCriterias) {
            GcMultiCriteriaEO eo = new GcMultiCriteriaEO();
            BeanUtils.copyProperties(multiCriteria, (Object)eo);
            eo.setKind(this.checkMulCriType(multiCriteria));
            if (StringUtils.isEmpty((String)multiCriteria.getId())) {
                eo.setId(UUIDOrderUtils.newUUIDStr());
                eo.setCreateTime(new Date());
                insertEos.add(eo);
                continue;
            }
            eo.setModifyTime(new Date());
            updateEos.add(eo);
        }
        this.updateMultiCriteriaZbEos(insertEos, updateEos);
        this.multiCriteriaDao.addBatch(insertEos);
        this.multiCriteriaDao.updateBatch(updateEos);
    }

    private void updateMultiCriteriaZbEos(List<GcMultiCriteriaEO> insertEos, List<GcMultiCriteriaEO> updateEos) {
        List<GcMultiCriteriaEO> allEos = Stream.concat(insertEos.stream(), updateEos.stream()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allEos)) {
            return;
        }
        List<String> mcids = updateEos.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        this.mulCriBeforeZbDao.deleteMulCriBeforeZb(mcids);
        this.mulCriAfterZbDao.deleteMulCriAfterZb(mcids);
        List beforeZbEOS = (List)this.getMulCriZbDetails(allEos, true);
        List afterZbEOS = (List)this.getMulCriZbDetails(allEos, false);
        this.mulCriBeforeZbDao.addBatch(beforeZbEOS);
        this.mulCriAfterZbDao.addBatch(afterZbEOS);
    }

    private Object getMulCriZbDetails(List<GcMultiCriteriaEO> allEos, boolean isBefore) {
        ArrayList<DefaultTableEntity> zbDetails = new ArrayList<DefaultTableEntity>();
        for (GcMultiCriteriaEO eo : allEos) {
            String zbJson;
            String string = zbJson = isBefore ? eo.getBeforeZbJson() : eo.getAfterZbJson();
            if (isBefore && eo.getHasFormula() == 1) {
                zbDetails.addAll(this.parseFormulaToZbCode(eo.getBeforeZbCodes(), eo));
                continue;
            }
            HashMap<String, Map<String, String>> taskAndSchemeIdMap = new HashMap<String, Map<String, String>>();
            Map<String, List<String>> form2FieldKeys = this.parseFormKeyAndZbs(zbJson, isBefore, taskAndSchemeIdMap);
            for (Map.Entry<String, List<String>> entry : form2FieldKeys.entrySet()) {
                Map form2TaskAndSchemeMap = (Map)taskAndSchemeIdMap.get(entry.getKey());
                for (String fieldKey : entry.getValue()) {
                    if (isBefore) {
                        GcMulCriBeforeZbEO beforeZbEO = new GcMulCriBeforeZbEO();
                        beforeZbEO.setId(UUIDOrderUtils.newUUIDStr());
                        beforeZbEO.setTaskId((String)form2TaskAndSchemeMap.get("taskId"));
                        beforeZbEO.setSchemeId((String)form2TaskAndSchemeMap.get("schemeId"));
                        beforeZbEO.setMcid(eo.getId());
                        beforeZbEO.setAfterZbTitles(this.getZbShowText(eo.getAfterZbTitles(), eo.getAfterZbCodes()));
                        beforeZbEO.setBeforeFormKey(entry.getKey());
                        beforeZbEO.setBeforeZbKey(fieldKey);
                        beforeZbEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
                        zbDetails.add(beforeZbEO);
                        continue;
                    }
                    GcMulCriAfterZbEO afterZbEO = new GcMulCriAfterZbEO();
                    afterZbEO.setId(UUIDOrderUtils.newUUIDStr());
                    afterZbEO.setTaskId(eo.getTaskId());
                    afterZbEO.setSchemeId(eo.getSchemeId());
                    afterZbEO.setMcid(eo.getId());
                    afterZbEO.setBeforeZbTitles(eo.getBeforeZbCodes());
                    if (eo.getHasFormula() == 0) {
                        afterZbEO.setBeforeZbTitles(this.getZbShowText(eo.getBeforeZbTitles(), eo.getBeforeZbCodes()));
                    }
                    afterZbEO.setAfterFormKey(entry.getKey());
                    afterZbEO.setAfterZbKey(fieldKey);
                    afterZbEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
                    zbDetails.add(afterZbEO);
                }
            }
        }
        return zbDetails;
    }

    private String getZbShowText(String zbTitles, String zbCodes) {
        if (StringUtils.isEmpty((String)zbCodes) || StringUtils.isEmpty((String)zbTitles)) {
            return zbTitles;
        }
        List<String> zbTitleList = Arrays.asList(zbTitles.split(","));
        zbTitleList = zbTitleList.stream().filter(zb -> !StringUtils.isEmpty((String)zb)).collect(Collectors.toList());
        List<String> zbCodeList = Arrays.asList(zbCodes.split(","));
        if (CollectionUtils.isEmpty(zbCodeList = zbCodeList.stream().filter(zb -> !StringUtils.isEmpty((String)zb)).collect(Collectors.toList())) || CollectionUtils.isEmpty(zbTitleList) || zbCodeList.size() != zbTitleList.size()) {
            return zbTitles;
        }
        StringBuilder zbShowText = new StringBuilder();
        for (int i = 0; i < zbCodeList.size(); ++i) {
            zbShowText.append(zbTitleList.get(i));
            if (i == zbCodeList.size() - 1) continue;
            zbShowText.append(",");
        }
        return zbShowText.toString();
    }

    private List<GcMulCriBeforeZbEO> parseFormulaToZbCode(String formula, GcMultiCriteriaEO eo) {
        if (StringUtils.isEmpty((String)formula)) {
            throw new BusinessRuntimeException("\u6307\u6807\u683c\u5f0f\u4e0d\u6b63\u786e\uff1a\u516c\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List<String> zbCodes = this.parseFormulaToZbCodes(formula);
        String regex = "(.+)\\[(.+)\\]";
        ArrayList<GcMulCriBeforeZbEO> beforeZbDetails = new ArrayList<GcMulCriBeforeZbEO>();
        for (String zbCode : zbCodes) {
            if (!zbCode.matches(regex)) {
                throw new BusinessRuntimeException("\u516c\u5f0f\u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + formula + "\u3002\u53c2\u8003\u683c\u5f0f\uff1a-A[a]+B[b]-C[c]");
            }
            GcMulCriBeforeZbEO beforeZbEO = new GcMulCriBeforeZbEO();
            beforeZbEO.setId(UUIDOrderUtils.newUUIDStr());
            beforeZbEO.setTaskId(eo.getTaskId());
            beforeZbEO.setSchemeId(eo.getSchemeId());
            beforeZbEO.setMcid(eo.getId());
            beforeZbEO.setAfterZbTitles("\u516c\u5f0f\u4e2d\u6307\u6807\uff0c\u4e0d\u663e\u793a\u3002");
            beforeZbEO.setBeforeFormKey("00000000-0000-0000-0000-000000000000");
            try {
                FieldDefine fieldDefine = this.initDataQuery(zbCode);
                if (fieldDefine == null) {
                    throw new BusinessRuntimeException("\u4fdd\u5b58\u5931\u8d25\uff1a\u6307\u6807" + zbCode + "\u4e0d\u5b58\u5728\u3002");
                }
                beforeZbEO.setBeforeZbKey(fieldDefine.getKey());
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                throw new BusinessRuntimeException(e.getMessage());
            }
            beforeZbEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
            beforeZbDetails.add(beforeZbEO);
        }
        return beforeZbDetails;
    }

    private List<String> parseFormulaToZbCodes(String formula) {
        formula = formula.trim().replace('+', ';').replace('-', ';');
        List<String> zbCodes = Arrays.asList(formula.split(";"));
        zbCodes = zbCodes.stream().filter(zbCode -> !StringUtils.isEmpty((String)zbCode)).collect(Collectors.toList());
        return zbCodes;
    }

    private Map<String, List<String>> parseFormKeyAndZbs(String zbJson, boolean isBefore, Map<String, Map<String, String>> taskAndSchemeIdMap) {
        JsonNode jsonNode = JsonUtils.readTree((String)zbJson);
        HashMap<String, List<String>> form2FieldKeys = new HashMap<String, List<String>>();
        for (JsonNode zb : jsonNode) {
            String formKey = zb.get("formKey").asText();
            HashMap<String, String> form2TaskAndSchemeMap = new HashMap<String, String>();
            if (isBefore) {
                String taskId = zb.get("taskId").asText();
                form2TaskAndSchemeMap.put("taskId", taskId);
                String schemeId = zb.get("schemeId").asText();
                form2TaskAndSchemeMap.put("schemeId", schemeId);
            }
            taskAndSchemeIdMap.put(formKey, form2TaskAndSchemeMap);
            JsonNode zbJsonNode = zb.get("zbs");
            ArrayList<String> zbs = new ArrayList<String>();
            for (JsonNode node : zbJsonNode) {
                String zbCode = node.asText();
                try {
                    FieldDefine fieldDefine = this.initDataQuery(zbCode);
                    zbs.add(fieldDefine.getKey());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    this.logger.error(e.getMessage(), e);
                }
            }
            form2FieldKeys.put(formKey, zbs);
        }
        return form2FieldKeys;
    }

    private String checkMulCriType(GcMultiCriteriaVO multiCriteria) {
        String afterZbCodes = multiCriteria.getAfterZbCodes();
        if (StringUtils.isEmpty((String)afterZbCodes)) {
            return "";
        }
        String[] afterZbCodeArr = afterZbCodes.split(",");
        if (afterZbCodeArr.length > 1) {
            return MulCriTypeEnum.MULTI_CRITERIA_MANUAL.getCode();
        }
        return MulCriTypeEnum.MULTI_CRITERIA_AUTO.getCode();
    }

    @Override
    public List<GcMultiCriteriaVO> querySubjectMapping(GcMultiCriteriaConditionVO condition) {
        List<GcMultiCriteriaEO> eos = this.queryMultiCriterias(condition);
        List<String> zbCodes = this.getZbCodes(eos);
        List<GcMultiCriteriaVO> vos = eos.stream().map(eo -> {
            GcMultiCriteriaVO vo = new GcMultiCriteriaVO();
            BeanUtils.copyProperties(eo, vo);
            return vo;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(vos)) {
            vos = new ArrayList();
        }
        for (String zbCode : condition.getZbs()) {
            GcMultiCriteriaVO vo;
            if (zbCodes.size() != 0 && zbCodes.contains(zbCode) || (vo = this.getGcMultiCriteriaVO(condition, zbCode)) == null) continue;
            vo.setHasFormula(Integer.valueOf(0));
            vos.add(vo);
        }
        return vos;
    }

    private List<GcMultiCriteriaEO> queryMultiCriterias(GcMultiCriteriaConditionVO condition) {
        HashSet<String> fieldKeys = new HashSet<String>();
        for (String zbCode : condition.getZbs()) {
            try {
                FieldDefine fieldDefine = this.initDataQuery(zbCode);
                if (fieldDefine == null) continue;
                fieldKeys.add(fieldDefine.getKey());
            }
            catch (Exception e) {
                this.logger.error("\u6307\u6807" + zbCode + "\u4e0d\u5b58\u5728" + e.getMessage(), e);
            }
        }
        Set<String> mcids = condition.getBeforeReport() != false ? this.mulCriBeforeZbDao.queryMcidsByBeforeZbKeys(condition.getSchemeId(), fieldKeys) : this.mulCriAfterZbDao.queryMcidsByAfterZbKeys(condition.getSchemeId(), fieldKeys);
        return this.multiCriteriaDao.querySubjectMappingByIds(mcids);
    }

    private GcMultiCriteriaVO getGcMultiCriteriaVO(GcMultiCriteriaConditionVO condition, String zbCode) {
        GcMultiCriteriaVO vo = new GcMultiCriteriaVO();
        vo.setTaskId(condition.getTaskId());
        vo.setSchemeId(condition.getSchemeId());
        String zbTitle = this.getZbTitle(zbCode);
        if (null == zbTitle || "".equals(zbTitle)) {
            return null;
        }
        zbTitle = zbTitle.concat("(").concat(zbCode).concat(")");
        if (condition.getBeforeReport().booleanValue()) {
            vo.setBeforeZbCodes(zbCode);
            vo.setBeforeZbTitles(zbTitle);
        } else {
            vo.setAfterZbCodes(zbCode);
            vo.setAfterZbTitles(zbTitle);
            vo.setKind(MulCriTypeEnum.MULTI_CRITERIA_AUTO.getCode());
        }
        return vo;
    }

    private List<String> getZbCodes(List<GcMultiCriteriaEO> eos) {
        ArrayList<String> zbCodes = new ArrayList<String>();
        for (GcMultiCriteriaEO eo : eos) {
            if (eo.getHasFormula() == 1) {
                zbCodes.addAll(this.parseFormulaToZbCodes(eo.getBeforeZbCodes()));
            } else {
                String beforeCodes = eo.getBeforeZbCodes();
                zbCodes.addAll(Arrays.asList(beforeCodes.split(",")));
            }
            String afterCodes = eo.getAfterZbCodes();
            zbCodes.addAll(Arrays.asList(afterCodes.split(",")));
        }
        return zbCodes;
    }

    private String getZbTitle(String zbCode) {
        FieldDefine fieldDefine;
        try {
            fieldDefine = this.initDataQuery(zbCode);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6839\u636e\u6307\u6807id\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5931\u8d25", (Throwable)e);
        }
        if (fieldDefine == null) {
            return null;
        }
        return fieldDefine.getTitle();
    }

    private FieldDefine initDataQuery(String tableZbCode) throws Exception {
        TableDefine tabldefine;
        String regex = "(.+)\\[(.+)\\]";
        if (!tableZbCode.matches(regex)) {
            throw new BusinessRuntimeException("\u6307\u6807\u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + tableZbCode + "\u3002\u53c2\u8003\u683c\u5f0f\uff1a\u8868\u540d[\u6307\u6807\u4ee3\u7801]");
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tableZbCode);
        String tableName = "";
        String zbCode = "";
        if (matcher.find()) {
            tableName = matcher.group(1).trim();
            zbCode = matcher.group(2).trim();
        }
        if ((tabldefine = this.dataDefinitionDesignTimeController.queryTableDefineByCode(tableName)) == null) {
            throw new BusinessRuntimeException("\u6307\u6807" + tableZbCode + "\u5728\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e2d\u4e0d\u5b58\u5728");
        }
        return this.dataDefinitionDesignTimeController.queryFieldByCodeInTable(zbCode, tabldefine.getKey());
    }

    @Override
    public String queryZbTitlesByCode(List<String> zbCodes) {
        if (zbCodes == null) {
            return "";
        }
        if (zbCodes.size() == 0) {
            return "";
        }
        StringBuilder zbTitles = new StringBuilder();
        for (int i = 0; i < zbCodes.size(); ++i) {
            if (StringUtils.isEmpty((String)zbCodes.get(i))) continue;
            zbTitles.append(this.getZbTitle(zbCodes.get(i)));
            zbTitles.append("(").append(zbCodes.get(i)).append(")");
            if (i == zbCodes.size() - 1) continue;
            zbTitles.append(",");
        }
        return zbTitles.toString();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSubjectMapping(List<String> ids) {
        this.multiCriteriaDao.deleteSubjectMapping(ids);
        this.mulCriBeforeZbDao.deleteMulCriBeforeZb(ids);
        this.mulCriAfterZbDao.deleteMulCriAfterZb(ids);
    }

    @Override
    public List<GcMultiCriteriaZbDataVO> queryZbData(GcMultiCriteriaConditionVO condition) {
        List<Object> zbDatas = new ArrayList<GcMultiCriteriaZbDataVO>();
        List<GcMulCriAfterZbEO> afterZbDetails = this.listAafterZbDetails(condition);
        Set<String> mcids = afterZbDetails.stream().map(GcMulCriAfterZbEO::getMcid).collect(Collectors.toSet());
        List<GcMultiCriteriaEO> multiCriteriaEos = this.multiCriteriaDao.querySubjectMappingByIds(mcids);
        if (MulCriShowTypeEnum.MANUAL_ADJUST.getCode().equals(condition.getShowType())) {
            multiCriteriaEos = multiCriteriaEos.stream().filter(eo -> MulCriTypeEnum.MULTI_CRITERIA_MANUAL.getCode().equals(eo.getKind())).collect(Collectors.toList());
            Set<String> finalMcids = mcids = multiCriteriaEos.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
            afterZbDetails = afterZbDetails.stream().filter(eo -> finalMcids.contains(eo.getMcid())).collect(Collectors.toList());
        }
        List<GcMulCriBeforeZbEO> beforeZbDetails = this.mulCriBeforeZbDao.queryMulCriBeforeDataByMcids(new ArrayList<String>(mcids));
        Map<String, List<GcMulCriAfterZbEO>> mcid2AfterDetails = this.getMcid2AfterDetails(afterZbDetails);
        Map<String, List<GcMulCriBeforeZbEO>> mcid2BeforeDetails = this.getMcid2BeforeDetails(beforeZbDetails);
        DimensionValueSet ds = this.getDimensionValueSet(condition);
        try {
            HashMap<String, String> zbTitleMap = new HashMap<String, String>();
            boolean isCalcAfterZbValue = CollectionUtils.isEmpty((Collection)condition.getAfterFormKeys());
            Map<String, Map<String, BigDecimal>> mcid2BeforeZbValue = this.getBeforeZbValue(beforeZbDetails, ds, zbTitleMap);
            for (GcMultiCriteriaEO criteriaEo : multiCriteriaEos) {
                ArrayList<GcMultiCriteriaZbDataVO> oneGroupDeatils = new ArrayList<GcMultiCriteriaZbDataVO>();
                this.assemeMulCriVo(mcid2AfterDetails, mcid2BeforeDetails, condition, zbTitleMap, mcid2BeforeZbValue, criteriaEo, oneGroupDeatils, isCalcAfterZbValue);
                zbDatas.addAll(oneGroupDeatils);
            }
            if (MulCriShowTypeEnum.HAVE_ADJUST_AMT.getCode().equals(condition.getShowType())) {
                zbDatas = zbDatas.stream().filter(zbData -> BigDecimal.ZERO.compareTo(zbData.getAdjustAmt()) != 0).collect(Collectors.toList());
            }
            this.setRowSpanAndSort(zbDatas, mcid2BeforeDetails, mcid2AfterDetails);
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u67e5\u8be2\u9519\u8bef\uff1a" + e.getMessage(), e);
            throw new BusinessRuntimeException("\u6570\u636e\u67e5\u8be2\u9519\u8bef\uff1a" + e.getMessage(), (Throwable)e);
        }
        return zbDatas;
    }

    public List<GcMulCriAfterZbEO> listAafterZbDetails(GcMultiCriteriaConditionVO condition) {
        Set<String> afterFormKeys = CollectionUtils.isEmpty((Collection)condition.getAfterFormKeys()) ? (StringUtils.isEmpty((String)condition.getCurrFormKey()) ? new HashSet() : Collections.singleton(condition.getCurrFormKey())) : condition.getAfterFormKeys();
        return this.mulCriAfterZbDao.queryMulCriAfterDataByFormKey(condition.getSchemeId(), afterFormKeys);
    }

    private void setRowSpanAndSort(List<GcMultiCriteriaZbDataVO> zbDatas, Map<String, List<GcMulCriBeforeZbEO>> mcid2BeforeDetails, Map<String, List<GcMulCriAfterZbEO>> mcid2AfterDetails) {
        if (CollectionUtils.isEmpty(zbDatas)) {
            return;
        }
        Map<String, List<GcMultiCriteriaZbDataVO>> rowSpanMap = zbDatas.stream().collect(Collectors.groupingBy(GcMultiCriteriaZbDataVO::getMcid));
        int index = 0;
        String mcid = "";
        for (GcMultiCriteriaZbDataVO vo : zbDatas) {
            if (!mcid.equals(vo.getMcid())) {
                int preIndex;
                ++index;
                mcid = vo.getMcid();
                vo.setRowspan(rowSpanMap.get(vo.getMcid()).size());
                int n = preIndex = rowSpanMap.size() == 1 ? zbDatas.size() : zbDatas.indexOf(vo);
                if (preIndex >= 1) {
                    this.setDataRowspan(zbDatas, mcid2BeforeDetails, mcid2AfterDetails, preIndex);
                }
                if (index == rowSpanMap.size() && rowSpanMap.size() > 1) {
                    preIndex = zbDatas.size();
                    this.setDataRowspan(zbDatas, mcid2BeforeDetails, mcid2AfterDetails, preIndex);
                }
            }
            vo.setIndex(index);
        }
    }

    private void setDataRowspan(List<GcMultiCriteriaZbDataVO> zbDatas, Map<String, List<GcMulCriBeforeZbEO>> mcid2BeforeDetails, Map<String, List<GcMulCriAfterZbEO>> mcid2AfterDetails, int preIndex) {
        GcMultiCriteriaZbDataVO preZbData = zbDatas.get(preIndex - 1);
        int beforedDetailSize = Integer.valueOf(1).equals(preZbData.getHasFormula()) ? mcid2BeforeDetails.get(preZbData.getMcid()).size() + 1 : mcid2BeforeDetails.get(preZbData.getMcid()).size();
        int oneGroupSizeDifference = mcid2AfterDetails.get(preZbData.getMcid()).size() - beforedDetailSize;
        if (oneGroupSizeDifference != 0) {
            GcMultiCriteriaZbDataVO rowspanData = zbDatas.get(preIndex - Math.abs(oneGroupSizeDifference) - 1);
            rowspanData.setDataRowspan(oneGroupSizeDifference < 0 ? oneGroupSizeDifference - 1 : oneGroupSizeDifference + 1);
        }
    }

    private void assemeMulCriVo(Map<String, List<GcMulCriAfterZbEO>> mcid2AfterDetails, Map<String, List<GcMulCriBeforeZbEO>> mcid2BeforeDetails, GcMultiCriteriaConditionVO condition, Map<String, String> zbTitleMap, Map<String, Map<String, BigDecimal>> mcid2BeforeZbValue, GcMultiCriteriaEO criteriaEo, List<GcMultiCriteriaZbDataVO> oneGroupDeatils, boolean isCalcAfterZbValue) {
        Map<String, BigDecimal> beforeZbValue = mcid2BeforeZbValue.get(criteriaEo.getId());
        BigDecimal beforeSumZbValue = beforeZbValue.values().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        List<GcMulCriBeforeZbEO> beforeDetails = mcid2BeforeDetails.get(criteriaEo.getId());
        this.assemBeforeData(condition, zbTitleMap, criteriaEo, oneGroupDeatils, beforeZbValue, beforeSumZbValue, beforeDetails);
        List<GcMulCriAfterZbEO> afterDetails = mcid2AfterDetails.get(criteriaEo.getId());
        this.assemeAfterData(condition, afterDetails, zbTitleMap, oneGroupDeatils, criteriaEo.getKind(), isCalcAfterZbValue);
    }

    private void assemeAfterData(GcMultiCriteriaConditionVO condition, List<GcMulCriAfterZbEO> afterDetails, Map<String, String> zbTitleMap, List<GcMultiCriteriaZbDataVO> oneGroupDeatils, String kind, boolean isCalcAfterZbValue) {
        int oneGroupSize = oneGroupDeatils.size();
        BigDecimal beforeTotalValue = oneGroupDeatils.get(0).getBeforeTotalAmt();
        Integer hasFormula = oneGroupDeatils.get(0).getHasFormula();
        BigDecimal sumZbValue = BigDecimal.ZERO;
        BigDecimal zbValue = beforeTotalValue.divide(BigDecimal.valueOf(afterDetails.size()), 2);
        List<String> ids = afterDetails.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        Map<Object, Object> afterAmtMap = new HashMap();
        if (isCalcAfterZbValue) {
            afterAmtMap = this.mulCriAfterAmtDao.queryMulCriAfterAmt(ids, condition);
        }
        for (int i = 0; i < afterDetails.size(); ++i) {
            GcMultiCriteriaZbDataVO vo = i < oneGroupSize ? oneGroupDeatils.get(i) : new GcMultiCriteriaZbDataVO();
            GcMulCriAfterZbEO detail2 = afterDetails.get(i);
            vo.setAfterTitle(this.getAfterTitle(zbTitleMap, detail2.getAfterZbKey()));
            vo.setAfterFieldKey(detail2.getAfterZbKey());
            vo.setHasFormula(hasFormula);
            vo.setCriAfterZbId(detail2.getId());
            vo.setMulCriTypeTitle(MulCriTypeEnum.titleOfCode(kind));
            if (!afterAmtMap.containsKey(detail2.getId())) {
                if (i == afterDetails.size() - 1) {
                    zbValue = beforeTotalValue.subtract(sumZbValue);
                } else {
                    sumZbValue = sumZbValue.add(zbValue);
                }
                vo.setAfterAmt(zbValue);
            } else {
                vo.setAfterAmt(new BigDecimal(((Double)afterAmtMap.get(detail2.getId())).toString()));
            }
            if (i < oneGroupSize) continue;
            vo.setMcid(detail2.getMcid());
            oneGroupDeatils.add(vo);
        }
        BigDecimal totalValue = oneGroupDeatils.stream().map(detail -> detail.getAfterAmt() == null ? BigDecimal.ZERO : detail.getAfterAmt()).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal adjustAmt = totalValue.subtract(beforeTotalValue);
        oneGroupDeatils.forEach(detail -> {
            detail.setAfterTotalAmt(totalValue);
            detail.setAdjustAmt(adjustAmt);
        });
    }

    private String getAfterTitle(Map<String, String> zbTitleMap, String afterZbKey) {
        String afterTitle = null;
        try {
            afterTitle = zbTitleMap.get(afterZbKey);
            if (StringUtils.isEmpty((String)afterTitle)) {
                ColumnModelDefine columnDefine = DataFieldUtils.getNvwaColumnDefineByNrFieldKey((String)afterZbKey);
                if (columnDefine == null) {
                    return afterTitle;
                }
                afterTitle = columnDefine.getTitle();
                zbTitleMap.put(afterZbKey, columnDefine.getTitle());
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u6307\u6807\u5f02\u5e38:" + e.getMessage(), e);
        }
        return afterTitle;
    }

    private void assemBeforeData(GcMultiCriteriaConditionVO condition, Map<String, String> zbTitleMap, GcMultiCriteriaEO criteriaEo, List<GcMultiCriteriaZbDataVO> oneGroupDeatils, Map<String, BigDecimal> beforeZbValue, BigDecimal beforeSumZbValue, List<GcMulCriBeforeZbEO> beforeDetails) {
        DimensionValueSet ds = this.getDimensionValueSet(condition);
        int detailSize = criteriaEo.getHasFormula() == 1 ? beforeDetails.size() + 1 : beforeDetails.size();
        beforeSumZbValue = criteriaEo.getHasFormula() == 1 ? BigDecimal.valueOf(this.formulaEvalService.evaluate(ds, criteriaEo.getBeforeZbCodes(), criteriaEo.getSchemeId())) : beforeSumZbValue;
        for (int i = 0; i < detailSize; ++i) {
            GcMulCriBeforeZbEO detail = criteriaEo.getHasFormula() == 1 && i == detailSize - 1 ? beforeDetails.get(i - 1) : beforeDetails.get(i);
            GcMultiCriteriaZbDataVO vo = new GcMultiCriteriaZbDataVO();
            vo.setMcid(detail.getMcid());
            vo.setHasFormula(criteriaEo.getHasFormula());
            vo.setMulCriTypeTitle(MulCriTypeEnum.titleOfCode(criteriaEo.getKind()));
            if (criteriaEo.getHasFormula() == 1 && i == detailSize - 1) {
                vo.setBeforeTitle("        \u516c\u5f0f:  " + criteriaEo.getBeforeZbCodes());
                vo.setBeforeAmt(beforeSumZbValue);
                vo.setBeforeFieldKey("");
                vo.setBeforeTotalAmt(beforeSumZbValue);
            } else {
                vo.setBeforeTitle(zbTitleMap.get(detail.getBeforeZbKey()));
                vo.setBeforeAmt(beforeZbValue.get(detail.getBeforeZbKey()));
                vo.setBeforeFieldKey(detail.getBeforeZbKey());
                vo.setBeforeTotalAmt(beforeSumZbValue);
            }
            oneGroupDeatils.add(vo);
        }
    }

    private Map<String, List<GcMulCriAfterZbEO>> getMcid2AfterDetails(List<GcMulCriAfterZbEO> afterZbDetails) {
        return afterZbDetails.stream().collect(Collectors.toMap(GcMulCriAfterZbEO::getMcid, item -> CollectionUtils.newArrayList((Object[])new GcMulCriAfterZbEO[]{item}), (newValueList, oldValueList) -> {
            oldValueList.addAll(newValueList);
            return oldValueList;
        }));
    }

    private Map<String, List<GcMulCriBeforeZbEO>> getMcid2BeforeDetails(List<GcMulCriBeforeZbEO> beforeZbDetails) {
        return beforeZbDetails.stream().collect(Collectors.toMap(GcMulCriBeforeZbEO::getMcid, item -> CollectionUtils.newArrayList((Object[])new GcMulCriBeforeZbEO[]{item}), (newValueList, oldValueList) -> {
            oldValueList.addAll(newValueList);
            return oldValueList;
        }));
    }

    private Map<String, Map<String, BigDecimal>> getBeforeZbValue(List<GcMulCriBeforeZbEO> beforeZbDetails, DimensionValueSet ds, Map<String, String> zbTitleMap) throws Exception {
        HashMap<String, Map<String, BigDecimal>> mcid2ZbValue = new HashMap<String, Map<String, BigDecimal>>();
        for (GcMulCriBeforeZbEO detail : beforeZbDetails) {
            String beforeZbKey = detail.getBeforeZbKey();
            ColumnModelDefine columnModelDefine = DataFieldUtils.getNvwaColumnDefineByNrFieldKey((String)beforeZbKey);
            FieldDefine fieldDefine = DataFieldUtils.getFieldDefineByNrFieldKey((String)beforeZbKey);
            zbTitleMap.put(beforeZbKey, columnModelDefine.getTitle());
            BigDecimal zbValue = this.getZbValue(fieldDefine, ds);
            if (!mcid2ZbValue.containsKey(detail.getMcid())) {
                mcid2ZbValue.put(detail.getMcid(), new HashMap());
            }
            ((Map)mcid2ZbValue.get(detail.getMcid())).put(beforeZbKey, zbValue);
        }
        return mcid2ZbValue;
    }

    private Map<String, Map<String, BigDecimal>> getAfterZbValue(List<GcMulCriAfterZbEO> afterZbDetails, DimensionValueSet ds, Map<String, String> zbTitleMap) throws Exception {
        HashMap<String, Map<String, BigDecimal>> mcid2ZbValue = new HashMap<String, Map<String, BigDecimal>>();
        for (GcMulCriAfterZbEO detail : afterZbDetails) {
            String afterZbKey = detail.getAfterZbKey();
            ColumnModelDefine columnModelDefine = DataFieldUtils.getNvwaColumnDefineByNrFieldKey((String)afterZbKey);
            FieldDefine fieldDefine = DataFieldUtils.getFieldDefineByNrFieldKey((String)afterZbKey);
            zbTitleMap.put(fieldDefine.getKey(), columnModelDefine.getTitle());
            BigDecimal zbValue = this.getZbValue(fieldDefine, ds);
            if (!mcid2ZbValue.containsKey(detail.getMcid())) {
                mcid2ZbValue.put(detail.getMcid(), new HashMap());
            }
            ((Map)mcid2ZbValue.get(detail.getMcid())).put(afterZbKey, zbValue);
        }
        return mcid2ZbValue;
    }

    private BigDecimal getZbValue(FieldDefine fieldDefine, DimensionValueSet ds) throws Exception {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(ds);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        IDataTable dataTable = dataQuery.executeQuery(context);
        int rowCount = dataTable.getCount();
        if (rowCount > 0) {
            IDataRow dataRow = dataTable.getItem(0);
            AbstractData data = dataRow.getValue(fieldDefine);
            return BigDecimal.valueOf(data.getAsFloat());
        }
        return BigDecimal.ZERO;
    }

    private DimensionValueSet getDimensionValueSet(GcMultiCriteriaConditionVO condition) {
        DimensionValueSet ds = new DimensionValueSet();
        ds.setValue("MD_ORG", (Object)condition.getOrgId());
        ds.setValue("DATATIME", (Object)condition.getPeriodStr());
        ds.setValue("MD_GCORGTYPE", (Object)condition.getOrgType());
        ds.setValue("MD_CURRENCY", (Object)condition.getCurrency());
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            ds.setValue("ADJUST", (Object)condition.getSelectAdjustCode());
        }
        return ds;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveZbData(GcMultiCriteriaConditionVO condition) {
        this.checkCurrFormLockedState(condition);
        List<GcMultiCriteriaZbDataVO> multiCriteriaZbDatas = condition.getMultiCriteriaZbDatas();
        multiCriteriaZbDatas = multiCriteriaZbDatas.stream().filter(data -> !StringUtils.isEmpty((String)data.getCriAfterZbId()) && data.getAfterAmt() != null).collect(Collectors.toList());
        this.updateAfterAmt(condition, multiCriteriaZbDatas);
        this.sumSameFieldValue(multiCriteriaZbDatas);
        Map<String, Double> zbValueMap = multiCriteriaZbDatas.stream().collect(Collectors.toMap(GcMultiCriteriaZbDataVO::getAfterFieldKey, data -> data.getAfterAmt().doubleValue(), (e1, e2) -> e1));
        Set afterFieldKeys = multiCriteriaZbDatas.stream().map(GcMultiCriteriaZbDataVO::getAfterFieldKey).collect(Collectors.toSet());
        try {
            Map nrFieldKey2NvwaColumnDefineMap = DataFieldUtils.getNrFieldKey2NvwaColumnDefineMapByNrFieldKey(afterFieldKeys);
            Map nrFieldKey2DeployInfoMap = DataFieldUtils.getNrFieldKey2DeployInfoMapByNrFieldKeys(afterFieldKeys);
            ArrayList<Object> params = new ArrayList<Object>();
            params.add(0, new Object());
            String updateSql = this.getUpdateSql(condition, params);
            String insertSqlTemplate = null;
            ArrayList<Object> insertParams = null;
            for (GcMultiCriteriaZbDataVO zbData : multiCriteriaZbDatas) {
                String afterFieldKey = zbData.getAfterFieldKey();
                if (!nrFieldKey2NvwaColumnDefineMap.containsKey(afterFieldKey)) continue;
                ColumnModelDefine fieldDefine = (ColumnModelDefine)nrFieldKey2NvwaColumnDefineMap.get(afterFieldKey);
                DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)nrFieldKey2DeployInfoMap.get(afterFieldKey);
                String sql = String.format(updateSql, dataFieldDeployInfo.getTableName(), fieldDefine.getName());
                params.set(0, zbValueMap.get(afterFieldKey));
                int updateRowsCount = EntNativeSqlDefaultDao.getInstance().execute(sql, params);
                if (updateRowsCount != 0) continue;
                if (insertParams == null) {
                    insertParams = new ArrayList<Object>();
                    insertParams.add(0, new Object());
                }
                if (StringUtils.isEmpty(insertSqlTemplate)) {
                    insertSqlTemplate = this.getInsertSql(condition, insertParams);
                }
                insertParams.set(0, zbValueMap.get(afterFieldKey));
                String insertSql = String.format(insertSqlTemplate, dataFieldDeployInfo.getTableName(), fieldDefine.getName());
                EntNativeSqlDefaultDao.getInstance().execute(insertSql, insertParams);
            }
        }
        catch (Exception e) {
            this.logger.error("\u4fee\u6539\u6307\u6807\u503c\u5f02\u5e38:" + e.getMessage(), e);
            throw new BusinessRuntimeException("\u4fee\u6539\u6307\u6807\u503c\u5f02\u5e38:" + e.getMessage());
        }
    }

    private void checkCurrFormLockedState(GcMultiCriteriaConditionVO condition) {
        if (StringUtils.isEmpty((String)condition.getCurrFormKey())) {
            return;
        }
        DimensionParamsVO dimensionParamsVO = this.getDimensionParamsVO(condition);
        List writeAccessDescs = FormUploadStateTool.getInstance().writeable(dimensionParamsVO, Collections.singletonList(condition.getCurrFormKey()));
        ReadWriteAccessDesc accessDesc = (ReadWriteAccessDesc)writeAccessDescs.get(0);
        if (!accessDesc.getAble().booleanValue()) {
            throw new BusinessRuntimeException("\u5f53\u524d\u8868\u4e3a\u5df2\u9501\u5b9a\u6216\u4e0a\u62a5\u72b6\u6001\uff0c\u4e0d\u80fd\u8fdb\u884c\u51c6\u5219\u8f6c\u6362");
        }
    }

    @Override
    public DimensionParamsVO getDimensionParamsVO(GcMultiCriteriaConditionVO condition) {
        DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
        dimensionParamsVO.setCurrency(condition.getCurrency());
        dimensionParamsVO.setCurrencyId(condition.getCurrency());
        dimensionParamsVO.setOrgId(condition.getOrgId());
        dimensionParamsVO.setOrgType(condition.getOrgType());
        dimensionParamsVO.setOrgTypeId(condition.getOrgType());
        dimensionParamsVO.setPeriodStr(condition.getPeriodStr());
        dimensionParamsVO.setSchemeId(condition.getSchemeId());
        dimensionParamsVO.setTaskId(condition.getTaskId());
        dimensionParamsVO.setSelectAdjustCode(condition.getSelectAdjustCode());
        return dimensionParamsVO;
    }

    private void updateAfterAmt(GcMultiCriteriaConditionVO condition, List<GcMultiCriteriaZbDataVO> multiCriteriaZbDatas) {
        Map<String, Double> afterAmtMap = multiCriteriaZbDatas.stream().collect(Collectors.toMap(GcMultiCriteriaZbDataVO::getCriAfterZbId, data -> data.getAfterAmt().doubleValue()));
        List<String> criAfterZbIds = multiCriteriaZbDatas.stream().map(GcMultiCriteriaZbDataVO::getCriAfterZbId).collect(Collectors.toList());
        List<GcMulCriAfterZbEO> mulCriAfterZbS = this.mulCriAfterZbDao.queryMulCriAfterDataByIds(criAfterZbIds);
        List<String> mcAfterZbIds = mulCriAfterZbS.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        DimensionValueSet ds = this.getDimensionValueSet(condition);
        List<GcMulCriAfterAmtEO> mulCriAfterAmtEos = this.mulCriAfterAmtDao.queryMulCriAfterAmtByDs(mcAfterZbIds, condition);
        Map<String, GcMulCriAfterAmtEO> mulCriAfterAmtMap = mulCriAfterAmtEos.stream().collect(Collectors.toMap(GcMulCriAfterAmtEO::getMcAfterZbId, eo -> eo, (e1, e2) -> e1));
        ArrayList insertEos = new ArrayList();
        ArrayList updateEos = new ArrayList();
        mulCriAfterZbS.forEach(zb -> {
            if (mulCriAfterAmtMap.containsKey(zb.getId())) {
                GcMulCriAfterAmtEO eo = (GcMulCriAfterAmtEO)((Object)((Object)mulCriAfterAmtMap.get(zb.getId())));
                eo.setAfterZbAmt((Double)afterAmtMap.get(zb.getId()));
                updateEos.add(eo);
            } else {
                GcMulCriAfterAmtEO eo = new GcMulCriAfterAmtEO();
                eo.setId(UUIDOrderUtils.newUUIDStr());
                eo.setAfterZbAmt((Double)afterAmtMap.get(zb.getId()));
                eo.setMcAfterZbId(zb.getId());
                eo.setCurrency(condition.getCurrency());
                eo.setDefaultPeriod(condition.getPeriodStr());
                eo.setMdOrg(condition.getOrgId());
                eo.setGcOrgType(condition.getOrgType());
                eo.setAdjust(condition.getSelectAdjustCode());
                insertEos.add(eo);
            }
        });
        this.mulCriAfterAmtDao.updateBatch(updateEos);
        this.mulCriAfterAmtDao.addBatch(insertEos);
    }

    private void sumSameFieldValue(List<GcMultiCriteriaZbDataVO> multiCriteriaZbDatas) {
        HashMap<String, BigDecimal> zbValueMap = new HashMap<String, BigDecimal>();
        for (GcMultiCriteriaZbDataVO zbData2 : multiCriteriaZbDatas) {
            if (!zbValueMap.containsKey(zbData2.getAfterFieldKey())) {
                zbValueMap.put(zbData2.getAfterFieldKey(), zbData2.getAfterAmt());
                continue;
            }
            zbValueMap.put(zbData2.getAfterFieldKey(), ((BigDecimal)zbValueMap.get(zbData2.getAfterFieldKey())).add(zbData2.getAfterAmt()));
        }
        multiCriteriaZbDatas.forEach(zbData -> zbData.setAfterAmt((BigDecimal)zbValueMap.get(zbData.getAfterFieldKey())));
    }

    private String getUpdateSql(GcMultiCriteriaConditionVO condition, List<Object> params) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update  %1$s set  %2$s=? ");
        sqlBuilder.append("  where MDCODE=? and DATATIME=?\n");
        params.add(condition.getOrgId());
        params.add(condition.getPeriodStr());
        sqlBuilder.append((CharSequence)this.buildEntityTableWhere(condition, params));
        return sqlBuilder.toString();
    }

    private String getInsertSql(GcMultiCriteriaConditionVO condition, List<Object> params) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into  %1$s(%2$s,");
        params.add(condition.getCurrency());
        sqlBuilder.append("MD_CURRENCY").append(",\n");
        params.add(condition.getOrgType());
        sqlBuilder.append("MD_GCORGTYPE").append(",\n");
        params.add(condition.getOrgId());
        sqlBuilder.append("MDCODE").append(",\n");
        params.add(condition.getPeriodStr());
        sqlBuilder.append("DATATIME").append("\n");
        boolean exisAdjType = DimensionUtils.isExisAdjType((String)condition.getTaskId());
        if (exisAdjType) {
            sqlBuilder.append(", MD_GCADJTYPE\n");
            params.add(GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        sqlBuilder.append(") values(?,?,?,?,?");
        if (exisAdjType) {
            sqlBuilder.append(",?");
        }
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }

    private StringBuilder buildEntityTableWhere(GcMultiCriteriaConditionVO condition, List<Object> params) {
        Set entityTableNames = NrTool.getEntityTableNames((String)condition.getSchemeId());
        StringBuilder whereSql = new StringBuilder(128);
        if (entityTableNames.contains("MD_CURRENCY")) {
            params.add(condition.getCurrency());
            whereSql.append(" and ").append("MD_CURRENCY").append("=?\n");
        }
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            params.add(condition.getOrgType());
            whereSql.append(" and ").append("MD_GCORGTYPE").append("=?\n");
        }
        return whereSql;
    }

    @Override
    public Map<String, String> queryFieldMappingText(String formKey, String schemeId) {
        Map<String, String> fieldMappingText;
        List<GcMulCriAfterFormEO> afterForms = this.mulCriAfterFormDao.queryMulCriAfterForms(null, schemeId);
        List afterFormKeys = afterForms.stream().map(GcMulCriAfterFormEO::getFormKey).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(afterFormKeys) || !afterFormKeys.contains(formKey)) {
            List<GcMulCriBeforeZbEO> beforeZbs = this.mulCriBeforeZbDao.queryMulCriBeforeDataByFormKey(schemeId, formKey);
            fieldMappingText = beforeZbs.stream().collect(Collectors.toMap(GcMulCriBeforeZbEO::getBeforeZbKey, eo -> eo.getAfterZbTitles(), (e1, e2) -> e1));
        } else {
            List<GcMulCriAfterZbEO> afterZbs = this.mulCriAfterZbDao.queryMulCriAfterDataByFormKey(schemeId, Collections.singleton(formKey));
            fieldMappingText = afterZbs.stream().collect(Collectors.toMap(GcMulCriAfterZbEO::getAfterZbKey, eo -> eo.getBeforeZbTitles(), (e1, e2) -> e1));
        }
        return fieldMappingText;
    }

    @Override
    public ExportExcelSheet exportMultiCriteriaData(GcMultiCriteriaConditionVO condition) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u51c6\u5219\u8f6c\u6362", Integer.valueOf(1));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        String[] titles = new String[]{"\u5e8f\u53f7", "\u8f6c\u6362\u7c7b\u578b", "\u8f6c\u6362\u524d\u9879\u76ee", "\u8f6c\u6362\u524d\u91d1\u989d", "\u8f6c\u6362\u524d\u5408\u8ba1", "\u8f6c\u6362\u540e\u9879\u76ee", "\u8f6c\u6362\u540e\u91d1\u989d", "\u8f6c\u6362\u540e\u5408\u8ba1", "\u8c03\u6574\u91d1\u989d"};
        rowDatas.add(titles);
        List<GcMultiCriteriaZbDataVO> zbData = this.queryZbData(condition);
        int rowIndex = 1;
        int dataIndex = 0;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        List cellRangeAddresses = exportExcelSheet.getCellRangeAddresses();
        String lastMcid = UUIDOrderUtils.newUUIDStr();
        for (GcMultiCriteriaZbDataVO vo : zbData) {
            Object[] dataRow = new Object[titles.length];
            int colIndex = 0;
            if (!lastMcid.equals(vo.getMcid())) {
                lastMcid = vo.getMcid();
                ++dataIndex;
            }
            dataRow[colIndex++] = String.valueOf(dataIndex);
            dataRow[colIndex++] = vo.getMulCriTypeTitle();
            dataRow[colIndex++] = vo.getBeforeTitle();
            dataRow[colIndex++] = df.format(vo.getBeforeAmt() == null ? Integer.valueOf(0) : vo.getBeforeAmt());
            dataRow[colIndex++] = df.format(vo.getBeforeTotalAmt() == null ? Integer.valueOf(0) : vo.getBeforeTotalAmt());
            dataRow[colIndex++] = vo.getAfterTitle();
            dataRow[colIndex++] = df.format(vo.getAfterAmt() == null ? Integer.valueOf(0) : vo.getAfterAmt());
            dataRow[colIndex++] = df.format(vo.getAfterTotalAmt() == null ? Integer.valueOf(0) : vo.getAfterTotalAmt());
            dataRow[colIndex] = df.format(vo.getAdjustAmt() == null ? Integer.valueOf(0) : vo.getAdjustAmt());
            rowDatas.add(dataRow);
            if (vo.getRowspan() != 0) {
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getRowspan() - 1, 0, 0));
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getRowspan() - 1, 1, 1));
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getRowspan() - 1, 4, 4));
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getRowspan() - 1, 7, 7));
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getRowspan() - 1, 8, 8));
            }
            if (vo.getDataRowspan() > 0) {
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getDataRowspan() - 1, 2, 2));
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + vo.getDataRowspan() - 1, 3, 3));
            }
            if (vo.getDataRowspan() < 0) {
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + Math.abs(vo.getDataRowspan()) - 1, 5, 5));
                cellRangeAddresses.add(new CellRangeAddress(rowIndex, rowIndex + Math.abs(vo.getDataRowspan()) - 1, 6, 6));
            }
            ++rowIndex;
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    @Override
    public ExportExcelSheet exportMultiCriteriaSettingData(GcMultiCriteriaConditionVO condition, boolean templateExportFlag) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u51c6\u5219\u8f6c\u6362\u8bbe\u7f6e", Integer.valueOf(1));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        String[] titles = new String[]{"\u5e8f\u53f7", "\u8f6c\u6362\u524d\u9879\u76ee", "\u8f6c\u6362\u540e\u9879\u76ee"};
        rowDatas.add(titles);
        if (!templateExportFlag) {
            List<GcMultiCriteriaEO> multiCriteriaSettings = this.listMultiCriteriaSetting(condition);
            int rowIndex = 1;
            for (GcMultiCriteriaEO eo : multiCriteriaSettings) {
                Object[] dataRow = new Object[titles.length];
                int colIndex = 0;
                dataRow[colIndex++] = String.valueOf(rowIndex++);
                dataRow[colIndex++] = eo.getBeforeZbTitles();
                dataRow[colIndex++] = eo.getAfterZbTitles();
                rowDatas.add(dataRow);
            }
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    private List<GcMultiCriteriaEO> listMultiCriteriaSetting(GcMultiCriteriaConditionVO condition) {
        Set<String> mcids = new HashSet<String>();
        if (!condition.getAllExport().booleanValue()) {
            ArrayList<String> formKeys = new ArrayList<String>();
            formKeys.add(condition.getCurrFormKey());
            mcids = condition.getBeforeReport().booleanValue() ? this.mulCriBeforeZbDao.queryMulCriBeforeMcidsByFormKeys(condition.getSchemeId(), formKeys) : this.mulCriAfterZbDao.queryMulCriAfterDataByFormKeys(condition.getSchemeId(), formKeys);
        } else {
            mcids.addAll(this.mulCriBeforeZbDao.queryMulCriBeforeMcidsByFormKeys(condition.getSchemeId(), null));
            mcids.addAll(this.mulCriAfterZbDao.queryMulCriAfterDataByFormKeys(condition.getSchemeId(), null));
        }
        return this.multiCriteriaDao.querySubjectMappingByIds(mcids);
    }

    @Override
    public StringBuilder multiCriteriaSettingImport(String taskId, String schemeId, List<Object[]> excelDatas) {
        StringBuilder log = new StringBuilder(128);
        ArrayList<GcMultiCriteriaVO> multiCriterias = new ArrayList<GcMultiCriteriaVO>();
        Map<String, String> field2FormKey = this.getField2FormMap(schemeId);
        List<GcMulCriAfterFormEO> afterForms = this.mulCriAfterFormDao.queryMulCriAfterForms(taskId, schemeId);
        List<String> afterFormKeys = afterForms.stream().map(GcMulCriAfterFormEO::getFormKey).collect(Collectors.toList());
        for (int i = 1; i < excelDatas.size(); ++i) {
            Object[] excelRowData = excelDatas.get(i);
            try {
                String beforeSubjectTitles = (String)excelRowData[1];
                Assert.isNotEmpty((String)beforeSubjectTitles, (String)"\u672a\u89e3\u6790\u5230\u8f6c\u6362\u524d\u9879\u76ee", (Object[])new Object[0]);
                List<String> beforeSubjects = Arrays.asList(beforeSubjectTitles.split(","));
                if (CollectionUtils.isEmpty(beforeSubjects)) {
                    throw new BusinessRuntimeException("\u672a\u89e3\u6790\u5230\u8f6c\u6362\u524d\u9879\u76ee");
                }
                String afterSubjectTitles = (String)excelRowData[2];
                Assert.isNotEmpty((String)afterSubjectTitles, (String)"\u672a\u89e3\u6790\u5230\u8f6c\u6362\u540e\u9879\u76ee", (Object[])new Object[0]);
                List<String> aftreSubjecs = Arrays.asList(afterSubjectTitles.split(","));
                if (CollectionUtils.isEmpty(aftreSubjecs)) {
                    throw new BusinessRuntimeException("\u672a\u89e3\u6790\u5230\u8f6c\u6362\u540e\u9879\u76ee");
                }
                GcMultiCriteriaVO vo = new GcMultiCriteriaVO();
                vo.setTaskId(taskId);
                vo.setSchemeId(schemeId);
                vo.setBeforeZbTitles(beforeSubjectTitles);
                vo.setAfterZbTitles(afterSubjectTitles);
                String firstZb = beforeSubjects.get(0);
                boolean hasFormula = !firstZb.contains("(") && !firstZb.contains("\uff09") && (firstZb.contains("+") || firstZb.contains("-"));
                vo.setHasFormula(Integer.valueOf(hasFormula ? 1 : 0));
                StringBuilder zbCodes = new StringBuilder();
                if (hasFormula) {
                    vo.setBeforeZbCodes(beforeSubjectTitles);
                    vo.setBeforeZbJson(null);
                } else {
                    String beforeZbJson = this.getZbCodesAndZbJson(zbCodes, beforeSubjects, field2FormKey, taskId, schemeId, afterFormKeys, true);
                    vo.setBeforeZbCodes(zbCodes.toString());
                    vo.setBeforeZbJson(beforeZbJson);
                }
                zbCodes = new StringBuilder();
                String afterZbJson = this.getZbCodesAndZbJson(zbCodes, aftreSubjecs, field2FormKey, taskId, schemeId, afterFormKeys, false);
                vo.setAfterZbCodes(zbCodes.toString());
                vo.setAfterZbJson(afterZbJson);
                multiCriterias.add(vo);
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u7b2c%1d\u884c\uff1a%2s<br>", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u7b2c%1d\u884c\uff1a%2s<br>", i, e.getMessage()));
                this.logger.error(e.getMessage(), e);
            }
        }
        this.deleteAllSubjectsBySchemeId(schemeId);
        this.saveSubjectMapping(multiCriterias);
        return log;
    }

    private void deleteAllSubjectsBySchemeId(String schemeId) {
        Set<String> aftreMcids = this.mulCriAfterZbDao.queryMulCriAfterDataByFormKeys(schemeId, null);
        ArrayList<String> deleteMcids = new ArrayList<String>(aftreMcids);
        if (!CollectionUtils.isEmpty(deleteMcids)) {
            this.mulCriAfterZbDao.deleteMulCriAfterZb(deleteMcids);
            this.mulCriBeforeZbDao.deleteMulCriBeforeZb(deleteMcids);
            this.multiCriteriaDao.deleteSubjectMapping(deleteMcids);
        }
    }

    private Map<String, String> getField2FormMap(String schemeId) {
        List allFormKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(schemeId);
        HashMap<String, String> field2FormKey = new HashMap<String, String>();
        for (String formKey : allFormKeys) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
            if (CollectionUtils.isEmpty((Collection)fieldKeys)) continue;
            field2FormKey.putAll(fieldKeys.stream().collect(Collectors.toMap(fieldKey1 -> fieldKey1, fieldKey2 -> formKey, (e1, e2) -> e1)));
        }
        return field2FormKey;
    }

    private String getZbCodesAndZbJson(StringBuilder zbCodes, List<String> subjects, Map<String, String> field2FormKey, String taskId, String schemeId, List<String> afterFormKeys, boolean isBefore) throws Exception {
        HashMap allZbInfoMap = new HashMap();
        for (String subject : subjects) {
            FieldDefine fieldDefine;
            String formKey;
            int start = subject.lastIndexOf("(");
            int end = subject.lastIndexOf(")");
            String zbCode = subject.substring(start + 1, end);
            zbCodes.append(zbCode);
            if (subjects.indexOf(subject) != subjects.size() - 1) {
                zbCodes.append(",");
            }
            if (StringUtils.isEmpty((String)(formKey = field2FormKey.get((fieldDefine = this.initDataQuery(zbCode)).getKey())))) {
                throw new BusinessRuntimeException("\u6307\u6807" + zbCode + "\u5728\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e2d\u4e0d\u5b58\u5728");
            }
            if (isBefore) {
                if (afterFormKeys.contains(formKey)) {
                    throw new BusinessRuntimeException("\u6307\u6807" + zbCode + "\u4e0d\u80fd\u5b58\u5728\u4e8e\u8f6c\u6362\u540e\u62a5\u8868");
                }
            } else if (!afterFormKeys.contains(formKey)) {
                throw new BusinessRuntimeException("\u6307\u6807" + zbCode + "\u4e0d\u80fd\u5b58\u5728\u4e8e\u8f6c\u6362\u524d\u62a5\u8868");
            }
            List dataLinkDefines = this.runTimeViewController.getLinksInFormByField(formKey, fieldDefine.getKey());
            DataLinkDefine dataLinkDefine = (DataLinkDefine)dataLinkDefines.get(0);
            boolean isContainsCurrFormKey = allZbInfoMap.containsKey(formKey);
            if (!isContainsCurrFormKey) {
                allZbInfoMap.put(formKey, new HashMap());
            }
            Map zbInfoMap = (Map)allZbInfoMap.get(formKey);
            if (!isContainsCurrFormKey) {
                zbInfoMap.put("selections", new ArrayList());
                zbInfoMap.put("zbs", new ArrayList());
                zbInfoMap.put("formKey", formKey);
                zbInfoMap.put("taskId", taskId);
                zbInfoMap.put("schemeId", schemeId);
            }
            List selections = (List)zbInfoMap.get("selections");
            HashMap<String, Integer> posMap = new HashMap<String, Integer>();
            posMap.put("width", 1);
            posMap.put("height", 1);
            posMap.put("x", dataLinkDefine.getPosX());
            posMap.put("y", dataLinkDefine.getPosY());
            selections.add(posMap);
            List zbs = (List)zbInfoMap.get("zbs");
            zbs.add(zbCode);
            zbInfoMap.put("zbs", zbs);
        }
        return JsonUtils.writeValueAsString(allZbInfoMap.values());
    }

    @Override
    public String queryFormData(String schemeId, String formKey, Object o) {
        try {
            Grid2Data griddata = this.getGridDataByRunTime(formKey);
            Map<String, String> mutiCriteridMap = this.queryFieldMappingText(formKey, schemeId);
            this.fillZbCode(formKey, griddata, null, mutiCriteridMap, null);
            String result = this.serialize(griddata);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private Grid2Data getGridDataByRunTime(String formKey) {
        BigDataDefine dataDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        Grid2Data gridData = null;
        if (null != dataDefine) {
            if (dataDefine.getData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])dataDefine.getData());
            } else {
                gridData = new Grid2Data();
                gridData.setRowCount(10);
                gridData.setColumnCount(10);
            }
        }
        return gridData;
    }

    private void fillZbCode(String formKey, Grid2Data gridData, Consumer<GridCellData> consumer, Map<String, String> mutiCriteridMap, Map<String, Object> zbAttrsMap) throws Exception {
        List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            String regionKey = region.getKey();
            List dataLinks = this.runTimeViewController.getAllLinksInRegion(regionKey);
            for (DataLinkDefine link : dataLinks) {
                GridCellData cellData;
                FieldDefine fieldDefine = DataFieldUtils.getFieldDefineByNrFieldKey((String)link.getLinkExpression());
                DataFieldDeployInfo deployInfo = DataFieldUtils.getDeployInfoByNrFieldKey((String)link.getLinkExpression());
                String tableName = "";
                String fieldCode = "\u672a\u77e5";
                boolean isNumberField = true;
                if (fieldDefine != null) {
                    tableName = deployInfo.getTableName();
                    fieldCode = fieldDefine.getCode();
                    FieldType fieldType = fieldDefine.getType();
                    if (fieldType != FieldType.FIELD_TYPE_FLOAT && fieldType != FieldType.FIELD_TYPE_INTEGER && fieldType != FieldType.FIELD_TYPE_DECIMAL) {
                        isNumberField = false;
                    }
                }
                if ((cellData = gridData.getGridCellData(link.getPosX(), link.getPosY())) == null) continue;
                cellData.setShowText(tableName + "[" + fieldCode + "]");
                cellData.setHorzAlign(3);
                cellData.setForeGroundColor(255);
                if (null != consumer) {
                    consumer.accept(cellData);
                }
                cellData.setEditable(true);
                if (isNumberField) {
                    cellData.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                }
                if (mutiCriteridMap != null) {
                    cellData.setShowText(null);
                    cellData.setEditText(tableName + "[" + fieldCode + "]");
                    if (fieldDefine == null || mutiCriteridMap.get(fieldDefine.getKey()) == null) continue;
                    cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
                    cellData.setShowText(mutiCriteridMap.get(fieldDefine.getKey()));
                    continue;
                }
                if (zbAttrsMap == null) continue;
                cellData.setShowText(null);
                cellData.setEditText(tableName + "[" + fieldCode + "];" + link.getKey());
                if (!zbAttrsMap.containsKey(link.getKey())) continue;
                cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
            }
        }
        if (null == gridData) {
            gridData = new Grid2Data();
            gridData.insertRows(0, 1, -1);
            gridData.insertColumns(0, 1);
            gridData.setRowHidden(0, true);
            gridData.setColumnHidden(0, true);
        }
    }

    private String serialize(Grid2Data griddata) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)griddata);
    }
}

