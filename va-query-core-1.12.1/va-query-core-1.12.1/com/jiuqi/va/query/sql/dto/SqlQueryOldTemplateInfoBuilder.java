/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 */
package com.jiuqi.va.query.sql.dto;

import com.jiuqi.va.query.sql.dto.SqlQueryOldTemplateInfo;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class SqlQueryOldTemplateInfoBuilder {
    private final SqlQueryOldTemplateInfo info = new SqlQueryOldTemplateInfo();

    public SqlQueryOldTemplateInfoBuilder params(List<TemplateParamsVO> params) {
        if (!CollectionUtils.isEmpty(params)) {
            for (TemplateParamsVO param : params) {
                this.info.append(SqlQueryOldTemplateInfo.PluginEnum.PARAMS, param.getName(), param.getTitle());
            }
        }
        return this;
    }

    public SqlQueryOldTemplateInfoBuilder fields(List<TemplateFieldSettingVO> fields) {
        if (!CollectionUtils.isEmpty(fields)) {
            for (TemplateFieldSettingVO field : fields) {
                this.info.append(SqlQueryOldTemplateInfo.PluginEnum.FIELDS, field.getName(), field.getTitle());
            }
        }
        return this;
    }

    public SqlQueryOldTemplateInfo build() {
        return this.info;
    }
}

