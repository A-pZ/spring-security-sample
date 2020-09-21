package com.github.apz.controller.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/upload")
@Log4j2
public class FileUploadController {
	@GetMapping("index")
	public ModelAndView display(ModelAndView mnv) {
		mnv.setViewName("upload/index");
		return mnv;
	}

	@PostMapping("upload")
	public ModelAndView upload(@RequestParam("file") MultipartFile multipartFile, ModelAndView mnv, BindingResult result) {

		if (result.hasErrors()) {
			String message = result.getFieldError().getDefaultMessage();
			mnv.addObject("errorMessage", message);
			mnv.setViewName("upload/index");
			return mnv;
		}

		if (multipartFile.isEmpty()) {
			String message = "ファイルがありません";
			mnv.addObject("errorMessage", message);
			mnv.setViewName("upload/index");
			return mnv;
		}

		try (InputStream uploadStream = multipartFile.getInputStream()){
			String filename = multipartFile.getOriginalFilename();
			FileOutputStream fos = new FileOutputStream(new File("/var/file/" + filename));
			IOUtils.copyLarge(uploadStream, fos);
			mnv.addObject("uploadFileName", filename);
		} catch (IOException e) {
			log.warn("アップロード処理でエラーが発生しました", e);
			String message = "アップロード処理が失敗しました";
			mnv.addObject("errorMessage", message);
			mnv.setViewName("upload/index");
			return mnv;
		}

		mnv.addObject("message", "アップロードが完了しました");
		mnv.setViewName("upload/index");
		return mnv;
	}
}
