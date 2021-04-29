public class CmdCreateProject extends RecordedCommand {
    private Project p;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 2) {
                throw new ExInsufficientCommandArguments();
            }

            this.p = new Project(cmdParts[1]);

            Company c = Company.getInstance();
            c.addProject(this.p);

            addUndoCommand(this);
            clearRedoList();
            System.out.printf("Project created: [%s] %s (%s)\n", this.p.getCode(), this.p.getTitle(),
                    this.p.getDateSetup());

        } catch (ExInsufficientCommandArguments e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeProject(this.p);
        Project.projectNumMinusOne();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addProject(this.p);
        Project.projectNumAddOne();
        addUndoCommand(this);
    }
}
