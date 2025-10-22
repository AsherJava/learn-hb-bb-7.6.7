/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils
 *  com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeTemplateDao
 *  com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeUserDao
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeImpl
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException;
import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.service.impl.EstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeTemplateDao;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeUserDao;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationScheme;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeImpl;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeUserService
implements IEstimationSchemeUserService {
    @Resource
    private IEstimationSchemeUserDao schemeUserDao;
    @Resource
    private IEstimationSchemeTemplateDao templateDao;
    @Autowired
    private EstimationSchemeTemplateService estimationSchemeTemplateService;

    @Override
    public IEstimationScheme findEstimationScheme(String estimationScheme, DimensionValueSet dimValueSet) {
        EstimationScheme schemeData = this.schemeUserDao.findEstimationScheme(estimationScheme, dimValueSet);
        if (schemeData != null) {
            return new IEstimationSchemeImpl(this.estimationSchemeTemplateService.getImpl((EstimationSchemeBase)schemeData), dimValueSet);
        }
        return null;
    }

    @Override
    public List<IEstimationScheme> findEstimationSchemes(String formSchemeId, DimensionValueSet dimValueSet) {
        List estimationSchemes = this.schemeUserDao.findEstimationSchemes(NpContextHolder.getContext().getUserId(), formSchemeId, dimValueSet);
        return estimationSchemes.stream().map(e -> new IEstimationSchemeImpl(this.estimationSchemeTemplateService.getImpl((EstimationSchemeBase)e), dimValueSet)).collect(Collectors.toList());
    }

    @Override
    public IEstimationScheme newEstimationScheme(String formSchemeId, List<String> formIds, Map<String, DimensionValue> dimValueMap) {
        EstimationSchemeBase template;
        DimensionValueSet dimValueSet = EstimationSchemeUtils.convert2DimValueSet(dimValueMap);
        List estimationSchemes = this.schemeUserDao.findEstimationSchemes(NpContextHolder.getContext().getUserId(), formSchemeId, dimValueSet);
        if (estimationSchemes.size() > 0) {
            for (EstimationScheme scheme : estimationSchemes) {
                this.schemeUserDao.removeEstimationScheme(scheme.getKey());
            }
        }
        if ((template = this.templateDao.findTemplate("manager_created_scheme", formSchemeId)) == null) {
            throw new EstimationRuntimeException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u6ca1\u6709\u6a21\u677f\uff0c\u4e0d\u80fd\u65b0\u589e\u6d4b\u7b97\u65b9\u6848\uff01");
        }
        List<EstimationForm> formDefines = template.getFormDefines().stream().filter(form -> formIds.contains(form.getFormId())).collect(Collectors.toList());
        EstimationScheme schemeImpl = this.newEstimationSchemeImpl(template, formDefines, dimValueSet);
        this.schemeUserDao.insertEstimationScheme(schemeImpl);
        return new IEstimationSchemeImpl(this.estimationSchemeTemplateService.getImpl((EstimationSchemeBase)schemeImpl), dimValueSet);
    }

    @Override
    public IEstimationScheme oldEstimationScheme(String formSchemeId, Map<String, DimensionValue> dimValueMap) {
        DimensionValueSet dimValueSet = EstimationSchemeUtils.convert2DimValueSet(dimValueMap);
        List estimationSchemes = this.schemeUserDao.findEstimationSchemes(NpContextHolder.getContext().getUserId(), formSchemeId, dimValueSet);
        if (estimationSchemes.size() > 0) {
            EstimationScheme scheme = (EstimationScheme)estimationSchemes.get(estimationSchemes.size() - 1);
            IEstimationSchemeTemplateImpl serviceImpl = this.estimationSchemeTemplateService.getImpl((EstimationSchemeBase)scheme);
            return new IEstimationSchemeImpl(serviceImpl, dimValueSet);
        }
        EstimationSchemeBase template = this.templateDao.findTemplate("manager_created_scheme", formSchemeId);
        if (template == null) {
            throw new EstimationRuntimeException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u6ca1\u6709\u6a21\u677f\uff0c\u4e0d\u80fd\u65b0\u589e\u6d4b\u7b97\u65b9\u6848\uff01");
        }
        EstimationScheme schemeImpl = this.newEstimationSchemeImpl(template, template.getFormDefines(), dimValueSet);
        this.schemeUserDao.insertEstimationScheme(schemeImpl);
        return new IEstimationSchemeImpl(this.estimationSchemeTemplateService.getImpl((EstimationSchemeBase)schemeImpl), dimValueSet);
    }

    private EstimationScheme newEstimationSchemeImpl(EstimationSchemeBase template, List<EstimationForm> formDefines, DimensionValueSet dimValueSet) {
        EstimationScheme impl = new EstimationScheme(template, dimValueSet);
        impl.setKey(UUID.randomUUID().toString());
        impl.setCode(OrderGenerator.newOrder());
        impl.setTitle(template.getTitle() + "\uff08" + NpContextHolder.getContext().getUserName() + "\uff09");
        impl.setCreator(NpContextHolder.getContext().getUserId());
        impl.setFormDefines(formDefines);
        impl.setUpdateTime(new Date());
        impl.setOrder(OrderGenerator.newOrder());
        return impl;
    }
}

