/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.gcreport.dimension.internal.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.DimensionCustomColumnModelService;
import com.jiuqi.gcreport.dimension.internal.service.impl.AbstractPublishServiceImpl;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPublishServiceImpl
extends AbstractPublishServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DefaultPublishServiceImpl.class);
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired(required=false)
    private List<DimensionCustomColumnModelService> dimensionCustomColumnModelServices;

    @Override
    public void publish(TableModelDefine tableModelDefine, DimensionDTO dimensionDTO) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: publish");
    }

    @Override
    public TableModelDefine checkDesignAndRunTimeDiff(String tableName, String fieldCode) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: checkDesignAndRunTimeDiff");
    }

    @Override
    public DimensionPublishInfoVO publishCheck(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: publishCheck");
    }

    @Override
    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: publishCheckUnPublished");
    }

    @Override
    public boolean existTable(String tableName) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: existTable");
    }

    @Override
    public boolean checkTable(String tableName, String fieldCode) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: checkTable");
    }

    private ColumnModelType getColumnModelType(Integer type) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: getColumnModelType");
    }
}

