/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  com.jiuqi.va.query.template.web.QueryTemplateContentClient
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.va.query.template.service.join;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.template.web.QueryTemplateContentClient;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Primary
public class QueryTemplateContentClientImpl
implements QueryTemplateContentClient {
    @Autowired
    private TemplateContentService templateContentService;
    @Autowired
    private TemplateDesignService templateDesignService;

    public BusinessResponseEntity<List<TableHeaderVO>> getTableHeader(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok(this.templateContentService.getTableHeader(templateId));
    }

    public BusinessResponseEntity<TemplateContentVO> getTemplateContent(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.getTemplateContent(templateId));
    }

    public BusinessResponseEntity<QueryTemplate> getTemplate(String templateId) {
        return BusinessResponseEntity.ok((Object)this.templateDesignService.getTemplate(templateId));
    }

    public BusinessResponseEntity<TemplateContentVO> getTemplateContentByCode(@PathVariable(value="code") String code) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.getTemplateContentByCode(code));
    }

    public BusinessResponseEntity<QueryTemplate> getQueryTemplateByCode(String code) {
        return BusinessResponseEntity.ok((Object)this.templateDesignService.getTemplateByCode(code));
    }

    public BusinessResponseEntity<QueryTemplate> getBizTemplate(String code) {
        QueryTemplate templateByCode = this.templateDesignService.getTemplateByCode(code);
        return BusinessResponseEntity.ok((Object)this.templateDesignService.getBizTemplate(templateByCode.getId()));
    }

    public BusinessResponseEntity<List<TemplateParamsVO>> getTemplateParams(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok(this.templateContentService.getTemplateParams(templateId));
    }

    public BusinessResponseEntity<List<TemplateRelateQueryVO>> getTemplateRelateQuery(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok(this.templateContentService.getTemplateRelateQuery(templateId));
    }
}

