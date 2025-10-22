/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService
 *  com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeService
 *  com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.jtable.util.FloatOrderGenerator
 *  com.jiuqi.nr.mapping2.web.vo.SelectOptionVO
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formulaschemeconfig.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService;
import com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeService;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.gcreport.formulaschemeconfig.dao.BillFormulaSchemeConfigDao;
import com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import com.jiuqi.gcreport.formulaschemeconfig.service.BillFormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillFormulaSchemeConfigServiceImpl
implements BillFormulaSchemeConfigService,
IBillExtractSchemeConfigService {
    private Logger logger = LoggerFactory.getLogger(BillFormulaSchemeConfigServiceImpl.class);
    @Autowired
    private BillFormulaSchemeConfigDao billFormulaSchemeConfigDao;
    @Autowired
    private IBillExtractSchemeService billExtractSchemeService;
    @Autowired
    private BillExtractSettingClient settingClient;
    @Autowired
    OrgDataService orgDataService;
    private final NedisCache formulaSchemeConfigCache;

    public BillFormulaSchemeConfigServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BILL_FORMULA_SCHEME_CONFIG_MANAGE");
        this.formulaSchemeConfigCache = cacheManager.getCache("BILL_FORMULA_SCHEME_CONFIG");
    }

    private List<FormulaSchemeConfigEO> getFormulaSchemeConfig(String billId) {
        return (List)this.formulaSchemeConfigCache.get(billId, () -> this.billFormulaSchemeConfigDao.getAllFormulaSchemeConfigs(billId));
    }

    @Override
    public List<FormulaSchemeColumn> createQueryColumns(String tableCategory) {
        FormulaSchemeColumn orgTitle;
        ArrayList<FormulaSchemeColumn> columns = new ArrayList<FormulaSchemeColumn>();
        if ("mainTable".equals(tableCategory)) {
            orgTitle = new FormulaSchemeColumn("\u7ec4\u7ec7\u673a\u6784", "orgTitle", Integer.valueOf(250));
            columns.add(orgTitle);
        } else {
            orgTitle = new FormulaSchemeColumn("\u5355\u4f4d\u8303\u56f4", "orgUnit", Integer.valueOf(250));
            columns.add(orgTitle);
        }
        FormulaSchemeColumn fetchScheme = new FormulaSchemeColumn("\u53d6\u6570\u65b9\u6848", "fetchScheme", Integer.valueOf(150));
        columns.add(fetchScheme);
        if ("mainTable".equals(tableCategory)) {
            FormulaSchemeColumn collocationMethod = new FormulaSchemeColumn("\u914d\u7f6e\u65b9\u5f0f", "collocationMethod", Integer.valueOf(150));
            columns.add(collocationMethod);
            FormulaSchemeColumn strategyUnit = new FormulaSchemeColumn("\u7b56\u7565\u7ee7\u627f\u5355\u4f4d", "strategyUnit", Integer.valueOf(200));
            columns.add(strategyUnit);
            FormulaSchemeColumn action = new FormulaSchemeColumn("\u64cd\u4f5c", "action", Integer.valueOf(130));
            columns.add(action);
        }
        return columns;
    }

    @Override
    public BillFormulaSchemeConfigQueryResultDTO getShowTableByOrgId(BillFormulaSchemeConfigCondition formulaSchemeConfigCondition) {
        String billId = formulaSchemeConfigCondition.getBillId();
        String orgId = formulaSchemeConfigCondition.getOrgId();
        List fetchSchemes = formulaSchemeConfigCondition.getFetchSchemes();
        List collocationMethods = formulaSchemeConfigCondition.getCollocationMethods();
        Boolean showBlankRow = formulaSchemeConfigCondition.getShowBlankRow();
        Map<String, Object> formulaSchemeData = this.getFetchSchemesByBillId(billId);
        String orgType = formulaSchemeData.get("orgType").toString();
        List orgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgId);
        String parents = (String)((OrgDO)orgDOList.get(0)).get((Object)"parents");
        List<String> parentList = Arrays.stream(parents.split("/")).filter(id -> !orgId.equals(id)).collect(Collectors.toList());
        List<String> childWithSelfList = orgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
        List<Object> currentOrgTree = this.getCurrentOrgTree(orgDOList);
        List<FormulaSchemeConfigEO> allFormulaSchemeConfig = this.getFormulaSchemeConfig(billId);
        Map<Boolean, List<FormulaSchemeConfigEO>> partitioned = allFormulaSchemeConfig.stream().collect(Collectors.partitioningBy(eo -> "unitSetting".equals(eo.getBblx())));
        List<FormulaSchemeConfigEO> unitFormulaSchemeConfig = partitioned.get(true);
        List<FormulaSchemeConfigEO> strategyFormulaSchemeConfig = partitioned.get(false);
        if (!strategyFormulaSchemeConfig.isEmpty()) {
            Map<String, List<FormulaSchemeConfigEO>> codeToObjectsMap = strategyFormulaSchemeConfig.stream().collect(Collectors.groupingBy(FormulaSchemeConfigEO::getOrgId));
            this.processParentStrategies(parentList, codeToObjectsMap, currentOrgTree, formulaSchemeData);
            this.processChildStrategies(childWithSelfList, codeToObjectsMap, currentOrgTree, formulaSchemeData, orgId, orgType);
        }
        if (!unitFormulaSchemeConfig.isEmpty()) {
            this.processUnitConfigurations(unitFormulaSchemeConfig, currentOrgTree, formulaSchemeData);
        }
        if (!fetchSchemes.isEmpty()) {
            currentOrgTree = currentOrgTree.stream().filter(item -> fetchSchemes.contains(item.getFetchSchemeId())).collect(Collectors.toList());
        }
        if (!collocationMethods.isEmpty()) {
            currentOrgTree = currentOrgTree.stream().filter(item -> collocationMethods.contains(item.getCollocationMethodId())).collect(Collectors.toList());
        }
        if (showBlankRow != null && !showBlankRow.booleanValue()) {
            currentOrgTree = currentOrgTree.stream().filter(vo -> !StringUtils.isEmpty((String)vo.getFetchSchemeId())).collect(Collectors.toList());
        }
        int total = currentOrgTree.size();
        currentOrgTree = currentOrgTree.stream().skip((long)(formulaSchemeConfigCondition.getPage() - 1) * (long)formulaSchemeConfigCondition.getPageSize().intValue()).limit(formulaSchemeConfigCondition.getPageSize().intValue()).collect(Collectors.toList());
        return new BillFormulaSchemeConfigQueryResultDTO(currentOrgTree, Integer.valueOf(total));
    }

    private void processParentStrategies(List<String> parentList, Map<String, List<FormulaSchemeConfigEO>> codeToObjectsMap, List<BillFormulaSchemeConfigTableVO> currentOrgTree, Map<String, Object> formulaSchemeData) {
        List parentOrderedList = parentList.stream().filter(codeToObjectsMap::containsKey).flatMap(key -> ((List)codeToObjectsMap.get(key)).stream()).collect(Collectors.toList());
        for (FormulaSchemeConfigEO config : parentOrderedList) {
            this.setTableVO(currentOrgTree, config, formulaSchemeData);
        }
    }

    private void processChildStrategies(List<String> childWithSelfList, Map<String, List<FormulaSchemeConfigEO>> codeToObjectsMap, List<BillFormulaSchemeConfigTableVO> currentOrgTree, Map<String, Object> formulaSchemeData, String orgId, String orgType) {
        List childOrderedList = childWithSelfList.stream().filter(codeToObjectsMap::containsKey).flatMap(key -> ((List)codeToObjectsMap.get(key)).stream()).collect(Collectors.toList());
        if (!childOrderedList.isEmpty()) {
            for (FormulaSchemeConfigEO formulaSchemeConfig : childOrderedList) {
                if (!orgId.equals(formulaSchemeConfig.getOrgId())) {
                    List affectedOrgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfig.getOrgId());
                    List affectedOrgCodeList = affectedOrgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
                    List<BillFormulaSchemeConfigTableVO> finalCurrentOrgTree = currentOrgTree.stream().filter(vo -> affectedOrgCodeList.contains(vo.getOrgId())).collect(Collectors.toList());
                    this.setTableVO(finalCurrentOrgTree, formulaSchemeConfig, formulaSchemeData);
                    continue;
                }
                this.setTableVO(currentOrgTree, formulaSchemeConfig, formulaSchemeData);
            }
        }
    }

    private void processUnitConfigurations(List<FormulaSchemeConfigEO> unitFormulaSchemeConfig, List<BillFormulaSchemeConfigTableVO> currentOrgTree, Map<String, Object> formulaSchemeData) {
        Map<String, List<BillFormulaSchemeConfigTableVO>> orgIdToTableVOMap = currentOrgTree.stream().collect(Collectors.groupingBy(FormulaSchemeConfigTableVO::getOrgId));
        for (FormulaSchemeConfigEO config : unitFormulaSchemeConfig) {
            List<BillFormulaSchemeConfigTableVO> affectedList = orgIdToTableVOMap.get(config.getOrgId());
            if (affectedList == null || affectedList.isEmpty()) continue;
            affectedList.forEach(tableVO -> this.setFormulaScheme((BillFormulaSchemeConfigTableVO)tableVO, config, formulaSchemeData, "unitSetting"));
        }
    }

    private void setTableVO(List<BillFormulaSchemeConfigTableVO> currentOrgTree, FormulaSchemeConfigEO formulaSchemeConfig, Map<String, Object> formulaSchemeData) {
        if (currentOrgTree.isEmpty()) {
            return;
        }
        String orgType = formulaSchemeData.get("orgType").toString();
        List orgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfig.getOrgId());
        if (!currentOrgTree.isEmpty()) {
            for (BillFormulaSchemeConfigTableVO tableVO : currentOrgTree) {
                this.setFormulaScheme(tableVO, formulaSchemeConfig, formulaSchemeData, "strategySetting");
                tableVO.setStrategyUnit(((OrgDO)orgDOList.get(0)).getName());
            }
        }
    }

    private void setFormulaScheme(BillFormulaSchemeConfigTableVO formulaSchemeConfigTableVO, FormulaSchemeConfigEO formulaSchemeConfigEO, Map<String, Object> formulaSchemeMap, String collocationMethod) {
        if ("unitSetting".equals(formulaSchemeConfigEO.getBblx())) {
            formulaSchemeConfigTableVO.setId(formulaSchemeConfigEO.getId());
            formulaSchemeConfigTableVO.setFetchSchemeId("");
            formulaSchemeConfigTableVO.setFetchScheme("");
        }
        List fetchSchemes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formulaSchemeMap.get("fetchSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
        List collocationMethods = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formulaSchemeMap.get("collocationMethods")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
        fetchSchemes.forEach(fetchScheme -> {
            if (formulaSchemeConfigEO.getFetchSchemeId() != null && fetchScheme.getValue().equals(formulaSchemeConfigEO.getFetchSchemeId())) {
                formulaSchemeConfigTableVO.setFetchSchemeId(fetchScheme.getValue().toString());
                formulaSchemeConfigTableVO.setFetchScheme(fetchScheme.getLabel());
            }
        });
        collocationMethods.forEach(method -> {
            if (collocationMethod.equals(method.getValue().toString())) {
                formulaSchemeConfigTableVO.setCollocationMethodId(method.getValue().toString());
                formulaSchemeConfigTableVO.setCollocationMethod(method.getLabel());
            }
        });
        formulaSchemeConfigTableVO.setCreator(formulaSchemeConfigEO.getCreator());
        formulaSchemeConfigTableVO.setCreateTime(formulaSchemeConfigEO.getCreateTime());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveStrategyFormulaSchemeConfig(List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigTableVOs)) {
            return;
        }
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        String billId = formulaSchemeConfigTableVOs.get(0).getBillId();
        List<FormulaSchemeConfigEO> formulaSchemeConfigList = new ArrayList<FormulaSchemeConfigEO>();
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        for (int i = 0; i < formulaSchemeConfigTableVOs.size(); ++i) {
            FormulaSchemeConfigEO formulaSchemeConfigEO = new FormulaSchemeConfigEO();
            Set orgIds = formulaSchemeConfigTableVOs.stream().map(FormulaSchemeConfigTableVO::getOrgId).collect(Collectors.toSet());
            if (orgIds.size() < formulaSchemeConfigTableVOs.size()) {
                throw new BusinessRuntimeException("\u4fdd\u5b58\u7684\u5355\u4f4d\u91cd\u590d");
            }
            BeanUtils.copyProperties(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO);
            formulaSchemeConfigEO.setSortOrder(Double.valueOf(floatOrderGenerator.next()));
            formulaSchemeConfigEO.setBblx("strategySetting");
            if (StringUtils.isEmpty((String)formulaSchemeConfigEO.getId())) {
                formulaSchemeConfigEO.setId(UUIDUtils.newUUIDStr());
                formulaSchemeConfigEO.setCreateTime(time);
                formulaSchemeConfigEO.setCreator(userName);
            } else {
                formulaSchemeConfigEO.setUpdateTime(time);
                formulaSchemeConfigEO.setUpdator(userName);
            }
            formulaSchemeConfigList.add(formulaSchemeConfigEO);
        }
        this.billFormulaSchemeConfigDao.deleteStrategySchemeConfig(billId);
        formulaSchemeConfigList = this.filterBlankRow(formulaSchemeConfigList);
        if (!CollectionUtils.isEmpty(formulaSchemeConfigList)) {
            this.billFormulaSchemeConfigDao.addBatch(formulaSchemeConfigList);
            this.writeBatchSaveLogHelper("strategySetting", formulaSchemeConfigList, "save");
        }
        this.formulaSchemeConfigCache.evict(billId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveUnitFormulaSchemeConfig(List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigTableVOs)) {
            return;
        }
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        String billId = formulaSchemeConfigTableVOs.get(0).getBillId();
        List<String> deleteFormulaSchemeConfigIds = formulaSchemeConfigTableVOs.stream().filter(vo -> vo.getCollocationMethodId() != null && "unitSetting".equals(vo.getCollocationMethodId())).map(FormulaSchemeConfigTableVO::getId).collect(Collectors.toList());
        List<FormulaSchemeConfigEO> formulaSchemeConfigList = new ArrayList<FormulaSchemeConfigEO>();
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        for (int i = 0; i < formulaSchemeConfigTableVOs.size(); ++i) {
            FormulaSchemeConfigEO formulaSchemeConfigEO = new FormulaSchemeConfigEO();
            BeanUtils.copyProperties(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO);
            formulaSchemeConfigEO.setSortOrder(Double.valueOf(floatOrderGenerator.next()));
            formulaSchemeConfigEO.setBblx("unitSetting");
            if (StringUtils.isEmpty((String)formulaSchemeConfigEO.getId())) {
                formulaSchemeConfigEO.setId(UUIDUtils.newUUIDStr());
                formulaSchemeConfigEO.setCreateTime(time);
                formulaSchemeConfigEO.setCreator(userName);
            } else {
                formulaSchemeConfigEO.setUpdateTime(time);
                formulaSchemeConfigEO.setUpdator(userName);
            }
            formulaSchemeConfigList.add(formulaSchemeConfigEO);
        }
        if (!CollectionUtils.isEmpty(deleteFormulaSchemeConfigIds)) {
            this.billFormulaSchemeConfigDao.deleteSelectSchemeConfig(deleteFormulaSchemeConfigIds);
        }
        if (!CollectionUtils.isEmpty(formulaSchemeConfigList = this.filterBlankRow(formulaSchemeConfigList))) {
            this.billFormulaSchemeConfigDao.addBatch(formulaSchemeConfigList);
            this.writeBatchSaveLogHelper("unitSetting", formulaSchemeConfigList, "save");
        }
        this.formulaSchemeConfigCache.evict(billId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void importFormulaSchemeConfig(String selectTab, List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigTableVOs)) {
            return;
        }
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        String billId = formulaSchemeConfigTableVOs.get(0).getBillId();
        List<FormulaSchemeConfigEO> formulaSchemeConfigList = new ArrayList<FormulaSchemeConfigEO>();
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        for (int i = 0; i < formulaSchemeConfigTableVOs.size(); ++i) {
            FormulaSchemeConfigEO formulaSchemeConfigEO = new FormulaSchemeConfigEO();
            Set orgIds = formulaSchemeConfigTableVOs.stream().map(FormulaSchemeConfigTableVO::getOrgId).collect(Collectors.toSet());
            if (orgIds.size() < formulaSchemeConfigTableVOs.size()) {
                throw new BusinessRuntimeException("\u4fdd\u5b58\u7684\u5355\u4f4d\u91cd\u590d");
            }
            BeanUtils.copyProperties(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO);
            formulaSchemeConfigEO.setSortOrder(Double.valueOf(floatOrderGenerator.next()));
            formulaSchemeConfigEO.setBblx(selectTab);
            formulaSchemeConfigEO.setId(UUIDUtils.newUUIDStr());
            formulaSchemeConfigEO.setCreateTime(time);
            formulaSchemeConfigEO.setCreator(userName);
            formulaSchemeConfigList.add(formulaSchemeConfigEO);
        }
        List<String> deleteFormulaSchemeConfigIds = this.getImportData(formulaSchemeConfigList);
        if (!CollectionUtils.isEmpty(deleteFormulaSchemeConfigIds)) {
            this.billFormulaSchemeConfigDao.deleteSelectSchemeConfig(deleteFormulaSchemeConfigIds);
        }
        if (!CollectionUtils.isEmpty(formulaSchemeConfigList = this.filterBlankRow(formulaSchemeConfigList))) {
            this.billFormulaSchemeConfigDao.addBatch(formulaSchemeConfigList);
            this.writeBatchSaveLogHelper(selectTab, formulaSchemeConfigList, "import");
        }
        this.formulaSchemeConfigCache.evict(billId);
    }

    private List<FormulaSchemeConfigEO> filterBlankRow(List<FormulaSchemeConfigEO> formulaSchemeConfigList) {
        return formulaSchemeConfigList.stream().filter(vo -> !StringUtils.isEmpty((String)vo.getFetchSchemeId())).collect(Collectors.toList());
    }

    @Override
    public void recoverDefaultStrategy(BillFormulaSchemeConfigTableVO formulaSchemeConfigTableVO) {
        String billId = formulaSchemeConfigTableVO.getBillId();
        ArrayList<String> deletedList = new ArrayList<String>();
        deletedList.add(formulaSchemeConfigTableVO.getId());
        this.billFormulaSchemeConfigDao.deleteSelectSchemeConfig(deletedList);
        this.writeBatchDeleteLogHelper(deletedList);
        this.formulaSchemeConfigCache.evict(billId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSelectSchemeConfig(String billId, List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            this.billFormulaSchemeConfigDao.deleteSelectSchemeConfig(ids);
            this.writeBatchDeleteLogHelper(ids);
            this.formulaSchemeConfigCache.evict(billId);
        }
    }

    @Override
    public Map<String, Object> getFetchSchemesByBillId(String billId) {
        HashMap<String, Object> formulaSchemeMap = new HashMap<String, Object>(16);
        String masterTableName = (String)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.settingClient.getMasterTableName(billId));
        DataModelColumn column = (DataModelColumn)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.settingClient.getDataModelColumn(masterTableName, "UNITCODE"));
        String orgType = BillExtractUtil.queryOrgTypeByColumn((DataModelColumn)column);
        String orgVer = "";
        if (!StringUtils.isEmpty((String)orgType)) {
            orgVer = DateUtils.format((Date)FormulaSchemeConfigUtils.getOrgVersion((String)orgType), (String)"yyyyMMdd");
        }
        ArrayList<SelectOptionVO> collocationMethods = new ArrayList<SelectOptionVO>();
        collocationMethods.add(new SelectOptionVO("strategySetting", "\u7b56\u7565\u8bbe\u7f6e"));
        collocationMethods.add(new SelectOptionVO("unitSetting", "\u5355\u4f4d\u8bbe\u7f6e"));
        ArrayList<SelectOptionVO> fetchSchemes = new ArrayList<SelectOptionVO>();
        List BillFetchSchemeDTOList = this.billExtractSchemeService.listScheme(billId);
        for (BillFetchSchemeDTO dto : BillFetchSchemeDTOList) {
            fetchSchemes.add(new SelectOptionVO(dto.getId(), dto.getName()));
        }
        formulaSchemeMap.put("orgVer", orgVer);
        formulaSchemeMap.put("orgType", orgType);
        formulaSchemeMap.put("fetchSchemes", fetchSchemes);
        formulaSchemeMap.put("collocationMethods", collocationMethods);
        return formulaSchemeMap;
    }

    private List<String> getImportData(List<FormulaSchemeConfigEO> formulaSchemeConfigList) {
        FormulaSchemeConfigEO schemeConfig;
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        ArrayList<String> deleteImportIds = new ArrayList<String>();
        HashMap<String, FormulaSchemeConfigEO> importDeleteMap = new HashMap<String, FormulaSchemeConfigEO>();
        List<String> orgIds = formulaSchemeConfigList.stream().map(FormulaSchemeConfigEO::getOrgId).collect(Collectors.toList());
        List<FormulaSchemeConfigEO> schemeConfigList = this.billFormulaSchemeConfigDao.getFormulaSchemeConfigsByOrgIds(orgIds, (schemeConfig = formulaSchemeConfigList.get(0)).getBillId());
        if (!CollectionUtils.isEmpty(schemeConfigList)) {
            for (FormulaSchemeConfigEO formulaSchemeConfig : schemeConfigList) {
                importDeleteMap.put(formulaSchemeConfig.getOrgId() + formulaSchemeConfig.getBblx(), formulaSchemeConfig);
            }
        }
        for (FormulaSchemeConfigEO schemeConfigItem : formulaSchemeConfigList) {
            String uniqueKey = schemeConfigItem.getOrgId() + schemeConfigItem.getBblx();
            FormulaSchemeConfigEO item = (FormulaSchemeConfigEO)importDeleteMap.get(uniqueKey);
            if (item == null || !StringUtils.isNotEmpty((String)item.getId())) continue;
            schemeConfigItem.setCreateTime(item.getCreateTime());
            schemeConfigItem.setCreator(item.getCreator());
            schemeConfigItem.setUpdateTime(time);
            schemeConfigItem.setUpdator(userName);
            schemeConfigItem.setId(item.getId());
            deleteImportIds.add(item.getId());
        }
        return deleteImportIds;
    }

    private void writeBatchSaveLogHelper(String selectTab, List<FormulaSchemeConfigEO> formulaSchemeConfigs, String sourceInfo) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigs)) {
            return;
        }
        String unitTab = "unitSetting";
        Set codes = formulaSchemeConfigs.stream().map(FormulaSchemeConfigEO::getOrgId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        String title = "import".equals(sourceInfo) ? "\u5bfc\u5165-\u5355\u636e\u53d6\u6570\u65b9\u6848" : "\u65b0\u589e-\u5355\u636e\u53d6\u6570\u65b9\u6848";
        String unitTabTitle = unitTab.equals(selectTab) ? "-\u5355\u4f4d\u516c\u5f0f\u65b9\u6848" : "-\u7b56\u7565\u516c\u5f0f\u65b9\u6848";
        LogHelper.info((String)"\u5408\u5e76-\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848", (String)(title + unitTabTitle), (String)("\u5355\u4f4d\u4fe1\u606f\uff1a" + codes.toString()));
    }

    private void writeBatchDeleteLogHelper(List<String> ids) {
        List<FormulaSchemeConfigEO> formulaSchemeConfigs = this.billFormulaSchemeConfigDao.listFormulaSchemeConfigById(ids);
        if (CollectionUtils.isEmpty(formulaSchemeConfigs)) {
            return;
        }
        Set codes = formulaSchemeConfigs.stream().map(FormulaSchemeConfigEO::getOrgId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        LogHelper.info((String)"\u5408\u5e76-\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848", (String)"\u5220\u9664-\u4efb\u52a1\u5355\u636e\u53d6\u6570\u65b9\u6848", (String)("\u5355\u4f4d\u4fe1\u606f\uff1a" + codes.toString()));
    }

    private List<BillFormulaSchemeConfigTableVO> getCurrentOrgTree(List<OrgDO> list) {
        return list.stream().map(BillFormulaSchemeConfigTableVO::new).collect(Collectors.toList());
    }

    @Override
    public BillFormulaSchemeConfigDTO getSchemeConfigByOrgId(String billId, String orgId) {
        List<FormulaSchemeConfigEO> allSchemes = this.getFormulaSchemeConfig(billId);
        Map<String, Object> formulaSchemeData = this.getFetchSchemesByBillId(billId);
        String orgType = formulaSchemeData.get("orgType").toString();
        OrgDO orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgId).get(0);
        List unitTabSchemes = allSchemes.stream().filter(item -> "-".equals(item.getBblx()) && item.getOrgId().equals(orgId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(unitTabSchemes)) {
            BillFormulaSchemeConfigDTO formulaSchemeConfigDTO = new BillFormulaSchemeConfigDTO();
            BeanUtils.copyProperties(unitTabSchemes.get(0), formulaSchemeConfigDTO);
            return formulaSchemeConfigDTO;
        }
        BillFormulaSchemeConfigDTO formulaSchemeConfigDTO = new BillFormulaSchemeConfigDTO();
        final String parents = (String)orgDO.get((Object)"parents");
        List parentList = Arrays.stream(parents.split("/")).collect(Collectors.toList());
        HashSet parentIds = new HashSet(parentList);
        List formulaSchemeConfigEOs = allSchemes.stream().filter(item -> parentIds.contains(item.getOrgId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(formulaSchemeConfigEOs)) {
            if (formulaSchemeConfigEOs.size() > 1) {
                formulaSchemeConfigEOs.sort(new Comparator<FormulaSchemeConfigEO>(){

                    @Override
                    public int compare(FormulaSchemeConfigEO o1, FormulaSchemeConfigEO o2) {
                        return parents.indexOf(o1.getOrgId()) < parents.indexOf(o2.getOrgId()) ? 1 : -1;
                    }
                });
            }
            BeanUtils.copyProperties(formulaSchemeConfigEOs.get(0), formulaSchemeConfigDTO);
        }
        return formulaSchemeConfigDTO;
    }

    @Override
    public List<BillFormulaSchemeConfigTableVO> getStrategyTabSchemeConfig(String billId) {
        ArrayList<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs = new ArrayList<BillFormulaSchemeConfigTableVO>();
        Map<String, Object> formulaSchemeData = this.getFetchSchemesByBillId(billId);
        String orgType = formulaSchemeData.get("orgType").toString();
        HashMap orgDOMap = new HashMap();
        List<Object> allFormulaSchemeConfig = this.getFormulaSchemeConfig(billId);
        if (!allFormulaSchemeConfig.isEmpty()) {
            allFormulaSchemeConfig = allFormulaSchemeConfig.stream().filter(eo -> "strategySetting".equals(eo.getBblx())).collect(Collectors.toList());
        }
        if (allFormulaSchemeConfig.isEmpty()) {
            return formulaSchemeConfigTableVOs;
        }
        allFormulaSchemeConfig.forEach(formulaSchemeConfigEO -> {
            BillFormulaSchemeConfigTableVO formulaSchemeConfigTableVO = new BillFormulaSchemeConfigTableVO();
            BeanUtils.copyProperties(formulaSchemeConfigEO, formulaSchemeConfigTableVO);
            OrgDO orgDO = null;
            if (orgDOMap.containsKey(formulaSchemeConfigEO.getOrgId())) {
                orgDO = (OrgDO)orgDOMap.get(formulaSchemeConfigEO.getOrgId());
            } else {
                orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfigEO.getOrgId()).get(0);
                orgDOMap.put(formulaSchemeConfigEO.getOrgId(), orgDO);
            }
            if (orgDO != null) {
                formulaSchemeConfigTableVO.setOrgUnit(orgDO);
                formulaSchemeConfigTableVOs.add(formulaSchemeConfigTableVO);
            }
        });
        return formulaSchemeConfigTableVOs;
    }

    @Override
    public Map<String, List<BillFormulaSchemeConfigTableVO>> queryTabSelectOrgIds(String billId, List<String> orgIds, Boolean isSystemResource) {
        HashMap<String, List<BillFormulaSchemeConfigTableVO>> batchTableSelect = new HashMap<String, List<BillFormulaSchemeConfigTableVO>>();
        ArrayList<BillFormulaSchemeConfigTableVO> formulaSchemeConfigBatchStrategyList = new ArrayList<BillFormulaSchemeConfigTableVO>();
        ArrayList<BillFormulaSchemeConfigTableVO> formulaSchemeConfigBatchUnitList = new ArrayList<BillFormulaSchemeConfigTableVO>();
        Map<String, Object> formulaSchemeData = this.getFetchSchemesByBillId(billId);
        String orgType = formulaSchemeData.get("orgType").toString();
        HashMap<String, OrgDO> orgV0Map = new HashMap<String, OrgDO>();
        List<Object> formulaSchemeConfigEOList = new ArrayList();
        formulaSchemeConfigEOList = isSystemResource != false ? this.billFormulaSchemeConfigDao.getAllFormulaSchemeConfigs(billId) : this.billFormulaSchemeConfigDao.getFormulaSchemeConfigsByOrgIds(orgIds, billId);
        if (!CollectionUtils.isEmpty(formulaSchemeConfigEOList)) {
            for (FormulaSchemeConfigEO formulaSchemeConfigEO : formulaSchemeConfigEOList) {
                OrgDO orgDO = null;
                if (orgV0Map.containsKey(formulaSchemeConfigEO.getOrgId())) {
                    orgDO = (OrgDO)orgV0Map.get(formulaSchemeConfigEO.getOrgId());
                } else {
                    orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfigEO.getOrgId()).get(0);
                    orgV0Map.put(formulaSchemeConfigEO.getOrgId(), orgDO);
                }
                BillFormulaSchemeConfigTableVO formulaSchemeConfigTableVO = new BillFormulaSchemeConfigTableVO(orgDO);
                BeanUtils.copyProperties(formulaSchemeConfigEO, formulaSchemeConfigTableVO);
                formulaSchemeConfigTableVO.setId(null);
                this.setFormulaScheme(formulaSchemeConfigTableVO, formulaSchemeConfigEO, formulaSchemeData, "");
                if ("strategySetting".equals(formulaSchemeConfigEO.getBblx())) {
                    formulaSchemeConfigBatchStrategyList.add(formulaSchemeConfigTableVO);
                    continue;
                }
                formulaSchemeConfigBatchUnitList.add(formulaSchemeConfigTableVO);
            }
        }
        batchTableSelect.put("batchStrategy", formulaSchemeConfigBatchStrategyList);
        batchTableSelect.put("batchUnit", formulaSchemeConfigBatchUnitList);
        return batchTableSelect;
    }

    public BillSchemeConfigDTO getSchemeByOrgId(String defineName, String unitCode) {
        BillFormulaSchemeConfigDTO schemeConfigDto = this.getSchemeConfigByOrgId(defineName, unitCode);
        if (schemeConfigDto == null) {
            return null;
        }
        BillSchemeConfigDTO billScheme = new BillSchemeConfigDTO();
        BeanUtils.copyProperties(schemeConfigDto, billScheme);
        return billScheme;
    }
}

