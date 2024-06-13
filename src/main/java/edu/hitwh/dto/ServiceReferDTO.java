package edu.hitwh.dto;

import edu.hitwh.entity.Service;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceReferDTO {
    private Long id;

    private String name;

    private String url;

    public ServiceReferDTO(Service service) {
        this.id = service.getId();
        this.name = "";
        if (service.getName() != null) {
            this.name = service.getName();
        }
        this.url = "";
        if (service.getUrl() != null) {
            this.url = service.getUrl();
        }
    }
}
