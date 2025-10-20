/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.designer.web.rest.vo.EnumLinkVO;
import com.jiuqi.nr.designer.web.rest.vo.SimpleDataField;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u679a\u4e3e\u8054\u52a8\u6a21\u5757"})
public class EnumLinkSelectController {
    @Autowired
    private IEntityMetaService entityMetaService;

    @ApiOperation(value="\u67e5\u8be2\u5b9e\u4f53\u6709\u5173\u7cfb\u7684\u6307\u6807")
    @PostMapping(value={"/query-refentity"})
    public List<SimpleDataField> refEntityDataFiled(@RequestBody List<SimpleDataField> list) throws Exception {
        ArrayList<SimpleDataField> res = new ArrayList<SimpleDataField>();
        for (SimpleDataField simpleDataField : list) {
            String entityId = simpleDataField.getEntityId();
            if (entityId == null || CollectionUtils.isEmpty(this.entityMetaService.getEntityRefer(entityId))) continue;
            res.add(simpleDataField);
        }
        return res;
    }

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u533a\u57df\u4e2d\u6307\u6807\u5b9e\u4f53\u4e0e\u9009\u5b9a\u6307\u6807\u7684\u5b9e\u4f53\u6709\u5173\u7cfb\u7684\u7684\u6307\u6807")
    @PostMapping(value={"/query-current-refentity"})
    public List<SimpleDataField> currentRefEntityDataFiled(@RequestBody EnumLinkVO content) {
        ArrayList<SimpleDataField> res = new ArrayList<SimpleDataField>();
        List<SimpleDataField> currentFieldlist = content.getCurrentFiledList();
        SimpleDataField checkedField = content.getCheckedDataField();
        for (SimpleDataField simpleDataField : currentFieldlist) {
            if (checkedField.getEntityId() == null || simpleDataField.getEntityId() == null || !this.entityMetaService.estimateEntityRefer(checkedField.getEntityId(), simpleDataField.getEntityId())) continue;
            res.add(simpleDataField);
        }
        return res;
    }
}

