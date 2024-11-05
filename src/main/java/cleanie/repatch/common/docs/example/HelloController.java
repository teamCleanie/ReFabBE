package cleanie.repatch.common.docs.example;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<?> hello(@RequestParam(required = false) String name) {
        String message = name == null ? "World" : name;
        return ResponseEntity.ok(new HelloResponse("Hello, " + message + "!"));
    }

    @PostMapping("/hello")
    public ResponseEntity<?> createHello(@RequestBody HelloRequest request) {
        return ResponseEntity.ok(new HelloResponse("Hello, " + request.name() + "!"));
    }
}

record HelloResponse(String message) {}
record HelloRequest(String name) {}
