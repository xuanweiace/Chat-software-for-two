import java.io.*;

public class FileIO{
    private String filename = "user_and_pwd.txt";

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileIO() {
    }

    public boolean isinFile(String id,String pwd,boolean onlyId) {
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        boolean is_inFile = false;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
//                System.out.println(s[0]+"," + s[1]);
                if (onlyId == false && s[0].equals(id) && s[1].equals(pwd)) is_inFile = true;
                if (onlyId == true && s[0].equals(id)) is_inFile = true;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is_inFile;
    }
    public boolean addtoFile(String id, String pwd) {
        File file = new File(filename);
        BufferedWriter writer = null;//true表示追bai加到末du尾
        try {
            writer = new BufferedWriter(new FileWriter(file,true));
            writer.append(id + " " + pwd+"\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        FileIO io = new FileIO();
        io.addtoFile("12453","456");
        System.out.println(io.isinFile("12453", "456",false));
    }
}
