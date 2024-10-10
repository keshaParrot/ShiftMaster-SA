package ivan.denysiuk;

import ivan.denysiuk.domain.dto.CargoBusDTO;
import ivan.denysiuk.domain.entity.*;
import ivan.denysiuk.domain.mapper.VehicleMapperManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        /*CargoBus cargoBus = CargoBus.builder()
                .id(5L)
                .brand("as")
                .deployDate(LocalDateTime.now())
                .lastTimeMaintenance(LocalDateTime.MIN)
                .loadCapacity("25")
                .location("s")
                .registrationNumber("2w")
                .serialNumber("213")
                .build();

        CargoBusDTO dto = VehicleMapperManager.convertEntityToDto(cargoBus);
        System.out.println(cargoBus);
        System.out.println(dto);*/


    }
}
