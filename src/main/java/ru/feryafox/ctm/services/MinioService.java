package ru.feryafox.ctm.services;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@ApplicationScope
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    public MinioService(@Value("${minio.url}") String url,
                         @Value("${minio.accessKey}") String accessKey,
                         @Value("${minio.secretKey}") String secretKey) {
        this.minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String saveReport(String fileName, byte[] pdfBytes) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ByteArrayInputStream pdfInputStream = new ByteArrayInputStream(pdfBytes);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(pdfInputStream, pdfBytes.length, -1)
                        .contentType("application/pdf")
                        .build()
        );
        return String.format("%s/%s/%s", minioEndpoint, bucketName, fileName);
    }
}
