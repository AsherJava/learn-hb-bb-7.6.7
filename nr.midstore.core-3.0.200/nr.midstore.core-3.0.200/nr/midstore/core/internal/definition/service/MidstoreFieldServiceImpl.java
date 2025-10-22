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
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.internal.definition.MidstoreFieldDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreFieldServiceImpl
implements IMidstoreFieldService {
    @Autowired
    private IMidstoreDataDao<MidstoreFieldDO> midstoreDataDao;
    private final Function<MidstoreFieldDO, MidstoreFieldDTO> toDto = Convert::mdf2Do;
    private final Function<List<MidstoreFieldDO>, List<MidstoreFieldDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<MidstoreFieldDTO> list(MidstoreFieldDTO midstoreDataDTO) {
        ArrayList<MidstoreFieldDTO> list = new ArrayList<MidstoreFieldDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreFieldDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSrcTableKey())) {
                List<MidstoreFieldDO> list2 = this.midstoreDataDao.getByParentKey(midstoreDataDTO.getSchemeKey(), midstoreDataDTO.getSrcTableKey());
                list.addAll((Collection<MidstoreFieldDTO>)this.list2Dto.apply(list2));
            } else {
                List<MidstoreFieldDO> list2 = this.midstoreDataDao.getBySchemeKey(midstoreDataDTO.getSchemeKey());
                list.addAll((Collection<MidstoreFieldDTO>)this.list2Dto.apply(list2));
            }
        }
        return list;
    }

    @Override
    public void add(MidstoreFieldDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreFieldDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreFieldDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            this.midstoreDataDao.deleteBySchemeKey(midstoreDataDTO.getSchemeKey());
        }
    }

    @Override
    public void batchAdd(List<MidstoreFieldDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreFieldDTO> list2 = new ArrayList<MidstoreFieldDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreFieldDTO> list2 = new ArrayList<MidstoreFieldDTO>();
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
    public void batchUpdate(List<MidstoreFieldDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreFieldDTO> list2 = new ArrayList<MidstoreFieldDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreFieldDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreFieldDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

