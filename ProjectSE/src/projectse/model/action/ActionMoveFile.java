package projectse.model.action;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import projectse.controller.MyProjectSEViewController;

/**
 *
 * @author group07
 */
public class ActionMoveFile implements Action, Serializable {
    private String fromPathString;
    private String toDirectoryPathString; 

    public ActionMoveFile(String fromPathString, String toDirectoryPathString) {
        this.fromPathString = fromPathString;
        this.toDirectoryPathString = toDirectoryPathString;
    }

    @Override
    public String getAction() {
        Path fromPath = Paths.get(fromPathString);
        return "Move file -> " + fromPath.getFileName().toString() + " from " + fromPathString + " to " + toDirectoryPathString;
    }

    @Override
    public void executeAction() {
        Path fromPath = Paths.get(fromPathString);
        Path toPath = Paths.get(toDirectoryPathString, fromPath.getFileName().toString());

        try {
            Files.move(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
            MyProjectSEViewController.showSuccessPopup("Moving File", "File successfully moved: " + fromPath.getFileName().toString(), false);
        } catch (IOException e) {
            MyProjectSEViewController.showErrorPopup("Error!", "An error occurred while moving the file: " + e.getMessage());
        }
    }
}