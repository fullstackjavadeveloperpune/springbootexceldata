package com.fullstack.service;

import com.fullstack.model.Employee;
import com.fullstack.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveFileData(InputStream file) throws IOException {
        List<Employee> employeeList = new LinkedList<>();
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.forEach(row -> {
            Employee employee = new Employee();

            if (row.getRowNum() != 0) {
                employee.setEmpName(row.getCell(0).getStringCellValue());
                employee.setEmpSalary(row.getCell(1).getNumericCellValue());
                employeeList.add(employee);
            }
        });

        employeeRepository.saveAll(employeeList);

    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
