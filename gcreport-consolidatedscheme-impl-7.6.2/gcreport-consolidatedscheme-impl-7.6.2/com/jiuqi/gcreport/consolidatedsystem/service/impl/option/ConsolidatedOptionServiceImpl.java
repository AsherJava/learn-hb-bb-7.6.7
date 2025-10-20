/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedOptionChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedOptionChangedEvent$ConsolidatedOptionChangedInfo
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  jcifs.util.Base64
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.option;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.dao.option.ConsolidatedOptionDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedOptionChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import jcifs.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsolidatedOptionServiceImpl
implements ConsolidatedOptionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConsolidatedOptionDao consolidatedOptionDao;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ConsolidatedTaskService taskService;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedOptionCacheService consolidatedOptionCacheService;

    @Override
    public ConsolidatedOptionVO getOptionData(String systemId) {
        return this.consolidatedOptionCacheService.getConOptionBySystemId(systemId);
    }

    @Override
    public ConsolidatedOptionVO getOptionDataBySchemeId(String schemeId, String periodStr) {
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(schemeId, periodStr);
        if (null == reportSystemId) {
            return null;
        }
        return this.getOptionData(reportSystemId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveOptionData(String systemId, ConsolidatedOptionVO optionVO) {
        Set subjectCodes = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId).stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        int rowCount = 1;
        for (String sujectCode : optionVO.getCarryOverSubjectCodeMapping().keySet()) {
            if (!StringUtils.isEmpty((String)((String)((Map)optionVO.getCarryOverSubjectCodeMapping().get(sujectCode)).get(systemId))) && !subjectCodes.contains(((Map)optionVO.getCarryOverSubjectCodeMapping().get(sujectCode)).get(systemId))) {
                throw new BusinessRuntimeException("\u7b2c" + rowCount + "\u884c\u6620\u5c04\u79d1\u76ee\u4ee3\u7801\u6709\u8bef,\u4e0d\u662f\u76ee\u6807\u4f53\u7cfb\u4e0b\u7684\u79d1\u76ee\u6216\u4e3a\u4e2d\u6587\u5b57\u6837");
            }
            ++rowCount;
        }
        ConsolidatedOptionVO oldOptionData = this.getOptionData(systemId);
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId))).getSystemName();
        this.checkChangeAndDoLog(systemName, oldOptionData, optionVO);
        String optionJSONString = JsonUtils.writeValueAsString((Object)optionVO);
        String encode = Base64.encode((byte[])optionJSONString.getBytes());
        ConsolidatedOptionEO optionEO = this.consolidatedOptionDao.getOptionDataBySystemId(systemId);
        if (optionEO == null) {
            optionEO = new ConsolidatedOptionEO();
            optionEO.setSystemId(systemId);
            optionEO.setData(encode);
            this.consolidatedOptionDao.save(optionEO);
        } else {
            optionEO.setData(encode);
            this.consolidatedOptionDao.update((BaseEntity)optionEO);
        }
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedOptionChangedEvent(new ConsolidatedOptionChangedEvent.ConsolidatedOptionChangedInfo(systemId), context));
    }

    private void checkChangeAndDoLog(String systemName, ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        try {
            this.lossGainChangeLog(systemName, oldOptionData, newOptionVO);
            String ditaxStr = JsonUtils.writeValueAsString((Object)newOptionVO.getDiTax());
            if (!JsonUtils.writeValueAsString((Object)oldOptionData.getDiTax()).equals(ditaxStr)) {
                LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u9012\u5ef6\u6240\u5f97\u7a0e\u53ca\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)ditaxStr);
            }
            this.monthlyIncrementChangeLog(systemName, oldOptionData, newOptionVO);
            if (!this.collectionIsEquals(oldOptionData.getFinishCalcRewriteRuleIds(), newOptionVO.getFinishCalcRewriteRuleIds())) {
                LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u6d6e\u52a8\u4f59\u989d\u8868\u56de\u5199\u89c4\u5219\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("\u89c4\u5219\uff1a" + CollectionUtils.toString((List)newOptionVO.getMonthlyIncrementRuleIds())));
            }
            if (!this.collectionIsEquals(oldOptionData.getManagementDimension(), newOptionVO.getManagementDimension())) {
                LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u7ba1\u7406\u4f1a\u8ba1\u7ef4\u5ea6\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("\u7ef4\u5ea6\uff1a" + CollectionUtils.toString((List)newOptionVO.getManagementDimension())));
            }
            this.carryOverChangeLog(systemName, oldOptionData, newOptionVO);
            this.reclassifyChangeLog(systemName, oldOptionData, newOptionVO);
            String crossTaskCalcSets = JsonUtils.writeValueAsString((Object)newOptionVO.getCrossTaskCalcSets());
            if (!JsonUtils.writeValueAsString((Object)oldOptionData.getCrossTaskCalcSets()).equals(crossTaskCalcSets)) {
                LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u8de8\u4efb\u52a1\u5408\u5e76\u8ba1\u7b97\u89c4\u5219\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)crossTaskCalcSets);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5408\u5e76\u9009\u9879\u53d8\u52a8\uff0c\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    private void reclassifyChangeLog(String systemName, ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        String reclassifySubjectMappingStr = JsonUtils.writeValueAsString((Object)newOptionVO.getReclassifySubjectMappings());
        String reclassifyOtherInfoStr = JsonUtils.writeValueAsString((Object)newOptionVO.getReclassifyOtherInfo());
        if (JsonUtils.writeValueAsString((Object)oldOptionData.getReclassifySubjectMappings()).equals(reclassifySubjectMappingStr) && JsonUtils.writeValueAsString((Object)oldOptionData.getReclassifyOtherInfo()).equals(reclassifyOtherInfoStr)) {
            return;
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u4f59\u989d\u91cd\u5206\u7c7b\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("reclassifySubjectMapping:" + reclassifySubjectMappingStr + "\n reclassifyOtherInfo:" + reclassifyOtherInfoStr));
    }

    private void carryOverChangeLog(String systemName, ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        if (oldOptionData.getCarryOverConformRuleAdjustOffsets() == null) {
            oldOptionData.setCarryOverConformRuleAdjustOffsets(Boolean.valueOf(false));
        }
        if (newOptionVO.getCarryOverConformRuleAdjustOffsets() == null) {
            newOptionVO.setCarryOverConformRuleAdjustOffsets(Boolean.valueOf(false));
        }
        if (!this.carryOverChange(oldOptionData, newOptionVO)) {
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append("\u79d1\u76ee\u6620\u5c04:").append(JsonUtils.writeValueAsString((Object)newOptionVO.getCarryOverSubjectCodeMapping())).append(";");
        message.append("\u7ed3\u8f6c\u4e3a\u5e74\u521d\u672a\u5206\u914d\u5229\u6da6\u79d1\u76ee:").append(CollectionUtils.toString((List)newOptionVO.getCarryOverSubjectCodes())).append(";");
        message.append("\u5e74\u521d\u672a\u5206\u914d\u5229\u6da6\u79d1\u76ee:").append(newOptionVO.getCarryOverUndisProfitSubjectCode()).append(";");
        message.append("\u7ed3\u8f6c\u89c4\u5219:").append(CollectionUtils.toString((List)newOptionVO.getCarryOverRuleIds())).append(";");
        message.append("\u8f93\u5165\u8c03\u6574\u7b26\u5408\u89c4\u5219\u62b5\u9500\u5206\u5f55:").append(newOptionVO.getCarryOverConformRuleAdjustOffsets() != false ? "\u662f" : "\u5426").append(";");
        message.append("\u6c47\u603b\u89c4\u5219:").append(CollectionUtils.toString((List)newOptionVO.getCarryOverSumRuleIds())).append(";");
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u62b5\u9500\u5206\u5f55\u8de8\u5e74\u7ed3\u8f6c\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)message.toString());
    }

    private boolean carryOverChange(ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        Boolean newCarryOverConformRuleAdjustOffsets;
        Boolean carryOverConformRuleAdjustOffsets = oldOptionData.getCarryOverConformRuleAdjustOffsets();
        if (!carryOverConformRuleAdjustOffsets.equals(newCarryOverConformRuleAdjustOffsets = newOptionVO.getCarryOverConformRuleAdjustOffsets())) {
            return true;
        }
        if (!this.isEquals(oldOptionData.getCarryOverUndisProfitSubjectCode(), newOptionVO.getCarryOverUndisProfitSubjectCode())) {
            return true;
        }
        if (!this.collectionIsEquals(oldOptionData.getCarryOverSubjectCodes(), newOptionVO.getCarryOverSubjectCodes())) {
            return true;
        }
        if (!this.collectionIsEquals(oldOptionData.getCarryOverRuleIds(), newOptionVO.getCarryOverRuleIds())) {
            return true;
        }
        Map carryOverSubjectCodeMapping = oldOptionData.getCarryOverSubjectCodeMapping();
        Map newCarryOverSubjectCodeMapping = newOptionVO.getCarryOverSubjectCodeMapping();
        if (!JsonUtils.writeValueAsString((Object)carryOverSubjectCodeMapping).equals(JsonUtils.writeValueAsString((Object)newCarryOverSubjectCodeMapping))) {
            return true;
        }
        return !this.collectionIsEquals(oldOptionData.getCarryOverSumRuleIds(), newOptionVO.getCarryOverSumRuleIds());
    }

    private void monthlyIncrementChangeLog(String systemName, ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        if (oldOptionData.getMonthlyIncrement() == null) {
            oldOptionData.setMonthlyIncrement(Boolean.valueOf(false));
        }
        if (newOptionVO.getMonthlyIncrement() == null) {
            newOptionVO.setMonthlyIncrement(Boolean.valueOf(false));
        }
        if (!this.monthlyIncrementChange(oldOptionData, newOptionVO)) {
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append("\u6295\u8d44\u62b5\u9500\u542f\u7528\u6309\u6708\u589e\u91cf\u65b9\u5f0f:").append(newOptionVO.getMonthlyIncrement() != false ? "\u662f" : "\u5426").append(";");
        message.append("\u89c4\u5219\uff1a").append(CollectionUtils.toString((List)newOptionVO.getMonthlyIncrementRuleIds())).append(";");
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u6309\u6708\u589e\u91cf\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)message.toString());
    }

    private boolean monthlyIncrementChange(ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        if (!oldOptionData.getMonthlyIncrement().equals(newOptionVO.getMonthlyIncrement())) {
            return true;
        }
        if (!oldOptionData.getMonthlyIncrement().booleanValue()) {
            return false;
        }
        return !this.collectionIsEquals(oldOptionData.getMonthlyIncrementRuleIds(), newOptionVO.getMonthlyIncrementRuleIds());
    }

    private void lossGainChangeLog(String systemName, ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        if (!this.lossGainChange(oldOptionData, newOptionVO)) {
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append("\u672a\u5206\u914d\u5229\u6da6:").append(newOptionVO.getUndistributedProfitSubjectCode()).append(";");
        message.append("\u4e2d\u95f4\u79d1\u76ee:").append(newOptionVO.getIntermediateSubjectCode()).append(";");
        message.append("\u7ba1\u7406\u4f1a\u8ba1\u5b57\u6bb5:").append(CollectionUtils.toString((List)newOptionVO.getManagementAccountingFieldCodes())).append(";");
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u9009\u9879", (String)("\u7ed3\u8f6c\u635f\u76ca\u8bbe\u7f6e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)message.toString());
    }

    private boolean lossGainChange(ConsolidatedOptionVO oldOptionData, ConsolidatedOptionVO newOptionVO) {
        if (!this.isEquals(oldOptionData.getUndistributedProfitSubjectCode(), newOptionVO.getUndistributedProfitSubjectCode())) {
            return true;
        }
        if (!this.isEquals(oldOptionData.getIntermediateSubjectCode(), newOptionVO.getIntermediateSubjectCode())) {
            return true;
        }
        return !this.collectionIsEquals(oldOptionData.getManagementAccountingFieldCodes(), newOptionVO.getManagementAccountingFieldCodes());
    }

    private boolean collectionIsEquals(List<String> collection1, List<String> collection2) {
        if (collection1 == null) {
            collection1 = new ArrayList<String>();
        }
        if (collection2 == null) {
            collection2 = new ArrayList<String>();
        }
        if (collection1.size() != collection2.size()) {
            return false;
        }
        for (int i = 0; i < collection1.size(); ++i) {
            if (this.isEquals(collection1.get(i), collection2.get(i))) continue;
            return false;
        }
        return true;
    }

    private boolean isEquals(String string1, String string2) {
        if (string1 == null) {
            string1 = "";
        }
        if (string2 == null) {
            string2 = "";
        }
        return string1.equals(string2);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteOptionData(String systemId) {
        this.consolidatedOptionDao.deleteBySystemId(systemId);
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedOptionChangedEvent(new ConsolidatedOptionChangedEvent.ConsolidatedOptionChangedInfo(systemId), context));
    }

    @Override
    public Object getOptionItem(String systemId, String code) {
        String[] filedNames;
        ConsolidatedOptionVO optionVO = this.getOptionData(systemId);
        for (String filed : filedNames = ConsolidatedOptionServiceImpl.getFiledName(optionVO)) {
            if (!filed.equals(code)) continue;
            return ConsolidatedOptionServiceImpl.getFieldValueByName(filed, optionVO);
        }
        return null;
    }

    @Override
    public boolean getOptionItemBooleanBySchemeId(String schemeId, String periodStr, String code) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
        if (StringUtils.isEmpty((String)systemId)) {
            return false;
        }
        Object value = this.getOptionItem(systemId, code);
        return "1".equals(value) || Boolean.TRUE.equals(value);
    }

    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[0]);
            Object value = method.invoke(o, new Object[0]);
            return value;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<FieldDefineVO> getFieldDefineTree(String systemId, String tablename) {
        ArrayList<FieldDefineVO> fieldDefineVOs = new ArrayList<FieldDefineVO>();
        try {
            List<DimensionVO> dimensions = this.getDimensionsByTableName("GC_OFFSETVCHRITEM", systemId);
            Set dimensionCodes = dimensions.stream().map(DimensionVO::getCode).collect(Collectors.toSet());
            TableModelDefine tableDefine = ((DataModelService)SpringContextUtils.getBean(DataModelService.class)).getTableModelDefineByName(tablename);
            if (null == tableDefine) {
                return null;
            }
            List fieldDefines = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            for (ColumnModelDefine c : fieldDefines) {
                if (StringUtils.isEmpty((String)c.getTitle()) || c.getColumnType() != ColumnModelType.STRING || !dimensionCodes.contains(c.getCode())) continue;
                FieldDefineVO fieldDefineVO = new FieldDefineVO();
                BeanUtils.copyProperties(c, fieldDefineVO);
                fieldDefineVOs.add(fieldDefineVO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fieldDefineVOs;
    }

    private ConsolidatedOptionVO convertEO2VO(ConsolidatedOptionEO eo) {
        ConsolidatedOptionVO vo = new ConsolidatedOptionVO();
        BeanUtils.copyProperties((Object)eo, vo);
        return vo;
    }

    @Override
    public List<DimensionVO> getDimensionsByTableName(String tableName, String schemeId, String periodStr) {
        String systemId = this.taskService.getSystemIdBySchemeId(schemeId, periodStr);
        return this.getDimensionsByTableName(tableName, systemId);
    }

    @Override
    public List<DimensionVO> getAllDimensionsByTableName(String tableName, String schemeId, String periodStr) {
        String systemId = this.taskService.getSystemIdBySchemeId(schemeId, periodStr);
        return this.getAllDimensionsByTableName(tableName, systemId);
    }

    @Override
    public List<DimensionVO> getDimensionsByTableName(String tableName, String systemId) {
        List tableDimensionList = this.dimensionService.findDimFieldsByTableName(tableName);
        return this.getDimensions(systemId, tableDimensionList);
    }

    @Override
    public List<DimensionVO> getAllDimensionsByTableName(String tableName, String systemId) {
        List tableDimensionList = this.dimensionService.findAllDimFieldsByTableName(tableName);
        return this.getDimensions(systemId, tableDimensionList);
    }

    private List<DimensionVO> getDimensions(String systemId, List<DimensionEO> tableDimensionList) {
        Assert.isNotEmpty((String)systemId, (String)"\u672a\u5173\u8054\u4f53\u7cfb", (Object[])new Object[0]);
        List<ManagementDim> systemDimensionList = ((ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class)).getManagementDimsBySystemId(systemId);
        if (CollectionUtils.isEmpty(systemDimensionList)) {
            return new ArrayList<DimensionVO>();
        }
        Map tableDimIdToEoMap = tableDimensionList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        ArrayList<DimensionVO> resultDimensionVOS = new ArrayList<DimensionVO>();
        for (ManagementDim dimensionEO : systemDimensionList) {
            if (!tableDimIdToEoMap.containsKey(dimensionEO.getId())) continue;
            Boolean nullAble = dimensionEO.getNullAble();
            DimensionVO dimensionVO = new DimensionVO();
            BeanUtils.copyProperties(dimensionEO, dimensionVO);
            dimensionVO.setDictTableName(this.getTableName(dimensionEO.getReferTable()));
            dimensionVO.setNullAble(nullAble);
            DimensionEO dimension = (DimensionEO)tableDimIdToEoMap.get(dimensionEO.getId());
            dimensionVO.setFieldType(dimension.getFieldType());
            dimensionVO.setTitle(dimension.getTitle());
            dimensionVO.setFieldPrecision(dimension.getFieldDecimal());
            resultDimensionVOS.add(dimensionVO);
        }
        return resultDimensionVOS;
    }

    private String getTableName(String tableId) {
        if (tableId == null) {
            return "";
        }
        TableModelDefine table = this.dataModelService.getTableModelDefineById(tableId);
        if (table == null) {
            return "";
        }
        return table.getName();
    }
}

