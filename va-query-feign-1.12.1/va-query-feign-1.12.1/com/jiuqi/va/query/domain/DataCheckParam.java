/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.domain;

import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import java.util.List;

public class DataCheckParam {
    private List<TemplateInfoVO> templateInfoVOS;
    private List<DataSourceInfoVO> dataSourceInfoVOS;

    public DataCheckParam(List<TemplateInfoVO> templateInfoVOS, List<DataSourceInfoVO> dataSourceInfoVOS) {
        this.templateInfoVOS = templateInfoVOS;
        this.dataSourceInfoVOS = dataSourceInfoVOS;
    }

    public DataCheckParam() {
    }

    public List<TemplateInfoVO> getTemplateInfoVOS() {
        return this.templateInfoVOS;
    }

    public void setTemplateInfoVOS(List<TemplateInfoVO> templateInfoVOS) {
        this.templateInfoVOS = templateInfoVOS;
    }

    public List<DataSourceInfoVO> getDataSourceInfoVOS() {
        return this.dataSourceInfoVOS;
    }

    public void setDataSourceInfoVOS(List<DataSourceInfoVO> dataSourceInfoVOS) {
        this.dataSourceInfoVOS = dataSourceInfoVOS;
    }
}

