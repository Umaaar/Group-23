import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    public List<Contact> findAll() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    public Contact findById(@PathVariable Long id) {
        return contactService.findById(id);
    }

    @PostMapping("/")
    public Contact save(@RequestBody Contact contact) {
        return contactService.save(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        contactService.deleteById(id);
    }
}
