package app.service.menu;


import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by david on 20.06.16.
 */
public class MenuService {

    public static boolean saveMenu (final Part uploadedMenu) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(uploadedMenu.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
              //  String[] fields = line.split(",");
                // process fields here
                System.out.println("Line: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
