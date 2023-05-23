package hu.webuni.logistics;

import hu.webuni.logistics.service.InitDbService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class TransportControllerIT {

    @Autowired
    InitDbService initDbService;

    @BeforeEach
    void initDb() {
        initDbService.initDb();
    }
}
