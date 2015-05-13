package com.aolong.core.sys.controller;

import com.aolong.common.controller.BaseController;
import com.aolong.common.util.ControllerResult;
import com.aolong.common.util.FileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/file")
public class FileController extends BaseController {

    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("upload") List<MultipartFile> files){

        List<Map<String,Object>> result = FileUtil.upload(files,"d:/");

        return ControllerResult.getSuccessResult(result);
    }


    @RequestMapping("download/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable("fileId") String fileId){
        //获取filePath
        File file = new File("d:/bg.jpg");
        return FileUtil.download(file,"图片.jpg"); //TODO 中文乱码
    }

}
