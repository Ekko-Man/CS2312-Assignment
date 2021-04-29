public class CmdHire extends RecordedCommand {
    private Employee e;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length !=2) {
                throw new ExInsufficientCommandArguments();
            }

            Company c = Company.getInstance();
            if (c.hasEmployee(cmdParts[1])!=null) {
                throw new ExEmployeeNameAlreadyExists();
            }

            this.e = new Employee(cmdParts[1]);
            c.addEmployee(this.e);

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExEmployeeNameAlreadyExists e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommandArguments e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeEmployee(this.e);
        addRedoCommand(this);
    };

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addEmployee(this.e);
        addUndoCommand(this);
    };
}
