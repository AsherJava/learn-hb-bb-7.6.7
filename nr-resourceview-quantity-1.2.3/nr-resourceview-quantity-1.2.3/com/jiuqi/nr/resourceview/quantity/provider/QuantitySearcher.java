/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.quantity.bean.QuantityCategory
 *  com.jiuqi.nr.quantity.bean.QuantityInfo
 *  com.jiuqi.nr.quantity.bean.QuantityUnit
 *  com.jiuqi.nr.quantity.service.IQuantityService
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.search.SearchResult
 */
package com.jiuqi.nr.resourceview.quantity.provider;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.search.SearchResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantitySearcher
implements IResourceSearcher {
    private static final Logger logger = LoggerFactory.getLogger(QuantitySearcher.class);
    @Autowired
    private IQuantityService quantityService;

    public List<SearchResult> search(String keyword) {
        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
        try {
            QuantityInfo quantityInfo;
            ArrayList<String> pathTitles;
            ArrayList<String> pathIds;
            List quantityInfos = this.quantityService.fuzzyQueryQuantityInfo(keyword);
            List quantityCategories = this.quantityService.fuzzyQueryQuantityCategory(keyword);
            List quantityUnits = this.quantityService.fuzzyQueryQuantityUnit(keyword);
            HashMap<String, QuantityInfo> quantityInfoMapCache = new HashMap<String, QuantityInfo>();
            if (quantityInfos != null) {
                for (QuantityInfo quantityInfo2 : quantityInfos) {
                    quantityInfoMapCache.put(quantityInfo2.getId(), quantityInfo2);
                    SearchResult result1 = new SearchResult();
                    result1.setName(quantityInfo2.getName());
                    result1.setTitle(quantityInfo2.getTitle());
                    result1.setType(NodeType.NODE_GROUP);
                    ArrayList<String> pathIds2 = new ArrayList<String>();
                    ArrayList<String> pathTitles2 = new ArrayList<String>();
                    pathIds2.add("QI_" + quantityInfo2.getId());
                    pathTitles2.add(quantityInfo2.getTitle());
                    result1.setPathIds(pathIds2);
                    result1.setPathTitles(pathTitles2);
                    searchResults.add(result1);
                }
            }
            HashMap<String, QuantityCategory> quantityCategoryMapCache = new HashMap<String, QuantityCategory>();
            if (quantityCategories != null) {
                for (QuantityCategory quantityCategory : quantityCategories) {
                    quantityCategoryMapCache.put(quantityCategory.getId(), quantityCategory);
                    SearchResult result2 = new SearchResult();
                    result2.setName(quantityCategory.getName());
                    result2.setTitle(quantityCategory.getTitle());
                    result2.setType(NodeType.NODE_GROUP);
                    pathIds = new ArrayList();
                    pathTitles = new ArrayList();
                    pathIds.add("QI_" + quantityCategory.getQuantityId());
                    pathIds.add("QC_" + quantityCategory.getId());
                    quantityInfo = (QuantityInfo)quantityInfoMapCache.get(quantityCategory.getQuantityId());
                    if (quantityInfo == null) {
                        quantityInfo = this.quantityService.getQuantityInfoById(quantityCategory.getQuantityId());
                    }
                    pathTitles.add(quantityInfo.getTitle());
                    pathTitles.add(quantityCategory.getTitle());
                    result2.setPathIds(pathIds);
                    result2.setPathTitles(pathTitles);
                    searchResults.add(result2);
                }
            }
            if (quantityUnits != null) {
                for (QuantityUnit quantityUnit : quantityUnits) {
                    SearchResult result3 = new SearchResult();
                    result3.setName(quantityUnit.getName());
                    result3.setTitle(quantityUnit.getTitle());
                    result3.setType(NodeType.NODE_DATA);
                    result3.setIcon("#icon16_DH_A_NW_gongnengfenzushouqi");
                    pathIds = new ArrayList<String>();
                    pathTitles = new ArrayList<String>();
                    pathIds.add("QI_" + quantityUnit.getQuantityId());
                    pathIds.add("QC_" + quantityUnit.getCategoryId());
                    pathIds.add("QU_" + quantityUnit.getId());
                    result3.setPathIds(pathIds);
                    quantityInfo = (QuantityInfo)quantityInfoMapCache.get(quantityUnit.getQuantityId());
                    if (quantityInfo == null) {
                        quantityInfo = this.quantityService.getQuantityInfoById(quantityUnit.getQuantityId());
                    }
                    pathTitles.add(quantityInfo.getTitle());
                    QuantityCategory quantityCategory = (QuantityCategory)quantityCategoryMapCache.get(quantityUnit.getCategoryId());
                    if (quantityCategory == null) {
                        quantityCategory = this.quantityService.getQuantityCategoryById(quantityUnit.getCategoryId());
                    }
                    pathTitles.add(quantityCategory.getTitle());
                    pathTitles.add(quantityUnit.getTitle());
                    result3.setPathTitles(pathTitles);
                    searchResults.add(result3);
                }
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        return searchResults;
    }
}

