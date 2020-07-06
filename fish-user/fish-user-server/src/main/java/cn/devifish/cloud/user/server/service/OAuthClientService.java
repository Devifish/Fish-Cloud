package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.common.security.OAuthClient;
import cn.devifish.cloud.user.server.mapper.OAuthClientMapper;
import lombok.RequiredArgsConstructor;
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

    private final OAuthClientMapper OAuthClientMapper;

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClient
     */
    public OAuthClient findByClientId(String clientId) {
        return OAuthClientMapper.findByClientId(clientId);
    }

}