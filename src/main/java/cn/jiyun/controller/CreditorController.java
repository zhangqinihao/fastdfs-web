package cn.jiyun.controller;


import cn.jiyun.model.CreditorInfo;
import cn.jiyun.service.CreditorService;
import cn.jiyun.utils.FastDFSUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
public class CreditorController {
    @Resource
    private CreditorService creditorService;
    @RequestMapping("/")
    public String creditors(Model model){
        List<CreditorInfo> list=creditorService.selectAll();
        model.addAttribute("creditorList",list);
        return "creditors";
    }


    @GetMapping("/upload/{id}")
    public String toUpload(@PathVariable Integer id, Model model){
        CreditorInfo creditorInfo=creditorService.selectById(id);
        model.addAttribute("creditorInfo",creditorInfo);
        return "upload";
    }

    /**
     * 文件上传
     * 参数 MultipartFile 为Spring提供的一个类，专门用于封装请求中的文件数据
     * 属性名必须与表单中文件域的名字完全相同否则无法获取文件数据
     */
    @PostMapping("/upload")
    public String upload(Integer id, MultipartFile myFile, Model model) throws IOException {
        System.out.println(myFile.getBytes());//获取文件对应字节数组
        System.out.println(myFile.getInputStream());//获取文件对应的输入流
        System.out.println(myFile.getContentType());//获取文件类型
        System.out.println(myFile.getName());//获取表单元素名
        System.out.println(myFile.getOriginalFilename());//获取文件名
        System.out.println(myFile.getSize());//获取文件大小
        System.out.println(myFile.isEmpty());//判断文件是否为空 如果没有上传文件或文件大小为0 这个值都是true


        //获取文件对应的字节数组
        byte[] buffFile=myFile.getBytes();
        //获取文件名
        String fileName=myFile.getOriginalFilename();
        Long fileSize=myFile.getSize();
        String fileType=myFile.getContentType();
        //可能会出现问题因为有些文件可能没有扩展名，因此必要时需要做逻辑控制
        String fileExtName=fileName.substring(fileName.lastIndexOf(".")+1);
        String[] result= FastDFSUtil.upload(buffFile,fileExtName);
        CreditorInfo creditorInfo=new CreditorInfo();
        creditorInfo.setId(id);
        creditorInfo.setFileSize(fileSize);
        creditorInfo.setFileType(fileType);
        creditorInfo.setOldFilename(fileName);
        creditorInfo.setGroupName(result[0]);
        creditorInfo.setRemoteFilePath(result[1]);
        creditorService.updateFileInfo(creditorInfo);
        model.addAttribute("message","文件上传成功，点击确定返回列表页面！");
        model.addAttribute("url","/");
        return "success";
    }

    /**
     * 完成文件下载
     * @param id  需要下载的文件主键
     * @return ResponseEntity 表示一个响应的实体，这个类是Spring提供的一个类，这个类是Spring响应数据时的一个对象
     *         这个对象用包含则响应时的编码例如404 200 等等，以及响应的头文件信息，以及响应时的具体数据
     *         这个数据可以是一段html代码，也可以是一段js，也可以是一段普通字符串，也可以是一个文件的流
     */
    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id){
        CreditorInfo creditorInfo=creditorService.selectById(id);
        byte [] buffFile=FastDFSUtil.download(creditorInfo.getGroupName(),creditorInfo.getRemoteFilePath());
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//设置响应类型为文件类型
        headers.setContentLength(creditorInfo.getFileSize());//设置响应时的文件大小用于自动提供下载进度
        //设置下载时的默认文件名
        headers.setContentDispositionFormData("attachment",creditorInfo.getOldFilename());
        /**
         * 创建响应实体对象，Spring会将这个对象返回给浏览器，作为响应数据
         * 参数 1 为响应时的具体数据
         * 参数 2 为响应时的头文件信息
         * 参数 3 为响应时的状态码
         */
        ResponseEntity<byte[]> responseEntity=new ResponseEntity<byte[]>(buffFile,headers, HttpStatus.OK);
        return responseEntity;
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        creditorService.deleteFileById(id);
        return "redirect:/";
    }

}
