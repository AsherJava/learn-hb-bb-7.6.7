/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.impl.CommonQueryImpl;
import com.jiuqi.nr.entity.engine.intf.impl.EntityQueryBuilder;
import com.jiuqi.nr.entity.engine.intf.impl.EntityTableImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityQueryImpl
extends CommonQueryImpl
implements IEntityQuery {
    private List<ReferRelation> referRelations;
    private List<String> referExpression;
    private Set<String> referFilter;
    private String isolationCondition;
    private Integer stopModel;

    public EntityQueryImpl(QueryContext queryContext) {
        super(queryContext);
        queryContext.getLogger().accept(e -> {
            if (e.isDebugEnabled()) {
                NpContext context = NpContextHolder.getContext();
                String userId = context.getUserId();
                String userName = context.getUserName();
                e.debug("\u6267\u884c\u7528\u6237\uff1a{}-{}", userId, userName);
            }
        });
    }

    @Override
    public void addReferRelation(ReferRelation referRelation) {
        if (referRelation == null) {
            return;
        }
        if (CollectionUtils.isEmpty(this.referRelations)) {
            this.referRelations = new ArrayList<ReferRelation>();
        }
        this.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                List<String> range = referRelation.getRange();
                IEntityRefer refer = referRelation.getRefer();
                EntityViewDefine viewDefine = referRelation.getViewDefine();
                e.trace("\u6dfb\u52a0\u4f9d\u8d56\uff0crefer\uff1a{}, range:{}, viewDefine:{}", refer.toString(), String.join((CharSequence)",", range), viewDefine == null ? "null" : viewDefine.toString());
            }
        });
        this.referRelations.add(referRelation);
    }

    @Override
    public void setIsolateCondition(String isolateCondition) {
        this.isolationCondition = isolateCondition;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u9694\u79bb\u6761\u4ef6\uff1a{}", this.isolationCondition);
    }

    @Override
    public void queryStopModel(int stopModel) {
        this.stopModel = stopModel == 0 ? Integer.valueOf(-1) : Integer.valueOf(stopModel);
        this.getQueryContext().getLogger().trace("\u67e5\u8be2\u505c\u7528\u6570\u636e");
    }

    @Override
    public IEntityTable executeReader(IContext executorContext) throws Exception {
        this.getQueryContext().getLogger().debug("Reader\u6a21\u5f0f");
        EntityQueryBuilder builder = this.getEntityQueryBuilder(executorContext);
        return builder.getBuildTable();
    }

    @Override
    public IEntityTable executeFullBuild(IContext executorContext) throws Exception {
        this.getQueryContext().getLogger().debug("FullBuild\u6a21\u5f0f");
        EntityQueryBuilder builder = this.getEntityQueryBuilder(executorContext);
        IEntityTable entityTable = builder.getReaderTable();
        if (entityTable instanceof EntityTableImpl) {
            EntityTableImpl entityTableImpl = (EntityTableImpl)entityTable;
            builder.getDataLoader().loadData((ReadonlyTableImpl)entityTableImpl, builder.getEntityQueryParam());
            entityTableImpl.tableInit();
        }
        return entityTable;
    }

    @Override
    public IEntityTable executeRangeBuild(IContext executorContext, RangeQuery rangeQuery) throws Exception {
        this.getQueryContext().getLogger().debug("RangeBuild\u6a21\u5f0f");
        EntityQueryBuilder builder = this.getEntityQueryBuilder(executorContext);
        IEntityTable entityTable = builder.getReaderTable();
        if (entityTable instanceof EntityTableImpl) {
            EntityTableImpl entityTableImpl = (EntityTableImpl)entityTable;
            builder.getDataLoader().loadData((ReadonlyTableImpl)entityTableImpl, rangeQuery.loadData(builder.getEntityQueryParam(), builder.getEntityAdapter()));
            entityTableImpl.tableInit();
        }
        return entityTable;
    }

    public List<String> getReferExpression() {
        return this.referExpression;
    }

    public Set<String> getReferFilter() {
        return this.referFilter;
    }

    public String getIsolationCondition() {
        return this.isolationCondition;
    }

    public Integer getStopModel() {
        return this.stopModel;
    }

    private EntityQueryBuilder getEntityQueryBuilder(IContext executorContext) throws JQException {
        this.setContext(executorContext);
        this.loadVariable();
        this.resolutionVersion(true);
        this.judgementEntityRefer();
        return new EntityQueryBuilder(this.getQueryContext()).buildRuntimeEnv(this.getEntityView().getEntityId(), this.getMasterKeys()).buildQueryParam(this);
    }

    private void judgementEntityRefer() {
        List<IEntityRefer> selfEntityRefer;
        if (CollectionUtils.isEmpty(this.referRelations)) {
            return;
        }
        this.referExpression = new ArrayList<String>();
        this.referFilter = new LinkedHashSet<String>();
        IEntityAdapter selfEntityAdapter = this.getEntityAdapter(this.getEntityView().getEntityId());
        Map<Object, Object> referEntityMap = new HashMap();
        try {
            selfEntityRefer = selfEntityAdapter.getEntityRefer(EntityUtils.getId((String)this.getEntityView().getEntityId()));
        }
        catch (Exception e2) {
            this.getQueryContext().getLogger().error(String.format("\u83b7\u53d6\u5f53\u524d\u5b9e\u4f53%s\u7684\u4f9d\u8d56\u65f6\u53d1\u751f\u5f02\u5e38:%s", this.getEntityView().getEntityId(), e2.getMessage()), e2);
            return;
        }
        if (!CollectionUtils.isEmpty(selfEntityRefer)) {
            selfEntityRefer = this.getQueryContext().getQueryService().getEntityReferFilter().getFilterAttribute(selfEntityRefer);
        }
        if (!CollectionUtils.isEmpty(selfEntityRefer)) {
            referEntityMap = this.transferReferEntityId(selfEntityRefer).stream().collect(Collectors.groupingBy(IEntityRefer::getReferEntityId));
        }
        IEntityModel entityModel = selfEntityAdapter.getEntityModel(EntityUtils.getId((String)this.getEntityView().getEntityId()));
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        Set<Object> referSet = new LinkedHashSet();
        for (ReferRelation referRelation : this.referRelations) {
            String expression;
            EntityViewDefine paramView = referRelation.getViewDefine();
            IEntityRefer paramRefer = referRelation.getRefer();
            List initiativeRefers = (List)referEntityMap.get(paramView.getEntityId());
            IEntityRefer initiativeRefer = null;
            if (!CollectionUtils.isEmpty(initiativeRefers)) {
                if (paramRefer != null) {
                    boolean anyMatch = initiativeRefers.stream().anyMatch(e -> e.getOwnEntityId().equals(paramRefer.getOwnEntityId()) && e.getOwnField().equals(paramRefer.getOwnField()));
                    if (anyMatch) {
                        initiativeRefer = paramRefer;
                    }
                } else {
                    initiativeRefer = (IEntityRefer)initiativeRefers.get(0);
                }
            }
            if (initiativeRefer != null && StringUtils.hasText(expression = this.buildInitiativeReferCondition(initiativeRefer, referRelation.getRange()))) {
                this.referExpression.add(expression);
                this.getQueryContext().getLogger().trace("\u6839\u636e\u5173\u8054\u5173\u7cfb\u6784\u9020\u4e86\u8fc7\u6ee4\u516c\u5f0f\uff1a{}", expression);
            }
            ArrayList<String> referKeys = new ArrayList<String>();
            List<IEntityRefer> entityRefer = new ArrayList<IEntityRefer>();
            if (paramRefer != null) {
                entityRefer.add(paramRefer);
            } else {
                IEntityAdapter entityAdapter = this.getEntityAdapter(paramView.getEntityId());
                try {
                    List<IEntityRefer> referRelationEntityRefer = entityAdapter.getEntityRefer(EntityUtils.getId((String)paramView.getEntityId()));
                    if (!CollectionUtils.isEmpty(referRelationEntityRefer)) {
                        referRelationEntityRefer = this.getQueryContext().getQueryService().getEntityReferFilter().getFilterAttribute(referRelationEntityRefer);
                    }
                    if (!CollectionUtils.isEmpty(referRelationEntityRefer)) {
                        entityRefer = this.transferReferEntityId(referRelationEntityRefer);
                    }
                }
                catch (Exception e3) {
                    this.getQueryContext().getLogger().error(String.format("\u83b7\u53d6\u4f9d\u8d56\u5173\u7cfb\u4e2d\u5b9e\u4f53%s\u7684\u4f9d\u8d56\u65f6\u53d1\u751f\u5f02\u5e38\uff1a%s", paramView.getEntityId(), e3.getMessage()), e3);
                }
            }
            for (IEntityRefer iEntityRefer : entityRefer) {
                if (!this.getEntityView().getEntityId().equals(iEntityRefer.getReferEntityId()) || !bizKeyField.getName().equals(iEntityRefer.getReferEntityField())) continue;
                Set<String> passivityReferCondition = this.buildPassivityReferCondition(iEntityRefer, referRelation);
                referKeys.addAll(passivityReferCondition);
                this.getQueryContext().getLogger().trace("\u6839\u636e\u5173\u8054\u5173\u7cfb\uff1a{}\uff0c{}\uff0c\u6784\u9020\u4e86\u4e3b\u952e\u8fc7\u6ee4\uff1a{}", iEntityRefer.getReferEntityId(), iEntityRefer.getReferEntityField(), passivityReferCondition);
            }
            if (CollectionUtils.isEmpty(referSet)) {
                referSet = new LinkedHashSet(referKeys);
                continue;
            }
            referSet = referSet.stream().filter(referKeys::contains).collect(Collectors.toSet());
        }
        this.referFilter.addAll(referSet);
    }

    private List<IEntityRefer> transferReferEntityId(List<IEntityRefer> entityRefer) {
        ArrayList<IEntityRefer> refs = new ArrayList<IEntityRefer>();
        for (IEntityRefer refer : entityRefer) {
            EntityReferImpl impl = (EntityReferImpl)refer;
            IEntityAdapter ownAdapter = this.getEntityAdapter(refer.getOwnEntityId());
            impl.setOwnEntityId(EntityUtils.getEntityId((String)refer.getOwnEntityId(), (String)ownAdapter.getId()));
            IEntityAdapter referAdapter = this.getEntityAdapter(refer.getReferEntityId());
            if (referAdapter == null) {
                this.getQueryContext().getLogger().error(String.format("\u627e\u4e0d\u5230\u5b9e\u4f53%s\u7684\u5c5e\u6027%s\u5173\u8054\u7684\u5b9e\u4f53%s", refer.getOwnEntityId(), refer.getOwnField(), refer.getReferEntityId()));
                continue;
            }
            impl.setReferEntityId(EntityUtils.getEntityId((String)refer.getReferEntityId(), (String)referAdapter.getId()));
            refs.add(impl);
        }
        return refs;
    }

    private String buildInitiativeReferCondition(IEntityRefer initiativeRefer, List<String> range) {
        String ownEntityId = initiativeRefer.getOwnEntityId();
        IEntityAdapter entityAdapter = this.getEntityAdapter(ownEntityId);
        TableModelDefine tableByEntityId = entityAdapter.getTableByEntityId(EntityUtils.getId((String)ownEntityId));
        IEntityModel entityModel = entityAdapter.getEntityModel(EntityUtils.getId((String)ownEntityId));
        IEntityAttribute referField = entityModel.getAttribute(initiativeRefer.getOwnField());
        if (referField == null) {
            this.getQueryContext().getLogger().error(String.format("\u5b9e\u4f53%s\u4f9d\u8d56\u5173\u7cfb\u4e2d\uff0c\u5c5e\u6027%s\u672a\u627e\u5230\u5bf9\u5e94\u7684\u5b9a\u4e49", ownEntityId, initiativeRefer.getOwnField()));
            return null;
        }
        if (CollectionUtils.isEmpty(range)) {
            this.getQueryContext().getLogger().error(String.format("\u5b9e\u4f53id\u4e3a%s\u7684\u53c2\u6570\uff0c\u672a\u8bbe\u7f6e'range'\u7684\u503c", ownEntityId));
            return null;
        }
        boolean multival = referField.isMultival();
        StringBuilder sbs = new StringBuilder("(");
        for (String refEntityKey : range) {
            if (sbs.length() > 1) {
                sbs.append(" or ");
            }
            sbs.append("'").append(refEntityKey).append("' in ");
            if (multival) {
                sbs.append("split(");
            }
            sbs.append(tableByEntityId.getName()).append(".").append(referField.getCode());
            if (!multival) continue;
            sbs.append(",';')");
        }
        sbs.append(" or ISNULL(").append(tableByEntityId.getName()).append(".").append(referField.getCode()).append("))");
        return sbs.toString();
    }

    private Set<String> buildPassivityReferCondition(IEntityRefer entityRefer, ReferRelation referRelation) {
        LinkedHashSet<String> referSet = new LinkedHashSet<String>();
        if (CollectionUtils.isEmpty(referRelation.getRange())) {
            return referSet;
        }
        EntityViewDefine viewDefine = referRelation.getViewDefine();
        EntityResultSet resultSet = this.queryReferEntityData(viewDefine, referRelation.getRange());
        IEntityAdapter entityAdapter = this.getEntityAdapter(entityRefer.getOwnEntityId());
        IEntityModel entityModel = referRelation.getEntityModel();
        if (entityModel == null) {
            entityModel = entityAdapter.getEntityModel(EntityUtils.getId((String)entityRefer.getOwnEntityId()));
        }
        IEntityAttribute referAttribute = entityModel.getAttribute(entityRefer.getOwnField());
        while (resultSet.next()) {
            Object value = resultSet.getObject(referAttribute.getCode());
            if (value == null || "".equals(value)) continue;
            if (value instanceof List) {
                referSet.addAll((List)value);
                continue;
            }
            referSet.add(value.toString());
        }
        return referSet;
    }

    private EntityResultSet queryReferEntityData(EntityViewDefine viewDefine, List<String> keys) {
        IEntityAdapter entityAdapter = this.getEntityAdapter(viewDefine.getEntityId());
        EntityQueryParam referQueryParam = new EntityQueryParam();
        referQueryParam.setEntityId(EntityUtils.getId((String)viewDefine.getEntityId()));
        referQueryParam.setMasterKey(keys);
        referQueryParam.setVersionDate(this.getQueryVersionDate());
        referQueryParam.setContext(this.getExecutorContext());
        referQueryParam.setQueryContext(this.getQueryContext());
        return entityAdapter.simpleQuery(referQueryParam);
    }

    private IEntityAdapter getEntityAdapter(String entityId) {
        return this.getQueryContext().getQueryService().getAdapterService().getEntityAdapter(entityId);
    }

    private void loadVariable() {
        if (this.stopModel != null) {
            return;
        }
        VariableManager variableManager = super.getExecutorContext().getVariableManager();
        Variable varStopFlag = variableManager.find("QUERY_CONTEXT_STOP_FLAG");
        if (varStopFlag != null) {
            try {
                Object stopFlag = varStopFlag.getVarValue((IContext)super.getExecutorContext());
                if (stopFlag != null) {
                    int value = Integer.parseInt(stopFlag.toString().trim());
                    this.queryStopModel(value);
                }
            }
            catch (Exception e) {
                this.getQueryContext().getLogger().error("\u8bfb\u53d6\u505c\u7528\u6761\u4ef6\u53d8\u91cf\u5f02\u5e38\uff1a", e.getMessage());
            }
        }
    }
}

