package br.cefetmg.es.irest.controler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IpUtils {

	public static final String PATTERN =
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static boolean validate(final String ip){
	      Pattern pattern = Pattern.compile(PATTERN);
	      Matcher matcher = pattern.matcher(ip);
	      return matcher.matches();
	}

}
