import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageProcessor {

    // Convert the original image to grayscale
    public static BufferedImage toGray(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage grayImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int rgb = image.getRGB(i, j);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int gray = (r + g + b) / 3;  
                int newRGB = (gray << 16) | (gray << 8) | gray;

                grayImage.setRGB(i, j, newRGB);
            }
        }
        return grayImage;
    }

    // Brightness enhancer (+ve = brighter, -ve = darker)
    public static BufferedImage brighten(BufferedImage image, int amount) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int rgb = image.getRGB(x, y);

                int r = ((rgb >> 16) & 0xFF) + amount;
                int g = ((rgb >> 8) & 0xFF) + amount;
                int b = (rgb & 0xFF) + amount;

                r = Math.max(0, Math.min(255, r));
                g = Math.max(0, Math.min(255, g));
                b = Math.max(0, Math.min(255, b));

                int newRGB = (r << 16) | (g << 8) | b;
                output.setRGB(x, y, newRGB);
            }
        }
        return output;
    }

    public static void main(String[] args) {
        try {
            // Your exact image path
            File input = new File("C:\\Users\\JCS\\Desktop\\Image processing java\\src\\flower.jpeg");

            System.out.println("Reading image from: " + input.getAbsolutePath());
            System.out.println("Exists? " + input.exists());

            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                System.out.println("Error: Could not read image.");
                return;
            }

            // Grayscale conversion
            BufferedImage gray = toGray(image);
            File outGray = new File("C:\\Users\\JCS\\Desktop\\Image processing java\\src\\output_gray.jpg");
            ImageIO.write(gray, "jpg", outGray);
            System.out.println("Grayscale saved: " + outGray.getAbsolutePath());

            // Brightness +40
            BufferedImage bright = brighten(gray, 40);
            File outBright = new File("C:\\Users\\JCS\\Desktop\\Image processing java\\src\\output_bright.jpg");
            ImageIO.write(bright, "jpg", outBright);
            System.out.println("Brightened image saved: " + outBright.getAbsolutePath());

            System.out.println("Processing completed successfully!");

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}
