package ikea.baldemarfilabdulay_myikea.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ikea.baldemarfilabdulay_myikea.models.Producto;
import ikea.baldemarfilabdulay_myikea.repositories.ProductosRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductosRepository pr;
    
    public void Create(Producto newProducto){
        pr.saveAndFlush(newProducto);
    }
    
    public List<Producto> ReadAll(){
        Iterable<Producto> iterProducto = pr.findAll();
        
        List<Producto> listProducto = StreamSupport
        .stream(iterProducto.spliterator(), false)
        .toList();
        
        return listProducto;
    }
    
    public Producto ReadSingle(Integer idProducto){
        return GetProducto(idProducto);
    }
    
    public void Update(Producto productoUpdate){
        pr.saveAndFlush(productoUpdate);
    }
    
    public void Delete(Integer idProducto){
        pr.deleteById(idProducto);
        pr.flush();
    }
    
    //Métodos Secundarios (Exist+Get+Save)
    public Boolean Exist(Integer idProducto){
        return pr.existsById(idProducto);
    }
    
    public Producto GetProducto(Integer idProducto){
        Producto productoRead = null;
        Optional<Producto> productoFound = pr.findById(idProducto);
        
        if (productoFound.isPresent()) {
            productoRead = productoFound.get();
        }
        
        return productoRead;
    }
    
    public void SaveEntity(Producto producto) {
        pr.saveAndFlush(producto);
    }
    
    public void SaveAllEntity(List<Producto> productoList) {
        pr.saveAllAndFlush(productoList);
    }
}
