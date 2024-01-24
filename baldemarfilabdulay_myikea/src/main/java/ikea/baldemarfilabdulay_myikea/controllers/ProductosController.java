package ikea.baldemarfilabdulay_myikea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ikea.baldemarfilabdulay_myikea.models.Producto;
import ikea.baldemarfilabdulay_myikea.services.ProductoService;

@Controller
public class ProductosController {
    @Autowired
    private ProductoService prS;
    
    @GetMapping("/") /* Descartar this.Index*/
    public String Index(){
        return "index";
    }
    
    @GetMapping("/productos/create")
    public String Create(Model model){
        model.addAttribute("emptyProducto", new Producto());
        return "/productos/create";
    }

    @PostMapping("/productos/create")
    public String Create(@ModelAttribute("emptyProducto") Producto productoSave, BindingResult result){
        if (result.hasErrors()) {
            return "/create";
        }

        prS.Create(productoSave);

        return "redirect:/productos/list";
    }
    
    @GetMapping("/productos/list")/*Read All*/
    public String ReadAll(Model model){
        List<Producto> productoList = prS.ReadAll();
        model.addAttribute("productoList", productoList);

        return "productos/list";
    }
    
    @GetMapping("/productos/single/{idProducto}")/*Read One*/
    public String ReadSingle(@PathVariable Integer idProducto, Model model){
        Producto productoSingle = prS.GetProducto(idProducto);

        if (!(prS.Exist(idProducto))) {
            return "productos/error";
        }

        model.addAttribute("productoSingle", productoSingle);
        return "productos/single";
    }
    
    //Get + Post method
    @GetMapping("/productos/update/{idProducto}")
    public String Update(@PathVariable Integer idProducto, Model model){/*Update*/
        if (!prS.Exist(idProducto)) {
            return "productos/error";
        }

        Producto productoUpdate = prS.GetProducto(idProducto);
        model.addAttribute("productoUpdate", productoUpdate);
        
        return "productos/update";
    }
    
    @PostMapping("/productos/update")
    public String Update(@ModelAttribute("productoUpdate") Producto myProductoModel, BindingResult result){/*Update*/
        if (!result.hasErrors()) {
            prS.Update(myProductoModel);
            return "redirect:/productos/list";
        }

        return "productos/update/" + myProductoModel.getProduct_id();
    }
    
    //Get + Post method
    @GetMapping("/productos/delete/{idProducto}")
    public String Delete(@PathVariable Integer idProducto, Model model){/*Delete*/
        //¿Error al Encontrar un Animal?
        if (!(prS.Exist(idProducto))){
            return "/productos/error";
        }

        Producto productoDelete = prS.GetProducto(idProducto);
        model.addAttribute("productoDelete", productoDelete);
        
        return "productos/delete";
    }

    @PostMapping("/productos/delete")
    public String Delete(@ModelAttribute("productoDelete") Producto myProductoModel, BindingResult result){/*Delete*/
        //¿Otro Error?
        if (result.hasErrors()) {
            return "productos/delete";
        }

        prS.Delete(myProductoModel.getProduct_id());

        return "redirect:/productos/list";
    }

}