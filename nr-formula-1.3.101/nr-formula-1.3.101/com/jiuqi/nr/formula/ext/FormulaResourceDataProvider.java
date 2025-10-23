/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.task.api.common.Constants$QueryType
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 */
package com.jiuqi.nr.formula.ext;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class FormulaResourceDataProvider
implements IResourceDataProvider {
    private final IFormulaSchemeService formulaSchemeService;
    private final IDesignTimeViewController designTimeViewController;
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] FIELDS = new String[]{"index", "total", "key", "title", "type", "typeName", "default", "updateTime"};

    public FormulaResourceDataProvider(IFormulaSchemeService formulaSchemeService, IDesignTimeViewController designTimeViewController) {
        this.formulaSchemeService = formulaSchemeService;
        this.designTimeViewController = designTimeViewController;
    }

    public String getTypeCode() {
        return "FORMULA_RESOURCE";
    }

    public String getTypeTitle() {
        FormulaSchemeType formulaSchemeType = this.getFormulaSchemeType();
        if (formulaSchemeType == FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL) {
            return "\u8d22\u52a1\u516c\u5f0f\u65b9\u6848";
        }
        if (formulaSchemeType == FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) {
            return "\u516c\u5f0f\u65b9\u6848";
        }
        if (formulaSchemeType == FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM) {
            return "\u53d6\u6570\u516c\u5f0f\u65b9\u6848";
        }
        return "\u672a\u77e5\u65b9\u6848";
    }

    public List<ResourceSearchResultDO> search(SearchParam searchParam) {
        ArrayList<ResourceSearchResultDO> search = new ArrayList<ResourceSearchResultDO>();
        String formSchemeKey = searchParam.getFormSchemeKey();
        String keyWords = searchParam.getKeyWords();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            return Collections.emptyList();
        }
        List formulaSchemeDTOList = this.formulaSchemeService.listFormulaSchemeByFormScheme(formSchemeKey).stream().filter(x -> this.getFormulaSchemeType().equals((Object)x.getFormulaSchemeType())).collect(Collectors.toList());
        for (FormulaSchemeDTO formulaSchemeDTO : formulaSchemeDTOList) {
            if (!formulaSchemeDTO.getTitle().toLowerCase(Locale.ROOT).contains(keyWords.toLowerCase(Locale.ROOT))) continue;
            ResourceSearchResultDO resourceSearchResultDO = new ResourceSearchResultDO(this.getTypeCode());
            resourceSearchResultDO.setKey(formulaSchemeDTO.getKey());
            resourceSearchResultDO.setTitle(formulaSchemeDTO.getTitle());
            resourceSearchResultDO.setPath(formulaSchemeDTO.getTitle() + "/" + this.getTypeTitle());
            search.add(resourceSearchResultDO);
        }
        search.sort(this.getSort());
        return search;
    }

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        if (Constants.QueryType.ROOT == resourceCategoryDTO.getQueryType()) {
            return Arrays.asList(this.getResourceCategoryDO(resourceCategoryDTO));
        }
        return Collections.emptyList();
    }

    protected ResourceCategoryDO getResourceCategoryDO(ResourceCategoryDTO resourceCategoryDTO) {
        ResourceCategoryDO resourceCategoryDO = new ResourceCategoryDO(this.getTypeCode());
        resourceCategoryDO.setParent(resourceCategoryDTO.getParent());
        resourceCategoryDO.setIcon(this.getIcon());
        resourceCategoryDO.setKey(this.getTypeCode());
        resourceCategoryDO.setTitle(this.getTypeTitle());
        return resourceCategoryDO;
    }

    protected String getIcon() {
        return "#icon-J_GJ_A_NR_gongshifanganleixing";
    }

    public ResourceDO getResource(ResourceDTO resourceDTO) {
        String formSchemeKey = resourceDTO.getFormSchemeKey();
        if (null == formSchemeKey) {
            return null;
        }
        ResourceDO resourceDO = new ResourceDO(this.getTypeCode());
        resourceDO.setFields(this.getFields());
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        resourceDO.setValues(list);
        List<FormulaSchemeDTO> formulaSchemeDTOList = this.getFormulaScheme(formSchemeKey);
        int size = formulaSchemeDTOList.size();
        for (int i = 0; i < size; ++i) {
            list.add(this.getResourceData(formulaSchemeDTOList.get(i), i, size));
        }
        return resourceDO;
    }

    public ResourceCategoryDO getCategory(ResourceDTO resourceDTO) {
        ResourceCategoryDO resourceCategoryDO = new ResourceCategoryDO(this.getTypeCode());
        resourceCategoryDO.setKey(this.getTypeCode());
        resourceCategoryDO.setTitle(this.getTypeTitle());
        return resourceCategoryDO;
    }

    private Set<Character> splitToSet(String keyWords) {
        return keyWords.chars().mapToObj(c -> Character.valueOf((char)c)).collect(Collectors.toSet());
    }

    protected Object[] getResourceData(FormulaSchemeDTO schemeDTO, int index, int size) {
        return new Object[]{index, size, schemeDTO.getKey(), schemeDTO.getTitle(), this.getFormulaSchemeType().ordinal(), this.getTypeTitle(), Boolean.TRUE.equals(schemeDTO.getDefaultScheme()) ? "\u662f" : "\u5426", this.formatTime(schemeDTO.getUpdateTime())};
    }

    private Object formatTime(Date updateTime) {
        if (null == updateTime) {
            return null;
        }
        return this.SIMPLE_DATE_FORMAT.format(updateTime);
    }

    private List<FormulaSchemeDTO> getFormulaScheme(String formSchemeKey) {
        return this.formulaSchemeService.listFormulaSchemeByFormScheme(formSchemeKey).stream().filter(x -> this.getFormulaSchemeType().equals((Object)x.getFormulaSchemeType())).collect(Collectors.toList());
    }

    private Comparator<? super ResourceSearchResultDO> getSort() {
        return (o1, o2) -> {
            String path2;
            String path1 = null == o1 ? null : o1.getPath();
            String string = path2 = null == o2 ? null : o2.getPath();
            if (null == path1) {
                return -1;
            }
            if (null == path2) {
                return 1;
            }
            return path1.compareTo(path2);
        };
    }

    protected FormulaSchemeType getFormulaSchemeType() {
        return FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT;
    }

    protected String[] getFields() {
        return FIELDS;
    }

    private boolean contains(Set<Character> characterSet, String title) {
        return title.trim().chars().anyMatch(x -> characterSet.contains(Character.valueOf((char)x)));
    }
}

