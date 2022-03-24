package demo.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.springboot.model.Pessoa;
import demo.springboot.model.Telefone;
import demo.springboot.repository.PessoaRepository;
import demo.springboot.repository.TelefoneRepository;

@Controller
public class PessoalController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaobj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotações @NotEmpty e outras
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
			
		return andView;

	}
	
	@RequestMapping(method = RequestMethod.GET, value="/pessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/pessoa/{id}")
	public ModelAndView atualizar(@PathVariable("id") Long id ) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/removerPessoa/{id}")
	public ModelAndView deletar(@PathVariable("id") Long id ) {
		
		pessoaRepository.deleteById(id);

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/pessoas")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/telefones/{id}")
	public ModelAndView buscaTelefonesPessoa(@PathVariable("id") Long id ) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(id));
		return modelAndView;
		
	}
	
	@PostMapping("/telefone/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		
		telefoneRepository.save(telefone);
		
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return modelAndView;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/removerTelefone/{id}")
	public ModelAndView deletarTelefonePessoa(@PathVariable("id") Long id ) {
		
		Pessoa pessoa =  telefoneRepository.findById(id).get().getPessoa();
		telefoneRepository.deleteById(id);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		
		return modelAndView;
		
	}
	
	

}
