package com.study.provider.eneity;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/22 15:31
 **/
@Data
public class User implements Serializable {
    @NonNull
    private String id;
    private String name;
}
