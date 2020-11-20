package br.com.communicart.backendserver.config.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.communicart.backendserver.service.PerfilService;

@Service
public class AWSS3ServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3ServiceImpl.class);
	 
	@Autowired
	private PerfilService perfilService;
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucket}")
    private String bucketName;
    
    @Async
    public URL uploadFile(final MultipartFile multipartFile, String header) {
        LOGGER.info("File upload in progress.");
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            URL fileURL = uploadFileToS3Bucket(bucketName, file);
            LOGGER.info("File upload is completed.");
            file.delete();  // To remove the file locally created in the project folder.
            perfilService.saveImage(header, fileURL);
            return fileURL;
        } catch (final AmazonServiceException ex) {
            LOGGER.info("File upload has failed.");
            LOGGER.error("Error= {} while uploading file.", ex.getMessage());
            return null;
        }
    }
    
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            LOGGER.error("Error converting the multi-part file to file= ", ex.getMessage());
        }
        return file;
    }
    
    private URL uploadFileToS3Bucket(final String bucketName, final File file) {
        final String uniqueFileName = System.currentTimeMillis() + "_" + file.getName();
        LOGGER.info("Uploading file with name= " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, uniqueFileName);
    }
}
