/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.datamodelmanage.web.facade.ColumnModelVO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataInputTypeEnum
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerDataLinkEntity
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 *  com.jiuqi.gcreport.organization.impl.service.GcOrgDataService
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  org.activiti.engine.impl.util.CollectionUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.datamodelmanage.web.facade.ColumnModelVO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.dao.IntermediateLibraryDao;
import com.jiuqi.gcreport.intermediatelibrary.entity.FieldDataRegionEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILOrgEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSetupCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSyncCondition;
import com.jiuqi.gcreport.intermediatelibrary.enums.IntermediateLibraryEnums;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryService;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateProgrammeService;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataInputTypeEnum;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerDataLinkEntity;
import com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.activiti.engine.impl.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class IntermediateProgrammeServiceImpl
implements IntermediateProgrammeService {
    @Autowired
    private IntermediateLibraryDao intermediateLibraryDao;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IRuntimeFormGroupService iRuntimeFormGroupService;
    @Autowired
    private DesignDataModelService designDataModeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IntermediateLibraryService intermediateLibraryService;
    @Autowired
    private GcOrgDataService gcOrgDataService;
    private static final Logger logger = LoggerFactory.getLogger(IntermediateProgrammeServiceImpl.class);

    @Override
    public List<ILVO> getAllProgramme() {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ILEntity> iLEntityList = this.intermediateLibraryDao.getAllProgramme();
        ArrayList<ILVO> iLVOList = new ArrayList<ILVO>();
        for (ILEntity iLEntity : iLEntityList) {
            ILVO iLVO = new ILVO();
            BeanUtils.copyProperties(iLEntity, iLVO);
            iLVO.setCurrentTime(Timestamp.valueOf(simpleDate.format(new Date())));
            iLVOList.add(iLVO);
        }
        return iLVOList;
    }

    @Override
    public ILVO getProgrammeForId(ILCondition iLCondition) {
        ILEntity iLEntity = this.intermediateLibraryDao.getProgrammeForId(iLCondition.getId());
        List<ILOrgEntity> iLOrgEntityList = this.intermediateLibraryDao.getAllOrgIdForProgrammeId(iLCondition.getId());
        List<String> orgIdList = iLOrgEntityList.stream().map(ILOrgEntity::getOrgId).collect(Collectors.toList());
        ILVO iLVO = new ILVO();
        BeanUtils.copyProperties(iLEntity, iLVO);
        iLVO.setOrgIdList(orgIdList);
        OrgDataParam param = new OrgDataParam();
        param.setAuthType(OrgDataOption.AuthType.NONE.toString());
        param.setOrgType("MD_ORG");
        List orgDataDOList = this.gcOrgDataService.list(param);
        iLVO.setOrgDataVOList(this.convert(orgDataDOList, orgIdList));
        return iLVO;
    }

    private List<OrgDataVO> convert(List<OrgDataDO> orgDataDOList, List<String> orgIdList) {
        ArrayList<OrgDataVO> orgDataVOList = new ArrayList<OrgDataVO>();
        orgDataDOList.forEach(orgDataDO -> {
            if (orgIdList.contains(orgDataDO.getCode())) {
                orgDataVOList.add(this.gcOrgDataService.convert(orgDataDO));
            }
        });
        return orgDataVOList;
    }

    @Override
    public String addProgramme(ILCondition iLCondition) {
        String programmeId;
        try {
            programmeId = this.intermediateLibraryDao.addProgramme(iLCondition);
            iLCondition.setId(programmeId);
            this.intermediateLibraryDao.addProgrammeOfOrgId(iLCondition);
            if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(iLCondition.getSourceType()) && !CollectionUtils.isEmpty((Collection)iLCondition.getOrgIdList())) {
                try {
                    this.handleConnIsSuccess(iLCondition);
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
                List<GcOrgCacheVO> orgToJsonVOList = this.getOrgToJsonVOList(iLCondition);
                this.intermediateLibraryDao.addOrgInfo(orgToJsonVOList, iLCondition);
            }
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u65b0\u589e-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011"), (String)"\u65b0\u589e\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002", e);
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u65b0\u589e-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011"), (String)"\u65b0\u589e\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002");
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.add.programme.error"));
        }
        return programmeId;
    }

    private List<GcOrgCacheVO> getOrgToJsonVOList(ILCondition iLCondition) throws Exception {
        String[] taskIdArray;
        ArrayList<GcOrgCacheVO> gcOrgCacheVOList = new ArrayList<GcOrgCacheVO>();
        ArrayList gcOrgCacheVOCodeList = new ArrayList();
        for (String taskId : taskIdArray = iLCondition.getTaskId().split(",")) {
            String orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)taskId);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.ACCESS);
            iLCondition.getOrgIdList().forEach(orgId -> {
                GcOrgCacheVO gcOrgCacheVO = tool.getOrgByCode(orgId);
                if (gcOrgCacheVO != null && !gcOrgCacheVOCodeList.contains(gcOrgCacheVO.getCode())) {
                    gcOrgCacheVOList.add(gcOrgCacheVO);
                    gcOrgCacheVOCodeList.add(gcOrgCacheVO.getCode());
                }
            });
        }
        return gcOrgCacheVOList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void handleConnIsSuccess(ILCondition iLCondition) throws Exception {
        Connection connection = null;
        JdbcTemplate jdbcTemplate = null;
        try {
            jdbcTemplate = ((IntermediateLibraryDao)SpringContextUtils.getBean(IntermediateLibraryDao.class)).getJdbcTemplate(iLCondition.getLibraryDataSource());
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(jdbcTemplate.getDataSource()));
            String tableName = iLCondition.getTablePrefix().toUpperCase() + "_MD_ORG";
            ResultSet set = connection.getMetaData().getTables(null, null, tableName, null);
            if (!set.next()) {
                this.intermediateLibraryDao.createOrgInfo(iLCondition);
            }
            if (jdbcTemplate == null) return;
        }
        catch (Exception e) {
            try {
                logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8fde\u63a5\u6216\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u521b\u5efa\u7ec4\u7ec7\u5355\u4f4d\u8868\u5931\u8d25\u3002", e);
                throw new Exception(GcI18nUtil.getMessage((String)"gc.intermediate.jdbc.org.error"));
            }
            catch (Throwable throwable) {
                if (jdbcTemplate == null) throw throwable;
                DataSourceUtils.releaseConnection(connection, (DataSource)jdbcTemplate.getDataSource());
                throw throwable;
            }
        }
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)jdbcTemplate.getDataSource());
        return;
    }

    @Override
    public ILEntity getProgrammeOfName(ILCondition iLCondition) {
        return this.intermediateLibraryDao.getProgrammeForName(iLCondition.getProgrammeName());
    }

    @Override
    public void updateProgramme(ILCondition iLCondition) {
        try {
            this.intermediateLibraryDao.updateProgramme(iLCondition);
            if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(iLCondition.getSourceType()) && !CollectionUtils.isEmpty((Collection)iLCondition.getOrgIdList())) {
                try {
                    this.handleConnIsSuccess(iLCondition);
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
                List<GcOrgCacheVO> orgToJsonVOList = this.getOrgToJsonVOList(iLCondition);
                this.intermediateLibraryDao.addOrgInfo(orgToJsonVOList, iLCondition);
            }
            this.intermediateLibraryDao.deleteProgrammeOfOrgId(iLCondition);
            this.intermediateLibraryDao.addProgrammeOfOrgId(iLCondition);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u4fee\u6539-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011"), (String)"\u4fee\u6539\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u4fee\u6539\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002", e);
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u4fee\u6539-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011"), (String)"\u4fee\u6539\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002");
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.update.programme.error"));
        }
    }

    @Override
    public void deleteProgramme(ILCondition iLCondition) {
        ILEntity iLEntity = this.intermediateLibraryDao.getProgrammeForId(iLCondition.getId());
        try {
            this.intermediateLibraryDao.deleteProgramme(iLCondition);
            this.intermediateLibraryDao.deleteProgrammeOfOrgId(iLCondition);
            this.intermediateLibraryDao.deleteAllProgrammeOfField(iLCondition);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u5220\u9664-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLEntity.getProgrammeName() + "\u3011"), (String)"\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002", e);
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u5220\u9664-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLEntity.getProgrammeName() + "\u3011"), (String)"\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002");
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.delete.programme.error"));
        }
    }

    @Override
    public void addProgrammeOfField(ILCondition iLCondition, boolean getFull) {
        ILEntity ilEntity = this.intermediateLibraryDao.getProgrammeForId(iLCondition.getId());
        try {
            Set<String> fieldIdListInsert = this.extractedFieldId(iLCondition);
            List<String> fieldVOList = this.intermediateLibraryDao.getFieldIdOfProgrammeId(iLCondition.getId());
            ILCondition fieldCondition = new ILCondition();
            fieldCondition.setId(iLCondition.getId());
            if (getFull) {
                fieldCondition.setFieldIdList(fieldVOList);
                this.intermediateLibraryDao.deleteFieldOfProgrammeId(fieldCondition);
            }
            fieldCondition.setFieldIdList(new ArrayList<String>(fieldIdListInsert));
            this.intermediateLibraryDao.addProgrammeOfField(fieldCondition);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u65b0\u589e-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + ilEntity.getProgrammeName() + "\u3011\u6307\u6807"), (String)"\u65b0\u589e\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6307\u6807\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6307\u6807\u5f02\u5e38\u3002", e);
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u65b0\u589e-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + ilEntity.getProgrammeName() + "\u3011\u6307\u6807"), (String)"\u65b0\u589e\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6307\u6807\u5f02\u5e38\u3002");
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.add.programme.field.error"));
        }
    }

    private Set<String> extractedFieldId(ILCondition iLCondition) {
        HashSet<String> fieldIdListInsert = new HashSet<String>();
        iLCondition.getFieldIdList().stream().forEach(fieldId -> {
            int leftBracket = fieldId.indexOf(91);
            int rightBracket = fieldId.indexOf(93);
            if (leftBracket != -1 && rightBracket != -1) {
                String tableCode = fieldId.substring(0, leftBracket);
                String fieldCode = fieldId.substring(leftBracket + 1, rightBracket);
                try {
                    TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
                    if (tableDefine != null) {
                        String tableKey = tableDefine.getKey();
                        FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableKey);
                        fieldIdListInsert.add(fieldDefine.getKey());
                    }
                }
                catch (Exception e) {
                    logger.error("\u683c\u5f0f\u5316\u6307\u6807\u6570\u636e\u5f02\u5e38\u3002", e);
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.format"));
                }
            }
        });
        iLCondition.getFormTreeKeyList().stream().forEach(formId -> fieldIdListInsert.addAll(this.iRunTimeViewController.getFieldKeysInForm(formId)));
        return fieldIdListInsert;
    }

    @Override
    public List<ILTreeVO> getFieldGroupMessage(ILFieldCondition intermediateLibraryFieldCondition) {
        Map<String, ILTreeVO> formMap = this.handleRepeatForm(intermediateLibraryFieldCondition.getProgrammeId());
        Map<String, ILTreeVO> schemeMap = this.handleRepeatScheme(formMap);
        List<ILTreeVO> ilTreeVOS = this.handleRepeatTask(schemeMap);
        return this.handleGroupMessage(ilTreeVOS);
    }

    private Map<String, ILTreeVO> handleRepeatForm(String programmeId) {
        HashMap<String, ILTreeVO> formMap = new HashMap<String, ILTreeVO>();
        ArrayList fieldIdList = new ArrayList();
        List<FieldDataRegionEntity> entityList = this.intermediateLibraryDao.getDataRegionKeyForId(programmeId);
        entityList.stream().forEach(entity -> {
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(entity.getDataRegionKey());
            if (dataRegionDefine == null) {
                this.addNoDataRegionDefineFieldId(fieldIdList, entity.getFieldId(), programmeId);
            } else {
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
                if (formMap.get(formDefine.getKey()) != null) {
                    ((ILTreeVO)formMap.get(formDefine.getKey())).getFieldIdSet().add(entity.getFieldId());
                } else {
                    ILTreeVO fromEntity = new ILTreeVO();
                    fromEntity.setId(formDefine.getKey());
                    fromEntity.setTitle(formDefine.getTitle());
                    fromEntity.getFieldIdSet().add(entity.getFieldId());
                    fromEntity.setCode(formDefine.getFormCode());
                    formMap.put(formDefine.getKey(), fromEntity);
                }
            }
        });
        if (!CollectionUtils.isEmpty(fieldIdList)) {
            ILCondition iLCondition = new ILCondition();
            iLCondition.setProgrammeId(programmeId);
            iLCondition.setFieldIdList(fieldIdList);
            this.deleteFieldOfProgrammeId(iLCondition);
        }
        return formMap;
    }

    private void deleteFieldOfProgrammeId(ILCondition iLCondition) {
        this.intermediateLibraryDao.deleteFieldOfProgrammeId(iLCondition);
    }

    private void addNoDataRegionDefineFieldId(List<String> fieldIdList, String fieldId, String programmeId) {
        fieldIdList.add(fieldId);
        ILEntity ilEntity = this.intermediateLibraryDao.getProgrammeForId(programmeId);
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(fieldId);
        }
        catch (Exception e) {
            logger.error("\u6307\u6807\u67e5\u8be2\u5f02\u5e38:" + fieldId, e);
        }
        if (fieldDefine != null) {
            logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + ilEntity.getProgrammeName() + "\u3011\u6307\u6807\u3010" + fieldDefine.getCode() + "\u3011[" + fieldDefine.getKey() + "]\u5728\u62a5\u8868\u4e2d\u4e0d\u5b58\u5728\u3002");
        } else {
            logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + ilEntity.getProgrammeName() + "\u3011\u6307\u6807[" + fieldId + "]\u5728\u62a5\u8868\u4e2d\u4e0d\u5b58\u5728\u3002");
        }
    }

    private Map<String, ILTreeVO> handleRepeatScheme(Map<String, ILTreeVO> formMap) {
        HashMap<String, ILTreeVO> schemeMap = new HashMap<String, ILTreeVO>();
        formMap.forEach((key, value) -> {
            try {
                List schemeDefines = this.iRunTimeViewController.queryFormSchemeByForm(key);
                schemeDefines.stream().forEach(schemeDefine -> {
                    if (schemeMap.get(schemeDefine.getKey()) != null) {
                        Set fieldList = ((ILTreeVO)schemeMap.get(schemeDefine.getKey())).getFieldIdSet();
                        fieldList.addAll(value.getFieldIdSet());
                        ((ILTreeVO)schemeMap.get(schemeDefine.getKey())).setFieldIdSet(new HashSet(fieldList));
                        ((ILTreeVO)schemeMap.get(schemeDefine.getKey())).getChildren().add(value);
                    } else {
                        ILTreeVO schemeEntity = new ILTreeVO();
                        schemeEntity.setId(schemeDefine.getKey());
                        schemeEntity.setTitle(schemeDefine.getTitle());
                        schemeEntity.setTaskId(schemeDefine.getTaskKey());
                        schemeEntity.getChildren().add(value);
                        schemeEntity.getFieldIdSet().addAll(value.getFieldIdSet());
                        schemeMap.put(schemeDefine.getKey(), schemeEntity);
                    }
                });
            }
            catch (Exception e) {
                logger.error("\u6839\u636e\u62a5\u8868\u83b7\u53d6\u65b9\u6848\u5f02\u5e38\u3002", e);
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.programme.form.get.error"));
            }
        });
        return schemeMap;
    }

    private List<ILTreeVO> handleGroupMessage(List<ILTreeVO> ilTreeVOS) {
        ArrayList<ILTreeVO> ilTreeVOList = new ArrayList<ILTreeVO>();
        for (ILTreeVO ilTreeVO : ilTreeVOS) {
            ILTreeVO ilTree = new ILTreeVO();
            ilTree.setId(ilTreeVO.getId());
            ilTree.setTitle(ilTreeVO.getTitle());
            ilTree.setParentId(ilTreeVO.getParentId());
            ArrayList<ILTreeVO> voList = new ArrayList<ILTreeVO>();
            for (ILTreeVO schemeEntity : ilTreeVO.getChildren()) {
                HashMap groupMap = new HashMap();
                ILTreeVO schemeTree = new ILTreeVO();
                for (ILTreeVO formEntity : schemeEntity.getChildren()) {
                    schemeTree.setId(schemeEntity.getId());
                    schemeTree.setTitle(schemeEntity.getTitle());
                    schemeTree.setParentId(schemeEntity.getParentId());
                    schemeTree.setTaskId(schemeEntity.getTaskId());
                    schemeTree.setCode(schemeEntity.getCode());
                    schemeTree.setFieldIdSet(schemeEntity.getFieldIdSet());
                    List formGroupsByForm = this.iRuntimeFormGroupService.getFormGroupsByForm(formEntity.getId());
                    formGroupsByForm.forEach(formGroupDefine -> {
                        if (groupMap.get(formGroupDefine.getKey()) != null) {
                            Set fieldList = ((ILTreeVO)groupMap.get(formGroupDefine.getKey())).getFieldIdSet();
                            fieldList.addAll(formEntity.getFieldIdSet());
                            ((ILTreeVO)groupMap.get(formGroupDefine.getKey())).setFieldIdSet(new HashSet(fieldList));
                            ((ILTreeVO)groupMap.get(formGroupDefine.getKey())).getChildren().add(formEntity);
                        } else {
                            ILTreeVO groupEntity = new ILTreeVO();
                            groupEntity.setId(formGroupDefine.getKey());
                            groupEntity.setTitle(formGroupDefine.getTitle());
                            groupEntity.getChildren().add(formEntity);
                            groupEntity.getFieldIdSet().addAll(formEntity.getFieldIdSet());
                            groupMap.put(formGroupDefine.getKey(), groupEntity);
                            schemeTree.getChildren().add(groupEntity);
                        }
                    });
                }
                voList.add(schemeTree);
            }
            ilTree.setChildren(voList);
            ilTreeVOList.add(ilTree);
        }
        return ilTreeVOList;
    }

    private List<ILTreeVO> handleRepeatTask(Map<String, ILTreeVO> schemeMap) {
        HashMap taskMap = new HashMap();
        schemeMap.forEach((key, value) -> {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(value.getTaskId());
            if (taskMap.get(taskDefine.getKey()) != null) {
                ((ILTreeVO)taskMap.get(taskDefine.getKey())).getChildren().add(value);
            } else {
                ILTreeVO taskEntity = new ILTreeVO();
                taskEntity.setId(taskDefine.getKey());
                taskEntity.setTitle(taskDefine.getTitle());
                value.setParentId(taskDefine.getKey());
                taskEntity.getChildren().add(value);
                taskMap.put(taskDefine.getKey(), taskEntity);
            }
        });
        return new ArrayList<ILTreeVO>(taskMap.values());
    }

    @Override
    public ILFieldPageVO getFieldForGroup(ILFieldCondition intermediateLibraryFieldCondition) {
        ILFieldPageVO iLFieldPageVO = new ILFieldPageVO();
        ArrayList fileList = new ArrayList();
        Map<String, ILTreeVO> formMap = this.handleRepeatForm(intermediateLibraryFieldCondition.getProgrammeId());
        ILTreeVO intermediaTreeVO = formMap.get(intermediateLibraryFieldCondition.getId());
        if (intermediaTreeVO != null) {
            this.formFieldSort(intermediaTreeVO);
            intermediaTreeVO.getFieldIdSet().stream().forEach(fieldId -> {
                try {
                    ILFieldVO iLFieldVO = this.getFieldForId((String)fieldId);
                    if (iLFieldVO != null) {
                        fileList.add(iLFieldVO);
                    }
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\u3002", e);
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.get.error"));
                }
            });
            iLFieldPageVO.setILFieldVOList(fileList);
            iLFieldPageVO.setCount(fileList.size());
            return iLFieldPageVO;
        }
        Map<String, ILTreeVO> schemeMap = this.handleRepeatScheme(formMap);
        ILTreeVO intermediaTreeSchemeVO = schemeMap.get(intermediateLibraryFieldCondition.getId());
        if (intermediaTreeSchemeVO != null) {
            intermediaTreeSchemeVO.getFieldIdSet().stream().forEach(fieldId -> {
                try {
                    ILFieldVO iLFieldVO = this.getFieldForId((String)fieldId);
                    if (iLFieldVO != null) {
                        fileList.add(iLFieldVO);
                    }
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\u3002", e);
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.get.error"));
                }
            });
            iLFieldPageVO.setILFieldVOList(fileList);
            iLFieldPageVO.setCount(fileList.size());
            return iLFieldPageVO;
        }
        Map<String, ILTreeVO> groupMap = this.handleRepeatGroup(formMap);
        ILTreeVO intermediaTreeGroupVO = groupMap.get(intermediateLibraryFieldCondition.getId());
        if (intermediaTreeGroupVO != null) {
            intermediaTreeGroupVO.getFieldIdSet().forEach(fieldId -> {
                try {
                    fileList.add(this.getFieldForId((String)fieldId));
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\u3002", e);
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.get.error"));
                }
            });
            iLFieldPageVO.setILFieldVOList(fileList);
            iLFieldPageVO.setCount(fileList.size());
            return iLFieldPageVO;
        }
        ArrayList fieldIdList = new ArrayList();
        schemeMap.forEach((key, value) -> {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(value.getTaskId());
            if (intermediateLibraryFieldCondition.getId().equals(taskDefine.getKey())) {
                fieldIdList.addAll(new ArrayList(value.getFieldIdSet()));
            }
        });
        List fieldIdListDis = fieldIdList.stream().distinct().collect(Collectors.toList());
        fieldIdListDis.stream().forEach(fieldId -> {
            try {
                ILFieldVO iLFieldVO = this.getFieldForId((String)fieldId);
                if (iLFieldVO != null) {
                    fileList.add(iLFieldVO);
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\u3002", e);
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.get.error"));
            }
        });
        iLFieldPageVO.setILFieldVOList(fileList);
        iLFieldPageVO.setCount(fileList.size());
        return iLFieldPageVO;
    }

    protected Map<String, ILTreeVO> handleRepeatGroup(Map<String, ILTreeVO> formMap) {
        HashMap<String, ILTreeVO> groupMap = new HashMap<String, ILTreeVO>();
        formMap.forEach((key, value) -> {
            try {
                List groupDefines = this.iRuntimeFormGroupService.getFormGroupsByForm(key);
                groupDefines.forEach(groupDefine -> {
                    if (groupMap.get(groupDefine.getKey()) != null) {
                        Set fieldList = ((ILTreeVO)groupMap.get(groupDefine.getKey())).getFieldIdSet();
                        fieldList.addAll(value.getFieldIdSet());
                        ((ILTreeVO)groupMap.get(groupDefine.getKey())).setFieldIdSet(new HashSet(fieldList));
                        ((ILTreeVO)groupMap.get(groupDefine.getKey())).getChildren().add(value);
                    } else {
                        ILTreeVO groupEntity = new ILTreeVO();
                        groupEntity.setId(groupDefine.getKey());
                        groupEntity.setTitle(groupDefine.getTitle());
                        groupEntity.getChildren().add(value);
                        groupEntity.getFieldIdSet().addAll(value.getFieldIdSet());
                        groupMap.put(groupDefine.getKey(), groupEntity);
                    }
                });
            }
            catch (Exception e) {
                logger.error("\u6839\u636e\u62a5\u8868\u83b7\u53d6\u5206\u7ec4\u5f02\u5e38\u3002", e);
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.form.group"));
            }
        });
        return groupMap;
    }

    private void formFieldSort(ILTreeVO ilTreeVO) {
        HashMap tableName2TableDefineMap = new HashMap();
        ArrayList columnModelVOList = new ArrayList();
        HashMap fieldMap = new HashMap();
        ilTreeVO.getFieldIdSet().forEach(fieldId -> {
            try {
                FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(fieldId);
                if (fieldDefine == null) {
                    return;
                }
                TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                if (tableDefine == null) {
                    return;
                }
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableDefine.getCode());
                if (tableModelDefine == null) {
                    return;
                }
                fieldMap.put(fieldDefine.getCode(), fieldId);
                if (tableName2TableDefineMap.get(tableModelDefine.getName()) == null) {
                    tableName2TableDefineMap.put(tableModelDefine.getName(), tableDefine);
                    List<ColumnModelVO> columnModelVOS = this.queryFieldByTable(tableDefine.getKey());
                    if (columnModelVOS == null || columnModelVOS.size() == 0) {
                        columnModelVOS = this.queryFieldByTable(tableModelDefine.getID());
                    }
                    if (columnModelVOS != null) {
                        columnModelVOList.addAll(columnModelVOS);
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u6307\u6807\u6392\u5e8f\u5f02\u5e38", e);
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.sort.error"));
            }
        });
        ArrayList fields = new ArrayList();
        columnModelVOList.forEach(cmv -> {
            if (fieldMap.get(cmv.getFieldName()) != null) {
                fields.add(fieldMap.get(cmv.getFieldName()));
            }
        });
        if (CollectionUtil.isEmpty(fields)) {
            return;
        }
        ilTreeVO.setFieldIdSet(new LinkedHashSet(fields));
    }

    private List<ColumnModelVO> queryFieldByTable(String tableId) {
        DesignTableModelDefine tableModelDefine = this.designDataModeController.getTableModelDefine(tableId);
        List columnModelDefines = this.designDataModeController.getColumnModelDefinesByTable(tableId);
        if (columnModelDefines != null) {
            ArrayList<ColumnModelVO> vos = new ArrayList<ColumnModelVO>(columnModelDefines.size());
            for (DesignColumnModelDefine field : columnModelDefines) {
                try {
                    String name = "";
                    if (field.getReferTableID() != null && field.getReferColumnID() != null) {
                        String tableTitle = this.designDataModeController.getTableModelDefine(field.getReferTableID()).getTitle();
                        String columnTitle = this.designDataModeController.getColumnModelDefine(field.getReferColumnID()).getTitle();
                        name = tableTitle + "#" + columnTitle;
                    }
                    ColumnModelVO fieldVO = new ColumnModelVO(field, name);
                    fieldVO.setIsBizk(!StringUtils.isEmpty((String)tableModelDefine.getBizKeys()) && tableModelDefine.getBizKeys().contains(fieldVO.getKey()));
                    vos.add(fieldVO);
                }
                catch (Exception e) {
                    logger.error("\u6307\u6807\u6392\u5e8f\u5f02\u5e38", e);
                }
            }
            return vos.stream().sorted(Comparator.comparing(ColumnModelVO::getOrder)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<ZbPickerEntity> getZbPickerList(ILFieldCondition iLFieldCondition) {
        Map<String, ZbPickerEntity> formMap = this.handleZbPickerRepeatForm(iLFieldCondition.getProgrammeId());
        return this.handleZbPickerRepeatScheme(formMap);
    }

    private Map<String, ZbPickerEntity> handleZbPickerRepeatForm(String programmeId) {
        List<FieldDataRegionEntity> entityList = this.intermediateLibraryDao.getDataRegionKeyForId(programmeId);
        HashMap<String, ZbPickerEntity> formMap = new HashMap<String, ZbPickerEntity>();
        ArrayList fieldIdList = new ArrayList();
        entityList.stream().forEach(entity -> {
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(entity.getDataRegionKey());
            if (dataRegionDefine == null) {
                this.addNoDataRegionDefineFieldId(fieldIdList, entity.getFieldId(), programmeId);
                return;
            }
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
            List dataLinkDefineList = this.iRunTimeViewController.getLinksInFormByField(formDefine.getKey(), entity.getFieldId());
            for (DataLinkDefine dataLinkDefine : dataLinkDefineList) {
                ZbPickerDataLinkEntity zbPickerDataLinkEntity = new ZbPickerDataLinkEntity();
                zbPickerDataLinkEntity.setHeight(1);
                zbPickerDataLinkEntity.setWidth(1);
                zbPickerDataLinkEntity.setX(dataLinkDefine.getPosX());
                zbPickerDataLinkEntity.setY(dataLinkDefine.getPosY());
                try {
                    FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(entity.getFieldId());
                    if (fieldDefine == null) continue;
                    if (fieldDefine.getOwnerTableKey() == null) {
                        System.out.println(fieldDefine.getTitle());
                    }
                    TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                    if (formMap.get(formDefine.getKey()) != null) {
                        ((ZbPickerEntity)formMap.get(formDefine.getKey())).getSelections().add(zbPickerDataLinkEntity);
                        ((ZbPickerEntity)formMap.get(formDefine.getKey())).getZbs().add(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]");
                        continue;
                    }
                    ZbPickerEntity zbPickerEntity = new ZbPickerEntity();
                    zbPickerEntity.setFormKey(formDefine.getKey());
                    zbPickerEntity.getSelections().add(zbPickerDataLinkEntity);
                    zbPickerEntity.getZbs().add(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]");
                    formMap.put(formDefine.getKey(), zbPickerEntity);
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\u3002", e);
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.zb.get.error"));
                }
            }
        });
        if (!CollectionUtils.isEmpty(fieldIdList)) {
            ILCondition iLCondition = new ILCondition();
            iLCondition.setProgrammeId(programmeId);
            iLCondition.setFieldIdList(fieldIdList);
            this.deleteFieldOfProgrammeId(iLCondition);
        }
        return formMap;
    }

    private List<ZbPickerEntity> handleZbPickerRepeatScheme(Map<String, ZbPickerEntity> formMap) {
        ArrayList<ZbPickerEntity> zbPickerEntityList = new ArrayList<ZbPickerEntity>();
        formMap.forEach((key, value) -> {
            try {
                List schemeDefines = this.iRunTimeViewController.queryFormSchemeByForm(key);
                schemeDefines.stream().forEach(schemeDefine -> {
                    ZbPickerEntity zbPickerEntity = new ZbPickerEntity();
                    zbPickerEntity.setSchemeKey(schemeDefine.getKey());
                    zbPickerEntity.setZbs(value.getZbs());
                    zbPickerEntity.setTaskId(schemeDefine.getTaskKey());
                    zbPickerEntity.setFormKey(key);
                    zbPickerEntity.setSelections(value.getSelections());
                    zbPickerEntityList.add(zbPickerEntity);
                });
            }
            catch (Exception e) {
                logger.error("\u6839\u636e\u62a5\u8868\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\u3002", e);
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.form.zb.get.error"));
            }
        });
        return zbPickerEntityList;
    }

    @Override
    public void clearProgramme(ILClearCondition clearCondition) {
        try {
            this.intermediateLibraryService.createAsyncTask(clearCondition.getAsyncTaskMonitor(), "\u5f00\u59cb\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4efb\u52a1");
            this.intermediateLibraryService.modifyAsyncTaskState(clearCondition.getAsyncTaskMonitor(), 0.1, "\u5f00\u59cb\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u9a8c\u8bc1");
            List<ILEntity> libraryList = this.intermediateLibraryDao.getAllProgramme();
            int dataTypeCount = 4;
            double stepProgress = this.getClearStepProgress(libraryList.size() * (clearCondition.getEndYear() - clearCondition.getStartYear() + 1) * dataTypeCount);
            clearCondition.setStepProgress(stepProgress);
            clearCondition.setCurrentProgress(0.1);
            for (ILEntity library : libraryList) {
                if (!IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(library.getSourceType())) continue;
                ILSetupCondition iLSetupCondition = this.getProgrammeSyncData(library.getId());
                this.intermediateLibraryService.handleConnIsSuccess(iLSetupCondition.getLibraryDataSource());
                this.intermediateLibraryDao.clearProgrammeForYear(iLSetupCondition, clearCondition, library.getTablePrefix());
            }
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4fe1\u606f-" + clearCondition.getStartYear() + "\u5e74\u81f3" + clearCondition.getEndYear() + "\u5e74"), (String)"\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4fe1\u606f\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4fe1\u606f\u5931\u8d25\u3002", e);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4fe1\u606f-" + clearCondition.getStartYear() + "\u5e74\u81f3" + clearCondition.getEndYear() + "\u5e74"), (String)"\u6e05\u7a7a\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4fe1\u606f\u5931\u8d25\u3002");
        }
    }

    private double getClearStepProgress(int count) {
        double stepProgress = count > 0 ? BigDecimal.valueOf(0.9f / (float)count).setScale(5, 1).doubleValue() : 0.9;
        return stepProgress;
    }

    @Override
    public void synchroProgramme(ILExtractCondition iCondition) {
        ILEntity iLEntity = this.intermediateLibraryDao.getProgrammeForId(iCondition.getProgrammeId());
        try {
            this.intermediateLibraryService.createAsyncTask(iCondition.getAsyncTaskMonitor(), "\u5f00\u59cb\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u4efb\u52a1");
            this.intermediateLibraryService.modifyAsyncTaskState(iCondition.getAsyncTaskMonitor(), 0.1, "\u5f00\u59cb\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u9a8c\u8bc1");
            if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(iLEntity.getSourceType())) {
                ILSetupCondition iLSetupCondition = this.getProgrammeSyncData(iCondition.getProgrammeId());
                this.intermediateLibraryService.handleConnIsSuccess(iLSetupCondition.getLibraryDataSource());
                iCondition.getAsyncTaskMonitor().progressAndMessage(0.2, "");
                this.intermediateLibraryDao.clearProgramme(iLSetupCondition);
                iCondition.getAsyncTaskMonitor().progressAndMessage(0.6, "");
                this.intermediateLibraryDao.synchroProgramme(iLSetupCondition);
                iCondition.getAsyncTaskMonitor().progressAndMessage(1.0, "");
            }
            logger.info("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u6210\u529f\u3002");
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u3010" + iLEntity.getProgrammeName() + "\u3011\u65b9\u6848"), (String)"\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25\u3002", e);
            if (iLEntity == null) {
                LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)"\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f", (String)"\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25\u3002");
            } else {
                LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u3010" + iLEntity.getProgrammeName() + "\u3011\u65b9\u6848"), (String)"\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25\u3002");
            }
            throw new BusinessRuntimeException("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25\u3002");
        }
    }

    private ILSetupCondition getProgrammeSyncData(String programmeId) throws Exception {
        ILSetupCondition iLSetupCondition = new ILSetupCondition();
        ILEntity iLEntity = this.intermediateLibraryDao.getProgrammeForId(programmeId);
        List<ILFieldVO> fileIdList = this.intermediateLibraryDao.getFieldOfProgrammeId(iLEntity.getId());
        iLSetupCondition.setLibraryDataSource(iLEntity.getLibraryDataSource());
        iLSetupCondition.setTablePrefix(iLEntity.getTablePrefix());
        ArrayList<ILSyncCondition> iLSyncConditionList = new ArrayList<ILSyncCondition>();
        Map<String, List<String>> fieldId2DataRegionKeys = this.intermediateLibraryDao.getDataRegionKeyMap();
        block0: for (ILFieldVO fieldVO : fileIdList) {
            List<String> dataRegionKeyList = fieldId2DataRegionKeys.get(fieldVO.getFieldId());
            if (CollectionUtils.isEmpty(dataRegionKeyList)) {
                dataRegionKeyList = this.intermediateLibraryDao.getDataRegionKey(fieldVO.getFieldId());
                fieldId2DataRegionKeys.put(fieldVO.getFieldId(), dataRegionKeyList);
            }
            for (String key : dataRegionKeyList) {
                DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(key);
                FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(fieldVO.getFieldId());
                if (fieldDefine == null) continue;
                ILSyncCondition iLSyncCondition = new ILSyncCondition();
                TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                iLSyncCondition.setCode(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]");
                iLSyncCondition.setTitle(fieldDefine.getTitle());
                iLSyncCondition.setDescr(fieldDefine.getDescription());
                iLSyncCondition.setDataType(this.switchDataType(fieldDefine.getType().getValue()));
                iLSyncCondition.setAccuracy(fieldDefine.getSize());
                iLSyncCondition.setDecimal(fieldDefine.getFractionDigits());
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
                iLSyncCondition.setBelongForm(formDefine.getFormCode() + "[" + formDefine.getTitle() + "]");
                if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind())) {
                    iLSyncConditionList.add(iLSyncCondition);
                    continue block0;
                }
                iLSyncCondition.setFloatarea(dataRegionDefine.getKey());
                iLSyncConditionList.add(iLSyncCondition);
            }
        }
        iLSetupCondition.setILSyncConditionList(iLSyncConditionList);
        return iLSetupCondition;
    }

    private int switchDataType(int type) {
        if (FieldType.FIELD_TYPE_STRING.getValue() == type) {
            return 1;
        }
        if (FieldType.FIELD_TYPE_INTEGER.getValue() == type) {
            return 4;
        }
        if (FieldType.FIELD_TYPE_FLOAT.getValue() == type || FieldType.FIELD_TYPE_DECIMAL.getValue() == type) {
            return 2;
        }
        if (FieldType.FIELD_TYPE_LOGIC.getValue() == type) {
            return 4;
        }
        if (FieldType.FIELD_TYPE_DATE.getValue() == type) {
            return 91;
        }
        if (FieldType.FIELD_TYPE_DATE_TIME.getValue() == type || FieldType.FIELD_TYPE_TIME.getValue() == type) {
            return 93;
        }
        if (FieldType.FIELD_TYPE_GENERAL.getValue() == type) {
            return 2004;
        }
        return 1;
    }

    @Override
    public ILFieldPageVO getFieldOfProgrammeId(ILFieldCondition iLFieldCondition) {
        ILFieldPageVO iLFieldPageVO = new ILFieldPageVO();
        int totalCount = this.intermediateLibraryDao.getFieldCouontOfProgrammeId(iLFieldCondition);
        List<ILFieldVO> fileList = this.intermediateLibraryDao.getFieldOfProgrammeIdPage(iLFieldCondition, totalCount);
        ArrayList<ILFieldVO> fileLists = new ArrayList<ILFieldVO>();
        for (ILFieldVO item : fileList) {
            try {
                ILFieldVO iLFieldVO = this.getFieldForId(item.getFieldId());
                if (iLFieldVO != null) {
                    fileLists.add(iLFieldVO);
                    continue;
                }
                ILCondition iLCondition = new ILCondition();
                iLCondition.setId(iLFieldCondition.getProgrammeId());
                ArrayList<String> fileIdList = new ArrayList<String>();
                fileIdList.add(item.getFieldId());
                iLCondition.setFieldIdList(fileIdList);
                this.intermediateLibraryDao.deleteFieldOfProgrammeId(iLCondition);
                return this.getFieldOfProgrammeId(iLFieldCondition);
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5f02\u5e38\u3002", e);
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.programme.get.error"));
            }
        }
        iLFieldPageVO.setCount(totalCount);
        iLFieldPageVO.setILFieldVOList(fileLists);
        return iLFieldPageVO;
    }

    @Override
    public void deleteProgrammeOfField(ILCondition iLCondition) {
        try {
            this.intermediateLibraryDao.deleteFieldOfProgrammeId(iLCondition);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u5220\u9664-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011\u6307\u6807"), (String)"\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6307\u6807\u6210\u529f\u3002");
        }
        catch (Exception e) {
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u5220\u9664-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011\u6307\u6807"), (String)"\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u6307\u6807\u5931\u8d25\u3002");
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.delete.programme.field.error"));
        }
    }

    @Override
    public void deleteAllProgrammeOfField(ILCondition iLCondition) {
        try {
            this.intermediateLibraryDao.deleteAllProgrammeOfField(iLCondition);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u5220\u9664-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011\u5168\u90e8\u6307\u6807"), (String)"\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5168\u90e8\u6307\u6807\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5168\u90e8\u6307\u6807\u5f02\u5e38\u3002", e);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u8bbe\u7f6e", (String)("\u5220\u9664-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u3010" + iLCondition.getProgrammeName() + "\u3011\u5168\u90e8\u6307\u6807"), (String)"\u5220\u9664\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u5168\u90e8\u6307\u6807\u5f02\u5e38\u3002");
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.delete.programme.all.field.error"));
        }
    }

    protected ILFieldVO getFieldForId(String field) throws Exception {
        FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(field);
        if (fieldDefine == null) {
            return null;
        }
        TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
        ILFieldVO intermediateLibraryFieldVO = new ILFieldVO();
        intermediateLibraryFieldVO.setId(fieldDefine.getKey());
        intermediateLibraryFieldVO.setFieldCode(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]");
        intermediateLibraryFieldVO.setFieldType(DataInputTypeEnum.typeOf((FieldType)fieldDefine.getType()).name());
        intermediateLibraryFieldVO.setFieldSize(fieldDefine.getSize().intValue());
        intermediateLibraryFieldVO.setFieldTitle(fieldDefine.getTitle());
        intermediateLibraryFieldVO.setFieldName(fieldDefine.getCode());
        intermediateLibraryFieldVO.setTableName(tableDefine.getCode());
        return intermediateLibraryFieldVO;
    }
}

