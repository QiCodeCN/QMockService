package cn.daqi.mock.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/9/17
 * @ Des :
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QBaseEntity implements Serializable {

    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;

}
