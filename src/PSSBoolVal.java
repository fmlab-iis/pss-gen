
public class PSSBoolVal extends PSSVal {

	boolean m_val;

	public PSSBoolVal(String text) {
		m_val = Boolean.parseBoolean(text);
	}

	public PSSBoolVal(boolean val) {
		m_val = val;
	}

	public String getText() {
		return String.valueOf(m_val);
	}

	public int toInt() {
		PSSMessage.Error("INTVAL", "BoolVal cannot be converted to int");
		return 0;
	}

	public String toString() {
		PSSMessage.Error("INTVAL", "IntVal cannot be converted to string");
		return "";
	}

	public boolean toBool() {
		return m_val;
	}

	public PSSBoolVal LogicalAnd(PSSVal rhs) {
		boolean rhs_val = rhs.toBool();
		return new PSSBoolVal(m_val && rhs_val);
	}

	public PSSBoolVal LogicalOr(PSSVal rhs) {
		boolean rhs_val = rhs.toBool();
		return new PSSBoolVal(m_val || rhs_val);
	}

	public PSSBoolVal LogicalNot() {
		return new PSSBoolVal(!m_val);
	}

	@Override
	public int compareTo(PSSVal o) {
		if (o instanceof PSSBoolVal) {
			PSSBoolVal b = (PSSBoolVal) o;
			return Boolean.valueOf(m_val).compareTo(Boolean.valueOf(b.m_val));
		}
		return super.compareTo(o);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PSSBoolVal) {
			PSSBoolVal b = (PSSBoolVal) o;
			return m_val == b.m_val;
		}
		return false;
	}

}
