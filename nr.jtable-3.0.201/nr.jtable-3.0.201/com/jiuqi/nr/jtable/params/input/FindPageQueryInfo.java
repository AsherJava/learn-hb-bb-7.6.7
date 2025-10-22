/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;

public class FindPageQueryInfo {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    private String searchText;
    private String replaceText;
    private List<String> checkbox;
    private List<String> pageRegionKey;
    private Map<String, PagerInfo> regionKeyPageMap;
    private Map<String, List<String>> regionKeyEnumLinksMap;
    private Map<String, List<String>> regionKeyUnEditableMap;
    private Map<String, RegionFilterInfo> regionKeyFiltMap;
    private List<String> bizKeys;
    private String regionKey;

    public Map<String, RegionFilterInfo> getRegionKeyFiltMap() {
        return this.regionKeyFiltMap;
    }

    public void setRegionKeyFiltMap(Map<String, RegionFilterInfo> regionKeyFiltMap) {
        this.regionKeyFiltMap = regionKeyFiltMap;
    }

    public List<String> getBizKeys() {
        return this.bizKeys;
    }

    public void setBizKeys(List<String> bizKeys) {
        this.bizKeys = bizKeys;
    }

    public Map<String, PagerInfo> getRegionKeyPageMap() {
        return this.regionKeyPageMap;
    }

    public Map<String, List<String>> getRegionKeyEnumLinksMap() {
        return this.regionKeyEnumLinksMap;
    }

    public void setRegionKeyEnumLinksMap(Map<String, List<String>> regionKeyEnumLinksMap) {
        this.regionKeyEnumLinksMap = regionKeyEnumLinksMap;
    }

    public Map<String, List<String>> getRegionKeyUnEditableMap() {
        return this.regionKeyUnEditableMap;
    }

    public void setRegionKeyUnEditableMap(Map<String, List<String>> regionKeyUnEditableMap) {
        this.regionKeyUnEditableMap = regionKeyUnEditableMap;
    }

    public void setRegionKeyPageMap(Map<String, PagerInfo> regionKeyPageMap) {
        this.regionKeyPageMap = regionKeyPageMap;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getReplaceText() {
        return this.replaceText;
    }

    public void setReplaceText(String replaceText) {
        this.replaceText = replaceText;
    }

    public List<String> getCheckbox() {
        return this.checkbox;
    }

    public void setCheckbox(List<String> checkbox) {
        this.checkbox = checkbox;
    }

    public List<String> getPageRegionKey() {
        return this.pageRegionKey;
    }

    public void setPageRegionKey(List<String> pageRegionKey) {
        this.pageRegionKey = pageRegionKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }
}

