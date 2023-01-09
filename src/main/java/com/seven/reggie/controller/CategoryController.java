package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.CategoryEntity;
import com.seven.reggie.entity.Page;
import com.seven.reggie.servce.CategoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:15:01
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired(required = false)
    private CategoryService categoryService;

    @PostMapping
    private R add(@RequestBody CategoryEntity categoryEntity, HttpSession session){
        //从session中获取登录者得id
        Long emploId = (Long) session.getAttribute("EmployeeEntity");
        categoryEntity.setCreateUser(emploId);  //创建人
        categoryEntity.setUpdateUser(emploId);  //修改人
        R result =categoryService.add(categoryEntity);
        return result;
    }

    //分页
    @GetMapping("/page")
    public R page(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5") Integer pageSize){
       R<Page> result= categoryService.page(page,pageSize);
        return result;
    }

    //修改分类
    @PutMapping
    public R update(@RequestBody CategoryEntity categoryEntity,HttpSession session){
        Long emploId= (Long) session.getAttribute("EmployeeEntity");
        categoryEntity.setUpdateUser(emploId);
        categoryService.update(categoryEntity);
        return R.success("修改成功!");
    }

    //删除数据
    @DeleteMapping
    public R deleteById(Long id){
        R result=categoryService.deleteById(id);
        return result;
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        //通知浏览器处理内容的时候要以文件下载形式去处理。通过响应头去设置
        response.setHeader("content-disposition","attachment;filename=category.xlsx");
        //1. 读取模板的输入流
        InputStream asStream = CategoryController.class.getResourceAsStream("/excel/cateory.xlsx");
        //2. 通过输入流构建工作簿
        Workbook workbook = new XSSFWorkbook(asStream);
        //3. 通过工作薄得到工作单
        Sheet sheet = workbook.getSheetAt(0);
        //4. 读取第二行得样式并储存到集合
        List<CellStyle> cellStyles=new ArrayList<>();
        Row row = sheet.getRow(1);
        for (int i = 1; i <= 5; i++) {
            Cell cell = row.getCell(i);
            CellStyle cellStyle = cell.getCellStyle();
            cellStyles.add(cellStyle);
        }
        //5.查询所有类别信息
        List<CategoryEntity> categoryEntityList=categoryService.findAll();
        //6. 遍历类别所有信息，每一行类别信息就是每一行数据，并且设置样式
        for (int i = 0; i < categoryEntityList.size(); i++) {
            CategoryEntity categoryEntity = categoryEntityList.get(i);
            row = sheet.createRow(i + 1);
            Cell cell = row.createCell(1);
            cell.setCellValue(categoryEntity.getType());
            cell.setCellStyle(cellStyles.get(0));

            cell = row.createCell(2);
            cell.setCellValue(categoryEntity.getName());
            cell.setCellStyle(cellStyles.get(1));

            cell = row.createCell(3);
            cell.setCellValue(categoryEntity.getSort());
            cell.setCellStyle(cellStyles.get(2));

            DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            cell = row.createCell(4);
            cell.setCellValue(dateTimeFormatter.format(categoryEntity.getCreateTime()));
            cell.setCellStyle(cellStyles.get(3));

            cell = row.createCell(5);
            cell.setCellValue(dateTimeFormatter.format(categoryEntity.getUpdateTime()));
            cell.setCellStyle(cellStyles.get(4));

        }
        //7. 把工作薄写到浏览器的输出流上
        workbook.write(response.getOutputStream());
    }

    @GetMapping("/list")
    public R list(Integer type){
       R result = categoryService.list(type);
        return result;
    }
}
