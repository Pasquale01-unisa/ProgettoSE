package projectse.model.action;

import java.io.File;
import java.io.Serializable;
import projectse.controller.MyProjectSEViewController;

/**
 *
 * @author group07
 */
public class ActionDeleteFile implements Action, Serializable {
    private File file;

    public ActionDeleteFile(File file) {
        this.file = file;
    }

    @Override
    public String getAction() {
        return "Delete file -> " + file.toString();
    }

    @Override
    public void executeAction() {
        if (file != null && file.exists()) {
            boolean isDeleted = file.delete();
            if (isDeleted) {
                MyProjectSEViewController.showSuccessPopup("Deleted File", "File successfully deleted: " + file, false);
            } else {
                MyProjectSEViewController.showErrorPopup("Error", "Unable to delete the file: " + file);
            }
        } else {
            MyProjectSEViewController.showWarningPopup("File not found", "File not found or already deleted: " + file);
        }
    }
}

