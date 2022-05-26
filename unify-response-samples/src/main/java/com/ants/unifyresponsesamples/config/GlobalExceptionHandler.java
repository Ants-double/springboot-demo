package com.ants.unifyresponsesamples.config;

import com.ants.unifyresponsesamples.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String ERROR_EXCEPTION="网络或操作过程异常,请联系管理员";
	private static final String ACCESS_EXCEPTION="你没有操作权限,请联系部门经理或管理员";
	/**
	 * 方法参数错误异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		log.error("方法参数错误异常");
		// 从异常对象中拿到ObjectError对象
		List<String> list=new ArrayList<>();
		if (!e.getBindingResult().getAllErrors().isEmpty()){
			for(ObjectError error:e.getBindingResult().getAllErrors()){
				list.add(error.getDefaultMessage().toString());
			}
		}
		// 然后提取错误提示信息进行返回
		return Result.error(list);
	}
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Result<?>> exceptionHandler(Exception ex, WebRequest request) {
		log.error("ExceptionHandler: {}", ex.getMessage());
		HttpHeaders headers = new HttpHeaders();
		// 处理权限不足的异常
		if (ex instanceof AccessDeniedException) {
			Result<?> body = Result.error(ACCESS_EXCEPTION);
			HttpStatus status =  HttpStatus.BAD_REQUEST;
			return this.handleExceptionInternal(ex, body, headers, status, request);
		}

		if (ex instanceof Exception) {
			return this.handleException((Exception) ex, headers, request);
		}


		return this.handleException(ex, headers, request);
	}

	/** 异常类的统一处理 */
	protected ResponseEntity<Result<?>> handleException(Exception ex, HttpHeaders headers, WebRequest request) {

		Result<?> body = Result.error(ex);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return this.handleExceptionInternal(ex, body, headers, status, request);
	}
	protected ResponseEntity<Result<?>> handleExceptionInternal(
			Exception ex, Result<?> body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (HttpStatus.BAD_REQUEST.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}
}
