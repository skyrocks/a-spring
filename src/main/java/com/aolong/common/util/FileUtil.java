package com.aolong.common.util;

import com.aolong.common.exception.BusinessException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    public static List<Map<String,Object>> upload(List<MultipartFile> files, String path){
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        for(MultipartFile file : files){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",file.getOriginalFilename());

            String fileName = file.getOriginalFilename();
            String filePath = path + fileName;
            File localFile = new File(filePath);
            try{
                //TODO 存储file数据
                file.transferTo(localFile);
                map.put("success",true);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                map.put("success",false);
            }
            result.add(map);
        }
        return result;
    }

    public static ResponseEntity<byte[]> download(File file,String fileName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
             logger.error(e.getMessage(),e);
            throw new BusinessException(e.getMessage());
        }
    }

    protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
}
