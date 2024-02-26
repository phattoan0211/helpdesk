package com.onlinehelpdesk.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
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
@RequestMapping("nhanvien")
public class NhanvienController {

	@Autowired
	private NhanvienService nhanvienService;
	@Autowired
	private YeucauService yeucauService;
	@Autowired
	private DouutienService douutienService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@RequestMapping(value = { "searchbydate" }, method = RequestMethod.GET)
	public String Searchbydate(ModelMap modelMap, @RequestParam("username") String username,
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

		return "nhanvien/yeucau";
	}

	@RequestMapping(value = { "add/{mayeucau}" }, method = RequestMethod.GET)
	public String Add(ModelMap modelMap, @PathVariable("mayeucau") int mayeucau) {
		modelMap.put("supporters", nhanvienService.finduserbyrole(2));
		modelMap.put("yeucau", yeucauService.findbyId(mayeucau));
		return "nhanvien/add";
	}

	@RequestMapping(value = { "add" }, method = RequestMethod.POST)
	public String Add(@ModelAttribute("yeucau") Yeucau yeucau, RedirectAttributes redirectAttributes) {
		try {
			if (yeucauService.save(yeucau)) {
				redirectAttributes.addFlashAttribute("msg", "ok");

			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
				return "redirect:/nhanvien/yeucau/" + yeucau.getNhanvienByNvGui().getUsername();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/nhanvien/yeucau/" + yeucau.getNhanvienByNvGui().getUsername();
	}

	@RequestMapping(value = { "yeucau/{username}" }, method = RequestMethod.GET)
	public String Yeucau(ModelMap modelMap, @PathVariable("username") String username) {
		Nhanvien nv = nhanvienService.findbyusername(username);
		modelMap.put("name", nv);
		List<Douutien> douutiens = (List<Douutien>) douutienService.findall();
		modelMap.put("douutiens", douutiens);
		modelMap.put("yeucaus", yeucauService.findYeucauByNhanvien(nv));
		return "nhanvien/yeucau";
	}

	@RequestMapping(value = { "", "index", "/" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("nhanviens", nhanvienService.findall());

		return "nhanvien/index";
	}

	@RequestMapping(value = { "listyeucau" }, method = RequestMethod.GET)
	public String Listyeucau(ModelMap modelMap) {
		modelMap.put("yeucaus", yeucauService.findnew());
		modelMap.put("douutiens", douutienService.findall());
		return "nhanvien/listyeucau";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, ModelMap modelMap,
			Authentication authentication) {
		if (authentication != null) {

			return "nhanvien/accessdenied";
		} else {
			if (error != null) {
				modelMap.put("msg", "Tai khoan khong hop le");
			}
			if (logout != null) {
				modelMap.put("msg", "Dang xuat thanh cong");
			}
			return "nhanvien/login";
		}
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap modelMap) {
		Nhanvien nv = new Nhanvien();
		modelMap.put("nhanvien", nv);
		return "nhanvien/register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(@ModelAttribute("nhanvien") Nhanvien nv, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		nv.setPassword(encoder.encode(nv.getPassword()));
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				nv.setHinhanh(filename);
			} else {
				nv.setHinhanh("noimages.jpg");
			}
			if (nhanvienService.save(nv)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail");
				return "redirect:/nhanvien/register";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/nhanvien/index";

	}

	@RequestMapping(value = "accessdenied", method = RequestMethod.GET)
	public String accessdenied() {
		return "nhanvien/accessdenied";
	}

	@RequestMapping(value = { "update/{username}" }, method = RequestMethod.GET)
	public String update(ModelMap modelMap, @PathVariable("username") String username, Authentication authentication) {
		username = authentication.getName();
		Nhanvien nv = nhanvienService.findbyusername(username);
		modelMap.put("nhanvien", nv);
		return "nhanvien/update";

	}

	@RequestMapping(value = { "update" }, method = RequestMethod.POST)
	public String update(@ModelAttribute("nhanvien") Nhanvien nv, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {

		try {

			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				nv.setHinhanh(filename);
			}

			//
			if (nv.getPassword().isEmpty()) {
				nv.setPassword(nhanvienService.getpassword(nv.getUsername()));
			} else
				nv.setPassword(encoder.encode(nv.getPassword()));

			if (nhanvienService.save(nv)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
				return "nhanvien/update";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "redirect:/nhanvien/login";
		return "redirect:/nhanvien/logout";

	}
}
