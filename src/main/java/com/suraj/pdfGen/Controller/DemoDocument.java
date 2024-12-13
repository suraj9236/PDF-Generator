package com.suraj.pdfGen.Controller;

import com.suraj.pdfGen.Service.DocumentGenerator;
import com.suraj.pdfGen.mapper.DataMapper;
import com.suraj.pdfGen.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DemoDocument {
    @Autowired
    private DocumentGenerator documentGenerator;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private DataMapper dataMapper;

    @PostMapping("/create")
    public String generateDocument(@RequestBody List<Employee> employeeList){
        String finalHtml=null;
        Context dataContext = dataMapper.setData(employeeList);
        finalHtml=springTemplateEngine.process("template",dataContext);
        documentGenerator.htmlToPdf(finalHtml);
        return "success";
    }

}
