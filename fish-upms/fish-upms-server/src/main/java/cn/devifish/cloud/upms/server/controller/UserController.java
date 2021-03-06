package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.common.core.util.BeanUtils;
import cn.devifish.cloud.common.mybatis.Page;
import cn.devifish.cloud.common.security.annotation.OpenApi;
import cn.devifish.cloud.upms.common.dto.UserDTO;
import cn.devifish.cloud.upms.common.dto.UserPageDTO;
import cn.devifish.cloud.upms.common.enums.SmsCodeType;
import cn.devifish.cloud.upms.common.vo.UserDetailVO;
import cn.devifish.cloud.upms.common.vo.UserVO;
import cn.devifish.cloud.upms.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * UserController
 * 用户接口
 *
 * @author Devifish
 * @date 2020/7/1 11:27
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取当前用户数据
     *
     * @return UserVo
     */
    @GetMapping("/current")
    public UserVO current() {
        var user = userService.currentUser();
        return BeanUtils.copyProperties(user, UserVO::new);
    }

    /**
     * 根据用户ID查询单个信息
     *
     * @param userId 用户ID
     * @return User
     */
    @GetMapping("/select/id/{userId}")
    public UserVO selectById(@PathVariable Long userId) {
        var user = userService.selectById(userId);
        return BeanUtils.copyProperties(user, UserVO::new);
    }

    /**
     * 根据用户ID查询用户详情
     *
     * @param userId 用户ID
     * @return UserDetailVO
     */
    @GetMapping("/select/detail/id/{userId}")
    public UserDetailVO selectDetailById(@PathVariable Long userId) {
        return userService.selectDetailById(userId);
    }

    /**
     * 分页查询用户数据
     *
     * @param param 查询参数
     * @return Page<UserVO>
     */
    @GetMapping("/select/page")
    public Page<UserVO> selectPage(UserPageDTO param) {
        return userService.selectPage(param);
    }

    /**
     * 根据用户名查询是否存在
     *
     * @param username 用户名
     * @return boolean
     */
    @OpenApi
    @GetMapping("/exist/username/{username}")
    public Boolean existByUsername(@PathVariable String username) {
        return userService.existByUsername(username);
    }

    /**
     * 更新用户信息
     * 包含各项参数校验及数据转换
     *
     * @param username 用户名
     * @param userDTO 用户参数
     * @return 是否成功
     */
    @PutMapping("/update/{username}")
    @PreAuthorize("principal.username == #username")
    public Boolean update(@PathVariable String username, @Valid @RequestBody UserDTO userDTO) {
        return userService.update(username, userDTO);
    }

    /**
     * 根据用户ID删除用户
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    @DeleteMapping("/delete/id/{userId}")
    public Boolean delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    /**
     * 发送用户登录验证码
     *
     * @param telephone 电话号码
     * @return 是否成功
     */
    @OpenApi
    @PostMapping("/send/sms-code")
    public Boolean sendSmsCode(@RequestParam String telephone, @RequestParam SmsCodeType type) {
        return userService.sendSmsCode(telephone, type);
    }

}
