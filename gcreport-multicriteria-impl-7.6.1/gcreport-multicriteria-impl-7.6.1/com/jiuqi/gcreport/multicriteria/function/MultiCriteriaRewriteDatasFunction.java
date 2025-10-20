/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.multicriteria.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.enums.MulCriShowTypeEnum;
import com.jiuqi.gcreport.multicriteria.service.GcMultiCriteriaService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MultiCriteriaRewriteDatasFunction
extends AdvanceFunction
implements INrFunction {
    private static Logger logger = LoggerFactory.getLogger(MultiCriteriaRewriteDatasFunction.class);
    private transient IRunTimeViewController runTimeViewController;
    private transient GcMultiCriteriaService multiCriteriaService;

    public MultiCriteriaRewriteDatasFunction() {
        this.parameters().add(new Parameter("formCodes", 6, "\u62a5\u8868\u4ee3\u7801", true));
        this.runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        this.multiCriteriaService = (GcMultiCriteriaService)SpringContextUtils.getBean(GcMultiCriteriaService.class);
    }

    public String name() {
        return "ZZZH";
    }

    public String title() {
        return "\u51c6\u5219\u8f6c\u6362\u56de\u5199\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public String evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        return this.rewriteMultiCriteriaDatas((QueryContext)iContext, parameters);
    }

    private String rewriteMultiCriteriaDatas(QueryContext iContext, List<IASTNode> parameters) throws SyntaxException {
        List<String> formKeys;
        GcMultiCriteriaConditionVO condition = this.getMultiCriteriaCondition(iContext);
        Set<String> afterFormKeys = this.filterFormKeysByFormState(condition, formKeys = this.listFormKeys(parameters, condition));
        if (CollectionUtils.isEmpty(afterFormKeys)) {
            return "";
        }
        condition.setAfterFormKeys(afterFormKeys);
        List<Object> zbDataList = this.multiCriteriaService.queryZbData(condition);
        zbDataList = zbDataList.stream().filter(data -> !Integer.valueOf(1).equals(data.getHasFormula())).collect(Collectors.toList());
        condition.setMultiCriteriaZbDatas(zbDataList);
        this.multiCriteriaService.saveZbData(condition);
        return "";
    }

    private Set<String> filterFormKeysByFormState(GcMultiCriteriaConditionVO condition, List<String> formKeys) {
        HashSet<String> afterFormKeys = new HashSet<String>();
        DimensionParamsVO dimensionParamsVO = this.multiCriteriaService.getDimensionParamsVO(condition);
        List writeAccessDescs = FormUploadStateTool.getInstance().writeable(dimensionParamsVO, formKeys);
        for (int i = 0; i < formKeys.size(); ++i) {
            ReadWriteAccessDesc accessDesc = (ReadWriteAccessDesc)writeAccessDescs.get(i);
            if (!accessDesc.getAble().booleanValue()) continue;
            afterFormKeys.add(formKeys.get(i));
        }
        return afterFormKeys;
    }

    private List<String> listFormKeys(List<IASTNode> parameters, GcMultiCriteriaConditionVO condition) throws SyntaxException {
        String formCodes = CollectionUtils.isEmpty(parameters) ? "" : (String)parameters.get(0).evaluate(null);
        ArrayList<String> formKeys = new ArrayList();
        if (StringUtils.isEmpty((String)formCodes)) {
            formKeys = this.multiCriteriaService.queryMulCriAfterForms(condition.getTaskId(), condition.getSchemeId());
        } else {
            try {
                String[] formCodeArr = formCodes.split(";");
                for (int i = 0; i < formCodeArr.length; ++i) {
                    FormDefine formDefine = this.runTimeViewController.queryFormByCodeInScheme(condition.getSchemeId(), formCodeArr[i]);
                    if (formDefine == null) continue;
                    formKeys.add(formDefine.getKey());
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return formKeys;
    }

    private GcMultiCriteriaConditionVO getMultiCriteriaCondition(QueryContext iContext) {
        GcMultiCriteriaConditionVO condition = new GcMultiCriteriaConditionVO();
        DimensionValueSet ds = iContext.getMasterKeys();
        String orgTypeCode = (String)ds.getValue("MD_GCORGTYPE");
        String dataTime = (String)ds.getValue("DATATIME");
        String orgCode = (String)ds.getValue("MD_ORG");
        String currencyCode = (String)ds.getValue("MD_CURRENCY");
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)iContext.getExeContext().getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        condition.setTaskId(formScheme.getTaskKey());
        condition.setSchemeId(formSchemeKey);
        condition.setPeriodStr(dataTime);
        condition.setOrgType(orgTypeCode);
        condition.setOrgId(orgCode);
        condition.setCurrency(currencyCode);
        condition.setShowType(MulCriShowTypeEnum.ALL_TYPES.getCode());
        return condition;
    }
}

