package fatec.v2.unlockway.azure.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class BlobStorage {

    //TODO: Add your connection string here
    protected static final String  constr = "<your connection key here>";


    public static void removePhotoFromAzureBlobStorage(String filename, String containerName) {
        BlobClient blobClient = new BlobClientBuilder()
                .connectionString(constr)
                .containerName(containerName)
                .blobName(filename)
                .buildClient();
        try {
            blobClient.deleteIfExists();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    public static String storePhotoIntoAzureBlobStorage(MultipartFile photo, String containerName) {

        String filename = UUID.randomUUID().toString();

        BlobClient blobClient = new BlobClientBuilder()
                .connectionString(constr)
                .containerName(containerName)
                .blobName(filename)
                .buildClient();

        try {
            BlobHttpHeaders headers = new BlobHttpHeaders()
                    .setContentType(photo.getContentType());

            blobClient.uploadWithResponse(photo.getInputStream(), photo.getSize(), null, headers, null, null, null, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

}
