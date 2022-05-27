package com.ants.file.samples.controller;

import com.ants.file.samples.utils.Img2Base64Util;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-27
 **/
@RestController
@RequestMapping(value = "hello")
@Slf4j
public class HelloController {

	@Value(value = "${ants.dirPath}")
	private String filePath;

	@Value("${ants.pathFlag}")
	private String pathFlag;

	@PostMapping(value = "add")
	public Object add(HttpServletRequest request,
					  @RequestParam("file") MultipartFile file
	) {
		try {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("文件为空，请重试");
			}
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String allFileName = fileName + UUID.randomUUID() + suffix;
			String fullFilePath = filePath + "file/" + allFileName;
			File dest = new File(fullFilePath);
			try {
				file.transferTo(dest);
				log.info("上传成功");
			} catch (IOException e) {
				log.error(e.toString(), e);
			}
			return "ok";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new IllegalStateException("网络故障,请重试");
		}
	}


	@PostMapping(value = "addImage")
	public Object addCustomerImage(HttpServletRequest request,
								   @RequestParam("file") MultipartFile file
	) {
		log.info("图片开始上传");
		try {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("文件为空，请重试");
			}
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String allFileName = fileName + UUID.randomUUID() + suffix;
			String fullFilePath = filePath + "image/" + allFileName;
			String fullCompassFilePath = filePath + "imageCompass/" + allFileName;
			File dest = new File(fullFilePath);
			try {
				file.transferTo(dest);
				log.info("上传成功");
				// 压缩图片
				Thumbnails.of(fullFilePath)

						.size(160, 160)

						.toFile(fullCompassFilePath);
			} catch (IOException e) {
				log.error(e.toString(), e);
				throw new IllegalStateException("网络故障,请重试");
			}
			return "ok";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new IllegalStateException("网络故障,请重试");
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downLoad(HttpServletResponse response, @RequestParam(value = "fullPathFilename") String fullPathFilename) throws UnsupportedEncodingException {

		String suffix = fullPathFilename.substring(fullPathFilename.lastIndexOf("."));
		File file = new File(fullPathFilename);
		//String filename = StringUtils.substringBefore(StringUtils.substringAfterLast(fullPathFilename, pathFlag), ".") + suffix;
		//判断文件父目录是否存在
		if (file.exists()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.setHeader("content-type", "application/octet-stream");
			//加上设置大小 下载下来的excel文件才不会在打开前提示修复
			response.addHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("content-disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fullPathFilename, "UTF-8"));
			byte[] buffer = new byte[1024];
			//文件输入流
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			//输出流
			OutputStream os = null;
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
