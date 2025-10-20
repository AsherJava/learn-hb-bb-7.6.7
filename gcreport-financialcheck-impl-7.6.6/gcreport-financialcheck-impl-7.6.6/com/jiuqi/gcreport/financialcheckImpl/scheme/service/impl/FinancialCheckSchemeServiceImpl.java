/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckAttributeNumVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckMappingDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckMappingEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckSchemeStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckSchemeUsedStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckToleranceEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.SchemeTypeEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.SpecialCheckEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.FcUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRTOffsetExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeExtendExecutor;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeExtendExecutorFactory;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param.RealTimeCheckOrOffsetParam;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckAttributeNumVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckMappingDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckMappingEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckSchemeStateEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckSchemeUsedStateEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckToleranceEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.SchemeTypeEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.SpecialCheckEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.FcUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FinancialCheckSchemeServiceImpl
implements FinancialCheckSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(FinancialCheckSchemeServiceImpl.class);
    public static final String ROOTNAMEDEFAULT = "\u5bf9\u8d26\u65b9\u6848";
    private final FinancialCheckSchemeDao schemeDao;
    private final FinancialCheckProjectDao financialCheckProjectDao;
    private final FinancialCheckMappingDao financialCheckMappingDao;
    private final GcRelatedItemDao gcRelatedItemDao;
    @Autowired
    private GcRelatedItemCommandService gcRelatedItemCommandService;
    @Autowired
    FinancialCheckRTOffsetExecutorImpl offsetExecutor;
    @Autowired
    IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;
    @Autowired
    private DataModelService dataModelService;

    public FinancialCheckSchemeServiceImpl(FinancialCheckSchemeDao schemeDao, FinancialCheckProjectDao financialCheckProjectDao, FinancialCheckMappingDao financialCheckMappingDao, GcRelatedItemDao gcRelatedItemDao) {
        this.schemeDao = schemeDao;
        this.financialCheckMappingDao = financialCheckMappingDao;
        this.financialCheckProjectDao = financialCheckProjectDao;
        this.gcRelatedItemDao = gcRelatedItemDao;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FinancialCheckSchemeInitDataVO initData() {
        Date now = new Date();
        FinancialCheckSchemeInitDataVO iniData = new FinancialCheckSchemeInitDataVO();
        int acctYear = DateUtils.getDateFieldValue((Date)now, (int)1);
        iniData.setAcctYear(Integer.valueOf(acctYear));
        iniData.setOrgVer(DateUtils.format((Date)now, (String)"yyyyMMdd"));
        iniData.setOrgType(FinancialCheckConfigUtils.getCheckOrgType());
        iniData.setCheckDimensions(this.getOffsetGroupingField());
        iniData.setCheckAttributes(this.getCheckAttributes());
        return iniData;
    }

    private List<SelectOptionVO> getOffsetGroupingField() {
        ArrayList<SelectOptionVO> checkGroupingFieldOption = new ArrayList<SelectOptionVO>();
        TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM", OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource"));
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefineByCode.getID(), "GCNUMBER");
        checkGroupingFieldOption.add(new SelectOptionVO((Object)"GCNUMBER", columnModelDefine.getTitle()));
        DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        List dimensionEOs = dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        if (!CollectionUtils.isEmpty(dimensionEOs)) {
            dimensionEOs.forEach(dimensionEO -> {
                SelectOptionVO checkGroupingFieldOptionVO = new SelectOptionVO();
                checkGroupingFieldOptionVO.setLabel(dimensionEO.getTitle());
                checkGroupingFieldOptionVO.setValue((Object)dimensionEO.getCode());
                checkGroupingFieldOption.add(checkGroupingFieldOptionVO);
            });
        }
        return checkGroupingFieldOption;
    }

    private List<GcBaseData> getCheckAttributes() {
        return GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CHECK_ATTRIBUTE");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FinancialCheckSchemeVO addOrUpdate(FinancialCheckSchemeVO schemeVo) {
        if (StringUtils.isEmpty((String)schemeVo.getSchemeName())) {
            throw new BusinessRuntimeException("\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty((String)schemeVo.getId())) {
            FinancialCheckSchemeEO schemeEo = new FinancialCheckSchemeEO();
            this.validBySchemeName(schemeVo.getSchemeName(), schemeVo.getAcctYear());
            schemeEo.setSchemeName(schemeVo.getSchemeName());
            schemeEo.setDescription(schemeVo.getDescription());
            schemeEo.setId(UUIDUtils.newUUIDStr());
            schemeEo.setEnable(CheckSchemeStateEnum.ENABLE.getCode());
            schemeEo.setCreateTime(new Date());
            schemeEo.setCreator(NpContextHolder.getContext().getUserName());
            schemeEo.setSchemeType(Objects.nonNull(schemeVo.getSchemeType()) ? schemeVo.getSchemeType() : SchemeTypeEnum.SCHEME.getCode());
            schemeEo.setAcctYear(schemeVo.getAcctYear());
            schemeEo.setParentId(schemeVo.getParentId());
            schemeEo.setSpecialCheck(SpecialCheckEnum.NORMAL.getCode());
            schemeEo.setToleranceEnable(CheckToleranceEnum.DISABLE.getCode());
            schemeEo.setSortOrder(Double.valueOf(this.getNewSortOrder()));
            this.schemeDao.add((BaseEntity)schemeEo);
            schemeVo.setSchemeType(schemeEo.getSchemeType());
            schemeVo.setId(schemeEo.getId());
            schemeVo.setEnable(CheckSchemeStateEnum.ENABLE.getCode());
        } else {
            FinancialCheckSchemeEO financialCheckSchemeEO = this.queryById(schemeVo.getId());
            if (Objects.isNull(financialCheckSchemeEO)) {
                throw new BusinessRuntimeException("\u6570\u636e\u5df2\u7ecf\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4fee\u6539\u3002");
            }
            if (!Objects.equals(financialCheckSchemeEO.getSchemeName(), schemeVo.getSchemeName())) {
                this.validBySchemeName(schemeVo.getSchemeName(), financialCheckSchemeEO.getAcctYear());
            }
            financialCheckSchemeEO.setSchemeName(schemeVo.getSchemeName());
            financialCheckSchemeEO.setDescription(schemeVo.getDescription());
            financialCheckSchemeEO.setUpdateTime(new Date());
            this.schemeDao.update((BaseEntity)financialCheckSchemeEO);
        }
        return schemeVo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FinancialCheckSchemeTreeVO save(FinancialCheckSchemeVO financialCheckSchemeVO) {
        this.addOrUpdate(financialCheckSchemeVO);
        FinancialCheckSchemeEO schemeEO = this.convertVO2EO(financialCheckSchemeVO);
        schemeEO.setUpdateTime(new Date());
        FinancialCheckSchemeExtendExecutor executor = FinancialCheckSchemeExtendExecutorFactory.getExecutor(schemeEO.getCheckMode());
        executor.save(financialCheckSchemeVO, schemeEO);
        return this.convertEO2TreeVO(schemeEO);
    }

    @Override
    public String validCheckScheme(String id) {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(id);
        List schemeByIdsOrParentIds = this.schemeDao.getSchemeByIdsOrParentIds(ids);
        List allIds = schemeByIdsOrParentIds.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        List byCheckSchemeIds = this.gcRelatedItemDao.findByCheckSchemeIds(allIds);
        if (CollectionUtils.isEmpty(byCheckSchemeIds)) {
            return CheckSchemeUsedStateEnum.UNUSED.name();
        }
        Set chkStateSet = byCheckSchemeIds.stream().map(GcRelatedItemEO::getChkState).collect(Collectors.toSet());
        if (chkStateSet.contains(CheckStateEnum.CHECKED.getCode())) {
            return CheckSchemeUsedStateEnum.CHECKEDUSED.name();
        }
        return CheckSchemeUsedStateEnum.UNCHECKEDUSED.name();
    }

    FinancialCheckSchemeEO convertVO2EO(FinancialCheckSchemeVO schemeVo) {
        FinancialCheckSchemeEO financialCheckSchemeEO = new FinancialCheckSchemeEO();
        BeanUtils.copyProperties(schemeVo, financialCheckSchemeEO);
        if (!CollectionUtils.isEmpty(schemeVo.getCheckDimensions())) {
            financialCheckSchemeEO.setCheckDimensions(String.join((CharSequence)",", schemeVo.getCheckDimensions()));
        }
        if (Objects.nonNull(schemeVo.getToleranceEnable())) {
            financialCheckSchemeEO.setToleranceEnable(Integer.valueOf(schemeVo.getToleranceEnable()));
        }
        if (Objects.nonNull(schemeVo.getSpecialCheck())) {
            financialCheckSchemeEO.setSpecialCheck(Integer.valueOf(schemeVo.getSpecialCheck()));
        }
        GcOrgCenterService centerService = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType());
        if (CollectionUtils.isEmpty(schemeVo.getUnitCodes())) {
            financialCheckSchemeEO.setUnitCodes(null);
        } else {
            List unitCodeOnlyParent = OrgUtils.getUnitCodeOnlyParent((List)schemeVo.getUnitCodes(), (GcOrgCenterService)centerService);
            financialCheckSchemeEO.setUnitCodes(String.join((CharSequence)",", unitCodeOnlyParent));
        }
        FinancialCheckSchemeEO originEO = this.queryById(schemeVo.getId());
        financialCheckSchemeEO.setCreateTime(originEO.getCreateTime());
        financialCheckSchemeEO.setSortOrder(originEO.getSortOrder());
        financialCheckSchemeEO.setCreator(originEO.getCreator());
        return financialCheckSchemeEO;
    }

    private int getNewSortOrder() {
        Integer maxOrder = this.schemeDao.getMaxOrder();
        if (maxOrder == null) {
            return 0;
        }
        maxOrder = maxOrder + 1;
        return maxOrder;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(String id) {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(id);
        List byCheckSchemeIds = this.gcRelatedItemDao.findByCheckSchemeIds(ids);
        if (!CollectionUtils.isEmpty(byCheckSchemeIds)) {
            Set periods = byCheckSchemeIds.stream().map(item -> item.getAcctPeriod().toString()).collect(Collectors.toSet());
            String join = String.join((CharSequence)",", periods);
            throw new BusinessRuntimeException("\u6b64\u65b9\u6848\u5df2\u88ab\u5df2\u88ab\u5206\u5f55\u6570\u636e\u5339\u914d\uff0c\u8bf7\u5148\u53d6\u6d88\u5339\u914d\u540e\u518d\u5220\u9664\u3002\u5206\u5f55\u6570\u636e\u671f\u95f4\uff1a" + join);
        }
        FinancialCheckSchemeEO schemeEO = this.queryById(id);
        if (Objects.isNull(schemeEO)) {
            throw new BusinessRuntimeException("\u6b64\u65b9\u6848\u5df2\u88ab\u522a\u9664,\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.schemeDao.deleteByParentId(id);
        FinancialCheckSchemeExtendExecutor executor = FinancialCheckSchemeExtendExecutorFactory.getExecutor(schemeEO.getCheckMode());
        executor.afterDelete(id);
    }

    @Override
    public List<FinancialCheckSchemeTreeVO> treeCheckGroup(FinancialCheckGroupVO checkGroupVO, Boolean queryEnable) {
        this.initRoot();
        FinancialCheckSchemeEO param = new FinancialCheckSchemeEO();
        param.setAcctYear(checkGroupVO.getAcctYear());
        List financialCheckSchemeEOS = queryEnable != false ? this.schemeDao.queryEnable(checkGroupVO.getAcctYear().intValue(), null) : this.schemeDao.selectList((BaseEntity)param);
        List financialCheckSchemeTreeVOS = financialCheckSchemeEOS.stream().map(this::convertEO2TreeVO).collect(Collectors.toList());
        Map<String, List<FinancialCheckSchemeTreeVO>> parentId2DirectChildNode = financialCheckSchemeTreeVOS.stream().collect(Collectors.groupingBy(FinancialCheckSchemeTreeVO::getParentId, Collectors.mapping(Function.identity(), Collectors.toList())));
        ArrayList<FinancialCheckSchemeTreeVO> checkSchemeTreeVOS = new ArrayList<FinancialCheckSchemeTreeVO>();
        if (StringUtils.isEmpty((String)checkGroupVO.getId())) {
            FinancialCheckSchemeEO root = this.queryById(UUIDUtils.emptyUUIDStr());
            FinancialCheckSchemeTreeVO treeRoot = this.convertEO2TreeVO(root);
            checkSchemeTreeVOS.add(this.loadChildrenNode(treeRoot, parentId2DirectChildNode));
        } else {
            FinancialCheckSchemeEO rootParam = new FinancialCheckSchemeEO();
            rootParam.setAcctYear(checkGroupVO.getAcctYear());
            rootParam.setParentId(checkGroupVO.getId());
            List roots = null;
            roots = queryEnable != false ? this.schemeDao.queryEnable(checkGroupVO.getAcctYear().intValue(), checkGroupVO.getId()) : this.schemeDao.selectList((BaseEntity)rootParam);
            List rootTreeVOS = roots.stream().map(this::convertEO2TreeVO).collect(Collectors.toList());
            checkSchemeTreeVOS.addAll(rootTreeVOS.stream().map(financialCheckSchemeTreeVO -> this.loadChildrenNode((FinancialCheckSchemeTreeVO)financialCheckSchemeTreeVO, parentId2DirectChildNode)).collect(Collectors.toList()));
        }
        this.sortTreeVOs(checkSchemeTreeVOS);
        return checkSchemeTreeVOS;
    }

    @Override
    public List<FinancialCheckSchemeBaseDataVO> treeEnableScheme(FinancialCheckGroupVO checkGroupVO) {
        if (StringUtils.isEmpty((String)checkGroupVO.getParentCode())) {
            checkGroupVO.setId(UUIDUtils.emptyUUIDStr());
        } else {
            checkGroupVO.setId(checkGroupVO.getParentCode());
        }
        List<Object> financialCheckSchemeTreeVOS = this.treeCheckGroup(checkGroupVO, true);
        financialCheckSchemeTreeVOS = financialCheckSchemeTreeVOS.stream().filter(financialCheckSchemeTreeVO -> !CollectionUtils.isEmpty(financialCheckSchemeTreeVO.getChildren())).collect(Collectors.toList());
        return this.buildCheckSchemeBaseData(financialCheckSchemeTreeVOS);
    }

    @Override
    public FinancialCheckSchemeBaseDataVO singleEnableScheme(FinancialCheckGroupVO checkGroupVO) {
        FinancialCheckSchemeEO financialCheckSchemeEO = this.queryById(checkGroupVO.getCode());
        if (Objects.isNull(financialCheckSchemeEO)) {
            return null;
        }
        return this.convertEO2BaseDataVO(financialCheckSchemeEO);
    }

    @Override
    public List<FinancialCheckSchemeBaseDataVO> allEnableScheme(FinancialCheckGroupVO checkGroupVO) {
        String parentCode = checkGroupVO.getParentCode();
        FinancialCheckSchemeEO parentNode = this.queryById(parentCode);
        List byParentIdAndStartFlagOrderBySortOrder = this.schemeDao.findByParentIdAndStartFlagOrderBySortOrder(parentCode, true);
        byParentIdAndStartFlagOrderBySortOrder.add(0, parentNode);
        return byParentIdAndStartFlagOrderBySortOrder.stream().map(this::convertEO2BaseDataVO).collect(Collectors.toList());
    }

    private FinancialCheckSchemeBaseDataVO convertEO2BaseDataVO(FinancialCheckSchemeEO financialCheckSchemeEO) {
        FinancialCheckSchemeBaseDataVO financialCheckSchemeBaseDataVO = new FinancialCheckSchemeBaseDataVO();
        financialCheckSchemeBaseDataVO.setKey(financialCheckSchemeEO.getId());
        financialCheckSchemeBaseDataVO.setCode(financialCheckSchemeEO.getId());
        financialCheckSchemeBaseDataVO.setIsLeaf(Boolean.valueOf(SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeEO.getSchemeType())));
        financialCheckSchemeBaseDataVO.setTitle(financialCheckSchemeEO.getSchemeName());
        financialCheckSchemeBaseDataVO.setParentid(financialCheckSchemeEO.getParentId());
        financialCheckSchemeBaseDataVO.setChildren(null);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("key", financialCheckSchemeEO.getId());
        data.put("code", financialCheckSchemeEO.getId());
        data.put("isLeaf", SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeEO.getSchemeType()));
        data.put("title", financialCheckSchemeEO.getSchemeName());
        data.put("parentid", financialCheckSchemeEO.getParentId());
        data.put("checkMode", financialCheckSchemeEO.getCheckMode());
        financialCheckSchemeBaseDataVO.setData(data);
        return financialCheckSchemeBaseDataVO;
    }

    private List<FinancialCheckSchemeBaseDataVO> buildCheckSchemeBaseData(List<FinancialCheckSchemeTreeVO> financialCheckSchemeTreeVOs) {
        if (CollectionUtils.isEmpty(financialCheckSchemeTreeVOs)) {
            return null;
        }
        return financialCheckSchemeTreeVOs.stream().map(financialCheckSchemeTreeVO -> {
            FinancialCheckSchemeBaseDataVO financialCheckSchemeBaseDataVO = new FinancialCheckSchemeBaseDataVO();
            financialCheckSchemeBaseDataVO.setKey(financialCheckSchemeTreeVO.getId());
            financialCheckSchemeBaseDataVO.setCode(financialCheckSchemeTreeVO.getId());
            financialCheckSchemeBaseDataVO.setIsLeaf(Boolean.valueOf(SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeTreeVO.getSchemeType())));
            financialCheckSchemeBaseDataVO.setTitle(financialCheckSchemeTreeVO.getTitle());
            financialCheckSchemeBaseDataVO.setParentid(financialCheckSchemeTreeVO.getParentId());
            financialCheckSchemeBaseDataVO.setChildren(this.buildCheckSchemeBaseData(financialCheckSchemeTreeVO.getChildren()));
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("key", financialCheckSchemeTreeVO.getId());
            data.put("code", financialCheckSchemeTreeVO.getId());
            data.put("isLeaf", SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeTreeVO.getSchemeType()));
            data.put("title", financialCheckSchemeTreeVO.getTitle());
            data.put("parentid", financialCheckSchemeTreeVO.getParentId());
            data.put("checkMode", financialCheckSchemeTreeVO.getCheckMode());
            financialCheckSchemeBaseDataVO.setData(data);
            return financialCheckSchemeBaseDataVO;
        }).collect(Collectors.toList());
    }

    @Override
    public FinancialCheckSchemeNumVO countScheme(FinancialCheckGroupVO checkGroupVO) {
        if (Objects.isNull(checkGroupVO.getAcctYear())) {
            throw new BusinessRuntimeException("\u52a0\u8f7d\u65b9\u6848\u6570\u91cf\uff0c\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        FinancialCheckSchemeNumVO financialCheckSchemeNumVO = new FinancialCheckSchemeNumVO();
        FinancialCheckSchemeEO param = new FinancialCheckSchemeEO();
        param.setAcctYear(checkGroupVO.getAcctYear());
        List financialCheckSchemeEOS = this.schemeDao.selectList((BaseEntity)param);
        List totalSchemes = financialCheckSchemeEOS.stream().filter(financialCheckSchemeEO -> CheckSchemeStateEnum.ENABLE.getCode().equals(financialCheckSchemeEO.getEnable()) && SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeEO.getSchemeType())).collect(Collectors.toList());
        financialCheckSchemeNumVO.setTotalNum(Integer.valueOf(totalSchemes.size()));
        List bothSchemes = totalSchemes.stream().filter(financialCheckSchemeEO -> CheckModeEnum.BILATERAL.getCode().equals(financialCheckSchemeEO.getCheckMode())).collect(Collectors.toList());
        financialCheckSchemeNumVO.setBothNum(Integer.valueOf(bothSchemes.size()));
        List singleSchemes = totalSchemes.stream().filter(financialCheckSchemeEO -> CheckModeEnum.UNILATERAL.getCode().equals(financialCheckSchemeEO.getCheckMode())).collect(Collectors.toList());
        financialCheckSchemeNumVO.setSingleNum(Integer.valueOf(singleSchemes.size()));
        List clbrSchemes = totalSchemes.stream().filter(financialCheckSchemeEO -> CheckModeEnum.OFFSETVCHR.getCode().equals(financialCheckSchemeEO.getCheckMode())).collect(Collectors.toList());
        financialCheckSchemeNumVO.setOffsetVchrNum(Integer.valueOf(clbrSchemes.size()));
        List gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CHECK_ATTRIBUTE");
        Map<String, String> attributeCode2Title = gcBaseData.stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle, (k1, k2) -> k1));
        ArrayList checkAttributeNumVOS = new ArrayList();
        attributeCode2Title.forEach((code, title) -> {
            CheckAttributeNumVO checkAttributeNumVO = new CheckAttributeNumVO();
            checkAttributeNumVO.setCode(code);
            checkAttributeNumVO.setTitle(title);
            List dealingSchemes = bothSchemes.stream().filter(financialCheckSchemeEO -> code.equals(financialCheckSchemeEO.getCheckAttribute())).collect(Collectors.toList());
            checkAttributeNumVO.setValue(Integer.valueOf(dealingSchemes.size()));
            checkAttributeNumVOS.add(checkAttributeNumVO);
        });
        financialCheckSchemeNumVO.setCheckAttributeNumVOS(checkAttributeNumVOS);
        return financialCheckSchemeNumVO;
    }

    private void sortTreeVOs(List<FinancialCheckSchemeTreeVO> checkSchemeTreeVOS) {
        if (CollectionUtils.isEmpty(checkSchemeTreeVOS)) {
            return;
        }
        checkSchemeTreeVOS.sort(Comparator.comparing(FinancialCheckSchemeTreeVO::getSortOrder));
        for (FinancialCheckSchemeTreeVO checkSchemeTreeVO : checkSchemeTreeVOS) {
            this.sortTreeVOs(checkSchemeTreeVO.getChildren());
        }
    }

    private FinancialCheckSchemeTreeVO loadChildrenNode(FinancialCheckSchemeTreeVO root, Map<String, List<FinancialCheckSchemeTreeVO>> parentId2DirectChildNode) {
        List<FinancialCheckSchemeTreeVO> financialCheckSchemeTreeVOS = parentId2DirectChildNode.get(root.getId());
        if (!CollectionUtils.isEmpty(financialCheckSchemeTreeVOS)) {
            for (FinancialCheckSchemeTreeVO financialCheckSchemeTreeVO : financialCheckSchemeTreeVOS) {
                root.addChildren(this.loadChildrenNode(financialCheckSchemeTreeVO, parentId2DirectChildNode));
            }
            if (SchemeTypeEnum.GROUP.getCode().equals(root.getSchemeType())) {
                Set<Integer> enableStatus = financialCheckSchemeTreeVOS.stream().map(FinancialCheckSchemeTreeVO::getEnable).collect(Collectors.toSet());
                if (enableStatus.size() > 1) {
                    root.setEnable(CheckSchemeStateEnum.MIXED.getCode());
                } else {
                    enableStatus.forEach(arg_0 -> ((FinancialCheckSchemeTreeVO)root).setEnable(arg_0));
                }
            }
        }
        return root;
    }

    private void initRoot() {
        FinancialCheckSchemeEO root = this.queryById(UUIDUtils.emptyUUIDStr());
        if (!Objects.isNull(root)) {
            return;
        }
        FinancialCheckSchemeEO checkSchemeEO = new FinancialCheckSchemeEO();
        checkSchemeEO.setId(UUIDUtils.emptyUUIDStr());
        checkSchemeEO.setSchemeName(ROOTNAMEDEFAULT);
        checkSchemeEO.setCreateTime(new Date());
        checkSchemeEO.setCreator("system");
        checkSchemeEO.setSortOrder(Double.valueOf(0.0));
        checkSchemeEO.setParentId(null);
        checkSchemeEO.setEnable(CheckSchemeStateEnum.ENABLE.getCode());
        checkSchemeEO.setSchemeType(SchemeTypeEnum.ROOT.getCode());
        this.schemeDao.add((BaseEntity)checkSchemeEO);
    }

    private FinancialCheckSchemeTreeVO convertEO2TreeVO(FinancialCheckSchemeEO financialCheckSchemeEO) {
        FinancialCheckSchemeTreeVO financialCheckSchemeTreeVO = new FinancialCheckSchemeTreeVO();
        financialCheckSchemeTreeVO.setId(financialCheckSchemeEO.getId());
        financialCheckSchemeTreeVO.setParentId(financialCheckSchemeEO.getParentId());
        financialCheckSchemeTreeVO.setSortOrder(financialCheckSchemeEO.getSortOrder());
        financialCheckSchemeTreeVO.setTitle(financialCheckSchemeEO.getSchemeName());
        financialCheckSchemeTreeVO.setDescription(financialCheckSchemeEO.getDescription());
        financialCheckSchemeTreeVO.setEnable(financialCheckSchemeEO.getEnable());
        financialCheckSchemeTreeVO.setSchemeType(financialCheckSchemeEO.getSchemeType());
        if (SchemeTypeEnum.GROUP.getCode().equals(financialCheckSchemeEO.getSchemeType())) {
            financialCheckSchemeTreeVO.setSchemeTypeTitle(SchemeTypeEnum.GROUP.getTitle());
        } else if (SchemeTypeEnum.ROOT.getCode().equals(financialCheckSchemeEO.getSchemeType())) {
            financialCheckSchemeTreeVO.setSchemeTypeTitle(SchemeTypeEnum.ROOT.getTitle());
        } else if (SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeEO.getSchemeType())) {
            String unitCodes;
            GcBaseData gcBaseData;
            financialCheckSchemeTreeVO.setSchemeTypeTitle(SchemeTypeEnum.SCHEME.getTitle());
            if (!StringUtils.isEmpty((String)financialCheckSchemeEO.getCheckAttribute()) && Objects.nonNull(gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", financialCheckSchemeEO.getCheckAttribute()))) {
                financialCheckSchemeTreeVO.setCheckAttributeTitle(gcBaseData.getTitle());
            }
            if (!StringUtils.isEmpty((String)financialCheckSchemeEO.getCheckMode())) {
                financialCheckSchemeTreeVO.setCheckMode(financialCheckSchemeEO.getCheckMode());
                financialCheckSchemeTreeVO.setCheckModeTitle(CheckModeEnum.getEnumByCode((String)financialCheckSchemeEO.getCheckMode()).getTitle());
            }
            if (!StringUtils.isEmpty((String)(unitCodes = financialCheckSchemeEO.getUnitCodes()))) {
                String[] unitCodeArr = unitCodes.split(",");
                ArrayList<GcOrgCacheVO> unitList = new ArrayList<GcOrgCacheVO>(unitCodeArr.length);
                for (String unitCode : unitCodeArr) {
                    GcOrgCacheVO gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType()).getOrgByCode(unitCode);
                    if (!Objects.nonNull(gcOrgCacheVOS)) continue;
                    unitList.add(gcOrgCacheVOS);
                }
                List unitTitles = unitList.stream().map(GcOrgCacheVO::getTitle).collect(Collectors.toList());
                financialCheckSchemeTreeVO.setUnitTitles(String.join((CharSequence)",", unitTitles));
            }
            if (Objects.nonNull(financialCheckSchemeEO.getToleranceEnable())) {
                financialCheckSchemeTreeVO.setToleranceEnableTitle(CheckToleranceEnum.getEnumByCode((Integer)financialCheckSchemeEO.getToleranceEnable()).getTitle());
            }
            if (!StringUtils.isEmpty((String)financialCheckSchemeEO.getCheckDimensions())) {
                String[] dims = financialCheckSchemeEO.getCheckDimensions().split(",");
                HashSet<String> dimSet = new HashSet<String>(Arrays.asList(dims));
                DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
                List dimensionEOs = dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
                List dimTitles = dimensionEOs.stream().filter(dimensionEO -> dimSet.contains(dimensionEO.getCode())).map(DimensionEO::getTitle).collect(Collectors.toList());
                if (dimSet.contains("GCNUMBER")) {
                    TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM", OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource"));
                    ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefineByCode.getID(), "GCNUMBER");
                    dimTitles.add(0, columnModelDefine.getTitle());
                }
                financialCheckSchemeTreeVO.setCheckDimensionTitles(String.join((CharSequence)",", dimTitles));
            }
            FinancialCheckProjectEO param = new FinancialCheckProjectEO();
            param.setSchemeId(financialCheckSchemeEO.getId());
            List financialCheckProjectEOS = this.financialCheckProjectDao.selectList((BaseEntity)param);
            financialCheckProjectEOS.sort(Comparator.comparing(FinancialCheckProjectEO::getSortOrder));
            if (!CollectionUtils.isEmpty(financialCheckProjectEOS)) {
                List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CHECK_PROJECT");
                Map<String, String> projectCode2Title = baseData.stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle, (k1, k2) -> k1));
                ArrayList<String> assets = new ArrayList<String>();
                ArrayList<String> debts = new ArrayList<String>();
                for (FinancialCheckProjectEO financialCheckProjectEO : financialCheckProjectEOS) {
                    BusinessRoleEnum subjectCategoryEnum = BusinessRoleEnum.getEnumByCode((Integer)financialCheckProjectEO.getBusinessRole());
                    if (BusinessRoleEnum.DEBT.equals((Object)subjectCategoryEnum)) {
                        debts.add(projectCode2Title.get(financialCheckProjectEO.getCheckProject()));
                        continue;
                    }
                    assets.add(projectCode2Title.get(financialCheckProjectEO.getCheckProject()));
                }
                financialCheckSchemeTreeVO.setAssetProjects(String.join((CharSequence)",", assets));
                financialCheckSchemeTreeVO.setDebtProjects(String.join((CharSequence)",", debts));
            }
        }
        return financialCheckSchemeTreeVO;
    }

    @Override
    public FinancialCheckSchemeVO queryCheckScheme(String id) {
        FinancialCheckSchemeEO financialCheckSchemeEO = this.queryById(id);
        if (Objects.isNull(financialCheckSchemeEO)) {
            throw new BusinessRuntimeException("\u6570\u636e\u5df2\u7ecf\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5\u3002");
        }
        FinancialCheckSchemeExtendExecutor executor = FinancialCheckSchemeExtendExecutorFactory.getExecutor(financialCheckSchemeEO.getCheckMode());
        return executor.convertEO2VO(financialCheckSchemeEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void start(String id, boolean startFlag) {
        FinancialCheckSchemeEO financialCheckSchemeEO = this.queryById(id);
        if (Objects.isNull(financialCheckSchemeEO)) {
            throw new BusinessRuntimeException("\u6b64\u65b9\u6848\u5df2\u88ab\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5\u3002");
        }
        int schemeStatus = startFlag ? CheckSchemeStateEnum.ENABLE.getCode().intValue() : CheckSchemeStateEnum.DISABLE.getCode().intValue();
        if (SchemeTypeEnum.SCHEME.getCode().equals(financialCheckSchemeEO.getSchemeType())) {
            this.schemeDao.start(id, schemeStatus);
        } else if (SchemeTypeEnum.GROUP.getCode().equals(financialCheckSchemeEO.getSchemeType())) {
            this.schemeDao.startByParentId(id, schemeStatus);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void move(String id, double step) {
        if (step == 0.0) {
            throw new BusinessRuntimeException("\u79fb\u52a8\u53c2\u6570\u6709\u8bef\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u540e\u91cd\u8bd5\u3002");
        }
        FinancialCheckSchemeEO schemeEo = this.queryById(id);
        if (schemeEo == null) {
            throw new BusinessRuntimeException("\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5\u3002");
        }
        String parentId = schemeEo.getParentId();
        if (parentId == null) {
            throw new BusinessRuntimeException("\u6839\u8282\u70b9\u4e0d\u80fd\u79fb\u52a8\u3002");
        }
        this.moveScheme(schemeEo, step);
    }

    @Override
    public FinancialCheckSchemeEO queryById(String id) {
        if (StringUtils.isEmpty((String)id)) {
            return null;
        }
        FinancialCheckSchemeEO param = new FinancialCheckSchemeEO();
        param.setId(id);
        return (FinancialCheckSchemeEO)this.schemeDao.selectByEntity((BaseEntity)param);
    }

    @Override
    public List<CheckBusinessRoleOptionVO> queryBusinessRoleOptions(Map<String, String> params) {
        String id = params.get("id");
        FinancialCheckSchemeEO financialCheckSchemeEO = this.queryById(id);
        if (Objects.isNull(financialCheckSchemeEO)) {
            throw new BusinessRuntimeException("\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5\u3002");
        }
        String checkAttribute = params.get("checkAttribute");
        if (StringUtils.isEmpty((String)checkAttribute) && StringUtils.isEmpty((String)(checkAttribute = financialCheckSchemeEO.getCheckAttribute()))) {
            throw new BusinessRuntimeException("\u65b9\u6848\u672a\u8bbe\u7f6e\u5bf9\u8d26\u5c5e\u6027\u3002");
        }
        GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", checkAttribute);
        if (Objects.isNull(gcBaseData)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u8d26\u5c5e\u6027\u57fa\u7840\u6570\u636e\u3002");
        }
        ArrayList<CheckBusinessRoleOptionVO> checkBusinessRoleOptionVOS = new ArrayList<CheckBusinessRoleOptionVO>();
        CheckBusinessRoleOptionVO checkBusinessRoleOptionVO_1 = new CheckBusinessRoleOptionVO();
        checkBusinessRoleOptionVO_1.setValue(Integer.valueOf(1));
        checkBusinessRoleOptionVO_1.setTitle(gcBaseData.getFieldVal("BUSINESSROLE1").toString());
        checkBusinessRoleOptionVOS.add(checkBusinessRoleOptionVO_1);
        CheckBusinessRoleOptionVO checkBusinessRoleOptionVO_2 = new CheckBusinessRoleOptionVO();
        checkBusinessRoleOptionVO_2.setValue(Integer.valueOf(-1));
        checkBusinessRoleOptionVO_2.setTitle(gcBaseData.getFieldVal("BUSINESSROLE2").toString());
        checkBusinessRoleOptionVOS.add(checkBusinessRoleOptionVO_2);
        CheckBusinessRoleOptionVO checkBusinessRoleOptionVO_3 = new CheckBusinessRoleOptionVO();
        checkBusinessRoleOptionVO_3.setValue(Integer.valueOf(0));
        checkBusinessRoleOptionVO_3.setTitle("\u5168\u90e8");
        checkBusinessRoleOptionVOS.add(checkBusinessRoleOptionVO_3);
        return checkBusinessRoleOptionVOS;
    }

    @Override
    public void matchScheme(FinancialCheckMatchSchemeVO financialCheckMatchSchemeVO) {
        Integer acctYear = financialCheckMatchSchemeVO.getAcctYear();
        List gcRelatedItemEOS = this.gcRelatedItemDao.listNoSchemeIdByAcctYear(acctYear);
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            Map<Integer, List<GcRelatedItemEO>> group = gcRelatedItemEOS.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getAcctPeriod));
            for (Integer key : group.keySet()) {
                PeriodWrapper periodWrapper = new PeriodWrapper(acctYear.intValue(), 4, key.intValue());
                String dataTime = periodWrapper.toString();
                List<String> ids = group.get(key).stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
                ((FinancialCheckService)SpringContextUtils.getBean(FinancialCheckService.class)).realTimeCheck(new RealTimeCheckOrOffsetParam(ids, dataTime));
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            int accPeriod = calendar.get(2) + 1;
            int currYear = calendar.get(1);
            if (currYear > acctYear) {
                accPeriod = 12;
            }
            PeriodWrapper periodWrapper = new PeriodWrapper(acctYear.intValue(), 4, accPeriod);
            List<String> ids = gcRelatedItemEOS.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            ((FinancialCheckService)SpringContextUtils.getBean(FinancialCheckService.class)).realTimeCheck(new RealTimeCheckOrOffsetParam(ids, periodWrapper.toString()));
        }
    }

    @Override
    public void matchCheckSchemes(List<GcRelatedItemEO> items, List<String> schemeIds) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        List financialCheckSchemeEOS = this.schemeDao.getSchemeByIds(schemeIds);
        if (CollectionUtils.isEmpty(financialCheckSchemeEOS)) {
            return;
        }
        Map schemeId2SelfMap = financialCheckSchemeEOS.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        HashSet<String> schemeIdSet = new HashSet<String>(schemeIds);
        ArrayList<String> sortedSchemeIdList = new ArrayList<String>();
        FinancialCheckGroupVO financialCheckGroupVO = new FinancialCheckGroupVO();
        financialCheckGroupVO.setAcctYear(((FinancialCheckSchemeEO)financialCheckSchemeEOS.get(0)).getAcctYear());
        List<FinancialCheckSchemeBaseDataVO> financialCheckSchemeBaseDataVOS = this.treeEnableScheme(financialCheckGroupVO);
        if (!CollectionUtils.isEmpty(financialCheckSchemeBaseDataVOS)) {
            for (FinancialCheckSchemeBaseDataVO financialCheckSchemeBaseDataVO : financialCheckSchemeBaseDataVOS) {
                List children = financialCheckSchemeBaseDataVO.getChildren();
                if (CollectionUtils.isEmpty(children)) continue;
                for (FinancialCheckSchemeBaseDataVO child : children) {
                    if (!schemeIdSet.contains(child.getCode())) continue;
                    sortedSchemeIdList.add(child.getCode());
                }
            }
        }
        List financialCheckProjectEOS = this.financialCheckProjectDao.selectBySchemeIds(schemeIds);
        Map projectId2Self = financialCheckProjectEOS.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        List financialCheckMappingEOS = this.financialCheckMappingDao.selectBySchemeIds(schemeIds);
        Map projectGroupBySchemeId = financialCheckMappingEOS.stream().collect(Collectors.groupingBy(FinancialCheckMappingEO::getSchemeId, Collectors.mapping(FinancialCheckMappingEO::getCheckProject, Collectors.toSet())));
        Map subjectCodesGroupByProject = financialCheckMappingEOS.stream().collect(Collectors.groupingBy(FinancialCheckMappingEO::getCheckProject, Collectors.mapping(FinancialCheckMappingEO::getSubject, Collectors.toSet())));
        subjectCodesGroupByProject.forEach((project, subjectCodes) -> {
            Set allChildrenContainSelfByCodes = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)subjectCodes);
            subjectCodes.clear();
            subjectCodes.addAll(allChildrenContainSelfByCodes);
        });
        HashMap<String, Map<String, Map<Integer, Map<String, List<String>>>>> schemeSubjectScopeCache = new HashMap<String, Map<String, Map<Integer, Map<String, List<String>>>>>(1);
        GcOrgCenterService service = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE);
        ArrayList<GcRelatedItemEO> matchedItems = new ArrayList<GcRelatedItemEO>();
        block2: for (GcRelatedItemEO item : items) {
            String unitId = item.getUnitId();
            String oppUnitId = item.getOppUnitId();
            if (FcUtils.fieldValueIsEmpty((String)unitId) || FcUtils.fieldValueIsEmpty((String)oppUnitId)) continue;
            String subject = item.getSubjectCode();
            for (String schemeId : sortedSchemeIdList) {
                String[] unitCodeArr;
                Set allLevelsChildrenAndSelf;
                String unitCodes;
                FinancialCheckSchemeEO financialCheckSchemeEO = (FinancialCheckSchemeEO)schemeId2SelfMap.get(schemeId);
                String schemeCondition = financialCheckSchemeEO.getSchemeCondition();
                if (!StringUtils.isEmpty((String)schemeCondition) && !this.filterBySchemeCondition(schemeCondition, item) || !StringUtils.isEmpty((String)(unitCodes = financialCheckSchemeEO.getUnitCodes())) && (!(allLevelsChildrenAndSelf = OrgUtils.getAllLevelsChildrenAndSelf(new ArrayList<String>(Arrays.asList(unitCodeArr = unitCodes.split(","))), (GcOrgCenterService)service)).contains(unitId) || !allLevelsChildrenAndSelf.contains(oppUnitId))) continue;
                if (CheckModeEnum.OFFSETVCHR.getCode().equals(financialCheckSchemeEO.getCheckMode())) {
                    if ("SystemDefault".equals(item.getGcNumber())) continue;
                    this.putSchemeInfo(item, financialCheckSchemeEO, schemeSubjectScopeCache);
                    matchedItems.add(item);
                    continue block2;
                }
                Set projects = projectGroupBySchemeId.get(financialCheckSchemeEO.getId());
                if (CollectionUtils.isEmpty(projects)) {
                    logger.info("\u5bf9\u8d26\u65b9\u6848\u672a\u8bbe\u7f6e\u5bf9\u8d26\u9879\u76ee\uff1a{}", (Object)financialCheckSchemeEO.getSchemeName());
                    continue;
                }
                for (String project2 : projects) {
                    Set subjects = subjectCodesGroupByProject.get(project2);
                    if (CollectionUtils.isEmpty(subjects)) {
                        logger.info("\u5bf9\u8d26\u65b9\u6848:{},\u5bf9\u8d26\u9879\u76ee\uff1a{},\u672a\u8bbe\u7f6e\u79d1\u76ee\u3002", (Object)financialCheckSchemeEO.getSchemeName(), (Object)project2);
                        continue;
                    }
                    if (!subjects.contains(subject)) continue;
                    FinancialCheckProjectEO financialCheckProjectEO = (FinancialCheckProjectEO)projectId2Self.get(project2);
                    matchedItems.add(item);
                    item.setCheckRuleId(financialCheckSchemeEO.getId());
                    item.setCheckProject(financialCheckProjectEO.getCheckProject());
                    item.setCheckProjectDirection(financialCheckProjectEO.getCheckProjectDirection());
                    item.setBusinessRole(financialCheckProjectEO.getBusinessRole());
                    item.setCheckAttribute(financialCheckSchemeEO.getCheckAttribute());
                    String uc = BusinessRoleEnum.ASSET.getCode().equals(financialCheckProjectEO.getBusinessRole()) ? unitId + ";" + oppUnitId : oppUnitId + ";" + unitId;
                    item.setUnitCombine(uc);
                    continue block2;
                }
            }
        }
    }

    private void putSchemeInfo(GcRelatedItemEO item, FinancialCheckSchemeEO scheme, Map<String, Map<String, Map<Integer, Map<String, List<String>>>>> schemeSubjectScopeCache) {
        Map<Object, Object> subjectScopeMap;
        item.setCheckRuleId(scheme.getId());
        if (schemeSubjectScopeCache.containsKey(scheme.getId())) {
            subjectScopeMap = schemeSubjectScopeCache.get(scheme.getId());
        } else {
            subjectScopeMap = new HashMap(2);
            String jsonString = scheme.getJsonString();
            if (StringUtils.isEmpty((String)jsonString)) {
                return;
            }
            Map settings = (Map)JsonUtils.readValue((String)jsonString, (TypeReference)new TypeReference<Map<String, Object>>(){});
            List bilateralSubSettings = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(settings.get("bilateralSubSettings")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
            if (CollectionUtils.isEmpty(bilateralSubSettings)) {
                return;
            }
            for (Map bilateralSubSetting : bilateralSubSettings) {
                HashMap currentSubjectScope = new HashMap(2);
                List assetSubjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(bilateralSubSetting.get("subjects")), (TypeReference)new TypeReference<List<String>>(){});
                HashMap assetSubjectScope = new HashMap();
                if (!CollectionUtils.isEmpty(assetSubjects)) {
                    assetSubjects.forEach(assetSubject -> assetSubjectScope.put(assetSubject, BaseDataUtils.getAllChildrenContainSelf((String)"MD_ACCTSUBJECT", (String)assetSubject)));
                }
                currentSubjectScope.put(BusinessRoleEnum.ASSET.getCode(), assetSubjectScope);
                List debtSubjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(bilateralSubSetting.get("debtSubjects")), (TypeReference)new TypeReference<List<String>>(){});
                HashMap debtSubjectScope = new HashMap();
                if (!CollectionUtils.isEmpty(debtSubjects)) {
                    debtSubjects.forEach(debtSubject -> debtSubjectScope.put(debtSubject, BaseDataUtils.getAllChildrenContainSelf((String)"MD_ACCTSUBJECT", (String)debtSubject)));
                }
                currentSubjectScope.put(BusinessRoleEnum.DEBT.getCode(), debtSubjectScope);
                subjectScopeMap.put((String)bilateralSubSetting.get("group"), currentSubjectScope);
            }
            schemeSubjectScopeCache.put(scheme.getId(), subjectScopeMap);
        }
        String subjectCode = StringUtils.isEmpty((String)item.getCfItemCode()) || "#".equals(item.getCfItemCode()) ? item.getSubjectCode() : item.getCfItemCode();
        for (Map.Entry<Object, Object> entry : subjectScopeMap.entrySet()) {
            Iterator<Object> iterator = Arrays.asList(BusinessRoleEnum.ASSET.getCode(), BusinessRoleEnum.DEBT.getCode()).iterator();
            while (iterator.hasNext()) {
                int role = (Integer)iterator.next();
                Map subjectScope = (Map)((Map)entry.getValue()).get(role);
                if (subjectScope == null) continue;
                for (Map.Entry scopeEntry : subjectScope.entrySet()) {
                    if (!((List)scopeEntry.getValue()).contains(subjectCode)) continue;
                    item.setBusinessRole(Integer.valueOf(role));
                    item.setCheckAttribute((String)entry.getKey());
                    item.setCheckProject((String)scopeEntry.getKey());
                    item.setUnitCombine(role == BusinessRoleEnum.ASSET.getCode() ? item.getUnitId() + ";" + item.getOppUnitId() : item.getOppUnitId() + ";" + item.getUnitId());
                    return;
                }
            }
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean filterBySchemeCondition(String schemeCondition, GcRelatedItemEO item) {
        GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
        context.setDefaultGroupName("GC_RELATED_ITEM");
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{"GC_RELATED_ITEM"});
        context.setDataSet(dataSet);
        GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
        row.setData((DefaultTableEntity)item);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                try {
                    evaluator.prepare((ExecutorContext)context, null, schemeCondition);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(schemeCondition + " \u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                }
                try {
                    boolean e = evaluator.judge((DataRow)row);
                    return e;
                }
                catch (Exception e) {
                    try {
                        throw new BusinessRuntimeException(schemeCondition + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
            }
        }
        catch (Exception e) {
            logger.warn("\u5173\u8054\u4ea4\u6613\u5339\u914d\u65b9\u6848\u65f6\u6267\u884c\u516c\u5f0f\u51fa\u73b0\u5f02\u5e38" + e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, Map<Integer, List<GcBaseData>>> queryOneLevelSubjectsBySchemeAndCHeckAttribute(String schemeId, List<String> checkAttribute) {
        HashMap<String, Map<Integer, List<GcBaseData>>> subjectMap = new HashMap<String, Map<Integer, List<GcBaseData>>>(2);
        FinancialCheckSchemeEO scheme = this.queryById(schemeId);
        String jsonString = scheme.getJsonString();
        if (StringUtils.isEmpty((String)jsonString)) {
            return subjectMap;
        }
        Map settings = (Map)JsonUtils.readValue((String)jsonString, (TypeReference)new TypeReference<Map<String, Object>>(){});
        List bilateralSubSettings = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(settings.get("bilateralSubSettings")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        for (Map bilateralSubSetting : bilateralSubSettings) {
            String group = (String)bilateralSubSetting.get("group");
            if (!checkAttribute.contains(group)) continue;
            HashMap subjects = new HashMap(2);
            List assetSubjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(bilateralSubSetting.get("subjects")), (TypeReference)new TypeReference<List<String>>(){});
            ArrayList assetSubjectBaseData = new ArrayList();
            if (!CollectionUtils.isEmpty(assetSubjects)) {
                assetSubjects.forEach(code -> {
                    GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", code);
                    assetSubjectBaseData.add(gcBaseData);
                });
            }
            subjects.put(BusinessRoleEnum.ASSET.getCode(), assetSubjectBaseData);
            List debtSubjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(bilateralSubSetting.get("debtSubjects")), (TypeReference)new TypeReference<List<String>>(){});
            ArrayList debtSubjectBaseData = new ArrayList();
            if (!CollectionUtils.isEmpty(debtSubjects)) {
                debtSubjects.forEach(code -> {
                    GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", code);
                    debtSubjectBaseData.add(gcBaseData);
                });
            }
            subjects.put(BusinessRoleEnum.DEBT.getCode(), debtSubjectBaseData);
            subjectMap.put(group, subjects);
            break;
        }
        return subjectMap;
    }

    private void validBySchemeName(String schemeName, Integer acctYear) {
        if (StringUtils.isEmpty((String)schemeName)) {
            throw new BusinessRuntimeException("\u65b9\u6848\u6216\u5206\u7ec4\u540d\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        FinancialCheckSchemeEO param = new FinancialCheckSchemeEO();
        param.setSchemeName(schemeName);
        param.setAcctYear(acctYear);
        List financialCheckSchemeEOS = this.schemeDao.selectList((BaseEntity)param);
        if (!CollectionUtils.isEmpty(financialCheckSchemeEOS)) {
            throw new BusinessRuntimeException(acctYear + "\u5e74\u65b9\u6848\u6216\u5206\u7ec4\u540d\u91cd\u590d");
        }
    }

    private void moveScheme(FinancialCheckSchemeEO schemeEo, double step) {
        FinancialCheckSchemeEO schemeEoExchange;
        if (step > 0.0) {
            schemeEoExchange = this.schemeDao.getFrontScheme(schemeEo.getParentId(), schemeEo.getAcctYear().intValue(), schemeEo.getSortOrder().doubleValue());
            if (Objects.isNull(schemeEoExchange)) {
                throw new BusinessRuntimeException("\u5df2\u5230\u9876\u90e8");
            }
        } else {
            schemeEoExchange = this.schemeDao.getAfterScheme(schemeEo.getParentId(), schemeEo.getAcctYear().intValue(), schemeEo.getSortOrder().doubleValue());
            if (Objects.isNull(schemeEoExchange)) {
                throw new BusinessRuntimeException("\u5df2\u5230\u5e95\u90e8");
            }
        }
        double sortOrder = schemeEo.getSortOrder();
        schemeEo.setSortOrder(schemeEoExchange.getSortOrder());
        schemeEoExchange.setSortOrder(Double.valueOf(sortOrder));
        this.schemeDao.update((BaseEntity)schemeEo);
        this.schemeDao.update((BaseEntity)schemeEoExchange);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelCheckScheme(String schemeId, Integer year, Integer period, List<String> subjects) {
        Set subjectSet = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", subjects);
        List relatedItemS = this.gcRelatedItemDao.listBySchemeIdAndCondition(schemeId, year, period, new ArrayList(subjectSet));
        if (CollectionUtils.isEmpty(relatedItemS)) {
            return;
        }
        List<GcRelatedItemEO> checkedItems = relatedItemS.stream().filter(item -> Objects.nonNull(item.getCheckId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(checkedItems)) {
            UnitStateUtils.checkReconciliationPeriodStatus(checkedItems);
        }
        this.cancelCheckScheme(relatedItemS);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<GcRelatedItemEO> cancelCheckScheme(List<GcRelatedItemEO> relatedItemS) {
        List<String> unCheckIds;
        List ids = relatedItemS.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        Map<String, List<GcRelatedItemEO>> groups = relatedItemS.stream().filter(item -> !StringUtils.isEmpty((String)item.getCheckId())).collect(Collectors.groupingBy(GcRelatedItemEO::getCheckId));
        ArrayList<GcRelatedItemEO> needCancelCheckItems = new ArrayList<GcRelatedItemEO>();
        groups.forEach((k, group) -> {
            Integer minPeriod = group.stream().map(GcRelatedItemEO::getCheckPeriod).min(Integer::compareTo).get();
            List items = this.gcRelatedItemDao.findByCheckIdAndCheckPeriod(k, minPeriod);
            needCancelCheckItems.addAll(items);
        });
        List checkedItemIds = needCancelCheckItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(needCancelCheckItems)) {
            needCancelCheckItems.forEach(item -> {
                if (ids.contains(item.getId())) {
                    this.clearItemSchemeInfo((GcRelatedItemEO)item);
                }
            });
            this.gcRelatedItemCommandService.cancelCheck(needCancelCheckItems, true);
        }
        if (!CollectionUtils.isEmpty(unCheckIds = ids.stream().filter(id -> !checkedItemIds.contains(id)).collect(Collectors.toList()))) {
            List<GcRelatedItemEO> needCancelSchemeItems = relatedItemS.stream().filter(item -> unCheckIds.contains(item.getId())).collect(Collectors.toList());
            if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
                this.offsetExecutor.deleteOffsetAndRel(unCheckIds);
            }
            needCancelSchemeItems.forEach(this::clearItemSchemeInfo);
            this.gcRelatedItemCommandService.updateCheckSchemeInfo(needCancelSchemeItems);
        }
        return this.gcRelatedItemDao.queryByIds(ids);
    }

    private void clearItemSchemeInfo(GcRelatedItemEO item) {
        item.setCheckRuleId(null);
        item.setBusinessRole(null);
        item.setCheckProject(null);
        item.setCheckProjectDirection(null);
        item.setUnitCombine(null);
    }

    @Override
    public List<FinancialCheckSchemeEO> listSchemeByYear(Integer year) {
        FinancialCheckSchemeEO param = new FinancialCheckSchemeEO();
        param.setAcctYear(year);
        param.setEnable(CheckSchemeStateEnum.ENABLE.getCode());
        param.setSchemeType(SchemeTypeEnum.SCHEME.getCode());
        return this.schemeDao.selectList((BaseEntity)param);
    }
}

