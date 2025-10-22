/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.examine.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nvwa.grid2.Grid2Data;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"\u5c01\u9762\u4ee3\u7801\u53d6\u6d88\u81ea\u52a8\u5217\u5bbd"})
@RequestMapping(value={"api/v1/paramcheck/"})
public class FMDMGuidAutoWidthController {
    private static final Logger logger = LoggerFactory.getLogger(FMDMGuidAutoWidthController.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignBigDataTableDao bigDataDao;

    @ApiOperation(value="\u5c01\u9762\u4ee3\u7801\u53d6\u6d88\u81ea\u52a8\u5217\u5bbd")
    @RequestMapping(value={"fmdmGuidAutoWidth"}, method={RequestMethod.GET})
    public void entityViewUpgrade() throws JQException {
        try {
            logger.info("\u7cfb\u7edf\u68c0\u67e5\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u7b2c\u4e00\u5217\u53d6\u6d88\u81ea\u52a8\u5bbd\u5ea6\u5f00\u59cb\u626b\u63cf\uff01");
            List designFormDefines = this.designTimeViewController.getAllFormDefinesWithoutBinaryData();
            List fmForms = designFormDefines.stream().filter(a -> a.getFormType() == FormType.FORM_TYPE_FMDM || a.getFormType() == FormType.FORM_TYPE_NEWFMDM).collect(Collectors.toList());
            List<String> fmFormKeys = fmForms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            StringBuilder whereSql = new StringBuilder();
            whereSql.append("bd_code = 'FORM_DATA ' AND ").append(" bd_key").append(" in (?");
            for (int j = 1; j < fmFormKeys.size(); ++j) {
                whereSql.append(",?");
            }
            whereSql.append(")");
            List designBigDataTables = this.bigDataDao.list(whereSql.toString(), (Object[])fmFormKeys.toArray(new String[0]), DesignBigDataTable.class);
            ArrayList<DesignBigDataTable> updateFMBigDataTables = new ArrayList<DesignBigDataTable>();
            StringBuilder message = new StringBuilder("\u7cfb\u7edf\u68c0\u67e5\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u7b2c\u4e00\u5217\u53d6\u6d88\u81ea\u52a8\u5bbd\u5ea6\u7684\u62a5\u8868\u548c\u8bed\u8a00\u7c7b\u578b\u6709\uff1a");
            for (DesignBigDataTable designBigDataTable : designBigDataTables) {
                byte[] data = designBigDataTable.getData();
                Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])data);
                if (grid2Data == null || grid2Data.getColumnCount() < 2 || !grid2Data.isColumnAutoWidth(1)) continue;
                grid2Data.setColumnAutoWidth(1, false);
                byte[] bytes = Grid2Data.gridToBytes((Grid2Data)grid2Data);
                if (bytes == null) continue;
                designBigDataTable.setData(bytes);
                updateFMBigDataTables.add(designBigDataTable);
                message.append(designBigDataTable.getKey()).append("\u7684\u8bed\u8a00\u7c7b\u578b\u662f ").append(designBigDataTable.getLang()).append(" \u3001");
            }
            if (updateFMBigDataTables.size() > 0) {
                logger.info("\u7cfb\u7edf\u68c0\u67e5\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u7b2c\u4e00\u5217\u53d6\u6d88\u81ea\u52a8\u5bbd\u5ea6\u626b\u63cf\u7ed3\u675f\uff01");
                for (DesignBigDataTable updateFMBigDataTable : updateFMBigDataTables) {
                    try {
                        this.bigDataDao.updateData(updateFMBigDataTable);
                    }
                    catch (Exception e) {
                        logger.error("\u7cfb\u7edf\u68c0\u67e5\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u7b2c\u4e00\u5217\u53d6\u6d88\u81ea\u52a8\u5bbd\u5ea6\u51fa\u9519\uff0c\u51fa\u9519\u7684\u62a5\u8868\u4e3akey\u4e3a\uff1a " + updateFMBigDataTable.getKey(), e);
                    }
                }
                logger.info(message.toString());
            } else {
                logger.info("\u7cfb\u7edf\u68c0\u67e5\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u7b2c\u4e00\u5217\u53d6\u6d88\u81ea\u52a8\u5bbd\u5ea6\u626b\u63cf\u7ed3\u675f\uff01\u6ca1\u6709\u9700\u8981\u53d6\u6d88\u5217\u5bbd\u7684\u5c01\u9762\u4ee3\u7801\u8868");
            }
        }
        catch (Exception e) {
            logger.error("\u7cfb\u7edf\u68c0\u67e5\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u7b2c\u4e00\u5217\u53d6\u6d88\u81ea\u52a8\u5bbd\u5ea6\u51fa\u9519\uff01", e);
        }
    }
}

