package projectse.model.action;

import java.io.File;
import java.io.Serializable;
import projectse.controller.MyProjectSEViewController;

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
                    MyProjectSEViewController.showSuccessPopup("Eliminazione File", "File eliminato con successo: " + file, false);
                } else {
                    MyProjectSEViewController.showErrorPopup("Errore", "Impossibile eliminare il file: " + file);
                }
            } else {
                MyProjectSEViewController.showWarningPopup("File non trovato", "File non trovato o già eliminato: " + file);
            }
    }
}

