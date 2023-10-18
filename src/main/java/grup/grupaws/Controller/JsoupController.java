package grup.grupaws.Controller;

import grup.grupaws.Entity.Product;
import grup.grupaws.Service.JsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@Transactional
public class JsoupController {

    @Autowired
    private JsoupService jsoupService;

    @PostMapping("/api/searchSubmit")
    public ResponseEntity<List<Product>> handleSubmit(@RequestBody String searchName) throws IOException {
        System.out.println("들어온 이름 :  " + searchName); //
        String stringWithQuotes = searchName;
        String stringWithoutQuotes = stringWithQuotes.replace("\"", "");
        System.out.println("이름 확인용 :  " + stringWithoutQuotes);
        jsoupService.searchPlant(stringWithoutQuotes);
        List<Product> entities = jsoupService.getProductsBySearchName(stringWithoutQuotes);

        return ResponseEntity.ok(entities);
    }
}
