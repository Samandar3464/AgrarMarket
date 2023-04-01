package uz.pdp.agrarmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;
import uz.pdp.agrarmarket.repository.Address.CityRepository;
import uz.pdp.agrarmarket.repository.Address.DistrictRepository;
import uz.pdp.agrarmarket.repository.Address.ProvinceRepository;
import uz.pdp.agrarmarket.repository.AttachmentRepository;
import uz.pdp.agrarmarket.repository.PostCategoryRepository;
import uz.pdp.agrarmarket.repository.PostRepository;
import uz.pdp.agrarmarket.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class BaseTestConfiguration {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ProvinceRepository provinceRepository;

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected DistrictRepository districtRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PostCategoryRepository postCategoryRepository;

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected AttachmentRepository attachmentRepository;
    protected static final PostgreSQLContainer<?> postgres;
    private static final String IMAGE_NAME = "postgres:latest";


    static {
        postgres = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse(IMAGE_NAME))
                .withInitScript("sql/table-init.sql")
                .withExposedPorts(5432);
        postgres.withReuse(true);
        Startables.deepStart(postgres).join();
    }

    @DynamicPropertySource
    static void setUpPostgresConnectionProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.database", postgres::getDatabaseName);
    }
}
