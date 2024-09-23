import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("hi")
    public String hi() {
        return "hi, authenticated user!";
    }

    @Secured({"ROLE_READ"})
    @GetMapping("read")
    public String read(){
        return "read";
    }

    @RolesAllowed({"ROLE_WRITE"})
    @GetMapping ("write")
    public String write() {
        return "write";
    }

    @PreAuthorize("hasAnyRole('WRITE', 'DELETE')")
    @GetMapping("write or delete")
    public String writeOrDelete() {
        return "write or delete";
    }

    @PreAuthorize("hasAnyRole('READ','WRITE','DELETE')")
    @GetMapping("hello")
    @ResponseBody
    public String hello(@RequestParam("username") String username) throws Exception {
        var authUser= SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authUser.equals(username)) {
            throw new Exception("Person is not allowed");
        }
        return "This is for username like "+username+" where authUser=  "+authUser;
    }

}