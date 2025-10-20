/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.transfer.vo.TableFormatEnum
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.gcreport.transfer.vo.TransferVo
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.transfer.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.transfer.dao.TransferDao;
import com.jiuqi.gcreport.transfer.entity.TransferEo;
import com.jiuqi.gcreport.transfer.service.TransferService;
import com.jiuqi.gcreport.transfer.vo.TableFormatEnum;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.gcreport.transfer.vo.TransferVo;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class TransferServiceImpl
implements TransferService {
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void save(TransferVo vo) {
        TransferEo eo = this.converVO2EO(vo);
        this.beforeSave(eo, vo.getUsingUser());
        TransferEo oldEo = this.getByUserPath(eo.getUserId(), eo.getPath());
        if (oldEo != null) {
            eo.setId(oldEo.getId());
            this.transferDao.update((BaseEntity)eo);
        } else {
            eo.setId(UUIDUtils.newUUIDStr());
            this.transferDao.add((BaseEntity)eo);
        }
    }

    private TransferEo converVO2EO(TransferVo vo) {
        TransferEo eo = new TransferEo();
        eo.setPath(vo.getPath());
        eo.setColumns(JsonUtils.writeValueAsString((Object)vo.getSelectColumnKeys()));
        return eo;
    }

    private void beforeSave(TransferEo eo, boolean usingUser) {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        if (usingUser) {
            if (this.systemUserService.findByUsername(user.getName().trim()).isPresent()) {
                eo.setUserId(UUIDUtils.emptyUUIDStr());
            } else {
                eo.setUserId(user.getId());
            }
        }
        eo.setId(UUIDUtils.newUUIDStr());
        eo.setCreator(user.getName());
        eo.setCreateTime(new Date());
    }

    public TransferEo getByUserPath(String userId, String path) {
        TransferEo exampleEntity = new TransferEo();
        exampleEntity.setUserId(userId);
        exampleEntity.setPath(path);
        List dataList = this.transferDao.selectList((BaseEntity)exampleEntity);
        if (dataList != null && dataList.size() > 0) {
            return (TransferEo)((Object)dataList.get(0));
        }
        return null;
    }

    @Override
    public List<String> getSelectColumnsByPath(String path, boolean usingUser) {
        TransferEo exampleEntity = new TransferEo();
        if (usingUser) {
            NpContext context = NpContextHolder.getContext();
            ContextUser user = context.getUser();
            if (this.systemUserService.findByUsername(user.getName().trim()).isPresent()) {
                exampleEntity.setUserId(UUIDUtils.emptyUUIDStr());
            } else {
                exampleEntity.setUserId(user.getId());
            }
        }
        exampleEntity.setPath(path);
        return this.findByExample(exampleEntity);
    }

    @Override
    public List<String> getSelectColumnsByPathNoUser(String path) {
        TransferEo exampleEntity = new TransferEo();
        exampleEntity.setPath(path);
        return this.findByExample(exampleEntity);
    }

    private List<String> findByExample(TransferEo exampleEntity) {
        String columns;
        List dataList = this.transferDao.selectList((BaseEntity)exampleEntity);
        if (dataList != null && dataList.size() > 0 && (columns = ((TransferEo)((Object)dataList.get(0))).getColumns()) != null) {
            return (List)JsonUtils.readValue((String)columns, (TypeReference)new TypeReference<List<String>>(){});
        }
        return new ArrayList<String>();
    }

    @Override
    public List<TransferColumnVo> getAllField(String tableName) {
        ArrayList<TransferColumnVo> columnList = new ArrayList<TransferColumnVo>();
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableName);
            List columnModelDefines = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            columnModelDefines.forEach(item -> {
                TableModelDefine referTableDefine;
                String referTableID;
                TransferColumnVo tmpVo = new TransferColumnVo();
                tmpVo.setKey(item.getCode());
                tmpVo.setLabel(item.getLocaleTitle());
                tmpVo.setColumnType(item.getColumnType().name());
                if (item.getColumnType() == ColumnModelType.DOUBLE || item.getColumnType() == ColumnModelType.INTEGER || item.getColumnType() == ColumnModelType.INTEGER) {
                    tmpVo.setDefaultFormatter(TableFormatEnum.NUMBER);
                    tmpVo.setAlign("right");
                }
                if (!ObjectUtils.isEmpty(referTableID = item.getReferTableID()) && (referTableDefine = dataModelService.getTableModelDefineById(referTableID)) != null) {
                    tmpVo.setReferTableCode(referTableDefine.getCode());
                }
                columnList.add(tmpVo);
            });
            return columnList;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u5217\u51fa\u73b0\u5f02\u5e38\uff01");
        }
    }
}

