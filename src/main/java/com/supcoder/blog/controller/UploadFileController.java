package com.supcoder.blog.controller;

import com.supcoder.blog.util.ExcelUtil;
import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UploadFileController {

    @GetMapping("/uploadHtml")
    public String uploadHtml() {
        return "/uploadFile";
    }

    /**
     * 处理文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    public @ResponseBody
    String uploadImg(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "false";
        }
        //获取名字
        String fileName = file.getOriginalFilename();

        String path = "/Users/lee/Documents/JavaProgram/blog/file";
        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        //保存文件
        try {
            file.transferTo(dest);
            return "上传成功!";
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "上传失败!";
        }
    }

    @PostMapping(value = "/postImgFile")
    public @ResponseBody
    String postFile(HttpServletRequest request) {
        String result = "";
        ServletInputStream is = null;
        FileOutputStream fos = null;
        try {
            File file = new File("/Users/lee/Documents/JavaProgram/blog/file/imgs", "up.png");
            fos = new FileOutputStream(file);
            is = request.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            result = "SUCCESS";
        } catch (IOException e) {
            e.printStackTrace();
            result = "ERROR";
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 多文件上传(包括一个)
     *
     * @param files
     * @return
     */
    @PostMapping(value = "/uploadImgs")
    public @ResponseBody
    String uploadImg(@RequestParam("file") List<MultipartFile> files) {
        StringBuilder result = new StringBuilder();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return "false";
            }
            //获取名字
            String fileName = file.getOriginalFilename();
            String path = "F:/SpringBootFiles/imgs/";
            File dest = new File(path + "/" + fileName);
            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            //保存文件
            try {
                file.transferTo(dest);
                result.append(fileName + "上传成功!\n");
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                result.append(fileName + "上传失败!\n");
            }
        }
        return result.toString();
    }




    /**
     * 处理文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/parseExcel")
    public @ResponseBody
    JsonResult parseExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtil.error("上传文件不能为空");
        }
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        String filename = file.getOriginalFilename();
        String columns[] = {"name", "age", "score", "niss", "qqq"};

        String extString = filename.substring(filename.lastIndexOf("."));
        InputStream is = null;
        try {
            is = file.getInputStream();
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int column = row.getPhysicalNumberOfCells();
            for (int i = 1; i < rowNum; i++) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < column; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(row.getCell(j));
                        map.put((String) ExcelUtil.getCellFormatValue(sheet.getRow(0).getCell(j)), cellData);
                    }
                } else {
                    break;
                }
                list.add(map);
            }
        }
        //遍历解析出来的list
        for (Map<String, String> map : list) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.print(entry.getKey() + ":" + entry.getValue() + ",");
            }
            System.out.println();
        }

        return ResultUtil.success(list);
    }


    /**
     * 上传Excel
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadExcel")
    public @ResponseBody
    String uploadExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "false";
        }
        //获取名字
        String fileName = file.getOriginalFilename();
        String path = "/Users/lee/Documents/JavaProgram/blog/file";
        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        //保存文件
        try {
            file.transferTo(dest);
            return "上传成功!";
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "上传失败!";
        }
    }

}
