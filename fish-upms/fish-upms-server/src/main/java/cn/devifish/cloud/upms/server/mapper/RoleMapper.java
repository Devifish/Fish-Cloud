package cn.devifish.cloud.upms.server.mapper;

import cn.devifish.cloud.upms.common.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * RoleMapper
 *
 * @author Devifish
 * @date 2020/7/13 10:33
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色数据
     *
     * @param userId 用户ID
     * @return List<Role>
     */
    List<Role> selectByUserId(Long userId);

    /**
     * 根据角色Code统计角色数据
     *
     * @param code 角色Code
     * @return Integer
     */
    Integer countByCode(String code);

}
