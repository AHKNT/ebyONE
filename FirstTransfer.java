import java.util.ArrayList;

public class FirstTransfer{
    private int sesseionID;
    private ArrayList<String> recieveData;
    private ArrayList<int> submitData;
    private Socket socket;

    public FirstTransfer(Socket soket){
        this.socket = soket;
    }

    public void recieve(String message){

    }
}