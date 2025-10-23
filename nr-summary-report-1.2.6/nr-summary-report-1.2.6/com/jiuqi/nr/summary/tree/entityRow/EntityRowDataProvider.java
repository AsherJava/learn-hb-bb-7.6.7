/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.summary.tree.entityRow;

import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.utils.EntityQueryUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityRowDataProvider
implements TreeNodeBuilder<IEntityRow> {
    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        return true;
    }

    @Override
    public List<IEntityRow> queryData(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        String period = treeQueryParam.getPeriod();
        Date endDate = null;
        if (StringUtils.hasLength(period)) {
            try {
                Date[] periodDateRegion = new DefaultPeriodAdapter().getPeriodDateRegion(period);
                endDate = periodDateRegion[1];
            }
            catch (ParseException e) {
                throw new SummaryCommonException(SummaryErrorEnum.PERIOD_PARSE_FAIL);
            }
        }
        if (StringUtils.hasLength(treeQueryParam.getRowFilter())) {
            return this.queryDataByFilter(treeQueryParam, endDate);
        }
        if (!CollectionUtils.isEmpty(treeQueryParam.getNodeKeyDatas())) {
            return this.queryDataByKeyDatas(treeQueryParam, endDate);
        }
        return this.queryDataNormal(treeQueryParam, endDate);
    }

    private List<IEntityRow> queryDataNormal(TreeQueryParam treeQueryParam, Date versionDate) throws SummaryCommonException {
        String entityId = treeQueryParam.getCustomValue("entityId").toString();
        String nodeKey = treeQueryParam.getNodeKey();
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(entityId, null, null, versionDate);
        treeQueryParam.putCustomParam("entityTable", entityTable);
        EntityType entityType = null;
        if (entityId.endsWith("@ORG")) {
            entityType = EntityType.ORG;
        } else if (entityId.endsWith("@BASE")) {
            entityType = EntityType.BASE;
        }
        treeQueryParam.putCustomParam("entityType", (Object)entityType);
        return EntityQueryUtil.getEntityRows(entityTable, nodeKey, false);
    }

    private List<IEntityRow> queryDataByFilter(TreeQueryParam treeQueryParam, Date versionDate) throws SummaryCommonException {
        String entityId = treeQueryParam.getCustomValue("entityId").toString();
        String rowFilter = treeQueryParam.getRowFilter();
        String nodeKey = treeQueryParam.getNodeKey();
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(entityId, rowFilter, treeQueryParam.getPeriod(), versionDate);
        treeQueryParam.putCustomParam("entityTable", entityTable);
        EntityType entityType = null;
        if (entityId.endsWith("@ORG")) {
            entityType = EntityType.ORG;
        } else if (entityId.endsWith("@BASE")) {
            entityType = EntityType.BASE;
        }
        treeQueryParam.putCustomParam("entityType", (Object)entityType);
        return EntityQueryUtil.getEntityRows(entityTable, nodeKey, false);
    }

    private List<IEntityRow> queryDataByKeyDatas(TreeQueryParam treeQueryParam, Date versionDate) throws SummaryCommonException {
        String entityId = treeQueryParam.getCustomValue("entityId").toString();
        EntityType entityType = null;
        if (entityId.endsWith("@ORG")) {
            entityType = EntityType.ORG;
        } else if (entityId.endsWith("@BASE")) {
            entityType = EntityType.BASE;
        }
        treeQueryParam.putCustomParam("entityType", (Object)entityType);
        return EntityQueryUtil.getEntityRows(entityId, treeQueryParam.getNodeKeyDatas(), versionDate);
    }

    @Override
    public TreeNode buildTreeNode(IEntityRow entityRow, TreeQueryParam treeQueryParam) {
        String icon = "";
        IEntityTable entityTable = (IEntityTable)treeQueryParam.getCustomValue("entityTable");
        EntityType entityType = (EntityType)((Object)treeQueryParam.getCustomValue("entityType"));
        if (entityType == EntityType.ORG) {
            icon = "#icon-_GJWzuzhijigou";
        } else if (entityType == EntityType.BASE) {
            icon = "#icon16_DH_A_NW_jichushuju";
        }
        TreeNode treeNode = new TreeNode(entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), icon);
        if (entityTable != null) {
            int directChildCount = entityTable.getDirectChildCount(entityRow.getEntityKeyData());
            treeNode.setLeaf(directChildCount == 0);
        } else {
            treeNode.setLeaf(true);
        }
        return treeNode;
    }

    static enum EntityType {
        ORG,
        BASE;

    }
}

