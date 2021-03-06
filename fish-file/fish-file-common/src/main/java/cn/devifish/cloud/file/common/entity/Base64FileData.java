package cn.devifish.cloud.file.common.entity;

import cn.devifish.cloud.common.core.constant.RegexpConstant;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Base64FileData
 * Base64文件数据
 *
 * @author Devifish
 * @date 2020/8/3 18:01
 */
@Data
public class Base64FileData implements Serializable {

    /** 文件名 **/
    private String filename;

    /** Base64内容 **/
    @NotEmpty
    @Pattern(regexp = RegexpConstant.BASE64_FILE)
    private String content;

}
