package uz.pdp.agrarmarket.model.attach;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@AllArgsConstructor
public class AttachmentDownloadDTO {
    private Resource resource;
    private String contentType;

}
