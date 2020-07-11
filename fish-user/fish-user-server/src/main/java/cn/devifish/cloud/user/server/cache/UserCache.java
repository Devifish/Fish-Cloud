package cn.devifish.cloud.user.server.cache;

import cn.devifish.cloud.common.redis.BaseCache;
import cn.devifish.cloud.user.common.entity.User;
import org.springframework.stereotype.Repository;

/**
 * UserCache
 * 用户缓存
 *
 * @author Devifish
 * @date 2020/7/11 15:47
 */
@Repository
public class UserCache extends BaseCache<User, Long> {
}
