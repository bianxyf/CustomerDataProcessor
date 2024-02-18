package main;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static main.Client.callCustomerAPI;

/**
 * <p>
 *  This is the Main class for the CustomerDataProcessor that processes the files under the
 * /resources/unprocessed folder. It parses the CSV file and sends each customer information
 * to the CustomerAPI that will save the details into the internal Database - this can be
 * replaced to be any SQL/Postgres Database (but to keep the coding simple we've left that for now)
 * </p>
 * <p>
 *  CSV file format:
 *      <ul>
 *      <li>1st line of the file is the header for attributes name separated by pipes (|)</li>
 *      <li>2nd line onwards will be the actual customer information separated by comma (,)</li>
 *      </ul>
 * </p>
 * <p>
 * See sample format below:
 *      customerRef|firstName|lastName|addressLine1|addressLine2|town|county|country|postCode
 *      12345,John,Smith,40 Evergreen Road,Baguley,Wythenshawe,Greater Manchester,United Kingdom,M23 3DF
 * </p>
 * <p>
 *  Once the processing is completed, the file is moved to the processed folder (under resources)
 * </p>
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String DELIMITER = ",";

    public static void main(String[] args) {
        Path filesToProcess = Path.of("src/resources/unprocessed");
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(filesToProcess)) {
            for (Path path : dirStream) {
                List<String> customerList = Files.readAllLines(path);
                customerList.forEach( e -> {
                    if (e.contains(DELIMITER)) {
                        Customer customer = new Customer(e.split(DELIMITER));
                        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                        try{
                            String json = ow.writeValueAsString(customer);
                            callCustomerAPI(json);
                        }catch(JsonProcessingException jpe){
                            LOGGER.severe(STR."JsonProcessingException encountered while processing customer: \{customer.getCustomerReference()} \{customer.getFirstName()} \{customer.getLastName()}");
                        }
                    }
                });
                fileProcessingCompleted(path);
            }
        }catch(IOException e){
            LOGGER.severe("IOException Encountered while reading the file");
        }
    }

    /**
     * This method is called once the file have been processed - it moves the processed file
     * from the unprocessed folder to the processed folder - to avoid re-processing the same file.
     * This function is NOT called when there are exceptions accessing the directory
     * or reading the contents of the file.
     *
     * @param path - Path of the file that was recently processed.
     */
    public static void fileProcessingCompleted(Path path){
        //Assume processing have been completed - move file to completed folder
        Path destinationDir = Paths.get("src/resources/processed");
        Path d2 = destinationDir.resolve(path.getFileName());
        LOGGER.info(STR."destination File=\{d2}");
        try{
            Files.move(path, d2, REPLACE_EXISTING);
        } catch(IOException ie){
            LOGGER.severe("IOException Encountered while moving the file to processed folder.");
        }
    }
}


