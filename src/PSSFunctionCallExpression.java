import java.util.*;

/**
 * This class models function call expressions.
 */
public class PSSFunctionCallExpression extends PSSExpression {

	/** a path to an instance */
	PSSHierarchicalIDExpression m_path;

	/** a method name of the instance */
	String m_id;

	/** the arguments for the method call */
	List<PSSExpression> m_args = null;

	/**
	 * Constructs a function call expression.
	 *
	 * @param path a path to an instance
	 * @param id   a method name of the instance
	 * @param args the arguments for the method call
	 */
	PSSFunctionCallExpression(PSSHierarchicalIDExpression path, String id, List<PSSExpression> args) {
		m_path = path;
		m_id = id;
		m_args = args;
	}

	@Override
	public PSSVal eval(PSSInst var) {
		PSSMessage.Debug("[PSSFunctionCall] calling function " + m_id + " of " + m_path);
		/* Evaluate arguments */
		List<PSSVal> actuals = m_args.stream().map(a -> a.eval(var)).toList();
		PSSInst inst = m_path.getInst(var);

		// m_id may refer to a user defined function

		// Find the component instance containing the definition of the function m_id.
		PSSInst ci = inst.getComponentInst();
		PSSModel cm = ci.getTypeModel();

		// Find the function definition
		PSSModel m = cm.findDeclaration(m_id);

		PSSVal res = null;
		if (m instanceof PSSFunctionModel) {
			// Invoke the PSS native function
			PSSFunctionModel fm = (PSSFunctionModel) m;
			PSSFunctionInst fi = fm.declInst(ci, actuals);
			res = fi.eval(var);
		} else {
			// m_id may refer to a builtin method associated to var
			try {
				res = inst.evalMethod(m_id, actuals);
			} catch (NoSuchMethodException e) {
				PSSMessage.Error("", "Function " + m_id + " is not defined.");
			}
		}

		return res;
	}

}
