public class CmdGiveAssignmentSuggestions implements Command {
    @Override
    public void execute(String[] cmdParts) {
        try {
            Company co = Company.getInstance();
            co.giveAssignmentSuggestions(cmdParts[1]);
        } catch (ExProjectIsNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
