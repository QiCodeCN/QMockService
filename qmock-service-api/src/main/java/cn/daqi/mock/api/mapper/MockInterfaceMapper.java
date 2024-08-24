package cn.daqi.mock.api.mapper;

import cn.daqi.mock.api.entity.MockInterfaceEntity;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MockInterfaceMapper {

    List<MockInterfaceEntity> selectMockInterface(InterfaceSearchRequest reqs);

    Integer insertMockInterface(MockInterfaceEntity entity);

    Integer updateMockInterface(MockInterfaceEntity entity);
}
