package com.spring.controller;


import java.util.Locale;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.spring.dao.EmployeeDao;
import com.spring.model.Employee;

/**
 * Handles requests for the application home page.
 */
@Controller
@Configuration
@ComponentScan(basePackages="com.spring")
@PropertySource(value ={"classpath:database.properties"})
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	EmployeeDao empDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		return "employee";
	}
	@RequestMapping(value = "insertData", method = RequestMethod.POST)
	public String insertData(@RequestParam("firstname")String firstname,
			@RequestParam("lastname")String lastname,
			@RequestParam("address")String address, Model model) throws  RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		logger.info("Welcome insertData()");
		logger.info("fname"+firstname);
		logger.info("fname"+lastname);
		logger.info("fname"+address);
		Employee empBean= new Employee();
		empBean.setFirstname(firstname);
		empBean.setLastname(lastname);
		empBean.setAddress(address);
		empBean.setDflag(1);
		empDao.addEmployee(empBean);
		logger.info("Welcome insertData()"+empBean);
		return "redirect:showAll";
	}
	@RequestMapping(value = "showAll", method = RequestMethod.GET)
	public String showAll(Locale locale, Model model) {
			logger.info("Welcome ShowData()");
		model.addAttribute("show", empDao.listemployee());
		for (Employee temp : empDao.listemployee()) {
			System.out.println("show  :::::"+temp.getId());
		}
		return "employeelist";
	}
	@RequestMapping(value = "editEmployee", method = RequestMethod.GET)
	public String editEmployee(@RequestParam("id")int id, Model model) {
		logger.info("id is"+id);
		model.addAttribute("editemp", empDao.getById(id));
		return "update";
	}
	@RequestMapping(value = "deleteEmployee", method = RequestMethod.GET)
	public String deleteEmployee(@RequestParam("id")int id, Model model) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		logger.info("id is"+id);
		 empDao.deleteEmp(id);
		return "redirect:showAll";
	}
	@RequestMapping(value = "updateEmployee", method = RequestMethod.POST)
	public String updateEmployee(@RequestParam("id")int id,
			@RequestParam("firstname")String firstname,
			@RequestParam("lastname")String lastname,
			@RequestParam("address")String address, Model model) throws  RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		logger.info("id is"+id);
		logger.info("fname"+firstname);
		logger.info("lastname"+lastname);
		logger.info("address"+address);
		Employee empBean = new Employee();
		empBean.setId(id);
		empBean.setFirstname(firstname);
		empBean.setLastname(lastname);
		empBean.setAddress(address);
		empBean.setDflag(1);
		 empDao.updateEmployee(empBean);
		return "redirect:showAll";
	}
	 @Bean
	    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	        return new PropertySourcesPlaceholderConfigurer();
	    }
	
}
