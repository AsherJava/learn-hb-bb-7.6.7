/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 */
package com.jiuqi.nr.nrdx.param.task.utils;

import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilsService {
    private static final String PARAM_LANGUAGE_ENGLISH = "2";
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;
    private List<AbstractParamTransfer> abstractParamTransfers;
    private Map<String, AbstractParamTransfer> abstractParamTransferMap;

    @Autowired
    private void setGatherList(List<AbstractParamTransfer> abstractParamTransferList) {
        this.abstractParamTransfers = abstractParamTransferList;
        this.abstractParamTransferMap = abstractParamTransferList.stream().collect(Collectors.toMap(ITransferModel::code, a -> a, (k1, k2) -> k1));
    }

    public AbstractParamTransfer getAbstractParamTransfer(String code) {
        AbstractParamTransfer abstractParamTransfer = this.abstractParamTransferMap.get(code);
        return abstractParamTransfer;
    }

    public DesParamLanguageDTO getDesParamLanguage(String resourceKey, LanguageResourceType resourceType, String languageType) {
        List desParamLanguages = this.desParamLanguageDao.queryLanguage(resourceKey, resourceType, languageType);
        if (desParamLanguages.size() > 0) {
            Optional<DesParamLanguage> first = desParamLanguages.stream().filter(a -> PARAM_LANGUAGE_ENGLISH.equals(a.getLanguageType())).findFirst();
            return first.map(DesParamLanguageDTO::valueOf).orElse(null);
        }
        return null;
    }
}

