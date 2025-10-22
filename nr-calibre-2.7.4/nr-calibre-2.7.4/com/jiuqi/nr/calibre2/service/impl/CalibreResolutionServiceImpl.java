/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.ICalibreResolutionService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.Utils;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreSolution;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreSubListDao;
import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalibreResolutionServiceImpl
implements ICalibreResolutionService {
    private static final Logger log = LoggerFactory.getLogger(CalibreResolutionServiceImpl.class);
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private ICalibreSubListDao subListDao;
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController viewRunTimeController;

    @Override
    public String getExpression(CalibreDataDTO calibreDataDTO) {
        String expression = null;
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setCode(calibreDataDTO.getCalibreCode());
        Result<CalibreDefineDTO> queryDefineResult = this.calibreDefineService.get(calibreDefineDTO);
        if (queryDefineResult.getCode() != 1) {
            throw new RuntimeException("\u67e5\u8be2\u53e3\u5f84\u5b9a\u4e49\u5f02\u5e38");
        }
        CalibreDefineDTO define = queryDefineResult.getData();
        Result<CalibreDataDTO> queryData = this.calibreDataService.get(calibreDataDTO);
        if (queryData.getCode() != 1) {
            throw new RuntimeException("\u67e5\u8be2\u53e3\u5f84\u6570\u636e\u5f02\u5e38");
        }
        CalibreDataDTO data = queryData.getData();
        expression = define.getType() == 0 ? this.buildExpression(this.queryEntities(define, calibreDataDTO.getCode()), define.getEntityId()) : (data.getValue().getExpression() == null ? "" : data.getValue().getExpression().toString());
        return expression;
    }

    @Override
    public CalibreSolution getList(CalibreDataDTO calibreDataDTO, ExecutorContext context) {
        String expression;
        CalibreSolution solution = new CalibreSolution();
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setCode(calibreDataDTO.getCalibreCode());
        Result<CalibreDefineDTO> queryDefineResult = this.calibreDefineService.get(calibreDefineDTO);
        if (queryDefineResult.getCode() != 1) {
            throw new RuntimeException("\u67e5\u8be2\u53e3\u5f84\u5b9a\u4e49\u5f02\u5e38");
        }
        Result<CalibreDataDTO> queryData = this.calibreDataService.get(calibreDataDTO);
        if (queryData.getCode() != 1) {
            throw new RuntimeException("\u67e5\u8be2\u53e3\u5f84\u6570\u636e\u5f02\u5e38");
        }
        CalibreDefineDTO define = queryDefineResult.getData();
        calibreDataDTO = queryData.getData();
        List<String> entities = null;
        if (define.getType() == 0) {
            entities = this.queryEntities(define, calibreDataDTO.getCode());
            expression = this.buildExpression(entities, define.getEntityId());
            solution.setSubName(Utils.generatorSubTableCode(define.getCode()));
        } else {
            if (calibreDataDTO.getValue().getExpression() != null && !"".equals(calibreDataDTO.getValue().getExpression())) {
                entities = this.parseEntities(define, calibreDataDTO, context);
            }
            expression = this.getExpressionValue(calibreDataDTO);
        }
        solution.setEntityId(define.getEntityId());
        solution.setId(define.getKey());
        solution.setExpression(expression);
        solution.setEntities(entities);
        return solution;
    }

    private String buildExpression(List<String> entities, String entityId) {
        StringBuilder builder = new StringBuilder();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        builder.append(bizKeyField.getCode()).append(" in { ");
        for (String entity : entities) {
            builder.append(entity).append(",");
        }
        builder.replace(builder.length() - 1, builder.length(), " ");
        builder.append(" }");
        return builder.toString();
    }

    private String getExpressionValue(CalibreDataDTO calibreDataDTO) {
        return calibreDataDTO.getValue().getExpression() == null ? "" : calibreDataDTO.getValue().getExpression().toString();
    }

    private List<String> parseEntities(CalibreDefineDTO defineDTO, CalibreDataDTO calibreDataDTO, ExecutorContext context) {
        List<String> entityKeys = new ArrayList<String>();
        EntityViewDefine entityViewDefine = this.viewRunTimeController.buildEntityView(defineDTO.getEntityId());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setRowFilter(calibreDataDTO.getValue().getExpression().toString());
        entityQuery.setEntityView(entityViewDefine);
        try {
            IEntityTable executeReader = entityQuery.executeReader((IContext)context);
            List rows = executeReader.getAllRows();
            entityKeys = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (defineDTO.getStructType() == 1 && calibreDataDTO.getValue().getSum().booleanValue()) {
            CalibreDataDTO calibreDataQueryDTO = new CalibreDataDTO();
            calibreDataQueryDTO.setDefineKey(defineDTO.getKey());
            calibreDataQueryDTO.setCalibreCode(defineDTO.getCode());
            calibreDataQueryDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
            calibreDataQueryDTO.setCode(calibreDataDTO.getCode());
            Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataQueryDTO);
            HashSet<String> allChildrenKeys = new HashSet<String>();
            if (list.getCode() == 1) {
                List<CalibreDataDTO> children = list.getData();
                for (CalibreDataDTO child : children) {
                    if (child.getValue().getExpression() == null || "".equals(child.getValue().getExpression())) continue;
                    List<String> childrenKeys = this.parseEntities(defineDTO, child, context);
                    allChildrenKeys.addAll(childrenKeys);
                }
            }
            entityKeys = entityKeys.stream().filter(allChildrenKeys::contains).collect(Collectors.toList());
        }
        return entityKeys;
    }

    private List<String> queryEntities(CalibreDefineDTO define, String key) {
        CalibreSubListDO subList = new CalibreSubListDO();
        subList.setCode(key);
        subList.setCalibreCode(define.getCode());
        List<CalibreSubListDO> query = this.subListDao.query(subList);
        return query.stream().map(CalibreSubListDO::getValue).collect(Collectors.toList());
    }
}

