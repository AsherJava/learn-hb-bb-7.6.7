/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.bi.dataset.report.provider;

import com.jiuqi.bi.dataset.report.bean.SelectFieldParam;
import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelDataBuilder;
import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelFieldParam;
import com.jiuqi.bi.dataset.report.exception.ExpParseError;
import com.jiuqi.bi.dataset.report.exception.ReportDsModelDataBuildException;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.remote.controller.vo.SelectFieldVo;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SelectFieldParseProvider {
    private final Logger logger = LoggerFactory.getLogger(SelectFieldParseProvider.class);
    @Autowired
    private IReportDsModelDataBuilder reportDsModelDataBuilder;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public List<ReportExpField> parse(List<SelectFieldVo> selectFields, List<ReportDsParameter> params) throws JQException {
        ArrayList<ReportExpField> reportExpFields = new ArrayList<ReportExpField>();
        if (!CollectionUtils.isEmpty(selectFields)) {
            ArrayList<IReportDsModelFieldParam> fieldParams = new ArrayList<IReportDsModelFieldParam>();
            for (SelectFieldVo selectFieldVo : selectFields) {
                FormDefine formDefine = this.runTimeViewController.queryFormById(selectFieldVo.getFormkey());
                fieldParams.add(new SelectFieldParam(selectFieldVo, formDefine));
            }
            try {
                reportExpFields.addAll(this.reportDsModelDataBuilder.buildExpFields(params, fieldParams));
            }
            catch (ReportDsModelDataBuildException e) {
                this.logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)new ExpParseError(e.getMessage()));
            }
        }
        return reportExpFields;
    }
}

