package javapaint;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PaintingCanvas extends JPanel {

    private int brushSize = 8;
    private Color currentColor = Color.BLACK;
    private boolean isEraserMode = false;
    private boolean isRectangleMode = false;

    public PaintingCanvas() {
        setPreferredSize(new Dimension(800, 600));
    }

    public void draw(int x, int y) {
        // Get the graphics context of the panel
        Graphics graphics = getGraphics();

        if (isEraserMode) {
            // If eraser mode is enabled, set the color to the background color and draw a filled rectangle
            graphics.setColor(getBackground());
            int eraserSizeHalf = brushSize / 2;
            graphics.fillRect(x - eraserSizeHalf, y - eraserSizeHalf, brushSize, brushSize);
        } else {
            // If eraser mode is disabled, set the color to the current color and draw a filled oval or rectangle
            graphics.setColor(currentColor);
            int brushSizeHalf = brushSize / 2;

            if (isRectangleMode) {
                // If rectangle mode is enabled, draw a filled rectangle
                graphics.fillRect(x - brushSizeHalf, y - brushSizeHalf, brushSize, brushSize);
            } else {
                // If rectangle mode is disabled, draw a filled oval
                graphics.fillOval(x - brushSizeHalf, y - brushSizeHalf, brushSize, brushSize);
            }
        }
    }

    public void saveAsImage() {
        // Create a BufferedImage to store the content of the panel
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        print(graphics2D);

        // Create a file chooser dialog to select the save location and file name
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // If a location and file name are selected, save the image as a PNG file
            File fileToSave = fileChooser.getSelectedFile();
            try {
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(this, "Image saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
