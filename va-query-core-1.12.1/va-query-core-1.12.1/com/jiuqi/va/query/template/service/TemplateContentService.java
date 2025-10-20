/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.template.vo.ConfigureExportVO
 *  com.jiuqi.va.query.template.vo.QueryConfigureImportVO
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.query.template.service;

import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.template.vo.ConfigureExportVO;
import com.jiuqi.va.query.template.vo.QueryConfigureImportVO;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TemplateContentService {
    @Deprecated
    public String templateContentSave(TemplateContentVO var1);

    @Deprecated
    public void templateContentUpdate(TemplateContentVO var1);

    public List<TableHeaderVO> getTableHeader(String var1);

    public List<TableHeaderVO> getPreviewTableHeader(List<TemplateFieldSettingVO> var1);

    @Deprecated
    public TemplateContentVO getTemplateContent(String var1);

    public List<TemplateParamsVO> getTemplateParams(String var1);

    public ConfigureExportVO configureExport(List<String> var1);

    public ConfigureExportVO configureImportPreview(MultipartFile var1);

    public QueryConfigureImportVO configureImport(ConfigureExportVO var1);

    @Deprecated
    public TemplateContentVO getTemplateContentByCode(String var1);

    public List<FetchQueryFiledVO> getSimpleTemplateParams(String var1);

    public List<FetchQueryFiledVO> getSimpleTemplateFields(String var1);

    public List<TemplateRelateQueryVO> getTemplateRelateQuery(String var1);

    public String templateCopy(String var1, String var2, TemplateInfoVO var3);

    public String templateMove(String var1, String var2);

    public List<TemplateInfoVO> getTemplatesByGroupId(String var1);

    public TemplateInfoVO getTemplatesByCode(String var1);
}

