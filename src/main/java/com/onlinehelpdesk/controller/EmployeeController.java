package com.onlinehelpdesk.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinehelpdesk.helpers.FileHelper;
import com.onlinehelpdesk.repositories.YeucauRepository;
import com.onlinehelpdesk.entities.Douutien;
import com.onlinehelpdesk.entities.Nhanvien;
import com.onlinehelpdesk.entities.Yeucau;
import com.onlinehelpdesk.services.DouutienService;
import com.onlinehelpdesk.services.NhanvienService;
import com.onlinehelpdesk.services.YeucauService;

@Controller
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	private NhanvienService nhanvienService;
	@Autowired
	private YeucauService yeucauService;
	@Autowired
	private DouutienService douutienService;



	@RequestMapping(value = { "index" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap, Principal principal) {
		String username = principal.getName();
		Nhanvien employee = nhanvienService.findbyusername(username);
		modelMap.put("yeucaus", yeucauService.findYeucaubyemployee(employee));
		modelMap.put("douutiens", douutienService.findall());
		modelMap.put("username", username);
		return "employee/index";
	}

	@RequestMapping(value = { "searchbydate/{username}" }, method = RequestMethod.GET)
	public String Searchbydate(ModelMap modelMap, @PathVariable("username") String username,
			@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
			@RequestParam("douutien") int madouutien) {
		Nhanvien nv = nhanvienService.findbyusername(username);
		modelMap.put("name", nv);

		Douutien douutien = douutienService.finddouutien(madouutien);
		List<Douutien> douutiens = (List<Douutien>) douutienService.findall();
		modelMap.put("douutiens", douutiens);
		
		if (from == null && to == null) {
			modelMap.put("yeucaus", yeucauService.findYeucauBydouutiennvg(nv, douutien));

		} else {
			modelMap.put("yeucaus", yeucauService.findYeucauByDate(nv, douutien, from, to));
		}
		
		return "employee/index";
	}

	@RequestMapping(value = { "add" }, method = RequestMethod.GET)
	public String Add(ModelMap modelMap) {
		Yeucau yeucau = new Yeucau();
		modelMap.put("yeucau", yeucau);
		modelMap.put("douutiens", douutienService.findall());
		return "employee/add";
	}

	@RequestMapping(value = { "add" }, method = RequestMethod.POST)
	public String Add(@ModelAttribute("yeucau") Yeucau yeucau, Principal principal, RedirectAttributes redirectAttributes) {
		try {
			yeucau.setNhanvienByNvXuly(null);
			String username= principal.getName();
			Nhanvien employee = nhanvienService.findbyusername(username);
			yeucau.setNhanvienByNvGui(employee);
			if (yeucauService.save(yeucau)) {
				redirectAttributes.addFlashAttribute("msg", "ok");

			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
				return "redirect:/employee/add";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/employee/index";
	}

}
