package hongsta;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UploadPanel extends JPanel {

    private final MainFrame mainFrame;

    private final JTextArea contentArea = new JTextArea(5, 20);
    private final JLabel previewLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton uploadBtn = new JButton("업로드");
    private final JButton deleteImgBtn = new JButton("사진 제거");

    private File selectedFile = null;

    public UploadPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JButton selectBtn = new JButton("사진 선택");
        uploadBtn.setEnabled(false);
        deleteImgBtn.setEnabled(false);

        selectBtn.addActionListener(e -> selectImage());
        uploadBtn.addActionListener(e -> upload());
        deleteImgBtn.addActionListener(e -> clearImage());

        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(previewLabel, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        btns.add(selectBtn);
        btns.add(deleteImgBtn);
        btns.add(uploadBtn);

        bottom.add(btns, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);
    }

    private void selectImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();

            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            previewLabel.setIcon(new ImageIcon(scaled));

            uploadBtn.setEnabled(true);
            deleteImgBtn.setEnabled(true);
        }
    }

    private void clearImage() {
        selectedFile = null;
        previewLabel.setIcon(null);
        uploadBtn.setEnabled(false);
        deleteImgBtn.setEnabled(false);
    }

    private void upload() {
        try {
            String content = contentArea.getText().trim();

            String imagePath = null;
            if (selectedFile != null) {
                new File("uploads").mkdirs();
                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File dest = new File("uploads" + File.separator + fileName);
                Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = dest.getPath();
            }

            new FeedDAO().insertFeed(SessionUser.id, content, imagePath);

            clearImage();
            contentArea.setText("");

            mainFrame.showFeed(); // 🔥 이게 핵심

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
