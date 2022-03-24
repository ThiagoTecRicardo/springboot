package demo.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerIndex {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

}
