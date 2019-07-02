package com.study.provider.eneity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/22 15:31
 **/
@Data
public class User implements Serializable {
    private String id;
    @JsonProperty("username")//可以改变返回的json键名
    @Size(min=6,max=50)//length
    @NotNull
    private String name;
}
