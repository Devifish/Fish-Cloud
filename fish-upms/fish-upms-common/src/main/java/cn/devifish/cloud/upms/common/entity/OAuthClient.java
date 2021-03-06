package cn.devifish.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * OAuthClient
 * OAuth2 客户端
 *
 * @author Devifish
 * @date 2020/7/6 10:18
 */
@Data
@TableName("upms_oauth_client")
public class OAuthClient implements Serializable {

    /** 客户端ID **/
    @TableId
    private String clientId;

    /** 资源ID **/
    private String resourceIds;

    /** 客户端 Secret **/
    private String clientSecret;

    /** 适用域 **/
    private String scope;

    /** 授权方式 **/
    private String grantTypes;

    /** 权限 **/
    private String authorities;

    /** AccessToken有效时间（秒） **/
    private Integer accessTokenValidity;

    /** RefreshToken有效时间（秒） **/
    private Integer refreshTokenValidity;

}
