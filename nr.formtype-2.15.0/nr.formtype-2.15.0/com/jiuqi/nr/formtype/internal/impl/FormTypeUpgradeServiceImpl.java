/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.internal.impl.FormTypeBaseDataHelper;
import com.jiuqi.nr.formtype.service.IFormTypeCacheService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.formtype.service.IFormTypeUpgradeService;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormTypeUpgradeServiceImpl
implements IFormTypeUpgradeService,
StorageSyncTask {
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private FormTypeBaseDataHelper formTypeBaseDataHelper;
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private IFormTypeCacheService iFormTypeCacheService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Resource
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(FormTypeUpgradeServiceImpl.class);

    @Override
    public void doTransUpgrade() {
        List<OrgCategoryDO> orgCategorys = this.getAllOrgCategory();
        if (CollectionUtils.isEmpty(orgCategorys)) {
            return;
        }
        logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u5f00\u59cb\u3002");
        Set<String> formTypeCodeSet = this.getFormTypeCode(orgCategorys);
        this.doUpgrade(formTypeCodeSet);
        logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u7ed3\u675f\u3002");
    }

    private void doUpgrade(String formTypeCode) throws JQException {
        this.doDmIndex(formTypeCode);
        List<String> needMergeFields = this.needMergeFields(formTypeCode);
        if (!needMergeFields.isEmpty()) {
            logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\uff0c[{}]\u5b58\u5728\u51b2\u7a81\u5b57\u6bb5\u3002", (Object)formTypeCode);
            List<BaseDataDO> allBaseDatas = this.getAllBaseDatas(formTypeCode);
            if (!allBaseDatas.isEmpty()) {
                logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\uff0c[{}]\u5220\u9664\u6240\u6709\u6570\u636e\u3002", (Object)formTypeCode);
                this.deleteAllBaseDatas(formTypeCode, allBaseDatas);
            }
            logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\uff0c[{}]\u5220\u9664\u51b2\u7a81\u5b57\u6bb5\u3002", (Object)formTypeCode);
            try {
                this.removeMergeField(formTypeCode);
            }
            catch (Exception e) {
                logger.error("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\uff0c[{}]\u5220\u9664\u51b2\u7a81\u5b57\u6bb5\u5931\u8d25\u8fd8\u539f\u6570\u636e\u3002", (Object)formTypeCode, (Object)e);
                this.addAllBaseDatas(formTypeCode, Collections.emptyList(), allBaseDatas);
                return;
            }
            if (!allBaseDatas.isEmpty()) {
                logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\uff0c[{}]\u8fd8\u539f\u6570\u636e\u3002", (Object)formTypeCode);
                this.addAllBaseDatas(formTypeCode, needMergeFields, allBaseDatas);
            }
        }
        logger.info("\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u670d\u52a1\uff1a[{}]\u5f00\u59cb\u5347\u7ea7\u3002", (Object)formTypeCode);
        FormTypeDefine formType = this.iFormTypeService.createFormType();
        formType.setCode(formTypeCode);
        this.formTypeBaseDataHelper.getFormTypeFullInfo(formType);
        this.iFormTypeService.insertFormTypeNoCheck(formType, false);
        List<FormTypeDataDefine> oldDatas = this.iFormTypeService.queryFormTypeDatas(formTypeCode);
        if (!CollectionUtils.isEmpty(oldDatas)) {
            Map<String, FormTypeDataDefine> defaultDataMap = this.iFormTypeService.createDefaultFormTypeDatas(formTypeCode).stream().collect(Collectors.toMap(FormTypeDataDefine::getCode, v -> v));
            for (FormTypeDataDefine oldData : oldDatas) {
                if (defaultDataMap.containsKey(oldData.getCode())) {
                    FormTypeDataDefine defaultData = defaultDataMap.get(oldData.getCode());
                    oldData.setUnitNatrue(defaultData.getUnitNatrue());
                } else {
                    oldData.setUnitNatrue(UnitNature.JCFHB);
                }
                oldData.setUpdateTime(new Date());
            }
            this.formTypeBaseDataHelper.batchUpdateData(oldDatas);
        }
        logger.info("\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u670d\u52a1\uff1a[{}]\u5347\u7ea7\u6210\u529f\u3002", (Object)formTypeCode);
    }

    private void doDmIndex(String formTypeCode) {
        try (Connection connection = this.dataSource.getConnection();){
            boolean dataSourceName;
            connection.setAutoCommit(false);
            IDatabase databaseByConnection = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            String name = databaseByConnection.getName();
            boolean bl = dataSourceName = "dm".equalsIgnoreCase(name) || name.toLowerCase().contains("dm");
            if (dataSourceName) {
                Throwable throwable;
                String sql1 = String.format("CREATE CLUSTER INDEX %s_UG_NAME ON %s (NAME)", formTypeCode, formTypeCode);
                try {
                    throwable = null;
                    try (PreparedStatement statement1 = connection.prepareStatement(sql1);){
                        int i = statement1.executeUpdate();
                        connection.commit();
                        logger.info(String.format("\u8fbe\u68a6\u6570\u636e\u5e93\u57fa\u7840\u6570\u636e[%s]\u5728\u5347\u7ea7\u4e3a\u62a5\u8868\u7c7b\u578b\u65f6\u5019\u5728NAME\u5b57\u6bb5\u65b0\u5efa\u4e86\u805a\u96c6\u7d22\u5f15", formTypeCode));
                    }
                    catch (Throwable i) {
                        throwable = i;
                        throw i;
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                String sql2 = String.format("drop index %s_UG_NAME", formTypeCode);
                try {
                    throwable = null;
                    try (PreparedStatement statement2 = connection.prepareStatement(sql2);){
                        int i1 = statement2.executeUpdate();
                        connection.commit();
                        logger.info(String.format("\u8fbe\u68a6\u6570\u636e\u5e93\u57fa\u7840\u6570\u636e[%s]\u5728\u5347\u7ea7\u4e3a\u62a5\u8868\u7c7b\u578b\u65f6\u5019\u5728NAME\u5b57\u6bb5\u5220\u9664\u4e86\u805a\u96c6\u7d22\u5f15", formTypeCode));
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            connection.setAutoCommit(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private List<String> needMergeFields(final String baseDefineCode) throws JQException {
        ArrayList<String> columnNames = new ArrayList<String>();
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(baseDefineCode);
        dataModelDTO.setTenantName("__default_tenant__");
        DataModelDO oldDataModelDO = this.dataModelClient.get(dataModelDTO);
        if (null == oldDataModelDO) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "000";
                }

                public String getMessage() {
                    return "[" + baseDefineCode + "]\u5bf9\u5e94\u57fa\u7840\u6570\u636e\u4e0d\u5b58\u5728\u3002";
                }
            });
        }
        for (DataModelColumn c : oldDataModelDO.getColumns()) {
            if ("ICON".equalsIgnoreCase(c.getColumnName())) {
                if (DataModelType.ColumnType.CLOB == c.getColumnType()) continue;
                columnNames.add(c.getColumnName());
                continue;
            }
            if ("UNIT_NATURE".equalsIgnoreCase(c.getColumnName())) {
                if (DataModelType.ColumnType.INTEGER == c.getColumnType()) continue;
                columnNames.add(c.getColumnName());
                continue;
            }
            if (!"UPDATETIME".equalsIgnoreCase(c.getColumnName()) || DataModelType.ColumnType.TIMESTAMP == c.getColumnType()) continue;
            columnNames.add(c.getColumnName());
        }
        return columnNames;
    }

    private String getFieldName(Map<String, Object> c) {
        return (String)c.get("columnName");
    }

    private List<BaseDataDO> getAllBaseDatas(String baseDefineCode) {
        ArrayList<BaseDataDO> allBaseDatas = new ArrayList<BaseDataDO>();
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(baseDefineCode);
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        if (null != pageVO && pageVO.getTotal() > 0) {
            allBaseDatas.addAll(pageVO.getRows());
        }
        baseDataDTO.setRecoveryflag(Integer.valueOf(1));
        pageVO = this.baseDataClient.list(baseDataDTO);
        if (null != pageVO && pageVO.getTotal() > 0) {
            allBaseDatas.addAll(pageVO.getRows());
        }
        return allBaseDatas;
    }

    private void deleteAllBaseDatas(String formTypeCode, List<BaseDataDO> allBaseDatas) {
        BaseDataBatchOptDTO opt = new BaseDataBatchOptDTO();
        opt.setTenantName(formTypeCode);
        opt.addExtInfo("forceDelete", (Object)true);
        opt.setDataList(allBaseDatas);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(formTypeCode);
        opt.setQueryParam(queryParam);
        this.baseDataClient.batchRemove(opt);
    }

    private void addAllBaseDatas(String formTypeCode, List<String> needMergeFields, List<BaseDataDO> allBaseDatas) {
        ArrayList<BaseDataDTO> newDataList = new ArrayList<BaseDataDTO>();
        for (BaseDataDO baseDataDO : allBaseDatas) {
            BaseDataDTO basedataDTO = new BaseDataDTO((Map)baseDataDO);
            for (String columnName : needMergeFields) {
                basedataDTO.remove((Object)columnName);
            }
            newDataList.add(basedataDTO);
        }
        BaseDataBatchOptDTO opt = new BaseDataBatchOptDTO();
        opt.setTenantName(formTypeCode);
        opt.setDataList(newDataList);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(formTypeCode);
        opt.setQueryParam(queryParam);
        opt.setHighTrustability(true);
        this.baseDataClient.batchAdd(opt);
    }

    private void removeMergeField(String formTypeCode) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(formTypeCode);
        dataModelDTO.setTenantName("__default_tenant__");
        DataModelDO oldDataModelDO = this.dataModelClient.get(dataModelDTO);
        List oldColumns = oldDataModelDO.getColumns();
        oldColumns.removeIf(c -> c.getColumnName().equalsIgnoreCase("ICON") || c.getColumnName().equalsIgnoreCase("UNIT_NATURE") || c.getColumnName().equalsIgnoreCase("UPDATETIME"));
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setTenantName("__default_tenant__");
        dataModelDO.setGroupcode("FORMTYPE");
        dataModelDO.setName(oldDataModelDO.getName());
        dataModelDO.setColumns(oldColumns);
        this.dataModelClient.pushComplete(dataModelDO);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName("__default_tenant__");
        baseDataDefineDTO.setName(formTypeCode);
        BaseDataDefineDO oldDefine = this.baseDataDefineClient.get(baseDataDefineDTO);
        List defaultShowColumns = oldDefine.getDefaultShowColumns();
        if (CollectionUtils.isEmpty(defaultShowColumns)) {
            return;
        }
        baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setName(oldDefine.getName());
        baseDataDefineDTO.setTitle(oldDefine.getTitle());
        baseDataDefineDTO.setTenantName("__default_tenant__");
        defaultShowColumns.removeIf(c -> "ICON".equalsIgnoreCase(this.getFieldName((Map<String, Object>)c)) || "UNIT_NATURE".equalsIgnoreCase(this.getFieldName((Map<String, Object>)c)) || "UPDATETIME".equalsIgnoreCase(this.getFieldName((Map<String, Object>)c)));
        baseDataDefineDTO.setDefaultShowColumns(defaultShowColumns);
        baseDataDefineDTO.setModifytime(new Date());
        baseDataDefineDTO.addExtInfo("onlyEditBasicInfo", (Object)true);
        this.baseDataDefineClient.upate(baseDataDefineDTO);
    }

    private void doUpgrade(Set<String> formTypeCodeSet) {
        if (CollectionUtils.isEmpty(formTypeCodeSet)) {
            return;
        }
        logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u4ee5\u4e0b\u57fa\u7840\u6570\u636e\u5c06\u88ab\u5347\u7ea7\u4e3a\u62a5\u8868\u7c7b\u578b{}\u3002", (Object)formTypeCodeSet);
        for (String formTypeCode : formTypeCodeSet) {
            if (this.iFormTypeCacheService.isFormType(formTypeCode)) {
                logger.info("\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u670d\u52a1\uff1a[{}]\u5df2\u7ecf\u662f\u62a5\u8868\u7c7b\u578b\uff0c\u8df3\u8fc7\u5347\u7ea7\u3002", (Object)formTypeCode);
                continue;
            }
            try {
                this.doUpgrade(formTypeCode);
            }
            catch (Exception e) {
                logger.error("\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u670d\u52a1\uff1a[{}]\u5347\u7ea7\u5931\u8d25\u3002\uff1a", (Object)formTypeCode, (Object)e);
            }
        }
    }

    private Set<String> getFormTypeCode(List<OrgCategoryDO> orgCategorys) {
        HashSet<String> formTypeCodeSet = new HashSet<String>();
        block0: for (OrgCategoryDO orgCategory : orgCategorys) {
            for (ZB zb : orgCategory.getZbs()) {
                if (!"BBLX".equalsIgnoreCase(zb.getName())) continue;
                if (null == zb.getRelatetype() || 1 != zb.getRelatetype()) {
                    logger.info("\u62a5\u8868\u7c7b\u578b\u5347\u7ea7\u670d\u52a1\uff1a\u673a\u6784\u7c7b\u578b[{}]\u5173\u8054\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u5173\u8054[{}]\u975e\u57fa\u7840\u6570\u636e\u65e0\u6cd5\u5347\u7ea7\uff0c\u8df3\u8fc7\u3002", (Object)orgCategory.getName(), (Object)zb.getReltablename());
                    continue block0;
                }
                if (!StringUtils.hasText(zb.getReltablename())) continue;
                formTypeCodeSet.add(zb.getReltablename());
            }
        }
        return formTypeCodeSet;
    }

    private List<OrgCategoryDO> getAllOrgCategory() {
        OrgCategoryDO params = new OrgCategoryDO();
        return this.orgCategoryClient.list(params).getRows();
    }

    @Override
    public void doInitUpgrade() {
        try {
            logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u521d\u59cb\u5316\u62a5\u8868\u7c7b\u578b\u5f00\u59cb\u3002");
            this.iFormTypeService.createDefaultFormType();
            logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u521d\u59cb\u5316\u62a5\u8868\u7c7b\u578b\u7ed3\u675f\u3002");
        }
        catch (JQException e) {
            logger.error("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u521d\u59cb\u5316\u62a5\u8868\u7c7b\u578b\u5931\u8d25\u3002\uff1a", e);
        }
    }

    public void execute() {
        this.doInitUpgrade();
        this.doTransUpgrade();
        this.doIconSchemeUpgrade();
    }

    public int getSortNum() {
        return 3000;
    }

    public String getVersion() {
        return "20220620-1105";
    }

    @Override
    public void doIconSchemeUpgrade() {
        logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u5347\u7ea7\u7cfb\u7edf\u56fe\u6807\u65b9\u6848\u5f00\u59cb\u3002");
        String value = this.systemOptionService.get("unit_tree_system_config", "name_of_icon_scheme_key");
        if (!StringUtils.hasText(value)) {
            logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u5347\u7ea7\u7cfb\u7edf\u56fe\u6807\u65b9\u6848,\u56fe\u6807\u4e3a\u7a7a\uff0c\u4e0d\u8fdb\u884c\u5904\u7406");
            return;
        }
        value = value.contains("DJB") ? "1" : (value.contains("djb") ? "1" : "0");
        SystemOptionSave option = new SystemOptionSave();
        option.setKey("form_type_option_id");
        SystemOptionItemValue item = new SystemOptionItemValue();
        item.setKey("formtype_option_iconscheme");
        item.setValue(value);
        option.setItemValues(Collections.singletonList(item));
        this.systemOptionService.save(option);
        logger.info("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u5347\u7ea7\u7cfb\u7edf\u56fe\u6807\u65b9\u6848\u7ed3\u675f\u3002");
    }
}

