package ivan.denysiuk.domain;


import lombok.*;

//@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusStation {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private Long id;
    private String stationName;
    private String accessibility;
    private String GPSLong;
    private String GPSLat;





}
