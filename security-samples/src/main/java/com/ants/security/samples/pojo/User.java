package com.ants.security.samples.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Data
@Builder
@AllArgsConstructor
public class User {
	private String userName;
	private String password;
}
