package cn.daqi.mock.gateway.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/9/17
 * @ Des :
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QMockApiEntity extends QBaseEntity implements Serializable {

    private Long id;
    private Long tagId;
    private Long projectId;
    private String title;
    private String method;
    private String path;
    private Integer enable;
    private String desc;
    private Integer resCode;
    private JSONObject resDefault;

}
