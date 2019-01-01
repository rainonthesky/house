package com.kiyozawa.houses.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class FileService {
    @Value("${file.path}")
    private String filePath;

    public List<String> getImgPaths(List<MultipartFile> files) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String>paths= Lists.newArrayList();
        files.forEach(file -> {
            File loalFile=null;
            //将上传的文件保存到本地
            if(!file.isEmpty()){
                try{
                    loalFile=saveToLocal(file,filePath);
                    // 返回绝对路径
                    String  path = StringUtils.substringAfterLast(loalFile.getAbsolutePath(),filePath);
                    paths.add(path);
                }catch(Exception e){
                    throw new IllegalArgumentException(e);

                }
            }
        });
        return paths;


    }

    public static String getResourcePath(){
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }    private File saveToLocal(MultipartFile file, String filePath2) throws IOException {
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() +"/"+file.getOriginalFilename());
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(), newFile);
        return newFile;
    }
}


