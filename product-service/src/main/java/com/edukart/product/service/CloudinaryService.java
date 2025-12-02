package com.edukart.product.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.edukart.product.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    // This would be helpful in uploading file and zip file.
    public File uploadFile(MultipartFile file) throws IOException {
        Map uploadFile = cloudinary
                .uploader()
                .upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "folder", "edukart"
                        )
                );
        return File
                .builder()
                .publicId((String) uploadFile.get("public_id"))
                .secureUrl((String) uploadFile.get("secure_url"))
                .resourceType((String) uploadFile.get("resource_type"))
                .build();
    }

    public Map removeFile(String publicId, String resourceType) throws IOException {
        return cloudinary
                .uploader()
                .destroy(publicId, ObjectUtils.asMap(
                        "resource_type", resourceType,
                        "invalidate", true
                ));
    }
}
