package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.user.common.entity.OAuthClient;
import cn.devifish.cloud.user.common.vo.OAuthClientVo;
import cn.devifish.cloud.user.server.cache.OAuthClientCache;
import cn.devifish.cloud.user.server.mapper.OAuthClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * OAuthClientService
 * OAuth2 客户端服务
 *
 * @author Devifish
 * @date 2020/7/3 17:36
 */
@Service
@RequiredArgsConstructor
public class OAuthClientService {

    private final OAuthClientMapper oauthClientMapper;
    private final OAuthClientCache oauthClientCache;

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClient
     */
    public OAuthClient findByClientId(String clientId) {
        return oauthClientCache.getIfAbsent(clientId, oauthClientMapper::findByClientId);
    }

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClientVo
     */
    public OAuthClientVo findVoByClientId(String clientId) {
        OAuthClient oauthClient = findByClientId(clientId);
        OAuthClientVo oauthClientVo = new OAuthClientVo();
        BeanUtils.copyProperties(oauthClient, oauthClientVo);
        return oauthClientVo;
    }

}
