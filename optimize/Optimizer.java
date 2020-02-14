package optimize;
import arch.*;
import java.util.*;


public interface Optimizer{
	public List<Instruction> Optimize(List<Instruction> insts);
}
