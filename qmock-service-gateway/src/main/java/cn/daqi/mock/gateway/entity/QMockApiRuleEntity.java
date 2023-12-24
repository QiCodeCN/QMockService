package cn.daqi.mock.gateway.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/12/3
 * @ Des :
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QMockApiRuleEntity extends QBaseEntity implements Serializable {

    private Long id;
    private Long apiId;
    private String title;
    private int enable;
    private String type;
    private JSONArray reqFilter;
    private JSONObject resBody;
    private int resCode = 200;
    private String shell;

}
