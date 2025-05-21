package utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class IOTools {

    public static Stream<String> resourceToStream(Class<?> classContext, String resource) {
        URI uri = null;
        try {
            uri = classContext // current class: CityStreamDemo
                    .getResource(resource) // check if resource exists
                    .toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Resource does not exists", e);
        }
        Path path = Paths.get(uri);

        List<String> lines = null;
        // Java 7: try-with-resources
        try (
                // declared resources here:

                // 1 - binary stream
                InputStream inputStream = Files.newInputStream(path);

                // NB: binary read:
                //  byte[] buffer = new byte[1024];
                //  inputStream.read(buffer);
                //  System.out.println(Arrays.toString(buffer));

                // 2 - decompression
                GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

                // 3 - read as text with encoding
                Reader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);

                // NB: character read
                //        char[] buffer = new char[255];
                //        reader.read(buffer);
                //        System.out.println(Arrays.toString(buffer));

                // 4 - read line by line
                BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            lines = bufferedReader.lines().toList();
            // auto close at the end of this block
        } catch (IOException e){
            throw new RuntimeException("Error while reading resource", e);
        }
        return lines.stream();
        // NB: a better solution is to delay close of resources after finishing executing the pipeline with .onClose()
    }
}
