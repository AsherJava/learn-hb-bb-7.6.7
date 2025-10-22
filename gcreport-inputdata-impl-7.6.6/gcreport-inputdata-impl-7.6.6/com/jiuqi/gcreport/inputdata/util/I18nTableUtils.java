/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class I18nTableUtils {
    @Autowired
    private DataModelService dataModelService;
    private I18nHelper i18Provider;
    private Logger logger = LoggerFactory.getLogger(I18nTableUtils.class);

    public List<I18NResourceItem> getResourceByTableName(String tableName) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        try {
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByName(tableName);
            List defines = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            if (CollectionUtils.isEmpty((Collection)defines)) {
                return resourceObjects;
            }
            for (ColumnModelDefine fieldDefine : defines) {
                resourceObjects.add(new I18NResourceItem(fieldDefine.getID(), fieldDefine.getTitle()));
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return resourceObjects;
    }

    public List<DesignFieldDefineVO> getAllFieldsByTableName(String tableName, Set<String> disableColumnSet) {
        ArrayList<DesignFieldDefineVO> designFieldDefineVOs = new ArrayList<DesignFieldDefineVO>();
        try {
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByName(tableName);
            List defines = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            for (ColumnModelDefine columnModelDefine : defines) {
                String title;
                String code = columnModelDefine.getName().toUpperCase();
                if (disableColumnSet.contains(code)) continue;
                DesignFieldDefineVO designFieldDefineVO = new DesignFieldDefineVO();
                designFieldDefineVO.setKey(code);
                String localTitle = this.getI18Provider().getMessage(columnModelDefine.getID());
                String string = title = StringUtils.isEmpty((String)localTitle) ? columnModelDefine.getTitle() : localTitle;
                if (StringUtils.isEmpty((String)title)) {
                    title = code;
                }
                designFieldDefineVO.setLabel(title);
                designFieldDefineVO.setType(columnModelDefine.getColumnType());
                TableModelDefine refTable = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                if (refTable != null) {
                    designFieldDefineVO.setDictTableName(refTable.getName());
                }
                designFieldDefineVOs.add(designFieldDefineVO);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return designFieldDefineVOs;
    }

    private I18nHelper getI18Provider() {
        if (null == this.i18Provider) {
            this.i18Provider = (I18nHelper)SpringContextUtils.getBean((String)"DataModelManage", I18nHelper.class);
        }
        return this.i18Provider;
    }
}

