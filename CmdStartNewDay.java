public class CmdStartNewDay extends RecordedCommand{
    private String oldDay;
    private String newDay;

    @Override
    public void execute(String[] cmdParts) {
        SystemDate sysD = SystemDate.getInstance();
        oldDay = sysD.toString();
        newDay = cmdParts[1];
        sysD.set(newDay);
        
        System.out.println("Done.");
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        SystemDate.getInstance().set(oldDay);
        addRedoCommand(this);
    };
    
    @Override
    public void redoMe() {
        SystemDate.getInstance().set(newDay);
        addUndoCommand(this);
    };
}
