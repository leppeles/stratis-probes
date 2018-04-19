package superclass;

public class SubcImpl extends SupercImpl {

	@Override
	public void writer(Superc superc) {
		((Subc)superc).setNum2(5);
		super.writer(superc);
	}

}
