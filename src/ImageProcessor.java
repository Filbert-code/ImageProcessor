/*
 * This class takes a file of RGB color values as input and outputs the image produced from adding all the pixels
 * to a canvas.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    ImageProcessor class that takes a file of RGB color values and outputs an image in .PNG format.
 */
public class ImageProcessor {
    public static void main(String[] args) throws IOException {
        // returns an array of rows, each containing RGB values corresponding to the color of the pixels in that row
        ArrayList<String> dataRows = fileInput();
        // get the number of rows and columns
        int row_num = Integer.parseInt(dataRows.get(0).split(" ")[0]) - 1;
        int col_num = Integer.parseInt(dataRows.get(0).split(" ")[1]);
        // a BufferedImage instance used to add the pixel data onto and save as the output
        BufferedImage canvas = painter(dataRows, row_num, col_num);
        // create the output .PNG file
        ImageIO.write(canvas, "PNG", new File("image.png"));
    }

    /*
        Returns a BufferedImage object with the pixels set at the RGB values corresponding to the
        input file data. The 3 RGB values per pixel are converted to a single 32-bit integer prior
        to setting the pixel on the BufferedImage object.
     */
    public static BufferedImage painter(ArrayList<String> dataRows, int row_num, int col_num) {
        BufferedImage canvas = new BufferedImage(col_num, row_num, BufferedImage.TYPE_INT_RGB);
        for(int row = 1; row < row_num; row++) {
            // grab one row of the RGB color data
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
                // set the pixel colors at the (x,y) positions on the BufferedImage object
                canvas.setRGB(col, row, rgbBitShifter(red, green, blue));
            }
        }
        return canvas;
    }

    /*
        Bitwise operations to combine the 3 given RGB values into a single 32-bit integer.
        The 4th 8-bit number represents the pixel's Alpha value.
     */
    public static int rgbBitShifter(int r, int g, int b) {
        int a = 255;
        int alpha = (a & 0xFF) << 24;
        int red = (r & 0xFF) << 16;
        int green = (g & 0xFF) << 8;
        int blue = (b & 0xFF) << 0;
        return red | green | blue | alpha;
    }

    /*
        Returns an ArrayList of Strings; representing the rows of the input file.
     */
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
        }
        return dataRows;
    }
}
