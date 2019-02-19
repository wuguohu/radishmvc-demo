package win.dord.open.radishmvc;

public class RadishException extends Exception {
	private static final long serialVersionUID = -2896459178015482929L;
	public static final int ERR_CODE_SERVICE_NOT_FOUND = 100;
	public static final int ERR_CODE_SERVICE_CANT_ACCESS = 101;
	public static final int ERR_CODE_ACTION_NOT_FOUND = 102;
	public static final int ERR_CODE_ACTION_FAILED = 103;
	public static final int ERR_CODE_VIEW_FORWARD_FAILED = 104;
	
	public static final int ERR_CODE_TYPE_NOT_SUPPORT = 110;
	private int code;
	private String[] params;
	public RadishException() {
		this(0);
	}
	
	public RadishException(int code, String... params) {
		super();
		this.code = code;
		this.params = params;
	}

	@Override
	public String getMessage() {
		switch (code) {
		case ERR_CODE_SERVICE_NOT_FOUND:
			return "service " + params[0] + " not found";
		case ERR_CODE_SERVICE_CANT_ACCESS:
			return "service " + params[0] + " cant access";
		case ERR_CODE_ACTION_NOT_FOUND:
		case ERR_CODE_ACTION_FAILED:
			return "service " + params[0] +  " with action " + params[1] + " execute failed";
		case ERR_CODE_VIEW_FORWARD_FAILED:
			return "servlet forward to " + params[0] + " failed";
		case ERR_CODE_TYPE_NOT_SUPPORT:
			return "param type " + params[0] + " not support";
		default:
			return super.getMessage();
		}
	}
	
	

}
