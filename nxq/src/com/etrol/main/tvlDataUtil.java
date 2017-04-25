package com.etrol.main;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.etrol.model.BaseInfoAssayResult;
import com.etrol.model.WorkFlow;
import com.etrol.util.DBHelper;

/**
 * @author jiangyi
 *
 */
public class tvlDataUtil {
	private static final String Q_AGREEMENT = "AA 55 FF FF";
	private static final String H_AGREEMENT = "00 00 41 02 19 04 02 04";
	private static  String AGREEMENT = "00 00";//"AA 55 FF FF 47 00 00 00 C1 02";// 协议头可变部分
	
	private static String FENQUID="0D 01";//分区ID//支持8个分区，也就是0~7，0用于背景，给你用的只有1~7
	private static String FENQUID_H = "00";
	private static String[] XYWH={"10 08 B8 00 28 00 40 00 10 00","10 08 48 00 28 00 40 00 10 00","10 08 B8 00 40 00 40 00 10 00","10 08 48 00 58 00 B0 00 10 00","10 08 48 00 10 00 40 00 10 00","10 08 48 00 40 00 40 00 10 00","10 08 B8 00 10 00 40 00 10 00"};
	private static final String NEXT_PLAY_ITEM_DATA = "0C 01 04" ;
	private static final String NEXT_PLAY_ITEM_DATAI ="0E 01 00 09 01 00";
	private static final String NEXT_PLAY_ITEM_DATAII="14 03 01 0A 01 11 0A 00 01 00 00 00 10 07 00 10 00 12 06 01 00 00 00 00 04 13";//"12 06 01 00 00 00 00 04";// 指定接下来播放项数据包的属性"
	
	
	public static String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv+" ");   
	    }   
	    return stringBuilder.toString();   
	}   
	
	
	/**
	 * 将指定字符串转换成unicode编码 byte[]
	 * 
	 * @param s
	 * @return StringEncodingUtil
	 */
	public static String stringToUnicode(String s) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	/**
	 * unicode转字符串 String
	 * 
	 * @param unicode
	 * @return StringEncodingUtil
	 */
	public static String unicodeToString(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\u");
		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}
		return string.toString();
	}

	/**
	 * 字符串转换成十六进制字符串
	 * 
	 * @param String
	 *            str 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	 */
	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	/**
	 * 十六进制转换字符串
	 * 
	 * @param String
	 *            str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * 数据组合 String
	 * 
	 * @param text
	 * @return StringEncodingUtil
	 * @throws UnsupportedEncodingException 
	 */
	public static String tlvData(String text,int i) throws Exception {
		if ("".equals(text))
			return "";
		
		

		StringBuffer result = new StringBuffer();
		String[] agreementArrayStrings = H_AGREEMENT.split(" ");
		String isNum = agreementArrayStrings[2];
		result.append(Q_AGREEMENT+" ");
		result.append(AGREEMENT + " ");
		result.append(H_AGREEMENT+" 0"+i+" 00 ");
		result.append(FENQUID+" ");
		result.append("0"+i+" ");
		result.append(XYWH[i-1]+" ");
		result.append(NEXT_PLAY_ITEM_DATA+" ");
		result.append(NEXT_PLAY_ITEM_DATAI+ " ");
		result.append(NEXT_PLAY_ITEM_DATAII+ " ");
		String str = bytesToHexString(text.getBytes("GB2312"));
		int len = removeTrim(str).length()/2;
		String hextext = "";
		if (len<128) {
			hextext = Integer.toHexString(removeTrim(str).length()/2);
			if (hextext.length()<2) {
				hextext = "0"+hextext;
			}
		}
		else if (len<=256) {
			    hextext = "81 "+Integer.toHexString(removeTrim(str).length()/2);
		}
		else{
			hextext = Integer.toHexString(removeTrim(str).length()/2);
			if (hextext.length()==3) {
				hextext="82 "+hextext.substring(1, 3)+" 0"+hextext.substring(0,1);
			}
		}
		
		
		
		
		result.append(hextext+" "+str);
		String string = removeTrim(result.toString());
		String s = Integer.toHexString(string.length()/2-6);
		
		
		if (s.length()==2) {
			result.replace(12, 14, s.substring(0, 2));
		}
		else if (s.length()==3) {
			result.replace(12, 14, s.substring(0, 2));
			result.replace(15, 17, s.substring(2, 3)+"0");
		}else if(s.length()==4){
			result.replace(12, 14, s.substring(0, 2));
			result.replace(15, 17, s.substring(2, 4));
		}
		
//		result.append(str2HexStr(text)+ " ");

//		if ("C1".equals(isNum)) {// 校验和(为C1时才需要计算校验和)
//			String string = removeTrim(result.toString());
//			String string2 = CRC(string).toUpperCase();
//			StringBuffer num = new StringBuffer();
//			if (string2.length() == 3) {
//				num.append(string2.charAt(1));
//				num.append(string2.charAt(2) + " ");
//				num.append("0");
//				num.append(string2.charAt(0));
//			} else {
//				num.append(string2.charAt(2));
//				num.append(string2.charAt(3) + " ");
//				num.append(string2.charAt(0));
//				num.append(string2.charAt(1));
//			}
//			result.append(num.toString());
//		}
		return result.toString().toUpperCase();

	}

	/**
	 * 字符串去空格 String
	 * 
	 * @param str
	 * @return tvlDataUtil
	 */
	public static String removeTrim(String str) {
		return str.replaceAll(" ", "");
	}

	/**
	 * 求校验和算法 String
	 * 
	 * @param s
	 * @return tvlDataUtil
	 */
	public static String CRC(String s) {
		int c = 0;
		for (int i = 0; i < s.length(); i += 2) {
			c += Integer.valueOf(s.substring(i, i + 2), 16);
		}
		return Integer.toHexString(c);
	}

	/**
	 * 连接数据库获取数据 void tvlDataUtil
	 */
	public static String readData() {
		Connection connection = DBHelper.getConnection();
		List<BaseInfoAssayResult> list = null;
		StringBuffer sbBuffer = null;
		if (connection == null) {
			System.out.println("数据库连接失败，请检查!");
			return "";
		}
		String sql = "select ID,CarCode,AssayResultID,ConciergeDate,WeightNet from crk_WorkFlow cw where OutInType=9 order by ConciergeDate desc"; // 查询语句
		try {
			Statement ps = connection.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			List<WorkFlow> list2 = new ArrayList<WorkFlow>();
			WorkFlow workFlow = null;
			if (rs.next()) {
				workFlow = new WorkFlow();
				workFlow.setId(rs.getString(1));
				workFlow.setCarcode(rs.getString(2)==null?" ":rs.getString(2));
				workFlow.setAssayresultid(rs.getString(3));
				if(rs.getString(4)==null){
					workFlow.setConciergeDate(" ");
				}else{
			
				workFlow.setConciergeDate(rs.getString(4).substring(0, 19));
				}
				workFlow.setWeightNet(rs.getString(5)==null?" ":rs.getString(5));
				
				list2.add(workFlow);
				
			}
			// System.out.println(workFlow.toString());
			String sqlStr ="select ba.IsImpurityDeduction,ba.IsWaterDeduction,i.ItemName,a.GradeName,ba.StoreHouseCode from baseinfo_AssayResult ba LEFT JOIN baseinfo_Item i ON i.Code=ba.ItemCode LEFT JOIN baseinfo_Grade a ON a.Code=ba.GradeCode where ba.ID="+workFlow.getAssayresultid();
//			String sqlString = "select b.AssayName,bai.Value from baseinfo_AssayResult ba left join baseinfo_AssayResultItem bai on ba.ID=bai.ResultID"
//					+ " left join baseinfo_Assay b on bai.AssayCode=b.Code where ba.ID="
//					+ workFlow.getAssayresultid();// 根据sql查询结果组合查询
			Statement psStatement = (Statement) connection.createStatement();
			ResultSet rSet = psStatement.executeQuery(sqlStr);
			List<BaseInfoAssayResult> list1 = new ArrayList<BaseInfoAssayResult>();
			while (rSet.next()) {
				BaseInfoAssayResult assayResult = new BaseInfoAssayResult();
				assayResult.setIsImpurityDeduction(rSet.getString(1)==null?" ":rSet.getString(1));
				assayResult.setIsWaterDeduction(rSet.getString(2)==null?" ":rSet.getString(2));
				assayResult.setItemCode(rSet.getString(3)==null?" ":rSet.getString(3));
				assayResult.setGradeCode(rSet.getString(4)==null?" ":rSet.getString(4));
				assayResult.setStoreHouseCode(rSet.getString(5)==null?" ":rSet.getString(5));
				
				list1.add(assayResult);
				
				
			}
			
			sbBuffer = new StringBuffer();
			for (BaseInfoAssayResult baseInfoAssayResult : list1) {
				
				sbBuffer.append(baseInfoAssayResult.getIsImpurityDeduction());
				sbBuffer.append(",");
				sbBuffer.append(baseInfoAssayResult.getIsWaterDeduction());
				sbBuffer.append(",");
				sbBuffer.append(baseInfoAssayResult.getStoreHouseCode());
				sbBuffer.append(",");
				sbBuffer.append(baseInfoAssayResult.getItemCode()+"/"+baseInfoAssayResult.getGradeCode());
				sbBuffer.append(",");
				
			}
		  for(WorkFlow workFlowl:list2){
			  sbBuffer.append(workFlowl.getCarcode());
			  sbBuffer.append(",");
			  sbBuffer.append(workFlowl.getWeightNet());
			  sbBuffer.append(",");
			  sbBuffer.append(workFlowl.getConciergeDate());
			  
			 
		  }	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("2222222222222"+sbBuffer.toString());
		return sbBuffer.toString();
	}

	public static byte[] hexStringToByte(String hex) {
		hex = hex.toUpperCase();
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	public static byte[] stringToGbkByte(String str){
		byte[] newtemp = null;
		try {
			byte[] temp=str.getBytes("utf-8");
            newtemp=new String(temp,"utf-8").getBytes("gbk");//转换后的编码方式
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newtemp;
	}

	
	
	public static void main(String[] args) {
		String s = "abc";
		System.out.println(readData());
	}
	
	
	

	
	
	
}
