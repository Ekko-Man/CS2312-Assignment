public class ExtensionProject extends Project{
    private static int[] number_of_extension_project = new int[999];
    private String eCode;

    public ExtensionProject(String projectCode, String title) {
        super(projectCode, title);
        int pCode =projectCodeToInt(projectCode);
        number_of_extension_project[pCode]++; // Excaption Max eProject 9 throw Excaption
        this.eCode = "-E"+number_of_extension_project[pCode];
    }

    @Override
    public String getCode() {
        return super.getCode() + this.eCode;
    }

    @Override
    public int compareTo(Project another) 
    {
        return this.getCode().compareTo(another.getCode());
    }

    public static int projectCodeToInt(String projectCode) {
        if (projectCode.contains("-E")) return Integer.parseInt(projectCode.split("P")[1].split("-E")[0]);
        else return Integer.parseInt(projectCode.split("P")[1]);
    }

    public static void extensionProjectNumMinusOne(String projectCode) {
        number_of_extension_project[projectCodeToInt(projectCode)]--;
    }

    public static void extensionProjectNumAddOne(String projectCode) {
        number_of_extension_project[projectCodeToInt(projectCode)]++;
    }

}

