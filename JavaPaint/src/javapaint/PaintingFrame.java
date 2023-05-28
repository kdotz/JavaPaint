package javapaint;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.colorchooser.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PaintingFrame extends JFrame {

    private PaintingCanvas canvas;
    private int brushSize = 8;
    private Color currentColor = Color.BLACK;
    private boolean isEraserMode = false;
    private boolean isRectangleMode = false;

    public PaintingFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Painting Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create an instance of the PaintingCanvas
        canvas = new PaintingCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // When the mouse is pressed, call the draw method on the canvas to perform drawing
                canvas.draw(e.getX(), e.getY());
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // When the mouse is dragged, call the draw method on the canvas to perform continuous drawing
                canvas.draw(e.getX(), e.getY());
            }
        });

        // Create the brush size slider
        JSlider brushSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, brushSize);
        brushSizeSlider.setMajorTickSpacing(5);
        brushSizeSlider.setMinorTickSpacing(1);
        brushSizeSlider.setPaintTicks(true);
        brushSizeSlider.setPaintLabels(true);
        brushSizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // When the value of the brush size slider changes, update the brush size
                brushSize = brushSizeSlider.getValue();
            }
        });

        // Create the eraser mode button
        JButton eraserButton = new JButton("Eraser Mode");
        eraserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the eraser mode when the button is clicked
                isEraserMode = !isEraserMode;
                if (isEraserMode) {
                    eraserButton.setText("Drawing Mode");
                } else {
                    eraserButton.setText("Eraser Mode");
                }
            }
        });

        // Create the color chooser button
        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show a color chooser dialog to select a new color
                Color newColor = JColorChooser.showDialog(canvas, "Choose Color", currentColor);
                if (newColor != null) {
                    // Update the current color if a color is selected
                    currentColor = newColor;
                }
            }
        });

        // Create the options panel to hold the components
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        optionsPanel.add(new JLabel("Brush Size:"));
        optionsPanel.add(brushSizeSlider);
        optionsPanel.add(eraserButton);
        optionsPanel.add(colorButton);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        // Create the rectangle mode menu item
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle Mode");
        rectangleMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the rectangle mode when the menu item is selected
                isRectangleMode = !isRectangleMode;
                rectangleMenuItem.setSelected(isRectangleMode);
            }
        });
        optionsMenu.add(rectangleMenuItem);

        // Create the save as image menu item
        JMenuItem saveMenuItem = new JMenuItem("Save as Image");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call the saveAsImage method on the canvas to save the drawing as an image
                canvas.saveAsImage();
            }
        });
        optionsMenu.add(saveMenuItem);

        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(canvas, BorderLayout.CENTER);
        contentPane.add(optionsPanel, BorderLayout.SOUTH);
    }
}
