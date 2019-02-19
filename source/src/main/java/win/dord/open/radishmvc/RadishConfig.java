package win.dord.open.radishmvc;

public class RadishConfig {
	public static boolean log = true;
	// View文件路径
	private String viewBase = "/";
	// Model类方法名获取key
	private String actionParam = "method";
	
	public String getViewBase() {
		return viewBase;
	}

	public void setViewBase(String viewBase) {
		this.viewBase = StringUtils.isNotEmpty(viewBase) ? viewBase : "/";
	}

	public String getActionParam() {
		return actionParam;
	}

	public void setActionParam(String actionParam) {
		this.actionParam = StringUtils.isNotEmpty(actionParam) ? actionParam : "method";
	}

}
