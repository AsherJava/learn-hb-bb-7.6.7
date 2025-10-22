/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.impl.DataLoader;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class QueryEntityTableImpl
extends ReadonlyTableImpl
implements IEntityTable {
    private final DataLoader dataLoader;

    public QueryEntityTableImpl(QueryContext queryContext, QueryInfo queryInfo, DataLoader dataLoader) {
        super(queryContext, queryInfo);
        this.dataLoader = dataLoader;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        this.queryContext.getLogger().trace("start-getAllRows, masterKey:{}", this.masterKeys);
        EntityResultSet rs = this.dataLoader.getEntityAdapter().getAllRows(this.queryInfo.getQueryParam());
        this.queryContext.getLogger().trace("end-getAllRows\uff1a\u5171{}\u6761\u8bb0\u5f55", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        return this.extractRows();
    }

    private List<IEntityRow> extractRows() {
        return new ArrayList<IEntityRow>(this.dataRows);
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.queryContext.getLogger().trace("start-getRootRows");
        EntityResultSet rs = this.dataLoader.getEntityAdapter().getRootRows(this.queryInfo.getQueryParam());
        this.queryContext.getLogger().trace("end-getRootRows\uff1a\u5171{}\u6761\u8bb0\u5f55", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        return this.extractRows();
    }

    @Override
    public List<IEntityRow> getChildRows(String entityKeyData) {
        this.queryContext.getLogger().trace("start-getChildRows: entityKeyData={}", entityKeyData);
        EntityResultSet rs = this.dataLoader.getEntityAdapter().getChildRows(this.queryInfo.getQueryParam(), entityKeyData);
        this.queryContext.getLogger().trace("end-getChildRows: \u5171{}\u6761\u8bb0\u5f55", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        return this.extractRows();
    }

    @Override
    public List<IEntityRow> getAllChildRows(String entityKeyData) {
        this.queryContext.getLogger().trace("start-getAllChildRows: entityKeyData={}", entityKeyData);
        EntityResultSet rs = this.dataLoader.getEntityAdapter().getAllChildRows(this.queryInfo.getQueryParam(), entityKeyData);
        this.queryContext.getLogger().trace("end-getAllChildRows: \u5171{}\u6761\u8bb0\u5f55", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        return this.extractRows();
    }

    private void buildQueryParamAddCodes(List<String> codes) {
        EntityQueryParam queryParam = this.queryInfo.getQueryParam();
        List<String> metaCodes = queryParam.getCodes();
        if (metaCodes == null) {
            metaCodes = new ArrayList<String>();
        }
        metaCodes.addAll(codes);
        queryParam.setCodes(metaCodes);
    }

    private void removeQueryParamCodes(List<String> codes) {
        EntityQueryParam queryParam = this.queryInfo.getQueryParam();
        List<String> metaCodes = queryParam.getCodes();
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        metaCodes.removeAll(codes);
    }

    @Override
    public IEntityRow findByEntityKey(String entityKeyData) {
        this.queryContext.getLogger().trace("start-findByEntityKey: entityKeyData={}", entityKeyData);
        EntityQueryParam queryParam = this.queryInfo.getQueryParam();
        List<String> masterKey = queryParam.getMasterKey();
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(entityKeyData);
        if (CollectionUtils.isEmpty(masterKey)) {
            queryParam.setMasterKey(keys);
        } else if (masterKey.contains(entityKeyData)) {
            queryParam.setMasterKey(keys);
        } else {
            return null;
        }
        EntityResultSet rs = this.dataLoader.getEntityAdapter().findByEntityKeys(this.queryInfo.getQueryParam());
        this.queryContext.getLogger().trace("end-findByEntityKey: \u5171{}\u6761\u8bb0\u5f55", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        IEntityRow row = null;
        List<IEntityRow> iEntityRows = this.extractRows();
        if (!CollectionUtils.isEmpty(iEntityRows)) {
            row = iEntityRows.get(0);
        }
        queryParam.setMasterKey(masterKey);
        return row;
    }

    @Override
    public Map<String, IEntityRow> findByEntityKeys(Set<String> entityKeyDatas) {
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("start-findByEntityKeys: entityKeyData={}", String.join((CharSequence)",", entityKeyDatas));
            }
        });
        EntityQueryParam queryParam = this.queryInfo.getQueryParam();
        List<String> masterKey = queryParam.getMasterKey();
        ArrayList<String> copyMasterKey = new ArrayList<String>(masterKey);
        if (CollectionUtils.isEmpty(masterKey)) {
            queryParam.setMasterKey(new ArrayList<String>(entityKeyDatas));
        } else {
            masterKey.retainAll(entityKeyDatas);
        }
        EntityResultSet rs = this.dataLoader.getEntityAdapter().findByEntityKeys(queryParam);
        this.queryContext.getLogger().trace("end-findByEntityKeys: \u5171{}\u6761\u8bb0\u5f55", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        queryParam.setMasterKey(copyMasterKey);
        return this.dataRows.stream().collect(Collectors.toMap(EntityRowImpl::getEntityKeyData, e -> e, (e1, e2) -> e2));
    }

    @Override
    public int getMaxDepth() {
        this.queryContext.getLogger().trace("start-getMaxDepth");
        int maxDepth = this.dataLoader.getEntityAdapter().getMaxDepth(this.queryInfo.getQueryParam());
        this.queryContext.getLogger().trace("end-getMaxDepth, maxDepth={}", maxDepth);
        return maxDepth;
    }

    @Override
    public int getMaxDepthByEntityKey(String entityKeyData) {
        this.queryContext.getLogger().trace("start-getMaxDepthByEntityKey: entityKeyData={}", entityKeyData);
        int depth = this.dataLoader.getEntityAdapter().getMaxDepthByEntityKey(this.queryInfo.getQueryParam(), entityKeyData);
        this.queryContext.getLogger().trace("end-getMaxDepthByEntityKey, depth={}", depth);
        return depth;
    }

    @Override
    public IEntityRow findByCode(String code) {
        this.queryContext.getLogger().trace("start-findByCode: code={}", code);
        List<IEntityRow> allRows = this.findAllByCode(code);
        if (CollectionUtils.isEmpty(allRows)) {
            return null;
        }
        this.queryContext.getLogger().trace("end-findByCode, row={}", allRows.get(0).getEntityKeyData());
        return allRows.get(0);
    }

    @Override
    public List<IEntityRow> findAllByCode(String codeValue) {
        this.queryContext.getLogger().trace("start-findAllByCode: codeValue={}", codeValue);
        ArrayList<String> codes = new ArrayList<String>(1);
        codes.add(codeValue);
        Map<String, List<IEntityRow>> allByCodes = this.findAllByCodes(codes);
        List<IEntityRow> iEntityRows = allByCodes.get(codeValue);
        if (null == iEntityRows) {
            return null;
        }
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder("end-findAllByCode:");
                for (IEntityRow iEntityRow : iEntityRows) {
                    sb.append(iEntityRow.getEntityKeyData()).append(",");
                }
                e.trace(sb.toString());
            }
        });
        return iEntityRows;
    }

    @Override
    public Map<String, IEntityRow> findByCodes(List<String> codeValue) {
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("start-findByCodes: codeValue={}", String.join((CharSequence)",", codeValue));
            }
        });
        Map<String, List<IEntityRow>> allByCodes = this.findAllByCodes(codeValue);
        HashMap<String, IEntityRow> result = new HashMap<String, IEntityRow>(allByCodes.size());
        Set<String> codes = allByCodes.keySet();
        this.queryContext.getLogger().trace("end-findByCodes:");
        for (String code : codes) {
            List<IEntityRow> iEntityRows = allByCodes.get(code);
            if (CollectionUtils.isEmpty(iEntityRows)) continue;
            this.queryContext.getLogger().trace("code:{}, value:{}", code, iEntityRows.size());
            result.put(code, iEntityRows.get(0));
        }
        return result;
    }

    @Override
    public Map<String, List<IEntityRow>> findAllByCodes(List<String> codeValue) {
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("start-findAllByCodes: codeValue={}", String.join((CharSequence)",", codeValue));
            }
        });
        this.buildQueryParamAddCodes(codeValue);
        EntityResultSet rs = this.dataLoader.getEntityAdapter().findByCode(this.queryInfo.getQueryParam());
        this.queryContext.getLogger().trace("end-findAllByCodes, total={}", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        HashMap<String, List<IEntityRow>> rowMap = new HashMap<String, List<IEntityRow>>(codeValue.size());
        List<IEntityRow> allRows = this.extractRows();
        if (!CollectionUtils.isEmpty(allRows)) {
            for (IEntityRow iEntityRow : allRows) {
                String code = iEntityRow.getCode();
                rowMap.computeIfAbsent(code, key -> new ArrayList()).add(iEntityRow);
            }
        }
        this.removeQueryParamCodes(codeValue);
        return rowMap;
    }

    @Override
    public int getDirectChildCount(String entityKeyData) {
        this.queryContext.getLogger().trace("start-getDirectChildCount, entityKeyData={}", entityKeyData);
        int count = this.dataLoader.getEntityAdapter().getDirectChildCount(this.queryInfo.getQueryParam(), entityKeyData);
        this.queryContext.getLogger().trace("end-getDirectChildCount, count={}", count);
        return count;
    }

    @Override
    public int getAllChildCount(String entityKeyData) {
        this.queryContext.getLogger().trace("start-getAllChildCount, entityKeyData={}", entityKeyData);
        int count = this.dataLoader.getEntityAdapter().getAllChildCount(this.queryInfo.getQueryParam(), entityKeyData);
        this.queryContext.getLogger().trace("end-getAllChildCount, count={}", count);
        return count;
    }

    @Override
    public Map<String, Integer> getDirectChildCountByParent(String parentKey) {
        this.queryContext.getLogger().trace("start-getDirectChildCountByParent, parentKey={}", parentKey);
        Map<String, Integer> countByParent = this.dataLoader.getEntityAdapter().getDirectChildCountByParent(this.queryInfo.getQueryParam(), parentKey);
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder("end-getDirectChildCountByParent:");
                for (Map.Entry entry : countByParent.entrySet()) {
                    sb.append((String)entry.getKey()).append("=").append(entry.getValue()).append(",");
                }
                e.trace(sb.toString());
            }
        });
        return countByParent;
    }

    @Override
    public Map<String, Integer> getAllChildCountByParent(String parentKey) {
        this.queryContext.getLogger().trace("start-getAllChildCountByParent, parentKey={}", parentKey);
        Map<String, Integer> countByParent = this.dataLoader.getEntityAdapter().getAllChildCountByParent(this.queryInfo.getQueryParam(), parentKey);
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder("end-getAllChildCountByParent:");
                for (Map.Entry entry : countByParent.entrySet()) {
                    sb.append((String)entry.getKey()).append("=").append(entry.getValue()).append(",");
                }
                e.trace(sb.toString());
            }
        });
        return countByParent;
    }

    @Override
    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        this.queryContext.getLogger().trace("start-getParentsEntityKeyDataPath, entityKeyData={}", entityKeyData);
        IEntityRow row = this.findByEntityKey(entityKeyData);
        if (row == null) {
            return null;
        }
        String[] parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath();
        EntityQueryParam entityQueryParam = this.queryInfo.getQueryParam();
        List<String> masterKey = entityQueryParam.getMasterKey();
        if (CollectionUtils.isEmpty(masterKey)) {
            return parentsEntityKeyDataPath;
        }
        ArrayList<String> path = new ArrayList<String>(parentsEntityKeyDataPath.length);
        for (String keyDataPath : parentsEntityKeyDataPath) {
            if (!masterKey.contains(keyDataPath)) continue;
            path.add(keyDataPath);
        }
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("end-getParentsEntityKeyDataPath, path={}", String.join((CharSequence)",", path));
            }
        });
        return path.toArray(new String[0]);
    }

    @Override
    public int getTotalCount() {
        this.queryContext.getLogger().trace("start-getTotalCount");
        int count = this.dataLoader.getEntityAdapter().getTotalCount(this.queryInfo.getQueryParam());
        this.queryContext.getLogger().trace("end-getTotalCount, count={}", count);
        return count;
    }

    @Override
    public List<IEntityRow> getPageRows(PageCondition pageCondition) {
        this.queryContext.getLogger().trace("start-getPageRows: pageSize={}, pageIndex={}", pageCondition.getPageSize(), pageCondition.getPageIndex());
        Integer pageIndex = pageCondition.getPageIndex();
        Integer pageSize = pageCondition.getPageSize();
        EntityQueryParam entityQueryParam = this.queryInfo.getQueryParam();
        entityQueryParam.setLimit(pageSize);
        entityQueryParam.setOffSet(pageIndex);
        EntityResultSet rs = this.dataLoader.getEntityAdapter().getAllRows(entityQueryParam);
        this.queryContext.getLogger().trace("end-getPageRows: total={}", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        return this.extractRows();
    }

    @Override
    public IEntityRow quickFindByEntityKey(String entityKeyData) {
        this.queryContext.getLogger().trace("start-quickFindByEntityKey: entityKeyData={}", entityKeyData);
        EntityQueryParam queryParam = this.queryInfo.getQueryParam();
        List<String> masterKey = queryParam.getMasterKey();
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(entityKeyData);
        if (CollectionUtils.isEmpty(masterKey)) {
            queryParam.setMasterKey(keys);
        } else if (masterKey.contains(entityKeyData)) {
            queryParam.setMasterKey(keys);
        } else {
            return null;
        }
        EntityResultSet rs = this.dataLoader.getEntityAdapter().simpleQuery(this.queryInfo.getQueryParam());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        IEntityRow row = null;
        List<IEntityRow> iEntityRows = this.extractRows();
        if (!CollectionUtils.isEmpty(iEntityRows)) {
            row = iEntityRows.get(0);
        }
        this.queryContext.getLogger().trace("end-quickFindByEntityKey: row={}", row == null ? "" : row.getEntityKeyData());
        queryParam.setMasterKey(masterKey);
        return row;
    }

    @Override
    public Map<String, IEntityRow> quickFindByEntityKeys(Set<String> entityKeyDatas) {
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("start-quickFindByEntityKeys: entityKeyDatas={}", String.join((CharSequence)",", entityKeyDatas));
            }
        });
        EntityQueryParam queryParam = this.queryInfo.getQueryParam();
        List<String> masterKey = queryParam.getMasterKey();
        if (CollectionUtils.isEmpty(masterKey)) {
            queryParam.setMasterKey(new ArrayList<String>(entityKeyDatas));
        } else {
            List<String> filterKey = entityKeyDatas.stream().filter(masterKey::contains).collect(Collectors.toList());
            queryParam.setMasterKey(filterKey);
        }
        queryParam.setMasterKey(new ArrayList<String>(entityKeyDatas));
        EntityResultSet rs = this.dataLoader.getEntityAdapter().simpleQuery(queryParam);
        this.queryContext.getLogger().trace("end-quickFindByEntityKeys: total={}", rs.getTotal());
        this.dataLoader.loadData((ReadonlyTableImpl)this, rs);
        queryParam.setMasterKey(masterKey);
        return this.dataRows.stream().collect(Collectors.toMap(EntityRowImpl::getEntityKeyData, e -> e, (e1, e2) -> e2));
    }
}

