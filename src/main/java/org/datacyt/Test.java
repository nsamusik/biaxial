package org.datacyt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test {

    public static void main(String [] args) throws Exception{
        DataTableModel dtm = new DataTableModel(new File("C:\\Users\\Nikolay Samusik\\Documents\\BM2_cct_normalized_07_non-Neutrophils_asinh.csv"));
        BiaxialPlot bp = new BiaxialPlot(dtm);
        BufferedImage bi = bp.getImage(3,7);
        ImageIO.write(bi,"PNG", new File("C:\\Users\\Nikolay Samusik\\Documents\\plot.png"));
    }
}
