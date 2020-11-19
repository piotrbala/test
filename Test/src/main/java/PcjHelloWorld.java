
import java.io.File;
import java.io.IOException;
import org.pcj.PCJ;
import org.pcj.RegisterStorage;
import org.pcj.StartPoint;
import org.pcj.Storage;

/**
 * @author pbala
 */
@RegisterStorage(PcjHelloWorld.Shared.class)
public class PcjHelloWorld implements StartPoint {

    @Storage(PcjHelloWorld.class)             // Zmienna współdzielona
    enum Shared {
        a
    };
    double a;

    public static void main(String[] args) throws IOException {
        PCJ.executionBuilder(PcjHelloWorld.class)
                .addNodes(new File("nodes.txt"))
                .start();
    }

    @Override
    public void main() throws Throwable {
        int p = PCJ.threadCount();
        int i = PCJ.myId();
        System.out.println("I am " + i + " out of  " + p);

//        PCJ.barrier();
        if (PCJ.myId() == 0) {
            a = 1.0;
        }
        System.out.println("I am " + i + " a= " + a);

          if (PCJ.myId() == 0) PCJ.broadcast(a, Shared.a);
//          PCJ.barrier();        
          PCJ.waitFor(Shared.a);
          System.out.println("I am " + i + " a2 = " + a);
    }
}
