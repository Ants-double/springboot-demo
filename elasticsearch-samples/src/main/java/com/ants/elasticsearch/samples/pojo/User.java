package com.ants.elasticsearch.samples.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-25
 **/
@Slf4j
@Data
@AllArgsConstructor
@Builder
public class User {
	private String id;
	private String userName;
	private String email;
}
