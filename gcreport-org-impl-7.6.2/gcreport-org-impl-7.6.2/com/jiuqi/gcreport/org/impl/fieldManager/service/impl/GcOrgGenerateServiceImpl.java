/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.org.api.enums.GenerateWay
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.FrontEndParams
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.gcreport.org.impl.fieldManager.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.org.api.enums.GenerateWay;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.FrontEndParams;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.fieldManager.common.formula.GcOrgFormulaFilter;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcOrgGenerateService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class GcOrgGenerateServiceImpl
implements GcOrgGenerateService {
    private Logger logger = LoggerFactory.getLogger(GcOrgGenerateServiceImpl.class);
    @Autowired
    private DataModelClient dataModelClient;

    @Override
    public void generateOrgTree(GcOrgTypeCopyApiParam param) {
        ArrayList<String> resultList = new ArrayList<String>();
        FrontEndParams params = new FrontEndParams(param.getSn(), 0, new ProgressDataImpl(param.getSn()));
        params.getProgressData().setResult(resultList);
        String orgType = param.getOrgType();
        String orgVerCode = param.getOrgVerCode();
        GenerateContext context = new GenerateContext(param);
        context.setFrontEndParams(params);
        List<OrgToJsonVO> existList = context.getTargetInstance().getOrgTree("-");
        if (!CollectionUtils.isEmpty(existList)) {
            throw new BusinessRuntimeException("\u76ee\u6807\u5355\u4f4d\u7c7b\u578b\u5df2\u5b58\u5728\u6570\u636e\uff0c\u65e0\u6cd5\u751f\u6210");
        }
        List<OrgToJsonVO> sourceTree = context.getSourceInstance().getOrgTree(null);
        Assert.notEmpty(sourceTree, "\u53c2\u7167\u673a\u6784\u6570\u636e\u4e3a\u7a7a");
        resultList.add("\u5f00\u59cb\u6267\u884c");
        resultList.add("\u83b7\u53d6\u6e90\u7ec4\u7ec7\u673a\u6784");
        params.getProgressData().addProgressValueAndRefresh(10.0);
        ArrayList<OrgToJsonVO> filterList = new ArrayList<OrgToJsonVO>();
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transform(null, (String)orgVerCode);
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByDate(orgType, yearPeriodDO.getBeginDate());
        resultList.add("\u5f00\u59cb\u6309\u516c\u5f0f\u8fc7\u6ee4");
        params.getProgressData().addProgressValueAndRefresh(5.0);
        this.filterTree(sourceTree, filterList, context, version, null);
        resultList.add("\u751f\u6210\u65b0\u65b0\u7ec4\u7ec7\u673a\u6784\u6811\u5b8c\u6bd5");
        params.getProgressData().addProgressValueAndRefresh(15.0);
        List<OrgToJsonVO> orgTree = this.collectionToJsonTree(filterList, context.getSourceInstance().listOrg());
        ArrayList<OrgToJsonVO> allItem = new ArrayList<OrgToJsonVO>();
        this.treeToList(allItem, orgTree);
        params.setTotalCount(allItem.size());
        try {
            resultList.add("\u5f00\u59cb\u751f\u6210\u5230\u76ee\u6807\u5355\u4f4d\u7c7b\u578b");
            context.setProcessStartTime(System.currentTimeMillis());
            params.getProgressData().addProgressValueAndRefresh(1.0);
            double progressValue = params.getProgressData().getProgressValue();
            double percentValue = (100.0 - progressValue - 10.0) / (double)allItem.size();
            this.importOrgTree(context, orgTree, percentValue);
            if (!CollectionUtils.isEmpty(context.getSecondOrgList())) {
                resultList.add("\u4fee\u590d\u90e8\u5206\u7ec4\u7ec7\u673a\u6784");
                params.getProgressData().addProgressValueAndRefresh(1.0);
                context.getSecondOrgList().forEach(orgToJsonVO -> {
                    orgToJsonVO.setFieldValue("categoryname", (Object)context.getTargetType());
                    orgToJsonVO.setFieldValue("NO_CACHE_EVENT", (Object)true);
                    orgToJsonVO.setFieldValue("IGNORE_CALC_ORGTYPE", (Object)true);
                    if (System.currentTimeMillis() - context.getProcessStartTime() > 300000L) {
                        orgToJsonVO.setFieldValue("NO_CACHE_EVENT", (Object)false);
                        context.setProcessStartTime(System.currentTimeMillis());
                    }
                    Object baseUnitId = orgToJsonVO.getFieldValue("BASEUNITID_temp");
                    Object diffUnitId = orgToJsonVO.getFieldValue("DIFFUNITID_temp");
                    if (baseUnitId != null && StringUtils.hasText((String)baseUnitId)) {
                        orgToJsonVO.setFieldValue("BASEUNITID", baseUnitId);
                    }
                    if (diffUnitId != null && StringUtils.hasText((String)diffUnitId)) {
                        orgToJsonVO.setFieldValue("DIFFUNITID", diffUnitId);
                    }
                    orgToJsonVO.getDatas().remove("BBLX");
                    OrgToJsonVO updateOrgToJsonVO = new OrgToJsonVO();
                    updateOrgToJsonVO.setDatas(orgToJsonVO.getDatas());
                    updateOrgToJsonVO.setFieldValue("VER", null);
                    context.getTargetInstance().updateWithAnyPreProcess(updateOrgToJsonVO);
                });
            }
            resultList.add("\u751f\u6210\u5b8c\u6bd5");
            params.getProgressData().setProgressValueAndRefresh(100.0);
            params.getProgressData().setSuccessFlagAndRefresh(true);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u751f\u6210\u7ec4\u7ec7\u673a\u6784-\u6765\u6e90" + orgType + "-\u76ee\u6807" + context.getTargetType()), (String)JsonUtils.writeValueAsString((Object)param));
        }
        catch (Exception e) {
            resultList.add("\u51fa\u73b0\u5f02\u5e38\uff0c\u4efb\u52a1\u7ed3\u675f:" + e.getMessage());
            this.logger.error("\u51fa\u73b0\u5f02\u5e38\uff0c\u4efb\u52a1\u7ed3\u675f:" + e.getMessage(), e);
            LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u751f\u6210\u7ec4\u7ec7\u673a\u6784-\u6765\u6e90" + orgType + "-\u76ee\u6807" + context.getTargetType()), (String)JsonUtils.writeValueAsString((Object)param));
            params.getProgressData().setSuccessFlagAndRefresh(false);
        }
        InspectOrgUtils.clearOrgCache(param.getOrgTypeVo().getName());
    }

    @Override
    public List<OrgToJsonVO> generateOrgTreePreview(GcOrgTypeCopyApiParam param) {
        String orgType = param.getOrgType();
        String orgVerCode = param.getOrgVerCode();
        GenerateContext context = new GenerateContext(param);
        List<OrgToJsonVO> sourceOrgTree = context.getSourceInstance().getOrgTree(null);
        ArrayList<OrgToJsonVO> filterList = new ArrayList<OrgToJsonVO>();
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transform(null, (String)orgVerCode);
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByDate(orgType, yearPeriodDO.getBeginDate());
        this.filterTree(sourceOrgTree, filterList, context, version, null);
        return this.collectionToJsonTree(filterList, context.getSourceInstance().listOrg());
    }

    private void filterTree(List<OrgToJsonVO> tree, List<OrgToJsonVO> resultList, GenerateContext context, OrgVersionVO version, String diffUnitId) {
        tree.forEach(orgToJsonVO -> {
            if (CollectionUtils.isEmpty((Collection)orgToJsonVO.getChildren())) {
                if (orgToJsonVO.getCode().equals(diffUnitId)) {
                    return;
                }
                if (this.checkData(context.getSourceType(), version, context.getFilterFormula(), (OrgToJsonVO)orgToJsonVO)) {
                    resultList.add((OrgToJsonVO)orgToJsonVO);
                }
            } else {
                this.filterTree(orgToJsonVO.getChildren(), resultList, context, version, orgToJsonVO.getDiffUnitId());
            }
        });
    }

    private void importOrgTree(GenerateContext context, List<OrgToJsonVO> orgTree, double percentValue) {
        orgTree.forEach(orgToJsonVO -> {
            int totalCount = context.getFrontEndParams().getTotalCount();
            int doneCount = context.getFrontEndParams().getDoneCount();
            orgToJsonVO.setFieldValue("categoryname", (Object)context.getTargetType());
            orgToJsonVO.setFieldValue("NO_CACHE_EVENT", (Object)true);
            orgToJsonVO.setFieldValue("IGNORE_CALC_ORGTYPE", (Object)true);
            if (System.currentTimeMillis() - context.getProcessStartTime() > 300000L) {
                orgToJsonVO.setFieldValue("NO_CACHE_EVENT", (Object)false);
                context.setProcessStartTime(System.currentTimeMillis());
            }
            if (CollectionUtils.isEmpty((Collection)orgToJsonVO.getChildren())) {
                orgToJsonVO.setFieldValue("ORGTYPEID", (Object)this.getOrgTypeByGenerateWay(context, (OrgToJsonVO)orgToJsonVO));
                orgToJsonVO.setFieldValue("BBLX", (Object)"0");
                if (context.getOrgTypeMap().containsKey(orgToJsonVO.getCode())) {
                    orgToJsonVO.setFieldValue("BBLX", (Object)"1");
                }
                try {
                    context.getTargetInstance().addWithoutAnyPreProcess((OrgToJsonVO)orgToJsonVO);
                }
                catch (Exception e) {
                    this.logger.error("\u751f\u6210\u5355\u4f4d\uff1a" + orgToJsonVO.getCode() + " \u5931\u8d25", e);
                    throw new RuntimeException("\u751f\u6210\u5355\u4f4d\uff1a" + orgToJsonVO.getCode() + " \u5931\u8d25");
                }
                context.getResultList().clear();
                context.getResultList().add("(\u8fdb\u5ea6: " + doneCount + " / \u5171 " + totalCount + " \u6761\u8bb0\u5f55 ) \u6b63\u5728\u5904\u7406\uff1a" + orgToJsonVO.getTitle());
                context.getFrontEndParams().addDoneCount(1);
                context.getFrontEndParams().getProgressData().addProgressValueAndRefresh(percentValue);
            } else {
                if (StringUtils.hasText(orgToJsonVO.getDiffUnitId())) {
                    context.getOrgTypeMap().put(orgToJsonVO.getDiffUnitId(), context.getTargetType());
                }
                orgToJsonVO.setFieldValue("BBLX", (Object)"9");
                orgToJsonVO.setFieldValue("ORGTYPEID", (Object)this.getOrgTypeByGenerateWay(context, (OrgToJsonVO)orgToJsonVO));
                boolean needSecondUpdate = false;
                if (StringUtils.hasText(orgToJsonVO.getDiffUnitId())) {
                    needSecondUpdate = true;
                    orgToJsonVO.setFieldValue("DIFFUNITID_temp", (Object)orgToJsonVO.getDiffUnitId());
                    orgToJsonVO.setFieldValue("DIFFUNITID", (Object)"");
                }
                if (StringUtils.hasText(orgToJsonVO.getBaseUnitId())) {
                    needSecondUpdate = true;
                    orgToJsonVO.setFieldValue("BASEUNITID_temp", (Object)orgToJsonVO.getBaseUnitId());
                    orgToJsonVO.setFieldValue("BASEUNITID", (Object)"");
                }
                if (needSecondUpdate) {
                    context.getSecondOrgList().add((OrgToJsonVO)orgToJsonVO);
                }
                OrgToJsonVO addOrgToJsonVO = new OrgToJsonVO();
                addOrgToJsonVO.setDatas(orgToJsonVO.getDatas());
                context.getResultList().add("(\u8fdb\u5ea6: " + doneCount + " / \u5171 " + totalCount + " \u6761\u8bb0\u5f55 ) \u6b63\u5728\u5904\u7406\uff1a" + addOrgToJsonVO.getTitle());
                context.getFrontEndParams().getProgressData().addProgressValueAndRefresh(percentValue);
                orgToJsonVO.setFieldValue("NO_CACHE_EVENT", (Object)false);
                context.getTargetInstance().addWithoutAnyPreProcess(addOrgToJsonVO);
                context.getFrontEndParams().addDoneCount(1);
                this.importOrgTree(context, orgToJsonVO.getChildren(), percentValue);
            }
        });
    }

    private boolean checkData(String orgType, OrgVersionVO version, String filterFormula, OrgToJsonVO orgToJsonVO) {
        if (!StringUtils.hasText(filterFormula)) {
            return true;
        }
        OrgDO orgDO = new OrgDO();
        orgToJsonVO.getDatas().keySet().forEach(s -> orgDO.put(s.toLowerCase(), orgToJsonVO.getFieldValue(s)));
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgType);
        param.setVersionDate(version.getValidTime());
        param.setStopflag(Integer.valueOf(-1));
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setExpression(filterFormula);
        boolean flag = false;
        try {
            flag = ((GcOrgFormulaFilter)SpringContextUtils.getBean(GcOrgFormulaFilter.class)).checkData(param, orgDO);
        }
        catch (Exception e) {
            this.logger.error("\u516c\u5f0f\u8fc7\u6ee4\u5f02\u5e38", e);
        }
        return flag;
    }

    protected List<OrgToJsonVO> collectionToJsonTree(Collection<OrgToJsonVO> list, List<OrgToJsonVO> sourceList) {
        ArrayList tree = CollectionUtils.newArrayList();
        ArrayList parentList = CollectionUtils.newArrayList();
        Map sourceOrgMapper = sourceList.stream().collect(Collectors.toMap(OrgToJsonVO::getId, Function.identity(), (o1, o2) -> o1));
        list.forEach(org -> this.addParent(null, parentList, (OrgToJsonVO)org, sourceOrgMapper));
        ArrayList<OrgToJsonVO> allList = new ArrayList<OrgToJsonVO>(list);
        allList.addAll(parentList);
        Map datas = allList.stream().collect(Collectors.toMap(OrgToJsonVO::getId, Function.identity(), (o1, o2) -> o1));
        allList.stream().forEach(org -> {
            OrgToJsonVO pobj = (OrgToJsonVO)datas.get(org.getParentid());
            if (pobj != null) {
                pobj.getChildren().add(datas.get(org.getId()));
                pobj.setLeaf(true);
            } else {
                org.setLeaf(true);
                tree.add(datas.get(org.getId()));
            }
        });
        return tree;
    }

    private void addParent(Map<String, OrgToJsonVO> datas, List<OrgToJsonVO> parentList, OrgToJsonVO obj, Map<String, OrgToJsonVO> sourceOrgMapper) {
        OrgToJsonVO pobj = sourceOrgMapper.get(obj.getParentid());
        if (pobj != null) {
            if (!parentList.contains(pobj)) {
                parentList.add(pobj);
                if (StringUtils.hasText(pobj.getDiffUnitId())) {
                    parentList.add(sourceOrgMapper.get(pobj.getDiffUnitId()));
                }
            }
            this.addParent(datas, parentList, pobj, sourceOrgMapper);
        }
    }

    private void treeToList(List<OrgToJsonVO> list, Collection<OrgToJsonVO> tree) {
        tree.stream().forEach(v -> {
            list.add((OrgToJsonVO)v);
            if (v.getChildren() != null && !v.getChildren().isEmpty()) {
                this.treeToList(list, v.getChildren());
            }
        });
    }

    private String getOrgTypeByGenerateWay(GenerateContext context, OrgToJsonVO target) {
        GenerateWay way = context.param.getGenerateWay();
        if (way.equals((Object)GenerateWay.SIMPLE_COPY)) {
            return String.valueOf(target.getFieldValue("ORGTYPEID"));
        }
        if (!CollectionUtils.isEmpty((Collection)target.getChildren())) {
            return context.getTargetType();
        }
        if (context.getOrgTypeMap().containsKey(target.getCode())) {
            return context.getOrgTypeMap().get(target.getCode());
        }
        return "MD_ORG_CORPORATE";
    }

    public class GenerateContext {
        private GcOrgTypeCopyApiParam param;
        private GcOrgMangerCenterTool sourceCenterTool;
        private GcOrgMangerCenterTool targetCenterTool;
        private List<OrgToJsonVO> sourceOrgList = new ArrayList<OrgToJsonVO>();
        private List<OrgToJsonVO> filterOrgList = new ArrayList<OrgToJsonVO>();
        private Map<String, OrgToJsonVO> filterOrgMap = new ConcurrentHashMap<String, OrgToJsonVO>();
        private Map<String, String> orgTypeMap = new ConcurrentHashMap<String, String>();
        private List<OrgToJsonVO> secondOrgList = new ArrayList<OrgToJsonVO>();
        private FrontEndParams frontEndParams;
        private long processStartTime = 0L;

        public long getProcessStartTime() {
            return this.processStartTime;
        }

        public void setProcessStartTime(long processStartTime) {
            this.processStartTime = processStartTime;
        }

        public GenerateContext(GcOrgTypeCopyApiParam param) {
            this.param = param;
        }

        GcOrgMangerCenterTool getInstance(String orgType, String orgVerCode) {
            return GcOrgMangerCenterTool.getInstance(orgType, orgVerCode);
        }

        GcOrgMangerCenterTool getSourceInstance() {
            if (this.sourceCenterTool != null) {
                return this.sourceCenterTool;
            }
            this.sourceCenterTool = GcOrgMangerCenterTool.getInstance(this.getSourceType(), this.getSourceVersionCode());
            return this.sourceCenterTool;
        }

        GcOrgMangerCenterTool getTargetInstance() {
            if (this.targetCenterTool != null) {
                return this.targetCenterTool;
            }
            this.targetCenterTool = GcOrgMangerCenterTool.getInstance(this.getTargetType(), this.getTargetVersionCode());
            return this.targetCenterTool;
        }

        public String getFilterFormula() {
            return this.param.getFilterFormula();
        }

        public String getSourceType() {
            return this.param.getOrgType();
        }

        public String getSourceVersionCode() {
            return this.param.getOrgVerCode();
        }

        public String getTargetType() {
            return this.param.getOrgTypeVo().getName();
        }

        public String getTargetVersionCode() {
            return this.param.getOrgVersion().getName();
        }

        public List<OrgToJsonVO> getSourceOrgList() {
            return this.sourceOrgList;
        }

        public void setSourceOrgList(List<OrgToJsonVO> sourceOrgList) {
            this.sourceOrgList = sourceOrgList;
        }

        public List<OrgToJsonVO> getSecondOrgList() {
            return this.secondOrgList;
        }

        public void setSecondOrgList(List<OrgToJsonVO> secondOrgList) {
            this.secondOrgList = secondOrgList;
        }

        public List<OrgToJsonVO> getFilterOrgList() {
            return this.filterOrgList;
        }

        public void setFilterOrgList(List<OrgToJsonVO> filterOrgList) {
            this.filterOrgList = filterOrgList;
            this.filterOrgMap = filterOrgList.stream().collect(Collectors.toMap(OrgToJsonVO::getId, Function.identity(), (o1, o2) -> o1));
        }

        public Map<String, OrgToJsonVO> getFilterOrgMap() {
            return this.filterOrgMap;
        }

        public void setFilterOrgMap(Map<String, OrgToJsonVO> filterOrgMap) {
            this.filterOrgMap = filterOrgMap;
        }

        public Map<String, String> getOrgTypeMap() {
            return this.orgTypeMap;
        }

        public void setOrgTypeMap(Map<String, String> orgTypeMap) {
            this.orgTypeMap = orgTypeMap;
        }

        public FrontEndParams getFrontEndParams() {
            return this.frontEndParams;
        }

        public void setFrontEndParams(FrontEndParams frontEndParams) {
            this.frontEndParams = frontEndParams;
        }

        public List<String> getResultList() {
            return (List)this.frontEndParams.getProgressData().getResult();
        }
    }
}

