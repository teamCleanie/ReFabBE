package cleanie.refab.check;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final CheckEntityRepository repository;

    @PostMapping("/test")
    public CheckEntity createCheckEntity(@RequestParam String name) {
        return repository.save(new CheckEntity(name));
    }

    @GetMapping("/test")
    public List<CheckEntity> getCheckEntities() {
        return repository.findAll();
    }
}
