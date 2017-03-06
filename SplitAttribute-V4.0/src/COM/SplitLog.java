package COM;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断msg是否为认证失败，若是则切割msg字段，提取括号里面的内容
 * 
 * @author YH
 *
 */
public class SplitLog {

	// msg 抓取一条字段
	private String msg;
	// list 存放字段拆分的列表
	List<String> list = new ArrayList<String>();
	private String create_time;

	/**
	 * 判断msg是否为认证失败，如果是，则切割msg，否则返回
	 * 
	 * @param msg
	 * @return list
	 */
	public List<String> Split(String msg,String create_time) {
		this.create_time=create_time;
		this.msg = msg;
		System.out.println(msg);
		// 判断msg是否为认证失败
		if (is_authentication_fails()) {
			System.out.println("ture");
			list = extractMessageByRegular(msg,create_time);
			return list;
		} else {
			System.out.println("false");
			return null;
		}

	}

	/**
	 * 判断提取出来的msg是否包含“认证失败”这四个字
	 * 
	 * @param msg
	 * @return matcher.matches()
	 */
	public boolean is_authentication_fails() {

		String str = msg;
		String pattern = ".+认证失败.+";
		// 模式匹配
		Pattern pat = Pattern.compile(pattern);
		// 匹配结果
		Matcher matcher = pat.matcher(str);
		return matcher.matches();
	}

	/**
	 * 使用正则表达式提取中括号中的内容
	 * 
	 * @param msg
	 * @return list
	 */
	public static List<String> extractMessageByRegular(String msg,String create_time) {

		// list 接受拆分结果列表
		List<String> list = new ArrayList<String>();
		// 模式匹配
		Pattern pattern = Pattern.compile("(\\([^\\)]*\\))");
		// 匹配结果
		Matcher matcher = pattern.matcher(msg);
		while (matcher.find()) {
			list.add(matcher.group().substring(1, matcher.group().length() - 1));
		}
		list.add(create_time);
		return list;
	}

}
