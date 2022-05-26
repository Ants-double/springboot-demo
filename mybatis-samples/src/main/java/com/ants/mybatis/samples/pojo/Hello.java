package com.ants.mybatis.samples.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Data
@AllArgsConstructor
@Builder
public class Hello {
	private String id;
	private String name;
}
