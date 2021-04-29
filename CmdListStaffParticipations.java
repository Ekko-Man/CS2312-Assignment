public class CmdListStaffParticipations implements Command {
    @Override
    public void execute(String[] cmdParts) {
        Company co = Company.getInstance();
        co.listStaffParticipations();
    }
}
