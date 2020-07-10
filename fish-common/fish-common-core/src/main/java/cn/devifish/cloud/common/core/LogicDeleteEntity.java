package cn.devifish.cloud.common.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LogicDeleteEntity
 * 逻辑删除实体类
 * 用于需要逻辑删除的业务代码
 *
 * @author Devifish
 * @date 2020/7/10 23:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class LogicDeleteEntity extends BaseEntity {

    /**
     * 删除标记
     */
    private Boolean deleteFlag;

}
