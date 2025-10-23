/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeReportController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.facade.ReportTemplateDefine
 *  com.jiuqi.nr.task.api.common.Constants$QueryType
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 */
package com.jiuqi.nr.report.internal;

import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ReportResourceDataProvider
implements IResourceDataProvider {
    private IDesignTimeReportController reportDesignTimeController;
    private IDesignTimeViewController designTimeViewController;

    public ReportResourceDataProvider(IDesignTimeReportController reportDesignTimeController, IDesignTimeViewController designTimeViewController) {
        this.reportDesignTimeController = reportDesignTimeController;
        this.designTimeViewController = designTimeViewController;
    }

    public List<ResourceSearchResultDO> search(SearchParam searchParam) {
        List reportTemplateByScheme = this.reportDesignTimeController.listReportTemplateByFormScheme(searchParam.getFormSchemeKey());
        String keyWords = searchParam.getKeyWords();
        List filterDefine = reportTemplateByScheme.stream().filter(a -> a.getFileName().toLowerCase(Locale.ROOT).contains(keyWords.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        ArrayList<ResourceSearchResultDO> result = new ArrayList<ResourceSearchResultDO>();
        for (DesignReportTemplateDefine designReportTemplateDefine : filterDefine) {
            ResourceSearchResultDO resultDO = new ResourceSearchResultDO("REPORT_DEFINE");
            resultDO.setKey(designReportTemplateDefine.getKey());
            resultDO.setTitle(designReportTemplateDefine.getFileName());
            resultDO.setPath(designReportTemplateDefine.getFileName() + "/" + "\u62a5\u544a\u6a21\u677f");
            result.add(resultDO);
        }
        return result;
    }

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        ArrayList<ResourceCategoryDO> dos = new ArrayList<ResourceCategoryDO>();
        if (resourceCategoryDTO.getQueryType() == Constants.QueryType.ROOT) {
            ResourceCategoryDO categoryDO = new ResourceCategoryDO("REPORT_DEFINE");
            categoryDO.setKey("REPORT_DEFINE");
            categoryDO.setTitle("\u62a5\u544a\u6a21\u677f");
            categoryDO.setIcon("#icon-J_GJ_A_NR_baogaomobanleixing");
            categoryDO.setParent("-");
            dos.add(categoryDO);
        }
        return dos;
    }

    public ResourceDO getResource(ResourceDTO resourceDTO) {
        List reportTemplateByScheme = this.reportDesignTimeController.listReportTemplateByFormScheme(resourceDTO.getFormSchemeKey());
        reportTemplateByScheme.sort(Comparator.comparing(ReportTemplateDefine::getOrder));
        ResourceDO resourceDO = new ResourceDO("REPORT_DEFINE");
        String[] fields = new String[]{"key", "fileKey", "fileName", "fileNameExp", "condition", "taskKey"};
        ArrayList<Object[]> value = new ArrayList<Object[]>(reportTemplateByScheme.size());
        int fieldLengths = fields.length;
        for (DesignReportTemplateDefine designReportTemplateDefine : reportTemplateByScheme) {
            Object[] lineData = new Object[fieldLengths];
            lineData[0] = designReportTemplateDefine.getKey();
            lineData[1] = designReportTemplateDefine.getFileKey();
            lineData[2] = designReportTemplateDefine.getFileName();
            lineData[3] = designReportTemplateDefine.getFileNameExp();
            lineData[4] = designReportTemplateDefine.getCondition();
            lineData[5] = designReportTemplateDefine.getTaskKey();
            value.add(lineData);
        }
        resourceDO.setFields(fields);
        resourceDO.setValues(value);
        return resourceDO;
    }

    public ResourceCategoryDO getCategory(ResourceDTO resourceDTO) {
        ResourceCategoryDO categoryDO = new ResourceCategoryDO("REPORT_DEFINE");
        categoryDO.setKey("REPORT_DEFINE");
        categoryDO.setTitle("\u62a5\u544a\u6a21\u677f");
        return categoryDO;
    }
}

