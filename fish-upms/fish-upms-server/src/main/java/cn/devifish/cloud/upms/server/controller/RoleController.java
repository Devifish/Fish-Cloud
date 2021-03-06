package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.common.mybatis.Page;
import cn.devifish.cloud.common.security.annotation.OpenApi;
import cn.devifish.cloud.upms.common.dto.RolePageDTO;
import cn.devifish.cloud.upms.common.entity.Role;
import cn.devifish.cloud.upms.server.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * RoleController
 *
 * @author Devifish
 * @date 2020/7/13 10:45
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 根据角色ID查询角色数据
     *
     * @param roleId 角色ID
     * @return 角色
     */
    @GetMapping("/select/id/{roleId}")
    public Role selectById(@PathVariable Long roleId) {
        return roleService.selectById(roleId);
    }

    /**
     * 分页查询角色数据
     *
     * @param param 参数
     * @return Page<Role>
     */
    @GetMapping("/select/page")
    public Page<Role> selectPage(RolePageDTO param) {
        return roleService.selectPage(param);
    }

    /**
     * 根据用户ID查询角色数据
     *
     * @param userId 用户ID
     * @return List<Role>
     */
    @GetMapping("/select/user-id/{userId}")
    public List<Role> selectByUserId(@PathVariable Long userId) {
        return roleService.selectByUserId(userId);
    }

    /**
     * 根据角色Code查询是否存在
     *
     * @param code 角色Code
     * @return boolean
     */
    @OpenApi
    @GetMapping("/exist/code/{code}")
    public Boolean existByCode(@PathVariable String code) {
        return roleService.existByCode(code);
    }

    /**
     * 根据角色ID更新角色权限
     *
     * @param roleId      角色ID
     * @param authorities 权限集合
     * @return 是否成功
     */
    @PutMapping("/update/authorities/id/{roleId}")
    public Boolean updateAuthoritiesByRoleId(@PathVariable Long roleId, @RequestBody Set<String> authorities) {
        return roleService.updateAuthoritiesByRoleId(roleId, authorities);
    }

    /**
     * 删除角色
     * 必须与用户进行解绑
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    @DeleteMapping("/delete/id/{roleId}")
    public Boolean delete(@PathVariable Long roleId) {
        return roleService.delete(roleId);
    }

}
