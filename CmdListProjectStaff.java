public class CmdListProjectStaff implements Command {
    @Override
    public void execute(String[] cmdParts) {
        try {
            Company co = Company.getInstance();
            co.listProjectStaff(cmdParts[1]);
        } catch (ExProjectIsNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
