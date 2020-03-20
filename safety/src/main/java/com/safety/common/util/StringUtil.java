package com.safety.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    public StringUtil() {
    }

    public static List<String> handleTagCondition(String name) {
        String[] strs = name.split(",");
        List<String> strList = new ArrayList<String>();
        if (null != strs && strs.length > 0) {
            for (String str : strs) {
                if (!StringUtils.isEmpty(str)) {
                    strList.add(str);
                }
            }
        }
        return strList;
    }

    /**
     * 是否空串
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (s == null || "".equals(s.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 鬃贩创鬃换,贩种挝null
     * 
     * @param str
     *            创创览鬃贩创
     * @param defaultValue
     *            哪热照行适种
     * @return value 创览汉档照适
     */
    public static int getAsInt(String str, int defaultValue) {
        // 防止strParameterGeted为空
        int i_value;
        try {
            if (str == null || str.equals(""))
                i_value = defaultValue;
            else
                i_value = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            i_value = 0;
        }
        return i_value;
    }
    public static double getAsDouble(String str, double defaultValue) {
        // 防止strParameterGeted为空
        double i_value;
        try {
            if (str == null || str.equals(""))
                i_value = defaultValue;
            else
                i_value = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            i_value = 0d;
        }
        return i_value;
    }
    /**
     * 是否是正确的email地址
     */
    public static boolean isEmail(String parameter) {
        if (parameter == null) {
            return false;
        }
        Pattern reEmail = null;
        Matcher matcher = null;
        try {
            reEmail = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
            matcher = reEmail.matcher(parameter);
            return matcher.find();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return true;
        }
    }
    
    /**
     * 过滤 \r \n \t \s 等符号
     * @param str
     * @return
     */
    public static String filterBlankChars(String str){
        return filterChars(str, "\\s*|\t|\r|\n");
    }
    
    /**
     * 过滤字符串中的特殊字符
     * @param str 需要过滤的字符串
     * @param regex 过滤表达式，例：\\s*|\t|\r|\n 去掉 空格、回车、换行符、制表符
     * @return
     */
    public static String filterChars(String str, String regex){
        if(str == null){
            return null;
        }
        String content = new String(str);
        Pattern p = Pattern.compile(regex);
        return p.matcher(content).replaceAll("");
    }
    
    /**
     * 格式化金额，将小数位后三位变为小数位后两位
     * @param money
     * @return
     */
	public static String formatMoney(String money) {
		if(money == null){
			return "0.00";
		}
		if (money.indexOf(".") == -1) {
			money = money + ".000";
		}
		return money.substring(0, money.indexOf(".") + 3);
	}
	
	/**
	 * 对象转化为字符串
	 * null直接返回null
	 * @Title: objToString  
	 * @param obj
	 * @return
	 */
	public static String objToString(Object obj) {
		if(null == obj){
			return null;
		}
		return String.valueOf(obj);
	}

	public static String objToString(Object obj, String defaultNullStr){
	    if(null == obj){
	        return defaultNullStr;
        }
        return String.valueOf(obj);
    }

    /**
     * 转义模糊(like)查询参数中出现的特殊字符（\ _ %）
     * @param parameter
     * @return
     */
	public static String escapeLikeQueryParameter(String parameter){

	    return parameter.replaceAll("\\\\","\\\\\\\\")
                .replaceAll("\\_", "\\\\_")
                .replaceAll("\\%", "\\\\%");
    }

    public static String randomStr(int count){
	    String allChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String result = "";
	    for(int i=0;i<count;i++){
	        Random random = new Random();
	        int rand = random.nextInt(61);
            result +=allChars.charAt(rand);
        }

        return result;
    }

    public static String escapeMiniRichText(String str){
	    if (!isEmpty(str)) {
            str = str.replaceAll("(&rdquo;)|(&ldquo;)|(&deg;)|(&middot;)|(&mu;)|(&times;)|(&Phi;)|(&divide;)","");
        }
        return str;
    }

    /**
     * 将Html富文本的HTML内容转化为为文本文字
     * @param htmlStr
     * @return
     * @throws Exception
     */
    public static String html2Text(String htmlStr) throws Exception {
    	if(isEmpty(htmlStr)) {
    		return "";
    	}
        Html2TextUtil parse = new Html2TextUtil();
        Reader in=new StringReader(htmlStr);
        parse.parse(in);
        String txt = parse.getText().trim();
	    return txt;
    }

    /**
     * 将明文中的手机号转换为138****6666格式
     * @param phone
     * @return
     */
    public static String telHide(String phone){
        if(!StringUtil.isEmpty(phone) && phone.length() > 3){
            String hiddenPhone = phone.substring(0,3)+"****"+phone.substring(phone.length() > 6 ? phone.length()-4:3,phone.length());
            return hiddenPhone;
        }
        return phone;
    }

    /**
     * 将明文中的邮箱转换成eam****@acmtc.com形式
     * @param email
     * @return
     */
    public static String eamilHide(String email){
        if(!StringUtil.isEmpty(email) && email.lastIndexOf("@") > 3){
            String hiddenEmail = email.substring(0,3)+"****"+email.substring(email.lastIndexOf("@"),email.length());
            return hiddenEmail;
        }
        return email;
    }

    public static String uuid(){
        return UUID.randomUUID()+"";
    }
    /*切除字符串后几位*/
    public static String resectionEndNumStr(String str, int num){
        if(null == str || str.length() < num){
            return str;
        }
        return str.substring(0, str.length() - num);
    }
    /*切除字符串前几位*/
    public static String resectionStartNumStr(String str, int num){
        if(null == str || str.length() < num){
            return str;
        }
        return str.substring(num, str.length());
    }
}
