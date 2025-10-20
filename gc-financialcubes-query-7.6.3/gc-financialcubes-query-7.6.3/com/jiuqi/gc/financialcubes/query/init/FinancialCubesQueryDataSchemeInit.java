/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.va.service.EntVaModelDataInitService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.dataresource.service.IDataResourceDefineService
 *  com.jiuqi.nr.dataresource.service.IDataSchemeNode2ResourceService
 *  com.jiuqi.nr.dataresource.util.SceneUtilService
 *  com.jiuqi.nr.dataresource.web.param.CheckedParam
 *  com.jiuqi.nr.dataresource.web.param.DimNodeFilter
 *  com.jiuqi.nr.dataresource.web.vo.DataResourceDefineVO
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.base.EntityUtil
 *  com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO
 *  com.jiuqi.nr.datascheme.web.facade.DataSchemeVO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService
 *  com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.organization.task.VaOrgStorageCoreSyncTask
 */
package com.jiuqi.gc.financialcubes.query.init;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.service.EntVaModelDataInitService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataSchemeNode2ResourceService;
import com.jiuqi.nr.dataresource.util.SceneUtilService;
import com.jiuqi.nr.dataresource.web.param.CheckedParam;
import com.jiuqi.nr.dataresource.web.param.DimNodeFilter;
import com.jiuqi.nr.dataresource.web.vo.DataResourceDefineVO;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO;
import com.jiuqi.nr.datascheme.web.facade.DataSchemeVO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.organization.task.VaOrgStorageCoreSyncTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialCubesQueryDataSchemeInit
implements CustomClassExecutor {
    private final String QUERY_DATA_SCHEME_CODE = "FINANCIALCUBES";
    private final String QUERY_DATA_SCHEME_TITLE = "\u591a\u7ef4\u67e5\u8be2\u65b9\u6848";
    private final String QUERY_DATA_SCHEME_ORG_DIM = "MD_ORG@ORG";
    private final String QUERY_DATA_SCHEME_CURRENCY_DIM = "MD_CURRENCY@BASE";
    private final String QUERY_DATA_SCHEME_ORG_DIM_CORPORATE = "MD_ORG_CORPORATE@ORG";
    private final String QUERY_DATA_SCHEME_ORG_DIM_MANAGEMENT = "MD_ORG_MANAGEMENT@ORG";
    private final String CORPORATE = "CORPORATE";
    private final String CORPORATE_TITLE = "\u6cd5\u4eba";
    private final String MANAGEMENT = "MANAGEMENT";
    private final String MANAGEMENT_TITLE = "\u7ba1\u7406";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) throws Exception {
        NpContext context = NpContextHolder.getContext();
        try {
            if (context == null || StringUtils.isEmpty((String)context.getIdentityId())) {
                this.initUserInfo();
            }
            this.initDataQueryScheme();
            this.initDataSourceTree();
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private void initDataSourceTree() {
        try {
            this.logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u591a\u7ef4\u8d44\u6e90\u6811");
            IDesignQueryDataSchemeService designDataSchemeService = (IDesignQueryDataSchemeService)SpringContextUtils.getBean(IDesignQueryDataSchemeService.class);
            IDataResourceDefineService defineService = (IDataResourceDefineService)SpringContextUtils.getBean(IDataResourceDefineService.class);
            IDataSchemeNode2ResourceService iDataSchemeNode2ResourceService = (IDataSchemeNode2ResourceService)SpringContextUtils.getBean(IDataSchemeNode2ResourceService.class);
            SceneUtilService sceneUtil = (SceneUtilService)SpringContextUtils.getBean(SceneUtilService.class);
            DataResourceDefineVO resourceVO = new DataResourceDefineVO();
            resourceVO.setDimKey("MD_ORG@ORG");
            resourceVO.setTitle("\u591a\u7ef4\u67e5\u8be2\u65b9\u6848");
            resourceVO.setGroupKey(UUIDUtils.emptyUUIDStr());
            String groupId = defineService.insert(resourceVO.toDd(defineService));
            DimNodeFilter filter = new DimNodeFilter(sceneUtil);
            HashSet<RuntimeDataSchemeNodeDTO> allDataSchemeNode = new HashSet<RuntimeDataSchemeNodeDTO>();
            for (FinancialCubesPeriodTypeEnum periodTypeEnum : FinancialCubesPeriodTypeEnum.values()) {
                DesignDataScheme dataSchemeByCode = designDataSchemeService.getDataSchemeByCode("FINANCIALCUBES_" + periodTypeEnum.getCode());
                DataSchemeTreeQuery param = new DataSchemeTreeQuery();
                param.setDataSchemeNode((INode)new RuntimeDataSchemeNodeDTO((DataScheme)dataSchemeByCode));
                allDataSchemeNode.add(new RuntimeDataSchemeNodeDTO((DataScheme)dataSchemeByCode));
                allDataSchemeNode.addAll(iDataSchemeNode2ResourceService.queryAllChildren(param, (NodeFilter)filter));
            }
            CheckedParam checkedVO = new CheckedParam();
            checkedVO.setGroup(groupId);
            checkedVO.setOnlyField(false);
            checkedVO.setSelected(allDataSchemeNode);
            iDataSchemeNode2ResourceService.add(checkedVO);
            this.logger.info("\u5b8c\u6210\u521d\u59cb\u5316\u591a\u7ef4\u8d44\u6e90\u6811");
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u591a\u7ef4\u8d44\u6e90\u6811\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private void initDataQueryScheme() throws JQException {
        this.logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u591a\u7ef4\u67e5\u8be2\u6570\u636e\u65b9\u6848");
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u548c\u7ec4\u7ec7\u673a\u6784");
        VaOrgStorageCoreSyncTask vaOrgStorageCoreSyncTask = (VaOrgStorageCoreSyncTask)SpringContextUtils.getBean(VaOrgStorageCoreSyncTask.class);
        vaOrgStorageCoreSyncTask.execute();
        EntVaModelDataInitService entVaModelDataInitService = (EntVaModelDataInitService)SpringContextUtils.getBean(EntVaModelDataInitService.class);
        entVaModelDataInitService.init(false, null);
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u548c\u7ec4\u7ec7\u673a\u6784\u5b8c\u6210");
        IDesignQueryDataSchemeService designDataSchemeService = (IDesignQueryDataSchemeService)SpringContextUtils.getBean(IDesignQueryDataSchemeService.class);
        DefinitionAutoCollectionService definitionAutoCollectionService = (DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        for (FinancialCubesPeriodTypeEnum periodTypeEnum : FinancialCubesPeriodTypeEnum.values()) {
            String dataSchemeKey = this.addQueryDataScheme(designDataSchemeService, periodTypeEnum);
            this.logger.info("\u3010{}\u3011\u7c7b\u578b\u591a\u7ef4\u67e5\u8be2\u6570\u636e\u65b9\u6848\u521b\u5efa\u6210\u529f\uff0c\u5f00\u59cb\u6dfb\u52a0\u6570\u636e\u8868", (Object)periodTypeEnum.getName());
            String tableName = FinancialCubesCommonUtil.getFinancialCubesDimTableName((FinancialCubesPeriodTypeEnum)periodTypeEnum);
            this.addQueryDataTableByFinancialTable(designDataSchemeService, dataSchemeKey, definitionAutoCollectionService, dataModelService, tableName, periodTypeEnum);
            tableName = FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)periodTypeEnum);
            this.addQueryDataTableByFinancialTable(designDataSchemeService, dataSchemeKey, definitionAutoCollectionService, dataModelService, tableName, periodTypeEnum);
            tableName = FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)periodTypeEnum);
            this.addQueryDataTableByFinancialTable(designDataSchemeService, dataSchemeKey, definitionAutoCollectionService, dataModelService, tableName, periodTypeEnum);
            this.logger.info("\u5f00\u59cb\u53d1\u5e03\u3010{}\u3011\u7c7b\u578b\u591a\u7ef4\u67e5\u8be2\u6570\u636e\u65b9\u6848", (Object)periodTypeEnum.getName());
            ((IDataSchemeDeployService)SpringContextUtils.getBean(IDataSchemeDeployService.class)).unsafeDeployDataScheme(dataSchemeKey, null, null);
            this.logger.info("\u3010{}\u3011\u7c7b\u578b\u591a\u7ef4\u67e5\u8be2\u6570\u636e\u65b9\u6848\u53d1\u5e03\u6210\u529f", (Object)periodTypeEnum.getName());
        }
    }

    private void addQueryDataTableByFinancialTable(IDesignQueryDataSchemeService designDataSchemeService, String dataSchemeKey, DefinitionAutoCollectionService definitionAutoCollectionService, DataModelService dataModelService, String tableName, FinancialCubesPeriodTypeEnum periodTypeEnum) throws JQException {
        definitionAutoCollectionService.initTableDefineByTableName(tableName);
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByCode(tableName);
        this.logger.info("\u5b8c\u6210\u540c\u6b65\u3010{}\u3011\u8868\u6570\u636e\u5efa\u6a21", (Object)tableModelDefine.getTitle());
        this.addQueryDataTable(designDataSchemeService, dataSchemeKey, "CORPORATE", "\u6cd5\u4eba", tableName, tableModelDefine, periodTypeEnum);
        this.addQueryDataTable(designDataSchemeService, dataSchemeKey, "MANAGEMENT", "\u7ba1\u7406", tableName, tableModelDefine, periodTypeEnum);
        this.logger.info("\u5b8c\u6210\u6dfb\u52a0\u3010{}\u3011\u6570\u636e\u8868", (Object)tableModelDefine.getTitle());
    }

    private void initUserInfo() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = this.buildUserContext();
        npContext.setUser((ContextUser)contextUser);
        npContext.setIdentity((ContextIdentity)this.buildIdentityContext(contextUser));
        String tenantId = "__default_tenant__";
        npContext.setTenant(tenantId);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private NpContextUser buildUserContext() {
        NpContextUser userContext = new NpContextUser();
        SystemUserService sysUserService = (SystemUserService)SpringContextUtils.getBean(SystemUserService.class);
        SystemUser user = (SystemUser)sysUserService.getByUsername("admin");
        if (user == null) {
            user = (SystemUser)sysUserService.getUsers().get(0);
        }
        userContext.setId("SYSTEM.ROOT");
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getName());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }

    private String addQueryDataScheme(IDesignQueryDataSchemeService designDataSchemeService, FinancialCubesPeriodTypeEnum periodTypeEnum) {
        DataSchemeVO dataSchemeVO = this.initDataSchemeVO(periodTypeEnum);
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity((IDesignDataSchemeService)designDataSchemeService, (BaseDataSchemeVO)dataSchemeVO, null);
        List<DesignDataDimension> dimensions = this.buildDimsByVO(designDataSchemeService, dataSchemeVO);
        designDataSchemeService.insertDataScheme(scheme, dimensions);
        return scheme.getKey();
    }

    private void addQueryDataTable(IDesignQueryDataSchemeService designDataSchemeService, String dataSchemeKey, String orgTypeCode, String orgTypeTitle, String tableName, TableModelDefine tableModelDefine, FinancialCubesPeriodTypeEnum periodTypeEnum) throws JQException {
        QueryDataTableDTO queryDataTableDTO = new QueryDataTableDTO();
        queryDataTableDTO.setCode(String.format("%s_%s", orgTypeCode, tableName));
        queryDataTableDTO.setDataSchemeKey(dataSchemeKey);
        queryDataTableDTO.setDataTableGatherType(DataTableGatherType.NONE);
        queryDataTableDTO.setDataTableType(DataTableType.DETAIL);
        queryDataTableDTO.setExpression(String.format("(md_gcorgtype = 'MD_ORG_%s' or md_gcorgtype = 'NONE')", orgTypeCode));
        queryDataTableDTO.setRepeatCode(Boolean.valueOf(true));
        queryDataTableDTO.setTableName(tableName);
        queryDataTableDTO.setTableType("nvwaDataModel");
        String queryDataTableTitle = String.format("%s_%s", orgTypeTitle, tableModelDefine.getTitle());
        queryDataTableDTO.setTitle(queryDataTableTitle);
        HashMap<String, String> dimColumns = new HashMap<String, String>();
        dimColumns.put("MD_ORG@ORG", "MDCODE");
        dimColumns.put("MD_CURRENCY@BASE", "MD_CURRENCY");
        dimColumns.put(periodTypeEnum.getCode(), "DATATIME");
        designDataSchemeService.insertQueryDataTable(queryDataTableDTO, dimColumns);
        this.logger.info("\u5b8c\u6210\u3010{}\u3011\u521d\u59cb\u5316", (Object)queryDataTableTitle);
    }

    private DataSchemeVO initDataSchemeVO(FinancialCubesPeriodTypeEnum periodTypeEnum) {
        DataSchemeVO dataSchemeVO = new DataSchemeVO();
        dataSchemeVO.setCode("FINANCIALCUBES_" + periodTypeEnum.getCode());
        dataSchemeVO.setDataGroupKey("00000000-0000-0000-0000-111111111111");
        dataSchemeVO.setOrgDimKey("MD_ORG@ORG");
        dataSchemeVO.setOrgDimScope(Arrays.asList("MD_ORG_CORPORATE@ORG", "MD_ORG_MANAGEMENT@ORG"));
        dataSchemeVO.setTitle(periodTypeEnum.getName() + "\u591a\u7ef4\u67e5\u8be2\u65b9\u6848");
        dataSchemeVO.setType(DataSchemeType.QUERY);
        dataSchemeVO.setDimKeys(Arrays.asList("MD_CURRENCY@BASE"));
        dataSchemeVO.setPeriodDimKey(periodTypeEnum.getCode());
        dataSchemeVO.setPeriodType(PeriodType.fromCode((int)periodTypeEnum.getCode().charAt(0)).type());
        return dataSchemeVO;
    }

    private List<DesignDataDimension> buildDimsByVO(IDesignQueryDataSchemeService designDataSchemeService, DataSchemeVO schemeObj) {
        DesignDataDimension dim;
        ArrayList<DesignDataDimension> dimensions = new ArrayList<DesignDataDimension>(5);
        DesignDataDimension dimension = designDataSchemeService.initDataSchemeDimension();
        dimension.setDimKey(schemeObj.getOrgDimKey());
        dimension.setDimensionType(DimensionType.UNIT);
        dimensions.add(dimension);
        if (!StringUtils.isEmpty((String)schemeObj.getPeriodDimKey())) {
            DesignDataDimension period = designDataSchemeService.initDataSchemeDimension();
            period.setDimKey(schemeObj.getPeriodDimKey());
            period.setDimensionType(DimensionType.PERIOD);
            period.setPeriodType(PeriodType.fromType((int)schemeObj.getPeriodType()));
            dimensions.add(period);
        }
        if (schemeObj.getDimKeys() != null) {
            for (String v : schemeObj.getDimKeys()) {
                dim = designDataSchemeService.initDataSchemeDimension();
                dim.setDimKey(v);
                dim.setDimensionType(DimensionType.DIMENSION);
                dimensions.add(dim);
            }
        }
        if (schemeObj.getOrgDimScope() != null) {
            for (String v : schemeObj.getOrgDimScope()) {
                dim = designDataSchemeService.initDataSchemeDimension();
                dim.setDimKey(v);
                dim.setDimensionType(DimensionType.UNIT_SCOPE);
                dimensions.add(dim);
            }
        }
        return dimensions;
    }

    private int allNode() {
        int checkboxOptional = 0;
        for (NodeType value : NodeType.values()) {
            checkboxOptional |= value.getValue();
        }
        return checkboxOptional;
    }
}

