package fatec.v2.unlockway.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitializer {

    public void initialize() throws IOException {
        try {
            // Load the resource using ClassPathResource
            // TODO: Export the firebase connection JSON and put it in the root of this project.
            Resource resource = new ClassPathResource("<ex: firebase_conn.json>");

            // Get the File object from the resource
            File file = resource.getFile();

            FileInputStream serviceAccount = new FileInputStream(file);

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
