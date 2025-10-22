/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.midstore.core.internal.definition.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.midstore.core.definition.dao.IMidstoreDataDao;
import nr.midstore.core.definition.db.Convert;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataFieldDTO;
import nr.midstore.core.definition.service.IMidstoreBaseDataFieldService;
import nr.midstore.core.internal.definition.MidstoreBaseDataFieldDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreBaseDataFieldServiceImpl
implements IMidstoreBaseDataFieldService {
    @Autowired
    private IMidstoreDataDao<MidstoreBaseDataFieldDO> midstoreDataDao;
    private final Function<MidstoreBaseDataFieldDO, MidstoreBaseDataFieldDTO> toDto = Convert::mdbf2Do;
    private final Function<List<MidstoreBaseDataFieldDO>, List<MidstoreBaseDataFieldDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<MidstoreBaseDataFieldDTO> list(MidstoreBaseDataFieldDTO midstoreDataDTO) {
        ArrayList<MidstoreBaseDataFieldDTO> list = new ArrayList<MidstoreBaseDataFieldDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreBaseDataFieldDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            if (StringUtils.isNotEmpty((String)midstoreDataDTO.getBaseDataKey())) {
                List<MidstoreBaseDataFieldDO> list2 = this.midstoreDataDao.getByParentKey(midstoreDataDTO.getSchemeKey(), midstoreDataDTO.getBaseDataKey());
                list.addAll((Collection<MidstoreBaseDataFieldDTO>)this.list2Dto.apply(list2));
            } else {
                List<MidstoreBaseDataFieldDO> list2 = this.midstoreDataDao.getBySchemeKey(midstoreDataDTO.getSchemeKey());
                list.addAll((Collection<MidstoreBaseDataFieldDTO>)this.list2Dto.apply(list2));
            }
        }
        return list;
    }

    @Override
    public void add(MidstoreBaseDataFieldDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreBaseDataFieldDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreBaseDataFieldDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            if (StringUtils.isNotEmpty((String)midstoreDataDTO.getBaseDataKey())) {
                this.midstoreDataDao.deleteByParentKey(midstoreDataDTO.getSchemeKey(), midstoreDataDTO.getBaseDataKey());
            } else {
                this.midstoreDataDao.deleteBySchemeKey(midstoreDataDTO.getSchemeKey());
            }
        }
    }

    @Override
    public void batchAdd(List<MidstoreBaseDataFieldDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreBaseDataFieldDTO> list2 = new ArrayList<MidstoreBaseDataFieldDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreBaseDataFieldDTO> list2 = new ArrayList<MidstoreBaseDataFieldDTO>();
            for (int i = 0; i < midstoreDataDTOs.size(); ++i) {
                if (list2.size() >= 600) {
                    this.midstoreDataDao.batchInsert(list2);
                    list2.clear();
                }
                list2.add(midstoreDataDTOs.get(i));
            }
            if (list2.size() > 0) {
                this.midstoreDataDao.batchInsert(list2);
            }
        }
    }

    @Override
    public void batchUpdate(List<MidstoreBaseDataFieldDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreBaseDataFieldDTO> list2 = new ArrayList<MidstoreBaseDataFieldDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreBaseDataFieldDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreBaseDataFieldDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

