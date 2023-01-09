package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/31
 * Time:16:02
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String path;


    /**
     * 图片上传
     *  接收浏览器端上传的图片，并且把图片保存在指定文件目录。
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        //1.获取上传文件的文件原始名
        String filename = file.getOriginalFilename();
        //为了防止重名 所以使用UUID将文件名进行处理，使用UUID作为文件名
        String substring = filename.substring(filename.lastIndexOf("."));
        String uuiFieName = UUID.randomUUID().toString()+substring;
        //2.判断文件目录是否存在，如果不存在则创建
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //3.使用目标目录+原始目录组合出新的目录文件
        File file2 = new File(file1, uuiFieName);
        //4.将上传的目录文件上传到目标文件上
        file.transferTo(file2);
        return R.success(uuiFieName);
    }


    @GetMapping("/download")
    public  void download(String name , HttpServletResponse response) throws IOException {
        //找到目标文件
        File file = new File(path, name);
        //创建文件的输入流，与的带response的输出流
        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();

        //边读边写
        byte[] buf=new byte[1024];
        int length=0;
        while ((length=fileInputStream.read(buf))!=-1) {
                outputStream.write(buf,0,length);

        }
        //关闭资源
        fileInputStream.close();

    }
}
