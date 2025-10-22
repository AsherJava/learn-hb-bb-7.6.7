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
import nr.midstore.core.definition.dto.MidstoreSchemeGroupDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeGroupService;
import nr.midstore.core.internal.definition.MidstoreSchemeGroupDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreSchemeGroupServiceImpl
implements IMidstoreSchemeGroupService {
    @Autowired
    private IMidstoreDataDao<MidstoreSchemeGroupDO> midstoreDataDao;
    private final Function<MidstoreSchemeGroupDO, MidstoreSchemeGroupDTO> toDto = Convert::mdg2Do;
    private final Function<List<MidstoreSchemeGroupDO>, List<MidstoreSchemeGroupDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<MidstoreSchemeGroupDTO> list(MidstoreSchemeGroupDTO midstoreDataDTO) {
        ArrayList<MidstoreSchemeGroupDTO> list = new ArrayList<MidstoreSchemeGroupDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreSchemeGroupDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            if (obj != null) {
                list.add(this.toDto.apply(obj));
            }
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getParent())) {
            List<MidstoreSchemeGroupDO> list2 = this.midstoreDataDao.getByParentKey(midstoreDataDTO.getParent());
            list.addAll((Collection<MidstoreSchemeGroupDTO>)this.list2Dto.apply(list2));
        } else {
            List<MidstoreSchemeGroupDO> list2 = this.midstoreDataDao.getAll();
            list.addAll((Collection<MidstoreSchemeGroupDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public String add(MidstoreSchemeGroupDTO midstoreDataDTO) throws MidstoreException {
        return this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreSchemeGroupDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreSchemeGroupDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        }
    }

    @Override
    public void batchAdd(List<MidstoreSchemeGroupDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreSchemeGroupDTO> list2 = new ArrayList<MidstoreSchemeGroupDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreSchemeGroupDTO> list2 = new ArrayList<MidstoreSchemeGroupDTO>();
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
    public void batchUpdate(List<MidstoreSchemeGroupDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreSchemeGroupDTO> list2 = new ArrayList<MidstoreSchemeGroupDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreSchemeGroupDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreSchemeGroupDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

