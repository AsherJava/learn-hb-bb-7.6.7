/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionDataSetStrategy
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionQueryTabeStrategy
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn
 *  com.jiuqi.nr.jtable.filter.RegionTabFilter
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.params.input.GradeCellInfo
 *  com.jiuqi.nr.jtable.params.input.RegionFilterInfo
 *  com.jiuqi.nr.jtable.params.input.RegionGradeInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.output.RegionDataSet
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DataFormaterCache
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.filter.RegionTabFilter;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class PushInventoryFunction
extends AdvanceFunction
implements INrFunction {
    @Lazy
    @Autowired
    private transient IRunTimeViewController iRunTimeViewController;
    @Lazy
    @Autowired
    private transient InputDataService inputDataService;
    @Lazy
    @Autowired
    private transient IJtableParamService jtableParamService;
    @Lazy
    @Autowired
    private transient IDataAccessProvider iDataAccessProvider;
    @Lazy
    @Autowired
    private transient InputDataNameProvider inputDataNameProvider;
    private static final Logger logger = LoggerFactory.getLogger(PushInventoryFunction.class);
    private static final long serialVersionUID = 1L;
    private static final String FUNCTION_NAME = "PushInventory";

    PushInventoryFunction() {
        this.parameters().add(new Parameter("formCodes", 6, "\u62a5\u8868\u4ee3\u7801"));
        this.parameters().add(new Parameter("zbCodes", 6, "\u4e1a\u52a1\u4e3b\u952e\u4ee3\u7801"));
        this.parameters().add(new Parameter("filterConditon", 6, "\u8fc7\u6ee4\u6761\u4ef6", true));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u590d\u5236\u5b58\u8d27\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    @Transactional
    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String formCodes = (String)parameters.get(0).evaluate(null);
        String[] formCodeArr = formCodes.split(":");
        String zbCodes = (String)parameters.get(1).evaluate(null);
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)((QueryContext)context).getExeContext().getEnv();
        if (!formCodes.contains(":")) {
            throw new BusinessRuntimeException("\u7b2c\u4e00\u4e2a\u53c2\u6570\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u9700\u5305\u542b\":\"");
        }
        FormDefine buyerFormDefine = null;
        FormDefine sellerFormDefine = null;
        try {
            buyerFormDefine = this.iRunTimeViewController.queryFormByCodeInScheme(env.getFormSchemeDefine().getKey(), formCodeArr[0]);
            sellerFormDefine = this.iRunTimeViewController.queryFormByCodeInScheme(env.getFormSchemeDefine().getKey(), formCodeArr[1]);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u9500\u552e\u65b9\u6216\u91c7\u8d2d\u65b9\u51fa\u9519\uff1a", e);
        }
        if (buyerFormDefine == null) {
            throw new BusinessRuntimeException("\u91c7\u8d2d\u65b9\u62a5\u8868\u4e0d\u5b58\u5728");
        }
        if (sellerFormDefine == null) {
            throw new BusinessRuntimeException("\u9500\u552e\u65b9\u62a5\u8868\u4e0d\u5b58\u5728");
        }
        List<String> zbCodeList = Arrays.asList(zbCodes.split(","));
        for (String zbCode : zbCodeList) {
            List buyerFields = this.jtableParamService.getForm(buyerFormDefine.getKey(), zbCode).getFields();
            List sellerFields = this.jtableParamService.getForm(sellerFormDefine.getKey(), zbCode).getFields();
            if (CollectionUtils.isEmpty(buyerFields)) {
                throw new BusinessRuntimeException("\u91c7\u8d2d\u65b9\u6307\u6807\u4ee3\u7801:" + zbCode + "\u4e0d\u5b58\u5728");
            }
            if (!CollectionUtils.isEmpty(sellerFields)) continue;
            throw new BusinessRuntimeException("\u9500\u552e\u65b9\u6307\u6807\u4ee3\u7801:" + zbCode + "\u4e0d\u5b58\u5728");
        }
        return super.validate(context, parameters);
    }

    @Transactional
    public Object evalute(IContext context, List<IASTNode> parameters) {
        try {
            String formCodes = (String)parameters.get(0).evaluate(context);
            String zbCodes = (String)parameters.get(1).evaluate(context);
            String unitCode = (String)((QueryContext)context).getMasterKeys().getValue("MD_ORG");
            DimensionValueSet currentMasterKey = ((QueryContext)context).getCurrentMasterKey();
            ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)((QueryContext)context).getExeContext().getEnv();
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)env.getFormSchemeDefine().getKey(), (String)((String)currentMasterKey.getValue("DATATIME")));
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)((String)currentMasterKey.getValue("MD_GCORGTYPE")), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
            String[] formCodeArr = formCodes.split(":");
            List<String> zbCodeList = Arrays.asList(zbCodes.split(","));
            FormDefine buyerFormDefine = this.iRunTimeViewController.queryFormByCodeInScheme(env.getFormSchemeDefine().getKey(), formCodeArr[0]);
            FormDefine sellerFormDefine = this.iRunTimeViewController.queryFormByCodeInScheme(env.getFormSchemeDefine().getKey(), formCodeArr[1]);
            DataRegionDefine buyerCurrDataRegionDefine = this.getCurrDataRegionDefine(buyerFormDefine.getKey());
            DataRegionDefine sellerCurrDataRegionDefine = this.getCurrDataRegionDefine(sellerFormDefine.getKey());
            List buyerAllLinksInRegion = this.iRunTimeViewController.getAllLinksInRegion(buyerCurrDataRegionDefine.getKey());
            List sellerAllLinksInRegion = this.iRunTimeViewController.getAllLinksInRegion(sellerCurrDataRegionDefine.getKey());
            if (buyerFormDefine != null && sellerFormDefine != null) {
                String filterConditon = (String)parameters.get(2).evaluate(context);
                List<InputDataEO> buyerInputDataEOS = this.getInputDataEOS(buyerFormDefine.getFormCode(), buyerFormDefine.getKey(), env, currentMasterKey, buyerCurrDataRegionDefine, null, filterConditon);
                String rootUnitCode = this.getRootCode(unitCode, orgTool);
                DimensionValueSet dimensionValueSet = new DimensionValueSet(currentMasterKey);
                dimensionValueSet.setValue("MD_ORG", (Object)rootUnitCode);
                List<InputDataEO> sellerInputDataEOS = this.getInputDataEOS(sellerFormDefine.getFormCode(), sellerFormDefine.getKey(), env, dimensionValueSet, sellerCurrDataRegionDefine, unitCode, null);
                HashSet<ArrayKey> allKeys = new HashSet<ArrayKey>();
                Map<ArrayKey, InputDataEO> buyerUnitAndOppUnit2EoMap = this.getKey2EOMap(buyerInputDataEOS, allKeys, zbCodeList, "buyer");
                Map<ArrayKey, InputDataEO> sellerOppUnitAndUnit2EoMap = this.getKey2EOMap(sellerInputDataEOS, allKeys, zbCodeList, null);
                Map<String, Field> fieldCode2fieldMap = this.getFieldCode2fieldMap();
                String tableName = this.inputDataNameProvider.getTableNameByTaskId(env.getTaskDefine().getKey());
                EntNativeSqlDefaultDao inputDataSqlDao = EntNativeSqlDefaultDao.newInstance((String)tableName, InputDataEO.class);
                allKeys.forEach(key -> {
                    InputDataEO buyerInputDataEO = (InputDataEO)((Object)((Object)buyerUnitAndOppUnit2EoMap.get(key)));
                    InputDataEO sellerInputDataEO = (InputDataEO)((Object)((Object)sellerOppUnitAndUnit2EoMap.get(key)));
                    if (buyerInputDataEO != null) {
                        if (sellerInputDataEO == null) {
                            this.saveSellerInputData(buyerInputDataEO, sellerFormDefine.getKey(), (EntNativeSqlDefaultDao<InputDataEO>)inputDataSqlDao);
                        } else {
                            this.updateSellerInputData(buyerInputDataEO, sellerInputDataEO, buyerAllLinksInRegion, sellerAllLinksInRegion, fieldCode2fieldMap, (EntNativeSqlDefaultDao<InputDataEO>)inputDataSqlDao);
                        }
                    } else if (sellerInputDataEO != null) {
                        inputDataSqlDao.delete((BaseEntity)sellerInputDataEO);
                    }
                });
            }
        }
        catch (Exception e) {
            logger.error("\u5b58\u8d27\u53f0\u8d26\u590d\u5236\u5931\u8d25\uff1a", e);
        }
        return null;
    }

    private Map<String, Field> getFieldCode2fieldMap() {
        HashMap<String, Field> fieldCode2fieldMap = new HashMap<String, Field>();
        Field[] declaredFields = InputDataEO.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; ++i) {
            fieldCode2fieldMap.put(declaredFields[i].getName().toLowerCase(), declaredFields[i]);
        }
        return fieldCode2fieldMap;
    }

    private DataRegionDefine getCurrDataRegionDefine(String formDefinKey) {
        List buyerAllRegionsInForms = this.iRunTimeViewController.getAllRegionsInForm(formDefinKey);
        return buyerAllRegionsInForms.stream().filter(dataRegionDefine -> dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE).findFirst().orElse(null);
    }

    private void saveSellerInputData(InputDataEO sellerInputDataEO, String sellerFormDefineKey, EntNativeSqlDefaultDao<InputDataEO> inputDataSqlDao) {
        String uuidStr = UUIDOrderUtils.newUUIDStr();
        String oppUnitId = sellerInputDataEO.getMdOrg();
        sellerInputDataEO.setId(uuidStr);
        sellerInputDataEO.setBizkeyOrder(uuidStr);
        sellerInputDataEO.setFormId(sellerFormDefineKey);
        sellerInputDataEO.setMdOrg(sellerInputDataEO.getOppUnitId());
        sellerInputDataEO.setOrgCode(sellerInputDataEO.getOppUnitId());
        sellerInputDataEO.setOppUnitId(oppUnitId);
        sellerInputDataEO.setOffsetState(ReportOffsetStateEnum.NOTOFFSET.getValue());
        sellerInputDataEO.setOffsetAmt(null);
        sellerInputDataEO.setCreateTime(new Date());
        sellerInputDataEO.getFields().put("ISSELL", 1);
        inputDataSqlDao.add((BaseEntity)sellerInputDataEO);
    }

    private void updateSellerInputData(InputDataEO buyerInputDataEO, InputDataEO sellerInputDataEO, List<DataLinkDefine> buyerAllLinksInRegion, List<DataLinkDefine> sellerAllLinksInRegion, Map<String, Field> fieldCode2fieldMap, EntNativeSqlDefaultDao<InputDataEO> inputDataSqlDao) {
        Map buyerFields = buyerInputDataEO.getFields();
        HashMap fieldKey2FiledCodeMap = new HashMap(16);
        sellerAllLinksInRegion.forEach(daDataLinkDefine -> {
            FieldData fieldData = this.jtableParamService.getField(daDataLinkDefine.getLinkExpression());
            fieldKey2FiledCodeMap.put(fieldData.getFieldKey(), fieldData.getFieldCode());
        });
        String sellerFormDefineKey = sellerInputDataEO.getFormId();
        String mdOrg = sellerInputDataEO.getMdOrg();
        String oppUnitId = sellerInputDataEO.getOppUnitId();
        for (DataLinkDefine daDataLinkDefine2 : buyerAllLinksInRegion) {
            String fieldCode;
            String fieldCodeOfLowerCase;
            Field field;
            if (!fieldKey2FiledCodeMap.containsKey(daDataLinkDefine2.getLinkExpression()) || (field = fieldCode2fieldMap.get(fieldCodeOfLowerCase = (fieldCode = (String)fieldKey2FiledCodeMap.get(daDataLinkDefine2.getLinkExpression())).toLowerCase())) == null) continue;
            field.setAccessible(true);
            try {
                field.set((Object)sellerInputDataEO, buyerFields.get(fieldCode));
            }
            catch (IllegalAccessException e) {
                logger.error("\u9500\u552e\u65b9\u6570\u636e\u8d4b\u503c\u51fa\u9519\uff1a", e);
            }
        }
        sellerInputDataEO.setUpdateTime(new Date());
        sellerInputDataEO.setFormId(sellerFormDefineKey);
        sellerInputDataEO.setMdOrg(mdOrg);
        sellerInputDataEO.setOrgCode(mdOrg);
        sellerInputDataEO.setOppUnitId(oppUnitId);
        inputDataSqlDao.update((BaseEntity)sellerInputDataEO);
    }

    private String getRootCode(String unitCode, GcOrgCenterService orgTool) {
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(unitCode);
        if (orgCacheVO != null && orgCacheVO.getParentStr() != null) {
            return orgCacheVO.getParentStr().split("/")[0];
        }
        return "";
    }

    private Map<ArrayKey, InputDataEO> getKey2EOMap(List<InputDataEO> inputDataEOS, Set<ArrayKey> allKeys, List zbCodeList, String buyerFlag) {
        HashMap<ArrayKey, InputDataEO> unitAndOppUnit2EoMap = new HashMap<ArrayKey, InputDataEO>(16);
        if (!CollectionUtils.isEmpty(inputDataEOS)) {
            inputDataEOS.forEach(item -> {
                ArrayList<String> values = !StringUtils.isEmpty(buyerFlag) ? new ArrayList<String>(Arrays.asList(item.getMdOrg(), item.getOppUnitId())) : new ArrayList<String>(Arrays.asList(item.getOppUnitId(), item.getMdOrg()));
                zbCodeList.forEach(zbCode -> values.add((String)item.getFields().get(zbCode)));
                ArrayKey arrayKey = new ArrayKey(values);
                unitAndOppUnit2EoMap.put(arrayKey, (InputDataEO)((Object)item));
                allKeys.add(arrayKey);
            });
        }
        return unitAndOppUnit2EoMap;
    }

    private List<InputDataEO> getInputDataEOS(String formCode, String formKey, ReportFmlExecEnvironment env, DimensionValueSet currentMasterKey, DataRegionDefine regionDefine, String unitCode, String filter) {
        try {
            FormSchemeDefine formSchemeDefine = env.getFormSchemeDefine();
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(formSchemeDefine.getKey());
            queryEnvironment.setRegionKey(regionDefine.getKey());
            IDataQuery dataQuery = this.iDataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.setFilterDataByAuthority(false);
            dataQuery.setDefaultGroupName(formCode);
            dataQuery.setMasterKeys(new DimensionValueSet(currentMasterKey));
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(env.getTaskDefine().getKey());
            jtableContext.setFormSchemeKey(formSchemeDefine.getKey());
            jtableContext.setFormKey(formKey);
            jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)currentMasterKey));
            FloatRegionRelationEvn regionRelationEvn = new FloatRegionRelationEvn(this.jtableParamService.getRegion(regionDefine.getKey()), jtableContext);
            if (!StringUtils.isEmpty(unitCode)) {
                String condition = regionRelationEvn.getRegionData().getFilterCondition();
                if (StringUtils.isEmpty(condition)) {
                    condition = "";
                }
                regionRelationEvn.getRegionData().setFilterCondition(condition + " and (" + "OPPUNITID" + "='" + unitCode + "' )");
            }
            if (!StringUtils.isEmpty(filter)) {
                regionRelationEvn.getRegionData().setFilterCondition(filter.concat(" and ").concat(regionRelationEvn.getRegionData().getFilterCondition()));
            }
            regionRelationEvn.getRegionData().setFilterCondition("OFFSETSTATE = '0'".concat(" and ").concat(regionRelationEvn.getRegionData().getFilterCondition()));
            RegionQueryInfo regionQueryInfo = this.initRegionQueryInfo(this.jtableParamService, regionDefine, jtableContext, currentMasterKey);
            FloatRegionQueryTabeStrategy queryTabeStrategy = new FloatRegionQueryTabeStrategy(dataQuery, (AbstractRegionRelationEvn)regionRelationEvn, regionQueryInfo);
            DataFormaterCache dataFormater = new DataFormaterCache(jtableContext);
            FloatRegionDataSetStrategy dataSetStrategy = new FloatRegionDataSetStrategy((AbstractRegionRelationEvn)regionRelationEvn, (AbstractRegionQueryTableStrategy)queryTabeStrategy, dataFormater, regionQueryInfo);
            RegionDataSet regionDataSet = dataSetStrategy.getRegionDataSet();
            ArrayList<String> inputDataIds = new ArrayList<String>(10);
            List bizKeyOrderFields = regionRelationEvn.getBizKeyOrderFields();
            for (int index = 0; index < ((List)bizKeyOrderFields.get(0)).size(); ++index) {
                if (!((FieldData)((List)bizKeyOrderFields.get(0)).get(index)).getFieldCode().equals("BIZKEYORDER")) continue;
                for (List inputData : regionDataSet.getData()) {
                    String dataId = (String)inputData.get(0);
                    String[] bizKeys = dataId.split("\\#\\^\\$");
                    inputDataIds.add(bizKeys[index]);
                }
                break;
            }
            return this.inputDataService.queryByIds(inputDataIds, jtableContext.getTaskKey());
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5b58\u8d27\u53f0\u8d26\u6570\u636e\u5931\u8d25\uff1a", e);
            return null;
        }
    }

    private RegionQueryInfo initRegionQueryInfo(IJtableParamService jtableParamService, DataRegionDefine regionDefine, JtableContext jtableContext, DimensionValueSet dimensionValueSet) {
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(jtableContext);
        List regions = jtableParamService.getRegions(jtableContext.getFormKey());
        if (!CollectionUtils.isEmpty(regions) && (regionQueryInfo.getFilterInfo() == null || StringUtils.isEmpty(regionQueryInfo.getFilterInfo().getFilterFormula()))) {
            for (RegionData region : regions) {
                String[] gatherFields;
                List regionTabs = jtableParamService.getRegionTabs(region.getKey());
                if (null != regionTabs) {
                    RegionTabFilter regionTabSettingFilter = new RegionTabFilter(jtableContext, dimensionValueSet);
                    for (RegionTab regionTab : regionTabs) {
                        if (!regionTabSettingFilter.accept(regionTab)) continue;
                        RegionFilterInfo regionFilterInfo = regionQueryInfo.getFilterInfo() == null ? new RegionFilterInfo() : regionQueryInfo.getFilterInfo();
                        regionQueryInfo.setFilterInfo(regionFilterInfo);
                        regionFilterInfo.setFilterFormula(Arrays.asList(regionTab.getFilter()));
                        regionFilterInfo.setCellQuerys(region.getCells());
                        break;
                    }
                }
                if (region.getGrade() != null || StringUtils.isEmpty(regionDefine.getGatherFields()) && !regionDefine.getShowGatherSummaryRow()) continue;
                RegionGradeInfo grade = new RegionGradeInfo();
                region.setGrade(grade);
                if (StringUtils.isEmpty(regionDefine.getGatherFields())) continue;
                for (String gatherField : gatherFields = regionDefine.getGatherFields().split(";")) {
                    if (StringUtils.isEmpty(gatherField) || gatherField.equals("null")) continue;
                    GradeCellInfo gradeCellInfo = new GradeCellInfo();
                    gradeCellInfo.setZbid(gatherField);
                    gradeCellInfo.setGradeStruct("");
                    gradeCellInfo.setTrim(false);
                    grade.getGradeCells().add(gradeCellInfo);
                }
            }
        }
        return regionQueryInfo;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u590d\u5236\u5b58\u8d27\u6570\u636e").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5c06\u91c7\u8d2d\u65b9A\u6570\u636e\u63a8\u9001\u5230\u9500\u552e\u65b9B\u4e2d").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("PushInventory(\" formA:formB\",\"SUBJECTCODE\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

