/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.CSConst
 *  com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.executor;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.CSConst;
import com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.enums.ImportModuleTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.enums.SubjectExportEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectExportUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SubjectDataImportTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedSubjectUIService subjectUIService;
    @Autowired
    private DataModelService dataModelService;

    @Transactional(rollbackFor={Exception.class})
    public String uploadSubjects(ImportContext context, String systemId, List<Object[]> excelSheetDatas) {
        Map<String, String> titleMapMap = SubjectExportUtils.getSubjectExcelColumnTitleMap();
        List<Map<String, Object>> dataList = SubjectExportUtils.convertImportedData(titleMapMap, excelSheetDatas);
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String impMode = (String)param.get("impMode");
        if (CollectionUtils.isEmpty(dataList)) {
            return "\u5bfc\u5165\u6587\u4ef6\u4e3a\u7a7a";
        }
        int validitems = 0;
        int invaliditems = 0;
        StringBuffer importMsg = new StringBuffer();
        List vos = dataList.stream().map(rowData -> this.convertExcelModel2VO((Map<String, Object>)rowData, systemId)).collect(Collectors.toList());
        List<ConsolidatedSubjectEO> dbEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        ArrayList<ConsolidatedSubjectEO> tempList = new ArrayList<ConsolidatedSubjectEO>();
        ArrayList<ConsolidatedSubjectEO> addList = new ArrayList<ConsolidatedSubjectEO>();
        ArrayList<ConsolidatedSubjectEO> editList = new ArrayList<ConsolidatedSubjectEO>();
        TableModelDefine subjectTableDefine = this.dataModelService.getTableModelDefineByCode("MD_GCSUBJECT");
        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(subjectTableDefine.getID());
        Map<String, Integer> columnCode2PrecisionMap = columnModelDefinesByTable.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getPrecision, (o1, o2) -> o1));
        String optionValue = GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE");
        boolean relateGlobalSubject = "1".equals(optionValue);
        for (int i = 0; i < vos.size(); ++i) {
            try {
                this.convertImportVO2EO(systemId, (ConsolidatedSubjectVO)vos.get(i), dbEOS, tempList, addList, editList, columnCode2PrecisionMap, relateGlobalSubject);
                ++validitems;
                continue;
            }
            catch (Exception e) {
                ++invaliditems;
                importMsg.append("\u5bfc\u5165\u7b2c" + (i + 1) + "\u6761\u6570\u636e\u51fa\u9519\uff1a " + e.getMessage() + "\n");
                this.logger.error("\u79d1\u76ee\u5bfc\u5165\u5f02\u5e38\uff1a", e);
            }
        }
        ArrayList<ConsolidatedSubjectEO> newlist = new ArrayList<ConsolidatedSubjectEO>();
        newlist.addAll(addList);
        if (ImportModuleTypeEnum.OVERWRITE_REPEAT.getCode().equals(impMode)) {
            newlist.addAll(editList);
        }
        if (!CollectionUtils.isEmpty(newlist)) {
            ReturnObject ro = this.subjectUIService.checkSubjects(newlist);
            if (!ro.isSuccess()) {
                return "\u5bfc\u5165\u5931\u8d25\uff1a" + ro.getErrorMessage().replaceAll("<br>", "\n");
            }
            List<ConsolidatedSubjectVO> syncList = newlist.stream().map(SubjectConvertUtil::convertEO2VO).collect(Collectors.toList());
            this.subjectUIService.saveSubjects(syncList);
        }
        return "\u79d1\u76ee\u5bfc\u5165\u6210\u529f\uff0c\u65b0\u589e\uff1a" + addList.size() + "\u6761\u6570\u636e\uff0c" + (ImportModuleTypeEnum.OVERWRITE_REPEAT.getCode().equals(impMode) ? "\u4fee\u6539\uff1a" : "\u8df3\u8fc7\uff1a") + editList.size() + "\u6761\u6570\u636e\uff0c\u5931\u8d25\uff1a" + invaliditems + "\u6761\u6570\u636e\u3002\n" + importMsg;
    }

    public ConsolidatedSubjectVO convertExcelModel2VO(Map<String, Object> rowData, String systemId) {
        Integer enumValue;
        ConsolidatedSubjectVO vo = new ConsolidatedSubjectVO();
        vo.setSystemId(systemId);
        vo.setCode(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.CODE.getCode())));
        vo.setTitle(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.TITLE.getCode())));
        vo.setParentCode(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.PARENTCODE.getCode())));
        vo.setConsolidationFlag(CSConst.CONSOLIDATION_ENABLED_WORD.equalsIgnoreCase(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.STATUS.getCode()))) ? CSConst.CONSOLIDATION_ENABLED : CSConst.CONSOLIDATION_DISABLED);
        vo.setConsolidationType(CSConst.CONSOLIDATION_BALANCE_WORD.equalsIgnoreCase(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.CONSOLIDATIONTYPE.getCode()))) ? CSConst.CONSOLIDATION_BALANCE : CSConst.CONSOLIDATION_AMOUNT);
        vo.setOrient(CSConst.CONSOLIDATION_BORROW_WORD.equalsIgnoreCase(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.ORIENT.getCode()))) ? CSConst.CONSOLIDATION_BORROW : CSConst.CONSOLIDATION_LEND);
        String attri = ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.ATTRI.getCode()));
        if (attri != null && (enumValue = SubjectAttributeEnum.getEnumValue((String)attri)) != null) {
            vo.setAttri(enumValue);
        }
        vo.setBoundIndexPath(ConverterUtils.getAsString((Object)rowData.get(SubjectExportEnum.BOUNDINDEXPATH.getCode())));
        HashMap<String, String> multilingualNames = new HashMap<String, String>();
        for (String key : rowData.keySet()) {
            if (!key.startsWith("name_")) continue;
            String multilingual = ConverterUtils.getAsString((Object)rowData.get(key));
            multilingualNames.put(key, multilingual);
        }
        vo.setMultilingualNames(multilingualNames);
        return vo;
    }

    private void convertImportVO2EO(String systemId, ConsolidatedSubjectVO vo, List<ConsolidatedSubjectEO> dbEOS, List<ConsolidatedSubjectEO> tempList, List<ConsolidatedSubjectEO> addList, List<ConsolidatedSubjectEO> editList, Map<String, Integer> columnCode2PrecisionMap, boolean relateGlobalSubject) throws Exception {
        Optional<ConsolidatedSubjectEO> anyEO;
        ConsolidatedSubjectEO eo = new ConsolidatedSubjectEO();
        String code = vo.getCode();
        if (StringUtils.isEmpty((String)code) || !code.matches("^[a-zA-Z_0-9-]+$")) {
            throw new Exception("\u7f16\u7801\uff1a" + vo.getCode() + " \u4e0d\u7b26\u5408\u89c4\u8303.");
        }
        if (relateGlobalSubject) {
            SubjectService subjectService = (SubjectService)SpringContextUtils.getBean(SubjectService.class);
            SubjectDTO subjectDTO = subjectService.findByCode(code);
            if (subjectDTO == null) {
                throw new Exception("\u7f16\u7801\uff1a" + vo.getCode() + "\u5728\u79d1\u76ee\u7ba1\u7406\u4e2d\u4e0d\u5b58\u5728\u3002");
            }
            eo.setAsstype(subjectDTO.getAssType());
        }
        if (!(anyEO = dbEOS.stream().filter(one -> code.equals(one.getCode())).findAny()).isPresent()) {
            eo.setId(UUIDUtils.newUUIDStr());
            eo.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
        } else {
            eo.setId(anyEO.get().getId());
            eo.setOrdinal(anyEO.get().getOrdinal());
        }
        eo.setCode(code);
        if (StringUtils.isEmpty((String)vo.getTitle())) {
            throw new Exception("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u79d1\u76ee\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a.");
        }
        eo.setTitle(vo.getTitle());
        this.checkPrecision(vo, columnCode2PrecisionMap);
        String parentCode = vo.getParentCode();
        if (!StringUtils.isEmpty((String)parentCode)) {
            if (parentCode.equals(code)) {
                throw new Exception("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u4e0a\u7ea7\u7f16\u7801\u4e0e\u81ea\u8eab\u76f8\u540c.");
            }
            ConsolidatedSubjectEO parentEO = this.getImportEOByStdcode(parentCode, dbEOS, tempList);
            if (parentEO != null) {
                eo.setParentCode(parentEO.getCode());
            } else {
                throw new Exception("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u627e\u4e0d\u5230\u4e0a\u7ea7\u7f16\u7801.");
            }
        }
        eo.setConsolidationFlag(vo.getConsolidationFlag());
        eo.setConsolidationType(vo.getConsolidationType());
        eo.setOrient(vo.getOrient());
        eo.setAttri(vo.getAttri());
        eo.setBoundIndexPath(vo.getBoundIndexPath());
        eo.setFormula(vo.getFormula());
        eo.setMultilingualNames(vo.getMultilingualNames());
        eo.setSystemId(systemId);
        eo.setObjectCode(eo.getCode() + "||" + eo.getSystemId());
        tempList.add(eo);
        if (!anyEO.isPresent()) {
            addList.add(eo);
        } else {
            editList.add(eo);
        }
    }

    private void checkPrecision(ConsolidatedSubjectVO vo, Map<String, Integer> columnCode2PrecisionMap) {
        if (vo.getCode().length() > columnCode2PrecisionMap.get("CODE")) {
            throw new BusinessRuntimeException("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u79d1\u76ee\u4ee3\u7801\u8d85\u957f.");
        }
        if (vo.getTitle().length() > columnCode2PrecisionMap.get("NAME")) {
            throw new BusinessRuntimeException("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u79d1\u76ee\u540d\u79f0[" + vo.getTitle() + "]\u8d85\u957f.");
        }
        if (vo.getBoundIndexPath() != null && vo.getBoundIndexPath().length() > columnCode2PrecisionMap.get("BOUNDINDEXPATH")) {
            throw new BusinessRuntimeException("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u5173\u8054\u6307\u6807[" + vo.getBoundIndexPath() + "]\u8d85\u957f.");
        }
        if (vo.getFormula() != null && vo.getFormula().length() > columnCode2PrecisionMap.get("BOUNDINDEXPATH")) {
            throw new BusinessRuntimeException("\u79d1\u76ee\uff1a" + vo.getCode() + "\uff0c \u8fd0\u7b97\u516c\u5f0f[" + vo.getFormula() + "]\u8d85\u957f.");
        }
    }

    private ConsolidatedSubjectEO getImportEOByStdcode(String stdcode, List<ConsolidatedSubjectEO> dbEOS, List<ConsolidatedSubjectEO> tempList) {
        if (StringUtils.isEmpty((String)stdcode)) {
            return null;
        }
        ConsolidatedSubjectEO eo = null;
        Optional<ConsolidatedSubjectEO> parentEOOption = tempList.stream().filter(one -> one.getCode().equals(stdcode)).findFirst();
        if (parentEOOption.isPresent()) {
            eo = parentEOOption.get();
        } else {
            Optional<ConsolidatedSubjectEO> parentEOOption2 = dbEOS.stream().filter(one -> one.getCode().equals(stdcode)).findFirst();
            if (parentEOOption2.isPresent()) {
                eo = parentEOOption2.get();
            }
        }
        return eo;
    }
}

