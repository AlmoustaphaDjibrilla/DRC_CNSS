package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
public class FichierControler {
    @Autowired
    private FichierService fichierService;

    @GetMapping("fichier/telecharger_fichier")
    public ResponseEntity<byte[]> telechargerFichier(@RequestParam Long idFichier){
        return fichierService.telechargerFichier(idFichier)
                .map(fichier -> {
                    HttpHeaders headers= new HttpHeaders();
                    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fichier.getNomFichier()+"\"");
                    headers.set(HttpHeaders.CONTENT_TYPE, fichier.getFileContentType());

                    return new ResponseEntity<>(fichier.getData(), headers, HttpStatus.OK);
                })
                .orElseGet( () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) );
    }
}
