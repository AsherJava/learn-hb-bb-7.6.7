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
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.internal.definition.MidstoreOrgDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreOrgDataServiceImpl
implements IMidstoreOrgDataService {
    @Autowired
    private IMidstoreDataDao<MidstoreOrgDataDO> midstoreDataDao;
    private final Function<MidstoreOrgDataDO, MidstoreOrgDataDTO> toDto = Convert::mdo2Do;
    private final Function<List<MidstoreOrgDataDO>, List<MidstoreOrgDataDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<MidstoreOrgDataDTO> list(MidstoreOrgDataDTO midstoreDataDTO) {
        ArrayList<MidstoreOrgDataDTO> list = new ArrayList<MidstoreOrgDataDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreOrgDataDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            List<MidstoreOrgDataDO> list2 = this.midstoreDataDao.getBySchemeKey(midstoreDataDTO.getSchemeKey());
            list.addAll((Collection<MidstoreOrgDataDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(MidstoreOrgDataDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreOrgDataDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreOrgDataDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            this.midstoreDataDao.deleteBySchemeKey(midstoreDataDTO.getSchemeKey());
        }
    }

    @Override
    public void batchAdd(List<MidstoreOrgDataDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreOrgDataDTO> list2 = new ArrayList<MidstoreOrgDataDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreOrgDataDTO> list2 = new ArrayList<MidstoreOrgDataDTO>();
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
    public void batchUpdate(List<MidstoreOrgDataDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreOrgDataDTO> list2 = new ArrayList<MidstoreOrgDataDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreOrgDataDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreOrgDataDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

