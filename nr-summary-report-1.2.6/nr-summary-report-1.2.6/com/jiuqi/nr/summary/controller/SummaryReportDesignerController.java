/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.common.CalibreDataOption$DataTreeType
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.vo.EntityQueryParam;
import com.jiuqi.nr.summary.vo.EntityRowItem;
import com.jiuqi.nr.summary.vo.EntityTitleQueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report"})
public class SummaryReportDesignerController {
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private ICalibreDataService calibreDataService;

    @PostMapping(value={"/model/entityRows"})
    public List<EntityRowItem> getEntityRowItems(@RequestBody EntityQueryParam entityQueryParam) throws Exception {
        List<EntityRowItem> result = new ArrayList<EntityRowItem>();
        if (entityQueryParam.isCb()) {
            this.buildCalibreTree(result, entityQueryParam.getEntityId());
        } else {
            List<IEntityRow> entityRows = this.summaryParamService.getEntityRows(entityQueryParam);
            result = entityRows.stream().map(EntityRowItem::build).collect(Collectors.toList());
        }
        return result;
    }

    @PostMapping(value={"/design/entity/title"})
    public List<EntityRowItem> getEntityTitleByCode(@RequestBody EntityTitleQueryParam queryParam) throws Exception {
        List<EntityRowItem> result = new ArrayList<EntityRowItem>();
        List<IEntityRow> entityRows = this.summaryParamService.getEntityRows(queryParam);
        if (entityRows != null) {
            result = entityRows.stream().map(EntityRowItem::build).collect(Collectors.toList());
        }
        return result;
    }

    private void buildCalibreTree(List<EntityRowItem> entityRowItems, String calibreDefineKey) {
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setDefineKey(calibreDefineKey);
        calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
        Result root = this.calibreDataService.list(calibreDataDTO);
        List rootDatas = (List)root.getData();
        if (!CollectionUtils.isEmpty(rootDatas)) {
            for (CalibreDataDTO rootData : rootDatas) {
                entityRowItems.add(EntityRowItem.build(rootData));
                this.buildCalibreTreeChild(entityRowItems, calibreDefineKey, rootData.getCode());
            }
        }
    }

    private void buildCalibreTreeChild(List<EntityRowItem> entityRowItems, String calibreDefineKey, String code) {
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setDefineKey(calibreDefineKey);
        calibreDataDTO.setCode(code);
        calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
        Result childs = this.calibreDataService.list(calibreDataDTO);
        List childDatas = (List)childs.getData();
        if (!CollectionUtils.isEmpty(childDatas)) {
            for (CalibreDataDTO childData : childDatas) {
                entityRowItems.add(EntityRowItem.build(childData));
                this.buildCalibreTreeChild(entityRowItems, calibreDefineKey, childData.getCode());
            }
        }
    }
}

