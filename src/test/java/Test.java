import cn.edu.upc.mp.service.ValidCheck;
import org.springframework.beans.factory.annotation.Autowired;

public class Test {
    public static void main(String[] args) {
        ValidCheck validCheck = new ValidCheck();
        validCheck.patientCheck(1);
    }
}
