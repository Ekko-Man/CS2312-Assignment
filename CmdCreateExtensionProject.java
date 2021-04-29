public class CmdCreateExtensionProject extends RecordedCommand {
    private ExtensionProject p;
    private String projectCode;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 3) {
                throw new ExInsufficientCommandArguments();
            }
        
            Company c = Company.getInstance();
            projectCode = cmdParts[1];
            c.findProject(projectCode); // If not project will throw ExProjectIsNotFound
            p = new ExtensionProject(cmdParts[1], cmdParts[2]);
            c.addProject(p);

            addUndoCommand(this);
            clearRedoList();
            System.out.printf("Project created: [%s] %s (%s)\n", p.getCode(), p.getTitle(),
                    p.getDateSetup());
        } catch (ExInsufficientCommandArguments e) {
            System.out.println(e.getMessage());
        } catch (ExProjectIsNotFound e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeProject(p);
        ExtensionProject.extensionProjectNumMinusOne(projectCode);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addProject(p);
        ExtensionProject.extensionProjectNumAddOne(projectCode);
        addUndoCommand(this);
    }
}
