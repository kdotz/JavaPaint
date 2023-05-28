package javapaint;
import javax.swing.SwingUtilities;

public class PaintingProgram {
    public static void main(String[] args) {
    	
        // Start the program on the event dispatch thread (handle GUI events)
        SwingUtilities.invokeLater(() -> {
            // Create an instance of the PaintingFrame and make it visible
            PaintingFrame program = new PaintingFrame();
            program.setVisible(true);
        });
    }
}
