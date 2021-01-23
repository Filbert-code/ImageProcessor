import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ImageProcessor {
    public static void main(String[] args) throws IOException {
        // returns an array of rows, each containing RGB values corresponding to the color of the pixels in that row
        ArrayList<String> dataRows = fileInput();
        // get the number of rows and columns
        int row_num = Integer.parseInt(dataRows.get(0).split(" ")[0]) - 1;
        int col_num = Integer.parseInt(dataRows.get(0).split(" ")[1]);

        BufferedImage canvas = painter(dataRows, row_num, col_num);

        ImageIO.write(canvas, "PNG", new File("image.png"));
    }

    public static BufferedImage painter(ArrayList<String> dataRows, int row_num, int col_num) {
        BufferedImage canvas = new BufferedImage(col_num, row_num, BufferedImage.TYPE_INT_RGB);
        for(int row = 1; row < row_num; row++) {
            String row_string = dataRows.get(row);
            String[] row_val_array = row_string.split(" ");

            for(int col = 0; col < col_num; col++) {

                // get the indices of the commas in the strings
                int first_comma_ind = row_val_array[col].indexOf(",");
                int second_comma_ind = row_val_array[col].indexOf(",", first_comma_ind + 1);
                // get rbg color int values from the row_val_array Strings
                int red = Integer.parseInt(row_val_array[col].substring(0,first_comma_ind));
                int green = Integer.parseInt(row_val_array[col].substring(first_comma_ind+1,second_comma_ind));
                int blue = Integer.parseInt(row_val_array[col].substring(second_comma_ind+1));

                canvas.setRGB(col, row, rgbBitShifter(red, green, blue));
            }
        }
        return canvas;
    }

    public static int rgbBitShifter(int r, int g, int b) {
        int a = 255;
        int alpha = (a & 0xFF) << 24;
        int red = (r & 0xFF) << 16;
        int green = (g & 0xFF) << 8;
        int blue = (b & 0xFF) << 0;
        return red | green | blue | alpha;
    }

    public static ArrayList<String> fileInput() {
        ArrayList<String> dataRows = new ArrayList<>();
        try{
            File f = new File("image.dat");
            Scanner r = new Scanner(f);
            while(r.hasNextLine()) {
                dataRows.add(r.nextLine());
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataRows;
    }
}
